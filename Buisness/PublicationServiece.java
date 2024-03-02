package iit.unimiskolc.FRI.business;

import com.google.common.collect.Lists;
import iit.unimiskolc.FRI.model.Publication;
import iit.unimiskolc.FRI.model.PublicationLink;
import iit.unimiskolc.FRI.repository.PublicationLinkRepository;
import iit.unimiskolc.FRI.repository.PublicationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class PublicationService {
    private final MtmtPublicationsClient mtmtPublicationsClient;
    private final PublicationRepository publicationRepository;
    private final PublicationLinkRepository publicationLinkRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static Publication convertDtoToEntity(MtmtPublicationsClient.MtmtPublicationDto dto) {
        String journalLabel = Optional.ofNullable(dto.getJournal())
                .map(MtmtPublicationsClient.MtmtJournalDto::getLabel)
                .orElse(null);

        Publication publication = Publication.builder()
                .title(dto.getLabel())
                .mtid(String.valueOf(dto.getMtid()))
                .publishedYear(dto.getPublishedYear())
                .status(dto.getStatus())
                .label(dto.getLabel())
                .journalLabel(journalLabel)
                .volume(dto.getVolume())
                .issue(dto.getIssue())
                .firstPage(dto.getFirstPage())
                .lastPage(dto.getLastPage())
                .pageLength(dto.getPageLength())
                .build();

        List<PublicationLink> links = Optional.ofNullable(dto.getIdentifiers())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(e -> {
                    String source = Optional.ofNullable(e.getSource()).map(MtmtPublicationsClient.MtmtSourceDto::getName).orElse(null);
                    return PublicationLink.builder()
                            .link(e.getRealUrl())
                            .sourceName(source)
                            .publication(publication)
                            .build();
                })
                .collect(Collectors.toList());
        publication.setLinks(links);
        return publication;
    }

    public List<Publication> getPublications() {
        return Lists.newArrayList(publicationRepository.findAll());
    }

    public void syncPublications() {
        List<MtmtPublicationsClient.MtmtPublicationDto> articles = mtmtPublicationsClient.fetchAllArticles();
        Collection<Publication> entities = articles
                .stream()
                .map(PublicationService::convertDtoToEntity)
                .collect(Collectors.toList());
        publicationRepository.deleteAll();
        publicationLinkRepository.deleteAll();
        publicationRepository.saveAll(entities);
        log.info("{} Publications database updated", dateFormat.format(new Date()));
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void refreshPublicationsDatabase() {
        this.syncPublications();
    }

}
