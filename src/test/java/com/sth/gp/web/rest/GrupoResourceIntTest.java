package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.domain.Grupo;
import com.sth.gp.repository.GrupoRepository;
import com.sth.gp.repository.search.GrupoSearchRepository;

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
 * Test class for the GrupoResource REST controller.
 *
 * @see GrupoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GrupoResourceIntTest {

    private static final String DEFAULT_NM_GRUPO = "AAAAA";
    private static final String UPDATED_NM_GRUPO = "BBBBB";

    private static final BigDecimal DEFAULT_VL_COMISSAO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_COMISSAO = new BigDecimal(2);

    private static final Boolean DEFAULT_FL_DESCONTO = false;
    private static final Boolean UPDATED_FL_DESCONTO = true;

    private static final Boolean DEFAULT_FL_PROMO = false;
    private static final Boolean UPDATED_FL_PROMO = true;

    private static final LocalDate DEFAULT_DT_PROMO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_PROMO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DT_OPERACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_OPERACAO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_FL_SEMCONTAGEM = false;
    private static final Boolean UPDATED_FL_SEMCONTAGEM = true;

    private static final Boolean DEFAULT_FL_ENVIO = false;
    private static final Boolean UPDATED_FL_ENVIO = true;

    private static final Integer DEFAULT_NN_NOVO = 1;
    private static final Integer UPDATED_NN_NOVO = 2;

    @Inject
    private GrupoRepository grupoRepository;

    @Inject
    private GrupoSearchRepository grupoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGrupoMockMvc;

    private Grupo grupo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GrupoResource grupoResource = new GrupoResource();
        ReflectionTestUtils.setField(grupoResource, "grupoRepository", grupoRepository);
        ReflectionTestUtils.setField(grupoResource, "grupoSearchRepository", grupoSearchRepository);
        this.restGrupoMockMvc = MockMvcBuilders.standaloneSetup(grupoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        grupo = new Grupo();
        grupo.setNome(DEFAULT_NM_GRUPO);
        grupo.setValorComissao(DEFAULT_VL_COMISSAO);
        grupo.setSemDesconto(DEFAULT_FL_DESCONTO);
        grupo.setEmPromo(DEFAULT_FL_PROMO);
        grupo.setDataPromo(DEFAULT_DT_PROMO);
        grupo.setDataOperacao(DEFAULT_DT_OPERACAO);
        grupo.setSemSaldo(DEFAULT_FL_SEMCONTAGEM);
        grupo.setEnviado(DEFAULT_FL_ENVIO);
        grupo.setNovo(DEFAULT_NN_NOVO);
    }

    @Test
    @Transactional
    public void createGrupo() throws Exception {
        int databaseSizeBeforeCreate = grupoRepository.findAll().size();

        // Create the Grupo

        restGrupoMockMvc.perform(post("/api/grupos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grupo)))
                .andExpect(status().isCreated());

        // Validate the Grupo in the database
        List<Grupo> grupos = grupoRepository.findAll();
        assertThat(grupos).hasSize(databaseSizeBeforeCreate + 1);
        Grupo testGrupo = grupos.get(grupos.size() - 1);
        assertThat(testGrupo.getNome()).isEqualTo(DEFAULT_NM_GRUPO);
        assertThat(testGrupo.getValorComissao()).isEqualTo(DEFAULT_VL_COMISSAO);
        assertThat(testGrupo.getSemDesconto()).isEqualTo(DEFAULT_FL_DESCONTO);
        assertThat(testGrupo.getEmPromo()).isEqualTo(DEFAULT_FL_PROMO);
        assertThat(testGrupo.getDataPromo()).isEqualTo(DEFAULT_DT_PROMO);
        assertThat(testGrupo.getDataOperacao()).isEqualTo(DEFAULT_DT_OPERACAO);
        assertThat(testGrupo.getSemSaldo()).isEqualTo(DEFAULT_FL_SEMCONTAGEM);
        assertThat(testGrupo.getEnviado()).isEqualTo(DEFAULT_FL_ENVIO);
        assertThat(testGrupo.getNovo()).isEqualTo(DEFAULT_NN_NOVO);
    }

    @Test
    @Transactional
    public void getAllGrupos() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        // Get all the grupos
        restGrupoMockMvc.perform(get("/api/grupos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(grupo.getId().intValue())))
                .andExpect(jsonPath("$.[*].nm_grupo").value(hasItem(DEFAULT_NM_GRUPO.toString())))
                .andExpect(jsonPath("$.[*].vl_comissao").value(hasItem(DEFAULT_VL_COMISSAO.intValue())))
                .andExpect(jsonPath("$.[*].fl_desconto").value(hasItem(DEFAULT_FL_DESCONTO.booleanValue())))
                .andExpect(jsonPath("$.[*].fl_promo").value(hasItem(DEFAULT_FL_PROMO.booleanValue())))
                .andExpect(jsonPath("$.[*].dt_promo").value(hasItem(DEFAULT_DT_PROMO.toString())))
                .andExpect(jsonPath("$.[*].dt_operacao").value(hasItem(DEFAULT_DT_OPERACAO.toString())))
                .andExpect(jsonPath("$.[*].fl_semcontagem").value(hasItem(DEFAULT_FL_SEMCONTAGEM.booleanValue())))
                .andExpect(jsonPath("$.[*].fl_envio").value(hasItem(DEFAULT_FL_ENVIO.booleanValue())))
                .andExpect(jsonPath("$.[*].nn_novo").value(hasItem(DEFAULT_NN_NOVO)));
    }

    @Test
    @Transactional
    public void getGrupo() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

        // Get the grupo
        restGrupoMockMvc.perform(get("/api/grupos/{id}", grupo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(grupo.getId().intValue()))
            .andExpect(jsonPath("$.nm_grupo").value(DEFAULT_NM_GRUPO.toString()))
            .andExpect(jsonPath("$.vl_comissao").value(DEFAULT_VL_COMISSAO.intValue()))
            .andExpect(jsonPath("$.fl_desconto").value(DEFAULT_FL_DESCONTO.booleanValue()))
            .andExpect(jsonPath("$.fl_promo").value(DEFAULT_FL_PROMO.booleanValue()))
            .andExpect(jsonPath("$.dt_promo").value(DEFAULT_DT_PROMO.toString()))
            .andExpect(jsonPath("$.dt_operacao").value(DEFAULT_DT_OPERACAO.toString()))
            .andExpect(jsonPath("$.fl_semcontagem").value(DEFAULT_FL_SEMCONTAGEM.booleanValue()))
            .andExpect(jsonPath("$.fl_envio").value(DEFAULT_FL_ENVIO.booleanValue()))
            .andExpect(jsonPath("$.nn_novo").value(DEFAULT_NN_NOVO));
    }

    @Test
    @Transactional
    public void getNonExistingGrupo() throws Exception {
        // Get the grupo
        restGrupoMockMvc.perform(get("/api/grupos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupo() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

		int databaseSizeBeforeUpdate = grupoRepository.findAll().size();

        // Update the grupo
        grupo.setNome(UPDATED_NM_GRUPO);
        grupo.setValorComissao(UPDATED_VL_COMISSAO);
        grupo.setSemDesconto(UPDATED_FL_DESCONTO);
        grupo.setEmPromo(UPDATED_FL_PROMO);
        grupo.setDataPromo(UPDATED_DT_PROMO);
        grupo.setDataOperacao(UPDATED_DT_OPERACAO);
        grupo.setSemSaldo(UPDATED_FL_SEMCONTAGEM);
        grupo.setEnviado(UPDATED_FL_ENVIO);
        grupo.setNovo(UPDATED_NN_NOVO);

        restGrupoMockMvc.perform(put("/api/grupos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grupo)))
                .andExpect(status().isOk());

        // Validate the Grupo in the database
        List<Grupo> grupos = grupoRepository.findAll();
        assertThat(grupos).hasSize(databaseSizeBeforeUpdate);
        Grupo testGrupo = grupos.get(grupos.size() - 1);
        assertThat(testGrupo.getNome()).isEqualTo(UPDATED_NM_GRUPO);
        assertThat(testGrupo.getValorComissao()).isEqualTo(UPDATED_VL_COMISSAO);
        assertThat(testGrupo.getSemDesconto()).isEqualTo(UPDATED_FL_DESCONTO);
        assertThat(testGrupo.getEmPromo()).isEqualTo(UPDATED_FL_PROMO);
        assertThat(testGrupo.getDataPromo()).isEqualTo(UPDATED_DT_PROMO);
        assertThat(testGrupo.getDataOperacao()).isEqualTo(UPDATED_DT_OPERACAO);
        assertThat(testGrupo.getSemSaldo()).isEqualTo(UPDATED_FL_SEMCONTAGEM);
        assertThat(testGrupo.getEnviado()).isEqualTo(UPDATED_FL_ENVIO);
        assertThat(testGrupo.getNovo()).isEqualTo(UPDATED_NN_NOVO);
    }

    @Test
    @Transactional
    public void deleteGrupo() throws Exception {
        // Initialize the database
        grupoRepository.saveAndFlush(grupo);

		int databaseSizeBeforeDelete = grupoRepository.findAll().size();

        // Get the grupo
        restGrupoMockMvc.perform(delete("/api/grupos/{id}", grupo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Grupo> grupos = grupoRepository.findAll();
        assertThat(grupos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
