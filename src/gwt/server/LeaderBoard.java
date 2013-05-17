package gwt.server;

import gwt.client.statisticalciv.generator.nomadic.PopulationGenerator;
import gwt.server.datamodel.FileResource;
import gwt.server.datamodel.FResourceManager;
import gwt.server.datamodel.GameList;
import gwt.server.datamodel.ServerGame;
import gwt.server.datamodel.GameList.Popular;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.appengine.api.datastore.Blob;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.objectify.Key;

public class LeaderBoard extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}
	/**
	 * Leaderboard has an object that has a list of games that has been shared
	 * Build features does not delete if those games are in this list
	 * every time a game is played its popularity goes up and it might go up on the popularity list
	 * it shows a list of newest (ordered all the way back)
	 * it has a list of most popular (up to 100) with descriptions and specific names
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PersonLoginInfo info =LoginService.login(req.getRequestURI(),res.getWriter(),req);
		
		if(!info.loginInfo.isLoggedIn()){
			res.getWriter().write("You must login to use this service");
			return;
		}
		GameList gl = GameList.getGameList();
		
		String parameter = req.getParameter("gkey");
		if(parameter != null){
//			if(info.person == null||!info.person.id.equals(id)){
//				return;
//			}
			gl.addToShared(Long.parseLong(parameter));
		}
		
		res.getWriter().write("All shared games:<br>");
		for(GameList.Popular p : gl.sharedGames){
			ServerGame game=SDao.getServerGameDao().getRN(p.key);
			if(game == null){
				continue;
			}
			res.getWriter().write("<br><a href=\"/displayjsongame?gkey="+game.getKey().getId()+"\">"+game.getName()+ "</a></br>");				
		}
		
		res.getWriter().write("<br>Popular games:<br>");
		for(GameList.Popular p : gl.getMostPopular()){
			ServerGame game=SDao.getServerGameDao().get(p.key);
			res.getWriter().write("<br><a href=\"/displayjsongame?gkey="+game.getKey().getId()+"\">"+game.getName()+ "</a></br>");				
		}

	}
}