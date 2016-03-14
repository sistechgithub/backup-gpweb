package com.sth.gp.compositekey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.sth.gp.domain.Prazo;


public class PrazoIntervaloPK implements Serializable{
	
	private Long id;
	
    private Prazo prazo_intervalo_id;
    
	private Integer ordemIntervalo;
	
	public PrazoIntervaloPK() {
	}

	public Prazo getPrazo_intervalo_id() {
		return prazo_intervalo_id;
	}

	public void setPrazo_intervalo_id(Prazo prazo_intervalo_id) {
		this.prazo_intervalo_id = prazo_intervalo_id;
	}

	public Integer getOrdemIntervalo() {
		return ordemIntervalo;
	}

	public void setOrdemIntervalo(Integer ordemIntervalo) {
		this.ordemIntervalo = ordemIntervalo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		PrazoIntervaloPK other = (PrazoIntervaloPK) obj;
		if (prazo_intervalo_id == null) {
			if (other.prazo_intervalo_id != null)
				return false;
		} else if (!prazo_intervalo_id.equals(other.prazo_intervalo_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PrazoIntervaloPK [prazo_intervalo_id=" + prazo_intervalo_id + ", ordemIntervalo=" + ordemIntervalo+"]";
	}
	
}
