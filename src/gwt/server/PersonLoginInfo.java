package gwt.server;

import gwt.client.rpc.LoginInfo;
import gwt.server.datamodel.GUser;

public class PersonLoginInfo {
	public PersonLoginInfo() {
		// TODO Auto-generated constructor stub
	}
	public GUser person;
	public PersonLoginInfo(GUser person, LoginInfo loginInfo) {
		super();
		this.person = person;
		this.loginInfo = loginInfo;
		this.site = site;
	
	}
	public LoginInfo loginInfo;
	
	public String site;
}
