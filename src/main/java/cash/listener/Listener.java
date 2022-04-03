//package cash.listener;
//
//import javax.servlet.*;
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;
//
//@WebListener
//public class Listener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
//
//    public Listener() {
//    }
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
//        ServletContext servletContext = sce.getServletContext();
//        String fileDir = servletContext.getInitParameter("FILE_DIR");
//        System.out.println("InitParams: fileDir = " + fileDir);
//        if (fileDir == null) {
//            fileDir = "files";
//        }
//        servletContext.setAttribute("FILE_DIR", fileDir);
//    }
//
//}
