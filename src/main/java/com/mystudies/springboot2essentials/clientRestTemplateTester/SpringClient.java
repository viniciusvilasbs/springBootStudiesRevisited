package com.mystudies.springboot2essentials.clientRestTemplateTester;

import com.mystudies.springboot2essentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.asm.Advice;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity =
                new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 1);
        log.info(entity);
        log.info("--------------------------------------");

        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 1);
        log.info(object);
        log.info("--------------------------------------");

        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));
        log.info("--------------------------------------");

        ResponseEntity<List<Anime>> exchangedAnimes = new RestTemplate().exchange(
                "http://localhost:8080/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        log.info(exchangedAnimes.getBody());
        log.info("--------------------------------------");

//        Commented to avoid SAVING the same object again!
        /*Anime kingdom = Anime.builder().name("Kingdom").build();
        Anime animeSaved = new RestTemplate().postForObject("http://localhost:8080/animes", kingdom, Anime.class);
        log.info("Saved Anime: '{}'", animeSaved);
        log.info("--------------------------------------");*/

//        Commented to avoid SAVING the same object again!
        Anime samuraiChamploo = Anime.builder().name("Samurai Champloo").build();
        ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().exchange(
                "http://localhost:8080/animes",
                HttpMethod.POST,
                new HttpEntity<>(samuraiChamploo, createJsonHeader()),
                Anime.class);
        log.info("Saved Anime: '{}'", samuraiChamplooSaved);
        log.info("--------------------------------------");

        Anime animeToBeUpdated = samuraiChamplooSaved.getBody();
        animeToBeUpdated.setName("Samurai Champloo 2");

        ResponseEntity<Void> samuraiChamplooUpdated = new RestTemplate().exchange(
                "http://localhost:8080/animes",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class);
        log.info("Updated Anime: '{}'", animeToBeUpdated);
        log.info("--------------------------------------");

        ResponseEntity<Void> samuraiChamplooDeleted = new RestTemplate().exchange(
                "http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToBeUpdated.getId());
        log.info("Deleted Anime: '{}'", samuraiChamplooDeleted);
        log.info("--------------------------------------");
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return  httpHeaders;
    }
}
