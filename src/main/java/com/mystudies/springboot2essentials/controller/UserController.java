package com.mystudies.springboot2essentials.controller;

import com.mystudies.springboot2essentials.service.UserInfoDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "users")
public class UserController {

    private final UserInfoDetailsService userInfoDetailsService;

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Returns a User from the Database, when searched by Id.",
            description = "ROLE_ADMIN level required to operate.", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse (responseCode = "400", description = "When User is not found in the Database")
    })
    @GetMapping(path = "admin/{id}")
    public ResponseEntity<UserDetails> findByIdAuthenticationPrincipal(@PathVariable long id,
                                                               @AuthenticationPrincipal UserDetails userDetails) {
//        to check the user credentials (ADMIN or USER, for example)
        log.info(userDetails);
        return ResponseEntity.ok(userInfoDetailsService.findById(id));
    }
}
