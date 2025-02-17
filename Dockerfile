# Используем официальный образ Tomcat 10 с поддержкой нужной версии JDK
FROM tomcat:11-jdk21

# Указываем рабочую директорию в контейнере
WORKDIR /usr/local/tomcat

# Удаляем стандартное приложение, чтобы не конфликтовать с вашим
RUN rm -rf webapps/*

# Копируем собранный WAR-файл в каталог для развертывания Tomcat
# Если вы хотите, чтобы ваше приложение запускалось в корневом контексте, переименуйте WAR в ROOT.war
COPY target/iposhka.war webapps/ROOT.war

# Открываем порт 8080 (порт по умолчанию для Tomcat)
EXPOSE 8080

# Запускаем Tomcat
CMD ["catalina.sh", "run"]