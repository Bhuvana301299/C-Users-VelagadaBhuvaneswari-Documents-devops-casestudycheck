package com.myapp.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.spring.model.Ticket;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, String>{

	/*
	@Query(value = "insert into ticket(ticket_id, ticket_description , ticket_status) VALUES (?1, ?2 ,?3)", nativeQuery = true)
	Ticket saveNewTicket(Ticket ticket);
	//select * from product where price>
	
	*/
	
	
    
}