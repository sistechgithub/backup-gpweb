package com.sth.gp.domain;

import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Grupo.
 */
@Entity
@Table(name = "grupo")
@Document(indexName = "grupo")
public class Grupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_grupo")
    private String nm_grupo;

    @Column(name = "vl_comissao", precision=10, scale=2)
    private BigDecimal vl_comissao;

    @Column(name = "fl_desconto")
    private Boolean fl_desconto;

    @Column(name = "fl_promo")
    private Boolean fl_promo;

    @Column(name = "dt_promo")
    private LocalDate dt_promo;

    @Column(name = "dt_operacao")
    private LocalDate dt_operacao;

    @Column(name = "fl_semcontagem")
    private Boolean fl_semcontagem;

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

    public String getNm_grupo() {
        return nm_grupo;
    }

    public void setNm_grupo(String nm_grupo) {
        this.nm_grupo = nm_grupo;
    }

    public BigDecimal getVl_comissao() {
        return vl_comissao;
    }

    public void setVl_comissao(BigDecimal vl_comissao) {
        this.vl_comissao = vl_comissao;
    }

    public Boolean getFl_desconto() {
        return fl_desconto;
    }

    public void setFl_desconto(Boolean fl_desconto) {
        this.fl_desconto = fl_desconto;
    }

    public Boolean getFl_promo() {
        return fl_promo;
    }

    public void setFl_promo(Boolean fl_promo) {
        this.fl_promo = fl_promo;
    }

    public LocalDate getDt_promo() {
        return dt_promo;
    }

    public void setDt_promo(LocalDate dt_promo) {
        this.dt_promo = dt_promo;
    }

    public LocalDate getDt_operacao() {
        return dt_operacao;
    }

    public void setDt_operacao(LocalDate dt_operacao) {
        this.dt_operacao = dt_operacao;
    }

    public Boolean getFl_semcontagem() {
        return fl_semcontagem;
    }

    public void setFl_semcontagem(Boolean fl_semcontagem) {
        this.fl_semcontagem = fl_semcontagem;
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

        Grupo grupo = (Grupo) o;

        if ( ! Objects.equals(id, grupo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Grupo{" +
            "id=" + id +
            ", nm_grupo='" + nm_grupo + "'" +
            ", vl_comissao='" + vl_comissao + "'" +
            ", fl_desconto='" + fl_desconto + "'" +
            ", fl_promo='" + fl_promo + "'" +
            ", dt_promo='" + dt_promo + "'" +
            ", dt_operacao='" + dt_operacao + "'" +
            ", fl_semcontagem='" + fl_semcontagem + "'" +
            ", fl_envio='" + fl_envio + "'" +
            ", nn_novo='" + nn_novo + "'" +
            '}';
    }
}
