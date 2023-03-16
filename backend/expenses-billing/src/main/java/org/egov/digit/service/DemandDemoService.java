package org.egov.digit.service;

import digit.models.coremodels.Bill;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.repository.BillDemandDemoRepository;
import org.egov.digit.validator.DemandDemoValidator;
import org.egov.digit.util.ResponseInfoFactory;
import org.egov.digit.web.models.BillDemand;
import org.egov.digit.web.models.DemandDemoResponse;
import org.egov.digit.web.models.DemandSearchRequest;
import org.egov.digit.web.models.MusterRollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DemandDemoService {

    @Autowired
    private DemandDemoValidator demandDemoValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    @Autowired
    private BillDemandDemoRepository billDemandDemoRepository;

    private DemandDemoResponse createBillDemand(MusterRollRequest musterRollRequest){
        demandDemoValidator.validateCreateBillDemandRequest(musterRollRequest);



//        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(musterRollRequest.getRequestInfo(), true);
//        DemandDemoResponse contractResponse = DemandDemoResponse.builder().responseInfo(responseInfo).contracts(Collections.singletonList(null).build();
//        log.info("Contract created");
        //return contractResponse;
return null;

    }

    public List<BillDemand> searchBillDemand(DemandSearchRequest demandSearchRequest) {
        demandDemoValidator.validateSearchBillDemand(demandSearchRequest);
        List<BillDemand> billDemands = billDemandDemoRepository.getBillDemands(demandSearchRequest);
        return billDemands;
    }

    public Integer countAllBillDemand(DemandSearchRequest demandSearchRequest) {
        return billDemandDemoRepository.getBillDemandsCount(demandSearchRequest);
    }
}
