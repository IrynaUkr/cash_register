//package cash.servlet;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.*;
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//
//@WebServlet("/cashier/downLoad")
//public class DownLoadServlet extends HttpServlet {
//    private static final Logger logger = LogManager.getLogger(DownLoadServlet.class);
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        logger.info("Servlet: DownLoadServlet. Method: Get");
//        String path = getServletContext().getRealPath("");
//        String fileName = path + getServletContext().getAttribute("FILE_DIR") + File.separator + request.getParameter("filename");
//        System.out.println("download in download servlet " + fileName);
//        File file = new File(fileName);
//        if (!file.exists()) {
//            logger.info("Servlet: DownLoadServlet.File not found!!");
//            request.getSession().setAttribute("message", "File not found.");
//            response.sendRedirect("/WEB-INF/jsp/receipt/handleReceipt.jsp");
//            return;
//        }
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
//        OutputStream out = response.getOutputStream();
//        try (FileInputStream fileInputStream = new FileInputStream(file)) {
//            byte[] buffer = new byte[4096];
//            int bytesRead = -1;
//            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
//                out.write(buffer, 0, bytesRead);
//            }
//            out.flush();
//        } catch (IOException e) {
//            logger.error("file was not download");
//        }
//    }
//}
