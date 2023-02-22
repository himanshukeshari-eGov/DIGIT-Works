package org.egov.works.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * The object will contain all the search parameters for contract service.
 */
@ApiModel(description = "The object will contain all the search parameters for contract service.")
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractCriteria {
    @JsonProperty("tenantId")
    @NotNull
    @Size(min = 2, max = 64)
    private String tenantId = null;

    @JsonProperty("ids")
    private List<String> ids = null;

    @JsonProperty("estimateIds")
    private List<String> estimateIds = null;

    @JsonProperty("estimateLineItemIds")
    private List<String> estimateLineItemIds = null;

    @JsonProperty("pagination")
    private Pagination pagination = null;


    public ContractCriteria addIdsItem(String idsItem) {
        if (this.ids == null) {
            this.ids = new ArrayList<>();
        }
        this.ids.add(idsItem);
        return this;
    }

    public ContractCriteria addEstimateIdsItem(String estimateIdsItem) {
        if (this.estimateIds == null) {
            this.estimateIds = new ArrayList<>();
        }
        this.estimateIds.add(estimateIdsItem);
        return this;
    }

    public ContractCriteria addEstimateLineItemIdsItem(String estimateLineItemIdsItem) {
        if (this.estimateLineItemIds == null) {
            this.estimateLineItemIds = new ArrayList<>();
        }
        this.estimateLineItemIds.add(estimateLineItemIdsItem);
        return this;
    }

}
