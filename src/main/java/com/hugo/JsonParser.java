package com.hugo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hugo.entities.TvOrMovie;

public class JsonParser {
    
    private static final Pattern REGEX_ITEMS = Pattern.compile(".*\\[(.+)\\].*");
    private static final Pattern REGEX_ATRIBUTOS_JSON = Pattern.compile("\"(.+?)\":\"(.*?)\"");
    
    public List<TvOrMovie> parse(String json) {

        Matcher matcher = REGEX_ITEMS.matcher(json);
        if (!matcher.find()) {

            throw new IllegalArgumentException("Não encontrou items.");
        }

        String[] items = matcher.group(1).replace("},{", "}},{{").split("\\},\\{");

        Gson gson = new Gson();
        List<TvOrMovie> dados = new ArrayList<>();

        for (String item : items) {
            TvOrMovie tvOrMovie = gson.fromJson(item, TvOrMovie.class);
            dados.add(tvOrMovie);
        }


        return dados;

    }
}
