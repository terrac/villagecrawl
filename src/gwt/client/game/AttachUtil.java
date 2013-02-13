package gwt.client.game;

import gwt.client.EntryPoint;
import gwt.client.game.display.UIVParams;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.shared.datamodel.VParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Window;

public class AttachUtil {
	
	public static final String DONTCONTINUE = "dontcontinue";
	public static final String OBJECT = "object";
	public static final String clickfmd = "clickfmd";
	public static final String paused = "paused";
	public  static final String clickItem = "clickitem";
	public static final String personStartOnMap = "personstartonmap";
	public static final String personadded = "personadded";
	public static final String attacher = "attacher";
	public static final String exit = "exit";
	public static final String death = "death";
	public static final String personchosen = "personchosen";
	public static final String selectTab = "selecttab";
	public static final String groupupdate = "groupupdate";
	public static final String clicklist = "clicklist";
	public static final String gamestart = "gamestart";
	public static final String runpersonbefore = "runpersonbefore";
	public static final String runbefore = "runbefore";
	public static final String runpersonafter = "runpersonafter";
	public static final String remove = "remove";
	public static final String stop = "stop";
	public static final String damage = "damage";
	public static final String allies = "allies";
	public static final String selected = "selected";
	public static final String mapstart = "mapstart";
	public static final String personlist = "personlist";
	public static final String placed = "placed";
	
	
	
	
	
	public static boolean shouldRun = true;
	
	public static void attach(String value,VParams execute,PBase o) {
		o.getListCreate(value).add(execute);
	}
	public static void attach(String value,String execute,PBase o) {
		o.getListCreate(value).add(execute);
	}
	public static void attach(String value,Object execute,PBase o) {
		o.getListCreate(value).add(execute);
	}
	

	
	
//	public static boolean run(String value,HashMapData hmd, PBase o) {
//		if(!shouldRun){
//			return false;
//		}
//		
//		Map<String, Object> map = buildMap(hmd);
//		
//		
//		map.put(attacher, o);
//		
//		//make a static method that creates the map variable given the game and the relevant person
//		for(Object vp:o.getList(value)){
//			vp = getMappedParam(vp);
//			((VParams) vp).execute(map);
//		}		
//		return true;
//	}
	public static Object getMappedParam(Object vp) {
		
		if(vp instanceof String){
			vp = EntryPoint.game.getVParams().get(vp);
		}
		if(!(vp instanceof VParams)){
			Window.alert("Class cast issue.  Should be vparam"+ vp);
		}
		
		return vp;
	}

//	public static Map<String, Object> buildMap(HashMapData hmd) {
//		Map<String,Object> map = new HashMap<String, Object>();
//		
//		if(hmd != null){
//			map.put(HashMapData.hashMapData, hmd);
//			map.put(VConstants.person, hmd.getPerson());
//			//might need to change to be less static later.  Thats why I am doing it here
//			
//		}
//		map.put(VConstants.game, EntryPoint.game);
//		return map;
//	}

	public static void run(String value, Object object,
			PBase o) {
		if(!o.containsKey(value)){
			return;
		}
		runWithoutUpdates(value, object, o);
		update(value, o);
		
		
		
	}



	public static void runWithoutUpdates(String value, Object object, PBase o) {
		if(!shouldRun){
			return;
		}
		
		Map<String, Object> map = createMap(object, o);
		
		
		//make a static method that creates the map variable given the game and the relevant person
		for(Object vp:(List)o.getListCreate(value)){
		
			if(map.containsKey(AttachUtil.stop)){
				break;
			}
			VParams ov = (VParams) getMappedParam(vp);
			if(ov == null){
				Window.alert(""+vp+" is missing");
			}
			((VParams) ov).execute(map);
			
			
		}
		if(map.containsKey(AttachUtil.remove)){
			o.getListCreate(value).remove(map.get(AttachUtil.remove));
		}
	}
	public static Map<String, Object> createMap(Object object, PBase o) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put(OBJECT, object);
		
		
		
		map.put(attacher, o);
		return map;
	}



	public static void update(String value, PBase o) {
		if(!shouldRun){
			return;
		}
		
		List<UIVParams> groupupdates = null;
		List list = o.getList(value);
		if(list == null){
			return;
		}
		for(Object vp:list){
			vp = getMappedParam(vp);
			groupupdates =(List<UIVParams>) ((PBase) vp).get(AttachUtil.groupupdate);
			if(groupupdates != null){
				for(UIVParams uvp: groupupdates){
					uvp.update();
				}
				break;
			}
		}
	}



	public static void setGroupList(List<UIVParams> groupList) {
		for(UIVParams uvp: groupList){
			uvp.put(AttachUtil.groupupdate,groupList);
		}
	}


}
