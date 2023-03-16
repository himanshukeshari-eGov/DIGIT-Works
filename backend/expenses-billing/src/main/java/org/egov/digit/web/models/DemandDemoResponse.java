package org.egov.digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.egov.common.contract.response.ResponseInfo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * DemandDemoResponse
 */
@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandDemoResponse {
	@JsonProperty("ResponseInfo")

	@Valid
	private ResponseInfo responseInfo = null;

	@JsonProperty("BillDemands")
	@Valid
	private List<BillDemand> billDemands = null;

	@JsonProperty("Pagination")

	@Valid
	private Pagination pagination = null;

	@JsonProperty("TotalCount")
	private Integer totalCount = 0;

	public DemandDemoResponse addBillDemandsItem(BillDemand billDemand) {
		if (this.billDemands == null) {
			this.billDemands = new ArrayList<>();
		}
		this.billDemands.add(billDemand);
		return this;
	}

}
