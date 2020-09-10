package com.annet.common;

import com.annet.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobaExceptionHandler {
    /**
     * 自定义全局异常
     *
     * @param e the e
     * @return R
     */
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public R zdyException(Exception e) {
//        log.error("全局异常信息 ex={}", e.getMessage(), e);
//        return new R<>(e);
//    }

}
