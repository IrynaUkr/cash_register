package cash.service;

import cash.db.dao.impl.ProductDaoImpl;
import cash.db.dao.impl.UserDaoImpl;
import cash.db.manager.DBManager;
import cash.entity.Role;
import cash.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import static org.apache.logging.log4j.core.util.Closer.close;

public class ServLetUtils {
    private static final Logger logger = LogManager.getLogger(ServLetUtils.class);


    public static int getIdLang(HttpServletRequest request) {
        String language = "en";
        String lang = (String) request.getSession().getAttribute("lang");
        if (lang != null) {
            language = lang;
        }
        return ProductDaoImpl.getInstance().getId_lang(language);
    }

    public static boolean isNameAndAmountValid(HttpServletRequest request) {
        logger.info("query: isNameAndAmountValid");
        return request.getParameter("productNA") != ""
                && request.getParameter("amountNA") != ""
          //      && isPositiveNumeric(request.getParameter("amountNA"))
                && request.getParameter("productCA") != ""
                && request.getParameter("amountCA") != "";
        //        && isPositiveNumeric(request.getParameter("amountCA"));
    }

    public static boolean isPositiveNumeric(String str) {
        double number;
        try {
            number = Double.parseDouble(str);
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static boolean isNameValid(HttpServletRequest request) {
        return request.getParameter("productNA") != ""
                && request.getParameter("productCA") != ""
                && request.getParameter("number") != "";

    }

    public static boolean isCreateUserFormValid(HttpServletRequest request) {
        return request.getParameter("login") != ""
                && request.getParameter("password") != ""
                && request.getParameter("surname") != ""
                && request.getParameter("role") != ""
                && request.getParameter("address") != ""
                && request.getParameter("phone number") != ""
                && request.getParameter("email") != "";
    }
    public static boolean isLoginFormValid(HttpServletRequest request){
        return request.getParameter("login") != ""
                && request.getParameter("password") != "";
    }

    public static void chooseStartPage(Role role, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (role) {
            case ADMIN -> request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(request, response);
            case CASHIER -> request.getRequestDispatcher("/WEB-INF/jsp/cashier.jsp").forward(request, response);
            case MERCHANDISER -> request.getRequestDispatcher("/WEB-INF/jsp/startMerch.jsp").forward(request, response);
            case CHIEF_CASHIER -> request.getRequestDispatcher("/WEB-INF/jsp/startChief.jsp").forward(request, response);
        }
    }
    public static User getUserFromRequest(HttpServletRequest request){
        String login = request.getParameter("login");
        Connection connection = DBManager.getInstance().getConnection();
        User user = UserDaoImpl.getInstance().findEntityByLoginWithCon(login,connection);
        try {
            close(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }return user;
    }
    public static List<User> getUsersFromRequestWithSorting(HttpServletRequest request, int page,int recordsPerPage,String sorting){
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        Connection connection = DBManager.getInstance().getConnection();
        List<User>  users = userDao.viewAllWithSortingWithCon((page - 1) * recordsPerPage, recordsPerPage, sorting, connection);
        try {
            close(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
