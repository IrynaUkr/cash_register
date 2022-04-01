package cash.service;

import cash.db.dao.impl.PaymentDaoImpl;
import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationType;
import cash.entity.Payment;
import cash.entity.Receipt;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReportUtils {
    public static void getXReport(HttpServletRequest request, Date date) {
        System.out.println("getXReport");
        List<Receipt> receiptList = ReceiptImpl.getInstance().findReceiptByDate(date);
        List<Receipt> returns = getReceiptsByType(receiptList, OperationType.RETURN);
        System.out.println(returns);
        List<Receipt> sales = getReceiptsByType(receiptList, OperationType.SALE);
        System.out.println(sales);
        List<Payment> payments = PaymentDaoImpl.getInstance().findPaymentByDate(date);
        List<Payment> payIn = getPaymentsByType(payments, OperationType.SERVICE_CASH_INFLOW);
        System.out.println(payments);
        System.out.println(payIn);
        List<Payment> payOut = getPaymentsByType(payments, OperationType.SERVICE_CASH_OUTFLOW);
        System.out.println(payOut);
        double returnSum = getReceiptsSum(returns);
        System.out.println(returnSum);
        double salesSum = getReceiptsSum(sales);
        System.out.println(salesSum);
        double payInSum = getPaymentsSum(payIn);
        System.out.println(payInSum);
        double payOutSum = getPaymentsSum(payOut);
        System.out.println(payOut);
        double resultSum = getResultSum(returnSum, salesSum, payInSum, payOutSum);
        System.out.println(resultSum + "resultSum");
        setXReport(request, returns, sales, payIn, payOut, returnSum, salesSum, payInSum, payOutSum, resultSum);
    }

   public static double getResultSum(double returnSum, double salesSum, double payInSum, double payOutSum) {
        return  salesSum - returnSum + payInSum - payOutSum;

    }

    public static void setXReport(HttpServletRequest request, List<Receipt> returns, List<Receipt> sales, List<Payment> payIn, List<Payment> payOut, double returnSum, double salesSum, double payInSum, double payOutSum, double resultSum) {
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
        return receiptList.stream()
                .filter(r -> r.getType() == type)
                .collect(Collectors.toList());
    }

    public static List<Payment> getPaymentsByType(List<Payment> payments, OperationType type) {
        return payments.stream().filter(p -> p.getType() == type).toList();
    }

    public static double getReceiptsSum(List<Receipt> receipts) {
        return receipts
                .stream()
                .mapToDouble(Receipt::getSum)
                .sum();
    }

    public static double getPaymentsSum(List<Payment> payments) {
        return payments
                .stream()
                .mapToDouble(Payment::getValue)
                .sum();
    }
}
