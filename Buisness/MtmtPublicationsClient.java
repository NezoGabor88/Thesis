package iit.unimiskolc.FRI.business;

import com.fasterxml.jackson.databind.JsonNode;
import iit.unimiskolc.FRI.exception.MtmtDataNotFoundException;
import iit.unimiskolc.FRI.model.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class MtmtDataHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MtmtDataHandler.class);
    public static final int MAX_MTMT_QUERY_PAGE_SIZE = 500;
    private static final int PAGING_ITERATION_LIMIT = 100;

    // Common Properties
    private static final String CONTENT = "content";
    private static final String STATUS = "status";
    private static final String MTID = "mtid";
    // Paging properties

    // Author Properties
    private static final String FAMILY_NAME = "familyName";
    private static final String GIVENNAME = "givenName";
    private static final String AUX_NAME = "auxName";
    private static final String PUBLICATION_COUNT = "publicationCount";
    private static final String CITATION_COUNT = "citationCount";


    // Publication Properties


    public static final ParameterizedTypeReference<JsonNode> JSON_NODE_PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    private static final String GET_AUTHOR_PATH = "api/author";



    private final String mtmtTruststorePath;
    private final String mtmtTruststorePassword;
    private final String mtmtBaseUrl;
    private final Integer mtmtQueryPageSize;

    private RestTemplate restTemplate;

    public MtmtDataHandler(
            final MtmtConfig config
    ) {

        this.mtmtTruststorePath = config.getMtmtTruststorePath();
        this.mtmtTruststorePassword = config.getMtmtTruststorePassword();
        this.mtmtQueryPageSize = config.getMtmtQueryPageSize();
        mtmtBaseUrl = config.getMtmtUrl();
    }


    private HttpEntity<String> createRequestHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(headers);
    }


    private static Author extractAuthorData(final JsonNode responseBodyNode) {
        final JsonNode contentNode = Objects.requireNonNull(responseBodyNode).get(CONTENT);
        final String authorId = contentNode.get(MTID).asText();
        final String familyName = contentNode.get(FAMILY_NAME).asText();
        final String givenName = contentNode.get(GIVENNAME).asText();
        final String fullName = String.join("", familyName, givenName);
        final String status = contentNode.get(STATUS).asText();
        final String auxName = contentNode.get(AUX_NAME).asText();
        final Integer publicationCount = contentNode.get(PUBLICATION_COUNT).asInt();
        final Integer citationCount = contentNode.get(CITATION_COUNT).asInt();
        return new Author(authorId, fullName, familyName, givenName, status, auxName, publicationCount, citationCount);
    }


    public Author queryAuthor(final String authorMtmtId) {
        final String getAuthorUrl = String.join("/", mtmtBaseUrl, GET_AUTHOR_PATH, authorMtmtId) + "?format=json";
        LOGGER.info(String.format(" Attempt to perform HTTP request to query MTMT authors from url %s ", getAuthorUrl));
        final HttpEntity<String> requestHeaders = createRequestHeaders();
        try {

            final ResponseEntity<JsonNode> response = restTemplate.exchange(getAuthorUrl, HttpMethod.GET, requestHeaders, JSON_NODE_PARAMETERIZED_TYPE_REFERENCE);
            LOGGER.info(String.format(" Get Author status code: %s ", response.getStatusCode()));
            LOGGER.info(" Finished HTTP  exchange to query MTMT authors");
            final JsonNode responseBodyNode = response.getBody();
            return extractAuthorData(responseBodyNode);
        } catch (RestClientException e) {
            throw new MtmtDataNotFoundException(" Failed to request Author data from MTMT database doe to network issues!", e);
        } catch (Exception e) {
            throw new MtmtDataNotFoundException(" Failed to get Author data ", e);
        }
    }

    @PostConstruct
    public void initRestTemplate() {
        this.restTemplate = new RestTemplate();

    }


}