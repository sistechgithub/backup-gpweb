package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.domain.Produto;
import com.sth.gp.repository.ProdutoRepository;
import com.sth.gp.repository.search.ProdutoSearchRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProdutoResource REST controller.
 *
 * @see ProdutoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProdutoResourceIntTest {

    private static final String DEFAULT_NM_PRODUTO = "AAAAA";
    private static final String UPDATED_NM_PRODUTO = "BBBBB";

    private static final BigDecimal DEFAULT_VL_CUSTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_CUSTO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_QT_SALDO = new BigDecimal(1);
    private static final BigDecimal UPDATED_QT_SALDO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VL_VENDA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VL_VENDA = new BigDecimal(2);

    @Inject
    private ProdutoRepository produtoRepository;

    @Inject
    private ProdutoSearchRepository produtoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProdutoMockMvc;

    private Produto produto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProdutoResource produtoResource = new ProdutoResource();
        ReflectionTestUtils.setField(produtoResource, "produtoRepository", produtoRepository);
        ReflectionTestUtils.setField(produtoResource, "produtoSearchRepository", produtoSearchRepository);
        this.restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        produto = new Produto();
        produto.setNmProduto(DEFAULT_NM_PRODUTO);
        produto.setVlCusto(DEFAULT_VL_CUSTO);
        produto.setQtSaldo(DEFAULT_QT_SALDO);
        produto.setVlVenda(DEFAULT_VL_VENDA);
    }

    @Test
    @Transactional
    public void createProduto() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto

        restProdutoMockMvc.perform(post("/api/produtos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(produto)))
                .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtos = produtoRepository.findAll();
        assertThat(produtos).hasSize(databaseSizeBeforeCreate + 1);
        Produto testProduto = produtos.get(produtos.size() - 1);
        assertThat(testProduto.getNmProduto()).isEqualTo(DEFAULT_NM_PRODUTO);
        assertThat(testProduto.getVlCusto()).isEqualTo(DEFAULT_VL_CUSTO);
        assertThat(testProduto.getQtSaldo()).isEqualTo(DEFAULT_QT_SALDO);
        assertThat(testProduto.getVlVenda()).isEqualTo(DEFAULT_VL_VENDA);
    }

    @Test
    @Transactional
    public void getAllProdutos() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtos
        restProdutoMockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
                .andExpect(jsonPath("$.[*].nmProduto").value(hasItem(DEFAULT_NM_PRODUTO.toString())))
                .andExpect(jsonPath("$.[*].vlCusto").value(hasItem(DEFAULT_VL_CUSTO.intValue())))
                .andExpect(jsonPath("$.[*].qtSaldo").value(hasItem(DEFAULT_QT_SALDO.intValue())))
                .andExpect(jsonPath("$.[*].vlVenda").value(hasItem(DEFAULT_VL_VENDA.intValue())));
    }

    @Test
    @Transactional
    public void getProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(produto.getId().intValue()))
            .andExpect(jsonPath("$.nmProduto").value(DEFAULT_NM_PRODUTO.toString()))
            .andExpect(jsonPath("$.vlCusto").value(DEFAULT_VL_CUSTO.intValue()))
            .andExpect(jsonPath("$.qtSaldo").value(DEFAULT_QT_SALDO.intValue()))
            .andExpect(jsonPath("$.vlVenda").value(DEFAULT_VL_VENDA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProduto() throws Exception {
        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

		int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto
        produto.setNmProduto(UPDATED_NM_PRODUTO);
        produto.setVlCusto(UPDATED_VL_CUSTO);
        produto.setQtSaldo(UPDATED_QT_SALDO);
        produto.setVlVenda(UPDATED_VL_VENDA);

        restProdutoMockMvc.perform(put("/api/produtos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(produto)))
                .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtos = produtoRepository.findAll();
        assertThat(produtos).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtos.get(produtos.size() - 1);
        assertThat(testProduto.getNmProduto()).isEqualTo(UPDATED_NM_PRODUTO);
        assertThat(testProduto.getVlCusto()).isEqualTo(UPDATED_VL_CUSTO);
        assertThat(testProduto.getQtSaldo()).isEqualTo(UPDATED_QT_SALDO);
        assertThat(testProduto.getVlVenda()).isEqualTo(UPDATED_VL_VENDA);
    }

    @Test
    @Transactional
    public void deleteProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

		int databaseSizeBeforeDelete = produtoRepository.findAll().size();

        // Get the produto
        restProdutoMockMvc.perform(delete("/api/produtos/{id}", produto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Produto> produtos = produtoRepository.findAll();
        assertThat(produtos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
