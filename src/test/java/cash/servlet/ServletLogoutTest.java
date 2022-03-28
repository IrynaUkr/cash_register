package cash.servlet;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class ServletLogoutTest {
    final ServletLogout servlet = new ServletLogout();
    HttpServletRequest testRequest;
    HttpServletResponse testResponse;
    HttpSession testSession;

    @Before
    public void setUp() {
        testRequest = mock(HttpServletRequest.class);
        testResponse = mock(HttpServletResponse.class);
        testSession = mock(HttpSession.class);
        when(testRequest.getSession()).thenReturn(testSession);
    }


    @Test
    public void invalidateTest() throws ServletException, IOException {
        servlet.doPost(testRequest, testResponse);
        verify(testSession).invalidate();
    }

    @Test
    public void redirectLoginPageTest() throws ServletException, IOException {
        servlet.doPost(testRequest, testResponse);
        verify(testResponse).sendRedirect("login.jsp");
    }
}
