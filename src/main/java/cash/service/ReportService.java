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

public class ReportService {
    public static void getXReport(HttpServletRequest request, Date date) {
        List<Receipt> receiptList = new ReceiptImpl().findReceiptByDate(date);
        List<Receipt> returnList = receiptList.stream().filter(r -> r.getType() == OperationType.RETURN).collect(Collectors.toList());
        List<Receipt> saleList = receiptList.stream().filter(r -> r.getType() == OperationType.SALE).collect(Collectors.toList());
        List<Payment> payments = new PaymentDaoImpl().findPaymentByDate(date);
        List<Payment> payIn = payments.stream().filter(p -> p.getType() == OperationType.SERVICE_CASH_INFLOW).toList();
        List<Payment> payOut = payments.stream().filter(p -> p.getType() == OperationType.SERVICE_CASH_OUTFLOW).toList();
        double returnSum = returnList.stream().mapToDouble(Receipt::getSum).sum();
        double saleSum = saleList.stream().mapToDouble(Receipt::getSum).sum();
        double payInSum = payIn.stream().mapToDouble(Payment::getValue).sum();
        double payOutSum = payOut.stream().mapToDouble(Payment::getValue).sum();
        double result = saleSum - returnSum + payInSum - payOutSum;
        request.getSession().setAttribute("returnList", returnList);
        request.getSession().setAttribute("saleList", saleList);
        request.getSession().setAttribute("returnSum", returnSum);
        request.getSession().setAttribute("saleSum", saleSum);
        request.getSession().setAttribute("payIn", payIn);
        request.getSession().setAttribute("payOut", payOut);
        request.getSession().setAttribute("payInSum", payInSum);
        request.getSession().setAttribute("payOutSum", payOutSum);
        request.getSession().setAttribute("result", result);
    }
}
