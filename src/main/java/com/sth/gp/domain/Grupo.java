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
    
    @Column(name = "fl_desco")
    private Boolean flDesco;

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
    
    @Column(name = "nn_type")
    private Integer nnType;
    
    public Integer getNnDay() {
		return nnDay;
	}

	public void setNnDay(Integer nnDay) {
		this.nnDay = nnDay;
	}

	public String getNmDayweek() {
		return nmDayweek;
	}

	public void setNmDayweek(String nmDayweek) {
		this.nmDayweek = nmDayweek;
	}

	public Integer getNnType() {
		return nnType;
	}

	public void setNnType(Integer nnType) {
		this.nnType = nnType;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNmGrupo() {
        return nmGrupo;
    }

    public void setNmGrupo(String nmGrupo) {
        this.nmGrupo = nmGrupo;
    }

    public BigDecimal getVlComissao() {
        return vlComissao;
    }

    public void setVlComissao(BigDecimal vlComissao) {
        this.vlComissao = vlComissao;
    }

    public BigDecimal getVlDesconto() {
        return vlDesconto;
    }

    public void setVlDesconto(BigDecimal vlDesconto) {
        this.vlDesconto = vlDesconto;
    }

    public Boolean getFlPromo() {
        return flPromo;
    }

    public void setFlPromo(Boolean flPromo) {
        this.flPromo = flPromo;
    }

    public LocalDate getDtPromo() {
        return dtPromo;
    }

    public void setDtPromo(LocalDate dtPromo) {
        this.dtPromo = dtPromo;
    }

    public LocalDate getDtOperacao() {
        return dtOperacao;
    }

    public void setDtOperacao(LocalDate dtOperacao) {
        this.dtOperacao = dtOperacao;
    }

    public Boolean getFlSemcontagem() {
        return flSemcontagem;
    }

    public void setFlSemcontagem(Boolean flSemcontagem) {
        this.flSemcontagem = flSemcontagem;
    }

    public Boolean getFlEnvio() {
        return flEnvio;
    }

    public void setFlEnvio(Boolean flEnvio) {
        this.flEnvio = flEnvio;
    }

    public Integer getNnNovo() {
        return nnNovo;
    }

    public void setNnNovo(Integer nnNovo) {
        this.nnNovo = nnNovo;
    }

    public Boolean getFlDesco() {
		return flDesco;
	}

	public void setFlDesco(Boolean flDesco) {
		this.flDesco = flDesco;
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
            ", nmGrupo='" + nmGrupo + "'" +
            ", vlComissao='" + vlComissao + "'" +
            ", vlDesconto='" + vlDesconto + "'" +
            ", flPromo='" + flPromo + "'" +
            ", flDesco='" + flDesco + "'" +
            ", dtPromo='" + dtPromo + "'" +
            ", dtOperacao='" + dtOperacao + "'" +
            ", flSemcontagem='" + flSemcontagem + "'" +
            ", flEnvio='" + flEnvio + "'" +
            ", nnNovo='" + nnNovo + "'" +
            ", nnType='" + nnType + "'" +
            ", nnDay='" + nnDay + "'" +
            ", nmDayweek='" + nmDayweek + "'" +
            '}';
    }
}
