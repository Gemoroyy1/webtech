# lab9 — рабочие материалы

Модульное тестирование OrderServlet из лабораторной №3. Исходный сервлет скопирован без изменений.
JUnit 4.13.2, Mockito 4.11.0, Maven Surefire 3.5.2.
Тесты: src/test/java/ru/etu/web/OrderServletTest.java.
Mock-объекты: ServletConfig, HttpServletRequest, HttpServletResponse.
Проверяются GET/POST, курьер/самовывоз, границы количества, ошибочные и отсутствующие параметры, экранирование HTML, чтение init-параметров, их влияние на расчёт и отказ при неверной конфигурации.
verify проверяет взаимодействия с Servlet API; InOrder проверяет установку UTF-8 до чтения параметров.

mvn install: BUILD SUCCESS. 16 тестов, 0 ошибок, 0 провалов, 0 пропусков.
Сохранённые отчёты Surefire XML/TXT: results/. Полный журнал: build.log.
Тесты не требуют запуска Tomcat.



Итоговый отчёт: [Лабораторная 9](../output/reports/Лабораторная_9_Серов.docx).
Результаты подтверждены отчётами тестирования в results/.
