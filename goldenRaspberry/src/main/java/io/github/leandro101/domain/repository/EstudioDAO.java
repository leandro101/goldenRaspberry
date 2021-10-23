package io.github.leandro101.domain.repository;

import io.github.leandro101.domain.model.Estudio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudioDAO extends JpaRepository<Estudio, Integer> {

    boolean existsByNome(String nome);

    Estudio findOneByNome(String nome);
}
