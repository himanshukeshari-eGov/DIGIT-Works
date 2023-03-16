package org.egov.digit.repository.rowmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.egov.digit.web.models.Beneficiary;
import org.egov.digit.web.models.BillDemand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class BillDemandsRowMapper  implements ResultSetExtractor<List<BillDemand>> {

    @Autowired
    private ObjectMapper mapper;
    @Override
    public List<BillDemand> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<String, BillDemand> billDemandMap = new LinkedHashMap<>();
        while (resultSet.next()) {
            String billDemand_id = resultSet.getString("billDemand_id");

            if (!billDemandMap.containsKey(billDemand_id)) {
                billDemandMap.put(billDemand_id, createBillDemandsObj(resultSet));
            }
            else {
                addBeneficiaryToBillDemand(billDemandMap.get(billDemand_id), resultSet);
            }
        }

        return new ArrayList<>(billDemandMap.values());
    }

    private BillDemand createBillDemandsObj(ResultSet rs) throws SQLException, DataAccessException {
        Beneficiary beneficiary = createBeneficiaryObjFromResultSet(rs);
        List<Beneficiary> beneficiaries = new ArrayList<>();
        beneficiaries.add(beneficiary);

        String billDemand_id = rs.getString("billDemand_id");
        String billDemand_tenantId = rs.getString("billDemand_tenantId");
        String billDemand_billNumber = rs.getString("billDemand_billNumber");
        Long billDemand_billDate = rs.getLong("billDemand_billDate");
        BigDecimal billDemand_netAmount = rs.getBigDecimal("billDemand_netAmount");
        BigDecimal billDemand_grossAmount = rs.getBigDecimal("billDemand_grossAmount");
        String billDemand_headOfAccount = rs.getString("billDemand_headOfAccount");
        String billDemand_ifmsSanctionNumber = rs.getString("billDemand_ifmsSanctionNumber");
        String billDemand_purpose = rs.getString("billDemand_purpose");
        String billDemand_createdBy = rs.getString("billDemand_createdBy");
        String billDemand_lastModifiedBy = rs.getString("billDemand_lastModifiedBy");
        Long billDemand_createdTime = rs.getLong("billDemand_createdTime");
        Long billDemand_lastModifiedTime = rs.getLong("billDemand_lastModifiedTime");

        AuditDetails beneficiaryAuditDetails = AuditDetails.builder().createdBy(billDemand_createdBy).createdTime(billDemand_createdTime)
                .lastModifiedBy(billDemand_lastModifiedBy).lastModifiedTime(billDemand_lastModifiedTime)
                .build();

        BillDemand billDemand = BillDemand.builder()
                .id(billDemand_id)
                .tenantId(billDemand_tenantId)
                .billNumber(billDemand_billNumber)
                .billDate(billDemand_billDate)
                .netAmount(billDemand_netAmount)
                .grossAmount(billDemand_grossAmount)
                .headOfAccount(billDemand_headOfAccount)
                .ifmsSanctionNumber(billDemand_ifmsSanctionNumber)
                .purpose(billDemand_purpose)
                .beneficiaries(beneficiaries)
                .auditDetails(beneficiaryAuditDetails)
                .build();

        return billDemand;
    }

    private Beneficiary createBeneficiaryObjFromResultSet(ResultSet rs) throws SQLException, DataAccessException  {
        String billDemandBeneficiary_Id = rs.getString("billDemandBeneficiary_Id");
        String billDemandBeneficiary_billNumber = rs.getString("billDemandBeneficiary_billNumber");
        String billDemandBeneficiary_name = rs.getString("billDemandBeneficiary_name");
        String billDemandBeneficiary_accountNumber = rs.getString("billDemandBeneficiary_accountNumber");
        String billDemandBeneficiary_ifscCode = rs.getString("billDemandBeneficiary_ifscCode");
        String billDemandBeneficiary_mobileNumber = rs.getString("billDemandBeneficiary_mobileNumber");
        String billDemandBeneficiary_address = rs.getString("billDemandBeneficiary_address");
        String billDemandBeneficiary_accountType = rs.getString("billDemandBeneficiary_accountType");
        BigDecimal billDemandBeneficiary_amount = rs.getBigDecimal("billDemandBeneficiary_amount");
        String billDemandBeneficiary_purpose = rs.getString("billDemandBeneficiary_purpose");
        String billDemandBeneficiary_status = rs.getString("billDemandBeneficiary_status");
        String billDemandBeneficiary_createdBy = rs.getString("billDemandBeneficiary_createdBy");
        String billDemandBeneficiary_lastModifiedBy = rs.getString("billDemandBeneficiary_lastModifiedBy");
        Long billDemandBeneficiary_createdTime = rs.getLong("billDemandBeneficiary_createdTime");
        Long billDemandBeneficiary_lastModifiedTime = rs.getLong("billDemandBeneficiary_lastModifiedTime");

        AuditDetails beneficiaryAuditDetails = AuditDetails.builder().createdBy(billDemandBeneficiary_createdBy).createdTime(billDemandBeneficiary_createdTime)
                .lastModifiedBy(billDemandBeneficiary_lastModifiedBy).lastModifiedTime(billDemandBeneficiary_lastModifiedTime)
                .build();

        Beneficiary beneficiary = Beneficiary.builder()
                .id(billDemandBeneficiary_Id)
                .billNumber(billDemandBeneficiary_billNumber)
                .name(billDemandBeneficiary_name)
                .accountNumber(billDemandBeneficiary_accountNumber)
                .ifscCode(billDemandBeneficiary_ifscCode)
                .mobileNumber(billDemandBeneficiary_mobileNumber)
                .address(billDemandBeneficiary_address)
                .accountType(billDemandBeneficiary_accountType)
                .amount(billDemandBeneficiary_amount)
                .purpose(billDemandBeneficiary_purpose)
                .status(billDemandBeneficiary_status)
                .auditDetails(beneficiaryAuditDetails)
                .build();

        return beneficiary;

    }

    private void addBeneficiaryToBillDemand(BillDemand billDemand, ResultSet resultSet) throws SQLException {
        Beneficiary beneficiary = createBeneficiaryObjFromResultSet(resultSet);
        billDemand.addBeneficiaryItem(beneficiary);
    }

}
