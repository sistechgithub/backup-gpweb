package com.sth.gp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="prazo")
@Document(indexName="prazo")
public class Prazo implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="ds_prazo", unique = true, nullable = false)
	private String nome;
	
	@Column(name="qt_parcelas")
	private Integer qntParcelas;
	
	@Column(name="fl_entrada")
	private Boolean entrada;
	
	@Column(name="vl_ajuste")
	private BigDecimal ajuste;
	
	@Column(name="vl_minimo")
	private BigDecimal valorMinimo;
	
	@Column(name="nm_dias_intervalo")
	private Integer intervalo;
	
	@Column(name="vl_juros")
	private BigDecimal juros;
	
	@Column(name="conf_intervalo")
	private Boolean intervaloConfigurado;

	@JsonManagedReference
	@OneToMany(mappedBy="prazo_intervalo_id", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<PrazoIntervalo> intervalos = new HashSet<>();

	
	public Boolean getIntervaloConfigurado() {
		return intervaloConfigurado;
	}

	public void setIntervaloConfigurado(Boolean intervaloConfigurado) {
		this.intervaloConfigurado = intervaloConfigurado;
	}

	public Set<PrazoIntervalo> getIntervalos() {
		return intervalos;
	}

	public void setIntervalos(Set<PrazoIntervalo> intervalos) {
		if (this.intervaloConfigurado){
			for(PrazoIntervalo intervalo : intervalos){
				intervalo.setPrazo_intervalo_id(this);
				this.intervalos.add(intervalo);
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQntParcelas() {
		return qntParcelas;
	}

	public void setQntParcelas(Integer qntParcelas) {
		this.qntParcelas = qntParcelas;
	}

	public Boolean getEntrada() {
		return entrada;
	}

	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}

	public BigDecimal getAjuste() {
		return ajuste;
	}

	public void setAjuste(BigDecimal ajuste) {
		this.ajuste = ajuste;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public Integer getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Integer intervalo) {
		this.intervalo = intervalo;
	}

	public BigDecimal getJuros() {
		return juros;
	}

	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Prazo other = (Prazo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	

}
