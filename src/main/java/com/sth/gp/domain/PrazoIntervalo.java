package com.sth.gp.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sth.gp.compositekey.PrazoIntervaloPK;


@Entity
@Table(name = "prazo_intervalo")
@Document(indexName="prazo_intervalo")
@IdClass(value=PrazoIntervaloPK.class)
public class PrazoIntervalo implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JsonBackReference
    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="id_prazo")
    @Id
    private Prazo prazo_intervalo_id;

	@Column(name="nome")
	private String nome;
	
	@Column(name="ordem_intervalo")
    @Id
	private Integer ordemIntervalo;
	
	@Column(name="intervalo")
	private Integer intervalo;

	public Prazo getPrazo_intervalo_id() {
		return prazo_intervalo_id;
	}

	public void setPrazo_intervalo_id(Prazo prazo_intervalo_id) {
		this.prazo_intervalo_id = prazo_intervalo_id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getOrdemIntervalo() {
		return ordemIntervalo;
	}

	public void setOrdemIntervalo(Integer ordemIntervalo) {
		this.ordemIntervalo = ordemIntervalo;
	}

	public Integer getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Integer intervalo) {
		this.intervalo = intervalo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ordemIntervalo == null) ? 0 : ordemIntervalo.hashCode());
		result = prime * result + ((prazo_intervalo_id == null) ? 0 : prazo_intervalo_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrazoIntervalo other = (PrazoIntervalo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ordemIntervalo == null) {
			if (other.ordemIntervalo != null)
				return false;
		} else if (!ordemIntervalo.equals(other.ordemIntervalo))
			return false;
		if (prazo_intervalo_id == null) {
			if (other.prazo_intervalo_id != null)
				return false;
		} else if (!prazo_intervalo_id.equals(other.prazo_intervalo_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PrazoIntervalo [id=" + id + ", prazo_intervalo_id=" + prazo_intervalo_id + ", ordemIntervalo="
				+ ordemIntervalo + "]";
	}

	
	
}
