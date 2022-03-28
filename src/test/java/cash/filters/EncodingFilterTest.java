package cash.filters;

import cash.service.HashUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EncodingFilterTest {
    final private EncodingFilter testFilter = new EncodingFilter();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private FilterChain chain;
    private FilterConfig config;

    @Before
    public void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        chain = Mockito.mock(FilterChain.class);
        config = Mockito.mock(FilterConfig.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void doFilterEncodingTest() {
        try {
            testFilter.doFilter(request, response, chain);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        verify(response).setContentType("text/html; charset=UTF-8");
        verify(response).setCharacterEncoding("UTF-8");
        verify(request, never()).getSession();
        try {
            verify(chain).doFilter(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void initEncodingFilterCallInitParamTest(){
        try {
            testFilter.init(config);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        verify(config).getInitParameter("requestEncoding");

    }

}
