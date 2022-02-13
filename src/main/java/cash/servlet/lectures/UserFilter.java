//package cash.servlet;
//
//import javax.servlet.*;
//import javax.servlet.annotation.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter("/admin/*")
//public class UserFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
//        chain.doFilter(request, response);
//        Object isAdmin = ((HttpServletRequest)request).getSession().getAttribute("isAdmin");
//        if(isAdmin!=null){
//            chain.doFilter(request, response);
//        }else{
//            ((HttpServletResponse)response).sendRedirect("login.jsp");
//        }
//    }
//}
