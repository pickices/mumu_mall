package com.liuxinchi.mumu_mall.exception;

import com.liuxinchi.mumu_mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 拾荒老冰棍
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e){
        log.error("Default Exception",e);
        return ApiRestResponse.error(MumuMallExceptionEnum.SYSTEM_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(MumuMallException.class)
    public Object handleMumuMallException(MumuMallException e){
        log.error("MumuMallException",e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException",e);
        BindingResult result = e.getBindingResult();
        List<String> messages = new ArrayList<>();

        if(result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                messages.add(objectError.getDefaultMessage());
            }
        }

        if(messages.size()==0){
            return ApiRestResponse.error(MumuMallExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse.error(MumuMallExceptionEnum.REQUEST_PARAM_ERROR.getCode(),messages.toString());
    }


}
