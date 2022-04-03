package cash.servlet;

import cash.db.dao.impl.ReceiptImpl;
import cash.entity.OperationStatus;
import cash.entity.Receipt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/chief/canselReceipt")
public class ServletCanselReceipt extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ServletCanselReceipt.class);
    ReceiptImpl receiptDao = ReceiptImpl.getInstance();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: /chief/canselReceipt. Method: Get");
        int page = 1;
        int recordsPerPage = 4;
        int totalAmountRecords;
        int totalAmPages;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("number") != null) {
            request.getSession().setAttribute("sorting", "number");
        } else if (request.getParameter("date") != null) {
            request.getSession().setAttribute("sorting", "date");
        } else if (request.getParameter("type") != null) {
            request.getSession().setAttribute("sorting", "type");
        }else if (request.getParameter("status") != null) {
            request.getSession().setAttribute("sorting", "status");
        }
        List<Receipt> receipts;
        if (request.getSession().getAttribute("sorting") != null) {

            String sorting = String.valueOf(request.getSession().getAttribute("sorting"));
            System.out.println(sorting);
            receipts = receiptDao.viewAllReceiptsWithSorting((page - 1) * recordsPerPage, recordsPerPage, sorting);
        } else {
            receipts = receiptDao.findAllReceiptWithRestrict((page - 1) * recordsPerPage, recordsPerPage);
        }
        totalAmountRecords = receiptDao.getTotalAmountRecords();
        totalAmPages = (int) Math.ceil(totalAmountRecords * 1.0 / recordsPerPage);
        request.getSession().setAttribute("receipts", receipts);
        request.getSession().setAttribute("currentPage", page);
        request.getSession().setAttribute("totalAmPages", totalAmPages);
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/receiptPages.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Servlet: /chief/canselReceipt. Method: Post");
        boolean isReceiptDeleted;
        if (request.getParameterValues("selected") != null) {
            String[] numbers = request.getParameterValues("selected");
            isReceiptDeleted = receiptDao.deleteReceiptsByNumber(numbers);
            if (isReceiptDeleted) {
                request.getSession().setAttribute("message", "receipts were deleted!");
                response.sendRedirect("/ServletBack");
            } else {
                request.getSession().setAttribute("message", "receipts cannot be deleted! ");
                response.sendRedirect("/ServletBack");
            }
        } else {
            request.setAttribute("message", "required fields are empty");
            request.getRequestDispatcher("/WEB-INF/jsp/receipt/receiptPages.jsp").forward(request, response);
        }
    }
}

