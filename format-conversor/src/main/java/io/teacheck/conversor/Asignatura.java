package io.teacheck.conversor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "asignaturas")
@XmlAccessorType(XmlAccessType.FIELD)
public class Asignatura {
	
	@XmlAttribute(name = "nombre")
	private String nombre;
	
	@XmlElement(name = "asistencia")
	private float asistencia;
	
	@XmlElement(name = "nota")
	private float nota;
	
	@XmlElement(name = "entregable")
	private boolean entregable;
	
	@XmlElement(name = "notaEntregable")
	private float notaEntregable;
	
	@XmlElement(name = "horas")
	private float horas;
	
	@XmlElement(name = "atencionProfesor")
	private byte atencionProfesor;
	
	@XmlElement(name = "motivacionProfesor")
	private byte motivacionProfesor;

	public Asignatura() {}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(float asistencia) {
		this.asistencia = asistencia;
	}

	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}

	public boolean isEntregable() {
		return entregable;
	}

	public void setEntregable(boolean entregable) {
		this.entregable = entregable;
	}

	public float getNotaEntregable() {
		return notaEntregable;
	}

	public void setNotaEntregable(float notaEntregable) {
		this.notaEntregable = notaEntregable;
	}

	public float getHoras() {
		return horas;
	}

	public void setHoras(float horas) {
		this.horas = horas;
	}

	public byte getAtencionProfesor() {
		return atencionProfesor;
	}

	public void setAtencionProfesor(byte atencionProfesor) {
		this.atencionProfesor = atencionProfesor;
	}

	public byte getMotivacionProfesor() {
		return motivacionProfesor;
	}

	public void setMotivacionProfesor(byte motivacionProfesor) {
		this.motivacionProfesor = motivacionProfesor;
	}

	@Override
	public String toString() {
		return "Asignatura [nombre=" + nombre + ", asistencia=" + asistencia + ", nota=" + nota + ", entregable="
				+ entregable + ", notaEntregable=" + notaEntregable + ", horas=" + horas + ", atencionProfesor="
				+ atencionProfesor + ", motivacionProfesor=" + motivacionProfesor + "]";
	}

}
