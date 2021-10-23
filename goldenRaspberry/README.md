# API RESTful Golden Raspberry Awards.

API RESTful que retorna os vencedores do premio no maior e menor intervalo.

# Swagger Documentation

```url
http://localhost:8080/swagger-ui.html
```

## Arquivo CSV

O Arquivo de dados CSV deve estar em classpath:movielist.csv.

## Endpoint

```url
@GET http://localhost:8080/api/produtor/intervalo/premios

Obs: Nenhuma autenticação foi implementada.
```

Retorno
```JSON
{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
      "producer": "Matthew Vaughn",
      "interval": 13,
      "previousWin": 2002,
      "followingWin": 2015
    }
  ]
}
```

## Spring Boot

```bash
2.5.4.RELEASE 
```

##Java

```bash
 OpenJDK 15
```


