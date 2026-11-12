package com.krakedev.proyectos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.services.TareaService;

@RestController
@RequestMapping("/api/tareas")

public class TareaController {

	private final TareaService servicioTarea;

	public TareaController(TareaService servicioTarea) {
		this.servicioTarea = servicioTarea;
	}

	@PostMapping
	public ResponseEntity<?> guardarTareas(@RequestBody Tarea tarea) {
		try {
			Tarea tareaGuardar = servicioTarea.agregarTarea(tarea);
			if (tareaGuardar == null) {
				return ResponseEntity.badRequest().build();
			} else {
				return ResponseEntity.ok(tareaGuardar);
			}

		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> listar() {
		try {
			return ResponseEntity.ok(servicioTarea.listar());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable int id) {
		try {
			return ResponseEntity.ok(servicioTarea.buscarTarea(id));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable int id) {
		try {
			return ResponseEntity.ok(servicioTarea.eliminarTarea(id));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
