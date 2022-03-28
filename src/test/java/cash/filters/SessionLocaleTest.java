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

public class SessionLocaleTest {
    final private SessionLocaleFilter testFilter = new SessionLocaleFilter();
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
    public void doFilterNullTest() throws ServletException, IOException {
        when(request.getParameter("sessionLocale")).thenReturn(null);
        testFilter.doFilter(request, response, chain);
        verify(session).setAttribute("lang","ua");
        verify(request, times(1)).getParameter("sessionLocale");
        verify(chain).doFilter(request,response);
    }
    @Test
    public void doFilterEnLangTest() throws ServletException, IOException {
        when(request.getParameter("sessionLocale")).thenReturn("en");
        testFilter.doFilter(request, response, chain);
        verify(session).setAttribute("lang","en");
        verify(request, times(2)).getParameter("sessionLocale");
        verify(chain).doFilter(request,response);
    }
    @Test
    public void doFilterUaLangTest() throws ServletException, IOException {
        when(request.getParameter("sessionLocale")).thenReturn("ua");
        testFilter.doFilter(request, response, chain);
        verify(session).setAttribute("lang","ua");
        verify(request, times(2)).getParameter("sessionLocale");
        verify(chain).doFilter(request,response);
    }
    @Test
    public void doFilterRuLangTest() throws ServletException, IOException {
        when(request.getParameter("sessionLocale")).thenReturn("ru");
        testFilter.doFilter(request, response, chain);
        verify(session).setAttribute("lang","ru");
        verify(request, times(2)).getParameter("sessionLocale");
        verify(chain).doFilter(request,response);
    }
}
