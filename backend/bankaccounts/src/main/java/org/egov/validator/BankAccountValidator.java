package org.egov.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.tracer.model.CustomException;
import org.egov.util.IndividualUtil;
import org.egov.util.OrganisationUtil;
import org.egov.web.models.BankAccount;
import org.egov.web.models.BankAccountRequest;
import org.egov.web.models.BankAccountSearchCriteria;
import org.egov.web.models.BankAccountSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BankAccountValidator {

    @Autowired
    private OrganisationUtil organisationUtil;

    @Autowired
    private IndividualUtil individualUtilUtil;


    /**
     * validate the search bank account
     *
     * @param searchRequest
     */
    public void validateBankAccountOnSearch(BankAccountSearchRequest searchRequest) {
        log.info("BankAccountValidator::validateSearchBankAccount");
        BankAccountSearchCriteria searchCriteria = searchRequest.getBankAccountDetails();
        RequestInfo requestInfo = searchRequest.getRequestInfo();
        if (searchCriteria == null) {
            throw new CustomException("BANK_ACCOUNTS_SEARCH_CRITERIA_REQUEST", "Bank accounts search criteria request is mandatory");
        }
        if (StringUtils.isBlank(searchCriteria.getTenantId())) {
            throw new CustomException("TENANT_ID", "TenantId is mandatory");
        }
//        if (searchCriteria.getIds() != null && !searchCriteria.getIds().isEmpty() && searchCriteria.getIds().size() > 10) {
//            throw new CustomException("IDS", "Ids should be of max 10.");
//        }

    }

    public void validateServiceCode(BankAccountRequest bankAccountRequest) {
        RequestInfo requestInfo = bankAccountRequest.getRequestInfo();
        String tenantId = bankAccountRequest.getBankAccounts().get(0).getTenantId();
        List<BankAccount> bankAccounts = bankAccountRequest.getBankAccounts();

        Map<String, List<String>> serviceCodeIdsMap = new HashMap<>();

        for (BankAccount bankAccount: bankAccounts) {
            // If the service code already exists in the map, add the boundary to the existing list
            if (serviceCodeIdsMap.containsKey(bankAccount.getServiceCode())) {
                serviceCodeIdsMap.get(bankAccount.getServiceCode()).add(bankAccount.getReferenceId());
            }
            // If the service does not exist in the map, create a new list and add the boundary to it
            else {
                List<String> refIds = new ArrayList<>();
                refIds.add(bankAccount.getReferenceId());
                serviceCodeIdsMap.put(bankAccount.getServiceCode(), refIds);
            }
        }

        if (serviceCodeIdsMap.containsKey("ORG")) {
            organisationUtil.fetchOrganisationDetails(serviceCodeIdsMap.get("ORG"), requestInfo, tenantId);
        }
        if (serviceCodeIdsMap.containsKey("IND")) {
            individualUtilUtil.fetchIndividualDetails(serviceCodeIdsMap.get("IND"), requestInfo, tenantId);
        }

    }


}
