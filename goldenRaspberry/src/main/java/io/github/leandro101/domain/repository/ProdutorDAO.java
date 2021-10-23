package io.github.leandro101.domain.repository;

import io.github.leandro101.domain.model.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutorDAO extends JpaRepository<Produtor, Integer> {

    boolean existsByNome(String nome);

    Produtor findOneByNome(String nome);
}
