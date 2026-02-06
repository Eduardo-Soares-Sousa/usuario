package com.javanauta.usuario.business.converter;

import com.javanauta.usuario.business.dto.EnderecoDto;
import com.javanauta.usuario.business.dto.TelefoneDto;
import com.javanauta.usuario.business.dto.UsuarioDto;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {
    public Usuario paraUsuario(UsuarioDto usuarioDto) {
        return Usuario.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(paraListaEndereco(usuarioDto.getEnderecoDto()))
                .telefones(paraListaTelefone(usuarioDto.getTelefoneDto()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDto> enderecoDto) {
        return enderecoDto.stream().map(this::paraEndereco).toList(); // utilizando Stream
    }

    public Endereco paraEndereco(EnderecoDto enderecoDto) {
        return Endereco.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .cidade(enderecoDto.getCidade())
                .complemento(enderecoDto.getComplemento())
                .cep(enderecoDto.getCep())
                .estado(enderecoDto.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDto> telefoneDto) {
        return telefoneDto.stream().map(this::paraTelefone).toList(); // utilizando Stream
    }

    public Telefone paraTelefone(TelefoneDto telefoneDto) {
        return Telefone.builder()
                .numero(telefoneDto.getNumero())
                .ddd(telefoneDto.getDdd())
                .build();
    }

    public UsuarioDto paraUsuarioDto(Usuario usuario) {
        return UsuarioDto.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecoDto(paraListaEnderecoDto(usuario.getEnderecos()))
                .telefoneDto(paraListaTelefoneDto(usuario.getTelefones()))
                .build();
    }

    public List<EnderecoDto> paraListaEnderecoDto(List<Endereco> endereco) {
        return endereco.stream().map(this::paraEnderecoDto).toList(); // utilizando Stream
    }

    public EnderecoDto paraEnderecoDto(Endereco endereco) {
        return EnderecoDto.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    public List<TelefoneDto> paraListaTelefoneDto(List<Telefone> telefone) {
        return telefone.stream().map(this::paraTelefoneDto).toList(); // utilizando Stream
    }

    public TelefoneDto paraTelefoneDto(Telefone telefone) {
        return TelefoneDto.builder()
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }
}
