package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.domain.Subgrupo;
import com.sth.gp.repository.SubgrupoRepository;
import com.sth.gp.repository.search.SubgrupoSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SubgrupoResource REST controller.
 *
 * @see SubgrupoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SubgrupoResourceIntTest {

    private static final String DEFAULT_NM_SUB_GRUPO = "AAAAA";
    private static final String UPDATED_NM_SUB_GRUPO = "BBBBB";

    private static final BigDecimal DEFAULT_VL_CUSTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_CUSTO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_VALOR = new BigDecimal(2);

    private static final LocalDate DEFAULT_DT_OPERACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_OPERACAO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_FL_ENVIO = false;
    private static final Boolean UPDATED_FL_ENVIO = true;

    private static final Integer DEFAULT_NN_NOVO = 1;
    private static final Integer UPDATED_NN_NOVO = 2;

    @Inject
    private SubgrupoRepository subgrupoRepository;

    @Inject
    private SubgrupoSearchRepository subgrupoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubgrupoMockMvc;

    private Subgrupo subgrupo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubgrupoResource subgrupoResource = new SubgrupoResource();
        ReflectionTestUtils.setField(subgrupoResource, "subgrupoRepository", subgrupoRepository);
        ReflectionTestUtils.setField(subgrupoResource, "subgrupoSearchRepository", subgrupoSearchRepository);
        this.restSubgrupoMockMvc = MockMvcBuilders.standaloneSetup(subgrupoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subgrupo = new Subgrupo();
        subgrupo.setNome(DEFAULT_NM_SUB_GRUPO);
        subgrupo.setCusto(DEFAULT_VL_CUSTO);
        subgrupo.setValor(DEFAULT_VL_VALOR);
        subgrupo.setDataOperacao(DEFAULT_DT_OPERACAO);
        subgrupo.setEnviado(DEFAULT_FL_ENVIO);
        subgrupo.setNovo(DEFAULT_NN_NOVO);
    }

    @Test
    @Transactional
    public void createSubgrupo() throws Exception {
        int databaseSizeBeforeCreate = subgrupoRepository.findAll().size();

        // Create the Subgrupo

        restSubgrupoMockMvc.perform(post("/api/subgrupos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subgrupo)))
                .andExpect(status().isCreated());

        // Validate the Subgrupo in the database
        List<Subgrupo> subgrupos = subgrupoRepository.findAll();
        assertThat(subgrupos).hasSize(databaseSizeBeforeCreate + 1);
        Subgrupo testSubgrupo = subgrupos.get(subgrupos.size() - 1);
        assertThat(testSubgrupo.getNome()).isEqualTo(DEFAULT_NM_SUB_GRUPO);
        assertThat(testSubgrupo.getCusto()).isEqualTo(DEFAULT_VL_CUSTO);
        assertThat(testSubgrupo.getValor()).isEqualTo(DEFAULT_VL_VALOR);
        assertThat(testSubgrupo.getDataOperacao()).isEqualTo(DEFAULT_DT_OPERACAO);
        assertThat(testSubgrupo.getEnviado()).isEqualTo(DEFAULT_FL_ENVIO);
        assertThat(testSubgrupo.getNovo()).isEqualTo(DEFAULT_NN_NOVO);
    }

    @Test
    @Transactional
    public void getAllSubgrupos() throws Exception {
        // Initialize the database
        subgrupoRepository.saveAndFlush(subgrupo);

        // Get all the subgrupos
        restSubgrupoMockMvc.perform(get("/api/subgrupos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subgrupo.getId().intValue())))
                .andExpect(jsonPath("$.[*].nm_sub_grupo").value(hasItem(DEFAULT_NM_SUB_GRUPO.toString())))
                .andExpect(jsonPath("$.[*].vl_custo").value(hasItem(DEFAULT_VL_CUSTO.intValue())))
                .andExpect(jsonPath("$.[*].vl_valor").value(hasItem(DEFAULT_VL_VALOR.intValue())))
                .andExpect(jsonPath("$.[*].dt_operacao").value(hasItem(DEFAULT_DT_OPERACAO.toString())))
                .andExpect(jsonPath("$.[*].fl_envio").value(hasItem(DEFAULT_FL_ENVIO.booleanValue())))
                .andExpect(jsonPath("$.[*].nn_novo").value(hasItem(DEFAULT_NN_NOVO)));
    }

    @Test
    @Transactional
    public void getSubgrupo() throws Exception {
        // Initialize the database
        subgrupoRepository.saveAndFlush(subgrupo);

        // Get the subgrupo
        restSubgrupoMockMvc.perform(get("/api/subgrupos/{id}", subgrupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subgrupo.getId().intValue()))
            .andExpect(jsonPath("$.nm_sub_grupo").value(DEFAULT_NM_SUB_GRUPO.toString()))
            .andExpect(jsonPath("$.vl_custo").value(DEFAULT_VL_CUSTO.intValue()))
            .andExpect(jsonPath("$.vl_valor").value(DEFAULT_VL_VALOR.intValue()))
            .andExpect(jsonPath("$.dt_operacao").value(DEFAULT_DT_OPERACAO.toString()))
            .andExpect(jsonPath("$.fl_envio").value(DEFAULT_FL_ENVIO.booleanValue()))
            .andExpect(jsonPath("$.nn_novo").value(DEFAULT_NN_NOVO));
    }

    @Test
    @Transactional
    public void getNonExistingSubgrupo() throws Exception {
        // Get the subgrupo
        restSubgrupoMockMvc.perform(get("/api/subgrupos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubgrupo() throws Exception {
        // Initialize the database
        subgrupoRepository.saveAndFlush(subgrupo);

		int databaseSizeBeforeUpdate = subgrupoRepository.findAll().size();

        // Update the subgrupo
        subgrupo.setNome(UPDATED_NM_SUB_GRUPO);
        subgrupo.setCusto(UPDATED_VL_CUSTO);
        subgrupo.setValor(UPDATED_VL_VALOR);
        subgrupo.setDataOperacao(UPDATED_DT_OPERACAO);
        subgrupo.setEnviado(UPDATED_FL_ENVIO);
        subgrupo.setNovo(UPDATED_NN_NOVO);

        restSubgrupoMockMvc.perform(put("/api/subgrupos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subgrupo)))
                .andExpect(status().isOk());

        // Validate the Subgrupo in the database
        List<Subgrupo> subgrupos = subgrupoRepository.findAll();
        assertThat(subgrupos).hasSize(databaseSizeBeforeUpdate);
        Subgrupo testSubgrupo = subgrupos.get(subgrupos.size() - 1);
        assertThat(testSubgrupo.getNome()).isEqualTo(UPDATED_NM_SUB_GRUPO);
        assertThat(testSubgrupo.getCusto()).isEqualTo(UPDATED_VL_CUSTO);
        assertThat(testSubgrupo.getValor()).isEqualTo(UPDATED_VL_VALOR);
        assertThat(testSubgrupo.getDataOperacao()).isEqualTo(UPDATED_DT_OPERACAO);
        assertThat(testSubgrupo.getEnviado()).isEqualTo(UPDATED_FL_ENVIO);
        assertThat(testSubgrupo.getNovo()).isEqualTo(UPDATED_NN_NOVO);
    }

    @Test
    @Transactional
    public void deleteSubgrupo() throws Exception {
        // Initialize the database
        subgrupoRepository.saveAndFlush(subgrupo);

		int databaseSizeBeforeDelete = subgrupoRepository.findAll().size();

        // Get the subgrupo
        restSubgrupoMockMvc.perform(delete("/api/subgrupos/{id}", subgrupo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Subgrupo> subgrupos = subgrupoRepository.findAll();
        assertThat(subgrupos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
