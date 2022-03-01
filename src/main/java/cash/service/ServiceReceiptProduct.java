package cash.service;

import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;

import java.util.ArrayList;

public class ServiceReceiptProduct {

    public static ReceiptProducts createReceiptProduct( Product product, Double amount) {
        ReceiptProducts receiptProducts = new ReceiptProducts();
        receiptProducts.setProductId(product.getId());
        receiptProducts.setAmount(amount);
        receiptProducts.setPrice(product.getPrice());
        receiptProducts.setCode(product.getCode());
        receiptProducts.setName(product.getName());
        return receiptProducts;
    }

    public static void updateAmountSumReceipt(Receipt receipt) {
        ArrayList<ReceiptProducts> receiptProducts = receipt.getReceiptProducts();
        double sum = 0.0;
        double amount = 0.0;
        for (ReceiptProducts rp : receiptProducts) {
            sum += rp.getPrice() * rp.getAmount();
            amount += rp.getAmount();
        }
        receipt.setSum(sum);
        receipt.setAmount(amount);
    }
}

