package gwt.server.datamodel;

import gwt.shared.datamodel.VParams;

import java.util.List;

import javax.persistence.Id;

public class ServerCreation {
	@Id Long id;
	
	String classname;
	List<VParams> parameters;
	String type;
}
