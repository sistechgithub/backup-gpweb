package com.sth.gp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.sth.gp.Application;
import com.sth.gp.domain.Fabricante;
import com.sth.gp.repository.FabricanteRepository;


/**
 * Test class for the FabricanteResource REST controller.
 *
 * @see FabricanteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FabricanteResourceIntTest {

    private static final String DEFAULT_NM_FABRICANTE = "AAAAAAAAAA";
    private static final String UPDATED_NM_FABRICANTE = "BBBBBBBBBB";
    private static final String DEFAULT_CD_CGC = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CD_CGC = "BBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CD_CGF = "AAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CD_CGF = "BBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NN_NUMERO = "AAAAA";
    private static final String UPDATED_NN_NUMERO = "BBBBB";
    private static final String DEFAULT_CS_COMPLEMENTO = "AAAAA";
    private static final String UPDATED_CS_COMPLEMENTO = "BBBBB";
    private static final String DEFAULT_CD_TEL = "AAAAA";
    private static final String UPDATED_CD_TEL = "BBBBB";
    private static final String DEFAULT_CD_FAX = "AAAAA";
    private static final String UPDATED_CD_FAX = "BBBBB";

    private static final Boolean DEFAULT_FL_INATIVO = false;
    private static final Boolean UPDATED_FL_INATIVO = true;

    @Inject
    private FabricanteRepository fabricanteRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFabricanteMockMvc;

    private Fabricante fabricante;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FabricanteResource fabricanteResource = new FabricanteResource();
        ReflectionTestUtils.setField(fabricanteResource, "fabricanteRepository", fabricanteRepository);        
        this.restFabricanteMockMvc = MockMvcBuilders.standaloneSetup(fabricanteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fabricante = new Fabricante();
        fabricante.setNome(DEFAULT_NM_FABRICANTE);
        fabricante.setCnpj(DEFAULT_CD_CGC);
        fabricante.setIe(DEFAULT_CD_CGF);
        fabricante.setNumero(DEFAULT_NN_NUMERO);
        fabricante.setComplemento(DEFAULT_CS_COMPLEMENTO);
        fabricante.setTelefone(DEFAULT_CD_TEL);
        fabricante.setFax(DEFAULT_CD_FAX);
        fabricante.setInativo(DEFAULT_FL_INATIVO);
    }

    @Test
    @Transactional
    public void createFabricante() throws Exception {
        int databaseSizeBeforeCreate = fabricanteRepository.findAll().size();

        // Create the Fabricante

        restFabricanteMockMvc.perform(post("/api/fabricantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fabricante)))
                .andExpect(status().isCreated());

        // Validate the Fabricante in the database
        List<Fabricante> fabricantes = fabricanteRepository.findAll();
        assertThat(fabricantes).hasSize(databaseSizeBeforeCreate + 1);
        Fabricante testFabricante = fabricantes.get(fabricantes.size() - 1);
        assertThat(testFabricante.getNome()).isEqualTo(DEFAULT_NM_FABRICANTE);
        assertThat(testFabricante.getCnpj()).isEqualTo(DEFAULT_CD_CGC);
        assertThat(testFabricante.getIe()).isEqualTo(DEFAULT_CD_CGF);
        assertThat(testFabricante.getNumero()).isEqualTo(DEFAULT_NN_NUMERO);
        assertThat(testFabricante.getComplemento()).isEqualTo(DEFAULT_CS_COMPLEMENTO);
        assertThat(testFabricante.getTelefone()).isEqualTo(DEFAULT_CD_TEL);
        assertThat(testFabricante.getFax()).isEqualTo(DEFAULT_CD_FAX);
        assertThat(testFabricante.getInativo()).isEqualTo(DEFAULT_FL_INATIVO);
    }

    @Test
    @Transactional
    public void checkNm_fabricanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricanteRepository.findAll().size();
        // set the field null
        fabricante.setNome(null);

        // Create the Fabricante, which fails.

        restFabricanteMockMvc.perform(post("/api/fabricantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fabricante)))
                .andExpect(status().isBadRequest());

        List<Fabricante> fabricantes = fabricanteRepository.findAll();
        assertThat(fabricantes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFabricantes() throws Exception {
        // Initialize the database
        fabricanteRepository.saveAndFlush(fabricante);

        // Get all the fabricantes
        restFabricanteMockMvc.perform(get("/api/fabricantes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fabricante.getId().intValue())))
                .andExpect(jsonPath("$.[*].nm_fabricante").value(hasItem(DEFAULT_NM_FABRICANTE.toString())))
                .andExpect(jsonPath("$.[*].cd_cgc").value(hasItem(DEFAULT_CD_CGC.toString())))
                .andExpect(jsonPath("$.[*].cd_cgf").value(hasItem(DEFAULT_CD_CGF.toString())))
                .andExpect(jsonPath("$.[*].nn_numero").value(hasItem(DEFAULT_NN_NUMERO.toString())))
                .andExpect(jsonPath("$.[*].cs_complemento").value(hasItem(DEFAULT_CS_COMPLEMENTO.toString())))
                .andExpect(jsonPath("$.[*].cd_tel").value(hasItem(DEFAULT_CD_TEL.toString())))
                .andExpect(jsonPath("$.[*].cd_fax").value(hasItem(DEFAULT_CD_FAX.toString())))
                .andExpect(jsonPath("$.[*].fl_inativo").value(hasItem(DEFAULT_FL_INATIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getFabricante() throws Exception {
        // Initialize the database
        fabricanteRepository.saveAndFlush(fabricante);

        // Get the fabricante
        restFabricanteMockMvc.perform(get("/api/fabricantes/{id}", fabricante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fabricante.getId().intValue()))
            .andExpect(jsonPath("$.nm_fabricante").value(DEFAULT_NM_FABRICANTE.toString()))
            .andExpect(jsonPath("$.cd_cgc").value(DEFAULT_CD_CGC.toString()))
            .andExpect(jsonPath("$.cd_cgf").value(DEFAULT_CD_CGF.toString()))
            .andExpect(jsonPath("$.nn_numero").value(DEFAULT_NN_NUMERO.toString()))
            .andExpect(jsonPath("$.cs_complemento").value(DEFAULT_CS_COMPLEMENTO.toString()))
            .andExpect(jsonPath("$.cd_tel").value(DEFAULT_CD_TEL.toString()))
            .andExpect(jsonPath("$.cd_fax").value(DEFAULT_CD_FAX.toString()))
            .andExpect(jsonPath("$.fl_inativo").value(DEFAULT_FL_INATIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFabricante() throws Exception {
        // Get the fabricante
        restFabricanteMockMvc.perform(get("/api/fabricantes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFabricante() throws Exception {
        // Initialize the database
        fabricanteRepository.saveAndFlush(fabricante);

		int databaseSizeBeforeUpdate = fabricanteRepository.findAll().size();

        // Update the fabricante
        fabricante.setNome(UPDATED_NM_FABRICANTE);
        fabricante.setCnpj(UPDATED_CD_CGC);
        fabricante.setIe(UPDATED_CD_CGF);
        fabricante.setNumero(UPDATED_NN_NUMERO);
        fabricante.setComplemento(UPDATED_CS_COMPLEMENTO);
        fabricante.setTelefone(UPDATED_CD_TEL);
        fabricante.setFax(UPDATED_CD_FAX);
        fabricante.setInativo(UPDATED_FL_INATIVO);

        restFabricanteMockMvc.perform(put("/api/fabricantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fabricante)))
                .andExpect(status().isOk());

        // Validate the Fabricante in the database
        List<Fabricante> fabricantes = fabricanteRepository.findAll();
        assertThat(fabricantes).hasSize(databaseSizeBeforeUpdate);
        Fabricante testFabricante = fabricantes.get(fabricantes.size() - 1);
        assertThat(testFabricante.getNome()).isEqualTo(UPDATED_NM_FABRICANTE);
        assertThat(testFabricante.getCnpj()).isEqualTo(UPDATED_CD_CGC);
        assertThat(testFabricante.getIe()).isEqualTo(UPDATED_CD_CGF);
        assertThat(testFabricante.getNumero()).isEqualTo(UPDATED_NN_NUMERO);
        assertThat(testFabricante.getComplemento()).isEqualTo(UPDATED_CS_COMPLEMENTO);
        assertThat(testFabricante.getTelefone()).isEqualTo(UPDATED_CD_TEL);
        assertThat(testFabricante.getFax()).isEqualTo(UPDATED_CD_FAX);
        assertThat(testFabricante.getInativo()).isEqualTo(UPDATED_FL_INATIVO);
    }

    @Test
    @Transactional
    public void deleteFabricante() throws Exception {
        // Initialize the database
        fabricanteRepository.saveAndFlush(fabricante);

		int databaseSizeBeforeDelete = fabricanteRepository.findAll().size();

        // Get the fabricante
        restFabricanteMockMvc.perform(delete("/api/fabricantes/{id}", fabricante.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fabricante> fabricantes = fabricanteRepository.findAll();
        assertThat(fabricantes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
