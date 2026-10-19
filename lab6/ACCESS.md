# Учебные данные lab6

Данные для проверки локального учебного приложения из этого репозитория.

| Пользователь | Пароль | Роль | Ожидаемый результат |
|---|---|---|---|
| student | `0cYyENyccQMFh92NQBBxj7Ad` | order-user | HTTP 200 |
| guest | `EIaCdHFvFxgz0EkytKfdrxR9` | guest | HTTP 403 |

Пароль учебного хранилища `localhost.p12`: `78BAXzk66ZuWPyNF5pr017oW`.

Адрес: https://localhost:8446/lab6/. Без входа сервер возвращает HTTP 401.

Файлы `config/tomcat-users.xml`, `config/server.xml`, `config/credentials.json` и `config/localhost.p12` содержат согласованные настройки. Публичный сертификат находится в `localhost.crt`.
