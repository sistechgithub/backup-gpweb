package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.domain.Cidade;
import com.sth.gp.repository.CidadeRepository;
import com.sth.gp.repository.search.CidadeSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CidadeResource REST controller.
 *
 * @see CidadeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CidadeResourceIntTest {

    private static final String DEFAULT_NM_CIDADE = "AAAAA";
    private static final String UPDATED_NM_CIDADE = "BBBBB";

    @Inject
    private CidadeRepository cidadeRepository;

    @Inject
    private CidadeSearchRepository cidadeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCidadeMockMvc;

    private Cidade cidade;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CidadeResource cidadeResource = new CidadeResource();
        ReflectionTestUtils.setField(cidadeResource, "cidadeRepository", cidadeRepository);
        ReflectionTestUtils.setField(cidadeResource, "cidadeSearchRepository", cidadeSearchRepository);
        this.restCidadeMockMvc = MockMvcBuilders.standaloneSetup(cidadeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cidade = new Cidade();
        cidade.setNome(DEFAULT_NM_CIDADE);
    }

    @Test
    @Transactional
    public void createCidade() throws Exception {
        int databaseSizeBeforeCreate = cidadeRepository.findAll().size();

        // Create the Cidade

        restCidadeMockMvc.perform(post("/api/cidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cidade)))
                .andExpect(status().isCreated());

        // Validate the Cidade in the database
        List<Cidade> cidades = cidadeRepository.findAll();
        assertThat(cidades).hasSize(databaseSizeBeforeCreate + 1);
        Cidade testCidade = cidades.get(cidades.size() - 1);
        assertThat(testCidade.getNome()).isEqualTo(DEFAULT_NM_CIDADE);
    }

    @Test
    @Transactional
    public void checkNm_cidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cidadeRepository.findAll().size();
        // set the field null
        cidade.setNome(null);

        // Create the Cidade, which fails.

        restCidadeMockMvc.perform(post("/api/cidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cidade)))
                .andExpect(status().isBadRequest());

        List<Cidade> cidades = cidadeRepository.findAll();
        assertThat(cidades).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCidades() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidades
        restCidadeMockMvc.perform(get("/api/cidades"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cidade.getId().intValue())))
                .andExpect(jsonPath("$.[*].nm_cidade").value(hasItem(DEFAULT_NM_CIDADE.toString())));
    }

    @Test
    @Transactional
    public void getCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get the cidade
        restCidadeMockMvc.perform(get("/api/cidades/{id}", cidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cidade.getId().intValue()))
            .andExpect(jsonPath("$.nm_cidade").value(DEFAULT_NM_CIDADE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCidade() throws Exception {
        // Get the cidade
        restCidadeMockMvc.perform(get("/api/cidades/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

		int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();

        // Update the cidade
        cidade.setNome(UPDATED_NM_CIDADE);

        restCidadeMockMvc.perform(put("/api/cidades")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cidade)))
                .andExpect(status().isOk());

        // Validate the Cidade in the database
        List<Cidade> cidades = cidadeRepository.findAll();
        assertThat(cidades).hasSize(databaseSizeBeforeUpdate);
        Cidade testCidade = cidades.get(cidades.size() - 1);
        assertThat(testCidade.getNome()).isEqualTo(UPDATED_NM_CIDADE);
    }

    @Test
    @Transactional
    public void deleteCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

		int databaseSizeBeforeDelete = cidadeRepository.findAll().size();

        // Get the cidade
        restCidadeMockMvc.perform(delete("/api/cidades/{id}", cidade.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cidade> cidades = cidadeRepository.findAll();
        assertThat(cidades).hasSize(databaseSizeBeforeDelete - 1);
    }
}
