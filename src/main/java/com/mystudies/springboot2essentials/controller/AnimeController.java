package com.mystudies.springboot2essentials.controller;

import com.mystudies.springboot2essentials.domain.Anime;
import com.mystudies.springboot2essentials.requests.AnimePostRequestBody;
import com.mystudies.springboot2essentials.requests.AnimePutRequestBody;
import com.mystudies.springboot2essentials.service.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "animes")
public class AnimeController {

    private final AnimeService animeService;

    @Operation(summary = "List all animes paginated.",
            description = "ROLE_USER level required to operate. The default size is 20, use the parameter " +
                    "size to change the default value. ",
            tags = {"anime"})
    @ApiResponses(value = {
            @ApiResponse (responseCode = "201", description = "Successful operation")
    })
    @GetMapping
    public ResponseEntity<Page<Anime>> listAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(animeService.listAll(pageable));
    }

    @Operation(summary = "List all animes not paginated.", description = "ROLE_USER level required to operate.",
            tags = {"anime"})
    @ApiResponses(value = {
            @ApiResponse (responseCode = "201", description = "Successful operation")
    })
    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAllNonPageable() {
        return ResponseEntity.ok(animeService.listAllNonPageable());
    }

    @Operation(summary = "Returns the required Anime by Id.", description = "ROLE_USER level required to operate.",
            tags = {"anime"})
    @ApiResponses(value = {
            @ApiResponse (responseCode = "204", description = "Successful operation"),
            @ApiResponse (responseCode = "400", description = "When Anime is not found in the Database")
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id) {
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }

    @Operation(summary = "Returns a List of required Animes searched by Name.",
            description = "ROLE_USER level required to operate. Search for the Anime's name on the URI, " +
                    "with Query Parameters -> ex: **?name=hellsing", tags = {"anime"})
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "Successful operation")
    })
    @GetMapping(path = "/findByName")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @Operation(summary = "Persists a new Anime in the Database.", description = "ROLE_ADMIN level required to operate.",
            tags = {"anime"})
    @ApiResponses(value = {
            @ApiResponse (responseCode = "201", description = "Successful operation"),
            @ApiResponse (responseCode = "403", description = "When not a ROLE_ADMIN executes operation.")
    })
    @PostMapping(path = "/admin")
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) {
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @Operation(summary = "Removes an Anime from the Database.", description = "ROLE_ADMIN level required to operate.",
            tags = {"anime"})
    @ApiResponses(value = {
            @ApiResponse (responseCode = "204", description = "Successful operation"),
            @ApiResponse (responseCode = "400", description = "When Anime is not found in the Database"),
            @ApiResponse (responseCode = "403", description = "When not a ROLE_ADMIN executes operation.")
    })
    @DeleteMapping(path = "/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Replace an Anime in the Database.", description = "ROLE_ADMIN level required to operate.",
            tags = {"anime"})
    @ApiResponses(value = {
            @ApiResponse (responseCode = "204", description = "Successful operation"),
            @ApiResponse (responseCode = "400", description = "When Anime is not found in the Database"),
            @ApiResponse (responseCode = "403", description = "When not a ROLE_ADMIN executes operation.")
    })
    @PutMapping(path = "/admin")
    public ResponseEntity<Void> replace(@RequestBody @Valid AnimePutRequestBody animePutRequestBody) {
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
