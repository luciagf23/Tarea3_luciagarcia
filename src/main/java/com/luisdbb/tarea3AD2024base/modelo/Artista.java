package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Set;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

@Entity
@DiscriminatorValue("ARTISTA")
public class Artista extends Persona {

	private String apodo;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Especialidad> especialidades;

	@ManyToMany(mappedBy = "artistas")
	private Set<Numero> numeros;

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

	@Override
	public String toString() {
		return getNombre();
	}

}
