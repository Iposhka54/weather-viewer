# Используем Tomcat 10 с JDK 17 (более стабильный вариант)
FROM tomcat:11-jdk21

# Указываем рабочую директорию
WORKDIR /usr/local/tomcat

# Удаляем стандартное Tomcat-приложение
RUN rm -rf webapps/*

# Копируем WAR-файл в контейнер
COPY target/iposhka.war webapps/ROOT.war

# Открываем порт Tomcat
EXPOSE 8080

# Запускаем Tomcat
CMD ["catalina.sh", "run"]
