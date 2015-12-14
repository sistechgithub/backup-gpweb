package com.sth.gp.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Estado.
 */
@Entity
@Table(name = "estado")
@Document(indexName = "estado")
public class Estado implements Serializable {

    @Id
    private Long id;

    @NotNull
    @Size(min = 30)
    @Column(name = "nm_estado", nullable = false)
    private String nm_estado;

    @NotNull
    @Size(max = 2)
    @Column(name = "sg_estado", length = 2, nullable = false)
    private String sg_estado;

    @Column(name = "ds_pais")
    private String ds_pais;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNm_estado() {
        return nm_estado;
    }

    public void setNm_estado(String nm_estado) {
        this.nm_estado = nm_estado;
    }

    public String getSg_estado() {
        return sg_estado;
    }

    public void setSg_estado(String sg_estado) {
        this.sg_estado = sg_estado;
    }

    public String getDs_pais() {
        return ds_pais;
    }

    public void setDs_pais(String ds_pais) {
        this.ds_pais = ds_pais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Estado estado = (Estado) o;
        return Objects.equals(id, estado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Estado{" +
            "id=" + id +
            ", nm_estado='" + nm_estado + "'" +
            ", sg_estado='" + sg_estado + "'" +
            ", ds_pais='" + ds_pais + "'" +
            '}';
    }
}
