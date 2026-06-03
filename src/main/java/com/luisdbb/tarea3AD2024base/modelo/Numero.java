package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class Numero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	private Double duracion;

	private Integer orden;

	@ManyToOne
	@JoinColumn(name = "espectaculo_id")
	private Espectaculo espectaculo;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "numero_artistas", joinColumns = @JoinColumn(name = "numero_id"), inverseJoinColumns = @JoinColumn(name = "artista_id"))
	private Set<Artista> artistas = new HashSet<>();

	public Numero() {

	}

	public Numero(Long id, String nombre, Double duracion, Integer orden, Espectaculo espectaculo,
			Set<Artista> artistas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.orden = orden;
		this.espectaculo = espectaculo;
		this.artistas = artistas;
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

	public Double getDuracion() {
		return duracion;
	}

	public void setDuracion(Double duracion) {
		this.duracion = duracion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Espectaculo getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(Espectaculo espectaculo) {
		this.espectaculo = espectaculo;
	}

	public Set<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(Set<Artista> artistas) {
		this.artistas = artistas;
	}

}
