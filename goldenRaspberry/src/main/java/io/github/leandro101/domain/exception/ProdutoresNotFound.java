package io.github.leandro101.domain.exception;

public class ProdutoresNotFound extends Exception{

    public ProdutoresNotFound(String mensagem){
        super(mensagem, null);
    }
}
