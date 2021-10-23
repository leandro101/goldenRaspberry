package io.github.leandro101.domain.model;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class ArquivoCsv {

    @CsvBindByName(column = "year")
    private Integer year;
    @CsvBindByName(column = "title")
    private String title;
    @CsvBindAndSplitByName(elementType = String.class, splitOn = ",")
    @CsvBindByName(column = "studios")
    private List<String> studios;
    @CsvBindAndSplitByName(elementType = String.class, splitOn = ",| and ")
    @CsvBindByName(column = "producers")
    private List<String> producers;
    @CsvBindByName(column = "winner")
    private String winner;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getStudios() {
        return studios;
    }

    public void setStudios(List<String> studios) {
        this.studios = studios;
    }

    public List<String> getProducers() {
        return producers;
    }

    public void setProducers(List<String> producers) {
        this.producers = producers;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "ArquivoCsv{" +
                "year=" + year +
                ", title='" + title + '\'' +
                ", studios=" + studios +
                ", producers=" + producers +
                ", winner='" + winner + '\'' +
                '}';
    }
}
