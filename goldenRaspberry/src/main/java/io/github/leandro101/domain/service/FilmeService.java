package io.github.leandro101.domain.service;

import com.opencsv.bean.CsvToBean;
import io.github.leandro101.domain.model.ArquivoCsv;
import io.github.leandro101.domain.model.Estudio;
import io.github.leandro101.domain.model.Filme;
import io.github.leandro101.domain.model.Produtor;
import io.github.leandro101.domain.repository.EstudioDAO;
import io.github.leandro101.domain.repository.FilmeDAO;
import io.github.leandro101.domain.repository.ProdutorDAO;
import io.github.leandro101.utility.StringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmeService {
    @Autowired
    private ProdutorDAO produtorDAO;
    @Autowired
    private EstudioDAO estudioDAO;
    @Autowired
    private FilmeDAO filmeDAO;
    @Autowired
    private ProdutorService produtorService;
    @Autowired
    private EstudioService estudioService;

    public void salvarFilmesArquivoCsv(CsvToBean<ArquivoCsv> csvToBean){
        List<ArquivoCsv> filmes = csvToBean.parse();
        filmes.forEach(f -> {
            f.setStudios(StringUtility.removeWhiteSpaceOrLine(f.getStudios()));
            f.setProducers(StringUtility.removeWhiteSpaceOrLine(f.getProducers()));
        });
        filmes.forEach(a -> {
            List<Produtor> produtores = new ArrayList<>();
            List<Estudio> estudios = new ArrayList<>();
            a.getProducers().forEach(p -> {
                produtorService.saveProdutor(p);
                produtores.add(produtorDAO.findOneByNome(p));
            });
            a.getStudios().forEach(e -> {
                estudioService.saveEstudio(e);
                estudios.add(estudioDAO.findOneByNome(e));
            });
            filmeDAO.save(new Filme(a.getYear(), a.getTitle(), a.getWinner(), estudios, produtores));
        });
    }
}
