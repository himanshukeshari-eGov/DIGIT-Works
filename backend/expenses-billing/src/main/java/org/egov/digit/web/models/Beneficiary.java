package org.egov.digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;


@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Beneficiary {

    @JsonProperty("id")
    private String id;

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

}
