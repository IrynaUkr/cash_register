package cash.db.dao;


import cash.entity.OperationStatus;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public interface ReceiptDao extends BaseDao<Receipt> {
    boolean updateStatus(OperationStatus status, Receipt receipt);

    List<Receipt> findEntityByStatus(OperationStatus status);

    Receipt findReceiptByNumber(String number);

    List<Receipt> findReceiptByDate(Date date);

    ArrayList<ReceiptProducts> getListProductsByIdReceiptLANG(Integer idReceipt, int id_lang);

    boolean setFiscalStatusReceipt();

}
