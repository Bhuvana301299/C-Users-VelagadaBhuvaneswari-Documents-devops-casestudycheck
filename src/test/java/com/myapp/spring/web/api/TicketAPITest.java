package com.myapp.spring.web.api;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.myapp.spring.model.Ticket;

import com.myapp.spring.repository.TicketRepository;

@SpringBootTest
@AutoConfigureMockMvc

public class TicketAPITest {

	@MockBean
	private TicketRepository repository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Test Ticket by ID - GET /admin/tickets/")
	@WithMockUser(username="admin",password="password",roles="ADMIN")
	public void testGetTicketsById() throws Exception {
		
		//Prepare Mock Product
		Ticket ticket =new Ticket("1","late delivery","unresolved");
		
		// Prepare Mock Service Method
		
		doReturn(Optional.of(ticket)).when(repository).findById(ticket.getTicket_id());
		
		// Perform GET Request
		
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/tickets/{id}","1"))
		// Validate Status should be 200 ok and json response recived
		
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		//Validate Response body
		
		.andExpect(jsonPath("$.ticket_id",is("1")))
		.andExpect(jsonPath("$.ticket_description",is("late delivery")))
		.andExpect(jsonPath("$.ticket_status",is("unresolved")));
		
		
	}
	@Test
	@DisplayName("Test All Product - GET /admin/tickets")
	@WithMockUser(username="admin",password="password",roles="ADMIN")
	public void testGetAllTicketsById() throws Exception {
		
		//Prepare Mock Product
		Ticket ticket1 =new Ticket("1","late delivery","unresolved");
		Ticket ticket2 =new Ticket("2","late delivery","resolved");
		
		List<Ticket> ticket = new ArrayList<>();
		ticket.add(ticket1);
		ticket.add(ticket2);
		
		// Prepare Mock Service Method
		
		doReturn(ticket).when(repository).findAll();
		
		// Perform GET Request
		
		mockMvc.perform(MockMvcRequestBuilders.get("/admin/tickets"))
		// Validate Status should be 200 ok and json response recived
		
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		//Validate Response body
		
		//Object1
		.andExpect(jsonPath("$[0].ticket_id",is("1")))
		.andExpect(jsonPath("$[0].ticket_description",is("late delivery")))
		.andExpect(jsonPath("$[0].ticket_status",is("unresolved")))
		

		//Object2	
		.andExpect(jsonPath("$[1].ticket_id",is("2")))
		.andExpect(jsonPath("$[1].ticket_description",is("late delivery")))
		.andExpect(jsonPath("$[1].ticket_status",is("resolved")));
	
		
	}

	@Test
	@DisplayName("Test Add New Ticket")
	@WithMockUser(username="admin",password="password",roles="ADMIN")
	public void testAddNewTickets() throws Exception {
		
		//Prepare Mock Product
		Ticket ticket1 =new Ticket("1","late delivery","unresolved");
		
		Ticket mockticket =new Ticket("1","late delivery","unresolved");
		
		// Prepare Mock Service Method
		
		doReturn(mockticket).when(repository).save(ArgumentMatchers.any());
		
		// Perform GET Request
		
		mockMvc.perform(post("/admin/tickets")
		// Validate Status should be 200 ok and json response recived
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(new ObjectMapper().writeValueAsString(ticket1)))
		
		//Validate Response body
		
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.ticket_id",is("1")))
		.andExpect(jsonPath("$.ticket_description",is("late delivery")))
		.andExpect(jsonPath("$.ticket_status",is("unresolved")));
		
		
	}
	
	
}