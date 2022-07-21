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
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // Exibir os dados
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println(filme.get("title"));
            System.out.println(filme.get("image"));
            System.out.println(filme.get("imDbRating"));
            System.out.println();
        }
    }
}
