package com.hugo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.hugo.entities.TvOrMovie;

public class App {
    public static void main(String[] args) throws Exception {

        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {

            Properties props = new Properties();
            props.load(input);

            // Fazer conecão HTTP e buscar top 250 filmes
            String urlTopMovie = props.getProperty("api.url.top.movies");
            String urlPopularMovie = props.getProperty("api.url.popular.movies");
            String urlTopTv = props.getProperty("api.url.top.tv");
            String urlPopularTv = props.getProperty("api.url.popular.tv");

            System.out.println("\n------------- Top Movie -------------\n");
            getFromAPI(urlTopMovie);
            System.out.println("\n------------- Popular Movie -------------\n");
            getFromAPI(urlPopularMovie);
            System.out.println("\n------------- Top Tv -------------\n");
            getFromAPI(urlTopTv);
            System.out.println("\n------------- Popular Tv -------------\n");
            getFromAPI(urlPopularTv);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getFromAPI(String url) throws IOException, InterruptedException {
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair só os dados que interessam (titulo, poster, classificação)
        JsonParser parser = new JsonParser();
        List<TvOrMovie> listaDeFilmes = parser.parse(body);

        // Exibir os dados
        for (TvOrMovie filme : listaDeFilmes) {
            System.out.println("Rank: " + filme.getRank());
            System.out.println("Title: " + filme.getTitle());
            System.out.println("Rarting: " + filme.getImDbRating());
            stars(filme.getImDbRating());
            System.out.println("Image: " + filme.getImage());
            System.out.println();
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
