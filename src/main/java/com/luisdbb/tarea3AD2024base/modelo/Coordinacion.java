package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class Coordinacion extends Persona{

		private boolean senior;

	    private LocalDate fechaSenior;
	    
	    

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
		
		
	    
	    
}
