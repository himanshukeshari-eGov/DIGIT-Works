package org.egov.digit.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.config.Configuration;
import org.egov.digit.enrichment.DemandDemoEnrichment;
import org.egov.digit.kafka.Producer;
import org.egov.digit.repository.BillDemandDemoRepository;
import org.egov.digit.validator.DemandDemoValidator;
import org.egov.digit.util.ResponseInfoFactory;
import org.egov.digit.web.models.BillDemand;
import org.egov.digit.web.models.DemandDemoResponse;
import org.egov.digit.web.models.DemandSearchRequest;
import org.egov.digit.web.models.MusterRollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class DemandDemoService {

    @Autowired
    private DemandDemoValidator demandDemoValidator;

    @Autowired
    private ResponseInfoFactory responseInfoFactory;
    @Autowired
    private DemandDemoEnrichment demandDemoEnrichment;
    @Autowired
    private BillDemandDemoRepository billDemandDemoRepository;

    @Autowired
    private Configuration config;

    @Autowired
    private Producer producer;
    public List<BillDemand> createBillDemand(MusterRollRequest musterRollRequest){
        demandDemoValidator.validateCreateBillDemandRequest(musterRollRequest);
        BillDemand billDemand = demandDemoEnrichment.enrichMusterRoll(musterRollRequest);
        producer.push(config.getBillTopic(),billDemand);
        log.info("Contract created");
        return Collections.singletonList(billDemand);

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
