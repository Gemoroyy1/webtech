package ru.etu.web;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Рассчитывает стоимость заказа по параметрам запроса и конфигурации. */
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BigDecimal unitPrice;
    private BigDecimal deliveryPrice;

    /** Читает постоянные цены при создании сервлета контейнером. */
    @Override public void init() throws ServletException {
        try {
            unitPrice = new BigDecimal(getInitParameter("unitPrice"));
            deliveryPrice = new BigDecimal(getInitParameter("deliveryPrice"));
            if (unitPrice.signum() < 0 || deliveryPrice.signum() < 0) throw new IllegalArgumentException();
        } catch (RuntimeException e) { throw new ServletException("Некорректные цены в web.xml", e); }
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException { processRequest(req, res); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException { processRequest(req, res); }

    /** Обрабатывает GET и POST; все данные конкретного заказа хранятся локально. */
    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        String content;
        try {
            String customer = req.getParameter("customer");
            if (customer == null || customer.trim().isEmpty() || customer.length() > 80) throw new IllegalArgumentException();
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            if (quantity < 1 || quantity > 1000) throw new IllegalArgumentException();
            String delivery = req.getParameter("delivery");
            if (!"pickup".equals(delivery) && !"courier".equals(delivery)) throw new IllegalArgumentException();
            BigDecimal shipping = "courier".equals(delivery) ? deliveryPrice : BigDecimal.ZERO;
            BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(quantity)).add(shipping);
            content = "<h1>Заказ рассчитан</h1><p>Покупатель: " + escape(customer.trim()) + "</p><p>Количество: " + quantity
                + "</p><p>Цена за единицу: " + unitPrice.toPlainString() + " руб.</p><p>Доставка: " + shipping.toPlainString()
                + " руб.</p><p>Итого: <strong>" + total.setScale(2).toPlainString() + " руб.</strong></p>";
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            content = "<h1>Ошибка ввода</h1><p>Укажите имя (1–80 символов), целое количество от 1 до 1000 и способ получения.</p>";
        }
        res.getWriter().write("<!doctype html><html lang='ru'><meta charset='UTF-8'><title>Результат расчёта</title><body>"
            + content + "<p><a href='index.html'>Новый расчёт</a></p></body></html>");
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
}
