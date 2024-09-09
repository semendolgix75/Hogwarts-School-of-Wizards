package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Создать контроллер InfoController. Добавить в него один эндпоинт GET /port.
@RestController
public class InfoController {
    Logger logger =  LoggerFactory.getLogger(InfoController.class);
    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/port")
    public int getServerPortNumber() {
        return serverPort;
    }



//    java -jar school-0.0.1-SNAPSHOT.jar
//    java -jar -Dspring.profiles.active=test school-0.0.1-SNAPSHOT.jar
}
