package org.egov.digit.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.web.models.*;
import org.egov.tracer.model.CustomException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DemandDemoValidator {
    public void validateCreateBillDemandRequest(MusterRollRequest musterRollRequest) {
        if(musterRollRequest == null){
            throw new CustomException("MUSTER_ROLL","Muster roll is mandatory");
        }

        Map<String, String> errorMap = new HashMap<>();
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        if(StringUtils.isBlank(musterRoll.getTenantId())){
            errorMap.put("MUSTER_ROLL.TENANTID", "TenantId is mandatory");
        }

        List<IndividualEntry> individualEntries = musterRoll.getIndividualEntries();
        if(individualEntries == null || individualEntries.isEmpty()){
            errorMap.put("MUSTER_ROLL.INDIVIDUAL_ENTRIES", "Individual Entries are mandatory");
        }

        for(IndividualEntry entry: individualEntries){
            if(StringUtils.isBlank(entry.getIndividualId())){
                errorMap.put("MUSTER_ROLL.INDIVIDUAL_ENTRIES.INDIVIDUAL_ID", "Individual Id is mandatory");
            }
        }

        if (!errorMap.isEmpty()){
            throw new CustomException(errorMap);
        }
    }

    public void validateSearchBillDemand(DemandSearchRequest demandSearchRequest) {
        Map<String, String> errorMap = new HashMap<>();
        //Verify if RequestInfo and UserInfo is present
        validateRequestInfo(demandSearchRequest.getRequestInfo(), errorMap);
        //Verify the search criteria
        validateSearchCriteria(demandSearchRequest.getDemandSearchCriteria());
    }

    private void validateSearchCriteria(DemandSearchCriteria demandSearchCriteria) {
        if (demandSearchCriteria == null) {
            log.error("Search criteria is mandatory");
            throw new CustomException("EXPENSE_BILLING", "Search criteria is mandatory");
        }

        if (StringUtils.isBlank(demandSearchCriteria.getTenantId())) {
            log.error("Tenant ID is mandatory in expense billing request body");
            throw new CustomException("TENANT_ID", "Tenant ID is mandatory");
        }

        if (StringUtils.isBlank(demandSearchCriteria.getBillNumber())) {
            log.error("Bill Number is mandatory in expense billing request body");
            throw new CustomException("EXPENSE_BILLING", "Bill Number is mandatory");
        }
    }

    /* Validates Request Info and User Info */
    private void validateRequestInfo(RequestInfo requestInfo, Map<String, String> errorMap) {
        log.info("OrganisationServiceValidator::validateRequestInfo");
        if (requestInfo == null) {
            log.error("Request info is mandatory");
            throw new CustomException("REQUEST_INFO", "Request info is mandatory");
        }
        if (requestInfo.getUserInfo() == null) {
            log.error("UserInfo is mandatory in RequestInfo");
            throw new CustomException("USERINFO", "UserInfo is mandatory");
        }
        if (requestInfo.getUserInfo() != null && StringUtils.isBlank(requestInfo.getUserInfo().getUuid())) {
            log.error("UUID is mandatory in UserInfo");
            throw new CustomException("USERINFO_UUID", "UUID is mandatory");
        }
    }
}
