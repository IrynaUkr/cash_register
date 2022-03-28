package cash.service;

import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;

public class ServiceReceiptProduct {
    private static final Logger logger = LogManager.getLogger(ServiceReceiptProduct.class);


    public static ReceiptProducts createReceiptProduct(Product product, Double amount) {
        ReceiptProducts receiptProducts = new ReceiptProducts();
        receiptProducts.setProductId(product.getId());
        receiptProducts.setAmount(amount);
        receiptProducts.setPrice(product.getPrice());
        receiptProducts.setCode(product.getCode());
        receiptProducts.setName(product.getName());
        return receiptProducts;
    }

    public static void setAmountSumReceipt(Receipt receipt) {
        logger.info("query: setAmountSumReceipt");
        if (receipt != null) {
            ArrayList<ReceiptProducts> receiptProducts = receipt.getReceiptProducts();
            double sum = 0.0;
            double amount = 0.0;
            for (ReceiptProducts rp : receiptProducts) {
                sum += rp.getPrice() * rp.getAmount();
                amount += rp.getAmount();
            }
            receipt.setSum(sum);
            receipt.setAmount(amount);
        } else {
            logger.error("receipt is null, cannot update amount of receipt`s sum");
            throw new IllegalArgumentException();
        }
    }
}

