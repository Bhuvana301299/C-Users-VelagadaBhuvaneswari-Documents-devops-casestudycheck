package com.myapp.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Ticket")

public class Ticket {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Column(name="TICKET_ID",nullable=false)
	private String ticket_id;
	
	@Column(name="TICKET_STATUS",nullable=false)
	private String ticket_status;
	
	@Column(name="TICKET_DESCRIPTION",nullable=false)	
	private String ticket_description;
	
	
	public Ticket() {
		// TODO Auto-generated constructor stub
	}

	public Ticket(String ticket_id,  String ticket_description, String ticket_status) {
		this.ticket_id = ticket_id;
		this.ticket_status = ticket_status;
		this.ticket_description = ticket_description;
	}

	public String getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}

	public String getTicket_status() {
		return ticket_status;
	}

	public String getTicket_description() {
		return ticket_description;
	}

	public void setTicket_description(String ticket_description) {
		this.ticket_description = ticket_description;
	}

	public void setTicket_status(String ticket_status) {
		this.ticket_status = ticket_status;
	}

	

}