package cash.service;

import cash.db.dao.impl.ProductDaoImpl;
import cash.db.dao.impl.UserDaoImpl;
import cash.entity.Role;
import cash.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServLetUtils {
    private static final Logger logger = LogManager.getLogger(ServLetUtils.class);

    private ServLetUtils() {
    }

    private static ServLetUtils instance = null;

    public static ServLetUtils getInstance() {
        if (instance == null) {
            return new ServLetUtils();
        }
        return instance;
    }


    public static int getIdLang(HttpServletRequest request) {
        logger.info("query: get id language");
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
                && request.getParameter("productCA") != ""
                && request.getParameter("amountCA") != "";
    }

    public static boolean isPositiveNumeric(String str) {
        logger.info("query: is positive numeric");
        double number;
        try {
            number = Double.parseDouble(str);
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNameValid(HttpServletRequest request) {
        logger.info("query: is name valid");
        return request.getParameter("productNA") != ""
                && request.getParameter("productCA") != ""
                && request.getParameter("number") != "";
    }

    public static boolean isCreateUserFormValid(HttpServletRequest request) {
        logger.info("query: isCreateUserFormValid");
        return request.getParameter("login") != ""
                && request.getParameter("password") != ""
                && request.getParameter("surname") != ""
                && request.getParameter("role") != ""
                && request.getParameter("address") != ""
                && request.getParameter("phone number") != ""
                && request.getParameter("email") != "";
    }

    public static boolean isLoginFormValid(HttpServletRequest request) {
        logger.info("query: is Login FormValid");
        return request.getParameter("login") != ""
                && request.getParameter("password") != "";
    }

    public static void chooseStartPage(Role role, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("query: chooseStartPage");
        switch (role) {
            case ADMIN -> request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp").forward(request, response);
            case CASHIER -> request.getRequestDispatcher("/WEB-INF/jsp/cashier.jsp").forward(request, response);
            case MERCHANDISER -> request.getRequestDispatcher("/WEB-INF/jsp/startMerch.jsp").forward(request, response);
            case CHIEF_CASHIER ->
                    request.getRequestDispatcher("/WEB-INF/jsp/startChief.jsp").forward(request, response);
        }
    }

    public static User getUserFromRequest(HttpServletRequest request) {
        logger.info("query: getUserFromRequest");
        String login = request.getParameter("login");
        User user = UserDaoImpl.getInstance().findEntityByLogin(login);
        return user;
    }
}
