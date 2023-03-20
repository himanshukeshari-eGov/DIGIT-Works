package org.egov.digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PaymentStatus {

    @JsonProperty("billNumber")
    private String billNumber;

    @JsonProperty("billDate")
    private Long billDate;

    @JsonProperty("voucherNumber")
    private String voucherNumber;

    @JsonProperty("voucherDate")
    private Long voucherDate;

    @JsonProperty("beneficiaryTransferStatuses")
    private List<BeneficiaryTransferStatus> beneficiaryTransferStatuses;
}