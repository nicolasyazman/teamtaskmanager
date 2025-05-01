package io.github.nicolasyazman.teamtaskmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import io.github.nicolasyazman.teamtaskmanager.config.WebSecurityConfig;

@WebMvcTest(controllers = HealthController.class, excludeAutoConfiguration = {WebSecurityConfig.class})
@AutoConfigureMockMvc(addFilters = false)
public class HealthControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void shouldReturnOKWhenTryingHealthCheckWhileServerIsRunning() throws Exception {
		this.mockMvc.perform(get("/api/health")).andExpect(status().isOk())
	    .andExpect(content().string("OK"));
	}

}
