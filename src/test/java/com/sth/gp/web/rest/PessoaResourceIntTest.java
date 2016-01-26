package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.domain.Pessoa;
import com.sth.gp.repository.PessoaRepository;
import com.sth.gp.repository.search.PessoaSearchRepository;

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
 * Test class for the PessoaResource REST controller.
 *
 * @see PessoaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PessoaResourceIntTest {


    private static final Long DEFAULT_ID_PESSOA = 1L;
    private static final Long UPDATED_ID_PESSOA = 2L;
    private static final String DEFAULT_NM_PESSOA = "AAAAA";
    private static final String UPDATED_NM_PESSOA = "BBBBB";
    private static final String DEFAULT_CD_TEL = "AAAAA";
    private static final String UPDATED_CD_TEL = "BBBBB";
    private static final String DEFAULT_CD_CEL = "AAAAA";
    private static final String UPDATED_CD_CEL = "BBBBB";
    private static final String DEFAULT_CD_FAX = "AAAAA";
    private static final String UPDATED_CD_FAX = "BBBBB";

    private static final Long DEFAULT_ID_LOGRADOURO = 1L;
    private static final Long UPDATED_ID_LOGRADOURO = 2L;
    private static final String DEFAULT_NM_NUMERO = "AAAAA";
    private static final String UPDATED_NM_NUMERO = "BBBBB";
    private static final String DEFAULT_DS_COMPLEMENTO = "AAAAA";
    private static final String UPDATED_DS_COMPLEMENTO = "BBBBB";

    private static final Boolean DEFAULT_DS_SITUACAO = false;
    private static final Boolean UPDATED_DS_SITUACAO = true;

    private static final Boolean DEFAULT_FL_FISICA = false;
    private static final Boolean UPDATED_FL_FISICA = true;
    private static final String DEFAULT_DS_EMAIL = "AAAAA";
    private static final String UPDATED_DS_EMAIL = "BBBBB";
    private static final String DEFAULT_DS_OBS = "AAAAA";
    private static final String UPDATED_DS_OBS = "BBBBB";
    private static final String DEFAULT_DS_HISTORICO = "AAAAA";
    private static final String UPDATED_DS_HISTORICO = "BBBBB";

    private static final Long DEFAULT_FL_VENDEDOR = 1L;
    private static final Long UPDATED_FL_VENDEDOR = 2L;

    private static final Boolean DEFAULT_DS_INATIVO = false;
    private static final Boolean UPDATED_DS_INATIVO = true;

    private static final Long DEFAULT_FL_USUARIO = 1L;
    private static final Long UPDATED_FL_USUARIO = 2L;

    private static final LocalDate DEFAULT_DT_CADASTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_CADASTRO = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PessoaRepository pessoaRepository;

    @Inject
    private PessoaSearchRepository pessoaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPessoaMockMvc;

    private Pessoa pessoa;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PessoaResource pessoaResource = new PessoaResource();
        ReflectionTestUtils.setField(pessoaResource, "pessoaRepository", pessoaRepository);
        ReflectionTestUtils.setField(pessoaResource, "pessoaSearchRepository", pessoaSearchRepository);
        this.restPessoaMockMvc = MockMvcBuilders.standaloneSetup(pessoaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pessoa = new Pessoa();
        pessoa.setId(DEFAULT_ID_PESSOA);
        pessoa.setNome(DEFAULT_NM_PESSOA);
        pessoa.setTelefone(DEFAULT_CD_TEL);
        pessoa.setCelular(DEFAULT_CD_CEL);
        pessoa.setFax(DEFAULT_CD_FAX);
        pessoa.setNumero(DEFAULT_NM_NUMERO);
        pessoa.setComplemento(DEFAULT_DS_COMPLEMENTO);
        pessoa.setSituacao(DEFAULT_DS_SITUACAO);
        pessoa.setPessoaFisica(DEFAULT_FL_FISICA);
        pessoa.setEmail(DEFAULT_DS_EMAIL);
        pessoa.setObs(DEFAULT_DS_OBS);
        pessoa.setHistorico(DEFAULT_DS_HISTORICO);
        pessoa.setInativo(DEFAULT_DS_INATIVO);
        pessoa.setCadastro(DEFAULT_DT_CADASTRO);
    }

    @Test
    @Transactional
    public void createPessoa() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // Create the Pessoa

        restPessoaMockMvc.perform(post("/api/pessoas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pessoa)))
                .andExpect(status().isCreated());

        // Validate the Pessoa in the database
        List<Pessoa> pessoas = pessoaRepository.findAll();
        assertThat(pessoas).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa testPessoa = pessoas.get(pessoas.size() - 1);
        assertThat(testPessoa.getId()).isEqualTo(DEFAULT_ID_PESSOA);
        assertThat(testPessoa.getNome()).isEqualTo(DEFAULT_NM_PESSOA);
        assertThat(testPessoa.getTelefone()).isEqualTo(DEFAULT_CD_TEL);
        assertThat(testPessoa.getCelular()).isEqualTo(DEFAULT_CD_CEL);
        assertThat(testPessoa.getFax()).isEqualTo(DEFAULT_CD_FAX);
        assertThat(testPessoa.getLogradouro()).isEqualTo(DEFAULT_ID_LOGRADOURO);
        assertThat(testPessoa.getNumero()).isEqualTo(DEFAULT_NM_NUMERO);
        assertThat(testPessoa.getComplemento()).isEqualTo(DEFAULT_DS_COMPLEMENTO);
        assertThat(testPessoa.getSituacao()).isEqualTo(DEFAULT_DS_SITUACAO);
        assertThat(testPessoa.getPessoaFisica()).isEqualTo(DEFAULT_FL_FISICA);
        assertThat(testPessoa.getEmail()).isEqualTo(DEFAULT_DS_EMAIL);
        assertThat(testPessoa.getObs()).isEqualTo(DEFAULT_DS_OBS);
        assertThat(testPessoa.getHistorico()).isEqualTo(DEFAULT_DS_HISTORICO);
        assertThat(testPessoa.getInativo()).isEqualTo(DEFAULT_DS_INATIVO);
        assertThat(testPessoa.getCadastro()).isEqualTo(DEFAULT_DT_CADASTRO);
    }

    @Test
    @Transactional
    public void checkNm_pessoaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setNome(null);

        // Create the Pessoa, which fails.

        restPessoaMockMvc.perform(post("/api/pessoas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pessoa)))
                .andExpect(status().isBadRequest());

        List<Pessoa> pessoas = pessoaRepository.findAll();
        assertThat(pessoas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkId_logradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaRepository.findAll().size();
        // set the field null
        pessoa.setLogradouro(null);

        // Create the Pessoa, which fails.

        restPessoaMockMvc.perform(post("/api/pessoas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pessoa)))
                .andExpect(status().isBadRequest());

        List<Pessoa> pessoas = pessoaRepository.findAll();
        assertThat(pessoas).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPessoas() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoas
        restPessoaMockMvc.perform(get("/api/pessoas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
                .andExpect(jsonPath("$.[*].id_pessoa").value(hasItem(DEFAULT_ID_PESSOA.intValue())))
                .andExpect(jsonPath("$.[*].nm_pessoa").value(hasItem(DEFAULT_NM_PESSOA.toString())))
                .andExpect(jsonPath("$.[*].cd_tel").value(hasItem(DEFAULT_CD_TEL.toString())))
                .andExpect(jsonPath("$.[*].cd_cel").value(hasItem(DEFAULT_CD_CEL.toString())))
                .andExpect(jsonPath("$.[*].cd_fax").value(hasItem(DEFAULT_CD_FAX.toString())))
                .andExpect(jsonPath("$.[*].id_logradouro").value(hasItem(DEFAULT_ID_LOGRADOURO.intValue())))
                .andExpect(jsonPath("$.[*].nm_numero").value(hasItem(DEFAULT_NM_NUMERO.toString())))
                .andExpect(jsonPath("$.[*].ds_complemento").value(hasItem(DEFAULT_DS_COMPLEMENTO.toString())))
                .andExpect(jsonPath("$.[*].ds_situacao").value(hasItem(DEFAULT_DS_SITUACAO.booleanValue())))
                .andExpect(jsonPath("$.[*].fl_fisica").value(hasItem(DEFAULT_FL_FISICA.booleanValue())))
                .andExpect(jsonPath("$.[*].ds_email").value(hasItem(DEFAULT_DS_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].ds_obs").value(hasItem(DEFAULT_DS_OBS.toString())))
                .andExpect(jsonPath("$.[*].ds_historico").value(hasItem(DEFAULT_DS_HISTORICO.toString())))
                .andExpect(jsonPath("$.[*].fl_vendedor").value(hasItem(DEFAULT_FL_VENDEDOR.intValue())))
                .andExpect(jsonPath("$.[*].ds_inativo").value(hasItem(DEFAULT_DS_INATIVO.booleanValue())))
                .andExpect(jsonPath("$.[*].fl_usuario").value(hasItem(DEFAULT_FL_USUARIO.intValue())))
                .andExpect(jsonPath("$.[*].dt_cadastro").value(hasItem(DEFAULT_DT_CADASTRO.toString())));
    }

    @Test
    @Transactional
    public void getPessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pessoa.getId().intValue()))
            .andExpect(jsonPath("$.id_pessoa").value(DEFAULT_ID_PESSOA.intValue()))
            .andExpect(jsonPath("$.nm_pessoa").value(DEFAULT_NM_PESSOA.toString()))
            .andExpect(jsonPath("$.cd_tel").value(DEFAULT_CD_TEL.toString()))
            .andExpect(jsonPath("$.cd_cel").value(DEFAULT_CD_CEL.toString()))
            .andExpect(jsonPath("$.cd_fax").value(DEFAULT_CD_FAX.toString()))
            .andExpect(jsonPath("$.id_logradouro").value(DEFAULT_ID_LOGRADOURO.intValue()))
            .andExpect(jsonPath("$.nm_numero").value(DEFAULT_NM_NUMERO.toString()))
            .andExpect(jsonPath("$.ds_complemento").value(DEFAULT_DS_COMPLEMENTO.toString()))
            .andExpect(jsonPath("$.ds_situacao").value(DEFAULT_DS_SITUACAO.booleanValue()))
            .andExpect(jsonPath("$.fl_fisica").value(DEFAULT_FL_FISICA.booleanValue()))
            .andExpect(jsonPath("$.ds_email").value(DEFAULT_DS_EMAIL.toString()))
            .andExpect(jsonPath("$.ds_obs").value(DEFAULT_DS_OBS.toString()))
            .andExpect(jsonPath("$.ds_historico").value(DEFAULT_DS_HISTORICO.toString()))
            .andExpect(jsonPath("$.fl_vendedor").value(DEFAULT_FL_VENDEDOR.intValue()))
            .andExpect(jsonPath("$.ds_inativo").value(DEFAULT_DS_INATIVO.booleanValue()))
            .andExpect(jsonPath("$.fl_usuario").value(DEFAULT_FL_USUARIO.intValue()))
            .andExpect(jsonPath("$.dt_cadastro").value(DEFAULT_DT_CADASTRO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPessoa() throws Exception {
        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

		int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa
        pessoa.setId(DEFAULT_ID_PESSOA);
        pessoa.setNome(DEFAULT_NM_PESSOA);
        pessoa.setTelefone(DEFAULT_CD_TEL);
        pessoa.setCelular(DEFAULT_CD_CEL);
        pessoa.setFax(DEFAULT_CD_FAX);
        pessoa.setNumero(DEFAULT_NM_NUMERO);
        pessoa.setComplemento(DEFAULT_DS_COMPLEMENTO);
        pessoa.setSituacao(DEFAULT_DS_SITUACAO);
        pessoa.setPessoaFisica(DEFAULT_FL_FISICA);
        pessoa.setEmail(DEFAULT_DS_EMAIL);
        pessoa.setObs(DEFAULT_DS_OBS);
        pessoa.setHistorico(DEFAULT_DS_HISTORICO);
        pessoa.setInativo(DEFAULT_DS_INATIVO);
        pessoa.setCadastro(DEFAULT_DT_CADASTRO);

        restPessoaMockMvc.perform(put("/api/pessoas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pessoa)))
                .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoas = pessoaRepository.findAll();
        assertThat(pessoas).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoas.get(pessoas.size() - 1);
        assertThat(testPessoa.getId()).isEqualTo(DEFAULT_ID_PESSOA);
        assertThat(testPessoa.getNome()).isEqualTo(DEFAULT_NM_PESSOA);
        assertThat(testPessoa.getTelefone()).isEqualTo(DEFAULT_CD_TEL);
        assertThat(testPessoa.getCelular()).isEqualTo(DEFAULT_CD_CEL);
        assertThat(testPessoa.getFax()).isEqualTo(DEFAULT_CD_FAX);
        assertThat(testPessoa.getLogradouro()).isEqualTo(DEFAULT_ID_LOGRADOURO);
        assertThat(testPessoa.getNumero()).isEqualTo(DEFAULT_NM_NUMERO);
        assertThat(testPessoa.getComplemento()).isEqualTo(DEFAULT_DS_COMPLEMENTO);
        assertThat(testPessoa.getSituacao()).isEqualTo(DEFAULT_DS_SITUACAO);
        assertThat(testPessoa.getPessoaFisica()).isEqualTo(DEFAULT_FL_FISICA);
        assertThat(testPessoa.getEmail()).isEqualTo(DEFAULT_DS_EMAIL);
        assertThat(testPessoa.getObs()).isEqualTo(DEFAULT_DS_OBS);
        assertThat(testPessoa.getHistorico()).isEqualTo(DEFAULT_DS_HISTORICO);
        assertThat(testPessoa.getInativo()).isEqualTo(DEFAULT_DS_INATIVO);
        assertThat(testPessoa.getCadastro()).isEqualTo(DEFAULT_DT_CADASTRO);
    }

    @Test
    @Transactional
    public void deletePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

		int databaseSizeBeforeDelete = pessoaRepository.findAll().size();

        // Get the pessoa
        restPessoaMockMvc.perform(delete("/api/pessoas/{id}", pessoa.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pessoa> pessoas = pessoaRepository.findAll();
        assertThat(pessoas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
