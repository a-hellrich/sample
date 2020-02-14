package de.derandy.restTest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Person {

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private Long id;
		@JsonProperty("name")
		private String name;
		 @JsonProperty("lastname")
		private String lastname;
		 @JsonProperty("zipcode")
		private String zipcode;
		 @JsonProperty("city")
		private String city;
		 @JsonProperty("color")
		private String color;
		
		protected Person() {}
		
		public Person(String name, String lastname, String zipcode, String city, String color) {
			this.name = name;
			this.lastname = lastname;
			this.zipcode = zipcode;
			this.city = city;
			this.color = color;
		}

		@Override
		public String toString() {
			return "Person [id=" + id + ", name=" + name + ", lastname=" + lastname + ", zipcode=" + zipcode + ", city="
					+ city + ", color=" + color + "]";
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getZipcode() {
			return zipcode;
		}

		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
		
		
}
