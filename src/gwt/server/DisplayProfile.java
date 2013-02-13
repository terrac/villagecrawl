package gwt.server;

import gwt.server.datamodel.GUser;
import gwt.server.datamodel.SaveGame;
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
import com.googlecode.objectify.Query;

@RemoteServiceRelativePath("displaypersongames")
public class DisplayProfile extends HttpServlet {



	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PersonLoginInfo info =LoginService.login(request.getRequestURI(),response.getWriter());
		
		if(!info.loginInfo.isLoggedIn()){
			response.getWriter().write("You must login to use this service");
			return;
		}
//		ServerGame sg;
//		if(request.getParameter("new") != null){
//			sg = new ServerGame("new");
//			
//			JsonData jd = new JsonData("new");
//			jd.setData(new Blob("aoeu".getBytes()));
//			Key k=SDao.getJsonDataDao().put(jd);
//			sg.jsonData.add(k);
//			SDao.getServerGameDao().put(sg);
//		} else {
			long id;
//			try {
//				id=Long.parseLong(request.getParameter("gkey"));
//			} catch (NumberFormatException e) {
//				response.getWriter().write("invalid format for key");
//				return;
//			}
//			sg=SDao.getServerGameDao().getRN(id);
//		}
		
		response.getWriter().write("<br><br><a href=/resourcemanager>manage resources</a><br><br>");
		
		
		
		
		if(!info.person.hasTitle("Early Adopter")){
			response.getWriter().write("The idea is that you buy titles which credit your account with money.  And then you can setup some algorithm that pays the game creators based on your ratings/playtime/etc.<br><form action=\"https://www.paypal.com/cgi-bin/webscr\" method=\"post\">\r\n" + 
					"<input type=\"hidden\" name=\"cmd\" value=\"_s-xclick\">\r\n" + 
					"<input type=\"hidden\" name=\"hosted_button_id\" value=\"7VFFG7J8U2D7A\">\r\n" + 
					"<input type=\"image\" src=\"https://www.paypalobjects.com/en_US/i/btn/btn_buynowCC_LG.gif\" border=\"0\" name=\"submit\" alt=\"PayPal - The safer, easier way to pay online!\">\r\n" + 
					"<img alt=\"\" border=\"0\" src=\"https://www.paypalobjects.com/en_US/i/scr/pixel.gif\" width=\"1\" height=\"1\">\r\n" + 
					"</form>\r\n" + 
					"\r\n" + 
					"");
		} else {
			response.getWriter().write("You have the title early adopter, good on ya");
		}
		
		
		
		for(SaveGame sg :SDao.getSaveGameDao().getQByProperty("ownerId", info.person.id)){
			Query<JsonData> qjd=SDao.getJsonDataDao().getQByProperty("gamekey", sg.gamekey);
			
			//qjd.filter("ownerId", sg.id);
			response.getWriter().write("<br> "+sg.name);
			for(JsonData jd : qjd){
				response.getWriter().write("<br><br><a href=\"/editjson?jsonkey="+jd.getKey().getId()+"\">"+jd.getName()+ "</a></br>");
			}
		}
		
		
		
		
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
