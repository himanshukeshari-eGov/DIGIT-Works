package org.egov.digit.web.models;

import javax.validation.Valid;

import org.egov.common.contract.request.RequestInfo;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DemandSearchRequest
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-15T12:39:54.253+05:30[Asia/Kolkata]")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandSearchRequest {
	@JsonProperty("RequestInfo")

	@Valid
	private RequestInfo requestInfo = null;

	@JsonProperty("SearchCriteria")

	@Valid
	private DemandSearchCriteria demandSearchCriteria = null;

	@JsonProperty("Pagination")

	@Valid
	private Pagination pagination = null;

}
