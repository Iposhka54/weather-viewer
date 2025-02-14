# Weather Viewer

![Overview](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/5919a763-bea5-4332-9319-a6942143dfdb)

## Overview
Web application for viewing the current weather. The user can register and add one or more locations (cities, villages, other points) to the collection, after which the main page of the application starts displaying the list of locations with their current weather.. The idea is taken from [here](https://zhukovsd.github.io/java-backend-learning-course/Projects/WeatherViewer/)

## Technologies / tools used:

### Backend

![java-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/bc1ab298-7a78-42ec-8813-05b38668310e)
![hibernate-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/071df0a5-79ef-4435-9c98-5a9b2383d420)
![postgresql](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/8922bdba-ad57-4d69-b68c-ec505fff82e0)
![maven-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/159c5f30-83db-49a2-906a-fc92a071eeff)
![opeanweatherapi](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/78bce6ce-0faf-4d08-bf48-cc12cea9cc83)
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Spring_Framework_Logo_2018.svg/1024px-Spring_Framework_Logo_2018.svg.png" width=100>

### Testing

![junit-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/a1a05826-fecb-4b7a-827c-946ffc72da32)
![mockito](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/c405a582-b268-4b82-b3e8-461d77b7f39c)
![h2-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/3e65f8a8-a9a7-44bc-85c8-42d173338c74)

### Frontend

![thymeleaf](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/5c5cda5f-c5d6-42c8-893b-3737e8d04db2)
![html-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/cf73900e-a565-405d-b7dd-cc05f9429c2f)
![css-logo](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/d7d9ecf6-1cfb-4fe1-ba32-dd43d59921a8)

### Deploy

![dockerfile](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/e22a80da-ca5a-438b-a5f5-605393f3208d)
![docker-compose](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/82390fb8-e6d4-4b15-b175-78eead5bc360)


## Database diagram

![diagram](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/06eab789-15ed-4dd5-b29a-70d48b3fd80a)

## Requirements
  + Java 17+
  + Apache Maven
  + Tomcat 10
  + Intellij IDEA
  + Docker
  + OpenWeather Api Key

## Project launch

+ Clone the repository:

   ```
   git clone https://github.com/Iposhka54/weather-viewer.git
   ```
+ Open your cloned repository folder in Intellij IDEA
  
+ Open the console(Alt + F12) and type `docker-compose up -d`
  
  ![Screen-cmd](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/c2db9f1a-7b9e-4762-8fba-ee70cd3f49a7)

+ Go to Database in Intellij IDEA and select PostgreSQL

  ![Screen-database-idea](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/a61c52ad-7ba6-4a92-a98d-7301599b315f)

+ Then set the settings as shown in the picture(login:postgres, password:postgres) and click Apply
  
  ![Screen-settings-postgres](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/e0e064fd-aa5b-4fc5-8c64-acd60cf75188)

+ Copy the sql script from src/main/resources/data

  ![Screen-init-sqript](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/cfbd80c3-81d9-4b42-ad8a-a31a9d8fcb9a)

+ Open Query console and paste the copied script code, then press Ctrl + A then Ctrl + Enter

  ![Screen-open-console](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/12ab6f7d-55b7-4b9c-af74-9018dbf5923f)

+ Open the config.properties file and insert your OpenWeatherAPI key there instead of YOUR_KEY

  ![image](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/b6703691-ac47-4c3c-96b6-64fa8aa551b5)

+ In Intellij IDEA, select Run -> Edit Configuration. In the pop-up window, click "+" and add Tomcat :
   
    ![Add tomcat](https://github.com/VladislavLevchikIsAProger/tennis_scoreboard/assets/153897612/66f677af-ce05-4676-8dc7-09bc8cbf5db5)

+ Then click "Fix" : 

    ![Fix](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/a494e8f2-b579-45df-a006-084c123b3cc9)


+ In the window that pops up, select :

   ![War](https://github.com/VladislavLevchikIsAProger/weather_tracker/assets/153897612/597e661f-fc6c-4658-bb01-4c8d6ffb7bc4)

+ In the Application context leave the following :
   
   ![Application context](https://github.com/VladislavLevchikIsAProger/currency_exchange/assets/153897612/895091c7-dd29-49b9-8edc-c9b5f29cf018)

+ Click Apply and start Tomcat

## Communication
My Telegram - https://t.me/subculture54
