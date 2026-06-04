package com.luisdbb.tarea3AD2024base.modelo;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;

@Entity
@DiscriminatorValue("ARTISTA")
public class Artista extends Persona {

	private String apodo;

	@ManyToMany(mappedBy = "artistas")
	private Set<Numero> numeros;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "artista_especialidad", joinColumns = @JoinColumn(name = "artista_id"), inverseJoinColumns = @JoinColumn(name = "especialidad_id"))
	private Set<Especialidad> especialidades = new HashSet<>();

	public Artista() {

	}

	public Artista(Long id, String nombre, String email, String nacionalidad, String apodo) {
		super(id, nombre, email, nacionalidad);
		this.apodo = apodo;

	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public Set<Numero> getNumeros() {
		return numeros;
	}

	public void setNumeros(Set<Numero> numeros) {
		this.numeros = numeros;
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
