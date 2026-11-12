package com.krakedev.proyectos.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	public Usuario guardar(Usuario usuario) {
		String hash = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
		usuario.setPassword(hash);
		return usuarioRepository.save(usuario);
	}

	public Optional<Usuario> autenticar(String username, String password) {
		Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
		if (usuario.isPresent()) {
			boolean coincide = BCrypt.checkpw(password, usuario.get().getPassword());
			if (coincide) {
				return usuario;
			} else {
				return Optional.empty();
			}
		} else {
			return Optional.empty();
		}

	}

}
