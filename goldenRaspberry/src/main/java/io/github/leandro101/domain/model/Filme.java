package io.github.leandro101.domain.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "FILME")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer ano;
    private String titulo;
    private String vencedor;
    @ManyToMany
    private List<Estudio> estudios;
    @ManyToMany
    private List<Produtor> produtores;

    public Filme() {}

    public Filme(Integer ano, String titulo, String vencedor, List<Estudio> estudios, List<Produtor> produtores) {
        this.ano = ano;
        this.titulo = titulo;
        this.vencedor = vencedor;
        this.estudios = estudios;
        this.produtores = produtores;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getVencedor() {
        return vencedor;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }

    public List<Estudio> getEstudios() {
        return estudios;
    }

    public void setEstudios(List<Estudio> estudios) {
        this.estudios = estudios;
    }

    public List<Produtor> getProdutores() {
        return produtores;
    }

    public void setProdutores(List<Produtor> produtores) {
        this.produtores = produtores;
    }
}
