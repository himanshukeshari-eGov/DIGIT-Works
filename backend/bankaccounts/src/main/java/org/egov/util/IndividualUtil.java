package org.egov.util;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.IndividualSearch;
import org.egov.web.models.IndividualSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@Slf4j
public class IndividualUtil {

    @Autowired
    private Configuration config;

    @Autowired
    private RestTemplate restTemplate;

    public void fetchIndividualDetails(List<String> ids, RequestInfo requestInfo, String tenantId){
        // fetch the individual details from individual service
        String uri = getSearchURLWithParams(tenantId).toUriString();

        IndividualSearch individualSearch = IndividualSearch.builder().id(ids).build();
        IndividualSearchRequest individualSearchRequest = IndividualSearchRequest.builder()
                .requestInfo(requestInfo).individual(individualSearch).build();

        Object response = null;
        log.info("BankAccountsService::fetchIndividualDetails::call individual search with tenantId::" + tenantId
                + "::organisation ids::" + ids);

        try {
            response = restTemplate.postForObject(uri, individualSearchRequest, Object.class);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.error("BankAccountsService::fetchIndividualDetails::Error thrown from individual service::" + httpClientOrServerExc.getStatusCode());
            throw new CustomException("INDIVIDUAL_SEARCH_SERVICE_EXCEPTION", "Error thrown from individual search service::" + httpClientOrServerExc.getStatusCode());
        }

        if (response == null) {
            log.error("Failed to validate individual Id against individual service");
            throw new CustomException("INDIVIDUAL_RESPONSE", "Failed to validate individual Id against individual service");
        }

        final String jsonPathForInds = "$.Individual.*.id";
        List<Object> indRes = null;

        try {
            indRes = JsonPath.read(response, jsonPathForInds);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse Organisation response");
        }

        for (String id : ids) {
            if (!indRes.contains(id)) {
                throw new CustomException("INDIVIDUAL_NOT_FOUND", "Individual with id " + id + " doesn't exist");
            }
        }

        log.info("BankAccountsService::fetchIndividualDetails::Individual search fetched successfully");
    }

    private UriComponentsBuilder getSearchURLWithParams(String tenantId) {
        StringBuilder uri = new StringBuilder();
        uri.append(config.getIndividualHost()).append(config.getIndividualEndPoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("limit", 100)
                .queryParam("offset", 0)
                .queryParam("tenantId", tenantId);

        return uriBuilder;
    }
}
