package org.egov.util;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.config.Configuration;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.OrgSearchCriteria;
import org.egov.web.models.OrgSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@Slf4j
public class OrganisationUtil {

    @Autowired
    private Configuration config;

    @Autowired
    private RestTemplate restTemplate;


    public void fetchOrganisationDetails(List<String> ids, RequestInfo requestInfo, String tenantId) {
        // fetch the organisation details from organisation service
        if (ids == null || ids.isEmpty()) {
            throw new CustomException("ORGANIZATION_IDS", "Organisation Ids are empty");
        }
        StringBuilder uri = new StringBuilder();
        uri.append(config.getOrganisationHost()).append(config.getOrganisationEndPoint());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri.toString());

        OrgSearchCriteria orgSearchCriteria = OrgSearchCriteria.builder()
                .id(ids)
                .tenantId(tenantId)
                .build();

        OrgSearchRequest orgSearchRequest = OrgSearchRequest.builder()
                .requestInfo(requestInfo)
                .searchCriteria(orgSearchCriteria)
                .build();

        Object response = null;
        log.info("BankAccountsService::fetchOrganisationDetails::call organisation search with tenantId::" + tenantId
                + "::organisation ids::" + ids);

        try {
            response = restTemplate.postForObject(uriBuilder.toUriString(), orgSearchRequest, Object.class);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            log.error("BankAccountsService::fetchOrganisationDetails::Error thrown from organisation search service::" + httpClientOrServerExc.getStatusCode());
            throw new CustomException("ORGANISATION_SEARCH_SERVICE_EXCEPTION", "Error thrown from organisation search service::" + httpClientOrServerExc.getStatusCode());
        }

        if (response == null) {
            log.error("Failed to validate organisation Id against Organisation service");
            throw new CustomException("ORGANISATION_RESPONSE", "Failed to validate organisation Id against Organisation service");
        }

        final String jsonPathForOrgs = "$.organisations.*.id";
        List<Object> orgRes = null;

        try {
            orgRes = JsonPath.read(response, jsonPathForOrgs);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse Organisation response");
        }

        for (String id : ids) {
            if (!orgRes.contains(id)) {
                throw new CustomException("ORGANISATION_NOT_FOUND", "Organisation with id " + id + " doesn't exist");
            }
        }

        log.info("BankAccountsService::fetchIndividualDetails::Organisation search fetched successfully");

    }

}
