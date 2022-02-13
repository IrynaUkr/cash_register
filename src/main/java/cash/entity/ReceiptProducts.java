package cash.entity;

public class ReceiptProducts extends Entity {
    private Integer productId;
    private double amount;

    public ReceiptProducts() {
    }

    public ReceiptProducts(Integer productId, double amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}


