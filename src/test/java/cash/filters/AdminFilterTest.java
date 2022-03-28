package cash.filters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class AdminFilterTest {
    final private AdminFilter testFilter = new AdminFilter();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private FilterChain chain;


    @Before
    public void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        chain = Mockito.mock(FilterChain.class);
        when(request.getSession()).thenReturn(session);

    }

    @Test
    public void doFilterAdminCallTest() throws ServletException, IOException {
        when(session.getAttribute("role")).thenReturn("ADMIN");
        testFilter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
        verify(response, never()).sendRedirect("login.jsp");
    }

    @Test
    public void doFilterAnotherUserCallTest() throws ServletException, IOException {
        when(session.getAttribute("role")).thenReturn("MERCHANDISER");
        testFilter.doFilter(request, response, chain);
        verify(chain, never()).doFilter(request, response);
        verify(response).sendRedirect("login.jsp");
    }
}


