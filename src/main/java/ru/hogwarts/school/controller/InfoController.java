package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.Stream;

//Создать контроллер InfoController. Добавить в него один эндпоинт GET /port.
@RestController
public class InfoController {
    Logger logger = LoggerFactory.getLogger(InfoController.class);
    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/port")
    public int getServerPortNumber() {
        return serverPort;
    }


//    java -jar school-0.0.1-SNAPSHOT.jar
//    java -jar -Dspring.profiles.active=test school-0.0.1-SNAPSHOT.jar


//Создать эндпоинт (не важно в каком контроллере), который будет возвращать целочисленное значение.
// Это значение вычисляется следующей формулой:int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000) .reduce(0, (a, b) -> a + b );
    @GetMapping("/getIntegerValue")
    public String getIntegerValue() {
        int sumInteger;
        long time = System.currentTimeMillis();
        sumInteger = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long timeNotParallelStream = System.currentTimeMillis() - time;
        logger.info("before use parallelStream:" + timeNotParallelStream);


        time = System.currentTimeMillis();
        sumInteger = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        long timeWithParallelStream = System.currentTimeMillis() - time;
        logger.info("after use parallelStream:" + timeWithParallelStream);

        return "Без использовании паралельных стримов время операции :"+ timeNotParallelStream+
                "мс, при использовании паралельных стримов время :"+ timeWithParallelStream+"мс";

//
//Response body
//Download
//Без использовании паралельных стримов время операции :143мс,
// при использовании паралельных стримов время :446мс
    }

}
