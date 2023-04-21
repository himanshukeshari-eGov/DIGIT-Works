package org.egov.digit.expense.calculator.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.calculator.service.ExpenseCalculatorService;
import org.egov.digit.expense.calculator.util.ResponseInfoFactory;
import org.egov.digit.expense.calculator.web.models.Bill;
import org.egov.digit.expense.calculator.web.models.BillResponse;
import org.egov.digit.expense.calculator.web.models.PurchaseBillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-04-17T16:59:23.221+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("/purchase")
public class PurchaseBillApiController {

	@Autowired
	private final ObjectMapper objectMapper;

	@Autowired
	private final HttpServletRequest request;

	@Autowired
	private ExpenseCalculatorService expenseCalculatorService;

	@Autowired
	private ResponseInfoFactory responseInfoFactory;

	@Autowired
	public PurchaseBillApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	@RequestMapping(value = "/v1/_createbill", method = RequestMethod.POST)
	public ResponseEntity<BillResponse> worksCalculatorPurchaseV1CreatebillPost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody PurchaseBillRequest purchaseBillRequest) {
		List<Bill> purchaseBills = expenseCalculatorService.createPurchaseBill(purchaseBillRequest);
		ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(purchaseBillRequest.getRequestInfo(), true);
		BillResponse billResponse = BillResponse.builder()
				.responseInfo(responseInfo)
				.bills(purchaseBills)
				.build();

		return new ResponseEntity<BillResponse>(billResponse, HttpStatus.OK);

	}

	@RequestMapping(value = "/v1/_updatebill", method = RequestMethod.POST)
	public ResponseEntity<BillResponse> worksCalculatorPurchaseV1UpdatebillPost(
			@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody PurchaseBillRequest body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				return new ResponseEntity<BillResponse>(objectMapper.readValue(
						"{  \"pagination\" : {    \"offSet\" : 9.301444243932576,    \"limit\" : 70.61401241503108,    \"sortBy\" : \"sortBy\",    \"totalCount\" : 3.616076749251911,    \"order\" : \"\"  },  \"bill\" : [ {    \"billDetails\" : [ {      \"lineItems\" : [ {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 2,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 5        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 5.962133916683182,        \"status\" : \"ACTIVE\"      }, {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 2,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 5        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 5.962133916683182,        \"status\" : \"ACTIVE\"      } ],      \"fromPeriod\" : 1680307200000,      \"netLineItemAmount\" : 1682899199000,      \"toPeriod\" : 1682899199000,      \"payableLineItems\" : [ null, null ],      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : { },      \"referenceId\" : \"WAGESEEKER-01\",      \"paymentStatus\" : \"paymentStatus\"    }, {      \"lineItems\" : [ {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 2,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 5        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 5.962133916683182,        \"status\" : \"ACTIVE\"      }, {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 2,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 5        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 5.962133916683182,        \"status\" : \"ACTIVE\"      } ],      \"fromPeriod\" : 1680307200000,      \"netLineItemAmount\" : 1682899199000,      \"toPeriod\" : 1682899199000,      \"payableLineItems\" : [ null, null ],      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : { },      \"referenceId\" : \"WAGESEEKER-01\",      \"paymentStatus\" : \"paymentStatus\"    } ],    \"businessService\" : \"WORKS-MUSTERROLE\",    \"fromPeriod\" : 1680307200000,    \"dueDate\" : 1685491199000,    \"billDate\" : 6.027456183070403,    \"netPayableAmount\" : 500,    \"additionalDetails\" : { },    \"payer\" : {      \"identifier\" : \"RURALMINISTRY-MUKTHA\",      \"type\" : \"DEPARTMENT\"    },    \"netPaidAmount\" : 1.4658129805029452,    \"referenceId\" : \"MUSTERROLE-WAGE-01\",    \"tenantId\" : \"pb.amritsar\",    \"toPeriod\" : 1682899199000,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"paymentStatus\" : \"paymentStatus\",    \"status\" : \"ACTIVE\"  }, {    \"billDetails\" : [ {      \"lineItems\" : [ {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 2,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 5        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 5.962133916683182,        \"status\" : \"ACTIVE\"      }, {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 2,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 5        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 5.962133916683182,        \"status\" : \"ACTIVE\"      } ],      \"fromPeriod\" : 1680307200000,      \"netLineItemAmount\" : 1682899199000,      \"toPeriod\" : 1682899199000,      \"payableLineItems\" : [ null, null ],      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : { },      \"referenceId\" : \"WAGESEEKER-01\",      \"paymentStatus\" : \"paymentStatus\"    }, {      \"lineItems\" : [ {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 2,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 5        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 5.962133916683182,        \"status\" : \"ACTIVE\"      }, {        \"amount\" : 500,        \"auditDetails\" : {          \"lastModifiedTime\" : 2,          \"createdBy\" : \"createdBy\",          \"lastModifiedBy\" : \"lastModifiedBy\",          \"createdTime\" : 5        },        \"tenantId\" : \"pb.amritsar\",        \"id\" : \"67dcdf9f-40c4-4621-be35-1982ecc650aa\",        \"headCode\" : \"WAGE\",        \"type\" : \"PAYABLE\",        \"additionalDetails\" : { },        \"paidAmount\" : 5.962133916683182,        \"status\" : \"ACTIVE\"      } ],      \"fromPeriod\" : 1680307200000,      \"netLineItemAmount\" : 1682899199000,      \"toPeriod\" : 1682899199000,      \"payableLineItems\" : [ null, null ],      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : { },      \"referenceId\" : \"WAGESEEKER-01\",      \"paymentStatus\" : \"paymentStatus\"    } ],    \"businessService\" : \"WORKS-MUSTERROLE\",    \"fromPeriod\" : 1680307200000,    \"dueDate\" : 1685491199000,    \"billDate\" : 6.027456183070403,    \"netPayableAmount\" : 500,    \"additionalDetails\" : { },    \"payer\" : {      \"identifier\" : \"RURALMINISTRY-MUKTHA\",      \"type\" : \"DEPARTMENT\"    },    \"netPaidAmount\" : 1.4658129805029452,    \"referenceId\" : \"MUSTERROLE-WAGE-01\",    \"tenantId\" : \"pb.amritsar\",    \"toPeriod\" : 1682899199000,    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"paymentStatus\" : \"paymentStatus\",    \"status\" : \"ACTIVE\"  } ],  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}",
						BillResponse.class), HttpStatus.NOT_IMPLEMENTED);
			} catch (IOException e) {
				return new ResponseEntity<BillResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<BillResponse>(HttpStatus.NOT_IMPLEMENTED);
	}

}