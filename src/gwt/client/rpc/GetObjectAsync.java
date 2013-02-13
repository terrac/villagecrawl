package gwt.client.rpc;

import gwt.shared.datamodel.IClientObject;

import java.util.Collection;
import java.util.Map;


import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetObjectAsync {

	void getObject(String type, String name,
			AsyncCallback<Map<String, IClientObject>> callback);

	void getObject(String[] type, String[] names,
			AsyncCallback<Map<String, IClientObject>> callback);

	//There could be a bandwidth concern with this eventually
	//you could add an additional method that is an array of boolean values which tells whether or not to send back a specific thing





}
