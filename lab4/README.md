# Лабораторная №4 — рабочие материалы

Локализация приложения расчёта заказа на русский и английский.
Ресурсы: src/main/resources/messages_ru.properties и messages_en.properties.
Переключение: параметр lang=ru/en, ссылки в интерфейсе. Без параметра используется русский.
Форма, результат, ошибки и заголовки локализованы; язык сохраняется при отправке формы и новом расчёте.
Цены остаются в RUB: перевод интерфейса не меняет валюту и расчёт.

Сборка install javadoc:javadoc: BUILD SUCCESS, журнал build.log.
WAR: target/lab4.war. Javadoc: target/site/apidocs/index.html.
Проверки: checks.txt. Переключение языка проверено в браузере.
Адрес: http://127.0.0.1:8081/lab4/order?lang=ru

`nСкриншоты языковых версий: screenshots/ru.png и screenshots/en.png.


Итоговый отчёт: [Лабораторная 4](../output/reports/Лабораторная_4_Серов.docx).
Скриншоты из итогового отчёта: [screenshots/lab4](../screenshots/lab4/).
