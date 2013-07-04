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
public class DisplayAdvancedEdit extends HttpServlet {



	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PersonLoginInfo info =LoginService.login(request.getRequestURI(),response.getWriter(),request);
		
		if(!info.loginInfo.isLoggedIn()){
			response.getWriter().write("You must login to use this service");
			return;
		}
		ServerGame sg;
		if(request.getParameter("new") != null){
			sg = new ServerGame("new");
			
			JsonData jd = new JsonData("new");
			jd.setData(new Blob("aoeu".getBytes()));
			Key k=SDao.getJsonDataDao().put(jd);
			sg.addOther(k);
			SDao.getServerGameDao().put(sg);
		} else {
			long id;
			try {
				id=Long.parseLong(request.getParameter("gkey"));
			} catch (NumberFormatException e) {
				response.getWriter().write("invalid format for key");
				return;
			}
			sg=SDao.getServerGameDao().getRN(id);
		}
		
		
		
		
		
		
		
		for(JsonData jd :sg.getMainJsonDatasByGame()){
			response.getWriter().write("<br><br><a href=\"/editjson?jsonkey="+jd.getKey().getId()+"\">"+jd.getName()+ "</a></br>");
			//add a delete
		}
		for(JsonData jd :sg.getOtherJsonDatasByGame()){
			response.getWriter().write("<br><br><a href=\"/editjson?jsonkey="+jd.getKey().getId()+"\">"+jd.getName()+ "</a></br>");
			//add a delete
		}
		//response.getWriter().write("<br><br><br><a href=/editjson?new=true&gkey="+sg.getKey().getId()+">Start a new json</a></br>");
		
		
		response.getWriter().write("<br><br><br><a href=/?gamekey="+sg.getKey().getId()+"><h1 style=\"font-size:200%\">Play "+sg.getName()+"</h1></a></br>");
		
		
		
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
