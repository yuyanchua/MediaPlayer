package utility;

public class Media {
	private String name;
	private String url;
	
	public Media() {}
	
	public Media(String name, String url) {
		setName(name);
		setUrl(url);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return String.format("%s\n%s\n", getName(), getUrl());
	}
	
}
