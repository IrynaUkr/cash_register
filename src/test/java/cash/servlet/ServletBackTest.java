package cash.servlet;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class ServletBackTest {
    final ServletBack servlet = new ServletBack();
    private HttpServletRequest testRequest;
    private HttpServletResponse testResponse;
    private HttpSession testSession;
    private RequestDispatcher testDispatcher;

    @Before
    public void setUp() throws Exception {
        testRequest = mock(HttpServletRequest.class);
        testResponse = mock(HttpServletResponse.class);
        testSession = mock(HttpSession.class);
        testDispatcher = mock(RequestDispatcher.class);
        when(testRequest.getSession()).thenReturn(testSession);
    }


    @Test
    public void whenCallDoGetServletBackAdminToLoginPageTest() throws ServletException, IOException {
        when(testSession.getAttribute("role")).thenReturn("ADMIN");
        when(testRequest.getRequestDispatcher("/WEB-INF/jsp/admin.jsp")).thenReturn(testDispatcher);
        servlet.doGet(testRequest, testResponse);
        verify(testRequest, times(1)).getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        verify(testDispatcher).forward(testRequest, testResponse);
    }

    @Test
    public void whenCallDoGetServletBackCashierToLoginPageTest() throws ServletException, IOException {
        when(testSession.getAttribute("role")).thenReturn("CASHIER");
        when(testRequest.getRequestDispatcher("/WEB-INF/jsp/cashier.jsp")).thenReturn(testDispatcher);
        servlet.doGet(testRequest, testResponse);
        verify(testRequest, times(1)).getRequestDispatcher("/WEB-INF/jsp/cashier.jsp");
        verify(testDispatcher).forward(testRequest, testResponse);
    }
    @Test
    public void whenCallDoGetServletBackChiefToLoginPageTest() throws ServletException, IOException {
        when(testSession.getAttribute("role")).thenReturn("MERCHANDISER");
        when(testRequest.getRequestDispatcher("/WEB-INF/jsp/startMerch.jsp")).thenReturn(testDispatcher);
        servlet.doGet(testRequest, testResponse);
        verify(testRequest, times(1)).getRequestDispatcher("/WEB-INF/jsp/startMerch.jsp");
        verify(testDispatcher).forward(testRequest, testResponse);
    }
    @Test
    public void whenCallDoGetServletBackMerchToLoginPageTest() throws ServletException, IOException {
        when(testSession.getAttribute("role")).thenReturn("CHIEF_CASHIER");
        when(testRequest.getRequestDispatcher("/WEB-INF/jsp/startChief.jsp")).thenReturn(testDispatcher);
        servlet.doGet(testRequest, testResponse);
        verify(testRequest, times(1)).getRequestDispatcher("/WEB-INF/jsp/startChief.jsp");
        verify(testDispatcher).forward(testRequest, testResponse);
    }
}
