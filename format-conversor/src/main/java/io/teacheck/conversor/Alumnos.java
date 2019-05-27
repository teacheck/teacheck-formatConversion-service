package io.teacheck.conversor;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "alumnos")
@XmlAccessorType(XmlAccessType.FIELD)
public class Alumnos {
	
	@XmlElement(name = "alumno")
	private ArrayList<Alumno> alumnos = new ArrayList<>();

	public Alumnos() {}
	
	public ArrayList<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setCatalog(ArrayList<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	@Override
	public String toString() {
		return "Alumnos [alumnos=" + alumnos + "]";
	}
	
}
