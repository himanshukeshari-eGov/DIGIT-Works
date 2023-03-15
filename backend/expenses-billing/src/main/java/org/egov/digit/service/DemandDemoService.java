package org.egov.digit.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.validator.DemandDemoValidator;
import org.egov.digit.util.ResponseInfoFactory;
import org.egov.digit.web.models.DemandDemoResponse;
import org.egov.digit.web.models.MusterRollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DemandDemoService {

    @Autowired
    private DemandDemoValidator demandDemoValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;

    private DemandDemoResponse createBillDemand(MusterRollRequest musterRollRequest){
        demandDemoValidator.validateCreateBillDemandRequest(musterRollRequest);



//        ResponseInfo responseInfo = responseInfoFactory.createResponseInfoFromRequestInfo(musterRollRequest.getRequestInfo(), true);
//        DemandDemoResponse contractResponse = DemandDemoResponse.builder().responseInfo(responseInfo).contracts(Collections.singletonList(null).build();
//        log.info("Contract created");
        //return contractResponse;
return null;

    }

}
