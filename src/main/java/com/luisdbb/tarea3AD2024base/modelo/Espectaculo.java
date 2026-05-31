package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

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

	@OneToMany(mappedBy = "espectaculo", cascade = CascadeType.ALL)
	private List<Numero> numeros;

	public Espectaculo() {

	}

	public Espectaculo(Long id, String nombre, LocalDate fechaInicio, LocalDate fechaFin, Coordinacion coordinador,
			List<Numero> numeros) {
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

	public List<Numero> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<Numero> numeros) {
		this.numeros = numeros;
	}

}
