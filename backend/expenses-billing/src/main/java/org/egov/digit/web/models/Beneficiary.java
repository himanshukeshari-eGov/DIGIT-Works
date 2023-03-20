package org.egov.digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;


@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Beneficiary {

    @JsonProperty("pkId")
    private String pkId;

    @JsonProperty("id")
    private String id;

    @JsonProperty("billNumber")
    private String billNumber;

    @JsonProperty("name")
    private String name;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("ifscCode")
    private String ifscCode;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("accountType")
    private String accountType;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("status")
    private String status;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

}
