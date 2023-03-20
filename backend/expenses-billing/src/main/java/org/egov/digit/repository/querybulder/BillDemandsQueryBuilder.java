package org.egov.digit.repository.querybulder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.digit.config.Configuration;
import org.egov.digit.web.models.DemandSearchCriteria;
import org.egov.digit.web.models.DemandSearchRequest;
import org.egov.digit.web.models.Order;
import org.egov.digit.web.models.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class BillDemandsQueryBuilder {

    @Autowired
    private Configuration config;

    private static final String FETCH_BILL_DEMAND_QUERY = "SELECT bd.id as billDemand_id, bd.tenant_id as billDemand_tenantId, " +
            "bd.bill_number as billDemand_billNumber, bd.bill_date as billDemand_billDate, bd.net_amount as billDemand_netAmount, " +
            "bd.gross_amount as billDemand_grossAmount, bd.head_of_account as billDemand_headOfAccount, " +
            "bd.ifms_sanction_number as billDemand_ifmsSanctionNumber, bd.purpose as billDemand_purpose, " +
            "bd.created_by as billDemand_createdBy, bd.last_modified_by as billDemand_lastModifiedBy, bd.created_time as billDemand_createdTime, " +
            "bd.last_modified_time as billDemand_lastModifiedTime, " +
            "bdBenificiary.id as billDemandBeneficiary_Id, bdBenificiary.bill_number as billDemandBeneficiary_billNumber, " +
            "bdBenificiary.name as billDemandBeneficiary_name, bdBenificiary.account_number as billDemandBeneficiary_accountNumber, " +
            "bdBenificiary.ifsc_code as billDemandBeneficiary_ifscCode, bdBenificiary.mobile_number as billDemandBeneficiary_mobileNumber, " +
            "bdBenificiary.address as billDemandBeneficiary_address, bdBenificiary.account_type as billDemandBeneficiary_accountType, " +
            "bdBenificiary.amount as billDemandBeneficiary_amount, bdBenificiary.purpose as billDemandBeneficiary_purpose, " +
            "bdBenificiary.status as billDemandBeneficiary_status, bdBenificiary.created_by as billDemandBeneficiary_createdBy, " +
            "bdBenificiary.last_modified_by as billDemandBeneficiary_lastModifiedBy, bdBenificiary.created_time as billDemandBeneficiary_createdTime, " +
            "bdBenificiary.last_modified_time as billDemandBeneficiary_lastModifiedTime " +
            "FROM eg_wms_bill_demand bd " +
            "LEFT JOIN eg_wms_demand_beneficiaries bdBenificiary ON bd.bill_number = bdBenificiary.bill_number";

    private final String paginationWrapper = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY billDemand_lastModifiedTime DESC , billDemand_id) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    private static final String BILL_DEMAND_COUNT_QUERY = "SELECT DISTINCT(bd.id) from eg_wms_bill_demand bd " +
            "LEFT JOIN eg_wms_demand_beneficiaries bdBenificiary ON bd.bill_number = bdBenificiary.bill_number";

    private static final String COUNT_WRAPPER = "SELECT COUNT(*) FROM ({INTERNAL_QUERY}) as count";

    public String getBillDemandsSearchQuery(DemandSearchRequest demandSearchRequest, List<Object> preparedStmtList, boolean isCountQuery) {
        String query = isCountQuery ? BILL_DEMAND_COUNT_QUERY : FETCH_BILL_DEMAND_QUERY;


        StringBuilder queryBuilder = new StringBuilder(query);
        DemandSearchCriteria searchCriteria = demandSearchRequest.getDemandSearchCriteria();

        List<String> ids = searchCriteria.getIds();
        if (ids != null && !ids.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bd.id IN (").append(createQuery(ids)).append(")");
            addToPreparedStatement(preparedStmtList, ids);
        }

        if (StringUtils.isNotBlank(searchCriteria.getTenantId())) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bd.tenant_id=? ");
            preparedStmtList.add(searchCriteria.getTenantId());
        }

        List<String> billNumbers = searchCriteria.getBillNumbers();

        if (billNumbers != null && !billNumbers.isEmpty()) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bd.bill_number IN (").append(createQuery(billNumbers)).append(")");
            addToPreparedStatement(preparedStmtList, billNumbers);
        }

        if (searchCriteria.getBillDate() != null && !Objects.equals(searchCriteria.getBillDate(), BigInteger.ZERO)) {
            addClauseIfRequired(preparedStmtList, queryBuilder);
            queryBuilder.append(" bd.bill_date=? ");
            preparedStmtList.add(searchCriteria.getBillDate());
        }

        if (isCountQuery) {
            return queryBuilder.toString();
        }

        addOrderByClause(queryBuilder, demandSearchRequest.getPagination());
        return addPaginationWrapper(queryBuilder.toString(), preparedStmtList, demandSearchRequest.getPagination());
    }

    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (values.isEmpty())
            queryString.append(" WHERE ");
        else {
            queryString.append(" AND");
        }
    }

    private void addToPreparedStatement(List<Object> preparedStmtList, Collection<String> ids) {
        ids.forEach(id -> {
            preparedStmtList.add(id);
        });
    }

    private String createQuery(Collection<String> ids) {
        StringBuilder builder = new StringBuilder();
        int length = ids.size();
        for (int i = 0; i < length; i++) {
            builder.append(" ? ");
            if (i != length - 1) builder.append(",");
        }
        return builder.toString();
    }

    private void addOrderByClause(StringBuilder queryBuilder, Pagination pagination) {
        log.info("BillDemandsQueryBuilder::getBillDemandsQuery");
        //default
        if (pagination != null) {
            if (pagination.getSortBy()  == null ) {
                queryBuilder.append(" ORDER BY bd.created_time ");
            } else {
                switch (pagination.getSortBy()) {
                    case "bill_number":
                        queryBuilder.append(" ORDER BY bd.bill_number ");
                        break;
                    case "bill_date":
                        queryBuilder.append(" ORDER BY bill_date.type ");
                        break;
                    default:
                        queryBuilder.append(" ORDER BY est.created_time ");
                        break;
                }
            }
            if (pagination.getOrder() != null && pagination.getOrder().equals(Order.ASC)) {
                queryBuilder.append(" ASC ");
            } else {
                queryBuilder.append(" DESC ");
            }
        } else {
            queryBuilder.append(" ORDER BY bd.created_time ");
            queryBuilder.append(" DESC ");
        }
    }

    private String addPaginationWrapper(String query, List<Object> preparedStmtList, Pagination pagination) {
        log.info("BillDemandQueryBuilder::addPaginationWrapper");
        double limit = config.getDefaultLimit();
        double offset = config.getDefaultOffset();
        String finalQuery = paginationWrapper.replace("{}", query);

        if (pagination != null && pagination.getLimit() != null) {
            if (pagination.getLimit() <= config.getMaxLimit())
                limit = pagination.getLimit();
            else
                limit = config.getMaxLimit();
        }

        if (pagination != null && pagination.getOffSet() != null)
            offset = pagination.getOffSet();

        preparedStmtList.add(offset);
        preparedStmtList.add(limit + offset);

        return finalQuery;
    }

    public String getSearchCountQueryString(DemandSearchRequest demandSearchRequest, List<Object> preparedStmtList) {
        log.info("BillDemandsQueryBuilder::getSearchCountQueryString");
        String query = getBillDemandsSearchQuery(demandSearchRequest, preparedStmtList, true);
        if (query != null)
            return COUNT_WRAPPER.replace("{INTERNAL_QUERY}", query);
        else
            return query;
    }
}
