package com.sth.gp.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Logradouro.
 */
@Entity
@Table(name = "logradouro")
@Document(indexName = "logradouro")
public class Logradouro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nm_logradouro", nullable = false)
    private String nmLogradouro;

    @Size(max = 9)
    @Column(name = "cd_cep", length = 9)
    private String cdCep;

    @ManyToOne
    @JoinColumn(name = "id_bairro", referencedColumnName="id")
    private Bairro idBairro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNm_logradouro() {
        return nmLogradouro;
    }

    public void setNm_logradouro(String nm_logradouro) {
        this.nmLogradouro = nm_logradouro;
    }

    public String getCd_dep() {
        return cdCep;
    }

    public void setCd_dep(String cd_dep) {
        this.cdCep = cd_dep;
    }

    public Bairro getId_bairro() {
        return idBairro;
    }

    public void setId_bairro(Bairro bairro) {
        this.idBairro = bairro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Logradouro logradouro = (Logradouro) o;
        return Objects.equals(id, logradouro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Logradouro{" +
            "id=" + id +
            ", nm_logradouro='" + nmLogradouro + "'" +
            ", cd_dep='" + cdCep + "'" +
            '}';
    }
}
