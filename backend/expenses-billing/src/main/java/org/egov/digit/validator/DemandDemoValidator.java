package org.egov.digit.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.digit.web.models.IndividualEntry;
import org.egov.digit.web.models.MusterRoll;
import org.egov.digit.web.models.MusterRollRequest;
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
}
