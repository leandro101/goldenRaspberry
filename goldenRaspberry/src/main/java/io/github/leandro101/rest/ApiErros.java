package io.github.leandro101.rest;

import java.util.Arrays;
import java.util.List;

public class ApiErros {

    private List<String> errors;

    public ApiErros(String mensagemErro){
        this.errors = Arrays.asList(mensagemErro);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
