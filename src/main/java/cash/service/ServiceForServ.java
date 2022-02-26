package cash.service;

import javax.servlet.http.HttpServletRequest;

public class ServiceForServ {

    public static int getId_lang(HttpServletRequest request) {
        String language= "en";
        if(request.getSession().getAttribute("lang")!=null){
            language= (String) request.getSession().getAttribute("lang");
        }
        int id_lang = (new ServiceReceiptProduct()).getId_lang(language);
        return id_lang;
    }
    public static boolean isValidate(HttpServletRequest request) {
        return request.getParameter("productNA") != ""
                && request.getParameter("amountNA") != ""
                && request.getParameter("productCA") != ""
                && request.getParameter("amountCA") != "";
    }
}
