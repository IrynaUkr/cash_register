package cash.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/cashier/upload")
@MultipartConfig(maxFileSize = 1024 * 1024, maxRequestSize = 1024 * 1024 * 3)
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uploadPath = getServletContext().getRealPath("") + File.separator + getServletContext().getAttribute("FILE_DIR");

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        System.out.println(uploadDir.getAbsolutePath());
        try {
            String fileName = "";
            for (Part part : request.getParts()) {
                fileName = part.getSubmittedFileName();//get name
                if (fileName != null && !fileName.isBlank()) {
                    System.out.println("----->" + fileName);
                    part.write(uploadPath + File.separator + fileName);
                }
            }
            System.out.println("ok");
            request.getSession().setAttribute("message", "Upload Ok" );
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
            request.getSession().setAttribute("message", "Upload Error" + e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/jsp/receipt/handleReceipt.jsp")
                .forward(request, response);
    }
}
