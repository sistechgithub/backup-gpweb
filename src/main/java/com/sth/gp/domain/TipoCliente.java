package com.sth.gp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;

import com.sth.gp.enums.PrecoVenda;

/**
 * TipoCliente
 */
@Entity
@Table(name = "tipo_cliente")
@Document(indexName = "tipo_cliente")
public class TipoCliente implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="ds_tipo_cliente", unique = true, nullable = false)
	private String nome;			
			
	@Column(name = "vl_deconto_max", precision = 18, scale = 6)
	private BigDecimal vlDescontoMax;

	@Column(name = "vl_comissao", precision = 18, scale = 6)
	private BigDecimal vlComissao;

	@Column(name = "vl_meta", precision = 18, scale = 6)
	private BigDecimal vlMeta;
	
	@Column(name = "nm_dia_fatura")
	private Integer diaFatura;

	@Column(name = "fl_preco_venda")
	@Enumerated(EnumType.STRING)
	private PrecoVenda precoVenda;

	@Column(name = "ds_vip")
	private Boolean vip;

	@Column(name = "ds_bloqueado")
	private Boolean bloqueado;	

	@Column(name = "ds_forca_juros")
	private Boolean forcaJuros;

	@Column(name = "fl_dinheiro")
	private Boolean flDinheiro;

	@Column(name = "fl_cartao")
	private Boolean flCartao;

	@Column(name = "fl_cheque")
	private Boolean flCheque;

	@Column(name = "fl_crediario")
	private Boolean flCrediario;

	@Column(name = "fl_bloqueia_prazo")
	private Boolean bloqueiaPrazo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_operacao")
	private Date dataCadastro;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="ds_prazo_conf",
	joinColumns= {@JoinColumn(name = "id_tipo_cliente", referencedColumnName = "id")},
	inverseJoinColumns={@JoinColumn(name = "id_prazo", referencedColumnName = "id")})
	private Set<Prazo> prazos = new HashSet<>();
	
	public Boolean getForcaJuros() {
		return forcaJuros;
	}

	public void setForcaJuros(Boolean forcaJuros) {
		this.forcaJuros = forcaJuros;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getVlDescontoMax() {
		return vlDescontoMax;
	}

	public void setVlDescontoMax(BigDecimal vlDescontoMax) {
		this.vlDescontoMax = vlDescontoMax;
	}

	public BigDecimal getVlComissao() {
		return vlComissao;
	}

	public void setVlComissao(BigDecimal vlComissao) {
		this.vlComissao = vlComissao;
	}

	public Integer getDiaFatura() {
		return diaFatura;
	}

	public void setDiaFatura(Integer diaFatura) {
		this.diaFatura = diaFatura;
	}

	public PrecoVenda getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(PrecoVenda precoVenda) {
		this.precoVenda = precoVenda;
	}

	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Set<Prazo> getPrazos() {
		return prazos;
	}

	public void setPrazos(Set<Prazo> prazos) {
		this.prazos = prazos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getVlMeta() {
		return vlMeta;
	}

	public void setVlMeta(BigDecimal vlMeta) {
		this.vlMeta = vlMeta;
	}

	public Boolean getFlDinheiro() {
		return flDinheiro;
	}

	public void setFlDinheiro(Boolean flDinheiro) {
		this.flDinheiro = flDinheiro;
	}

	public Boolean getFlCartao() {
		return flCartao;
	}

	public void setFlCartao(Boolean flCartao) {
		this.flCartao = flCartao;
	}

	public Boolean getFlCheque() {
		return flCheque;
	}

	public void setFlCheque(Boolean flCheque) {
		this.flCheque = flCheque;
	}

	public Boolean getFlCrediario() {
		return flCrediario;
	}

	public void setFlCrediario(Boolean flCrediario) {
		this.flCrediario = flCrediario;
	}

	public Boolean getBloqueiaPrazo() {
		return bloqueiaPrazo;
	}

	public void setBloqueiaPrazo(Boolean bloqueiaPrazo) {
		this.bloqueiaPrazo = bloqueiaPrazo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoCliente other = (TipoCliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
