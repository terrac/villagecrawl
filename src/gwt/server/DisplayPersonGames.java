package gwt.server;

import gwt.server.datamodel.GUser;
import gwt.server.datamodel.ServerGame;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("displaypersongames")
public class DisplayPersonGames extends HttpServlet {



	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PersonLoginInfo info =LoginService.login(request.getRequestURI(),response.getWriter());
		
		if(!info.loginInfo.isLoggedIn()){
			response.getWriter().write("You must login to use this service");
			return;
		}
		
		
		
		
		
		
		
		
		Key<GUser> pkey = info.person.getKey();
		response.getWriter().write("<br>");
		for(ServerGame game :info.person.getGamesByCreator()){
			
			response.getWriter().write("<br><a href=\"/displayjsongame?gkey="+game.getKey().getId()+"\">"+game.getName()+ "</a></br>");	
		}
		response.getWriter().write("<br><br><br><a href=/gameedit?new=true>Start a new game</a></br>");
		
		
		
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
