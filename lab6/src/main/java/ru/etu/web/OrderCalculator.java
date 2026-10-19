package ru.etu.web;
import java.math.BigDecimal;
/** Независимая от JSP модель расчёта заказа. */
public final class OrderCalculator {
    /** Цена одного блокнота в рублях. */
    public static final BigDecimal PRICE = new BigDecimal("250.00");
    /** Рассчитывает итог, проверяя входные данные. */
    public BigDecimal calculate(String customer, String quantity, String delivery) {
        if(customer == null || customer.trim().isEmpty() || customer.length()>80) throw new IllegalArgumentException("Укажите имя от 1 до 80 символов.");
        int count;
        try { count=Integer.parseInt(quantity); } catch(RuntimeException e){throw new IllegalArgumentException("Количество должно быть целым числом.");}
        if(count<1 || count>1000) throw new IllegalArgumentException("Количество должно быть от 1 до 1000.");
        if(!"pickup".equals(delivery) && !"courier".equals(delivery)) throw new IllegalArgumentException("Выберите способ получения.");
        return PRICE.multiply(BigDecimal.valueOf(count)).add("courier".equals(delivery)?new BigDecimal("300.00"):BigDecimal.ZERO).setScale(2);
    }
    /** Экранирует пользовательский текст перед включением в HTML. */
    public static String escape(String value){return value==null?"":value.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");}
}
