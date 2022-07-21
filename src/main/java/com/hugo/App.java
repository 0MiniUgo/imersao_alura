package com.hugo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.hugo.services.TvOrMovieService;

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
            TvOrMovieService.getFromAPI(urlTopMovie);
            System.out.println("\n------------- Popular Movie -------------\n");
            TvOrMovieService.getFromAPI(urlPopularMovie);
            System.out.println("\n------------- Top Tv -------------\n");
            TvOrMovieService.getFromAPI(urlTopTv);
            System.out.println("\n------------- Popular Tv -------------\n");
            TvOrMovieService.getFromAPI(urlPopularTv);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
}
