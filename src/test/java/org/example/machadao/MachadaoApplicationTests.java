package org.example.machadao;

import org.example.machadao.relic.RelicRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:machadao-test;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class MachadaoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RelicRepository relicRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void relicCrudEndpointsWork() throws Exception {
        relicRepository.deleteAll();

        MvcResult createResult = mockMvc.perform(post("/api/relics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code": "wood-axe-hermes",
                                  "name": "Wood_Axe_Hermes",
                                  "description": "Ficha fictícia da fábula.",
                                  "illustrativeValueUsd": 5000.00
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fictional").value(true))
                .andExpect(jsonPath("$.name").value("Wood_Axe_Hermes"))
                .andReturn();
        String location = createResult.getResponse().getHeader("Location");
        assertNotNull(location);
        String resourcePath = URI.create(location).getPath();

        mockMvc.perform(get(resourcePath))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("wood-axe-hermes"));

        mockMvc.perform(put(resourcePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code": "wood-axe-hermes",
                                  "name": "Wood_Axe_Hermes",
                                  "description": "Descrição atualizada, ainda fictícia.",
                                  "illustrativeValueUsd": 5000.00
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Descrição atualizada, ainda fictícia."));

        mockMvc.perform(delete(resourcePath))
                .andExpect(status().isNoContent());
        mockMvc.perform(get(resourcePath))
                .andExpect(status().isNotFound());
    }
}
