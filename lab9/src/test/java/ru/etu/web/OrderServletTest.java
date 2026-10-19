package ru.etu.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

/** Тесты реального сервлета lab3 с заглушками Servlet API. */
public class OrderServletTest {
    private OrderServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter output;

    @Before public void setUp() throws Exception {
        ServletConfig config = mock(ServletConfig.class);
        when(config.getInitParameter("unitPrice")).thenReturn("250.00");
        when(config.getInitParameter("deliveryPrice")).thenReturn("300.00");
        servlet = new OrderServlet();
        servlet.init(config);
        verify(config).getInitParameter("unitPrice");
        verify(config).getInitParameter("deliveryPrice");
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        output = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(output));
        parameters("Иван", "3", "courier");
    }

    private void parameters(String name, String quantity, String delivery) {
        when(request.getParameter("customer")).thenReturn(name);
        when(request.getParameter("quantity")).thenReturn(quantity);
        when(request.getParameter("delivery")).thenReturn(delivery);
    }
    private void invalid(String name, String quantity, String delivery) throws Exception {
        parameters(name,quantity,delivery);
        servlet.doPost(request,response);
        verify(response).setStatus(400);
        assertTrue(output.toString().contains("Ошибка ввода"));
        assertFalse(output.toString().contains("Заказ рассчитан"));
    }

    @Test public void postCalculatesCourierAndSetsEncodingBeforeReading() throws Exception {
        servlet.doPost(request,response);
        assertTrue(output.toString().contains("1050.00 руб."));
        assertTrue(output.toString().contains("Иван"));
        verify(response,never()).setStatus(anyInt());
        verify(response).setContentType("text/html;charset=UTF-8");
        InOrder order = inOrder(request);
        order.verify(request).setCharacterEncoding("UTF-8");
        order.verify(request).getParameter("customer");
        verify(response).getWriter();
    }
    @Test public void getCalculatesPickup() throws Exception {
        parameters("Мария","2","pickup"); servlet.doGet(request,response);
        assertTrue(output.toString().contains("500.00 руб."));
        verify(response,never()).setStatus(anyInt());
    }
    @Test public void maximumQuantityIsAccepted() throws Exception {
        parameters("Иван","1000","pickup"); servlet.doPost(request,response);
        assertTrue(output.toString().contains("250000.00 руб."));
    }
    @Test public void missingName() throws Exception { invalid(null,"1","pickup"); }
    @Test public void blankName() throws Exception { invalid("   ","1","pickup"); }
    @Test public void longName() throws Exception { invalid(new String(new char[81]).replace('\0','x'),"1","pickup"); }
    @Test public void zeroQuantity() throws Exception { invalid("Иван","0","pickup"); }
    @Test public void negativeQuantity() throws Exception { invalid("Иван","-1","pickup"); }
    @Test public void excessiveQuantity() throws Exception { invalid("Иван","1001","pickup"); }
    @Test public void nonIntegerQuantity() throws Exception { invalid("Иван","1.5","pickup"); }
    @Test public void missingQuantity() throws Exception { invalid("Иван",null,"pickup"); }
    @Test public void unknownDelivery() throws Exception { invalid("Иван","1","unknown"); }
    @Test public void missingDelivery() throws Exception { invalid("Иван","1",null); }
    @Test public void customerIsEscaped() throws Exception {
        parameters("<script>alert(1)</script>","1","pickup"); servlet.doPost(request,response);
        assertTrue(output.toString().contains("&lt;script&gt;"));
        assertFalse(output.toString().contains("<script>"));
    }
    @Test public void initializationPricesAffectResult() throws Exception {
        ServletConfig config=mock(ServletConfig.class);
        when(config.getInitParameter("unitPrice")).thenReturn("10.50");
        when(config.getInitParameter("deliveryPrice")).thenReturn("2.00");
        servlet.init(config); servlet.doPost(request,response);
        assertTrue(output.toString().contains("33.50 руб."));
    }
    @Test public void invalidConfigurationFailsInitialization() {
        ServletConfig config=mock(ServletConfig.class);
        when(config.getInitParameter("unitPrice")).thenReturn("-1");
        when(config.getInitParameter("deliveryPrice")).thenReturn("300");
        assertThrows(ServletException.class,()->new OrderServlet().init(config));
    }
}
