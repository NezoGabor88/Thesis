package iit.unimiskolc.FRI.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import iit.unimiskolc.FRI.model.Author;
import iit.unimiskolc.FRI.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FriDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FriDataService.class);

    private static final List<String> REQUIRED_AUTHOR_MTMT_IDS = List.of("10056717", "10002124", "10002275", "10068496", "10000253");
    private final MtmtDataHandler mtmtDataHandler;
    private final AuthorRepository authorRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public FriDataService(final MtmtDataHandler mtmtDataHandler,
                          final AuthorRepository authorRepository,
                          final ObjectMapper objectMapper) {
        this.mtmtDataHandler = mtmtDataHandler;
        this.authorRepository = authorRepository;
        this.objectMapper = objectMapper;
    }

    public String syncRequiredMtmtAuthorsOnFriDatabase() {
        LOGGER.info(" Attempting to sync required authors from MTMT server to FRI database... ");
        final List<Author> authors = REQUIRED_AUTHOR_MTMT_IDS.stream().map(authorMtmtId -> {
            final Author author = mtmtDataHandler.queryAuthor(authorMtmtId);

            return author;
        }).collect(Collectors.toList());
        authorRepository.saveAll(authors);

        final ArrayNode resultNode = objectMapper.createArrayNode();
        authors.stream().map(Author::getFullName).forEach(resultNode::add);
        final String result = mapToString(resultNode);
        LOGGER.info(" Finished to sync required auhors form MTMT server to FRI database ");
        return result;
    }

    public String getFriAuthors() {
        LOGGER.info(" Attempting to query authors from Fri Database...");
        final Iterable<Author> authors = authorRepository.findAll();
        final String authorNames = StreamSupport.stream(authors.spliterator(), false)
                .map(Author::getFullName)
                .collect(Collectors.joining(" ,"));
        LOGGER.info(" Finished the query auhtors from FRI database");
        return authorNames;
    }

    private String mapToString(final ArrayNode arrayNode) {
        try {
            return objectMapper.writeValueAsString(arrayNode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
