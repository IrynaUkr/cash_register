package cash.servlet;

import cash.db.dao.impl.ProductDaoImpl;
import cash.entity.Product;
import cash.service.ServiceReceiptProduct;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/merch/setAmountProduct")
public class ServletProductSetAmount extends HttpServlet {
    ProductDaoImpl productDao = new ProductDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String language= "en";
        if(request.getSession().getAttribute("lang")!=null){
            language= (String) request.getSession().getAttribute("lang");
        }
        int id_lang = (new ServiceReceiptProduct()).getId_lang(language);
        List<Product> products = productDao.findAllByLang(id_lang);
        request.getSession()
                .setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/jsp/product/updateAmount.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product= null;
        Double amount =0.0;
        String language= "en";
        if(request.getSession().getAttribute("lang")!=null){
            language= (String) request.getSession().getAttribute("lang");
        }
        int id_lang = (new ServiceReceiptProduct()).getId_lang(language);
        if (request.getParameter("productNA") != null && (request.getParameter("amountNA") != null)) {
            product = productDao.findProductByNameLang(request.getParameter("productNA"), id_lang);
            System.out.println(product +"found");
            amount = Double.valueOf(request.getParameter("amountNA"));
        } else if (request.getParameter("productCA") != null && request.getParameter("amountCA") != null) {
            product = productDao.findProductByCode(request.getParameter("productCA"));
             amount = Double.valueOf(request.getParameter("amountCA"));
        }
        boolean isUpdate = productDao.updateAmount(product, amount);
        if(isUpdate){
            request.getSession().setAttribute("message", "set new amount");
        }else{
            request.getSession().setAttribute("message", "did not set new amount");
        }
        response.sendRedirect("/ServletBack");
    }
}
