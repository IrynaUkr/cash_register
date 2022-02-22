package cash.db.dao.impl;

import cash.db.dao.ProductDao;
import cash.db.manager.DBManager;
import cash.entity.Product;
import cash.exceptions.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private int totalAmountRecords;

    public static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product  WHERE id_product = ?";
    public static final String SELECT_PRODUCT_BY_CODE = "SELECT * FROM product  WHERE code = ?";
    public static final String SELECT_PRODUCT_BY_NAME_LANG = "SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=? and name_tr = ?";
    public static final String SELECT_PRODUCT_BY_NAME = "SELECT * FROM product  WHERE name = ?";
    public static final String SELECT_FROM_PRODUCT = "SELECT * FROM product";
    public static final String SELECT_FROM_PRODUCT_BY_LANG = "SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=?";
    public static final String SELECT_FROM_PRODUCT_LIMIT = "SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=? LIMIT ?, ?";
    public static final String INSERT_PRODUCT = "INSERT INTO product" +
            " (code, name, description, price, amount, uom) VALUES (?, ?, ?, ?,?,?)";
    public static final String SET_PRODUCT = "UPDATE product SET code = ?, name = ?, " +
            "description = ?, price = ?, amount= ?, uom= ? WHERE id_product = ?";
    public static final String SET_AMOUNT_PRODUCT = "UPDATE product SET  amount= ? WHERE id_product = ?";
    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM product WHERE id_product = ?";
    public static final String DELETE_PRODUCT_BY_CODE = "DELETE FROM product WHERE code = ?";

    public ProductDaoImpl() {
    }

    public List<Product> viewAllWithRestrict(int offset, int noOfRecords, int id_lang) {
        List<Product> products = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_FROM_PRODUCT_LIMIT);
            pstmt.setInt(1,id_lang);
            pstmt.setInt(2, offset);
            pstmt.setInt(3, noOfRecords);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(extractProductLang(rs));
            }
            rs = pstmt.executeQuery("select count(id_product)  from product");
            if (rs.next())
                this.totalAmountRecords = rs.getInt(1);
            rs.close();
            return products;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
        return products;
    }

    public int getTotalAmountRecords() {
        return totalAmountRecords;
    }



    public List<Product> viewAllWithSorting(int offset, int recordsOnPage, String sortingType, int id_lang) {
        List<Product> products = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=? ORDER by ");
        queryBuilder.append(sortingType);
        queryBuilder.append(" LIMIT ?, ?");
        System.out.println(queryBuilder.toString() + "myquery");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(queryBuilder.toString());
            pstmt.setInt(1, id_lang);
            pstmt.setInt(2, offset);
            pstmt.setInt(3, recordsOnPage);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(extractProductLang(rs));
            }
            rs = pstmt.executeQuery("select count(id_product)  from product");
            System.out.println(rs + "SELECT count(id_product) FROM product");
            if (rs.next())
                this.totalAmountRecords = rs.getInt(1);
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
        return products;
    }

    @Override
    public List findAll() {
        List<Product> products = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_FROM_PRODUCT);
            while (rs.next()) {
                products.add(extractProduct(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(stmt);
            close(con);
        }
        return products;
    }

    public List findAllByLang(int id_lang) {
        List<Product> products = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_FROM_PRODUCT_BY_LANG);
            pstmt.setInt(1, id_lang);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(extractProductLang(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
        return products;
    }


    private Product extractProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("id_product"));
        product.setCode(rs.getString("code"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
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
        Product product = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_PRODUCT_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                product = extractProduct(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
        return product;
    }

    @Override
    public boolean delete(Product product) {
        if (product == null) {
            return false;
        }
        int id = product.getId();
        if (findEntityById(id) == null) {
            return false;
        } else {
            int executeUpdate = 0;
            PreparedStatement pstmt = null;
            Connection con = null;
            try {
                con = DBManager.getInstance().getConnection();
                pstmt = con.prepareStatement(DELETE_PRODUCT_BY_ID);
                pstmt.setInt(1, id);
                executeUpdate = pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return executeUpdate > 0;
        }
    }


    public boolean delete(Integer id) {
        int executeUpdate = 0;
        PreparedStatement pstmt = null;
        Connection con = null;
        if (findEntityById(id) == null) {
            return false;
        } else {
            try {
                con = DBManager.getInstance().getConnection();
                pstmt = con.prepareStatement(DELETE_PRODUCT_BY_ID);
                pstmt.setInt(1, id);
                executeUpdate = pstmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                close(pstmt);
                close(con);
            }
            return executeUpdate > 0;
        }
    }

    public boolean deleteByCode(String code) {
        int executeUpdate = 0;
        PreparedStatement pstmt = null;
        Connection con = null;
        if (findProductByCode(code) == null) {
            return false;
        } else {
            try {
                con = DBManager.getInstance().getConnection();
                pstmt = con.prepareStatement(DELETE_PRODUCT_BY_CODE);
                pstmt.setString(1, code);
                executeUpdate = pstmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                close(pstmt);
                close(con);
            }
            return executeUpdate > 0;
        }
    }

    @Override
    public boolean create(Product product) {
        if (product == null)
            throw new IllegalArgumentException();
        int result;
        PreparedStatement pstmt = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            mapProduct(product, pstmt);
            result = pstmt.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setProductId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DBException("insert  was failed", e);
        } finally {
            close(pstmt);
            close(con);
        }
        return result > 0;
    }


    @Override
    public boolean update(Product product) {
        if (product == null) {
            return false;
        }
        product.getId();
        if (findEntityById(product.getId()) != null) {
            PreparedStatement pstmt = null;
            Connection con = null;
            int result = 0;
            try {
                con = DBManager.getInstance().getConnection();
                pstmt = con.prepareStatement(SET_PRODUCT);
                mapProduct(product, pstmt);
                pstmt.setInt(7, product.getId());
                result = pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close(pstmt);
                close(con);
            }
            return result > 0;
        } else {
            return false;
        }
    }


    public boolean updateAmount(Product product, Double amount) {
        if (product == null) {
            return false;
        }
        product.getId();
        Double storeAmount = product.getAmount();
        if (findEntityById(product.getId()) != null) {
            PreparedStatement pstmt = null;
            Connection con = null;
            int result = 0;
            try {
                con = DBManager.getInstance().getConnection();
                pstmt = con.prepareStatement(SET_AMOUNT_PRODUCT);
                pstmt.setDouble(1, storeAmount + amount);
                pstmt.setInt(2, product.getId());
                result = pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                close(pstmt);
                close(con);
            }
            return result > 0;
        } else {
            return false;
        }
    }


    private void mapProduct(Product product, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, product.getCode());
        pstmt.setString(2, product.getName());
        pstmt.setString(3, product.getDescription());
        pstmt.setDouble(4, product.getPrice());
        pstmt.setDouble(5, product.getAmount());
        pstmt.setString(6, product.getUom());
    }

    @Override
    public Product findProductByCode(String code) {
        Product product = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_PRODUCT_BY_CODE);
            pstmt.setString(1, code);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                product = extractProduct(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
        return product;
    }

    @Override
    public Product findProductByName(String name) {
        Product product = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_PRODUCT_BY_NAME);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                product = extractProduct(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
        return product;
    }

    public Product findProductByNameLang(String name, int id_lang) {
        Product product = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement(SELECT_PRODUCT_BY_NAME_LANG);
            pstmt.setInt(1, id_lang);
            pstmt.setString(2, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                product = extractProductLang(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
        return product;
    }

    public boolean deleteByCode(String... codes) {
        boolean flag = false;
        for (String code : codes) {
            flag = deleteByCode(code);
        }
        return flag;
    }

    public int findIDReceiptByCodeInSales(String code) {
        int idReceipt = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("select product_has_receipt.receipt_id_receipt from product_has_receipt join product where product_has_receipt.product_id_product=product.id_product and code=?");
            pstmt.setString(1, code);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                idReceipt = rs.getInt("product_has_receipt.receipt_id_receipt");
                System.out.println(idReceipt +" idReceipt");
            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }
        return idReceipt;
    }


}
