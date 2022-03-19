package cash.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Receipt extends Entity {
    private Integer id;
    private String number;
    private Double sum;
    private Double amount;
    private Date date;
    private Integer idUser;                     //author
    private OperationStatus status;
    private OperationType operationType;      //sale or refund of goods
    private ArrayList<ReceiptProducts> receiptProducts;

    public Receipt() {
    }

    public Receipt(String number, Integer idUser, OperationStatus status, OperationType operationType, ArrayList<ReceiptProducts> receiptProducts) {
        this.number = number;
        this.idUser = idUser;
        this.status = status;
        this.operationType = operationType;
        this.receiptProducts = receiptProducts;
    }

    public Receipt(String number, Integer idUser, OperationStatus status, OperationType operationType) {
        this.number = number;
        this.idUser = idUser;
        this.status = status;
        this.operationType = operationType;
    }

    public Receipt(String number, Date date, Integer idUser, OperationStatus status, OperationType operationType) {
        this.number = number;
        this.date = date;
        this.idUser = idUser;
        this.status = status;
        this.operationType = operationType;
    }
    public Receipt(String number, Integer idUser, OperationStatus status, OperationType operationType, Double sum) {
        this.number = number;
        this.idUser = idUser;
        this.status = status;
        this.operationType = operationType;
        this.sum = sum;
    }

    public ArrayList<ReceiptProducts> getReceiptProducts() {
        return receiptProducts;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setReceiptProducts(ArrayList<ReceiptProducts> receiptProducts) {
        this.receiptProducts = receiptProducts;
    }

    public Integer getId() {
        return id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public OperationType getType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receipt)) return false;
        Receipt receipt = (Receipt) o;
        return getId() == receipt.getId() && Objects.equals(getNumber(), receipt.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber());
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", date=" + date +
                ", status=" + status +
                ", operationType=" + operationType +
                '}';
    }
}
