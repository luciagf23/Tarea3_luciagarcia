package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("COORDINACION")
public class Coordinacion extends Persona {

	@Column(name = "senior")
	private boolean senior;

	@Column(name = "fecha_senior")
	private LocalDate fechaSenior;
	
	@OneToMany(mappedBy = "coordinador")
	private List<Espectaculo> espectaculos;

	public Coordinacion() {

	}

	public Coordinacion(boolean senior, LocalDate fechaSenior) {
		super();
		this.senior = senior;
		this.fechaSenior = fechaSenior;
	}

	public boolean isSenior() {
		return senior;
	}

	public void setSenior(boolean senior) {
		this.senior = senior;
	}

	public LocalDate getFechaSenior() {
		return fechaSenior;
	}

	public void setFechaSenior(LocalDate fechaSenior) {
		this.fechaSenior = fechaSenior;
	}

	@Override
	public String toString() {
		return getNombre();
	}
	
	

}
