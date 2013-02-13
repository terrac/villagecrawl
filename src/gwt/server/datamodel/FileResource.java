package gwt.server.datamodel;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;

public class FileResource {
	
	public FileResource() {
	
	}
	@Id
	String name;
	
	String contentType;
	Blob data;
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Blob getData() {
		return data;
	}
	public void setData(Blob data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public FileResource(String name, String contentType, byte[] data) {
		super();
		this.name = name;
		this.contentType = contentType;
		this.data = new Blob(data);
	}
}
