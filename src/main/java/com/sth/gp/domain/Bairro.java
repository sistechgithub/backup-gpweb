package com.sth.gp.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Bairro.
 */
@Entity
@Table(name = "bairro")
@Document(indexName = "bairro")
public class Bairro implements Serializable {

    @Id
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "nm_bairro", length = 60, nullable = false)
    private String nm_bairro;

    @ManyToOne
    @JoinColumn(name = "id_cidade", referencedColumnName="id")
    private Cidade id_cidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNm_bairro() {
        return nm_bairro;
    }

    public void setNm_bairro(String nm_bairro) {
        this.nm_bairro = nm_bairro;
    }

    public Cidade getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(Cidade cidade) {
        this.id_cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bairro bairro = (Bairro) o;
        return Objects.equals(id, bairro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bairro{" +
            "id=" + id +
            ", nm_bairro='" + nm_bairro + "'" +
            '}';
    }
}
