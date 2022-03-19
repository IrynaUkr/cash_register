package cash.entity;

import java.util.Objects;

public class ReceiptProducts extends Entity {
    private Integer productId;
    private double amount;
    private double price;
    private String name;
    private String code;
    private String uom;

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public ReceiptProducts(Integer productId, double amount, double price) {
        this.productId = productId;
        this.amount = amount;
        this.price = price;
    }

    public ReceiptProducts(Integer productId, double amount, double price, String name, String code) {
        this.productId = productId;
        this.amount = amount;
        this.price = price;
        this.name = name;
        this.code = code;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReceiptProducts that)) return false;
        return Double.compare(that.getAmount(), getAmount()) == 0 && Objects.equals(getProductId(), that.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getAmount(), getPrice());
    }

    @Override
    public String toString() {
        return "ReceiptProducts{" +
                "productId=" + productId +
                ", amount=" + amount +
                '}';
    }
}


