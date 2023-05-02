package org.egov.digit.expense.util;

import java.util.UUID;

import org.egov.digit.expense.config.Configuration;
import org.egov.digit.expense.config.Constants;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.BillRequest;
import org.egov.digit.expense.web.models.BillSearchRequest;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.Pagination;
import org.egov.digit.expense.web.models.Payment;
import org.egov.digit.expense.web.models.PaymentBill;
import org.egov.digit.expense.web.models.PaymentBillDetail;
import org.egov.digit.expense.web.models.PaymentLineItem;
import org.egov.digit.expense.web.models.PaymentRequest;
import org.egov.digit.expense.web.models.enums.PaymentStatus;
import org.egov.digit.expense.web.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import digit.models.coremodels.AuditDetails;

@Component
public class EnrichmentUtil {

    @Autowired
    private Configuration config;

    @Autowired
    private IdgenUtil idgenUtil;

    public BillRequest encrichBillForCreate(BillRequest billRequest) {

        Bill bill = billRequest.getBill();
        String createdBy = billRequest.getRequestInfo().getUserInfo().getUuid();
		AuditDetails audit = getAuditDetails(createdBy, billRequest.getBill().getAuditDetails(), true);
		String billNumberIdFormatName = bill.getBusinessService().concat(Constants.BILL_ID_FORMAT_SUFFIX);
		String billNumber = idgenUtil
				.getIdList(billRequest.getRequestInfo(), bill.getTenantId().split("\\.")[0], billNumberIdFormatName, null, 1).get(0);

	    bill.setId(UUID.randomUUID().toString());
        bill.setAuditDetails(audit);
        bill.setBillNumber(billNumber);

        bill.getPayer().setId(UUID.randomUUID().toString());
        bill.getPayer().setAuditDetails(audit);
        bill.getPayer().setParentId(bill.getId());

        for (BillDetail billDetail : bill.getBillDetails()) {

            billDetail.setId(UUID.randomUUID().toString());
            billDetail.setBillId(bill.getId());
            billDetail.setAuditDetails(audit);
            billDetail.setStatus(Status.ACTIVE);
            
            billDetail.getPayee().setId(UUID.randomUUID().toString());
            billDetail.getPayee().setParentId(billDetail.getBillId());
            billDetail.getPayee().setAuditDetails(audit);

            for (LineItem lineItem : billDetail.getLineItems()) {
                lineItem.setId(UUID.randomUUID().toString());
                lineItem.setAuditDetails(audit);
                lineItem.setBillDetailId(billDetail.getId());
                lineItem.setStatus(Status.ACTIVE);
            }

            for (LineItem payablelineItem : billDetail.getPayableLineItems()) {
                payablelineItem.setId(UUID.randomUUID().toString());
                payablelineItem.setAuditDetails(audit);
                payablelineItem.setBillDetailId(billDetail.getId());
                payablelineItem.setStatus(Status.ACTIVE);

            }
        }
		return billRequest;
    }

	public BillRequest encrichBillWithUuidAndAuditForUpdate(BillRequest billRequest) {

        Bill bill = billRequest.getBill();
        String createdBy = billRequest.getRequestInfo().getUserInfo().getUuid();
        AuditDetails updateAudit = getAuditDetails(createdBy, billRequest.getBill().getAuditDetails(), false);
        AuditDetails createAudit = getAuditDetails(createdBy, billRequest.getBill().getAuditDetails(), true);

        bill.setAuditDetails(updateAudit);

        for (BillDetail billDetail : bill.getBillDetails()) {

            /*
             * Enrich new bill detail
             */
            if (null == billDetail.getId()) {

                billDetail.setId(UUID.randomUUID().toString());
                billDetail.setAuditDetails(createAudit);

                for (LineItem lineItem : billDetail.getLineItems()) {
                    lineItem.setId(UUID.randomUUID().toString());
                    lineItem.setAuditDetails(createAudit);
                }

                for (LineItem payablelineItem : billDetail.getPayableLineItems()) {
                    payablelineItem.setId(UUID.randomUUID().toString());
                    payablelineItem.setAuditDetails(createAudit);
                }
            }
            /*
             * Enrich update of bill detail
             */
            else {

                billDetail.setAuditDetails(updateAudit);

                for (LineItem lineItem : billDetail.getLineItems()) {

                    if (null == lineItem.getId()) { /* new line item */

                        lineItem.setId(UUID.randomUUID().toString());
                        lineItem.setAuditDetails(createAudit);
                    } else { /* updating line item */
                        lineItem.setAuditDetails(updateAudit);
                    }
                }

                for (LineItem payablelineItem : billDetail.getPayableLineItems()) {

                    if (null == payablelineItem.getId()) { /* new payable line item */
                        payablelineItem.setId(UUID.randomUUID().toString());
                        payablelineItem.setAuditDetails(createAudit);
                    } else /* updating payable line item */
                        payablelineItem.setAuditDetails(updateAudit);
                }
            }
        }
        return billRequest;
    }


