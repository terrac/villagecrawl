package gwt.server;

import gwt.server.datamodel.FileResource;
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
public class DisplayResourceManager extends HttpServlet {



	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PersonLoginInfo info =LoginService.login(request.getRequestURI(),response.getWriter());
		
		if(!info.loginInfo.isLoggedIn()){
			response.getWriter().write("You must login to use this service");
			return;
		}
		
		
		
		
		
		
		
		
		
		for(FileResource fr : info.person.getResourceManager().getResources()){
			response.getWriter().write("<br><br><a href=\""+fr.getName()+"\">"+fr.getName()+ "</a> delete link here</br>");
			//add a delete
		}
		response.getWriter().write("<br><br><br><a href=/createresource>Add Resource</a></br>");
		
		
		
		
		
		
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
