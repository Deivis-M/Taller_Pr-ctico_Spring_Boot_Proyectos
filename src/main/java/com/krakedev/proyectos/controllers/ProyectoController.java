package com.krakedev.proyectos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.services.ProyectoService;

@RestController

@RequestMapping("/api/proyectos")

public class ProyectoController {

	private final ProyectoService servicioProyecto;

	public ProyectoController(ProyectoService servicioProyecto) {
		this.servicioProyecto = servicioProyecto;
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registrarProyecto(@RequestBody Proyecto proyecto) {
		try {
			Proyecto comparar = servicioProyecto.agregarProyecto(proyecto);
			if (comparar == null) {
				return ResponseEntity.badRequest().build();
			} else {

				return ResponseEntity.ok(comparar);
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> listar() {
		try {
			return ResponseEntity.ok(servicioProyecto.listarProyecto());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarProyectoId(@PathVariable int id) {
		try {
			Proyecto comparar = servicioProyecto.buscarProyecto(id);
			if (comparar == null) {
				return ResponseEntity.notFound().build();
			} else {

				return ResponseEntity.ok(comparar);
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> modificarProyecto(@PathVariable int id, @RequestBody Proyecto proyecto) {
		try {
			Proyecto comparar = servicioProyecto.modificarProyecto(id, proyecto);
			if (comparar == null) {
				return ResponseEntity.notFound().build();
			} else {
				return ResponseEntity.ok(comparar);
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> eliminarProyecto(@PathVariable int id) {
		try {
			boolean comparar = servicioProyecto.eliminarProyecto(id);
			if (comparar) {
				return ResponseEntity.ok(comparar);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

}
