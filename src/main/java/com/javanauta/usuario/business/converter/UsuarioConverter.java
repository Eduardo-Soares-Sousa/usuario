package com.javanauta.usuario.business.converter;

import com.javanauta.usuario.business.dto.EnderecoDto;
import com.javanauta.usuario.business.dto.TelefoneDto;
import com.javanauta.usuario.business.dto.UsuarioDto;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioConverter {
    public Usuario paraUsuario(UsuarioDto usuarioDto) {
        return Usuario.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(usuarioDto.getEnderecoDto() != null ? paraListaEndereco(usuarioDto.getEnderecoDto()) : null)
                .telefones(usuarioDto.getTelefoneDto() != null ? paraListaTelefone(usuarioDto.getTelefoneDto()) : null)
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDto> enderecoDto) {
        return enderecoDto.stream().map(this::paraEndereco).toList(); // utilizando Stream
    }

    public Endereco paraEndereco(EnderecoDto enderecoDto) {
        return Endereco.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .complemento(enderecoDto.getComplemento())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .cep(enderecoDto.getCep())
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
                .enderecoDto(usuario.getEnderecos() != null ? paraListaEnderecoDto(usuario.getEnderecos()) : null)
                .telefoneDto(usuario.getTelefones() != null ? paraListaTelefoneDto(usuario.getTelefones()): null)
                .build();
    }

    public List<EnderecoDto> paraListaEnderecoDto(List<Endereco> endereco) {
        return endereco.stream().map(this::paraEnderecoDto).toList(); // utilizando Stream
    }

    public EnderecoDto paraEnderecoDto(Endereco endereco) {
        return EnderecoDto.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    public List<TelefoneDto> paraListaTelefoneDto(List<Telefone> telefone) {
        return telefone.stream().map(this::paraTelefoneDto).toList(); // utilizando Stream
    }

    public TelefoneDto paraTelefoneDto(Telefone telefone) {
        return TelefoneDto.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDto usuarioDto, Usuario usuario) {
        return Usuario.builder()
                .nome(usuarioDto.getNome() != null ? usuarioDto.getNome() : usuario.getNome())
                .id(usuario.getId())
                .senha(usuarioDto.getSenha() != null ? usuarioDto.getSenha() : usuario.getSenha())
                .enderecos(usuario.getEnderecos())
                .telefones(usuario.getTelefones())
                .email(usuarioDto.getEmail() != null ? usuarioDto.getEmail() : usuario.getEmail())
                .build();
    }

    public Endereco updateEndereco(EnderecoDto enderecoDto, Endereco endereco) {
        return Endereco.builder()
                .id(endereco.getId())
                .rua(enderecoDto.getRua() != null ? enderecoDto.getRua() : endereco.getRua())
                .numero(enderecoDto.getNumero() != null ? enderecoDto.getNumero() : endereco.getNumero())
                .cidade(enderecoDto.getCidade() != null ? enderecoDto.getCidade() : endereco.getCidade())
                .cep(enderecoDto.getCep() != null ? enderecoDto.getCep() : endereco.getCep())
                .complemento(enderecoDto.getComplemento() != null ? enderecoDto.getComplemento() : endereco.getComplemento())
                .estado(endereco.getEstado() != null ? enderecoDto.getEstado() : endereco.getEstado())
                .build();
    }

    public Telefone updateTelefone(TelefoneDto telefoneDto, Telefone telefone) {
        return Telefone.builder()
                .id(telefone.getId())
                .ddd(telefoneDto.getDdd() != null ? telefoneDto.getDdd() : telefone.getDdd())
                .numero(telefoneDto.getNumero() != null ? telefoneDto.getNumero() : telefone.getNumero())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDto enderecoDto, Long id) {
        return Endereco.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .complemento(enderecoDto.getComplemento())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .cep(enderecoDto.getCep())
                .usuario_id(id)
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDto telefoneDto, Long id) {
        return Telefone.builder()
                .numero(telefoneDto.getNumero())
                .ddd(telefoneDto.getDdd())
                .usuario_id(id)
                .build();
    }
}
