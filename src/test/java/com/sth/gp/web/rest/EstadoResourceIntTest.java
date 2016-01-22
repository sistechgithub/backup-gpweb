package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.domain.Estado;
import com.sth.gp.repository.EstadoRepository;
import com.sth.gp.repository.search.EstadoSearchRepository;

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
 * Test class for the EstadoResource REST controller.
 *
 * @see EstadoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EstadoResourceIntTest {

    private static final String DEFAULT_NM_ESTADO = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NM_ESTADO = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SG_ESTADO = "AAAAA";
    private static final String UPDATED_SG_ESTADO = "BBBBB";
    private static final String DEFAULT_DS_PAIS = "AAAAA";
    private static final String UPDATED_DS_PAIS = "BBBBB";

    @Inject
    private EstadoRepository estadoRepository;

    @Inject
    private EstadoSearchRepository estadoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEstadoMockMvc;

    private Estado estado;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EstadoResource estadoResource = new EstadoResource();
        ReflectionTestUtils.setField(estadoResource, "estadoRepository", estadoRepository);
        ReflectionTestUtils.setField(estadoResource, "estadoSearchRepository", estadoSearchRepository);
        this.restEstadoMockMvc = MockMvcBuilders.standaloneSetup(estadoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        estado = new Estado();
        estado.setNome(DEFAULT_NM_ESTADO);
        estado.setSigla(DEFAULT_SG_ESTADO);
        estado.setPais(DEFAULT_DS_PAIS);
    }

    @Test
    @Transactional
    public void createEstado() throws Exception {
        int databaseSizeBeforeCreate = estadoRepository.findAll().size();

        // Create the Estado

        restEstadoMockMvc.perform(post("/api/estados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estado)))
                .andExpect(status().isCreated());

        // Validate the Estado in the database
        List<Estado> estados = estadoRepository.findAll();
        assertThat(estados).hasSize(databaseSizeBeforeCreate + 1);
        Estado testEstado = estados.get(estados.size() - 1);
        assertThat(testEstado.getNome()).isEqualTo(DEFAULT_NM_ESTADO);
        assertThat(testEstado.getSigla()).isEqualTo(DEFAULT_SG_ESTADO);
        assertThat(testEstado.getPais()).isEqualTo(DEFAULT_DS_PAIS);
    }

    @Test
    @Transactional
    public void checkNm_estadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoRepository.findAll().size();
        // set the field null
        estado.setNome(null);

        // Create the Estado, which fails.

        restEstadoMockMvc.perform(post("/api/estados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estado)))
                .andExpect(status().isBadRequest());

        List<Estado> estados = estadoRepository.findAll();
        assertThat(estados).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSg_estadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoRepository.findAll().size();
        // set the field null
        estado.setSigla(null);

        // Create the Estado, which fails.

        restEstadoMockMvc.perform(post("/api/estados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estado)))
                .andExpect(status().isBadRequest());

        List<Estado> estados = estadoRepository.findAll();
        assertThat(estados).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstados() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        // Get all the estados
        restEstadoMockMvc.perform(get("/api/estados"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(estado.getId().intValue())))
                .andExpect(jsonPath("$.[*].nm_estado").value(hasItem(DEFAULT_NM_ESTADO.toString())))
                .andExpect(jsonPath("$.[*].sg_estado").value(hasItem(DEFAULT_SG_ESTADO.toString())))
                .andExpect(jsonPath("$.[*].ds_pais").value(hasItem(DEFAULT_DS_PAIS.toString())));
    }

    @Test
    @Transactional
    public void getEstado() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

        // Get the estado
        restEstadoMockMvc.perform(get("/api/estados/{id}", estado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(estado.getId().intValue()))
            .andExpect(jsonPath("$.nm_estado").value(DEFAULT_NM_ESTADO.toString()))
            .andExpect(jsonPath("$.sg_estado").value(DEFAULT_SG_ESTADO.toString()))
            .andExpect(jsonPath("$.ds_pais").value(DEFAULT_DS_PAIS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstado() throws Exception {
        // Get the estado
        restEstadoMockMvc.perform(get("/api/estados/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstado() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

		int databaseSizeBeforeUpdate = estadoRepository.findAll().size();

        // Update the estado
        estado.setNome(UPDATED_NM_ESTADO);
        estado.setSigla(UPDATED_SG_ESTADO);
        estado.setPais(UPDATED_DS_PAIS);

        restEstadoMockMvc.perform(put("/api/estados")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(estado)))
                .andExpect(status().isOk());

        // Validate the Estado in the database
        List<Estado> estados = estadoRepository.findAll();
        assertThat(estados).hasSize(databaseSizeBeforeUpdate);
        Estado testEstado = estados.get(estados.size() - 1);
        assertThat(testEstado.getNome()).isEqualTo(UPDATED_NM_ESTADO);
        assertThat(testEstado.getSigla()).isEqualTo(UPDATED_SG_ESTADO);
        assertThat(testEstado.getPais()).isEqualTo(UPDATED_DS_PAIS);
    }

    @Test
    @Transactional
    public void deleteEstado() throws Exception {
        // Initialize the database
        estadoRepository.saveAndFlush(estado);

		int databaseSizeBeforeDelete = estadoRepository.findAll().size();

        // Get the estado
        restEstadoMockMvc.perform(delete("/api/estados/{id}", estado.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Estado> estados = estadoRepository.findAll();
        assertThat(estados).hasSize(databaseSizeBeforeDelete - 1);
    }
}
