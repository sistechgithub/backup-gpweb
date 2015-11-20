package com.sth.gp.domain;

import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Subgrupo.
 */
@Entity
@Table(name = "sub_grupo")
@Document(indexName = "sub_grupo")
public class Subgrupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nm_sub_grupo")
    private String nm_sub_grupo;

    @Column(name = "vl_custo", precision=10, scale=2)
    private BigDecimal vl_custo;

    @Column(name = "vl_valor", precision=10, scale=2)
    private BigDecimal vl_valor;

    @Column(name = "dt_operacao")
    private LocalDate dt_operacao;

    @Column(name = "fl_envio")
    private Boolean fl_envio;

    @Column(name = "nn_novo")
    private Integer nn_novo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNm_sub_grupo() {
        return nm_sub_grupo;
    }

    public void setNm_sub_grupo(String nm_sub_grupo) {
        this.nm_sub_grupo = nm_sub_grupo;
    }

    public BigDecimal getVl_custo() {
        return vl_custo;
    }

    public void setVl_custo(BigDecimal vl_custo) {
        this.vl_custo = vl_custo;
    }

    public BigDecimal getVl_valor() {
        return vl_valor;
    }

    public void setVl_valor(BigDecimal vl_valor) {
        this.vl_valor = vl_valor;
    }

    public LocalDate getDt_operacao() {
        return dt_operacao;
    }

    public void setDt_operacao(LocalDate dt_operacao) {
        this.dt_operacao = dt_operacao;
    }

    public Boolean getFl_envio() {
        return fl_envio;
    }

    public void setFl_envio(Boolean fl_envio) {
        this.fl_envio = fl_envio;
    }

    public Integer getNn_novo() {
        return nn_novo;
    }

    public void setNn_novo(Integer nn_novo) {
        this.nn_novo = nn_novo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Subgrupo subgrupo = (Subgrupo) o;

        if ( ! Objects.equals(id, subgrupo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Subgrupo{" +
            "id=" + id +
            ", nm_sub_grupo='" + nm_sub_grupo + "'" +
            ", vl_custo='" + vl_custo + "'" +
            ", vl_valor='" + vl_valor + "'" +
            ", dt_operacao='" + dt_operacao + "'" +
            ", fl_envio='" + fl_envio + "'" +
            ", nn_novo='" + nn_novo + "'" +
            '}';
    }
}
