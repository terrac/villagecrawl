package gwt.server.rpc.gets;

import gwt.client.rpc.LoginInfo;
import gwt.server.IGet;
import gwt.server.LoginService;
import gwt.shared.datamodel.CString;
import gwt.shared.datamodel.IClientObject;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class GetLogin implements IGet{

	@Override
	public IClientObject get(String requestUri,
			Map<String, IClientObject> prevParams) {
		LoginInfo loginInfo =LoginService.login(requestUri, null).loginInfo;
		prevParams.put("name", new CString(loginInfo.getNickname()));
		return loginInfo;
	}
}
