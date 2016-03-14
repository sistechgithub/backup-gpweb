package com.sth.gp.web.rest;

import com.sth.gp.Application;
import com.sth.gp.repository.PrazoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrazoResource REST controller.
 *
 * @see PrazoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PrazoResourceIntTeste {

    @Inject
    private PrazoRepository prazoRepository;

    private MockMvc restPrazoMockMvc;

    @Before
    public void setup() {
        PrazoResource userResource = new PrazoResource();
        ReflectionTestUtils.setField(userResource, "prazoRepository", prazoRepository);
        this.restPrazoMockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }

    @Test
    public void testGetExistingPrazo() throws Exception {
        restPrazoMockMvc.perform(get("/api/prazos/admin")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.lastName").value("Administrator"));
    }

    @Test
    public void testGetUnknownPrazo() throws Exception {
        restPrazoMockMvc.perform(get("/api/prazos/unknown")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
