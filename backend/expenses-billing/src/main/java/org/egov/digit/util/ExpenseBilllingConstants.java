package org.egov.digit.util;

public class ExpenseBilllingConstants {

    //localization config
    public static final String NOTIFICATION_ENG_LOCALE_CODE = "en_IN";
    public static final String NOTIFICATION_MODULE_CODE = "egov-expenditure";
    public static final String CONTRACTS_LOCALIZATION_CODES_JSONPATH = "$.messages.*.code";
    public static final String CONTRACTS_LOCALIZATION_MSGS_JSONPATH = "$.messages.*.message";
    public static final String SUCCESS_MSG_LOCALIZATION_CODE = "sms.expenditure.ws.payment.success";

    public static final String SUCCESS_MSG= "Dear $individualName, Payment of Rs. $amount has been made into your registered bank account for work performed under " +
            "Mukta scheme. Thank you. \n\nEGOVS";

}
