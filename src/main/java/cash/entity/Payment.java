package cash.entity;

import java.sql.Date;


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
