package cash.service;

import cash.db.dao.impl.PaymentDaoImpl;
import cash.db.dao.impl.ReceiptImpl;
import cash.db.manager.DBManager;
import cash.entity.OperationType;
import cash.entity.Payment;
import cash.entity.Receipt;
import cash.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {
    public static void getXreport(HttpServletRequest request) {
        Date date = Date.valueOf(request.getParameter("date"));

        List<Receipt> receiptList = new ReceiptImpl().findReceiptByDate(date);
        List<Receipt> returnList = receiptList.stream().filter(r -> r.getType() == OperationType.RETURN).collect(Collectors.toList());
        List<Receipt> saleList = receiptList.stream().filter(r -> r.getType() == OperationType.SALE).collect(Collectors.toList());

        List<Payment> payments = new PaymentDaoImpl().findPaymentByDate(date);
        System.out.println(payments);
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


    public static boolean setFiscalStatusReceipt() {
        PreparedStatement pstmt = null;
        Connection con = null;
        int result = 0;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("UPDATE receipt SET status ='FISCALISED' WHERE receipt.status= 'CLOSED'");
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }
    public  static boolean setFiscalStatusPayment() {
        PreparedStatement pstmt = null;
        Connection con = null;
        int result = 0;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("UPDATE payment SET status ='FISCALISED' WHERE status= 'CLOSED'");
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }
}
