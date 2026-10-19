# Лабораторная № 2 — рабочие материалы

Создан проект lab2 из org.apache.maven.archetypes:maven-archetype-webapp:1.4.
Сборка mvn install завершена BUILD SUCCESS. WAR: lab2/target/lab2.war.
Архив проверен как ZIP, состав сохранён в war-contents.txt.
WAR скопирован в tools/apache-tomcat-9.0.123/webapps/lab2.war.
В браузере проверено http://127.0.0.1:8081/lab2/ — Hello World!, HTTP 200.
Логи: generate.log, build.log; HTTP-проверка: check.txt.

Повторная сборка из корня проекта:
. ./Лаба1/environment.ps1
mvn -Dmaven.repo.local=tools/m2 -f Лаба2/lab2/pom.xml install




Итоговый отчёт: [Лабораторная 2](../output/reports/Лабораторная_2_Серов.docx).
Скриншоты из итогового отчёта: [screenshots/lab2](../screenshots/lab2/).
