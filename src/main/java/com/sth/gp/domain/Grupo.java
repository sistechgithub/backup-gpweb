package com.sth.gp.domain;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Grupo.
 */
@Entity
@Table(name = "grupo")
@Document(indexName = "grupo")
public class Grupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_grupo", unique = true, nullable = false)
    private String nome;

    @Column(name = "vl_comissao", precision=10, scale=2)
    private BigDecimal valorComissao;
    
    @Column(name = "vl_desconto", precision=10, scale=2)
    private BigDecimal desconto;

    @Column(name = "fl_promo")
    private Boolean emPromo;

    @Column(name = "dt_promo")
    private LocalDate dataPromo;

    @Column(name = "dt_operacao")
    private LocalDate dataOperacao;

    @Column(name = "fl_semcontagem")
    private Boolean semSaldo;

    @Column(name = "fl_envio")
    private Boolean enviado;

    @Column(name = "nn_novo")
    private Integer novo;
    
    @Column(name = "nn_day")
    private Integer nomeDia;
    
    @Column(name = "nm_dayweek")
    private String nomeDiaSemana;    

	@Column(name = "nn_type")
    private Integer tipo;
	
	@Column(name = "fl_comdesconto")
	private Boolean comDesconto;

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

	public BigDecimal getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(BigDecimal valorComissao) {
		this.valorComissao = valorComissao;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public Boolean getEmPromo() {
		return emPromo;
	}

	public void setEmPromo(Boolean emPromo) {
		this.emPromo = emPromo;
	}

	public LocalDate getDataPromo() {
		return dataPromo;
	}

	public void setDataPromo(LocalDate dataPromo) {
		this.dataPromo = dataPromo;
	}

	public LocalDate getDataOperacao() {
		return dataOperacao;
	}

	public void setDataOperacao(LocalDate dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	public Boolean getSemSaldo() {
		return semSaldo;
	}

	public void setSemSaldo(Boolean semSaldo) {
		this.semSaldo = semSaldo;
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

	public Integer getNomeDia() {
		return nomeDia;
	}

	public void setNomeDia(Integer nomeDia) {
		this.nomeDia = nomeDia;
	}

	public String getNomeDiaSemana() {
		return nomeDiaSemana;
	}

	public void setNomeDiaSemana(String nomeDiaSemana) {
		this.nomeDiaSemana = nomeDiaSemana;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Boolean getComDesconto() {
		return comDesconto;
	}

	public void setComDesconto(Boolean comDesconto) {
		this.comDesconto = comDesconto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comDesconto == null) ? 0 : comDesconto.hashCode());
		result = prime * result + ((dataOperacao == null) ? 0 : dataOperacao.hashCode());
		result = prime * result + ((dataPromo == null) ? 0 : dataPromo.hashCode());
		result = prime * result + ((desconto == null) ? 0 : desconto.hashCode());
		result = prime * result + ((emPromo == null) ? 0 : emPromo.hashCode());
		result = prime * result + ((enviado == null) ? 0 : enviado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((nomeDia == null) ? 0 : nomeDia.hashCode());
		result = prime * result + ((nomeDiaSemana == null) ? 0 : nomeDiaSemana.hashCode());
		result = prime * result + ((novo == null) ? 0 : novo.hashCode());
		result = prime * result + ((semSaldo == null) ? 0 : semSaldo.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valorComissao == null) ? 0 : valorComissao.hashCode());
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
		Grupo other = (Grupo) obj;
		if (comDesconto == null) {
			if (other.comDesconto != null)
				return false;
		} else if (!comDesconto.equals(other.comDesconto))
			return false;
		if (dataOperacao == null) {
			if (other.dataOperacao != null)
				return false;
		} else if (!dataOperacao.equals(other.dataOperacao))
			return false;
		if (dataPromo == null) {
			if (other.dataPromo != null)
				return false;
		} else if (!dataPromo.equals(other.dataPromo))
			return false;
		if (desconto == null) {
			if (other.desconto != null)
				return false;
		} else if (!desconto.equals(other.desconto))
			return false;
		if (emPromo == null) {
			if (other.emPromo != null)
				return false;
		} else if (!emPromo.equals(other.emPromo))
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
		if (nomeDia == null) {
			if (other.nomeDia != null)
				return false;
		} else if (!nomeDia.equals(other.nomeDia))
			return false;
		if (nomeDiaSemana == null) {
			if (other.nomeDiaSemana != null)
				return false;
		} else if (!nomeDiaSemana.equals(other.nomeDiaSemana))
			return false;
		if (novo == null) {
			if (other.novo != null)
				return false;
		} else if (!novo.equals(other.novo))
			return false;
		if (semSaldo == null) {
			if (other.semSaldo != null)
				return false;
		} else if (!semSaldo.equals(other.semSaldo))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valorComissao == null) {
			if (other.valorComissao != null)
				return false;
		} else if (!valorComissao.equals(other.valorComissao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Grupo [id=" + id + ", nome=" + nome + ", valorComissao=" + valorComissao + ", desconto=" + desconto
				+ ", emPromo=" + emPromo + ", dataPromo=" + dataPromo + ", dataOperacao=" + dataOperacao + ", semSaldo="
				+ semSaldo + ", enviado=" + enviado + ", novo=" + novo + ", nomeDia=" + nomeDia + ", nomeDiaSemana="
				+ nomeDiaSemana + ", tipo=" + tipo + ", comDesconto=" + comDesconto + "]";
	}


	
	
}
