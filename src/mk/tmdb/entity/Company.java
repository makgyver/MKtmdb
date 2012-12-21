package mk.tmdb.entity;

import java.net.URL;

import mk.tmdb.core.Constants;
import net.sf.json.JSONObject;

public class Company extends CompanyThumbnail {

	//region Fields
	
	private String description = null;
	private String headquarters = null;
	private URL homepage = null;
	private String logoPath = null;
	private String parentCompany = null;
	
	//endregion
	
	public Company(JSONObject json) {
		super(json);
		parseJSON(json);
	}
	
	public Company(CompanyThumbnail company) {
		this(company.getOriginJSON());
	}

	//region Getters/Setters
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isDescriptionSet() {
		return description != null;
	}

	public String getHeadquarters() {
		return headquarters;
	}

	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}
	
	public boolean isHeadquartersSet() {
		return headquarters != null;
	}

	public URL getHomepage() {
		return homepage;
	}

	public void setHomepage(URL homepage) {
		this.homepage = homepage;
	}

	public boolean isHomepageSet() {
		return homepage != null;
	}
	
	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public boolean isLogoPathSet() {
		return logoPath != null;
	}
	
	public String getParentCompany() {
		return parentCompany;
	}

	public void setParentCompany(String parentCompany) {
		this.parentCompany = parentCompany;
	}
	
	public boolean isParentCompanySet() {
		return parentCompany != null;
	}
	
	//endregion
	
	@Override
	protected boolean parseJSON(JSONObject json) {
		
		if (json.has(Constants.DESCRIPTION)) setDescription(json.getString(Constants.DESCRIPTION));
		if (json.has(Constants.HOMEPAGE)) setDescription(json.getString(Constants.HOMEPAGE));
		if (json.has(Constants.PARENT_COMPANY)) setDescription(json.getString(Constants.PARENT_COMPANY));
		if (json.has(Constants.LOGO_PATH)) setDescription(json.getString(Constants.LOGO_PATH));
		if (json.has(Constants.HEADQUARTERS)) setDescription(json.getString(Constants.HEADQUARTERS));
		
		return true;
	}
	
}
