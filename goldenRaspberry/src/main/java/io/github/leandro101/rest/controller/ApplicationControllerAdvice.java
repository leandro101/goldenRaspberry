package io.github.leandro101.rest.controller;

import io.github.leandro101.domain.exception.ProdutoresNotFound;
import io.github.leandro101.rest.ApiErros;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(ProdutoresNotFound.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiErros handleException(Exception ex){
        String mensagemErro = ex.getMessage();
        return new ApiErros(mensagemErro);
    }
}
