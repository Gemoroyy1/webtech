package ru.etu.web.client;
import com.google.gwt.user.client.rpc.*;
/** Контракт расчёта заказа на сервере. */
@RemoteServiceRelativePath("calculate")
public interface OrderService extends RemoteService { String calculate(String customer,String quantity,String delivery) throws IllegalArgumentException; }
