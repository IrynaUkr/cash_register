package cash.service;

import cash.db.dao.impl.ProductDaoImpl;
import cash.db.dao.impl.ReceiptImpl;
import cash.db.manager.DBManager;
import cash.entity.Product;
import cash.entity.Receipt;
import cash.entity.ReceiptProducts;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceReceiptProduct {
    private ReceiptProducts receiptProducts;

    public int getId_lang(String lang){
        int id_lang=0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("select * from language where short_name= ? ");
            pstmt.setString(1, lang);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id_lang = rs.getInt(1);
                System.out.println(id_lang +" idlang in get lang");
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }


        return id_lang;
    }

    public static ReceiptProducts[] getReceiptProducts(HttpServletRequest request) {
        ProductDaoImpl productDao = new ProductDaoImpl();
        Integer id1 = productDao.findProductByName(request.getParameter("products1")).getId();
        Integer id2 = productDao.findProductByName(request.getParameter("products2")).getId();
        Integer id3 = productDao.findProductByCode(request.getParameter("products3")).getId();
        Integer id4 = productDao.findProductByCode(request.getParameter("products4")).getId();
        Double amount1 = Double.valueOf(request.getParameter("amount1"));
        Double amount2 = Double.valueOf(request.getParameter("amount2"));
        Double amount3 = Double.valueOf(request.getParameter("amount3"));
        Double amount4 = Double.valueOf(request.getParameter("amount4"));
        ReceiptProducts[] rp =
                {new ReceiptProducts(id1, amount1),
                        new ReceiptProducts(id2, amount2),
                        new ReceiptProducts(id3, amount3),
                        new ReceiptProducts(id4, amount4)};
        System.out.println(rp);
        return rp;
    }

    private Product products;

    public static Product[] getProducts(HttpServletRequest request) {
        ProductDaoImpl productDao = new ProductDaoImpl();
        Product p1 = productDao.findProductByName(request.getParameter("products1"));
        Product p2 = productDao.findProductByName(request.getParameter("products2"));
        Product p3 = productDao.findProductByCode(request.getParameter("products3"));
        Product p4 = productDao.findProductByCode(request.getParameter("products4"));

        Product[] products = {p1, p2, p3, p4};


        return products;
    }

    public static ReceiptProducts createReceiptProduct(HttpServletRequest request, Product product, Double amount) {
        ReceiptProducts receiptProducts = new ReceiptProducts();
        receiptProducts.setProductId(product.getId());
        receiptProducts.setAmount(amount);
        receiptProducts.setPrice(product.getPrice());
        receiptProducts.setCode(product.getCode());
        receiptProducts.setName(product.getName());
        return receiptProducts;
    }

    public static void updateAmountSumReceipt(Receipt receipt) {
        ArrayList<ReceiptProducts> receiptProducts = receipt.getReceiptProducts();
        Double sum = 0.0;
        Double amount = 0.0;
        for (ReceiptProducts rp : receiptProducts) {
            sum += rp.getPrice() * rp.getAmount();
            amount += rp.getAmount();
        }
        receipt.setSum(sum);
        receipt.setAmount(amount);
    }

 void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            //log
        }
    }

   void close(Connection connection) {
        try {
            if (connection != null) {
                //return connection to pull
                connection.close();
            }
        } catch (SQLException e) {
            //log
        }
    }

    void close(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            //log
        }
    }


}

