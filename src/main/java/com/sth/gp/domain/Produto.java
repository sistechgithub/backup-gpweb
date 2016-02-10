package com.sth.gp.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
@Document(indexName = "produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nm_produto")
    private String nmProduto;

    @Column(name = "vl_custo", precision=18, scale=6)
    private BigDecimal vlCusto;

    @Column(name = "qt_saldo", precision=18, scale=4)
    private BigDecimal qtSaldo;

    @Column(name = "vl_venda", precision=18, scale=6)
    private BigDecimal vlVenda;

    @ManyToOne
    private Grupo grupo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNmProduto() {
        return nmProduto;
    }

    public void setNmProduto(String nmProduto) {
        this.nmProduto = nmProduto;
    }

    public BigDecimal getVlCusto() {
        return vlCusto;
    }

    public void setVlCusto(BigDecimal vlCusto) {
        this.vlCusto = vlCusto;
    }

    public BigDecimal getQtSaldo() {
        return qtSaldo;
    }

    public void setQtSaldo(BigDecimal qtSaldo) {
        this.qtSaldo = qtSaldo;
    }

    public BigDecimal getVlVenda() {
        return vlVenda;
    }

    public void setVlVenda(BigDecimal vlVenda) {
        this.vlVenda = vlVenda;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Produto produto = (Produto) o;

        if ( ! Objects.equals(id, produto.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Produto{" +
            "id=" + id +
            ", nmProduto='" + nmProduto + "'" +
            ", vlCusto='" + vlCusto + "'" +
            ", qtSaldo='" + qtSaldo + "'" +
            ", vlVenda='" + vlVenda + "'" +
            '}';
    }
}
