package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * EstimateDetail
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-12-30T13:05:25.880+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstimateDetail {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("sorId")
    private String sorId = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("unitRate")
    private Double unitRate = null;

    @JsonProperty("noOfunit")
    private Double noOfunit = null;

    @JsonProperty("uom")
    private String uom = null;

    @JsonProperty("uomValue")
    private Double uomValue = null;

    @JsonProperty("amountDetail")
    @Valid
    private List<AmountDetail> amountDetail = null;

    @JsonProperty("totalAmount")
    private Double totalAmount = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;


    public EstimateDetail addAmountDetailItem(AmountDetail amountDetailItem) {
        if (this.amountDetail == null) {
            this.amountDetail = new ArrayList<>();
        }
        this.amountDetail.add(amountDetailItem);
        return this;
    }

}
