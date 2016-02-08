package com.sth.gp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
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

    @Column(name = "nm_pessoa")
    protected String nome;

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
