package com.fiserv.hibernateenversdemo.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.envers.Audited;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Audited
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class User {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private Long id;

	private String name;

	@ToString.Exclude
	@OneToMany(mappedBy = "user")
	private Set<Post> posts;
}