    public void enrichSearchBillRequest(BillSearchRequest billSearchRequest) {

        Pagination pagination = getPagination(billSearchRequest);

        if (pagination.getLimit() == null)
            pagination.setLimit(config.getDefaultLimit());

        if (pagination.getOffSet() == null)
            pagination.setOffSet(config.getDefaultOffset());

        if (pagination.getLimit() != null && pagination.getLimit().compareTo(config.getMaxSearchLimit()) > 0)
            pagination.setLimit(config.getMaxSearchLimit());
    }

    private Pagination getPagination(BillSearchRequest billSearchRequest) {
        Pagination pagination = billSearchRequest.getPagination();
        if (pagination == null) {
            pagination = Pagination.builder().build();
            billSearchRequest.setPagination(pagination);
        }
        return pagination;
    }

    public PaymentRequest encrichCreatePayment(PaymentRequest paymentRequest) {

        Payment payment = paymentRequest.getPayment();
        String createdBy = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        payment.setId(UUID.randomUUID().toString());
        /*
         * TODO needs to be removed when jit integration is implemented
         */
        PaymentStatus defaultStatus = PaymentStatus.fromValue(config.getDefaultPaymentStatus());
        payment.setStatus(defaultStatus);
        
		for (PaymentBill paymentBill : payment.getBills()) {

			paymentBill.setId(UUID.randomUUID().toString());
			paymentBill.setStatus(defaultStatus);
			
			for (PaymentBillDetail billDetail : paymentBill.getBillDetails()) {

				billDetail.setId(UUID.randomUUID().toString());
				billDetail.setStatus(defaultStatus);
				
				for (PaymentLineItem lineItem : billDetail.getPayableLineItems()) {
					
					lineItem.setId(UUID.randomUUID().toString());
					lineItem.setStatus(defaultStatus);				
				}
			}
		}
        payment.setAuditDetails(getAuditDetails(createdBy, paymentRequest.getPayment().getAuditDetails(), true));
        return paymentRequest;
    }

    public PaymentRequest encrichUpdatePayment(PaymentRequest paymentRequest) {

        Payment payment = paymentRequest.getPayment();
        String createdBy = paymentRequest.getRequestInfo().getUserInfo().getUuid();
        payment.setAuditDetails(getAuditDetails(createdBy, paymentRequest.getPayment().getAuditDetails(), false));
        return paymentRequest;
    }


    /**
     * Method to return auditDetails for create/update flows
     *
     * @param by
     * @param isCreate
     * @return AuditDetails
     */
    public AuditDetails getAuditDetails(String by, AuditDetails auditDetails, Boolean isCreate) {

        Long time = System.currentTimeMillis();

        if (isCreate)
            return AuditDetails.builder()
                    .createdBy(by)
                    .createdTime(time)
                    .lastModifiedBy(by)
                    .lastModifiedTime(time)
                    .build();
        else
            return AuditDetails.builder()
                    .createdBy(auditDetails.getCreatedBy())
                    .createdTime(auditDetails.getCreatedTime())
                    .lastModifiedBy(by)
                    .lastModifiedTime(time)
                    .build();
    }
}
