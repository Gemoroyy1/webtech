# lab8 — рабочие материалы

GWT 2.12.2: Java-клиент OrderApp компилируется в JavaScript. OrderService/OrderServiceAsync определяют RPC; OrderServiceImpl выполняет расчёт на сервере. Функциональность соответствует lab3: имя, количество, получение, расчёт стоимости и валидация.
Maven install javadoc:javadoc: BUILD SUCCESS, build.log.
WAR: target/lab8.war, развёрнут в Tomcat.
Приложение: http://127.0.0.1:8081/lab8/
Javadoc обеих частей: target/site/apidocs/index.html, просмотрен в браузере по http://127.0.0.1:8081/lab8-docs/index.html.
В браузере проверены RPC-расчёт (3 × 250 + 300 = 1050) и серверная ошибка для количества 0. Снимки: ../screenshots/lab8/01.png и 02.png.
GWT-компилятор запускается Maven отдельным Java-процессом. Дополнительно штатным WebAppCreator создан исходный шаблон в template/, выполнены его сборка WAR и запуск в Tomcat. Проверен RPC-ответ GWT User; снимок ../screenshots/lab8/03.png. Eclipse GWT Plugin и DevMode не использовались; применяется развёртывание WAR в Tomcat.



Итоговый отчёт: [Лабораторная 8](../output/reports/Лабораторная_8_Серов.docx).
Скриншоты из итогового отчёта: [screenshots/lab8](../screenshots/lab8/).
