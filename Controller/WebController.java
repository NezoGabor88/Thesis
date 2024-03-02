package iit.unimiskolc.FRI.controller;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import iit.unimiskolc.FRI.business.MtmtPublicationsClient;
import iit.unimiskolc.FRI.business.PublicationService;
import iit.unimiskolc.FRI.model.Publication;
import iit.unimiskolc.FRI.vo.LinkVo;
import iit.unimiskolc.FRI.vo.PaperVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@Slf4j
public class WebController {

    private final MtmtPublicationsClient mtmtPublicationsClient;

    private final PublicationService publicationService;


    @GetMapping("/")
    public String index(
            Model model
    ) {
        model.addAttribute("content", "main");
        //model.addAttribute("template", "content");
        return "index";
    }

    @GetMapping("/syncPublications")
    @ResponseBody
    public String syncPublications() {
        publicationService.syncPublications();
        return "Publications database updated successfully";
    }

    @GetMapping("/papers")
    public String subpage(Model model) {
        List<PaperVo> papers = publicationService.getPublications().stream()
                .map(WebController::mapVo)
                .collect(Collectors.toList());
        model.addAttribute("content", "papers");
        model.addAttribute("papers", papers);
        return "index";
    }

    private static PaperVo mapVo(Publication entity) {
        List<String> loc = new ArrayList<>();

        // 10 : 4 pp. 285-291. , 7 p. (2020) -->

        if (!Strings.isNullOrEmpty(entity.getVolume()) && !Strings.isNullOrEmpty(entity.getIssue())) {
            loc.add(entity.getVolume() + " : " + entity.getIssue());
        }
        if (!Strings.isNullOrEmpty(entity.getFirstPage()) || !Strings.isNullOrEmpty(entity.getLastPage())) {
            loc.add("pp. " + Strings.nullToEmpty(entity.getFirstPage()) + "-" + Strings.nullToEmpty(entity.getLastPage()) + ".");
        }
        if (entity.getPageLength() != null) {
            loc.add(" , " + entity.getPageLength() + " p.");
        }
        if (entity.getPublishedYear() != null) {
            loc.add("(" + entity.getPublishedYear() + ")");
        }

        String journalLocation = Joiner.on(" ").join(loc);
        List<LinkVo> links = entity.getLinks().stream()
                .map(publicationLink -> new LinkVo(
                        publicationLink.getLink(),
                        publicationLink.getSourceName()))
                .collect(Collectors.toList());
        return new PaperVo(
                entity.getTitle(),
                entity.getJournalLabel(),
                journalLocation,
                links
        );
    }

    @GetMapping("/favicon.ico")
    public String favicon() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/papers_old")
    public String Papers_Old(

            Model model
    ) {
        model.addAttribute("content", "papers_old");
        return "index";
    }

    @GetMapping("/team")
    public String team(

            Model model
    ) {
        model.addAttribute("content", "team");
        return "index";
    }

    @GetMapping("/FRItoolbox")
    public String FRItoolbox(

            Model model
    ) {
        model.addAttribute("content", "FRItoolbox");
        return "index";
    }


    @GetMapping("/examples")
    public String examples(

            Model model
    ) {
        model.addAttribute("content", "examples");
        return "index";
    }


    @GetMapping("/SFMItoolbox")
    public String SFMItoolbox(

            Model model
    ) {
        model.addAttribute("content", "SFMItoolbox");
        return "index";


    }


    @GetMapping("/links")
    public String links(

            Model model
    ) {
        model.addAttribute("content", "links");
        return "index";

    }


}