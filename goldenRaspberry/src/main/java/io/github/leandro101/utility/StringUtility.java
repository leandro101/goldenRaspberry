package io.github.leandro101.utility;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class StringUtility {

    public static List<String> removeWhiteSpaceOrLine(List<String> lista){
        List<String> retorno = new ArrayList<>();
        lista.forEach(l -> {
            if(!StringUtils.isEmpty(l.trim()))
                retorno.add(l.trim());
        });
        return retorno;
    }
}
