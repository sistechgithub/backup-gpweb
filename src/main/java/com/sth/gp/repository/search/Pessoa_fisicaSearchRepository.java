package com.sth.gp.repository.search;

import com.sth.gp.domain.PessoaFisica;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Pessoa_fisica entity.
 */
public interface Pessoa_fisicaSearchRepository extends ElasticsearchRepository<PessoaFisica, Long> {
}
