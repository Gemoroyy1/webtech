package ru.etu.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;
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
        String lang = "en".equals(req.getParameter("lang")) ? "en" : "ru";
        ResourceBundle b = ResourceBundle.getBundle("messages", Locale.forLanguageTag(lang));
        res.setHeader("Content-Language", lang);
        String content;
        if (req.getParameter("quantity") == null && "GET".equals(req.getMethod())) {
            res.getWriter().write(page(lang, b, form(lang, b)));
            return;
        }
        try {
            String customer = req.getParameter("customer");
            if (customer == null || customer.trim().isEmpty() || customer.length() > 80) throw new IllegalArgumentException();
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            if (quantity < 1 || quantity > 1000) throw new IllegalArgumentException();
            String delivery = req.getParameter("delivery");
            if (!"pickup".equals(delivery) && !"courier".equals(delivery)) throw new IllegalArgumentException();
            BigDecimal shipping = "courier".equals(delivery) ? deliveryPrice : BigDecimal.ZERO;
            BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(quantity)).add(shipping);
            content = "<h1>" + b.getString("success") + "</h1><p>" + b.getString("customer") + ": " + escape(customer.trim())
                + "</p><p>" + b.getString("quantity") + ": " + quantity + "</p><p>" + b.getString("price") + ": " + unitPrice
                + " RUB</p><p>" + b.getString("shipping") + ": " + shipping + " RUB</p><p>" + b.getString("total")
                + ": <strong>" + total.setScale(2) + " RUB</strong></p>";
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            content = "<h1>" + b.getString("error") + "</h1><p>" + b.getString("validation") + "</p>";
        }
        res.getWriter().write(page(lang, b, content));
    }

    private static String page(String lang, ResourceBundle b, String content) {
        return "<!doctype html><html lang='" + lang + "'><meta charset='UTF-8'><title>" + b.getString("title")
            + "</title><body><nav><a href='order?lang=ru'>Русский</a> | <a href='order?lang=en'>English</a></nav>"
            + content + "<p><a href='order?lang=" + lang + "'>" + b.getString("new") + "</a></p></body></html>";
    }

    private static String form(String lang, ResourceBundle b) {
        return "<h1>" + b.getString("title") + "</h1><p>" + b.getString("description")
            + "</p><form method='post' action='order'><input type='hidden' name='lang' value='" + lang
            + "'><p><label>" + b.getString("customer") + " <input name='customer' required maxlength='80'></label></p><p><label>"
            + b.getString("quantity") + " <input name='quantity' type='number' min='1' max='1000' value='1' required></label></p><p><label>"
            + b.getString("delivery") + " <select name='delivery'><option value='pickup'>" + b.getString("pickup")
            + "</option><option value='courier'>" + b.getString("courier") + "</option></select></label></p><button>"
            + b.getString("calculate") + "</button></form>";
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
}
