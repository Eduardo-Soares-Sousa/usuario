package com.javanauta.usuario.business;

import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dto.EnderecoDto;
import com.javanauta.usuario.business.dto.TelefoneDto;
import com.javanauta.usuario.business.dto.UsuarioDto;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.exceptions.ConflitException;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastructure.repository.EnderecoRepository;
import com.javanauta.usuario.infrastructure.repository.TelefoneRepository;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final TelefoneRepository telefoneRepository;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder PasswordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDto salvaUsuario(UsuarioDto usuarioDto) {
        emailExiste(usuarioDto.getEmail());
        usuarioDto.setSenha(PasswordEncoder.encode(usuarioDto.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDto);

        return usuarioConverter.paraUsuarioDto(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        try {
            if(usuarioRepository.existsByEmail(email)) {
                throw new ConflitException("O email " + email + " já está cadastrado");
            }
        }catch(ConflitException e) {
            throw new ConflitException("Email já cadastrado ", e.getCause());
        }
    }

    public UsuarioDto buscaUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDto(
                    usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email))
            );
        }catch(ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDto atualizaDadosUsuario(UsuarioDto usuarioDto, String token) {
        String email = jwtUtil.extractUsername(token.substring(7));

        usuarioDto.setSenha(usuarioDto.getSenha() != null ? PasswordEncoder.encode(usuarioDto.getSenha()) : null);

        Usuario usuarioEntity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não localizado"));

        Usuario usuario = usuarioConverter.updateUsuario(usuarioDto, usuarioEntity);

        return usuarioConverter.paraUsuarioDto(usuarioRepository.save(usuario));
    }

    public EnderecoDto atualizaEndereco(Long id, EnderecoDto enderecoDto) {
        Endereco entity = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID não encontrado: " + id));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDto, entity);

        return usuarioConverter.paraEnderecoDto(enderecoRepository.save(endereco));
    }

    public TelefoneDto atualizaTelefone(Long id, TelefoneDto telefoneDto) {
        Telefone entity = telefoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID não encontrado: " + id));

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDto, entity);

        return usuarioConverter.paraTelefoneDto(telefoneRepository.save(telefone));
    }

    public EnderecoDto cadastroEndereco(String token, EnderecoDto enderecoDto) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não localizado " + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(enderecoDto, usuario.getId());

        return usuarioConverter.paraEnderecoDto(enderecoRepository.save(endereco));
    }

    public TelefoneDto cadastroTelefone(String token, TelefoneDto telefoneDto) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não localizado " + email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(telefoneDto, usuario.getId());

        return usuarioConverter.paraTelefoneDto(telefoneRepository.save(telefone));
    }
}
