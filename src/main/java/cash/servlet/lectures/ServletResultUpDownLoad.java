package cash.servlet.lectures;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/result")
public class ServletResultUpDownLoad extends HttpServlet {
    private static final String html = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Result</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>Result</h1>\n" +
            "<p>$$$</p>\n" +
            "</body>\n" +
            "</html>";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object message = request.getSession().getAttribute("message");
        String defaultMessage = "No message";
        response.getWriter().append(html.replace("$$$", (message == null) ? defaultMessage : message.toString()));

    }


}
