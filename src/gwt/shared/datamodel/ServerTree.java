package gwt.shared.datamodel;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed
public class ServerTree implements IClientObject {

	public ServerTree() {
	}
	public ServerTree(String name, Key data) {
		super();
		this.name = name;
		this.data = data;
	}
	@Id String name;
	
	public List<Key<ServerTree>> children = new ArrayList();
	public Key data;
}
