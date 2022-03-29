package cash.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/cashier/downLoad")
public class DownLoadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = getServletContext().getRealPath("");
        String fileName = path + getServletContext().getAttribute("FILE_DIR") + File.separator + request.getParameter("filename");
        System.out.println("download in download servlet " + fileName);
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File not found!!");
            request.getSession().setAttribute("message", "File not found.");
            response.sendRedirect("/WEB-INF/jsp/receipt/handleReceipt.jsp");
            return;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        OutputStream out = response.getOutputStream();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            System.out.println("write bytes into buffer");
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = fileInputStream.read(buffer) )!= -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
