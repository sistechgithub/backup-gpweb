package com.sth.gp.repository.search;

import com.sth.gp.domain.PessoaJuridica;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Pessoa_juridica entity.
 */
public interface Pessoa_juridicaSearchRepository extends ElasticsearchRepository<PessoaJuridica, Long> {
}
