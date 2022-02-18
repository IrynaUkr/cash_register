package cash.servlet;

import cash.entity.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/cashier/openReceipt")
public class ServletOpenCheck extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet in /cashier/openReceipt");
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/openReceipt.jsp")
                .forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number = request.getParameter("number");
        OperationStatus status = OperationStatus.valueOf(request.getParameter("status"));
        OperationType type = OperationType.valueOf(request.getParameter("type"));
        User user = (User) request.getSession().getAttribute("user");
        ArrayList<ReceiptProducts> receiptProductsArrayList = new ArrayList<>();
        Receipt receipt = new Receipt(number,status,type);
        receipt.setReceiptProducts(receiptProductsArrayList);
        receipt.setNumber(number);
        receipt.setIdUser(user.getId());
        request.getSession().setAttribute("receipt",receipt);
        response.sendRedirect("/cashier/addProductToReceiptList");
    }
}
