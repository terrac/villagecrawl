package gwt.server.datamodel;

import gwt.client.map.FullMapData;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Serialized;

public class ServerBag {
	@Parent
	public Key<GUser> user;

	@Id Long id;
	@Serialized
	public FullMapData editBag;
	
	String name;

	public ServerBag(Key<GUser> user, FullMapData editBag, String name) {
		super();
		this.user = user;
		this.editBag = editBag;
		this.name = name;
	}
	public ServerBag() {
	
	}
}
