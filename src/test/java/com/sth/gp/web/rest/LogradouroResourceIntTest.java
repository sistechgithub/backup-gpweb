package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.domain.Logradouro;
import com.sth.gp.repository.LogradouroRepository;
import com.sth.gp.repository.search.LogradouroSearchRepository;

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
 * Test class for the LogradouroResource REST controller.
 *
 * @see LogradouroResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LogradouroResourceIntTest {

    private static final String DEFAULT_NM_LOGRADOURO = "AAAAA";
    private static final String UPDATED_NM_LOGRADOURO = "BBBBB";
    private static final String DEFAULT_CD_DEP = "AAAAA";
    private static final String UPDATED_CD_DEP = "BBBBB";

    @Inject
    private LogradouroRepository logradouroRepository;

    @Inject
    private LogradouroSearchRepository logradouroSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLogradouroMockMvc;

    private Logradouro logradouro;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LogradouroResource logradouroResource = new LogradouroResource();
        ReflectionTestUtils.setField(logradouroResource, "logradouroRepository", logradouroRepository);
        ReflectionTestUtils.setField(logradouroResource, "logradouroSearchRepository", logradouroSearchRepository);
        this.restLogradouroMockMvc = MockMvcBuilders.standaloneSetup(logradouroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        logradouro = new Logradouro();
        logradouro.setNome(DEFAULT_NM_LOGRADOURO);
        logradouro.setCep(DEFAULT_CD_DEP);
    }

    @Test
    @Transactional
    public void createLogradouro() throws Exception {
        int databaseSizeBeforeCreate = logradouroRepository.findAll().size();

        // Create the Logradouro

        restLogradouroMockMvc.perform(post("/api/logradouros")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(logradouro)))
                .andExpect(status().isCreated());

        // Validate the Logradouro in the database
        List<Logradouro> logradouros = logradouroRepository.findAll();
        assertThat(logradouros).hasSize(databaseSizeBeforeCreate + 1);
        Logradouro testLogradouro = logradouros.get(logradouros.size() - 1);
        assertThat(testLogradouro.getNome()).isEqualTo(DEFAULT_NM_LOGRADOURO);
        assertThat(testLogradouro.getCep()).isEqualTo(DEFAULT_CD_DEP);
    }

    @Test
    @Transactional
    public void checkNm_logradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = logradouroRepository.findAll().size();
        // set the field null
        logradouro.setNome(null);

        // Create the Logradouro, which fails.

        restLogradouroMockMvc.perform(post("/api/logradouros")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(logradouro)))
                .andExpect(status().isBadRequest());

        List<Logradouro> logradouros = logradouroRepository.findAll();
        assertThat(logradouros).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLogradouros() throws Exception {
        // Initialize the database
        logradouroRepository.saveAndFlush(logradouro);

        // Get all the logradouros
        restLogradouroMockMvc.perform(get("/api/logradouros"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(logradouro.getId().intValue())))
                .andExpect(jsonPath("$.[*].nm_logradouro").value(hasItem(DEFAULT_NM_LOGRADOURO.toString())))
                .andExpect(jsonPath("$.[*].cd_dep").value(hasItem(DEFAULT_CD_DEP.toString())));
    }

    @Test
    @Transactional
    public void getLogradouro() throws Exception {
        // Initialize the database
        logradouroRepository.saveAndFlush(logradouro);

        // Get the logradouro
        restLogradouroMockMvc.perform(get("/api/logradouros/{id}", logradouro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(logradouro.getId().intValue()))
            .andExpect(jsonPath("$.nm_logradouro").value(DEFAULT_NM_LOGRADOURO.toString()))
            .andExpect(jsonPath("$.cd_dep").value(DEFAULT_CD_DEP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogradouro() throws Exception {
        // Get the logradouro
        restLogradouroMockMvc.perform(get("/api/logradouros/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogradouro() throws Exception {
        // Initialize the database
        logradouroRepository.saveAndFlush(logradouro);

		int databaseSizeBeforeUpdate = logradouroRepository.findAll().size();

        // Update the logradouro
        logradouro.setNome(UPDATED_NM_LOGRADOURO);
        logradouro.setCep(UPDATED_CD_DEP);

        restLogradouroMockMvc.perform(put("/api/logradouros")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(logradouro)))
                .andExpect(status().isOk());

        // Validate the Logradouro in the database
        List<Logradouro> logradouros = logradouroRepository.findAll();
        assertThat(logradouros).hasSize(databaseSizeBeforeUpdate);
        Logradouro testLogradouro = logradouros.get(logradouros.size() - 1);
        assertThat(testLogradouro.getNome()).isEqualTo(UPDATED_NM_LOGRADOURO);
        assertThat(testLogradouro.getCep()).isEqualTo(UPDATED_CD_DEP);
    }

    @Test
    @Transactional
    public void deleteLogradouro() throws Exception {
        // Initialize the database
        logradouroRepository.saveAndFlush(logradouro);

		int databaseSizeBeforeDelete = logradouroRepository.findAll().size();

        // Get the logradouro
        restLogradouroMockMvc.perform(delete("/api/logradouros/{id}", logradouro.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Logradouro> logradouros = logradouroRepository.findAll();
        assertThat(logradouros).hasSize(databaseSizeBeforeDelete - 1);
    }
}
