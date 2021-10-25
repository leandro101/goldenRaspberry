package io.github.leandro101.domain.service;

import io.github.leandro101.domain.exception.ProdutoresNotFound;
import io.github.leandro101.domain.model.Filme;
import io.github.leandro101.domain.model.Produtor;
import io.github.leandro101.domain.model.ProdutorIntervalo;
import io.github.leandro101.domain.model.enumeration.TipoIntervalo;
import io.github.leandro101.domain.repository.FilmeDAO;
import io.github.leandro101.domain.repository.ProdutorDAO;
import io.github.leandro101.rest.dto.IntervaloMinimoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutorService {
    @Autowired
    private ProdutorDAO produtorDAO;
    @Autowired
    private FilmeDAO filmeDAO;

    public IntervaloMinimoDTO getIntervalos() throws ProdutoresNotFound {
        return new IntervaloMinimoDTO(this.getIntervalo(TipoIntervalo.MENOR), this.getIntervalo(TipoIntervalo.MAIOR));
    }

    private List<ProdutorIntervalo> getIntervalo(TipoIntervalo tipoIntervalo) throws ProdutoresNotFound {
        List<ProdutorIntervalo> listaRecord = new ArrayList<>();
        List<Produtor> produtores = produtorDAO.findAll();
        this.validaListaProdutoresBanco(produtores);
        for(Produtor produtor : produtores) {
            List<ProdutorIntervalo> intervaloProd = this.getIntervaloByProdutor(produtor, tipoIntervalo);
            if (intervaloProd.isEmpty())
                continue;
            if(tipoIntervalo == TipoIntervalo.MENOR)
                listaRecord = this.atualizaListaRecordMenorInt(listaRecord, intervaloProd);
            else
                listaRecord = this.atualizaListaRecordMaiorInt(listaRecord, intervaloProd);
        }
        return listaRecord;
    }

    private void validaListaProdutoresBanco(List<Produtor> produtores) throws ProdutoresNotFound {
        if(produtores.isEmpty())
            throw new ProdutoresNotFound("LISTA DE PRODUTORES ESTA VAZIA, CARGA NAO EFETUADA.");
    }

    private List<ProdutorIntervalo>  atualizaListaRecordMenorInt (List<ProdutorIntervalo> listaRecord,
                                                                  List<ProdutorIntervalo> intervaloProd){
        if (listaRecord.isEmpty() || listaRecord.get(0).equals(intervaloProd.get(0))) {
            listaRecord.addAll(intervaloProd);
        } else if (listaRecord.get(0).getInterval() > intervaloProd.get(0).getInterval()) {
            listaRecord.clear();
            listaRecord.addAll(intervaloProd);
        }
        return listaRecord;
    }

    private List<ProdutorIntervalo> atualizaListaRecordMaiorInt(List<ProdutorIntervalo> listaRecord,
                                                                List<ProdutorIntervalo> intervaloProd){
        if (listaRecord.isEmpty() || listaRecord.get(0).equals(intervaloProd)) {
            listaRecord.addAll(intervaloProd);
        } else if (listaRecord.get(0).getInterval() < intervaloProd.get(0).getInterval()) {
            listaRecord.clear();
            listaRecord.addAll(intervaloProd);
        }
        return listaRecord;
    }

    private List<ProdutorIntervalo> getIntervaloByProdutor(Produtor produtor, TipoIntervalo tipoIntervalo){
        ProdutorIntervalo prodIntervalo = new ProdutorIntervalo();
        prodIntervalo.setProducer(produtor.getNome());
        List<ProdutorIntervalo> listaProdutor = new ArrayList<>();
        List<Filme> filmes = filmeDAO.findFilmeByVencedorAndProdutoresOrderByAno("yes", produtor);
        if(filmes.size() > 1) {
            filmes.forEach(filme -> {
                if(prodIntervalo.getPreviousWin() == null) {
                    prodIntervalo.setPreviousWin(filme.getAno());
                } else if(prodIntervalo.getFollowingWin() == null) {
                    prodIntervalo.setFollowingWin(filme.getAno());
                    prodIntervalo.setInterval(prodIntervalo.getFollowingWin() - prodIntervalo.getPreviousWin());
                    listaProdutor.add(prodIntervalo);
                }else {
                    if(tipoIntervalo == TipoIntervalo.MENOR)
                        this.atualizaRecordProdutorMenorInt(filme, prodIntervalo, listaProdutor);
                    else
                        this.atualizaRecordProdutorMaiorInt(filme, prodIntervalo, listaProdutor);
                }
            });
        }
        return listaProdutor;
    }

    private void atualizaRecordProdutorMenorInt(Filme filme, ProdutorIntervalo produtorIntervalo,
                                                List<ProdutorIntervalo> listaRecords){
        if ((filme.getAno() - produtorIntervalo.getFollowingWin()) == produtorIntervalo.getInterval()) {
            updateRecord(filme, produtorIntervalo, listaRecords);
        }else if ((filme.getAno() - produtorIntervalo.getFollowingWin()) < produtorIntervalo.getInterval()) {
            listaRecords.clear();
            produtorIntervalo.setPreviousWin(produtorIntervalo.getFollowingWin());
            produtorIntervalo.setFollowingWin(filme.getAno());
            produtorIntervalo.setInterval(produtorIntervalo.getFollowingWin() - produtorIntervalo.getPreviousWin());
            listaRecords.add(produtorIntervalo);
        }
    }

    private void updateRecord(Filme filme, ProdutorIntervalo produtorIntervalo, List<ProdutorIntervalo> listaRecords) {
        ProdutorIntervalo novoInt = new ProdutorIntervalo(produtorIntervalo.getProducer(),
                filme.getAno() - produtorIntervalo.getFollowingWin(), produtorIntervalo.getFollowingWin(),
                filme.getAno());
        listaRecords.add(novoInt);
        produtorIntervalo.setProducer(novoInt.getProducer());
        produtorIntervalo.setInterval(novoInt.getInterval());
        produtorIntervalo.setPreviousWin(novoInt.getPreviousWin());
        produtorIntervalo.setFollowingWin(novoInt.getFollowingWin());
    }

    private void atualizaRecordProdutorMaiorInt(Filme filme, ProdutorIntervalo produtorIntervalo,
                                                List<ProdutorIntervalo> listaRecords){
        if ((filme.getAno() - produtorIntervalo.getFollowingWin()) == produtorIntervalo.getInterval()) {
            updateRecord(filme, produtorIntervalo, listaRecords);
        } else if ((filme.getAno() - produtorIntervalo.getFollowingWin()) > produtorIntervalo.getInterval()) {
            listaRecords.clear();
            produtorIntervalo.setPreviousWin(produtorIntervalo.getFollowingWin());
            produtorIntervalo.setFollowingWin(filme.getAno());
            produtorIntervalo.setInterval(produtorIntervalo.getFollowingWin() - produtorIntervalo.getPreviousWin());
            listaRecords.add(produtorIntervalo);
        }
    }

    public void saveProdutor(String produtor){
        if(!produtorDAO.existsByNome(produtor))
            produtorDAO.save(new Produtor(produtor));
    }
}
