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

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.services.EmpleadoService;

@RestController

@RequestMapping("/api/empleados")
public class EmpleadoController {

	private final EmpleadoService servicioEmpleado;

	public EmpleadoController(EmpleadoService servicioEmpleado) {
		this.servicioEmpleado = servicioEmpleado;
	}

	@PostMapping
	public ResponseEntity<?> agregarEmpleado(@RequestBody Empleado empleado) {
		try {
			Empleado comparar = servicioEmpleado.agregarEmpleado(empleado);
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
			return ResponseEntity.ok(servicioEmpleado.listar());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarEmpleado(@PathVariable int id) {
		try {
			Empleado comparar = servicioEmpleado.buscarPorId(id);
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
	public ResponseEntity<?> actualizarEmpleado(@PathVariable int id, @RequestBody Empleado empleado) {
		try {
			Empleado comparar = servicioEmpleado.actualizarEmpleado(id, empleado);
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
	public ResponseEntity<?> eliminarEmpleado(@PathVariable int id) {
		try {
			boolean comparar = servicioEmpleado.eliminarEmpleado(id);
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
