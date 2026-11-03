package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.krakedev.proyectos.entidades.Tarea;
import com.krakedev.proyectos.repositories.TareaRepository;

@Service
public class TareaService {

	private final TareaRepository tareaRepository;

	public TareaService(TareaRepository tareaRepository) {
		this.tareaRepository = tareaRepository;
	}

	public Tarea agregarTarea(Tarea tarea) {
		Tarea comparar = buscarTarea(tarea.getId());
		if (comparar == null) {
			return tareaRepository.save(tarea);
		} else {
			return null;
		}

	}

	public Tarea buscarTarea(int id) {
		Optional<Tarea> resultado = tareaRepository.findById(id);
		return resultado.orElse(null);
	}

	public List<Tarea> listar() {
		return tareaRepository.findAll();

	}

	public boolean eliminarTarea(int id) {
		Tarea comparar = buscarTarea(id);
		if (comparar == null) {
			return false;
		} else {
			tareaRepository.deleteById(id);
			return true;
		}

	}

}
