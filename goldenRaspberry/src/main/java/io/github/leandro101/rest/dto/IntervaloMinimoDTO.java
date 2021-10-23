package io.github.leandro101.rest.dto;

import io.github.leandro101.domain.model.ProdutorIntervalo;

import java.util.List;

public class IntervaloMinimoDTO {
    private List<ProdutorIntervalo> min;
    private List<ProdutorIntervalo> max;

    public IntervaloMinimoDTO(){}

    public IntervaloMinimoDTO(List<ProdutorIntervalo> min, List<ProdutorIntervalo> max) {
        this.min = min;
        this.max = max;
    }

    public List<ProdutorIntervalo> getMin() {
        return min;
    }

    public void setMin(List<ProdutorIntervalo> min) {
        this.min = min;
    }

    public List<ProdutorIntervalo> getMax() {
        return max;
    }

    public void setMax(List<ProdutorIntervalo> max) {
        this.max = max;
    }
}
