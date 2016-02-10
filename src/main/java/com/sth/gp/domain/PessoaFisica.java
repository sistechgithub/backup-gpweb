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
