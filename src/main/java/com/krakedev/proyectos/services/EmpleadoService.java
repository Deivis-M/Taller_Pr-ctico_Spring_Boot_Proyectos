package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.repositories.EmpleadoRepository;

@Service
public class EmpleadoService {

	private final EmpleadoRepository empleadoRepository;

	public EmpleadoService(EmpleadoRepository empleadoRepository) {
		this.empleadoRepository = empleadoRepository;
	}

	public Empleado buscarPorId(int id) {
		Optional<Empleado> resultado = empleadoRepository.findById(id);
		return resultado.orElse(null);

	}

	public List<Empleado> listar() {
		return empleadoRepository.findAll();
	}

	public Empleado agregarEmpleado(Empleado empleado) {
		Empleado empleadoAgregar = buscarPorId(empleado.getId());
		if (empleadoAgregar == null) {
			return empleadoRepository.save(empleado);

		} else {
			return null;
		}

	}

	public boolean eliminarEmpleado(int id) {
		Empleado empleadoEliminar = buscarPorId(id);
		if (empleadoEliminar == null) {
			return false;
		} else {
			empleadoRepository.deleteById(id);
			return true;
		}

	}
	
	public Empleado actualizarEmpleado(int id,Empleado empleado) {
		
		Empleado empleadoActualizar = buscarPorId(id);
		if (empleadoActualizar == null) {
			return null;
		} else {
			empleadoActualizar.setNombre(empleado.getNombre());
			empleadoActualizar.setCargo(empleado.getCargo());
			return empleadoRepository.save(empleadoActualizar);
		}
	}

}
