package cash.db.dao.impl;

import cash.db.dao.ProductDao;
import cash.db.manager.DBManager;
import cash.entity.Product;
import cash.exceptions.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    public static final String COUNT_PRODUCT = "SELECT COUNT(DISTINCT id_product)  FROM product ";
    public static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product  WHERE id_product = ?";
    public static final String SELECT_PRODUCT_BY_CODE = "SELECT * FROM product  WHERE code = ?";
    public static final String SELECT_PRODUCT_BY_CODE_LANG = "SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=? and code=?";
    public static final String SELECT_PRODUCT_BY_NAME_LANG = "SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=? and name_tr = ?";
    public static final String SELECT_FROM_PRODUCT = "SELECT * FROM product";
    public static final String SELECT_FROM_PRODUCT_BY_LANG = "SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=?";
    public static final String SELECT_FROM_PRODUCT_LIMIT = "SELECT * FROM product JOIN translate WHERE product.id_product = translate.id_prod_tr and id_lang_tr=? LIMIT ?, ?";
    public static final String INSERT_PRODUCT = "INSERT INTO product" +
            " (code, price, amount, uom) VALUES (?, ?, ?, ?)";
    public static final String SET_AMOUNT_PRODUCT = "UPDATE product SET  amount= ? WHERE id_product = ?";
    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM product WHERE id_product = ?";
    public static final String DELETE_PRODUCT_BY_CODE = "DELETE FROM product WHERE code = ?";


    public ProductDaoImpl() {
    }

    private int totalAmountRecords;

    public List<Product> viewAllWithRestrict(int offset, int noOfRecords, int id_lang) {
        List<Product> products = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_FROM_PRODUCT_LIMIT)) {
            pstmt.setInt(1, id_lang);
            pstmt.setInt(2, offset);
            pstmt.setInt(3, noOfRecords);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(extractProductLang(rs));
            }
            rs = pstmt.executeQuery(COUNT_PRODUCT);
            if (rs.next())
                this.totalAmountRecords = rs.getInt(1);
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(queryBuilder.toString())) {
            pstmt.setInt(1, id_lang);
            pstmt.setInt(2, offset);
            pstmt.setInt(3, recordsOnPage);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(extractProductLang(rs));
            }
            rs = pstmt.executeQuery(COUNT_PRODUCT);
            if (rs.next())
                this.totalAmountRecords = rs.getInt(1);
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_FROM_PRODUCT);
            while (rs.next()) {
                products.add(extractProduct(rs));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    public List<Product> findAllByLang(int id_lang) {
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
            ex.printStackTrace();
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
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product = extractProduct(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            try (Connection con = DBManager.getInstance().getConnection();
                 PreparedStatement pstmt = con.prepareStatement(DELETE_PRODUCT_BY_ID)) {
                pstmt.setInt(1, id);
                executeUpdate = pstmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return executeUpdate > 0;
        }
    }


    public boolean deleteProductByCode(String code) {
        int executeUpdate = 0;
        if (findProductByCode(code) == null) {
            return false;
        } else {
            try (Connection con = DBManager.getInstance().getConnection();
                 PreparedStatement pstmt = con.prepareStatement(DELETE_PRODUCT_BY_CODE)) {
                pstmt.setString(1, code);
                executeUpdate = pstmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return executeUpdate > 0;
        }
    }

    @Override
    public boolean create(Product product) {
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
        } catch (SQLException e) {
            throw new DBException("insert  was failed", e);
        }
        return result > 0;
    }


    public boolean updateAmount(Product product, Double amount) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }


    public void mapProduct(Product product, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, product.getCode());
        pstmt.setDouble(2, product.getPrice());
        pstmt.setDouble(3, product.getAmount());
        pstmt.setString(4, product.getUom());
    }


    public Product findProductByCode(String code) {
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
            ex.printStackTrace();
        }
        return product;
    }

    public Product findProductByCodeLang(String code, int id_lang) {
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
            ex.printStackTrace();
        }
        return product;
    }


    public Product findProductByNameLang(String name, int id_lang) {
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
            ex.printStackTrace();
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


    public int getId_lang(String lang) {
        int id_lang = 0;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement("select * from language where short_name= ? ")) {
            pstmt.setString(1, lang);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id_lang = rs.getInt(1);
                System.out.println(id_lang + " idlang in get lang");
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id_lang;
    }
}
