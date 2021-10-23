package io.github.leandro101.domain.service;

import io.github.leandro101.domain.model.Filme;
import io.github.leandro101.domain.model.Produtor;
import io.github.leandro101.domain.model.ProdutorIntervalo;
import io.github.leandro101.domain.model.enumeration.TipoIntervalo;
import io.github.leandro101.domain.exception.ProdutoresNotFound;
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
            ProdutorIntervalo intervaloProd = this.getIntervaloByProdutor(produtor, tipoIntervalo);
            if (intervaloProd.getInterval() == null)
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
                                                                  ProdutorIntervalo intervaloProd){
        if (listaRecord.isEmpty() || listaRecord.get(0).equals(intervaloProd)) {
            listaRecord.add(intervaloProd);
        } else if (listaRecord.get(0).getInterval() > intervaloProd.getInterval()) {
            listaRecord.clear();
            listaRecord.add(intervaloProd);
        }
        return listaRecord;
    }

    private List<ProdutorIntervalo> atualizaListaRecordMaiorInt(List<ProdutorIntervalo> listaRecord,
                                                                ProdutorIntervalo intervaloProd){
        if (listaRecord.isEmpty() || listaRecord.get(0).equals(intervaloProd)) {
            listaRecord.add(intervaloProd);
        } else if (listaRecord.get(0).getInterval() < intervaloProd.getInterval()) {
            listaRecord.clear();
            listaRecord.add(intervaloProd);
        }
        return listaRecord;
    }

    private ProdutorIntervalo getIntervaloByProdutor(Produtor produtor, TipoIntervalo tipoIntervalo){
        ProdutorIntervalo prodIntervalo = new ProdutorIntervalo();
        prodIntervalo.setProducer(produtor.getNome());
        List<Filme> filmes = filmeDAO.findFilmeByVencedorAndProdutoresOrderByAno("yes", produtor);
        if(filmes.size() > 1) {
            filmes.forEach(filme -> {
                if(prodIntervalo.getPreviousWin() == null) {
                    prodIntervalo.setPreviousWin(filme.getAno());
                } else if(prodIntervalo.getFollowingWin() == null) {
                    prodIntervalo.setFollowingWin(filme.getAno());
                    prodIntervalo.setInterval(prodIntervalo.getFollowingWin() - prodIntervalo.getPreviousWin());
                }else {
                    if(tipoIntervalo == TipoIntervalo.MENOR)
                        this.atualizaRecordProdutorMenorInt(filme, prodIntervalo);
                    else
                        this.atualizaRecordProdutorMaiorInt(filme, prodIntervalo);
                }
            });
        }
        return prodIntervalo;
    }

    private void atualizaRecordProdutorMenorInt(Filme filme, ProdutorIntervalo produtorIntervalo){
        if ((filme.getAno() - produtorIntervalo.getFollowingWin()) < produtorIntervalo.getInterval()) {
            produtorIntervalo.setPreviousWin(produtorIntervalo.getFollowingWin());
            produtorIntervalo.setFollowingWin(filme.getAno());
            produtorIntervalo.setInterval(produtorIntervalo.getFollowingWin() - produtorIntervalo.getPreviousWin());
        }
    }

    private void atualizaRecordProdutorMaiorInt(Filme filme, ProdutorIntervalo produtorIntervalo){
        if ((filme.getAno() - produtorIntervalo.getFollowingWin()) > produtorIntervalo.getInterval()) {
            produtorIntervalo.setPreviousWin(produtorIntervalo.getFollowingWin());
            produtorIntervalo.setFollowingWin(filme.getAno());
            produtorIntervalo.setInterval(produtorIntervalo.getFollowingWin() - produtorIntervalo.getPreviousWin());
        }
    }

    public void saveProdutor(String produtor){
        if(!produtorDAO.existsByNome(produtor))
            produtorDAO.save(new Produtor(produtor));
    }
}
