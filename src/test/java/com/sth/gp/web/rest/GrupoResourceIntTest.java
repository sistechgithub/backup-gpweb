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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
import com.sth.gp.domain.Grupo;
import com.sth.gp.repository.GrupoRepository;


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

    private static final BigDecimal DEFAULT_VL_DESCONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_DESCONTO = new BigDecimal(2);

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
        this.restGrupoMockMvc = MockMvcBuilders.standaloneSetup(grupoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        grupo = new Grupo();
        grupo.setNmGrupo(DEFAULT_NM_GRUPO);
        grupo.setVlComissao(DEFAULT_VL_COMISSAO);
        grupo.setVlDesconto(DEFAULT_VL_DESCONTO);
        grupo.setFlPromo(DEFAULT_FL_PROMO);
        grupo.setDtPromo(DEFAULT_DT_PROMO);
        grupo.setDtOperacao(DEFAULT_DT_OPERACAO);
        grupo.setFlSemcontagem(DEFAULT_FL_SEMCONTAGEM);
        grupo.setFlEnvio(DEFAULT_FL_ENVIO);
        grupo.setNnNovo(DEFAULT_NN_NOVO);
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
        assertThat(testGrupo.getNmGrupo()).isEqualTo(DEFAULT_NM_GRUPO);
        assertThat(testGrupo.getVlComissao()).isEqualTo(DEFAULT_VL_COMISSAO);
        assertThat(testGrupo.getVlDesconto()).isEqualTo(DEFAULT_VL_DESCONTO);
        assertThat(testGrupo.getFlPromo()).isEqualTo(DEFAULT_FL_PROMO);
        assertThat(testGrupo.getDtPromo()).isEqualTo(DEFAULT_DT_PROMO);
        assertThat(testGrupo.getDtOperacao()).isEqualTo(DEFAULT_DT_OPERACAO);
        assertThat(testGrupo.getFlSemcontagem()).isEqualTo(DEFAULT_FL_SEMCONTAGEM);
        assertThat(testGrupo.getFlEnvio()).isEqualTo(DEFAULT_FL_ENVIO);
        assertThat(testGrupo.getNnNovo()).isEqualTo(DEFAULT_NN_NOVO);
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
                .andExpect(jsonPath("$.[*].fl_desconto").value(hasItem(DEFAULT_VL_DESCONTO.intValue())))
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
            .andExpect(jsonPath("$.fl_desconto").value(DEFAULT_VL_DESCONTO.intValue()))
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
        grupo.setNmGrupo(UPDATED_NM_GRUPO);
        grupo.setVlComissao(UPDATED_VL_COMISSAO);
        grupo.setVlDesconto(UPDATED_VL_DESCONTO);
        grupo.setFlPromo(UPDATED_FL_PROMO);
        grupo.setDtPromo(UPDATED_DT_PROMO);
        grupo.setDtOperacao(UPDATED_DT_OPERACAO);
        grupo.setFlSemcontagem(UPDATED_FL_SEMCONTAGEM);
        grupo.setFlEnvio(UPDATED_FL_ENVIO);
        grupo.setNnNovo(UPDATED_NN_NOVO);

        restGrupoMockMvc.perform(put("/api/grupos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grupo)))
                .andExpect(status().isOk());

        // Validate the Grupo in the database
        List<Grupo> grupos = grupoRepository.findAll();
        assertThat(grupos).hasSize(databaseSizeBeforeUpdate);
        Grupo testGrupo = grupos.get(grupos.size() - 1);
        assertThat(testGrupo.getNmGrupo()).isEqualTo(UPDATED_NM_GRUPO);
        assertThat(testGrupo.getVlComissao()).isEqualTo(UPDATED_VL_COMISSAO);
        assertThat(testGrupo.getVlDesconto()).isEqualTo(UPDATED_VL_DESCONTO);
        assertThat(testGrupo.getFlPromo()).isEqualTo(UPDATED_FL_PROMO);
        assertThat(testGrupo.getDtPromo()).isEqualTo(UPDATED_DT_PROMO);
        assertThat(testGrupo.getDtOperacao()).isEqualTo(UPDATED_DT_OPERACAO);
        assertThat(testGrupo.getFlSemcontagem()).isEqualTo(UPDATED_FL_SEMCONTAGEM);
        assertThat(testGrupo.getFlEnvio()).isEqualTo(UPDATED_FL_ENVIO);
        assertThat(testGrupo.getNnNovo()).isEqualTo(UPDATED_NN_NOVO);
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
