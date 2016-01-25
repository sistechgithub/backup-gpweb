package com.sth.gp.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pessoa_juridica.
 */
@Entity
@Table(name = "pessoa_juridica")
@Document(indexName = "pessoa_juridica")
public class Pessoa_juridica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 18)
    @Column(name = "cgc", length = 18)
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

    @Column(name = "ds_obs")
    private String obs;

    @OneToOne
    @Column(name="id_pessoa")    
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCgf(String cd_cgf) {
        this.cgf = cd_cgf;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String nm_fantasia) {
        this.fantasia = nm_fantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cd_cnpj) {
        this.cnpj = cd_cnpj;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String ds_responsavel) {
        this.responsavel = ds_responsavel;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String ds_obs) {
        this.obs = ds_obs;
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
        Pessoa_juridica pessoa_juridica = (Pessoa_juridica) o;
        return Objects.equals(id, pessoa_juridica.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pessoa_juridica{" +
            "id=" + id +
            ", cgc='" + cgc + "'" +
            ", cd_cgf='" + cgf + "'" +
            ", nm_fantasia='" + fantasia + "'" +
            ", cd_cnpj='" + cnpj + "'" +
            ", ds_responsavel='" + responsavel + "'" +
            ", ds_obs='" + obs + "'" +
            '}';
    }
}
