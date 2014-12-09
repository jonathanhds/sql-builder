package com.github.sqlbuilder.jonathanhds.support;


import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Person {

	private String name;

	private Date birthDay;

	private Country country;

	public Person(String name, Date birthDay, Country country) {
		this.name = name;
		this.birthDay = birthDay;
		this.country = country;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Person)) {
			return false;
		}

		Person other = (Person) obj;

		return new EqualsBuilder().append(this.name, other.name)
								  .append(this.birthDay, other.birthDay)
								  .append(this.country, other.country)
								  .isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.name)
									.append(this.birthDay)
									.append(this.country)
									.toHashCode();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("name", name)
																		  .append("birthDay", birthDay)
																		  .append("country", country)
																		  .toString();
	}

	public String getName() {
		return name;
	}

	public Person setName(String name) {
		this.name = name;
        return this;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public Person setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
        return this;
	}

	public Country getCountry() {
		return country;
	}

	public Person setCountry(Country country) {
		this.country = country;
        return this;
	}

}