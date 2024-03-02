package iit.unimiskolc.FRI.controller;

import iit.unimiskolc.FRI.business.FriDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Set;

@Controller
@RequestMapping(path = "/v1")
public class FriDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriDataController.class);
    private final FriDataService friDataService;

    public FriDataController(final FriDataService friDataService) {
        this.friDataService = friDataService;
    }

    @GetMapping(path = "/syncAuthors")
    public @ResponseBody String syncAuthor() {
        LOGGER.info("Sync author request received with author %s");
        final String syncedAuthors = friDataService.syncRequiredMtmtAuthorsOnFriDatabase();
        String author = friDataService.syncRequiredMtmtAuthorsOnFriDatabase();
        return syncedAuthors;
    }


    @GetMapping(path = "/authors")
    public @ResponseBody Iterable<String> getAuthors() {
        LOGGER.info("Get authors request received...");
        final Set<String> authors = Collections.singleton(friDataService.getFriAuthors());
        LOGGER.info("Finished get authors request handling!");
        return authors;
    }


    @PutMapping(path = "/syncPublications")
    public @ResponseBody String syncPublications() {
        LOGGER.info("Sync author request received with author %s");
        final String syncedAuthors = friDataService.syncRequiredMtmtAuthorsOnFriDatabase();
        String author = friDataService.syncRequiredMtmtAuthorsOnFriDatabase();
        LOGGER.info(String.format("Finished sync author request! %s"));
        return syncedAuthors;
    }


    private void validateProperty(final String property) {
        // TODO: input validation for property
    }

}
