package cash.service;

import cash.entity.OperationStatus;
import cash.entity.OperationType;
import cash.entity.Payment;
import cash.entity.Receipt;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static cash.service.ReportUtils.setXReport;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ReportUtilsTest {
    List<Receipt> receipts = new ArrayList<>();
    List<Payment> payments = new ArrayList<>();

    @Before
    public void createReceiptsList() {
        receipts.add(new Receipt("A1", 4, OperationStatus.CLOSED, OperationType.SALE, 100.0));
        receipts.add(new Receipt("A2", 4, OperationStatus.CLOSED, OperationType.SALE, 200.0));
        receipts.add(new Receipt("A3", 4, OperationStatus.CLOSED, OperationType.SALE, 300.0));
        receipts.add(new Receipt("B1", 4, OperationStatus.CLOSED, OperationType.RETURN, 100.0));
        receipts.add(new Receipt("B2", 4, OperationStatus.CLOSED, OperationType.RETURN, 100.0));
    }

    @Before
    public void createPaymentList() {
        payments.add(new Payment(1000.0, 2, OperationType.SERVICE_CASH_INFLOW));
        payments.add(new Payment(2000.0, 2, OperationType.SERVICE_CASH_INFLOW));
        payments.add(new Payment(3000.0, 2, OperationType.SERVICE_CASH_INFLOW));
        payments.add(new Payment(500.0, 2, OperationType.SERVICE_CASH_OUTFLOW));
        payments.add(new Payment(400.0, 2, OperationType.SERVICE_CASH_OUTFLOW));
    }


    @Test
    public void getReceiptByTypeSaleTest() {
        List<Receipt> actual = ReportUtils.getReceiptsByType(receipts, OperationType.SALE);
        List<Receipt> expected = getExpectedSaleList();
        assertEquals(expected, actual);
    }

    @Test
    public void getReceiptByTypeReturnTest() {
        List<Receipt> actual = ReportUtils.getReceiptsByType(receipts, OperationType.RETURN);
        List<Receipt> expected = getExpectedReturnList();
        assertEquals(expected, actual);
    }

    @Test
    public void getReceiptsSumTest() {
        Double actual = ReportUtils.getReceiptsSum(receipts);
        Double expected = 800.0;
        assertEquals(expected, actual);
    }

    @Test
    public void getPaymentsByTypeInflowTest() {
        List<Payment> actual = ReportUtils.getPaymentsByType(payments, OperationType.SERVICE_CASH_INFLOW);
        List<Payment> expected = getPaymentsInflow();
        assertEquals(expected, actual);
    }

    @Test
    public void getPaymentsByTypeOutFlowTest() {
        List<Payment> actual = ReportUtils.getPaymentsByType(payments, OperationType.SERVICE_CASH_OUTFLOW);
        List<Payment> expected = getPaymentsOutFlow();
        assertEquals(expected, actual);
    }

    @Test
    public void getPaymentsSumTest() {
        Double expected = 6900.0;
        Double actual = ReportUtils.getPaymentsSum(payments);
        assertEquals(expected, actual);
    }

    private List<Receipt> getExpectedSaleList() {
        List<Receipt> expected = new ArrayList<>();
        expected.add(new Receipt("A1", 4, OperationStatus.CLOSED, OperationType.SALE, 100.0));
        expected.add(new Receipt("A2", 4, OperationStatus.CLOSED, OperationType.SALE, 200.0));
        expected.add(new Receipt("A3", 4, OperationStatus.CLOSED, OperationType.SALE, 300.0));
        return expected;
    }

    private List<Receipt> getExpectedReturnList() {
        List<Receipt> expected = new ArrayList<>();
        expected.add(new Receipt("B1", 4, OperationStatus.CLOSED, OperationType.RETURN, 100.0));
        expected.add(new Receipt("B2", 4, OperationStatus.CLOSED, OperationType.RETURN, 100.0));
        return expected;
    }

    private List<Payment> getPaymentsInflow() {
        List<Payment> expected = new ArrayList();
        expected.add(new Payment(1000.0, 2, OperationType.SERVICE_CASH_INFLOW));
        expected.add(new Payment(2000.0, 2, OperationType.SERVICE_CASH_INFLOW));
        expected.add(new Payment(3000.0, 2, OperationType.SERVICE_CASH_INFLOW));
        return expected;
    }

    private List<Payment> getPaymentsOutFlow() {
        List<Payment> expected = new ArrayList();
        expected.add(new Payment(500.0, 2, OperationType.SERVICE_CASH_OUTFLOW));
        expected.add(new Payment(400.0, 2, OperationType.SERVICE_CASH_OUTFLOW));
        return expected;
    }

    @Test
    public void getResultSumTest() {
        Double actual = ReportUtils.getResultSum(
                ReportUtils.getReceiptsSum(ReportUtils.getReceiptsByType(receipts,OperationType.SALE)),
                ReportUtils.getReceiptsSum(ReportUtils.getReceiptsByType(receipts,OperationType.RETURN)),
                ReportUtils.getPaymentsSum(ReportUtils.getPaymentsByType(payments, OperationType.SERVICE_CASH_INFLOW)),
                ReportUtils.getPaymentsSum(ReportUtils.getPaymentsByType(payments, OperationType.SERVICE_CASH_OUTFLOW)));
        Double expected =4700.0;
        assertEquals(expected, actual);


    }

    @Test
    public void setXReportTest() {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpSession testSession = mock(HttpSession.class);
        when(testRequest.getSession()).thenReturn(testSession);
        setXReport(testRequest, receipts, receipts, payments, payments, 5, 5, 6, 7, 8);
        verify(testRequest, times(9)).getSession();
    }

}
