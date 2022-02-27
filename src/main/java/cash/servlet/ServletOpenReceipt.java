package cash.servlet;

import cash.entity.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/cashier/openReceipt")
public class ServletOpenReceipt extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/openReceipt.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (validateReceipt(request)) {
            String number = request.getParameter("number");
            OperationStatus status = OperationStatus.valueOf(request.getParameter("status"));
            OperationType type = OperationType.valueOf(request.getParameter("type"));
            User user = (User) request.getSession().getAttribute("user");
            ArrayList<ReceiptProducts> receiptProductsArrayList = new ArrayList<>();
            Receipt receipt = new Receipt(number,user.getId(), status, type);
            receipt.setReceiptProducts(receiptProductsArrayList);
            request.getSession().setAttribute("receipt", receipt);
            response.sendRedirect("/cashier/addProductToReceiptList");
        }
        else{
            request.getSession().setAttribute("message", "not all fields are filled");
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/openReceipt.jsp").forward(request, response);
        }
    }

    private boolean validateReceipt(HttpServletRequest request) {
        return !request.getParameter("number").equals("")
                && !request.getParameter("status").equals("")
                && !request.getParameter("type").equals("");
    }
}
