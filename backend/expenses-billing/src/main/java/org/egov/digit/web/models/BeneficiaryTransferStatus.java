package org.egov.digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BeneficiaryTransferStatus {

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("ifscCode")
    private String ifscCode;

    @JsonProperty("rbiSequenceNumber")
    private String rbiSequenceNumber;

    @JsonProperty("sequenceDate")
    private Long sequenceDate;

    @JsonProperty("endToEndId")
    private String endToEndId;

    @JsonProperty("status")
    private String status;

}