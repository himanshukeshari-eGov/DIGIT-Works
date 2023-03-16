package org.egov.digit.enrichment;


import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.egov.digit.util.CommonUtil;
import org.egov.digit.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class DemandDemoEnrichment {
    @Autowired
    private CommonUtil commonUtil;

    public BillDemand enrichMusterRoll(MusterRollRequest musterRollRequest){
        return createBillDemandFromMusterRoll(musterRollRequest);
    }

    private BillDemand createBillDemandFromMusterRoll(MusterRollRequest musterRollRequest) {
        MusterRoll musterRoll = musterRollRequest.getMusterRoll();
        // Bill Demand details
        String id = String.valueOf(UUID.randomUUID());
        String tenantId = musterRoll.getTenantId();
        String billNumber = musterRoll.getMusterRollNumber();
        BigInteger billDate = BigInteger.valueOf(Instant.now().toEpochMilli());
        String headOfAccount = getHeadOfAccount();
        String ifmsSanctionNumber = getRandomNumberAsString(8);
        String purpose = "Mukta Wage Seeker Transfer";
        AuditDetails auditDetails = commonUtil.getAuditDetails(musterRollRequest.getRequestInfo().getUserInfo().getUuid(), null, true);
        BillDemand billDemand = BillDemand.
                builder().
                id(id).
                tenantId(tenantId).
                billNumber(billNumber).
                billDate(billDate).
                headOfAccount(headOfAccount).
                ifmsSanctionNumber(ifmsSanctionNumber).
                purpose(purpose).
                auditDetails(auditDetails).
                build();
        populateBeneficiaries(musterRoll,billDemand);
        return billDemand;
    }

    private void populateBeneficiaries(MusterRoll musterRoll, BillDemand billDemand) {
        List<IndividualEntry> individualEntries = musterRoll.getIndividualEntries();
        List<Beneficiary> beneficiaries = new ArrayList<>();
        for(IndividualEntry individual:individualEntries){
            String individualId = individual.getIndividualId();
            Object additionalDetails = individual.getAdditionalDetails();
            String mobileNo = commonUtil.findValue(additionalDetails,"mobileNo").get();
            String accountHolderName = commonUtil.findValue(additionalDetails,"accountHolderName").get();
            String accountType = commonUtil.findValue(additionalDetails,"accountType").get();
            String skillCode = commonUtil.findValue(additionalDetails,"skillCode").get();
            String bankDetails = commonUtil.findValue(additionalDetails,"bankDetails").get();
            final String[] split = bankDetails.split("-");
            String accountNumber = split[0];
            String ifscCode = split[1];
            String status = "PENDING";
            BigDecimal amount = individual.getActualTotalAttendance().multiply(BigDecimal.valueOf(commonUtil.getAmountForSkill(skillCode)));

            final Beneficiary beneficiary = Beneficiary.builder().
                    id(individualId).
                    mobileNumber(mobileNo).
                    name(accountHolderName).
                    accountType(accountType).
                    accountNumber(accountNumber).
                    ifscCode(ifscCode).
                    status(status).
                    amount(amount).
                    build();
            beneficiaries.add(beneficiary);

        }
        billDemand.setBeneficiaries(beneficiaries);
    }

    private String getHeadOfAccount() {
        return getRandomNumberAsString(4)+
                "/"+getRandomNumberAsString(2)+
                    "/"+getRandomNumberAsString(3)+
                        "/"+getRandomNumberAsString(4)+
                            "/"+getRandomNumberAsString(5)+
                                "/"+getRandomNumberAsString(3);
    }

    private String getRandomNumberAsString(int count){
        return RandomStringUtils.random(count, false, true);
    }
}
