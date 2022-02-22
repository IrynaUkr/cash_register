package cash.entity;

import java.util.Objects;

public class Product extends Entity {

    private Integer productId;
    private String code;
    private String name;
    private String description;
    private Double price;
    private Double amount;
    private String uom; //units of measure

    public Product() {
    }

    public Product(String code, String name,  Double price, Double amount, String uom,String description) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.uom = uom;
    }

    public Product(String code,  Double price, Double amount, String uom) {
        this.code = code;
        this.price = price;
        this.amount = amount;
        this.uom = uom;
    }

    public Product(String code) {
        this.code = code;
    }

    public Integer getId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getCode(), product.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", uom='" + uom + '\'' +
                '}';
    }
}
