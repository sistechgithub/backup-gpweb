package com.sth.gp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sth.gp.enums.Situacao;
import com.sth.gp.enums.TipoPessoa;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * Pessoa.
 */
@Entity
@Table(name = "pessoa")
@Document(indexName="pessoa")
@Inheritance(strategy=InheritanceType.JOINED)
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    @Column(name = "nm_pessoa", nullable = false)
    private String nome;

    @Size(max = 13)
    @Column(name = "cd_tel", length = 13)
    private String telefone;

    @Size(max = 13)
    @Column(name = "cd_cel", length = 13)
    private String celular;

    @Size(max = 13)
    @Column(name = "cd_fax", length = 13)
    private String fax;

    @Column(name = "nm_numero")
    private String numero;

    @Column(name = "ds_complemento")
    private String complemento;

    @Column(name = "ds_situacao")
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @Column(name = "tipo_pessoa")
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @Column(name = "ds_email")
    private String email;

    @Column(name = "ds_obs")
    private String obs;

    @Column(name = "ds_historico")
    private String historico;

    @Column(name = "fl_vendedor")
    private Boolean vendedor;

    @Column(name = "fl_inativo")
    private Boolean inativo;

    @Column(name = "fl_usuario")
    private Boolean usuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_cadastro")
    private Date dataCadastro;

    /**
     * TODO Adicionar logradouro
     *
    @ManyToOne
    @JoinColumn(name = "id_logradouro_id")
    private Logradouro logradouro;
     */

    @JsonManagedReference
    @OneToOne(mappedBy = "pessoa", cascade=CascadeType.ALL)
    protected PessoaFisica pessoaFisica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Boolean getVendedor() {
        return vendedor;
    }

    public void setVendedor(Boolean vendedor) {
        this.vendedor = vendedor;
    }

    public Boolean getInativo() {
        return inativo;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Boolean getUsuario() {
        return usuario;
    }

    public void setUsuario(Boolean usuario) {
        this.usuario = usuario;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
        this.pessoaFisica.setPessoa(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pessoa pessoa = (Pessoa) o;

        if ( ! Objects.equals(id, pessoa.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + "'" +
                '}';
    }
}
