package ru.etu.web.client;
import com.google.gwt.core.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;
/** Клиентская точка входа, компилируемая в JavaScript. */
public class OrderApp implements EntryPoint {
 public void onModuleLoad(){
  VerticalPanel panel=new VerticalPanel();
  TextBox name=new TextBox(),quantity=new TextBox(); quantity.setText("1");
  name.getElement().setAttribute("aria-label","Покупатель");quantity.getElement().setAttribute("aria-label","Количество");
  ListBox delivery=new ListBox();delivery.addItem("Самовывоз","pickup");delivery.addItem("Курьер","courier");delivery.getElement().setAttribute("aria-label","Получение");
  Label result=new Label();result.getElement().setAttribute("role","status");Button send=new Button("Рассчитать");
  panel.add(new Label("Покупатель"));panel.add(name);panel.add(new Label("Количество"));panel.add(quantity);panel.add(delivery);panel.add(send);panel.add(result);RootPanel.get("app").add(panel);
  final OrderServiceAsync service=GWT.create(OrderService.class);
  send.addClickHandler(event->{send.setEnabled(false);result.setText("Выполняется запрос к серверу…");service.calculate(name.getText(),quantity.getText(),delivery.getSelectedValue(),new AsyncCallback<String>(){
   public void onSuccess(String value){result.setText(value);send.setEnabled(true);}
   public void onFailure(Throwable error){result.setText(error instanceof IllegalArgumentException?error.getMessage():"Ошибка связи с сервером. Повторите запрос.");send.setEnabled(true);}
  });});
 }
}
