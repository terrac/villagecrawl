package gwt.client;

import gwt.client.game.buildgame;
import gwt.client.game.util.FlowControlException;
import gwt.client.main.Game;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.rpc.GWTTimer;
import gwt.client.rpc.GetObject;
import gwt.client.rpc.GetObjectAsync;
import gwt.client.rpc.LoginInfo;
import gwt.shared.SideBar;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.JsonDataList;
import gwt.test.testhtml;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EntryPoint extends SideBar implements com.google.gwt.core.client.EntryPoint {
	{
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void onUncaughtException(Throwable e) {
				if(e instanceof FlowControlException){
					GWTTimer.reinit = true;
					return;
				}
				if(e instanceof UmbrellaException){
					UmbrellaException ue = (UmbrellaException) e;
					
				
					for(Throwable t : ue.getCauses()){
						if(t instanceof FlowControlException){
							GWTTimer.reinit = true;
							return;
						}
					}
				}
				
				e.printStackTrace();
				if(e instanceof UmbrellaException){
					UmbrellaException ue = (UmbrellaException) e;
					
					String mes = "";
					for(Throwable t : ue.getCauses()){
						mes+= t.getMessage();
						t.printStackTrace();
					}
					Window.alert(""+mes);
					return;
				}
				
				Window.alert(e.getMessage());
			}
		});
		
	}
	GetObjectAsync goa = GWT.create(GetObject.class);
	@Override
	public void onModuleLoad() {
		
		//String person=Window.Location.getParameter("person");
		String gamekey=Window.Location.getParameter("gamekey");
		
		sideLoad(new String[] {"login","game","savegame" }, new String[] {
				 GWT.getHostPageBaseURL(),gamekey,"temporary" });
		
		//Log.shouldDisplay = false;
		
		
	}
	VerticalPanel panel = new VerticalPanel();
	boolean pressed = false;
	
	@Override
	protected void loadMain(Map<String, IClientObject> result) {
		
		mobile = checkMobile();
		
		
//		ServerTree st=(ServerTree) result.get("gametree");
		
		//populate using the name and the list on st
		
		//then clicking on a specific tree will pull its associated data, the game in this case
//		Tree games = new Tree();
//		
//		panel.add(games);
//		
//		
//		//panel.add(new HTML("<br><br>"));
//		RootPanel rootPanel = RootPanel.get("mainpage");
//		if(rootPanel != null){
//			rootPanel.add(panel);
//			
//		
//		} else {
//			return;
//		}
		
//		TreeItem root = games.addItem("root");
//		
//		
//		//callGame(null, "ebbandflow");
//		games.addSelectionHandler(new SelectionHandler<TreeItem>() {
//			
//			@Override
//			public void onSelection(final SelectionEvent<TreeItem> event) {
//				
//				if(pressed){
//					return;
//				}
//				pressed = true;
//				String text = event.getSelectedItem().getText();
//				callGame(event, text);
//			}
//		});
		
		//add first and auto select
//		for(Key<ServerTree> key:st.children){
//			
//			TreeItem item=root.addItem(key.getName());
//			if(key.getName().equals("Jomon people")){
//				games.setSelectedItem(item);
//			}
//			
//		}
		loginInfo = (LoginInfo) result.get("login");
		JsonDataList jdl=(JsonDataList) result.get("game");
		 
		if(Window.Location.getParameter("gamekey") != null ){
			if(jdl == null){
				Window.alert("game doesn't exist");
				return;
			} 
			String seed=Window.Location.getParameter("seed"); 
			Game g = new Game();
			
			
			game = g;
			testhtml.setup(g);
			for(String a : jdl.jsonData){
				buildgame.parseFile(g,a);
			}
			
			JsonDataList sdl=(JsonDataList)  result.get("savegame");
			if(sdl != null){
				for(String a : sdl.jsonData){
					buildgame.parseFile(g,a);
				}
			}
			
			buildgame.parseFile(g,sdl.currentJson);
			game.jsonCache = sdl.jsonCache;
			game.jsonCache.put(g.getName(), sdl.currentJson);
			
			buildgame.afterParse(g);
			
			if(seed != null){
				g.put(VConstants.seed, seed);
			}
			
			g.execute();
			
			
		} 
		else {
			RootPanel rootPanel = RootPanel.get("mainpage");
			rootPanel.add(new Anchor("latest game","/?gamekey=1"));
		}
		
			
	
		
		
		
		
		
		
		//populate first set and lazily populate further
		//this comes from servergame
		
		//servergame should be prepopulated with features
		
		//clicking on a game (as opposed to the plus sign)
		//will pull down the inital map and the tree
		
		//the game wont start yet though, initially
		//it will just display itself in a smaller screen that has a start and an edit button
		
		
		//edit will open up the map to a full screen and the tree again (don't optimize now)
		//from there there will be an ability to place templates and items on the map.
		//in addition to items there will be a list of plants.  Templates will be either people or animals
		//and there will also be a list of generic sprites.  These are simple objects that are either obstacles or gates
		//note that someone could supply their own sprites by passing in a url, those available sprites would be used first
		//clicking on a specific tile will show a list of things on that tile and those can be removed or deleted
		
		
		//once that is done if you hit start it will eventually save, but for now it just starts like regular
		//(note might need to remove initial diffs to ensure a speedy start
		
		
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		// TODO Auto-generated method stub
//		OutputDirector.mpane= new HtmlOut();
//		
//		Game g = (Game) result.get("game");
//		
//		OutputDirector.timer = new GWTTimer(g);
//		//System.out.println(result);
//		try {
//			g.execute("");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//serverside

	}
	public static Game game;
	public static boolean mobile = false;
	public static LoginInfo loginInfo;
	
