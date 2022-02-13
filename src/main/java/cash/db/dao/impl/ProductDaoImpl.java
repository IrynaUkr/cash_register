package cash.db.dao.impl;

import cash.db.dao.ProductDao;
import cash.db.manager.DBManager;
import cash.entity.Product;
import cash.exceptions.DBException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    public static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM product  WHERE id_product = ?";
    public static final String SELECT_PRODUCT_BY_CODE = "SELECT * FROM product  WHERE code = ?";
    public static final String SELECT_PRODUCT_BY_NAME = "SELECT * FROM product  WHERE name = ?";
    public static final String SELECT_FROM_PRODUCT = "SELECT * FROM product";
    public static final String INSERT_PRODUCT = "INSERT INTO product" +
            " (code, name, description, price, amount, uom) VALUES (?, ?, ?, ?,?,?)";
    public static final String SET_PRODUCT = "UPDATE product SET code = ?, name = ?, " +
            "description = ?, price = ?, amount= ?, uom= ? WHERE id_product = ?";

    public static final String DELETE_PRODUCT_BY_ID = "DELETE FROM product WHERE id_product = ?";

    public ProductDaoImpl() {
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
            stmt.close();
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
            pstmt.close();
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

    @Override
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
                pstmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
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
            System.out.println("this id has been found");
            PreparedStatement pstmt = null;
            Connection con = null;
            int result = 0;
            try {
                con = DBManager.getInstance().getConnection();
                pstmt = con.prepareStatement(SET_PRODUCT);
                mapProduct(product, pstmt);
                pstmt.setInt(7,product.getId());
                result = pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
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
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        }

        return product;
    }
}
