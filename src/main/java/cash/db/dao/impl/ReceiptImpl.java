package cash.db.dao.impl;

import cash.db.dao.ReceiptDao;
import cash.db.manager.DBManager;
import cash.entity.*;
import cash.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static cash.db.ConstantQueryDB.*;


public class ReceiptImpl implements ReceiptDao {
    private static final Logger logger = LogManager.getLogger(ReceiptImpl.class);

    private ReceiptImpl() {
    }

    private static ReceiptImpl instance;

    public static ReceiptImpl getInstance() {
        if (instance == null) {
            return new ReceiptImpl();
        }
        return instance;
    }

    private int totalAmountRecords;

    public int getTotalAmountRecords() {
        return totalAmountRecords;
    }

    public List<Receipt> findAllReceiptWithRestrict(int offset, int noOfRecords) {
        logger.info("query: find all products with restrict amount of lines");
        List<Receipt> receipts = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_FROM_RECEIPT_LIMIT)) {
            int k = 0;
            pst.setInt(++k, offset);
            pst.setInt(++k, noOfRecords);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                receipts.add(extractReceipt(rs));
            }
            rs = pst.executeQuery(COUNT_RECEIPT);
            if (rs.next())
                this.totalAmountRecords = rs.getInt(1);
            rs.close();
        } catch (SQLException ex) {
            logger.error("receipts with restrict amount of lines were  not found", ex);
            throw new DBException("receipts with restrict amount of lines were  not found", ex);
        }
        return receipts;
    }

    public List<Receipt> viewAllReceiptsWithSorting(int offset, int recordsOnPage, String sortingType) {
        logger.info("query: find all receipts with restrict amount of lines");
        List<Receipt> receipts = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM receipt ORDER by ");
        queryBuilder.append(sortingType);
        queryBuilder.append(" LIMIT ?, ?");
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(queryBuilder.toString())) {
            int k = 0;
            pst.setInt(++k, offset);
            pst.setInt(++k, recordsOnPage);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                receipts.add(extractReceipt(rs));
            }
            rs = pst.executeQuery(COUNT_RECEIPT);
            if (rs.next())
                this.totalAmountRecords = rs.getInt(1);
            rs.close();
        } catch (SQLException ex) {
            logger.error("receipts with sorting  were  not found", ex);
            throw new DBException("receipts with sorting  were  not found", ex);
        }
        return receipts;
    }


    @Override
    public List<Receipt> findAll() {
        logger.info("query: find receipts");
        List<Receipt> receipts = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL_RECEIPT);
            while (rs.next()) {
                receipts.add(extractReceipt(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("receipts were  not found", ex);
            throw new DBException("receipts were  not found", ex);
        }
        return receipts;
    }

    private Receipt extractReceipt(ResultSet rs) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(rs.getInt("id_receipt"));
        receipt.setNumber(rs.getString("number"));
        receipt.setDate(rs.getDate("date"));
        receipt.setStatus(OperationStatus.valueOf(rs.getString("status")));
        receipt.setOperationType(OperationType.valueOf(rs.getString("type")));
        return receipt;
    }
    private Receipt extractReceiptWithTotal(ResultSet rs) throws SQLException {
        Receipt receipt = new Receipt();
        receipt.setId(rs.getInt("id_receipt"));
        receipt.setNumber(rs.getString("number"));
        receipt.setDate(rs.getDate("date"));
        receipt.setStatus(OperationStatus.valueOf(rs.getString("status")));
        receipt.setOperationType(OperationType.valueOf(rs.getString("type")));
        receipt.setSum(rs.getDouble("total"));
        return receipt;
    }

    @Override
    public Receipt findEntityById(Integer id) {
        logger.info("query: find receipt by id");
        Receipt receipt = new Receipt();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_RECEIPT_BY_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                receipt = extractReceipt(rs);
            }
            close(rs);
            close(pstmt);
        } catch (SQLException ex) {
            logger.error("receipt by id was not found", ex);
            throw new DBException("receipt by id was not found", ex);
        }
        return receipt;
    }


    @Override
    public boolean delete(Receipt receipt) {
        logger.info("query: delete receipt");
        if (receipt == null) {
            return false;
        }
        if (receipt.getStatus()==OperationStatus.FISCALISED){
            System.out.println("fiscalazed receipts could not be deleted");
            return false;
        }
        int id = receipt.getId();
        if (findEntityById(id) == null ) {
            return false;
        } else {
            int executeUpdate = 0;
            try (Connection con = DBManager.getInstance().getConnection();
                 PreparedStatement pstmt = con.prepareStatement(DELETE_RECEIPT_BY_ID)) {
                pstmt.setInt(1, id);
                executeUpdate = pstmt.executeUpdate();
            } catch (SQLException ex) {
                logger.error("receipt  were not deleted", ex);
                throw new DBException("receipt were not deleted", ex);
            }
            return executeUpdate > 0;
        }
    }
    public boolean deleteReceiptsByNumber(String... numbers) {
        boolean flag = false;
        for (String number : numbers) {
            flag = delete(findReceiptByNumber(number));
        }
        return flag;
    }

    @Override
    public boolean create(Receipt receipt) {
        return false;
    }


    //update  Status by Number
    @Override
    public boolean updateStatus(OperationStatus status, Receipt receipt) {
        logger.info("query: update status receipt");
        if (receipt == null) {
            return false;
        }
        if (findEntityById(receipt.getId()) != null) {
            int result = 0;
            try (Connection con = DBManager.getInstance().getConnection();
                 PreparedStatement pstmt = con.prepareStatement(SET_RECEIPT)) {
                pstmt.setString(1, String.valueOf(status));
                pstmt.setString(2, receipt.getNumber());
                result = pstmt.executeUpdate();
            } catch (SQLException ex) {
                logger.error(" receipt's status was not update", ex);
                throw new DBException(" receipt's status was not update", ex);
            }
            return result > 0;
        } else {
            return false;
        }
    }

    @Override
    public List<Receipt> findEntityByStatus(OperationStatus status) {
        logger.info("query: find receipts by status");
        List<Receipt> receipts = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_RECEIPT_BY_STATUS)) {
            pstmt.setString(1, String.valueOf(status));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                receipts.add(extractReceipt(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("receipts by status were  not found", ex);
            throw new DBException("receipts by status were not found", ex);
        }
        return receipts;
    }

    @Override
    public Receipt findReceiptByNumber(String number) {
        logger.info("query: find receipt by number");
        Receipt receipt = new Receipt();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_RECEIPT_BY_NUMBER)) {
            pstmt.setString(1, number);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                receipt = extractReceipt(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error(" receipt by number was not found", ex);
            throw new DBException("receipt by number was not found", ex);
        }
        return receipt;
    }

    @Override
    public List<Receipt> findReceiptByDate(Date date) {
        logger.info("query: find receipt by date");
        List<Receipt> receipts = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_RETURN_BY_DATE)) {
            pstmt.setString(1, String.valueOf(date));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                receipts.add(extractReceiptWithTotal(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error(" receipt by date was not found", ex);
            throw new DBException("receipt by date was not found", ex);
        }
        return receipts;
    }

    @Override
    public ArrayList<ReceiptProducts> getListProductsByIdReceiptLANG(Integer idReceipt, int id_lang) {
        logger.info("query: find product's list by id receipt with chosen language ");
        ArrayList<ReceiptProducts> products = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_JOIN_LANG)) {
            pstmt.setInt(1, idReceipt);
            pstmt.setInt(2, id_lang);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(extractProductLang(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error(" product's list by id receipt with chosen language was not found", ex);
            throw new DBException(" product's list by id receipt with chosen language was not found", ex);
        }
        return products;
    }

    @Override
    public boolean setFiscalStatusReceipt() {
        logger.info("query: set fiscal status to receipt ");
        int result = 0;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(RECEIPT_SET_FISCALISED)) {
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(" fiscal status to receipt was not set", ex);
            throw new DBException("fiscal status to receipt was not set", ex);
        }
        return result > 0;
    }

    private ReceiptProducts extractProductLang(ResultSet rs) throws SQLException {
        ReceiptProducts receiptProduct = new ReceiptProducts();
        receiptProduct.setCode(rs.getString("code"));
        receiptProduct.setName(rs.getString("name_tr"));
        receiptProduct.setPrice(rs.getDouble("price"));
        receiptProduct.setAmount(rs.getDouble("amount"));
        receiptProduct.setUom(rs.getString("uom"));
        return receiptProduct;
    }
}
