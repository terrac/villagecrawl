package gwt.client.rpc;


import gwt.shared.datamodel.IClientObject;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SendObjectAsync {

	
	

	void sendObjectDefault(IClientObject obj, String parentName,
			String prevName, AsyncCallback<Void> callback);

	void sendObjectDefault(IClientObject[] obj, String parentName,
			String[] prevName, AsyncCallback<Void> callback);

	void sendObjectDefault(String obj, String parentName, String prevName,
			AsyncCallback<Void> callback);

	void deleteObjectDefault(String type, String parentName,
			String prevName, AsyncCallback<Void> callback);

	void sendObjectDefault(IClientObject[] obj, String parentName,
			String prevName, AsyncCallback<Void> callback);


}
