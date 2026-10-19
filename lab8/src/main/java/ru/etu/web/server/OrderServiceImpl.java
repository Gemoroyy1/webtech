package ru.etu.web.server;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ru.etu.web.client.OrderService;
/** Серверный обработчик GWT RPC. */
public class OrderServiceImpl extends RemoteServiceServlet implements OrderService {
 private static final long serialVersionUID=1L;
 public String calculate(String customer,String quantity,String delivery){try { return "Покупатель: "+customer+". Итого: "+new OrderCalculator().calculate(customer,quantity,delivery).toPlainString()+" руб."; } catch (IllegalArgumentException e) { return "Ошибка ввода: "+e.getMessage(); }}
}

