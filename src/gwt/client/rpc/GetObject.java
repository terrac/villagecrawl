package gwt.client.rpc;

import gwt.shared.datamodel.IClientObject;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;



@RemoteServiceRelativePath("getobject")
public interface GetObject extends RemoteService {


	public Map<String, IClientObject> getObject( String type, String name)
			throws IllegalArgumentException;

	Map<String, IClientObject> getObject( String[] type, String[] names)
			throws IllegalArgumentException;

}
