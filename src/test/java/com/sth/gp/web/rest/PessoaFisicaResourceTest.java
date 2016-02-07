package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.domain.PessoaFisica;
import com.sth.gp.repository.PessoaFisicaRepository;
import com.sth.gp.repository.search.PessoaFisicaSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
 * Test class for the PessoaFisicaResource REST controller.
 *
 * @see PessoaFisicaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PessoaFisicaResourceTest {

    private static final String DEFAULT_CPF = "SAMPLE_TEXT";
    private static final String UPDATED_CPF = "UPDATED_TEXT";

    @Inject
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Inject
    private PessoaFisicaSearchRepository pessoaFisicaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restPessoaFisicaMockMvc;

    private PessoaFisica pessoaFisica;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PessoaFisicaResource pessoaFisicaResource = new PessoaFisicaResource();
        ReflectionTestUtils.setField(pessoaFisicaResource, "pessoaFisicaRepository", pessoaFisicaRepository);
        ReflectionTestUtils.setField(pessoaFisicaResource, "pessoaFisicaSearchRepository", pessoaFisicaSearchRepository);
        this.restPessoaFisicaMockMvc = MockMvcBuilders.standaloneSetup(pessoaFisicaResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pessoaFisica = new PessoaFisica();
        pessoaFisica.setCpf(DEFAULT_CPF);
    }

    @Test
    @Transactional
    public void createPessoaFisica() throws Exception {
        int databaseSizeBeforeCreate = pessoaFisicaRepository.findAll().size();

        // Create the PessoaFisica

        restPessoaFisicaMockMvc.perform(post("/api/pessoaFisicas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pessoaFisica)))
                .andExpect(status().isCreated());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicas = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicas).hasSize(databaseSizeBeforeCreate + 1);
        PessoaFisica testPessoaFisica = pessoaFisicas.get(pessoaFisicas.size() - 1);
        assertThat(testPessoaFisica.getCpf()).isEqualTo(DEFAULT_CPF);
    }

    @Test
    @Transactional
    public void getAllPessoaFisicas() throws Exception {
        // Initialize the database
        pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        // Get all the pessoaFisicas
        restPessoaFisicaMockMvc.perform(get("/api/pessoaFisicas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pessoaFisica.getId().intValue())))
                .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())));
    }

    @Test
    @Transactional
    public void getPessoaFisica() throws Exception {
        // Initialize the database
        pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        // Get the pessoaFisica
        restPessoaFisicaMockMvc.perform(get("/api/pessoaFisicas/{id}", pessoaFisica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pessoaFisica.getId().intValue()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPessoaFisica() throws Exception {
        // Get the pessoaFisica
        restPessoaFisicaMockMvc.perform(get("/api/pessoaFisicas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePessoaFisica() throws Exception {
        // Initialize the database
        pessoaFisicaRepository.saveAndFlush(pessoaFisica);

		int databaseSizeBeforeUpdate = pessoaFisicaRepository.findAll().size();

        // Update the pessoaFisica
        pessoaFisica.setCpf(UPDATED_CPF);
        

        restPessoaFisicaMockMvc.perform(put("/api/pessoaFisicas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pessoaFisica)))
                .andExpect(status().isOk());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicas = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicas).hasSize(databaseSizeBeforeUpdate);
        PessoaFisica testPessoaFisica = pessoaFisicas.get(pessoaFisicas.size() - 1);
        assertThat(testPessoaFisica.getCpf()).isEqualTo(UPDATED_CPF);
    }

    @Test
    @Transactional
    public void deletePessoaFisica() throws Exception {
        // Initialize the database
        pessoaFisicaRepository.saveAndFlush(pessoaFisica);

		int databaseSizeBeforeDelete = pessoaFisicaRepository.findAll().size();

        // Get the pessoaFisica
        restPessoaFisicaMockMvc.perform(delete("/api/pessoaFisicas/{id}", pessoaFisica.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PessoaFisica> pessoaFisicas = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
