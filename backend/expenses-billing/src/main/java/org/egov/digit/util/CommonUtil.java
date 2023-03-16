package org.egov.digit.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class CommonUtil {
    static ConcurrentHashMap<String,Integer> SKILL_AMOUNT_MAPPING_REGISTRY = new ConcurrentHashMap<>();

    static {
        SKILL_AMOUNT_MAPPING_REGISTRY.put("UNSKILLED.MALE_MULIA",150);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("UNSKILLED.FEMALE_MULIA",150);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("UNSKILLED.SCAVENGER",150);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("UNSKILLED.CHAIN_MAN", 150);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("SEMI_SKILLED.MALE_MULIA", 170);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("SEMI_SKILLED.FEMALE_MULIA", 170);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("SEMI_SKILLED.SCAVENGER", 170);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("SEMI_SKILLED.SANGI_MULIA", 170);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("SEMI_SKILLED.HAMMER_MAN", 170);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("SEMI_SKILLED.CLEANER", 170);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("HIGHLY_SKILLED.PLUMBER", 205);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("HIGHLY_SKILLED.MASON", 205);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("HIGHLY_SKILLED.PAINTER", 205);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("HIGHLY_SKILLED.FITTER",205);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("OTHERS.BORING_MISTRY", 205);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("OTHERS.WINCH_OPERATOR", 205);
        SKILL_AMOUNT_MAPPING_REGISTRY.put("OTHERS.HELPER_WELL_SINKER", 190);
    }

    @Autowired
    private ObjectMapper mapper;

    public List<Object> readJSONPathValue(Object jsonObj, String path){
        try {
            return JsonPath.read(jsonObj, path);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException("JSONPATH_ERROR", "Failed to parse mdms response");
        }
    }

    public Optional<String> findValue(Object object, String findValueOf){
        try {
            JsonNode node = mapper.readTree(mapper.writeValueAsString(object));
            if (node.findValue(findValueOf) != null  && StringUtils.isNotBlank(node.findValue(findValueOf).textValue())) {
                return Optional.of(node.findValue(findValueOf).textValue());

            }
        } catch (IOException e) {
            log.error("Failed to parse the object :"+e);
            throw new CustomException("PARSING_ERROR", "Failed to parse the object");
        }
        return Optional.empty();
    }

    public Integer getAmountForSkill(String skill){
        final Integer amount = SKILL_AMOUNT_MAPPING_REGISTRY.get(skill);
        if(amount== null)
            return 150;

        return amount;
    }

    public AuditDetails getAuditDetails(String by, AuditDetails auditDetails, Boolean isCreate) {
        Long time = System.currentTimeMillis();
        if (isCreate)
            return AuditDetails.builder().createdBy(by).lastModifiedBy(by).createdTime(time).lastModifiedTime(time).build();
        else
            return AuditDetails.builder().createdBy(auditDetails.getCreatedBy()).lastModifiedBy(by)
                    .createdTime(auditDetails.getCreatedTime()).lastModifiedTime(time).build();
    }
}
