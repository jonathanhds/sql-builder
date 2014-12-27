package com.github.jonathanhds.sqlbuilder.support;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Country {

	private String countrycountryName;

	public Country(String countryName) {
		this.countrycountryName = countryName;
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

		return new EqualsBuilder().append(this.countrycountryName, other.countrycountryName)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.countrycountryName)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("countryName", countrycountryName)
				.toString();
	}

	public String getCountryName() {
		return countrycountryName;
	}

	public void setCountryName(String countryName) {
		this.countrycountryName = countryName;
	}

}