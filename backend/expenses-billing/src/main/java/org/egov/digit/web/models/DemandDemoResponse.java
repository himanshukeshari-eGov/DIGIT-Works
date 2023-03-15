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
	@JsonProperty("responseInfo")

	@Valid
	private ResponseInfo responseInfo = null;

	@JsonProperty("billDemands")
	@Valid
	private List<BillDemand> demands = null;

	@JsonProperty("pagination")

	@Valid
	private Pagination pagination = null;

	public DemandDemoResponse addBillDemandsItem(BillDemand demandsItem) {
		if (this.demands == null) {
			this.demands = new ArrayList<>();
		}
		this.demands.add(demandsItem);
		return this;
	}

}
