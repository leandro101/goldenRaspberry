package io.github.leandro101.rest.controller;

import io.github.leandro101.domain.exception.ProdutoresNotFound;
import io.github.leandro101.domain.model.Produtor;
import io.github.leandro101.domain.service.ProdutorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produtor")
@Api("API Produtores")
public class ProdutorController {

    @Autowired
    private ProdutorService produtorService;

    @GetMapping(value = "/intervalo/premios")
    @ApiOperation("Obter o produtor com maior e menor intervalo entre dois premios consecutivos.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista com os Produtores com maior e menor intervalo encontrada"),
            @ApiResponse(code = 204, message = "Nenhum produtor encontrado na base de dados")
    })
    public ResponseEntity<Produtor> getIntervalos() throws ProdutoresNotFound {
        return new ResponseEntity(produtorService.getIntervalos(),  HttpStatus.OK);
    }
}
