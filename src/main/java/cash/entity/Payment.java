package cash.entity;

import java.sql.Date;
import java.util.Objects;


public class Payment extends Entity {
    private Integer id;
    private Double value;
    private Date date;
    private Integer idUser;
    private OperationStatus status;
    private OperationType type;
    private String description;

    public Payment() {
    }

    public Payment(Double value, Integer idUser, OperationType type, String description) {
        this.value = value;
        this.idUser = idUser;
        this.type = type;
        this.description = description;
    }
    public Payment(Double value, Integer idUser, OperationType type) {
        this.value = value;
        this.idUser = idUser;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(getValue(), payment.getValue()) && Objects.equals(getIdUser(), payment.getIdUser()) && getType() == payment.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getIdUser(), getType());
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", value=" + value +
                ", date=" + date +
                ", idUser=" + idUser +
                ", status=" + status +
                ", type=" + type +
                ", description='" + description + '\'' +
                '}';
    }
}
