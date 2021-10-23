package io.github.leandro101.domain.repository;

import io.github.leandro101.domain.model.Filme;
import io.github.leandro101.domain.model.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeDAO extends JpaRepository<Filme, Integer> {

    List<Filme> findFilmeByVencedorAndProdutoresOrderByAno(String vencedor, Produtor produtor);

}
