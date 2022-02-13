package cash.db.dao;

import cash.entity.OperationType;
import cash.entity.Receipt;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface ReceiptDao extends BaseDao<Receipt>  {




    List<Receipt> findEntityByType(OperationType operationType);
    List<Receipt>findEntityByDate(Date date);

}
