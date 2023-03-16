package org.egov.digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDemand {
    @JsonProperty("id")
    private String id;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("billNumber")
    private String billNumber;

    @JsonProperty("billDate")
    private Long billDate;

    @JsonProperty("netAmount")
    private BigDecimal netAmount;

    @JsonProperty("grossAmount")
    private BigDecimal grossAmount;

    @JsonProperty("headOfAccount")
    private String headOfAccount;

    @JsonProperty("ifmsSanctionNumber")
    private String ifmsSanctionNumber;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("beneficiaries")
    private List<Beneficiary> beneficiaries;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

    public BillDemand addBeneficiaryItem(Beneficiary beneficiaryItem) {
        if (this.beneficiaries == null) {
            this.beneficiaries = new ArrayList<>();
        }
        this.beneficiaries.add(beneficiaryItem);
        return this;
    }


}
