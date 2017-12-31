package com.github.jonathanhds.sqlbuilder.support;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Country {

	private String countryName;

	public Country(String countryName) {
		this.countryName = countryName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Country)) {
			return false;
		}

		Country other = (Country) obj;

		return new EqualsBuilder().append(this.countryName, other.countryName)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.countryName)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("countryName", countryName)
				.toString();
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}