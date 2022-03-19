package cash.servlet;

import cash.db.dao.impl.PaymentDaoImpl;
import cash.entity.OperationType;
import cash.entity.Payment;
import cash.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/chief/servletPayment")
public class ServletPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("do get from/chief/servletPayment ");
        request.getRequestDispatcher("/WEB-INF/jsp/payment/servicePayment.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isCreatePaymentFormValid(request)) {
            Payment payment = getPaymentFromRequest(request);
            PaymentDaoImpl paymentDao = PaymentDaoImpl.getInstance();
            boolean isPay = paymentDao.create(payment);
            if (isPay) {
                request.setAttribute("message", "payment was added");
                response.sendRedirect("/ServletBack");
            } else {
                request.setAttribute("message", " payment was not added");
                request.getRequestDispatcher("/WEB-INF/jsp/payment/servicePayment.jsp")
                        .forward(request, response);
            }
        } else {
            request.setAttribute("message", "required fields are empty");
            request.getRequestDispatcher("/WEB-INF/jsp/payment/servicePayment.jsp")
                    .forward(request, response);
        }
    }

    private boolean isCreatePaymentFormValid(HttpServletRequest request) {
        return request.getParameter("value") != ""
                && request.getParameter("type") != ""
                && request.getParameter("description") != ""
                && request.getParameter("user") != "";
    }

    private Payment getPaymentFromRequest(HttpServletRequest request) {
        Double value = Double.valueOf(request.getParameter("value"));
        OperationType type = OperationType.valueOf(request.getParameter("type"));
        String desc = request.getParameter("description");
        User user = (User) request.getSession().getAttribute("user");
        return new Payment(value, user.getId(), type, desc);
    }
}
