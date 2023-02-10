package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.RequestInfoWrapper;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.works.service.ContractService;
import org.egov.works.util.ResponseInfoFactory;
import org.egov.works.web.models.*;
import io.swagger.annotations.ApiParam;
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

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-02-01T15:45:33.268+05:30")

@Controller
@RequestMapping("/contract/v1")
public class ContractApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    public ContractApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/_create", method = RequestMethod.POST)
    public ResponseEntity<ContractResponse> contractV1CreatePost(@ApiParam(value = "Details for the new contract.", required = true) @Valid @RequestBody ContractRequest contracts) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ContractResponse>(objectMapper.readValue("{  \"pagination\" : {    \"offSet\" : 1.024645700144157789424070870154537260532379150390625,    \"limit\" : 12.3151353677725552415722631849348545074462890625,    \"sortBy\" : \"sortBy\",    \"totalCount\" : 1.489415909854170383397331534069962799549102783203125,    \"order\" : \"asc\"  },  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"contracts\" : [ {    \"totalContractedamount\" : 6.02745618307040320615897144307382404804229736328125,    \"agreementDate\" : 5.962133916683182377482808078639209270477294921875,    \"endDate\" : 7.061401241503109105224211816675961017608642578125,    \"documents\" : [ {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    }, {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    } ],    \"contractType\" : \"WORK_ORDER\",    \"contractNumber\" : \"contractNumber\",    \"securityDeposit\" : 1.46581298050294517310021547018550336360931396484375,    \"additionalDetails\" : \"{}\",    \"defectLiabilityPeriod\" : 5.63737665663332876420099637471139430999755859375,    \"orgId\" : \"orgId\",    \"lineItems\" : [ {      \"estimateLineItemId\" : \"estimateLineItemId\",      \"noOfunit\" : 3.61607674925191080461672754609026014804840087890625,      \"amountBreakups\" : [ {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      }, {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      } ],      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 4      },      \"estimateId\" : \"estimateId\",      \"tenantId\" : \"pb.amritsar\",      \"unitRate\" : 9.301444243932575517419536481611430644989013671875,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : \"{}\"    }, {      \"estimateLineItemId\" : \"estimateLineItemId\",      \"noOfunit\" : 3.61607674925191080461672754609026014804840087890625,      \"amountBreakups\" : [ {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      }, {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      } ],      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 4      },      \"estimateId\" : \"estimateId\",      \"tenantId\" : \"pb.amritsar\",      \"unitRate\" : 9.301444243932575517419536481611430644989013671875,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : \"{}\"    } ],    \"executingAuthority\" : \"DEPARTMENT\",    \"auditDetails\" : {      \"lastModifiedTime\" : 7,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 4    },    \"tenantId\" : \"pb.amritsar\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"wfStatus\" : \"wfStatus\",    \"startDate\" : 2.3021358869347654518833223846741020679473876953125  }, {    \"totalContractedamount\" : 6.02745618307040320615897144307382404804229736328125,    \"agreementDate\" : 5.962133916683182377482808078639209270477294921875,    \"endDate\" : 7.061401241503109105224211816675961017608642578125,    \"documents\" : [ {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    }, {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    } ],    \"contractType\" : \"WORK_ORDER\",    \"contractNumber\" : \"contractNumber\",    \"securityDeposit\" : 1.46581298050294517310021547018550336360931396484375,    \"additionalDetails\" : \"{}\",    \"defectLiabilityPeriod\" : 5.63737665663332876420099637471139430999755859375,    \"orgId\" : \"orgId\",    \"lineItems\" : [ {      \"estimateLineItemId\" : \"estimateLineItemId\",      \"noOfunit\" : 3.61607674925191080461672754609026014804840087890625,      \"amountBreakups\" : [ {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      }, {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      } ],      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 4      },      \"estimateId\" : \"estimateId\",      \"tenantId\" : \"pb.amritsar\",      \"unitRate\" : 9.301444243932575517419536481611430644989013671875,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : \"{}\"    }, {      \"estimateLineItemId\" : \"estimateLineItemId\",      \"noOfunit\" : 3.61607674925191080461672754609026014804840087890625,      \"amountBreakups\" : [ {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      }, {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      } ],      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 4      },      \"estimateId\" : \"estimateId\",      \"tenantId\" : \"pb.amritsar\",      \"unitRate\" : 9.301444243932575517419536481611430644989013671875,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : \"{}\"    } ],    \"executingAuthority\" : \"DEPARTMENT\",    \"auditDetails\" : {      \"lastModifiedTime\" : 7,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 4    },    \"tenantId\" : \"pb.amritsar\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"wfStatus\" : \"wfStatus\",    \"startDate\" : 2.3021358869347654518833223846741020679473876953125  } ]}", ContractResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<ContractResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ContractResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public ResponseEntity<ContractResponse> contractV1SearchPost(@ApiParam(value = "") @Valid @RequestBody ContractCriteria contractCriteria) {

        RequestInfo requestInfo=contractCriteria.getRequestInfo();
        List<Contract> contracts = contractService.searchContracts(requestInfo, contractCriteria);
        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(requestInfo, true);
        ContractResponse contractResponse = ContractResponse.builder().responseInfo(responseInfo).contracts(contracts).pagination(contractCriteria.getPagination()).build();
        return new ResponseEntity<ContractResponse>(contractResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/_update", method = RequestMethod.POST)
    public ResponseEntity<ContractResponse> contractV1UpdatePost(@ApiParam(value = "", required = true) @Valid @RequestBody ContractRequest contracts) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ContractResponse>(objectMapper.readValue("{  \"pagination\" : {    \"offSet\" : 1.024645700144157789424070870154537260532379150390625,    \"limit\" : 12.3151353677725552415722631849348545074462890625,    \"sortBy\" : \"sortBy\",    \"totalCount\" : 1.489415909854170383397331534069962799549102783203125,    \"order\" : \"asc\"  },  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  },  \"contracts\" : [ {    \"totalContractedamount\" : 6.02745618307040320615897144307382404804229736328125,    \"agreementDate\" : 5.962133916683182377482808078639209270477294921875,    \"endDate\" : 7.061401241503109105224211816675961017608642578125,    \"documents\" : [ {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    }, {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    } ],    \"contractType\" : \"WORK_ORDER\",    \"contractNumber\" : \"contractNumber\",    \"securityDeposit\" : 1.46581298050294517310021547018550336360931396484375,    \"additionalDetails\" : \"{}\",    \"defectLiabilityPeriod\" : 5.63737665663332876420099637471139430999755859375,    \"orgId\" : \"orgId\",    \"lineItems\" : [ {      \"estimateLineItemId\" : \"estimateLineItemId\",      \"noOfunit\" : 3.61607674925191080461672754609026014804840087890625,      \"amountBreakups\" : [ {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      }, {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      } ],      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 4      },      \"estimateId\" : \"estimateId\",      \"tenantId\" : \"pb.amritsar\",      \"unitRate\" : 9.301444243932575517419536481611430644989013671875,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : \"{}\"    }, {      \"estimateLineItemId\" : \"estimateLineItemId\",      \"noOfunit\" : 3.61607674925191080461672754609026014804840087890625,      \"amountBreakups\" : [ {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      }, {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      } ],      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 4      },      \"estimateId\" : \"estimateId\",      \"tenantId\" : \"pb.amritsar\",      \"unitRate\" : 9.301444243932575517419536481611430644989013671875,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : \"{}\"    } ],    \"executingAuthority\" : \"DEPARTMENT\",    \"auditDetails\" : {      \"lastModifiedTime\" : 7,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 4    },    \"tenantId\" : \"pb.amritsar\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"wfStatus\" : \"wfStatus\",    \"startDate\" : 2.3021358869347654518833223846741020679473876953125  }, {    \"totalContractedamount\" : 6.02745618307040320615897144307382404804229736328125,    \"agreementDate\" : 5.962133916683182377482808078639209270477294921875,    \"endDate\" : 7.061401241503109105224211816675961017608642578125,    \"documents\" : [ {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    }, {      \"documentType\" : \"documentType\",      \"documentUid\" : \"documentUid\",      \"fileStore\" : \"fileStore\",      \"id\" : \"id\",      \"additionalDetails\" : \"{}\"    } ],    \"contractType\" : \"WORK_ORDER\",    \"contractNumber\" : \"contractNumber\",    \"securityDeposit\" : 1.46581298050294517310021547018550336360931396484375,    \"additionalDetails\" : \"{}\",    \"defectLiabilityPeriod\" : 5.63737665663332876420099637471139430999755859375,    \"orgId\" : \"orgId\",    \"lineItems\" : [ {      \"estimateLineItemId\" : \"estimateLineItemId\",      \"noOfunit\" : 3.61607674925191080461672754609026014804840087890625,      \"amountBreakups\" : [ {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      }, {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      } ],      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 4      },      \"estimateId\" : \"estimateId\",      \"tenantId\" : \"pb.amritsar\",      \"unitRate\" : 9.301444243932575517419536481611430644989013671875,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : \"{}\"    }, {      \"estimateLineItemId\" : \"estimateLineItemId\",      \"noOfunit\" : 3.61607674925191080461672754609026014804840087890625,      \"amountBreakups\" : [ {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      }, {        \"amount\" : 2.027123023002321833274663731572218239307403564453125,        \"estimateAmountBreakupId\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"additionalDetails\" : \"{}\"      } ],      \"auditDetails\" : {        \"lastModifiedTime\" : 7,        \"createdBy\" : \"createdBy\",        \"lastModifiedBy\" : \"lastModifiedBy\",        \"createdTime\" : 4      },      \"estimateId\" : \"estimateId\",      \"tenantId\" : \"pb.amritsar\",      \"unitRate\" : 9.301444243932575517419536481611430644989013671875,      \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",      \"additionalDetails\" : \"{}\"    } ],    \"executingAuthority\" : \"DEPARTMENT\",    \"auditDetails\" : {      \"lastModifiedTime\" : 7,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 4    },    \"tenantId\" : \"pb.amritsar\",    \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",    \"wfStatus\" : \"wfStatus\",    \"startDate\" : 2.3021358869347654518833223846741020679473876953125  } ]}", ContractResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<ContractResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ContractResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
