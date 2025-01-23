package com.example.board.Common.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//logback객체 만드는 방법 1.
@Slf4j
public class LogController {

//    logback 객체 만드는 방법2.
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/log/test")
    public String logTest(){
//        기본의 system print는 실무에서 사용하지 않음()
//        1. 성능이 좋지않음. , 2. 로그 분류작업이 뷸가
        System.out.println("시스템 프린트 로그");
//        로그 레벨 : trace < debug < info < error
        logger.trace("trace로그입니다.");
        logger.debug("debug로그입니다.");
        logger.info("info로그입니다.");
        logger.error("error로그입니다.");

//        Slf4j어노테이션을 선언시, log라는 변수로 logback객체 사용가능.
        log.info("slf4j info테스트입니다.");
        log.error("slf4j error로그 테스트 입니다.");
//        error 로그는 에러가 터졌을때 사용
        try{
            log.info("에러 테스트 시작");
            log.debug("경수혁 테스트 입니다.");
            throw new RuntimeException("에러 테스트");
        }catch (RuntimeException e) {
            log.error(e.getMessage());
        }
        return "ok";
    }
}
