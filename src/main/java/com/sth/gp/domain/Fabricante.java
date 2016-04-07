package com.sth.gp.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Fabricante.
 */
@Entity
@Table(name = "fabricante")
@Document(indexName = "fabricante")
public class Fabricante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nm_fabricante", length = 50, nullable = false, unique = true)
    private String nome;

    @Size(min = 14, max = 18)
    @Column(name = "cd_cgc", length = 18)
    private String cnpj;

    @Size(min = 4, max = 18)
    @Column(name = "cd_cgf", length = 18)
    private String ie;

    @Column(name = "nn_numero")
    private String numero;

    @Size(max = 20)
    @Column(name = "cs_complemento", length = 20)
    private String complemento;

    @Size(max = 13)
    @Column(name = "cd_tel", length = 13)
    private String telefone;

    @Size(max = 13)
    @Column(name = "cd_fax", length = 13)
    private String fax;

    @Column(name = "fl_inativo")
    private Boolean inativo;
    
    @Size(max = 35)
    @Column(name = "nm_fantasia", length = 35)
    private String nmFantasia;
    
    @Column(name = "dt_operacao")
    private LocalDate dtOperacao;
    
    @Column(name = "vl_comissao", precision=18, scale=6)
    private BigDecimal vlComissao;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_logradouro", referencedColumnName="id")
    private Logradouro logradouro;

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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Boolean getInativo() {
		return inativo;
	}

	public void setInativo(Boolean inativo) {
		this.inativo = inativo;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public String getNmFantasia() {
		return nmFantasia;
	}

	public void setNmFantasia(String nmFantasia) {
		this.nmFantasia = nmFantasia;
	}

	public LocalDate getDtOperacao() {
		return dtOperacao;
	}

	public void setDtOperacao(LocalDate dtOperacao) {
		this.dtOperacao = dtOperacao;
	}

	public BigDecimal getVlComissao() {
		return vlComissao;
	}

	public void setVlComissao(BigDecimal vlComissao) {
		this.vlComissao = vlComissao;
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
		Fabricante other = (Fabricante) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fabricante [id=" + id + ", nome=" + nome + ", cnpj=" + cnpj + ", ie=" + ie + ", numero=" + numero
				+ ", complemento=" + complemento + ", telefone=" + telefone + ", fax=" + fax + ", inativo=" + inativo
				+ ", nmFantasia=" + nmFantasia + ", dtOperacao=" + dtOperacao + ", vlComissao=" + vlComissao
				+ ", logradouro=" + logradouro + "]";
	}
}