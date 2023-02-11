package com.secure.api.token.auth.server;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/*
Doesn't start the server, but only test below layer
 */
@SpringBootTest
@AutoConfigureMockMvc
class PandaControllerWebAppTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPandas() throws Exception {
        this.mockMvc.perform(get("/pandas")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("kongfu")));
    }
}