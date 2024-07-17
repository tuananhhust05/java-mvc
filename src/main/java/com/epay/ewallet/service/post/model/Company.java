package com.epay.ewallet.service.post.model;

public class Company {
	private long id;
	private String shortName;
	private String logo;
	private String logoSocial;

	public Company(long id, String shortName, String logo) {
		super();
		this.id = id;
		this.shortName = shortName;
		this.logo = logo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public String getLogoSocial() {
		return logoSocial;
	}

	public void setLogoSocial(String logoSocial) {
		this.logoSocial = logoSocial;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", shortName=" + shortName + ", logo=" + logo + ", logoSocial=" + logoSocial + "]";
	}

}
