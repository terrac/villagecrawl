package gwt.server.build;

import gwt.client.game.AttachUtil;
import gwt.client.main.Game;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.server.LoginService;
import gwt.server.PersonLoginInfo;
import gwt.server.SDao;
import gwt.server.datamodel.FResourceManager;
import gwt.server.datamodel.FileResource;
import gwt.server.datamodel.GUser;
import gwt.server.datamodel.GameList;
import gwt.server.datamodel.SaveGame;
import gwt.server.datamodel.ServerGame;
import gwt.shared.ClientBuild;
import gwt.shared.ClientBuild2;
import gwt.shared.ClientBuildAvU;
import gwt.shared.ClientBuildAvZ;
import gwt.shared.ClientBuildDungeon;
import gwt.shared.DemographicCiv;
import gwt.shared.PrefetchImageList;
import gwt.shared.StatisticalCiv;
import gwt.shared.datamodel.JsonData;
import gwt.shared.datamodel.ServerTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;

public class BuildFeatures extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		PersonLoginInfo info =LoginService.login(req.getRequestURI(),resp.getWriter());
		
		if(!info.loginInfo.isLoggedIn()){
			resp.getWriter().write("You must login to use this service");
			return;
		}
		for(ServerGame sg :info.person.getGamesByCreator()){
			if(GameList.containsShared(sg.getKey().getId())){
				continue;
			}
			for(JsonData jd:SDao.getJsonDataDao().getQByProperty("gamekey", sg.id)){
				SDao.getJsonDataDao().delete(jd);
			}
			SDao.getServerGameDao().delete(sg);
		}
		for(SaveGame sg :SDao.getSaveGameDao().getQByProperty("ownerId", info.person.id)){
			SDao.getSaveGameDao().delete(sg);
		}
		
		
		build(info.person);
		SDao.getGUserDao().put(info.person);
		for(ServerGame sg :info.person.getGamesByCreator()){
			resp.getWriter().write("<br><br><a href=http://127.0.0.1:8888/Villagedc.html?gwt.codesvr=127.0.0.1:9997&gamekey="+sg.getKey().getId()+">"+sg.getName()+"</a></br>");	
		}
		
	}

	public static void build(GUser person) {
		
		
		AttachUtil.shouldRun = false;


		Key<ServerGame> eGame = addFeature(person,"First map (try me)",DemographicCiv.doBasicMap(),DemographicCiv.doActions(DemographicCiv.firstMap()));
		person.add(eGame);

		Key<ServerGame> aGame = addFeature(person,"Spread North (try me)",DemographicCiv.doBasicMap(),DemographicCiv.doActions(DemographicCiv.spreadNorth()));
		person.add(aGame);

		//examples(person);
	}

	public static void examples(GUser person) {
		Key<ServerGame> dGame = addFeature(person,"Demographic Civ (try me)",DemographicCiv.doBasicMap(),DemographicCiv.doActions());
		person.add(dGame);



		Key<ServerGame> fGame = addFeature(person,"Demographic Civ Bigger (try me)",DemographicCiv.doBasicMap(),DemographicCiv.doActions(DemographicCiv.getMap4()));
		person.add(fGame);
		
		Key<ServerGame> firstGame = addFeature(person,"StatcivSmall (try me)",StatisticalCiv.doBasicMap(),ClientBuild2.doPeople(),StatisticalCiv.doActions(),StatisticalCiv.doTechnology());
		person.add(firstGame);
//		
		Key<ServerGame> secondGame = addFeature(person,"Statciv (try me)",StatisticalCiv.doBasicMap(),ClientBuild2.doPeople(),StatisticalCiv.doActionsBigMap(),StatisticalCiv.doTechnology());
		person.add(secondGame);
//		
		Key<ServerGame> sGame = addFeature(person,"Haggle ( work in progress )",ClientBuildDungeon.doBasicDungeon(),ClientBuild2.doPeople(),ClientBuildDungeon.doInitialAdventurers());
		person.add(sGame);

		person.add(addFeature(person,"Adventurers vs Monsters (work in progress)",ClientBuild.doDC1(),ClientBuildAvZ.doInitialAdventurers(false),ClientBuild2.doPeople(),ClientBuildAvZ.doAdventurerScenes(false),ClientBuildAvZ.doFireNecromancer(),ClientBuildAvZ.doIllusionGnome()));
		SDao.getGUserDao().put(person);
	}

	static boolean first = true;
	private static Key<ServerGame> addFeature(GUser user,String string, Game doDC1,PBase ... pbaselist) {
		ServerGame sg = new ServerGame(string);
//		if(first){
//			first = false;
//			sg.id =  1L;
//		}
		Key<ServerGame> sgkey = SDao.getServerGameDao().put(sg);
		
		String export = doDC1.export();
		JsonData jd = new JsonData(doDC1.getName(),true,sgkey.getId());
		jd.setData(new Blob(export.getBytes()));
		
		sg.addMain(SDao.getJsonDataDao().put(jd));
		
		
		
//		String startName = pbaselist[0].getS(VConstants.name);

		
		boolean first = true;
		for(PBase pb : pbaselist){
			String sn = pb.getS(VConstants.name);
			List<String> jcl = pb.getType(VConstants.jsoncache).getList(VConstants.list);
			
			boolean b = pb.getB(VConstants.main);
			jd = new JsonData(sn,b,sgkey.getId(),jcl);
			jd.setData(new Blob(pb.export().getBytes()));
			Key<JsonData> key=SDao.getJsonDataDao().put(jd);
			if(b){
				sg.addMain(key);
				
			} else {
				if(first){
					sg.startJson = key;
					first=false;
				}
				sg.addOther(key);	
			}
			
		}
		
		SDao.getServerGameDao().put(sg);
		
	
		
		return sgkey;
		
		//put exported string into a jsondata on a game
		//do this for like 10 things of varying rules
	}

	protected void addFeature(HttpServletResponse resp, ServerTree st,
			String name, Game doAnimalBabies) throws IOException {
		//SDao.getGameDao().put(doAnimalBabies)
//		ServerGame sg1 = new ServerGame(doAnimalBabies, name);
//		resp.getWriter().write(name+"\n\n");
//		//resp.getWriter().write(sg1.game.fmdMap.keySet()+"\n\n");
//		
//		Key key = SDao.getServerGameDao().put(sg1);
//		ServerTree stc = new ServerTree(name, key);
//		SDao.getTreeDao().put(stc);
//		
//		st.children.add(new Key<ServerTree>(ServerTree.class, name));
//		SDao.getTreeDao().put(st);
	}
	public static void addResources(GUser person){
		for(String a :PrefetchImageList.vault.split(",")){
			File f;
			a = "C:\\Users\\terra\\workspace\\villagedc\\war\\"+a;
			try {
				f = new File(a);
			
				FileResource image = new FileResource(a, "image/png", getBytesFromFile(f));
				Key<FileResource> key=SDao.getFResourceDao().put(image);
				FResourceManager frm = person.getResourceManager();
				frm.frlist.add(key);
				SDao.getFResourceManagerDao().put(frm);
			} catch (
					Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	// Returns the contents of the file in a byte array.
	public static byte[] getBytesFromFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    // Get the size of the file
	    long length = file.length();

	    // You cannot create an array using a long type.
	    // It needs to be an int type.
	    // Before converting to an int type, check
	    // to ensure that file is not larger than Integer.MAX_VALUE.
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }

	    // Create the byte array to hold the data
	    byte[] bytes = new byte[(int)length];

	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    // Close the input stream and return bytes
	    is.close();
	    return bytes;
	}
}
