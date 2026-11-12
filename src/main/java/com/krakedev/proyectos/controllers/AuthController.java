package com.krakedev.proyectos.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Usuario;
import com.krakedev.proyectos.security.JwtUtil;
import com.krakedev.proyectos.services.TokenBlacklistService;
import com.krakedev.proyectos.services.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UsuarioService usuarioServicio;

	private final TokenBlacklistService tokenBlacklistService;

	public AuthController(UsuarioService usuarioServicio, TokenBlacklistService tokenBlacklistService) {
		super();
		this.usuarioServicio = usuarioServicio;
		this.tokenBlacklistService = tokenBlacklistService;
	}

	@PostMapping("/registrar")
	public ResponseEntity<?> guardar(@RequestBody Usuario usuario) {
		try {
			return ResponseEntity.ok(usuarioServicio.guardar(usuario));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro al registrar usuario: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
		try {
			String username = credenciales.get("username");
			String password = credenciales.get("password");
			Optional<Usuario> usuarioVerificar = usuarioServicio.autenticar(username, password);
			if (usuarioVerificar.isPresent()) {
				String token = JwtUtil.generarToken(usuarioVerificar.get().getUsername(),
						usuarioVerificar.get().getRol());
				return ResponseEntity.ok(Map.of("Usuario", usuarioVerificar.get().getUsername(), "Rol",
						usuarioVerificar.get().getRol(), "Token", token));

			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro verifique los datos ");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro verifique los datos: " + e.getMessage());
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String autHeader) {
		if (autHeader != null && autHeader.startsWith("Bearer ")) {
			String token = autHeader.substring(7);
			tokenBlacklistService.invalidarToken(token);
			return ResponseEntity.ok(Map.of("Mensaje", "Sesión Cerrada Exitosamente . Token Invalido"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token no proporcionado");
		}
	}

}
