package com.sth.gp.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cidade.
 */
@Entity
@Table(name = "cidade")
@Document(indexName = "cidade")
public class Cidade implements Serializable {

    @Id
    private Long id;

    @NotNull
    @Size(max = 60)
    @Column(name = "nm_cidade", length = 60, nullable = false)
    private String nm_cidade;

    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName="id")
    private Estado id_estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNm_cidade() {
        return nm_cidade;
    }

    public void setNm_cidade(String nm_cidade) {
        this.nm_cidade = nm_cidade;
    }

    public Estado getId_estado() {
        return id_estado;
    }

    public void setId_estado(Estado estado) {
        this.id_estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cidade cidade = (Cidade) o;
        return Objects.equals(id, cidade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cidade{" +
            "id=" + id +
            ", nm_cidade='" + nm_cidade + "'" +
            '}';
    }
}
