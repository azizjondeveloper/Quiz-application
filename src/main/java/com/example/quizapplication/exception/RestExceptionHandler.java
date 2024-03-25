package com.example.quizapplication.exception;

import com.example.quizapplication.payload.ApiResult;
import com.example.quizapplication.payload.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(value = InputDataExistsException.class)
    public ResponseEntity<ApiResult<List<ErrorData>>> exceptionHandle(InputDataExistsException ex) {
        ApiResult<List<ErrorData>> apiResult = ApiResult.failResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(apiResult, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = AccountStatusException.class)
    public ResponseEntity<ApiResult<List<ErrorData>>> exceptionHandle(AccountStatusException ex) {
        ApiResult<List<ErrorData>> apiResult =
                ApiResult.failResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(apiResult, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<List<ErrorData>>> exceptionHandle(MethodArgumentNotValidException ex) {
            List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

            List<ErrorData> errorDataList = new ArrayList<>();

            for (FieldError fieldError : fieldErrors)
                errorDataList.add(
                        new ErrorData(fieldError.getDefaultMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                fieldError.getField()));

            ApiResult<List<ErrorData>> apiResult = ApiResult.failResponse(errorDataList);
            return new ResponseEntity<>(apiResult, HttpStatus.BAD_REQUEST);
    }
}
