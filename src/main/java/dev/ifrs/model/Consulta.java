package dev.ifrs.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Consulta extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    public Long id;

    //Atributos
	private String data;
	private String horario;
	private String situacao;
	
	@ManyToOne (cascade= CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Paciente paciente;
	@ManyToOne (cascade= CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Quiropraxista quiro;
	
	//Contrutores
	public Consulta() {};
	public Consulta(String horario, String sit) {
		this.horario = horario;
		this.situacao = sit;
	}
	
	public Consulta(String data, String horario, String sit, Paciente paciente,
			Quiropraxista quiro) {
		this.data = data;
		this.horario = horario;
		this.situacao = sit;
		this.paciente = paciente;
		this.quiro = quiro;
	}
	
	//Getters e Setters
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	public Quiropraxista getQuiro() {
		return quiro;
	}
	public void setQuiro(Quiropraxista quiro) {
		this.quiro = quiro;
	}
    
}
