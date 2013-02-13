package gwt.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("lazyget")
public interface LazyTreeGet extends RemoteService {
	

	List<String> greetServer(String type, String input) throws IllegalArgumentException;
}
