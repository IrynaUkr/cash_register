package cash.service;

import cash.db.dao.impl.PaymentDaoImpl;
import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationType;
import cash.entity.Payment;
import cash.entity.Receipt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReportUtils {
    private ReportUtils() {
    }
    private static final Logger logger = LogManager.getLogger(ReportUtils.class);

    private static ReportUtils instance = null;

    public static ReportUtils getInstance() {
        if (instance == null) {
            return new ReportUtils();
        }
        return instance;
    }
    public static void getXReport(HttpServletRequest request, Date date) {
        logger.info("query: getXReport");
        List<Receipt> receiptList = ReceiptImpl.getInstance().findReceiptByDate(date);
        List<Receipt> returns = getReceiptsByType(receiptList, OperationType.RETURN);
        List<Receipt> sales = getReceiptsByType(receiptList, OperationType.SALE);
        List<Payment> payments = PaymentDaoImpl.getInstance().findPaymentByDate(date);
        List<Payment> payIn = getPaymentsByType(payments, OperationType.SERVICE_CASH_INFLOW);
        List<Payment> payOut = getPaymentsByType(payments, OperationType.SERVICE_CASH_OUTFLOW);
        double returnSum = getReceiptsSum(returns);
        double salesSum = getReceiptsSum(sales);
        double payInSum = getPaymentsSum(payIn);
        double payOutSum = getPaymentsSum(payOut);
        double resultSum = getResultSum(returnSum, salesSum, payInSum, payOutSum);
        setXReport(request, returns, sales, payIn, payOut, returnSum, salesSum, payInSum, payOutSum, resultSum);
    }

    public static double getResultSum(double returnSum, double salesSum, double payInSum, double payOutSum) {
        return salesSum - returnSum + payInSum - payOutSum;

    }

    public static void setXReport(HttpServletRequest request, List<Receipt> returns, List<Receipt> sales, List<Payment> payIn, List<Payment> payOut, double returnSum, double salesSum, double payInSum, double payOutSum, double resultSum) {
        logger.info("query: setXReport");
        request.getSession().setAttribute("returnList", returns);
        request.getSession().setAttribute("saleList", sales);
        request.getSession().setAttribute("returnSum", returnSum);
        request.getSession().setAttribute("saleSum", salesSum);
        request.getSession().setAttribute("payIn", payIn);
        request.getSession().setAttribute("payOut", payOut);
        request.getSession().setAttribute("payInSum", payInSum);
        request.getSession().setAttribute("payOutSum", payOutSum);
        request.getSession().setAttribute("result", resultSum);
    }

    public static List<Receipt> getReceiptsByType(List<Receipt> receiptList, OperationType type) {
        logger.info("query: getReceiptsByType");
        return receiptList.stream()
                .filter(r -> r.getType() == type)
                .collect(Collectors.toList());
    }

    public static List<Payment> getPaymentsByType(List<Payment> payments, OperationType type) {
        logger.info("query: get Payments By Type");
        return payments.stream().filter(p -> p.getType() == type).toList();
    }

    public static double getReceiptsSum(List<Receipt> receipts) {
        logger.info("query: getReceiptsBySum");
        return receipts
                .stream()
                .mapToDouble(Receipt::getSum)
                .sum();
    }

    public static double getPaymentsSum(List<Payment> payments) {
        logger.info("query: getPaymentsBySum");
        return payments
                .stream()
                .mapToDouble(Payment::getValue)
                .sum();
    }
}
