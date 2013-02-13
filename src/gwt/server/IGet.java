package gwt.server;

import gwt.shared.datamodel.IClientObject;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface IGet {
	//public Object get( String parentName);
	
	IClientObject get(String parentName, Map<String, IClientObject> prevParams);
}
