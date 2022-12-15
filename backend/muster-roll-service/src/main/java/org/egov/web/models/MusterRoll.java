package org.egov.web.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import digit.models.coremodels.AuditDetails;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * MusterRoll
 */
@Validated
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2022-11-14T19:58:09.415+05:30")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusterRoll {
    @JsonProperty("id")
    private UUID id = null;

    @JsonProperty("tenantId")
    private String tenantId = null;

    @JsonProperty("musterRollNumber")
    private String musterRollNumber = null;

    @JsonProperty("registerId")
    private String registerId = null;

    @JsonProperty("status")
    private Status status = null;

    @JsonProperty("musterRollStatus")
    private String musterRollStatus = null;

    @JsonProperty("startDate")
    private Long startDate = null;

    @JsonProperty("endDate")
    private Long endDate = null;

    @JsonProperty("individualEntries")
    @Valid
    private List<IndividualEntry> individualEntries = null;

    @JsonProperty("auditDetails")
    private AuditDetails auditDetails = null;


    public MusterRoll addIndividualEntriesItem(IndividualEntry individualEntriesItem) {
        if (this.individualEntries == null) {
            this.individualEntries = new ArrayList<>();
        }
        this.individualEntries.add(individualEntriesItem);
        return this;
    }

}
