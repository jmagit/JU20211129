package com.example;

public class Persona {
	private int id;
	private String nombre;
	private String apellidos;
	int edad;
	public Persona(int id, String nombre, String apellidos, int edad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public int getEdad() {
		return edad;
	}
	
	public void cumpleAños() {
		//while(true);
		edad++;
	}
	
	public boolean isJubilado() {
		return edad >= 67;
	}
}
