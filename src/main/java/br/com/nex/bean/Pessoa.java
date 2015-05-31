package br.com.nex.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Pessoa")
public class Pessoa implements Serializable {
 
	@Id
	@GeneratedValue
	private Integer id;
	 
	private String nome;
	 
	private String cpf;
	
	private Double valorSalario;

	public Pessoa(Integer id, String nome, String cpf, Double valorSalario) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.valorSalario = valorSalario;
	}

	public Pessoa() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getValorSalario() {
		return valorSalario;
	}

	public void setValorSalario(Double valorSalario) {
		this.valorSalario = valorSalario;
	}
	 
}
 
