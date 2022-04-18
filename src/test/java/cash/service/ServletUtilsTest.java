package cash.service;

import cash.entity.Role;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ServletUtilsTest {
    @Test
    public void createFormUserValidTest() {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        when(testRequest.getParameter("login")).thenReturn("someLogin");
        when(testRequest.getParameter("password")).thenReturn("somePassword");
        when(testRequest.getParameter("surname")).thenReturn("someSurname");
        when(testRequest.getParameter("role")).thenReturn("someRole");
        when(testRequest.getParameter("address")).thenReturn("someAddress");
        when(testRequest.getParameter("phoneNumber")).thenReturn("somePhoneNumber");
        when(testRequest.getParameter("email")).thenReturn("someEmail");
        assertTrue(ServLetUtils.isCreateUserFormValid(testRequest));
    }

    @Test
    public void createFormUserNotValidTest() {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        when(testRequest.getParameter("login")).thenReturn("");
        when(testRequest.getParameter("password")).thenReturn("");
        when(testRequest.getParameter("surname")).thenReturn("");
        when(testRequest.getParameter("role")).thenReturn("");
        when(testRequest.getParameter("address")).thenReturn("");
        when(testRequest.getParameter("phoneNumber")).thenReturn("");
        when(testRequest.getParameter("email")).thenReturn("");
        assertFalse(ServLetUtils.isCreateUserFormValid(testRequest));
    }

    @Test
    public void isNameValidTest() {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        when(testRequest.getParameter("productNA")).thenReturn("SomeProductNA");
        when(testRequest.getParameter("productCA")).thenReturn("SomeProductCA");
        when(testRequest.getParameter("number")).thenReturn("some Number");
        assertTrue(ServLetUtils.isNameValid(testRequest));
    }


    @Test
    public void isPositiveNumericNegativeEnterTest() {
        String number = "-5";
        assertFalse(ServLetUtils.isPositiveNumeric(number));
    }

    @Test
    public void isPositiveNumericNotNumberEnterTest() {
        String number = "notANumber";
        assertFalse(ServLetUtils.isPositiveNumeric(number));
    }

    @Test
    public void isPositiveNumericRightEnterTest() {
        String number = "125";
        assertTrue(ServLetUtils.isPositiveNumeric(number));
    }

    @Test
    public void isLoginFormValidTest() {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        when(testRequest.getParameter("login")).thenReturn("someLogin");
        when(testRequest.getParameter("password")).thenReturn("somePassword");
        assertTrue(ServLetUtils.isLoginFormValid(testRequest));
    }

    @Test
    public void isLoginFormNotValidTest() {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        when(testRequest.getParameter("login")).thenReturn("");
        when(testRequest.getParameter("password")).thenReturn("somePassword");
        assertFalse(ServLetUtils.isLoginFormValid(testRequest));
    }

    @Test
    public void isNameAndAmountValidTest() {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        when(testRequest.getParameter("productNA")).thenReturn("SomeProductNA");
        when(testRequest.getParameter("amountNA")).thenReturn("25");
        when(testRequest.getParameter("productCA")).thenReturn("SomeProductCA");
        when(testRequest.getParameter("amountCA")).thenReturn("75");
        assertTrue(ServLetUtils.isNameAndAmountValid(testRequest));
    }

    @Test
    public void isNameAndAmountNotValidTest() {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        when(testRequest.getParameter("productNA")).thenReturn("");
        when(testRequest.getParameter("amountNA")).thenReturn("-25");
        when(testRequest.getParameter("productCA")).thenReturn("SomeProductCA");
        when(testRequest.getParameter("amountCA")).thenReturn("ppp");
        assertFalse(ServLetUtils.isNameAndAmountValid(testRequest));
    }

    @Test
    public void chooseStartPageTest() throws ServletException, IOException {
        HttpServletRequest testRequest = mock(HttpServletRequest.class);
        HttpServletResponse testResponse = mock(HttpServletResponse.class);
        RequestDispatcher testDispatcher = mock(RequestDispatcher.class);
        Role role = Role.ADMIN;
        when(testRequest.getRequestDispatcher("/WEB-INF/jsp/admin.jsp")).thenReturn(testDispatcher);
        ServLetUtils.chooseStartPage(role, testRequest, testResponse);

        verify(testRequest, times(1)).getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        verify(testDispatcher).forward(testRequest, testResponse);
    }


}
