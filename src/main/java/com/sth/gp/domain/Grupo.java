package com.sth.gp.domain;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
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

    @Column(name = "nm_grupo", unique = true, nullable = false)
    private String nmGrupo;

    @Column(name = "vl_comissao", precision=10, scale=2)
    private BigDecimal vlComissao;
    
    @Column(name = "vl_desconto", precision=10, scale=2)
    private BigDecimal vlDesconto;

    @Column(name = "fl_promo")
    private Boolean flPromo;

    @Column(name = "dt_promo")
    private LocalDate dtPromo;

    @Column(name = "dt_operacao")
    private LocalDate dtOperacao;

    @Column(name = "fl_semcontagem")
    private Boolean flSemcontagem;

    @Column(name = "fl_envio")
    private Boolean flEnvio;

    @Column(name = "nn_novo")
    private Integer nnNovo;
    
    @Column(name = "nn_day")
    private Integer nnDay;
    
    @Column(name = "nm_dayweek")
    private String nmDayweek;
    
    public Integer getNn_day() {
		return nnDay;
	}

	public void setNn_day(Integer nn_day) {
		this.nnDay = nn_day;
	}

	public String getNm_dayweek() {
		return nmDayweek;
	}

	public void setNm_dayweek(String nm_dayweek) {
		this.nmDayweek = nm_dayweek;
	}

	public Integer getNn_type() {
		return nn_type;
	}

	public void setNn_type(Integer nn_type) {
		this.nn_type = nn_type;
	}

	@Column(name = "nn_type")
    private Integer nn_type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNm_grupo() {
        return nmGrupo;
    }

    public void setNm_grupo(String nm_grupo) {
        this.nmGrupo = nm_grupo;
    }

    public BigDecimal getVl_comissao() {
        return vlComissao;
    }

    public void setVl_comissao(BigDecimal vl_comissao) {
        this.vlComissao = vl_comissao;
    }

    public BigDecimal getVl_desconto() {
        return vlDesconto;
    }

    public void setVl_desconto(BigDecimal vl_desconto) {
        this.vlDesconto = vl_desconto;
    }

    public Boolean getFl_promo() {
        return flPromo;
    }

    public void setFl_promo(Boolean fl_promo) {
        this.flPromo = fl_promo;
    }

    public LocalDate getDt_promo() {
        return dtPromo;
    }

    public void setDt_promo(LocalDate dt_promo) {
        this.dtPromo = dt_promo;
    }

    public LocalDate getDt_operacao() {
        return dtOperacao;
    }

    public void setDt_operacao(LocalDate dt_operacao) {
        this.dtOperacao = dt_operacao;
    }

    public Boolean getFl_semcontagem() {
        return flSemcontagem;
    }

    public void setFl_semcontagem(Boolean fl_semcontagem) {
        this.flSemcontagem = fl_semcontagem;
    }

    public Boolean getFl_envio() {
        return flEnvio;
    }

    public void setFl_envio(Boolean fl_envio) {
        this.flEnvio = fl_envio;
    }

    public Integer getNn_novo() {
        return nnNovo;
    }

    public void setNn_novo(Integer nn_novo) {
        this.nnNovo = nn_novo;
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
            ", nm_grupo='" + nmGrupo + "'" +
            ", vl_comissao='" + vlComissao + "'" +
            ", vl_desconto='" + vlDesconto + "'" +
            ", fl_promo='" + flPromo + "'" +
            ", dt_promo='" + dtPromo + "'" +
            ", dt_operacao='" + dtOperacao + "'" +
            ", fl_semcontagem='" + flSemcontagem + "'" +
            ", fl_envio='" + flEnvio + "'" +
            ", nn_novo='" + nnNovo + "'" +
            ", nn_type='" + nn_type + "'" +
            ", nn_day='" + nnDay + "'" +
            ", nm_dayweek='" + nmDayweek + "'" +
            '}';
    }
}
