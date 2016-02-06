package com.sth.gp.domain;

import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import groovy.transform.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Subgrupo.
 */
@Entity
@Table(name = "sub_grupo")
@Document(indexName = "sub_grupo")
public class Subgrupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_sub_grupo", unique = true, nullable = false)
    private String nmSubGrupo;

    @Column(name = "vl_custo", precision=10, scale=2)
    private BigDecimal vlCusto;

    @Column(name = "vl_valor", precision=10, scale=2)
    private BigDecimal vlValor;

    @Column(name = "dt_operacao")
    private LocalDate dtOperacao;

    @Column(name = "fl_envio")
    private Boolean flEnvio;

    @Column(name = "nn_novo")
    private Integer nnNovo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNmSubGrupo() {
		return nmSubGrupo;
	}

	public void setNmSubGrupo(String nmSubGrupo) {
		this.nmSubGrupo = nmSubGrupo;
	}

	public BigDecimal getVlCusto() {
		return vlCusto;
	}

	public void setVlCusto(BigDecimal vlCusto) {
		this.vlCusto = vlCusto;
	}

	public BigDecimal getVlValor() {
		return vlValor;
	}

	public void setVlValor(BigDecimal vlValor) {
		this.vlValor = vlValor;
	}

	public LocalDate getDtOperacao() {
		return dtOperacao;
	}

	public void setDtOperacao(LocalDate dtOperacao) {
		this.dtOperacao = dtOperacao;
	}

	public Boolean getFlEnvio() {
		return flEnvio;
	}

	public void setFlEnvio(Boolean flEnvio) {
		this.flEnvio = flEnvio;
	}

	public Integer getNnNovo() {
		return nnNovo;
	}

	public void setNnNovo(Integer nnNovo) {
		this.nnNovo = nnNovo;
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
		Subgrupo other = (Subgrupo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subgrupo [id=" + id + ", nmSubGrupo=" + nmSubGrupo + ", vlCusto=" + vlCusto + ", vlValor=" + vlValor
				+ ", dtOperacao=" + dtOperacao + ", flEnvio=" + flEnvio + ", nnNovo=" + nnNovo + "]";
	}
}
