package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class Espectaculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String nombre;

	private LocalDate fechaInicio;

	private LocalDate fechaFin;

	@ManyToOne
	private Coordinacion coordinador;

	/*
	 * @OneToMany(mappedBy = "espectaculo", cascade = CascadeType.ALL, orphanRemoval
	 * = true // si eliminas numero lista se // elimina bbdd )
	 * 
	 * @OrderBy("orden ASC") private List<Numero> numeros = new ArrayList<>();
	 */
	@OneToMany(mappedBy = "espectaculo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Numero> numeros = new HashSet<>();

	public Espectaculo() {

	}

	public Espectaculo(Long id, String nombre, LocalDate fechaInicio, LocalDate fechaFin, Coordinacion coordinador,
			Set<Numero> numeros) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.coordinador = coordinador;
		this.numeros = numeros;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Coordinacion getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Coordinacion coordinador) {
		this.coordinador = coordinador;
	}

	public Set<Numero> getNumeros() {
		return numeros;
	}

	public void setNumeros(Set<Numero> numeros) {
		this.numeros = numeros;
	}

}
