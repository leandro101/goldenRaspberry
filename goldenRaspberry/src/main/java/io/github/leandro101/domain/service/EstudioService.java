package io.github.leandro101.domain.service;

import io.github.leandro101.domain.model.Estudio;
import io.github.leandro101.domain.repository.EstudioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudioService {
    @Autowired
    private EstudioDAO estudioDAO;

    public void saveEstudio(String nome){
        if(!estudioDAO.existsByNome(nome))
            estudioDAO.save(new Estudio(nome));
    }
}
