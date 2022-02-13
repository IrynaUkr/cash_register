package cash.entity;

public enum OperationType {
    SALE,                        // this operation created  with Receipt - debet
    RETURN,                      // this operation created  with Receipt  -kredit
    SERVICE_CASH_INFLOW,         // this operation created  with Receipt - debet
    SERVICE_CASH_OUTFLOW         // this operation created  with Receipt  -kredit
}
