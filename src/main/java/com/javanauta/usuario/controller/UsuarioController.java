package com.javanauta.usuario.controller;

import com.javanauta.usuario.business.UsuarioService;
import com.javanauta.usuario.business.dto.EnderecoDto;
import com.javanauta.usuario.business.dto.TelefoneDto;
import com.javanauta.usuario.business.dto.UsuarioDto;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDto> salvaUsuario(@RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDto));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDto usuarioDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDto.getEmail(), usuarioDto.getSenha())
        );

        return jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDto> buscaUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscaUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuario(@PathVariable String email) {
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDto> atualizaDadosUsuario(@RequestBody UsuarioDto usuarioDto,
                                                           @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(usuarioDto, token));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDto> atualizaEndereco(@RequestBody EnderecoDto enderecoDto,
                                                        @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, enderecoDto));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDto> atualizaTelefone(@RequestBody TelefoneDto telefoneDto,
                                                        @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, telefoneDto));
    }
}
