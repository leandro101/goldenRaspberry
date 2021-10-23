package io.github.leandro101.domain.config;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import io.github.leandro101.domain.model.ArquivoCsv;
import io.github.leandro101.domain.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class ScanFileMovie {

    @Autowired
    private FilmeService filmeService;

    @Bean
    boolean cargaFilmes() throws URISyntaxException, IOException {
        Reader reader = Files
                .newBufferedReader(Paths.get(ClassLoader.getSystemResource("movielist.csv").toURI()));
        CsvToBean<ArquivoCsv> csvToBean = new CsvToBeanBuilder(reader)
                .withSeparator(';')
                .withType(ArquivoCsv.class)
                .build();
        filmeService.salvarFilmesArquivoCsv(csvToBean);
        return true;
    }

}
