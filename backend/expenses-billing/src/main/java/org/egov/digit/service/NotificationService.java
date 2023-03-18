package org.egov.digit.service;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.config.Configuration;
import org.egov.digit.kafka.Producer;
import org.egov.digit.repository.ServiceRequestRepository;
import org.egov.digit.util.ExpenseBilllingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private Producer producer;

    @Autowired
    private ServiceRequestRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Configuration config;


    /**
     * Sends notification by putting the sms content onto the core-sms topic
     */
    public void sendNotification() {
        log.info("Get message template for billing");
        String message = getMessage(ExpenseBilllingConstants.SUCCESS_MSG_LOCALIZATION_CODE);

        //get sms details
        log.info("Get SMS details");
        Map<String, String> smsDetails = getDetailsForSMS();

        log.info("Build Message for billing");
        message = buildMessage(smsDetails, message);
        SMSRequest smsRequest = SMSRequest.builder().mobileNumber("7731045306").message(message).build();

        log.info("Push message for billing");
       // producer.push(config.getSmsNotifTopic(), smsRequest);
        producer.push("egov.core.notification.sms", smsRequest);
    }


    private Map<String, String> getDetailsForSMS() {
        Map<String, String> smsDetails = new HashMap<>();
        smsDetails.put("individualName", "CK");
        smsDetails.put("amount", "500");

        return smsDetails;
    }


    /**
     * Gets the message from localization
     *
     * @param msgCode
     * @return
     */
    private String getMessage(String msgCode) {
        String tenantId = "pg.citya";
        RequestInfo requestInfo = RequestInfo.builder().build();
        Map<String, Map<String, String>> localizedMessageMap = getLocalisedMessages(requestInfo, tenantId,
                ExpenseBilllingConstants.NOTIFICATION_ENG_LOCALE_CODE, ExpenseBilllingConstants.NOTIFICATION_MODULE_CODE);

        log.info("Localized message map received");

        Map<String, String> tenantIdMsgCodeMapping = localizedMessageMap.get(ExpenseBilllingConstants.NOTIFICATION_ENG_LOCALE_CODE + "|" + tenantId);
        String message= tenantIdMsgCodeMapping.get(msgCode);

        if(message.isEmpty()){
            log.info("Message received from localization is empty. Using default message");
            return ExpenseBilllingConstants.SUCCESS_MSG;
        }

        log.info("Returning received message");
        return message;
    }

    /**
     * Builds msg based on the format
     *
     * @param message
     * @param userDetailsForSMS
     * @return
     */
    public String buildMessage(Map<String, String> userDetailsForSMS, String message) {
        message = message.replace("$individualName", userDetailsForSMS.get("individualName"));
        message = message.replace("$amount", userDetailsForSMS.get("amount"));
        return message;
    }

    /**
     * Creates a cache for localization that gets refreshed at every call.
     *
     * @param requestInfo
     * @param tenantId
     * @param locale
     * @param module
     * @return
     */
    public Map<String, Map<String, String>> getLocalisedMessages(RequestInfo requestInfo, String tenantId, String locale, String module) {
        Map<String, Map<String, String>> localizedMessageMap = new HashMap<>();
        Map<String, String> mapOfCodesAndMessages = new HashMap<>();
        StringBuilder uri = new StringBuilder();
        RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
        requestInfoWrapper.setRequestInfo(requestInfo);
        String rootTenantId = tenantId.split("\\.")[0];
        uri.append(config.getLocalizationHost()).append(config.getLocalizationContextPath())
                .append(config.getLocalizationSearchEndpoint()).append("?tenantId=" + rootTenantId)
                .append("&module=" + module).append("&locale=" + locale);
        List<String> codes = null;
        List<String> messages = null;
        Object result = null;
        try {
            result = repository.fetchResult(uri, requestInfoWrapper);
            log.info("Result fetched for billing");
            codes = JsonPath.read(result, ExpenseBilllingConstants.CONTRACTS_LOCALIZATION_CODES_JSONPATH);
            messages = JsonPath.read(result, ExpenseBilllingConstants.CONTRACTS_LOCALIZATION_MSGS_JSONPATH);
        } catch (Exception e) {
            log.error("Exception while fetching from localization: " + e);
            throw e;
        }

        if (result != null) {
            for (int i = 0; i < codes.size(); i++) {
                mapOfCodesAndMessages.put(codes.get(i), messages.get(i));
            }
            localizedMessageMap.put(locale + "|" + tenantId, mapOfCodesAndMessages);
        }

        log.info("Localized message map prepared");
        return localizedMessageMap;
    }

}