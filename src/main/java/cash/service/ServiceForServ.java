package cash.service;

import javax.servlet.http.HttpServletRequest;

public class ServiceForServ {

    public static int getId_lang(HttpServletRequest request) {
        String language = "en";
        if (request.getSession().getAttribute("lang") != null) {
            language = (String) request.getSession().getAttribute("lang");
        }
        return (new ServiceReceiptProduct()).getId_lang(language);
    }

    public static boolean isValidate(HttpServletRequest request) {
        return request.getParameter("productNA") != ""
                && request.getParameter("amountNA") != ""
                && request.getParameter("productCA") != ""
                && request.getParameter("amountCA") != "";

    }
    public static boolean isValidateName(HttpServletRequest request) {
        return request.getParameter("productNA") != ""
                && request.getParameter("productCA") != ""
                && request.getParameter("number")!="";

    }

}
