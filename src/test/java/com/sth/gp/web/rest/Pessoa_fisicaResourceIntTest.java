package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.domain.Pessoa_fisica;
import com.sth.gp.repository.Pessoa_fisicaRepository;
import com.sth.gp.repository.search.Pessoa_fisicaSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Pessoa_fisicaResource REST controller.
 *
 * @see Pessoa_fisicaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Pessoa_fisicaResourceIntTest {


    private static final LocalDate DEFAULT_DT_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CD_RG = "AAAAA";
    private static final String UPDATED_CD_RG = "BBBBB";
    private static final String DEFAULT_CD_CPF = "AAAAA";
    private static final String UPDATED_CD_CPF = "BBBBB";
    private static final String DEFAULT_NM_PAI = "AAAAA";
    private static final String UPDATED_NM_PAI = "BBBBB";
    private static final String DEFAULT_NM_MAE = "AAAAA";
    private static final String UPDATED_NM_MAE = "BBBBB";
    private static final String DEFAULT_DS_ESTCIVIL = "AAAAA";
    private static final String UPDATED_DS_ESTCIVIL = "BBBBB";
    private static final String DEFAULT_NM_CONJUGE = "AAAAA";
    private static final String UPDATED_NM_CONJUGE = "BBBBB";
    private static final String DEFAULT_DS_PROFISSAO = "AAAAA";
    private static final String UPDATED_DS_PROFISSAO = "BBBBB";
    private static final String DEFAULT_DS_LOCALTRAB = "AAAAA";
    private static final String UPDATED_DS_LOCALTRAB = "BBBBB";
    private static final String DEFAULT_DS_COMPLEMENTO = "AAAAA";
    private static final String UPDATED_DS_COMPLEMENTO = "BBBBB";
    private static final String DEFAULT_DS_APELIDO = "AAAAA";
    private static final String UPDATED_DS_APELIDO = "BBBBB";
    private static final String DEFAULT_DS_OBS = "AAAAA";
    private static final String UPDATED_DS_OBS = "BBBBB";

    @Inject
    private Pessoa_fisicaRepository pessoa_fisicaRepository;

    @Inject
    private Pessoa_fisicaSearchRepository pessoa_fisicaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPessoa_fisicaMockMvc;

    private Pessoa_fisica pessoa_fisica;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Pessoa_fisicaResource pessoa_fisicaResource = new Pessoa_fisicaResource();
        ReflectionTestUtils.setField(pessoa_fisicaResource, "pessoa_fisicaRepository", pessoa_fisicaRepository);
        ReflectionTestUtils.setField(pessoa_fisicaResource, "pessoa_fisicaSearchRepository", pessoa_fisicaSearchRepository);
        this.restPessoa_fisicaMockMvc = MockMvcBuilders.standaloneSetup(pessoa_fisicaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pessoa_fisica = new Pessoa_fisica();
        pessoa_fisica.setDataNascimento(DEFAULT_DT_NASCIMENTO);
        pessoa_fisica.setRg(DEFAULT_CD_RG);
        pessoa_fisica.setCpf(DEFAULT_CD_CPF);
        pessoa_fisica.setPai(DEFAULT_NM_PAI);
        pessoa_fisica.setMae(DEFAULT_NM_MAE);
        pessoa_fisica.setEstadoCivil(DEFAULT_DS_ESTCIVIL);
        pessoa_fisica.setConjuge(DEFAULT_NM_CONJUGE);
        pessoa_fisica.setProfissao(DEFAULT_DS_PROFISSAO);
        pessoa_fisica.setLocalTrabalho(DEFAULT_DS_LOCALTRAB);
        pessoa_fisica.setComplemento(DEFAULT_DS_COMPLEMENTO);
        pessoa_fisica.setApelido(DEFAULT_DS_APELIDO);
        pessoa_fisica.setObs(DEFAULT_DS_OBS);
    }

    @Test
    @Transactional
    public void createPessoa_fisica() throws Exception {
        int databaseSizeBeforeCreate = pessoa_fisicaRepository.findAll().size();

        // Create the Pessoa_fisica

        restPessoa_fisicaMockMvc.perform(post("/api/pessoa_fisicas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pessoa_fisica)))
                .andExpect(status().isCreated());

        // Validate the Pessoa_fisica in the database
        List<Pessoa_fisica> pessoa_fisicas = pessoa_fisicaRepository.findAll();
        assertThat(pessoa_fisicas).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa_fisica testPessoa_fisica = pessoa_fisicas.get(pessoa_fisicas.size() - 1);
        assertThat(testPessoa_fisica.getDataNascimento()).isEqualTo(DEFAULT_DT_NASCIMENTO);
        assertThat(testPessoa_fisica.getRg()).isEqualTo(DEFAULT_CD_RG);
        assertThat(testPessoa_fisica.getCpf()).isEqualTo(DEFAULT_CD_CPF);
        assertThat(testPessoa_fisica.getPai()).isEqualTo(DEFAULT_NM_PAI);
        assertThat(testPessoa_fisica.getMae()).isEqualTo(DEFAULT_NM_MAE);
        assertThat(testPessoa_fisica.getEstadoCivil()).isEqualTo(DEFAULT_DS_ESTCIVIL);
        assertThat(testPessoa_fisica.getConjuge()).isEqualTo(DEFAULT_NM_CONJUGE);
        assertThat(testPessoa_fisica.getProfissao()).isEqualTo(DEFAULT_DS_PROFISSAO);
        assertThat(testPessoa_fisica.getLocalTrabalho()).isEqualTo(DEFAULT_DS_LOCALTRAB);
        assertThat(testPessoa_fisica.getComplemento()).isEqualTo(DEFAULT_DS_COMPLEMENTO);
        assertThat(testPessoa_fisica.getApelido()).isEqualTo(DEFAULT_DS_APELIDO);
        assertThat(testPessoa_fisica.getObs()).isEqualTo(DEFAULT_DS_OBS);
    }

    @Test
    @Transactional
    public void getAllPessoa_fisicas() throws Exception {
        // Initialize the database
        pessoa_fisicaRepository.saveAndFlush(pessoa_fisica);

        // Get all the pessoa_fisicas
        restPessoa_fisicaMockMvc.perform(get("/api/pessoa_fisicas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa_fisica.getId().intValue())))
                .andExpect(jsonPath("$.[*].dt_nascimento").value(hasItem(DEFAULT_DT_NASCIMENTO.toString())))
                .andExpect(jsonPath("$.[*].cd_rg").value(hasItem(DEFAULT_CD_RG.toString())))
                .andExpect(jsonPath("$.[*].cd_cpf").value(hasItem(DEFAULT_CD_CPF.toString())))
                .andExpect(jsonPath("$.[*].nm_pai").value(hasItem(DEFAULT_NM_PAI.toString())))
                .andExpect(jsonPath("$.[*].nm_mae").value(hasItem(DEFAULT_NM_MAE.toString())))
                .andExpect(jsonPath("$.[*].ds_estcivil").value(hasItem(DEFAULT_DS_ESTCIVIL.toString())))
                .andExpect(jsonPath("$.[*].nm_conjuge").value(hasItem(DEFAULT_NM_CONJUGE.toString())))
                .andExpect(jsonPath("$.[*].ds_profissao").value(hasItem(DEFAULT_DS_PROFISSAO.toString())))
                .andExpect(jsonPath("$.[*].ds_localtrab").value(hasItem(DEFAULT_DS_LOCALTRAB.toString())))
                .andExpect(jsonPath("$.[*].ds_complemento").value(hasItem(DEFAULT_DS_COMPLEMENTO.toString())))
                .andExpect(jsonPath("$.[*].ds_apelido").value(hasItem(DEFAULT_DS_APELIDO.toString())))
                .andExpect(jsonPath("$.[*].ds_obs").value(hasItem(DEFAULT_DS_OBS.toString())));
    }

    @Test
    @Transactional
    public void getPessoa_fisica() throws Exception {
        // Initialize the database
        pessoa_fisicaRepository.saveAndFlush(pessoa_fisica);

        // Get the pessoa_fisica
        restPessoa_fisicaMockMvc.perform(get("/api/pessoa_fisicas/{id}", pessoa_fisica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pessoa_fisica.getId().intValue()))
            .andExpect(jsonPath("$.dt_nascimento").value(DEFAULT_DT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.cd_rg").value(DEFAULT_CD_RG.toString()))
            .andExpect(jsonPath("$.cd_cpf").value(DEFAULT_CD_CPF.toString()))
            .andExpect(jsonPath("$.nm_pai").value(DEFAULT_NM_PAI.toString()))
            .andExpect(jsonPath("$.nm_mae").value(DEFAULT_NM_MAE.toString()))
            .andExpect(jsonPath("$.ds_estcivil").value(DEFAULT_DS_ESTCIVIL.toString()))
            .andExpect(jsonPath("$.nm_conjuge").value(DEFAULT_NM_CONJUGE.toString()))
            .andExpect(jsonPath("$.ds_profissao").value(DEFAULT_DS_PROFISSAO.toString()))
            .andExpect(jsonPath("$.ds_localtrab").value(DEFAULT_DS_LOCALTRAB.toString()))
            .andExpect(jsonPath("$.ds_complemento").value(DEFAULT_DS_COMPLEMENTO.toString()))
            .andExpect(jsonPath("$.ds_apelido").value(DEFAULT_DS_APELIDO.toString()))
            .andExpect(jsonPath("$.ds_obs").value(DEFAULT_DS_OBS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPessoa_fisica() throws Exception {
        // Get the pessoa_fisica
        restPessoa_fisicaMockMvc.perform(get("/api/pessoa_fisicas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePessoa_fisica() throws Exception {
        // Initialize the database
        pessoa_fisicaRepository.saveAndFlush(pessoa_fisica);

		int databaseSizeBeforeUpdate = pessoa_fisicaRepository.findAll().size();

        // Update the pessoa_fisica
        pessoa_fisica.setDataNascimento(UPDATED_DT_NASCIMENTO);
        pessoa_fisica.setRg(UPDATED_CD_RG);
        pessoa_fisica.setCpf(UPDATED_CD_CPF);
        pessoa_fisica.setPai(UPDATED_NM_PAI);
        pessoa_fisica.setMae(UPDATED_NM_MAE);
        pessoa_fisica.setEstadoCivil(UPDATED_DS_ESTCIVIL);
        pessoa_fisica.setConjuge(UPDATED_NM_CONJUGE);
        pessoa_fisica.setProfissao(UPDATED_DS_PROFISSAO);
        pessoa_fisica.setLocalTrabalho(UPDATED_DS_LOCALTRAB);
        pessoa_fisica.setComplemento(UPDATED_DS_COMPLEMENTO);
        pessoa_fisica.setApelido(UPDATED_DS_APELIDO);
        pessoa_fisica.setObs(UPDATED_DS_OBS);

        restPessoa_fisicaMockMvc.perform(put("/api/pessoa_fisicas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pessoa_fisica)))
                .andExpect(status().isOk());

        // Validate the Pessoa_fisica in the database
        List<Pessoa_fisica> pessoa_fisicas = pessoa_fisicaRepository.findAll();
        assertThat(pessoa_fisicas).hasSize(databaseSizeBeforeUpdate);
        Pessoa_fisica testPessoa_fisica = pessoa_fisicas.get(pessoa_fisicas.size() - 1);
        assertThat(testPessoa_fisica.getDataNascimento()).isEqualTo(UPDATED_DT_NASCIMENTO);
        assertThat(testPessoa_fisica.getRg()).isEqualTo(UPDATED_CD_RG);
        assertThat(testPessoa_fisica.getCpf()).isEqualTo(UPDATED_CD_CPF);
        assertThat(testPessoa_fisica.getPai()).isEqualTo(UPDATED_NM_PAI);
        assertThat(testPessoa_fisica.getMae()).isEqualTo(UPDATED_NM_MAE);
        assertThat(testPessoa_fisica.getEstadoCivil()).isEqualTo(UPDATED_DS_ESTCIVIL);
        assertThat(testPessoa_fisica.getConjuge()).isEqualTo(UPDATED_NM_CONJUGE);
        assertThat(testPessoa_fisica.getProfissao()).isEqualTo(UPDATED_DS_PROFISSAO);
        assertThat(testPessoa_fisica.getLocalTrabalho()).isEqualTo(UPDATED_DS_LOCALTRAB);
        assertThat(testPessoa_fisica.getComplemento()).isEqualTo(UPDATED_DS_COMPLEMENTO);
        assertThat(testPessoa_fisica.getApelido()).isEqualTo(UPDATED_DS_APELIDO);
        assertThat(testPessoa_fisica.getObs()).isEqualTo(UPDATED_DS_OBS);
    }

    @Test
    @Transactional
    public void deletePessoa_fisica() throws Exception {
        // Initialize the database
        pessoa_fisicaRepository.saveAndFlush(pessoa_fisica);

		int databaseSizeBeforeDelete = pessoa_fisicaRepository.findAll().size();

        // Get the pessoa_fisica
        restPessoa_fisicaMockMvc.perform(delete("/api/pessoa_fisicas/{id}", pessoa_fisica.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pessoa_fisica> pessoa_fisicas = pessoa_fisicaRepository.findAll();
        assertThat(pessoa_fisicas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
