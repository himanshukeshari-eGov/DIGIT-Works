package org.egov.digit.repository.rowmapper;

import lombok.extern.slf4j.Slf4j;
import org.egov.digit.web.models.BillDemand;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class BillDemandsRowMapper  implements ResultSetExtractor<List<BillDemand>> {
    @Override
    public List<BillDemand> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        return null;
    }
}
