package com.sth.gp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;


/**
 * Pessoa Jurídica.
 */
@Entity
@Table(name = "pessoa_juridica")
@Document(indexName="pessoa_juridica")
public class PessoaJuridica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 18)
    @Column(name = "cd_cgc", length = 18)
    private String cgc;

    @Size(max = 18)
    @Column(name = "cd_cgf", length = 18)
    private String cgf;

    @Column(name = "nm_fantasia")
    private String fantasia;

    @Size(max = 14)
    @Column(name = "cd_cnpj", length = 14)
    private String cnpj;

    @Column(name = "ds_responsavel")
    private String responsavel;

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getCgc() {
        return cgc;
    }

    public void setCgc(String cgc) {
        this.cgc = cgc;
    }

    public String getCgf() {
        return cgf;
    }

    public void setCgf(String cgf) {
        this.cgf = cgf;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PessoaJuridica pessoaFisica = (PessoaJuridica) o;

        if ( ! Objects.equals(id, pessoaFisica.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PessoaJuridica{" +
                "id=" + id +
                ", cnpj='" + cnpj + "'" +
                '}';
    }
}
