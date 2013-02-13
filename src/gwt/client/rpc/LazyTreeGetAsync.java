package gwt.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface LazyTreeGetAsync {
	void greetServer(String type,String input, AsyncCallback<List<String>> callback)
			throws IllegalArgumentException;
}