//	private void callGame(final SelectionEvent<TreeItem> event, String text) {
//		panel.add(new Label("StartCall "+text));
//		goa.getObject(new String[]{"gametree","game"}, new String[]{text,null}, new AsyncCallback<Map<String,IClientObject>>() {
//			
//			@Override
//			public void onSuccess(Map<String, IClientObject> result) {
//				panel.add(new Label("Success"));
//				ServerTree st=(ServerTree) result.get("gametree");
//				if(st == null){
//					return;
//				}
//				game=(Game) result.get("game");
//				
//				
//				if( game != null){
//					testhtml.setup(game);
//					
//					game.execute("");
//				}
//				if(event != null){
//					for(Key<ServerTree> key:st.children){
//						event.getSelectedItem().addItem(key.getName());
//					}
//				}
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				Window
//				.alert("Failure of getting the objects for edit: "
//						+ caught.getMessage());
//				
//			}
//		});
//	}

	
    static final String[] MOBILE_SPECIFIC_SUBSTRING = {  
        "iPhone","Android","MIDP","Opera Mobi",  
        "Opera Mini","BlackBerry","HP iPAQ","IEMobile",  
        "MSIEMobile","Windows Phone","HTC","LG",  
        "MOT","Nokia","Symbian","Fennec",  
        "Maemo","Tear","Midori","armv",  
        "Windows CE","WindowsCE","Smartphone","240x320",  
        "176x220","320x320","160x160","webOS",  
        "Palm","Sagem","Samsung","SGH",  
        "SIE","SonyEricsson","MMP","UCWEB"};  
    
    
  private boolean checkMobile() {  
        String userAgent = Window.Navigator.getUserAgent();  
       for (String mobile: MOBILE_SPECIFIC_SUBSTRING){  
             if (userAgent.contains(mobile)  
               || userAgent.contains(mobile.toUpperCase())  
               || userAgent.contains(mobile.toLowerCase())){  
                    return true;  
            }  
       }  
    
       return false;  
  }  
  
  public static PBase getCulture(String pbname){
	  return EntryPoint.game.getPBase(VConstants.culture).getPBase(pbname);
  }
}
