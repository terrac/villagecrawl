package gwt.client.rpc;

import gwt.shared.datamodel.IClientObject;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("sendobject")
public interface SendObject extends RemoteService {

	void sendObjectDefault( IClientObject obj, String parentName,
			String prevName);

	void sendObjectDefault( IClientObject[] obj, String parentName,
			String[] prevName);

	void sendObjectDefault( String obj, String parentName,
			String prevName);

	void deleteObjectDefault( String type, String parentName,
			String prevName);

	void sendObjectDefault(IClientObject[] obj, String parentName,
			String prevName);

	




}
