package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.domain.Pessoa_juridica;
import com.sth.gp.repository.Pessoa_juridicaRepository;
import com.sth.gp.repository.search.Pessoa_juridicaSearchRepository;

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
 * Test class for the Pessoa_juridicaResource REST controller.
 *
 * @see Pessoa_juridicaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Pessoa_juridicaResourceIntTest {

    private static final String DEFAULT_CD_CGC = "AAAAA";
    private static final String UPDATED_CD_CGC = "BBBBB";
    private static final String DEFAULT_CD_CGF = "AAAAA";
    private static final String UPDATED_CD_CGF = "BBBBB";
    private static final String DEFAULT_NM_FANTASIA = "AAAAA";
    private static final String UPDATED_NM_FANTASIA = "BBBBB";
    private static final String DEFAULT_CD_CNPJ = "AAAAA";
    private static final String UPDATED_CD_CNPJ = "BBBBB";
    private static final String DEFAULT_DS_RESPONSAVEL = "AAAAA";
    private static final String UPDATED_DS_RESPONSAVEL = "BBBBB";
    private static final String DEFAULT_DS_OBS = "AAAAA";
    private static final String UPDATED_DS_OBS = "BBBBB";

    @Inject
    private Pessoa_juridicaRepository pessoa_juridicaRepository;

    @Inject
    private Pessoa_juridicaSearchRepository pessoa_juridicaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPessoa_juridicaMockMvc;

    private Pessoa_juridica pessoa_juridica;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Pessoa_juridicaResource pessoa_juridicaResource = new Pessoa_juridicaResource();
        ReflectionTestUtils.setField(pessoa_juridicaResource, "pessoa_juridicaRepository", pessoa_juridicaRepository);
        ReflectionTestUtils.setField(pessoa_juridicaResource, "pessoa_juridicaSearchRepository", pessoa_juridicaSearchRepository);
        this.restPessoa_juridicaMockMvc = MockMvcBuilders.standaloneSetup(pessoa_juridicaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pessoa_juridica = new Pessoa_juridica();
        pessoa_juridica.setCgc(DEFAULT_CD_CGC);
        pessoa_juridica.setCgf(DEFAULT_CD_CGF);
        pessoa_juridica.setFantasia(DEFAULT_NM_FANTASIA);
        pessoa_juridica.setCnpj(DEFAULT_CD_CNPJ);
        pessoa_juridica.setResponsavel(DEFAULT_DS_RESPONSAVEL);
        pessoa_juridica.setObs(DEFAULT_DS_OBS);
    }

    @Test
    @Transactional
    public void createPessoa_juridica() throws Exception {
        int databaseSizeBeforeCreate = pessoa_juridicaRepository.findAll().size();

        // Create the Pessoa_juridica

        restPessoa_juridicaMockMvc.perform(post("/api/pessoa_juridicas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pessoa_juridica)))
                .andExpect(status().isCreated());

        // Validate the Pessoa_juridica in the database
        List<Pessoa_juridica> pessoa_juridicas = pessoa_juridicaRepository.findAll();
        assertThat(pessoa_juridicas).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa_juridica testPessoa_juridica = pessoa_juridicas.get(pessoa_juridicas.size() - 1);
        assertThat(testPessoa_juridica.getCgc()).isEqualTo(DEFAULT_CD_CGC);
        assertThat(testPessoa_juridica.getCgf()).isEqualTo(DEFAULT_CD_CGF);
        assertThat(testPessoa_juridica.getFantasia()).isEqualTo(DEFAULT_NM_FANTASIA);
        assertThat(testPessoa_juridica.getCnpj()).isEqualTo(DEFAULT_CD_CNPJ);
        assertThat(testPessoa_juridica.getResponsavel()).isEqualTo(DEFAULT_DS_RESPONSAVEL);
        assertThat(testPessoa_juridica.getObs()).isEqualTo(DEFAULT_DS_OBS);
    }

    @Test
    @Transactional
    public void getAllPessoa_juridicas() throws Exception {
        // Initialize the database
        pessoa_juridicaRepository.saveAndFlush(pessoa_juridica);

        // Get all the pessoa_juridicas
        restPessoa_juridicaMockMvc.perform(get("/api/pessoa_juridicas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa_juridica.getId().intValue())))
                .andExpect(jsonPath("$.[*].cd_cgc").value(hasItem(DEFAULT_CD_CGC.toString())))
                .andExpect(jsonPath("$.[*].cd_cgf").value(hasItem(DEFAULT_CD_CGF.toString())))
                .andExpect(jsonPath("$.[*].nm_fantasia").value(hasItem(DEFAULT_NM_FANTASIA.toString())))
                .andExpect(jsonPath("$.[*].cd_cnpj").value(hasItem(DEFAULT_CD_CNPJ.toString())))
                .andExpect(jsonPath("$.[*].ds_responsavel").value(hasItem(DEFAULT_DS_RESPONSAVEL.toString())))
                .andExpect(jsonPath("$.[*].ds_obs").value(hasItem(DEFAULT_DS_OBS.toString())));
    }

    @Test
    @Transactional
    public void getPessoa_juridica() throws Exception {
        // Initialize the database
        pessoa_juridicaRepository.saveAndFlush(pessoa_juridica);

        // Get the pessoa_juridica
        restPessoa_juridicaMockMvc.perform(get("/api/pessoa_juridicas/{id}", pessoa_juridica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pessoa_juridica.getId().intValue()))
            .andExpect(jsonPath("$.cd_cgc").value(DEFAULT_CD_CGC.toString()))
            .andExpect(jsonPath("$.cd_cgf").value(DEFAULT_CD_CGF.toString()))
            .andExpect(jsonPath("$.nm_fantasia").value(DEFAULT_NM_FANTASIA.toString()))
            .andExpect(jsonPath("$.cd_cnpj").value(DEFAULT_CD_CNPJ.toString()))
            .andExpect(jsonPath("$.ds_responsavel").value(DEFAULT_DS_RESPONSAVEL.toString()))
            .andExpect(jsonPath("$.ds_obs").value(DEFAULT_DS_OBS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPessoa_juridica() throws Exception {
        // Get the pessoa_juridica
        restPessoa_juridicaMockMvc.perform(get("/api/pessoa_juridicas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePessoa_juridica() throws Exception {
        // Initialize the database
        pessoa_juridicaRepository.saveAndFlush(pessoa_juridica);

		int databaseSizeBeforeUpdate = pessoa_juridicaRepository.findAll().size();

        // Update the pessoa_juridica
        pessoa_juridica.setCgc(UPDATED_CD_CGC);
        pessoa_juridica.setCgf(UPDATED_CD_CGF);
        pessoa_juridica.setFantasia(UPDATED_NM_FANTASIA);
        pessoa_juridica.setCnpj(UPDATED_CD_CNPJ);
        pessoa_juridica.setResponsavel(UPDATED_DS_RESPONSAVEL);
        pessoa_juridica.setObs(UPDATED_DS_OBS);

        restPessoa_juridicaMockMvc.perform(put("/api/pessoa_juridicas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pessoa_juridica)))
                .andExpect(status().isOk());

        // Validate the Pessoa_juridica in the database
        List<Pessoa_juridica> pessoa_juridicas = pessoa_juridicaRepository.findAll();
        assertThat(pessoa_juridicas).hasSize(databaseSizeBeforeUpdate);
        Pessoa_juridica testPessoa_juridica = pessoa_juridicas.get(pessoa_juridicas.size() - 1);
        assertThat(testPessoa_juridica.getCgc()).isEqualTo(UPDATED_CD_CGC);
        assertThat(testPessoa_juridica.getCgf()).isEqualTo(UPDATED_CD_CGF);
        assertThat(testPessoa_juridica.getFantasia()).isEqualTo(UPDATED_NM_FANTASIA);
        assertThat(testPessoa_juridica.getCnpj()).isEqualTo(UPDATED_CD_CNPJ);
        assertThat(testPessoa_juridica.getResponsavel()).isEqualTo(UPDATED_DS_RESPONSAVEL);
        assertThat(testPessoa_juridica.getObs()).isEqualTo(UPDATED_DS_OBS);
    }

    @Test
    @Transactional
    public void deletePessoa_juridica() throws Exception {
        // Initialize the database
        pessoa_juridicaRepository.saveAndFlush(pessoa_juridica);

		int databaseSizeBeforeDelete = pessoa_juridicaRepository.findAll().size();

        // Get the pessoa_juridica
        restPessoa_juridicaMockMvc.perform(delete("/api/pessoa_juridicas/{id}", pessoa_juridica.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pessoa_juridica> pessoa_juridicas = pessoa_juridicaRepository.findAll();
        assertThat(pessoa_juridicas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
