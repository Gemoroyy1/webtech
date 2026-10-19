from pathlib import Path
p=Path('Лаба4/src/main/java/ru/etu/web/OrderServlet.java')
s=p.read_text(encoding='utf-8-sig').replace('import java.math.BigDecimal;', 'import java.math.BigDecimal;\nimport java.util.Locale;\nimport java.util.ResourceBundle;')
s=s.replace('String content;', '''String lang = "en".equals(req.getParameter("lang")) ? "en" : "ru";
        ResourceBundle b = ResourceBundle.getBundle("messages", Locale.forLanguageTag(lang));
        res.setHeader("Content-Language", lang);
        String content;
        if (req.getParameter("quantity") == null && "GET".equals(req.getMethod())) {
            res.getWriter().write(page(lang, b, form(lang, b)));
            return;
        }''')
a=s.index('            content = "<h1>Заказ рассчитан')
z=s.index('\n        } catch',a)
s=s[:a]+'''            content = "<h1>" + b.getString("success") + "</h1><p>" + b.getString("customer") + ": " + escape(customer.trim())
                + "</p><p>" + b.getString("quantity") + ": " + quantity + "</p><p>" + b.getString("price") + ": " + unitPrice
                + " RUB</p><p>" + b.getString("shipping") + ": " + shipping + " RUB</p><p>" + b.getString("total")
                + ": <strong>" + total.setScale(2) + " RUB</strong></p>";'''+s[z:]
a=s.index('            content = "<h1>Ошибка ввода')
z=s.index('\n    private static String escape',a)
s=s[:a]+'''            content = "<h1>" + b.getString("error") + "</h1><p>" + b.getString("validation") + "</p>";
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
''' +s[z:]
p.write_text(s,encoding='utf-8')
ru=['Расчёт стоимости заказа','Товар: блокнот. Валюта расчёта — российский рубль.','Покупатель','Количество','Получение','Самовывоз','Курьер','Рассчитать','Заказ рассчитан','Цена за единицу','Доставка','Итого','Ошибка ввода','Укажите имя (1–80 символов), целое количество от 1 до 1000 и способ получения.','Новый расчёт']
en=['Order cost calculator','Product: notebook. All prices are in Russian rubles.','Customer','Quantity','Delivery method','Pickup','Courier','Calculate','Order calculated','Unit price','Shipping','Total','Input error','Enter a name (1–80 characters), an integer quantity from 1 to 1000 and a delivery method.','New calculation']
keys=['title','description','customer','quantity','delivery','pickup','courier','calculate','success','price','shipping','total','error','validation','new']
r=Path('Лаба4/src/main/resources');r.mkdir(parents=True,exist_ok=True)
for lang,values in [('ru',ru),('en',en)]:
    (r/('messages_'+lang+'.properties')).write_text(''.join(k+'='+v.encode('ascii','backslashreplace').decode().replace('\\x','\\u00')+'\n' for k,v in zip(keys,values)),encoding='ascii')
(r/'messages.properties').write_bytes((r/'messages_ru.properties').read_bytes())
w=Path('Лаба4/src/main/webapp/WEB-INF/web.xml');w.write_text(w.read_text(encoding='utf-8-sig').replace('index.html','order'),encoding='utf-8')
Path('Лаба4/src/main/webapp/index.html').write_text('<!doctype html><meta charset="UTF-8"><meta http-equiv="refresh" content="0;url=order"><a href="order?lang=ru">Русский</a> | <a href="order?lang=en">English</a>',encoding='utf-8')
