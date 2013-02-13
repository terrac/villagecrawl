package gwt.server;

import gwt.server.datamodel.GUser;
import gwt.server.datamodel.ServerGame;
import gwt.shared.ClientBuild;
import gwt.shared.datamodel.JsonData;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("displaypersongames")
public class Paid extends HttpServlet {



	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PersonLoginInfo info =LoginService.login(request.getRequestURI(),response.getWriter());
		
		if(!info.loginInfo.isLoggedIn()){
			response.getWriter().write("You must login to use this service");
			return;
		}
		
		info.person.titles.add("Early Adopter");
		
		SDao.getGUserDao().put(info.person);
		
		
		
		
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
