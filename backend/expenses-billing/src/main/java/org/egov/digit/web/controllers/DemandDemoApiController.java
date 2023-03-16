package org.egov.digit.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.service.DemandDemoService;
import org.egov.digit.util.ResponseInfoFactory;
import org.egov.digit.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-03-15T12:39:54.253+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("")
public class DemandDemoApiController {

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@Autowired
	private DemandDemoService demandDemoService;

	@Autowired
	private ResponseInfoFactory responseInfoFactory;

	@Autowired
	public DemandDemoApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@RequestMapping(value = "/demand/demo/v1/_create", method = RequestMethod.POST)
	public ResponseEntity<DemandDemoResponse> demandV1CreatePost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody MusterRollRequest musterRollRequest) {


//		ContractResponse contractResponse = contractService.createContract(contractRequest);
//		return new ResponseEntity<ContractResponse>(contractResponse, HttpStatus.OK);

		return new ResponseEntity<DemandDemoResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

	@RequestMapping(value = "/demand/demo/v1/_search", method = RequestMethod.POST)
	public ResponseEntity<DemandDemoResponse> demandV1SearchPost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody DemandSearchRequest body) {

		List<BillDemand> billDemands = demandDemoService.searchBillDemand(body);
		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(body.getRequestInfo(), true);
		Integer count = demandDemoService.countAllBillDemand(body);
		DemandDemoResponse billDemandResponse = DemandDemoResponse.builder().responseInfo(responseInfo).billDemands(billDemands).totalCount(count).build();
		return new ResponseEntity<DemandDemoResponse>(billDemandResponse, HttpStatus.OK);
	}

}
