package org.egov.digit.repository.querybulder;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.web.models.DemandSearchRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BillDemandsQueryBuilder {

    private static final String FETCH_BILL_DEMAND_QUERY = "";
    public String getBillDemandsSearchQuery(DemandSearchRequest demandSearchRequest, List<Object> preparedStmtList, boolean isCountQuery) {
        return "";
    }
}
