package gwt.shared.datamodel;

import java.util.List;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;

public class JsonData implements IClientObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonData() {
	}

	public Blob getData() {
		return data;
	}

	public void setData(Blob data) {
		this.data = data;
	}

	public void setData(String data) {
		setData(new Blob(data.getBytes()));
	}

	public JsonData(String name) {
		super();
		this.name = name;
	}

	public JsonData(String name, boolean b, Long gamekey) {
		super();
		this.name = name;
		main = b;
		this.gamekey = gamekey;
	}

	public JsonData(String name, boolean b, Long gamekey, List<String> jsonCache) {
		super();
		this.name = name;
		main = b;
		this.gamekey = gamekey;
		this.jsonCache = jsonCache;
	}

	Blob data;
	 
	@Id
	public Long id;
	public String name;
	public List<String> jsonCache;
	boolean main;

	public Key<JsonData> getKey() {
		return new Key(JsonData.class, id);
	}

	public String getName() {
		return name;
	}

	public Long gamekey;
	public Long ownerId;
}
