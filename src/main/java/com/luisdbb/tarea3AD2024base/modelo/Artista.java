package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Artista extends Persona{

	private String apodo;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Especialidad> especialidades;

    public Artista() {
    	
    }
    


	public Artista(Long id, String nombre, String email, String nacionalidad, String apodo,
			Set<Especialidad> especialidades) {
		super(id, nombre, email, nacionalidad);
		this.apodo = apodo;
		this.especialidades = especialidades;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public Set<Especialidad> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}

	
    
}
