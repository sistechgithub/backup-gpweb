package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.domain.Bairro;
import com.sth.gp.repository.BairroRepository;
import com.sth.gp.repository.search.BairroSearchRepository;

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
 * Test class for the BairroResource REST controller.
 *
 * @see BairroResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BairroResourceIntTest {

    private static final String DEFAULT_NM_BAIRRO = "AAAAA";
    private static final String UPDATED_NM_BAIRRO = "BBBBB";

    @Inject
    private BairroRepository bairroRepository;

    @Inject
    private BairroSearchRepository bairroSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBairroMockMvc;

    private Bairro bairro;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BairroResource bairroResource = new BairroResource();
        ReflectionTestUtils.setField(bairroResource, "bairroRepository", bairroRepository);
        ReflectionTestUtils.setField(bairroResource, "bairroSearchRepository", bairroSearchRepository);
        this.restBairroMockMvc = MockMvcBuilders.standaloneSetup(bairroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bairro = new Bairro();
        bairro.setNm_bairro(DEFAULT_NM_BAIRRO);
    }

    @Test
    @Transactional
    public void createBairro() throws Exception {
        int databaseSizeBeforeCreate = bairroRepository.findAll().size();

        // Create the Bairro

        restBairroMockMvc.perform(post("/api/bairros")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bairro)))
                .andExpect(status().isCreated());

        // Validate the Bairro in the database
        List<Bairro> bairros = bairroRepository.findAll();
        assertThat(bairros).hasSize(databaseSizeBeforeCreate + 1);
        Bairro testBairro = bairros.get(bairros.size() - 1);
        assertThat(testBairro.getNm_bairro()).isEqualTo(DEFAULT_NM_BAIRRO);
    }

    @Test
    @Transactional
    public void checkNm_bairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = bairroRepository.findAll().size();
        // set the field null
        bairro.setNm_bairro(null);

        // Create the Bairro, which fails.

        restBairroMockMvc.perform(post("/api/bairros")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bairro)))
                .andExpect(status().isBadRequest());

        List<Bairro> bairros = bairroRepository.findAll();
        assertThat(bairros).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBairros() throws Exception {
        // Initialize the database
        bairroRepository.saveAndFlush(bairro);

        // Get all the bairros
        restBairroMockMvc.perform(get("/api/bairros"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bairro.getId().intValue())))
                .andExpect(jsonPath("$.[*].nm_bairro").value(hasItem(DEFAULT_NM_BAIRRO.toString())));
    }

    @Test
    @Transactional
    public void getBairro() throws Exception {
        // Initialize the database
        bairroRepository.saveAndFlush(bairro);

        // Get the bairro
        restBairroMockMvc.perform(get("/api/bairros/{id}", bairro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bairro.getId().intValue()))
            .andExpect(jsonPath("$.nm_bairro").value(DEFAULT_NM_BAIRRO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBairro() throws Exception {
        // Get the bairro
        restBairroMockMvc.perform(get("/api/bairros/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBairro() throws Exception {
        // Initialize the database
        bairroRepository.saveAndFlush(bairro);

		int databaseSizeBeforeUpdate = bairroRepository.findAll().size();

        // Update the bairro
        bairro.setNm_bairro(UPDATED_NM_BAIRRO);

        restBairroMockMvc.perform(put("/api/bairros")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bairro)))
                .andExpect(status().isOk());

        // Validate the Bairro in the database
        List<Bairro> bairros = bairroRepository.findAll();
        assertThat(bairros).hasSize(databaseSizeBeforeUpdate);
        Bairro testBairro = bairros.get(bairros.size() - 1);
        assertThat(testBairro.getNm_bairro()).isEqualTo(UPDATED_NM_BAIRRO);
    }

    @Test
    @Transactional
    public void deleteBairro() throws Exception {
        // Initialize the database
        bairroRepository.saveAndFlush(bairro);

		int databaseSizeBeforeDelete = bairroRepository.findAll().size();

        // Get the bairro
        restBairroMockMvc.perform(delete("/api/bairros/{id}", bairro.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bairro> bairros = bairroRepository.findAll();
        assertThat(bairros).hasSize(databaseSizeBeforeDelete - 1);
    }
}
