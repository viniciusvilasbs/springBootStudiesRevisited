package com.mystudies.springboot2essentials.repository;

import com.mystudies.springboot2essentials.domain.Anime;
import com.mystudies.springboot2essentials.util.AnimeCreator;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Log4j2
@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists Anime when Successful.")
    void save_PersistentAnime_WhenSuccessful() {
        log.info("----------- Start SAVE Anime -----------");
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
        log.info("----------- Finish SAVE Anime -----------");
    }

    @Test
    @DisplayName("Save updated Anime when Successful.")
    void save_UpdatesAnime_WhenSuccessful() {
        log.info("----------- Start UPDATE Anime -----------");
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        animeSaved.setName("Overlord");
        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
        log.info("----------- Finish UPDATE Anime -----------");
    }

    @Test
    @DisplayName("Delete removes Anime when Successful.")
    void delete_RemovesAnime_WhenSuccessful() {
        log.info("----------- Start DELETE Anime -----------");
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
        log.info("----------- Finish DELETE Anime -----------");
    }

    @Test
    @DisplayName("Find by name returns a List of Animes when Successful.")
    void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        log.info("----------- Start FIND BY NAME Anime -----------");
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

        Assertions.assertThat(animes)
                .isNotEmpty()
                .contains(animeSaved);

        log.info("----------- Finish FIND BY NAME Anime -----------");
    }

    @Test
    @DisplayName("Find by name returns empty List when no Anime is found.")
    void findByName_ReturnsEmptyList_WhenAnimeNotFound() {
        log.info("----------- Start FIND BY NAME returns EMPTY LIST -----------");
        List<Anime> animes = this.animeRepository.findByName("No_name_test");

        Assertions.assertThat(animes).isEmpty();
        log.info("----------- Finish FIND BY NAME returns EMPTY LIST -----------");
    }

    @Test
    @DisplayName("Save throws ConstraintViolationException when name is empty.")
    void save_throwsConstraintViolationException_WhenNameIsEmpty() {
        log.info("----------- Start SAVE THROWS ConstraintValidationException Anime -----------");
        Anime animeToBeSaved = new Anime();

//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(animeToBeSaved))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                        .isThrownBy(() -> this.animeRepository.save(animeToBeSaved))
                .withMessageContaining("The name cannot be empty nor null!");
        log.info("----------- Finish SAVE THROWS ConstraintViolationException Anime -----------");
    }
}