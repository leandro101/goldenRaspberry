package io.github.leandro101.domain.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PRODUTOR")
public class Produtor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    @ManyToMany(mappedBy = "produtores")
    private List<Filme> filmes;

    public Produtor(){}

    public Produtor(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }
}
