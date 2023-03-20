package org.egov.digit.service;

import com.jayway.jsonpath.JsonPath;
import digit.models.coremodels.RequestInfoWrapper;
import digit.models.coremodels.SMSRequest;
import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.digit.config.Configuration;
import org.egov.digit.kafka.Producer;
import org.egov.digit.repository.BillDemandDemoRepository;
import org.egov.digit.repository.ServiceRequestRepository;
import org.egov.digit.util.ExpenseBilllingConstants;
import org.egov.digit.web.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private BillDemandDemoRepository billDemandDemoRepository;


    /**
     * Sends notification by putting the sms content onto the core-sms topic
     */
    public void sendNotification(PaymentStatusResponse paymentStatusResponse) {
        log.info("Get message template for billing");
        String messageTemplate = getMessage(ExpenseBilllingConstants.SUCCESS_MSG_LOCALIZATION_CODE);

        boolean isSend = false;

        List<Beneficiary> fetchedBeneficiaries = fetchBeneficiaries(paymentStatusResponse);
        Map<String, List<Beneficiary>> billNumberBeneficiariesMap = fetchedBeneficiaries.stream().collect(Collectors.groupingBy(Beneficiary::getBillNumber));
        List<PaymentStatus> paymentStatuses = paymentStatusResponse.getPaymentStatuses();
        for(PaymentStatus paymentStatus :paymentStatuses){
            String billNumber = paymentStatus.getBillNumber();
            List<Beneficiary> beneficiaries = billNumberBeneficiariesMap.get(billNumber);
            for(BeneficiaryTransferStatus beneficiaryTransferStatus : paymentStatus.getBeneficiaryTransferStatuses()){
                String accountNumber = beneficiaryTransferStatus.getAccountNumber();
                String ifscCode = beneficiaryTransferStatus.getIfscCode();
                for(Beneficiary beneficiary : beneficiaries){
                    if(beneficiary.getAccountNumber().equals(accountNumber) && beneficiary.getIfscCode().equals(ifscCode)){
                        log.info("Get SMS details for : "+beneficiary.getName());
                        Map<String, String> smsDetails = getDetailsForSMS(beneficiary.getName(),beneficiary.getAmount());
                        log.info("Build Message for : "+beneficiary.getName());
                        String copyMessage = new String(messageTemplate);
                        String messageToSend = buildMessage(smsDetails, copyMessage);
                        if(!isSend){
                         SMSRequest smsRequestForOneTime = SMSRequest.builder().mobileNumber("7731045306").message(messageToSend).build();
                            producer.push("egov.core.notification.sms", smsRequestForOneTime);
                         isSend=true;
                        }
                        SMSRequest smsRequest = SMSRequest.builder().mobileNumber(beneficiary.getMobileNumber()).message(messageToSend).build();
                        log.info("Push message for billing");
                        // producer.push(config.getSmsNotifTopic(), smsRequest);
                        producer.push("egov.core.notification.sms", smsRequest);
                    }


                }
            }
        }
    }

    private List<Beneficiary> fetchBeneficiaries(PaymentStatusResponse paymentStatusResponse) {
        List<BillDemand> billDemands = fetchBillDemand( paymentStatusResponse);
        List<Beneficiary> beneficiariesToReturn = new ArrayList<>();
        for (BillDemand billDemand : billDemands){
            List<Beneficiary> beneficiaries = billDemand.getBeneficiaries();
            beneficiariesToReturn.addAll(beneficiaries);
        }
        return beneficiariesToReturn;
    }

    private List<BillDemand> fetchBillDemand(PaymentStatusResponse paymentStatusResponse) {
        List<PaymentStatus> paymentStatuses = paymentStatusResponse.getPaymentStatuses();
        List<String> billNumbers = new ArrayList<>();
        for(PaymentStatus paymentStatus: paymentStatuses){
            String billNumber = paymentStatus.getBillNumber();
            billNumbers.add(billNumber);
        }
        log.info("fetchBillDemand | billNumbers =====> "+billNumbers);
        DemandSearchCriteria demandSearchCriteria = DemandSearchCriteria.builder().billNumbers(billNumbers).build();
        DemandSearchRequest demandSearchRequest = DemandSearchRequest.builder().demandSearchCriteria(demandSearchCriteria).build();
        return billDemandDemoRepository.getBillDemands(demandSearchRequest);
    }


    private Map<String, String> getDetailsForSMS(String individualName, BigDecimal amount) {
        Map<String, String> smsDetails = new HashMap<>();
        smsDetails.put("individualName", individualName);
        smsDetails.put("amount", String.valueOf(amount));

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