package cash.entity;

import java.sql.Timestamp;

public class CashFlow {
    private Integer cashFlowID;
    private Double cashFlowValue;
    private Integer idUser;                  //author
    private OperationType operationType;     //SERVICE_CASH_INFLOW,   SERVICE_CASH_OUTFLOW
    private Timestamp cashDateTime;

}
