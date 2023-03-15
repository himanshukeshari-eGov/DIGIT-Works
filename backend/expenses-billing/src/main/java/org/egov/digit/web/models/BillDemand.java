package org.egov.digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.BigInteger;
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
    private BigInteger billDate;

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


    private List<Beneficiary> beneficiaries;


}
