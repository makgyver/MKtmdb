package mk.tmdb.entity;

import java.net.MalformedURLException;
import java.net.URL;

import mk.tmdb.core.Constants;
import mk.tmdb.utils.Log;
import net.sf.json.JSONObject;

public class Company extends CompanyThumbnail {

	//region Fields
	
	private String description = null;
	private String headquarters = null;
	private URL homepage = null;
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
		if (json.has(Constants.HOMEPAGE))
			try {
				setHomepage(new URL(json.getString(Constants.HOMEPAGE)));
			} catch (MalformedURLException e) {
				Log.print(e);
			}
		if (json.has(Constants.PARENT_COMPANY)) setParentCompany(json.getString(Constants.PARENT_COMPANY));
		if (json.has(Constants.HEADQUARTERS)) setHeadquarters(json.getString(Constants.HEADQUARTERS));
		
		return true;
	}
	
}
