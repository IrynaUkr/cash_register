package cash.service;

import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.assertEquals;


public class ServiceReceiptProductTest {
    Receipt receipt = new Receipt();


    @Test(expected = IllegalArgumentException.class)
    public void setAmountSumReceiptTestNullInput() {
        ServiceReceiptProduct.setAmountSumReceipt(null);
    }

    @Before
    public void setReceiptProducts() {
        ArrayList<ReceiptProducts> rp = new ArrayList<>();
        rp.add(new ReceiptProducts(1, 10.0, 10.00));
        rp.add(new ReceiptProducts(2, 10.0, 20.00));
        rp.add(new ReceiptProducts(3, 10.0, 30.00));
        receipt.setReceiptProducts(rp);
    }

    @Test
    public void setAmountSumReceiptTest_is_Amount_Correct() {
        ServiceReceiptProduct.setAmountSumReceipt(receipt);
        double expected = 30.0;
        double actual = receipt.getAmount();
        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void setAmountSumReceiptTest_is_Sum_Correct() {
        ServiceReceiptProduct.setAmountSumReceipt(receipt);
        double expected = 600.0;
        double actual = receipt.getSum();
        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void createReceiptProductTest_is_ok() {
        Product apple = new Product(1, "11", "apple", 20.0, 1000.0, "kg", "fruit");
        ReceiptProducts expected = new ReceiptProducts(apple.getId(), 10);
        ReceiptProducts actual = ServiceReceiptProduct.createReceiptProduct(apple, 10.0);
        assertEquals(expected, actual);

    }

}
