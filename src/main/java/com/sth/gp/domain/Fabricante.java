package com.sth.gp.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
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
    @Column(name="id_fabricante")
    private Long id;

    @NotNull
    @Size(min = 10, max = 50)
    @Column(name = "nm_fabricante", length = 50, nullable = false)
    private String nm_fabricante;

    @Size(min = 18, max = 18)
    @Column(name = "cd_cgc", length = 18)
    private String cd_cgc;

    @Size(min = 18, max = 18)
    @Column(name = "cd_cgf", length = 18)
    private String cd_cgf;

    @Column(name = "nn_numero")
    private String nn_numero;

    @Size(max = 20)
    @Column(name = "cs_complemento", length = 20)
    private String cs_complemento;

    @Size(max = 13)
    @Column(name = "cd_tel", length = 13)
    private String cd_tel;

    @Size(max = 13)
    @Column(name = "cd_fax", length = 13)
    private String cd_fax;

    @Column(name = "fl_inativo")
    private Boolean fl_inativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNm_fabricante() {
        return nm_fabricante;
    }

    public void setNm_fabricante(String nm_fabricante) {
        this.nm_fabricante = nm_fabricante;
    }

    public String getCd_cgc() {
        return cd_cgc;
    }

    public void setCd_cgc(String cd_cgc) {
        this.cd_cgc = cd_cgc;
    }

    public String getCd_cgf() {
        return cd_cgf;
    }

    public void setCd_cgf(String cd_cgf) {
        this.cd_cgf = cd_cgf;
    }

    public String getNn_numero() {
        return nn_numero;
    }

    public void setNn_numero(String nn_numero) {
        this.nn_numero = nn_numero;
    }

    public String getCs_complemento() {
        return cs_complemento;
    }

    public void setCs_complemento(String cs_complemento) {
        this.cs_complemento = cs_complemento;
    }

    public String getCd_tel() {
        return cd_tel;
    }

    public void setCd_tel(String cd_tel) {
        this.cd_tel = cd_tel;
    }

    public String getCd_fax() {
        return cd_fax;
    }

    public void setCd_fax(String cd_fax) {
        this.cd_fax = cd_fax;
    }

    public Boolean getFl_inativo() {
        return fl_inativo;
    }

    public void setFl_inativo(Boolean fl_inativo) {
        this.fl_inativo = fl_inativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fabricante fabricante = (Fabricante) o;
        return Objects.equals(id, fabricante.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fabricante{" +
            "id=" + id +
            ", nm_fabricante='" + nm_fabricante + "'" +
            ", cd_cgc='" + cd_cgc + "'" +
            ", cd_cgf='" + cd_cgf + "'" +
            ", nn_numero='" + nn_numero + "'" +
            ", cs_complemento='" + cs_complemento + "'" +
            ", cd_tel='" + cd_tel + "'" +
            ", cd_fax='" + cd_fax + "'" +
            ", fl_inativo='" + fl_inativo + "'" +
            '}';
    }
}
