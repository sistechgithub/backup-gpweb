package com.sth.gp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * Pessoa Física.
 */
@Entity
@Table(name = "pessoa_fisica")
@Document(indexName="pessoa_fisica")
public class PessoaFisica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dt_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "cd_rg")
    private String rg;

    @Column(name = "cd_cpf")
    private String cpf;

    @Column(name = "nm_pai")
    private String pai;

    @Column(name = "nm_mae")
    private String mae;

    @Size(max = 11)
    @Column(name = "ds_estcivil", length = 11)
    private String estadoCivil;

    @Column(name = "nm_conjuge")
    private String conjuge;

    @Column(name = "ds_profissao")
    private String profissao;

    @Column(name = "ds_localtrab")
    private String localTrabalho;

    @Column(name = "ds_complemento")
    private String complemento;

    @Column(name = "nm_numero")
    private String numero;

    @Column(name = "ds_apelido")
    private String apelido;

    @Column(name = "ds_obs")
    private String obs;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name="id_pessoa")
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getPai() {
		return pai;
	}

	public void setPai(String pai) {
		this.pai = pai;
	}

	public String getMae() {
		return mae;
	}

	public void setMae(String mae) {
		this.mae = mae;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getConjuge() {
		return conjuge;
	}

	public void setConjuge(String conjuge) {
		this.conjuge = conjuge;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getLocalTrabalho() {
		return localTrabalho;
	}

	public void setLocalTrabalho(String localTrabalho) {
		this.localTrabalho = localTrabalho;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PessoaFisica pessoaFisica = (PessoaFisica) o;

        if ( ! Objects.equals(id, pessoaFisica.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PessoaFisica{" +
                "id=" + id +
                ", cpf='" + cpf + "'" +
                '}';
    }
}
