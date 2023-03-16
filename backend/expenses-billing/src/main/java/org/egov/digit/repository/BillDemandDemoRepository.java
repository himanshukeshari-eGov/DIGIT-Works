package org.egov.digit.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.repository.querybulder.BillDemandsQueryBuilder;
import org.egov.digit.repository.rowmapper.BillDemandsRowMapper;
import org.egov.digit.web.models.BillDemand;
import org.egov.digit.web.models.DemandSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class BillDemandDemoRepository {

    @Autowired
    private BillDemandsQueryBuilder billDemandsQueryBuilder;
    @Autowired
    private BillDemandsRowMapper billDemandsRowMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BillDemand> getBillDemands(DemandSearchRequest demandSearchRequest) {
        List<Object> preparedStmtList = new ArrayList<>();
        String query = billDemandsQueryBuilder.getBillDemandsSearchQuery(demandSearchRequest, preparedStmtList, false);
        List<BillDemand> billDemands = jdbcTemplate.query(query, billDemandsRowMapper, preparedStmtList.toArray());

        log.info("Fetched Bill Demands list based on given search criteria");

        return billDemands;
    }

    public Integer getBillDemandsCount(DemandSearchRequest demandSearchRequest) {
        List<Object> preparedStatement = new ArrayList<>();
        String query = billDemandsQueryBuilder.getSearchCountQueryString(demandSearchRequest, preparedStatement);

        if (query == null)
            return 0;

        Integer count = jdbcTemplate.queryForObject(query, preparedStatement.toArray(), Integer.class);
        log.info("Total Bill Demand count is : " + count);
        return count;
    }
}
