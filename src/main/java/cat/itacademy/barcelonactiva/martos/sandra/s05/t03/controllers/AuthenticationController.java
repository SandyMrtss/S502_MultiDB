package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.controllers;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.request.SignInRequest;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.request.SignUpRequest;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.response.JwtAuthenticationResponse;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diceGame/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}