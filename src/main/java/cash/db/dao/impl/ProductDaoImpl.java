package cash.db.dao.impl;

import cash.db.dao.ProductDao;
import cash.db.manager.DBManager;
import cash.entity.Product;
import cash.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static cash.db.ConstantQueryDB.*;

public class ProductDaoImpl implements ProductDao {
    private static final Logger logger = LogManager.getLogger(ProductDaoImpl.class);

    private ProductDaoImpl() {
    }

    private static ProductDaoImpl instance = null;

    public static ProductDaoImpl getInstance() {
        if (instance == null) {
            return new ProductDaoImpl();
        }
        return instance;
    }

    private int totalAmountRecords;

    @Override
    public List<Product> findAllWithRestrict(int offset, int noOfRecords, int id_lang) {
        logger.info("query: find all products with restrict amount of lines");
        List<Product> products = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_FROM_PRODUCT_LIMIT)) {
            int k = 0;
            pst.setInt(++k, id_lang);
            pst.setInt(++k, offset);
            pst.setInt(++k, noOfRecords);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                products.add(extractProductLang(rs));
            }
            rs = pst.executeQuery(COUNT_PRODUCT);
            if (rs.next())
                this.totalAmountRecords = rs.getInt(1);
            rs.close();
        } catch (SQLException ex) {
            logger.error("products with restrict amount of lines were  not found", ex);
            throw new DBException("products with restrict amount of lines were  not found", ex);
        }
        return products;
    }

    @Override
    public int getTotalAmountRecords() {
        return totalAmountRecords;
    }

    @Override
    public List<Product> viewAllWithSorting(int offset, int recordsOnPage, String sortingType, int id_lang) {
        logger.info("query: find all products with restrict amount of lines");
        List<Product> products = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=? ORDER by ");
        queryBuilder.append(sortingType);
        queryBuilder.append(" LIMIT ?, ?");
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(queryBuilder.toString())) {
            int k = 0;
            pst.setInt(++k, id_lang);
            pst.setInt(++k, offset);
            pst.setInt(++k, recordsOnPage);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                products.add(extractProductLang(rs));
            }
            rs = pst.executeQuery(COUNT_PRODUCT);
            if (rs.next())
                this.totalAmountRecords = rs.getInt(1);
            rs.close();
        } catch (SQLException ex) {
            logger.error("products with sorting amount of lines and language were  not found", ex);
            throw new DBException("products with sorting amount of lines and language were  not found", ex);
        }
        return products;
    }

    @Override
    public List<Product> findAll() {
        logger.info("query: find all products ");
        List<Product> products = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_FROM_PRODUCT);
            while (rs.next()) {
                products.add(extractProduct(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("products were  not found", ex);
            throw new DBException("products were  not found", ex);
        }
        return products;
    }

    @Override
    public List<Product> findAllByLang(int id_lang) {
        logger.info("query: find all products by chosen language ");
        List<Product> products = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_FROM_PRODUCT_BY_LANG)) {
            pstmt.setInt(1, id_lang);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(extractProductLang(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("products by chosen language were  not found", ex);
            throw new DBException("products  by chosen language were  not found", ex);
        }
        return products;
    }


    private Product extractProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("id_product"));
        product.setCode(rs.getString("code"));
        product.setPrice(rs.getDouble("price"));
        product.setAmount(rs.getDouble("amount"));
        product.setUom(rs.getString("uom"));
        return product;
    }

    private Product extractProductLang(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("id_product"));
        product.setCode(rs.getString("code"));
        product.setName(rs.getString("name_tr"));
        product.setDescription(rs.getString("description_tr"));
        product.setPrice(rs.getDouble("price"));
        product.setAmount(rs.getDouble("amount"));
        product.setUom(rs.getString("uom"));
        return product;
    }

    @Override
    public Product findEntityById(Integer id) {
        logger.info("query: find product by id ");
        Product product = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = extractProduct(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("product by id was  not found", ex);
            throw new DBException("products  by id was not found", ex);
        }
        return product;
    }

    @Override
    public boolean delete(Product product) {
        logger.info("query: delete product");
        if (product == null) {
            return false;
        }
        int id = product.getId();
        if (findEntityById(id) == null) {
            return false;
        } else {
            int executeUpdate = 0;
            try (Connection con = DBManager.getInstance().getConnection();
                 PreparedStatement pstmt = con.prepareStatement(DELETE_PRODUCT_BY_ID)) {
                pstmt.setInt(1, id);
                executeUpdate = pstmt.executeUpdate();
            } catch (SQLException ex) {
                logger.error("product was not deleted", ex);
                throw new DBException("product was not deleted", ex);
            }
            return executeUpdate > 0;
        }
    }

    @Override
    public boolean deleteProductByCode(String code) {
        logger.info("query: delete product by code");
        int executeUpdate = 0;
        if (findProductByCode(code) == null) {
            return false;
        } else {
            try (Connection con = DBManager.getInstance().getConnection();
                 PreparedStatement pstmt = con.prepareStatement(DELETE_PRODUCT_BY_CODE)) {
                pstmt.setString(1, code);
                executeUpdate = pstmt.executeUpdate();
            } catch (SQLException ex) {
                logger.error("product by code was not deleted", ex);
                throw new DBException("product by code was not deleted", ex);
            }
            return executeUpdate > 0;
        }
    }

    @Override
    public boolean create(Product product) {
        logger.info("query: create product by code");
        int result;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
            mapProduct(product, pst);
            result = pst.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setProductId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException ex) {
            logger.error("product  was not created", ex);
            throw new DBException("product  was not created", ex);
        }
        return result > 0;
    }

    @Override
    public boolean updateAmount(Product product, Double amount) {
        logger.info("query: update product's amount");
        if (product == null) {
            return false;
        }
        Double storeAmount = product.getAmount();
        int result = 0;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SET_AMOUNT_PRODUCT)) {
            pstmt.setDouble(1, storeAmount + amount);
            pstmt.setInt(2, product.getId());
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("product's amount  was not updated", ex);
            throw new DBException("product's amount  was not updated", ex);
        }
        return result > 0;
    }


    public void mapProduct(Product product, PreparedStatement pst) throws SQLException {
        pst.setString(1, product.getCode());
        pst.setDouble(2, product.getPrice());
        pst.setDouble(3, product.getAmount());
        pst.setString(4, product.getUom());
    }

    @Override
    public Product findProductByCode(String code) {
        logger.info("query: find  product by code");
        Product product = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_PRODUCT_BY_CODE)) {
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = extractProduct(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("product was not found", ex);
            throw new DBException("product was not found", ex);
        }
        return product;
    }

    @Override
    public Product findProductByCodeLang(String code, int id_lang) {
        logger.info("query: find  product by code with chosen language");
        Product product = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pst = con.prepareStatement(SELECT_PRODUCT_BY_CODE_LANG)) {
            pst.setInt(1, id_lang);
            pst.setString(2, code);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                product = extractProductLang(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("product by code with chosen language was not found", ex);
            throw new DBException("product by code with chosen language was not found", ex);
        }
        return product;
    }

    @Override
    public Product findProductByNameLang(String name, int id_lang) {
        logger.info("query: find  product by name with chosen language");
        Product product = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_PRODUCT_BY_NAME_LANG)) {
            pstmt.setInt(1, id_lang);
            pstmt.setString(2, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = extractProductLang(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("product by name with chosen language was not found", ex);
            throw new DBException("product by name with chosen language was not found", ex);
        }
        return product;
    }

    public boolean deleteProductsByCode(String... codes) {
        boolean flag = false;
        for (String code : codes) {
            flag = deleteProductByCode(code);
        }
        return flag;
    }

    @Override
    public int getId_lang(String lang) {
        logger.info("query: find id language");
        int id_lang = 0;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_ID_BY_NAME_LANG)) {
            pstmt.setString(1, lang);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id_lang = rs.getInt(1);
                System.out.println(id_lang + " id lang in get lang");
            }
            rs.close();
        } catch (SQLException ex) {
            logger.error("id language was not found", ex);
            throw new DBException("id language was not found", ex);
        }
        return id_lang;
    }
}
