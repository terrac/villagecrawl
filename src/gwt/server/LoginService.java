package gwt.server;

import gwt.client.rpc.LoginInfo;
import gwt.server.datamodel.GUser;
import gwt.shared.SideBar;

import java.io.PrintWriter;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;


import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;

public class LoginService {



	public static PersonLoginInfo login(String requestUri,PrintWriter writer
			) {
		return login(requestUri, writer, null);
	}
		public static PersonLoginInfo login(String requestUri, PrintWriter writer
				,HttpServletRequest req) {
		
		
		GUser per = null;
		UserService userService = UserServiceFactory.getUserService();

		User user = userService.getCurrentUser();
		
		LoginInfo loginInfo = new LoginInfo();
		// probably should add in a cookie later that says logged in/ not logged
		// in
		
		String gkey=null;
		if(req != null){
			gkey=req.getParameter("gkey");
		}
		if (user != null) {
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			if(requestUri != null){
				loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
			}
			
			
			per = SDao.getGUserDao().getRN(new Key<GUser>(GUser.class, user.getUserId()));

			if (per == null) {
				per = new GUser(user.getUserId(),user.getNickname());
				SDao.getGUserDao().put(per);
			}
			if (writer != null) {
				writer.println(SideBar.getServletRep("Sign Out",loginInfo.getLogoutUrl(),gkey));
			}
		} else {
			loginInfo.setLoggedIn(false);
			if(requestUri != null){
				loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
			}
			
			if (writer != null) {
				writer.println(SideBar.getServletRep("Sign In",loginInfo.getLoginUrl(),gkey));
			}
		}
		return new PersonLoginInfo(per, loginInfo);
	}



}
