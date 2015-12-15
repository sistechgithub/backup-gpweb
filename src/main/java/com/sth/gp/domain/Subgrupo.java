package com.sth.gp.domain;

import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

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

    @Column(name = "nm_sub_grupo")
    private String nome;

    @Column(name = "vl_custo", precision=10, scale=2)
    private BigDecimal custo;

    @Column(name = "vl_valor", precision=10, scale=2)
    private BigDecimal valor;

    @Column(name = "dt_operacao")
    private LocalDate dataOperacao;

    @Column(name = "fl_envio")
    private Boolean enviado;

    @Column(name = "nn_novo")
    private Integer novo;

    public Long getId() {
        return id;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getDataOperacao() {
		return dataOperacao;
	}

	public void setDataOperacao(LocalDate dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public Boolean getEnviado() {
		return enviado;
	}

	public void setEnviado(Boolean enviado) {
		this.enviado = enviado;
	}

	public Integer getNovo() {
		return novo;
	}

	public void setNovo(Integer novo) {
		this.novo = novo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custo == null) ? 0 : custo.hashCode());
		result = prime * result + ((dataOperacao == null) ? 0 : dataOperacao.hashCode());
		result = prime * result + ((enviado == null) ? 0 : enviado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((novo == null) ? 0 : novo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		if (custo == null) {
			if (other.custo != null)
				return false;
		} else if (!custo.equals(other.custo))
			return false;
		if (dataOperacao == null) {
			if (other.dataOperacao != null)
				return false;
		} else if (!dataOperacao.equals(other.dataOperacao))
			return false;
		if (enviado == null) {
			if (other.enviado != null)
				return false;
		} else if (!enviado.equals(other.enviado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (novo == null) {
			if (other.novo != null)
				return false;
		} else if (!novo.equals(other.novo))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subgrupo [id=" + id + ", nome=" + nome + ", custo=" + custo + ", valor=" + valor + ", dataOperacao="
				+ dataOperacao + ", enviado=" + enviado + ", novo=" + novo + "]";
	}
}
