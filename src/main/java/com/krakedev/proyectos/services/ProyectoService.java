package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.repositories.ProyectoRepository;

@Service
public class ProyectoService {

	private final ProyectoRepository proyectoRepository;

	public ProyectoService(ProyectoRepository proyectoRepository) {
		this.proyectoRepository = proyectoRepository;
	}

	public Proyecto buscarProyecto(int id) {
		Optional<Proyecto> resultado = proyectoRepository.findById(id);
		return resultado.orElse(null);

	}

	public Proyecto agregarProyecto(Proyecto proyecto) {
		Proyecto comparar = buscarProyecto(proyecto.getId());
		if (comparar == null) {
			return proyectoRepository.save(proyecto);
		} else {
			return null;
		}
	}

	public List<Proyecto> listarProyecto() {
		return proyectoRepository.findAll();

	}
	
	public boolean eliminarProyecto(int id) {
		Proyecto eliminar = buscarProyecto(id);
		if (eliminar == null) {
			return false ;
		}else {
			proyectoRepository.deleteById(id);
			return true ;
		}
	}
	
	public Proyecto modificarProyecto (int id , Proyecto proyecto) {
		Proyecto modificar = buscarProyecto (id);
		if (modificar == null) {
			return null;
		}else {
			modificar.setDescripcion(proyecto.getDescripcion());
			modificar.setNombre(proyecto.getNombre());
			modificar.setFechaInicio(proyecto.getFechaInicio());
			return proyectoRepository.save(modificar);
		}
		
	}
	

}
