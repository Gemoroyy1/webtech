# Web-программирование — webtech

Лабораторные работы 1–9. Студент: **Серов И. С.**, группа **4312**. Преподаватель: **Павловский М. Г.**

| № | Каталог | Тема |
|---|---|---|
| 1 | [Лаба1](Лаба1/) | Eclipse, Maven и настройка Tomcat |
| 2 | [Лаба2/lab2](Лаба2/lab2/) | Maven Web Application |
| 3 | [Лаба3](Лаба3/) | Сервлет и обработка заказа |
| 4 | [lab4](lab4/) | Локализация интерфейса |
| 5 | [lab5](lab5/) | JSP |
| 6 | [lab6](lab6/) | Аутентификация, роли и HTTPS |
| 7 | [lab7](lab7/) | Cookie и HTTP-сессии |
| 8 | [lab8](lab8/) | GWT и RPC |
| 9 | [lab9](lab9/) | JUnit и Mockito |

## Отчёты и результаты

Все девять итоговых отчётов в формате **DOCX** находятся в [output/reports](output/reports/). 24 скриншота из этих отчётов сохранены отдельно в [screenshots](screenshots/). Результаты 16 модульных тестов: [lab9/results](lab9/results/). Журналы сборки и проверок находятся в каталогах лабораторных.

## Сборка и запуск

Нужны JDK, Maven и **Apache Tomcat 9** (Servlet API javax.servlet). Работы проверены с JDK 25, Maven 3.9.16 и Tomcat 9.0.123. Maven загружает зависимости при первой сборке. Локальные установки инструментов в репозиторий не включены.

Из корня репозитория выполните сборку нужного проекта:

```powershell
mvn -f "Лаба2/lab2/pom.xml" clean package
mvn -f "Лаба3/pom.xml" clean package
mvn -f lab4/pom.xml clean package
mvn -f lab5/pom.xml clean package
mvn -f lab6/pom.xml clean package
mvn -f lab7/pom.xml clean package
mvn -f lab8/pom.xml clean package
mvn -f lab9/pom.xml clean package
```

Скопируйте полученный `target/labN.war` в каталог `webapps` Tomcat. После запуска сервера приложение доступно по `/labN/`. В выполненных работах основной Tomcat использовал порт 8081; конфигурация сохранена в [Лаба1/server.xml](Лаба1/server.xml). Для открытия в Eclipse импортируйте каталог с `pom.xml` как существующий Maven-проект.

Модульные тесты запускаются без Tomcat:

```powershell
mvn -f lab9/pom.xml test
```

Исходный шаблон GWT сохранён отдельно от приложения lab8:

```powershell
mvn -f lab8/template/pom.xml -Dmaven.test.skip=true package
```

Его WAR — `lab8/template/target/lab8-template.war`, страница после развёртывания — `/lab8-template/Template.html`.

## Проверка lab6

Учебные пользователи, пароли и пароль хранилища сертификата опубликованы в [lab6/ACCESS.md](lab6/ACCESS.md). Согласованная конфигурация и учебное хранилище сертификата находятся в [lab6/config](lab6/config/).

После сборки lab6 подготовьте отдельный экземпляр Tomcat, указав путь к установленному Tomcat 9:

```powershell
python scripts/prepare_lab6.py --tomcat-home "C:\apache-tomcat-9.0.123"
$env:CATALINA_HOME = "C:\apache-tomcat-9.0.123"
$env:CATALINA_BASE = (Resolve-Path .\local\lab6-server).Path
& "$env:CATALINA_HOME\bin\catalina.bat" run
```

Адрес: `https://localhost:8446/lab6/` (HTTP — порт 8086). Сертификат самоподписанный, его открытая часть — [lab6/localhost.crt](lab6/localhost.crt). Проверка с доверием этому сертификату:

```powershell
python lab6/verify.py
```
