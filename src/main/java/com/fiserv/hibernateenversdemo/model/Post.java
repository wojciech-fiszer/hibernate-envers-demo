package com.fiserv.hibernateenversdemo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Audited
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Post {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id;

	private String content;

	@ManyToOne
	@JoinColumn
	@ToString.Exclude
	private User user;
}
