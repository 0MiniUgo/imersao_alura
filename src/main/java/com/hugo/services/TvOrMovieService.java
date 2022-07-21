package com.hugo.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import com.hugo.entities.TvOrMovie;

public class TvOrMovieService {

    public static void getFromAPI(String url) throws Exception {
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair só os dados que interessam (titulo, poster, classificação)
        JsonParser parser = new JsonParser();
        List<TvOrMovie> listaDeFilmes = parser.parse(body);

        // Exibir os dados
        for (TvOrMovie tvOrMovie : listaDeFilmes) {
            System.out.println("Rank: " + tvOrMovie.getRank());
            System.out.println("Title: " + tvOrMovie.getTitle());
            System.out.println("Rarting: " + tvOrMovie.getImDbRating());
            stars(tvOrMovie.getImDbRating());
            System.out.println("Image: " + tvOrMovie.getImage());
            System.out.println();

            InputStream inputStream = new URL(tvOrMovie.getImage()).openStream();
            String nomeArquivo = tvOrMovie.getTitle() + ".png";
            GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();
            geradora.cria(inputStream, nomeArquivo);
        }
    }

    public static void stars(String rating){

        Double nota = Double.parseDouble(rating);
        String star = "";

        for (int i = 0; i < nota; i++) {
            star += "* ";
        }
        System.out.println("Stars: " + "\u001B[33m" + star + "\u001B[0m");
    }
}
