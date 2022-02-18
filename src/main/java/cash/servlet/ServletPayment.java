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
        Double value = Double.valueOf(request.getParameter("value"));
        OperationType type = OperationType.valueOf(request.getParameter("type"));
        String desc = request.getParameter("description");
        User user = (User) request.getSession().getAttribute("user");
        Payment payment = new Payment();
        payment.setValue(value);
        payment.setType(type);
        payment.setIdUser(user.getId());
        payment.setDescription(desc);
        System.out.println(payment);
        PaymentDaoImpl paymentDao = new PaymentDaoImpl();
        boolean isPay = paymentDao.create(payment);
        if (isPay) {
            request.setAttribute("message", "payment was added");
        } else {
            request.setAttribute("message", " payment was not added");
        }

        response.sendRedirect("/ServletBack");
    }
}
