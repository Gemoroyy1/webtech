package ru.etu.web.client;
import com.google.gwt.user.client.rpc.AsyncCallback;
/** Асинхронный контракт GWT RPC. */
public interface OrderServiceAsync { void calculate(String customer,String quantity,String delivery,AsyncCallback<String> callback); }
