# Лабораторная №3 — рабочие материалы

Предметная область: расчёт стоимости заказа блокнотов.
HTML-форма: src/main/webapp/index.html.
Сервлет: src/main/java/ru/etu/web/OrderServlet.java.
Конфигурация: src/main/webapp/WEB-INF/web.xml.
Параметры инициализации: unitPrice=250.00, deliveryPrice=300.00.
Динамические параметры: customer, quantity, delivery.
Поддерживаются GET и POST; ошибки ввода возвращают HTTP 400.

Сборка: mvn install javadoc:javadoc — BUILD SUCCESS (build.log).
WAR: target/lab3.war, развёрнут в Tomcat webapps.
Javadoc: target/site/apidocs/index.html.
Приложение: http://127.0.0.1:8081/lab3/
Проверки расчётов и валидации: checks.txt. Форма и её отправка проверены в браузере.
Оформление отчёта отложено по просьбе пользователя.


Итоговый отчёт: [Лабораторная 3](../output/reports/Лабораторная_3_Серов.docx).
Скриншоты из итогового отчёта: [screenshots/lab3](../screenshots/lab3/).
