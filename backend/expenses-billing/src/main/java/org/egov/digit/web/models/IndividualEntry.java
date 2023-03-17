package org.egov.digit.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * IndividualEntry
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T19:58:09.415+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndividualEntry {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("individualId")
    private String individualId = null;

    @JsonProperty("actualTotalAttendance")
    private BigDecimal actualTotalAttendance = null;

    @JsonProperty("modifiedTotalAttendance")
    private BigDecimal modifiedTotalAttendance = null;

    @JsonProperty("additionalDetails")
    private Object additionalDetails = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;

}
