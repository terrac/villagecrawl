package gwt.client.main;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.buildgame;
import gwt.client.game.display.LogDisplay;
import gwt.client.game.vparams.GoTo;
import gwt.client.game.vparams.RunTurn;
import gwt.client.game.vparams.random.RandomItemCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.game.vparams.ui.Score;
import gwt.client.item.Item;
import gwt.client.main.base.GGroup;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.MapObject;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.SymbolicMap;
import gwt.client.output.HtmlOut;
import gwt.client.output.OutputDirector;
import gwt.shared.PrefetchImageList;
import gwt.shared.datamodel.ClientSaveGame;
import gwt.shared.datamodel.IClientObject;
import gwt.shared.datamodel.VParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.googlecode.objectify.annotation.Unindexed;
import com.sun.java.swing.plaf.windows.resources.windows;

@Unindexed
public class Game extends PBase implements IClientObject, Serializable {
	private static final String STATECOUNT = "statecount";
	public @Id
	Long id;

	public Game() {

	}

	
	public RandomItemCreation getRandomItemCreation(){
		return (RandomItemCreation) get(VConstants.randomitem);
	}
	// TreeMap root = new TreeMap();

	// public LocalTemplate templateRoot = new LocalTemplate();
	// Multimap<String, AI> aiMap = ArrayListMultimap.create();

//	@Embedded
//	public MapArea mapArea;

	// public Map<String, Item> itemMap = new HashMap<String, Item>();

	// public Map<String,OObject> oobMap= new HashMap<String, OObject>();

	public MapArea getMapArea() {
		return (MapArea) get(VConstants.maparea);
	}

	public void setMapArea(MapArea mapArea) {
		put(VConstants.maparea,mapArea);
	}
	
	public HtmlOut getHtmlOut(){
		if(!containsKey(VConstants.htmlout)){
			put(VConstants.htmlout,new HtmlOut());
		}
	
		return (HtmlOut) get(VConstants.htmlout);
	}
	public Map<String, OObject> getOOMap() {
		return (Map) getPBase(VConstants.oObject).getObjMap();
	}
	
	public Map<String, VParams> getVParams() {
		return (Map) getPBase(VConstants.vparams).getObjMap();
	}

	public Map<String, Item> getItemMap() {
			
			return (Map) getPBase(VConstants.item).getObjMap();
		}
	
	
	public Item getItem(String key) {
		
		Item object = (Item) getPBase(VConstants.item).get(key);
		if(object == null){
			return object;
		}
	
		return object.clone();
	}

	public Map<String, PTemplate> getTemplateMap() {
		return (Map) getPBase(VConstants.template).getObjMap();
	}

	public Map<String, GGroup> getLivingBeingGroupMap() {
		return (Map) getPBase(VConstants.ggroup).getObjMap();
	}

	public List<OObject> getInitList() {
		return (List) get(VConstants.initList);
	}

	public List<VParams> getMapInitList() {
		return (List<VParams>) get(VConstants.mapInitList);
	}

	public Map<String, FullMapData> getFMDMap() {
		return (Map) getPBase(VConstants.fullmapdata).getObjMap();
	}

	
	
	public Map<String, LivingBeing> getPersonMap() {
		return (Map) getPBase(VConstants.person).getObjMap();
	}

	{
		put(VConstants.oObject, new PBase());
		put(VConstants.vparams, new PBase());
		put(VConstants.item, new PBase());
		
		put(VConstants.template, new PBase());
		put(VConstants.ggroup, new PBase());
		put(VConstants.initList, new ArrayList());
		put(VConstants.mapInitList, new ArrayList());
		put(VConstants.fullmapdata, new PBase());
		put(VConstants.turns, 3000);
		put(VConstants.turn, 0);
		put(VConstants.pause, false);
		put(VConstants.speed, 100);
		put(VConstants.turnwait, 300);
		put(VConstants.sound, false);
		
		put(VConstants.idnum, 0);
	
		put(VConstants.person,new PBase());
		put(VConstants.temporary, new PBase());

	}

	// @NotSaved
	// public Map<String, String> statedata = new HashMap<String, String>();
	//	
	// int i = 0;

	public void execute(String a) {
		execute();
	}
	public void execute() {
		getMapArea().getMap().setParent(getMapArea());
		getMapArea().game = this;
		
		Window.setTitle(getName());
		if(containsKey(VConstants.startmessage)){
			String ifdone=Cookies.getCookie(VConstants.startmessage);
			if(!"done".equals(ifdone)){
				Window.alert(getS(VConstants.startmessage));
				Cookies.setCookie(VConstants.startmessage, "done");
			}
		}
		// glist.add(gg2);

		// mapAreas.add(new MapArea(gg2));
		// topmap.setData(0, 0, mapAreas.get(0));
		// topmap.setData(0, 1, mapAreas.get(1));

		// holder object should be root of build json
		// extract(new HolderObject(),templateRoot.root);

		// init the maps
		// buildMapdata();

		MapArea ma = getMapArea();

		// mapArea.map.init();

		// ma.diffs = new DiffSymbolicMap(ma.map.getXsize(),
		// ma.map.getYsize());
		// for (FullMapData fmd : ma.map) {
		// System.out.println(fmd.getPosition());
		// ma.diffs.setData(fmd.getPosition(), new DiffFullMapData(fmd));
		// }
//		for (FullMapData fmd : ma.getMap()) {
//			if(fmd == null){
//				ma.getMap().init();
//				break;
//			}
//		}




		// mapAreas.get(1).addPerson("worker", 7, 7, "F");
		// people = (List<Person>) map.getAll(Person.class);


		
		
		
		
		OutputDirector.timer.cancel();
		OutputDirector.timer.scheduleRepeating(getSpeed());

		// build the items map from grabbing all items
		// or accepting one when it is created

		// ma.end();
		OutputDirector.log.log("init", "executed, starting run");
		SymbolicMap sm = (SymbolicMap) get(VConstants.savedmap);
		if(sm == null){
			firstRun(ma);	
		} else {
			getMapArea().setMap(sm);
			
			getHtmlOut().currentFMD = getMapArea().getMap().getData(0, 0);
			
			if(getHtmlOut().currentFMD == null){
				getMapArea().getMap().setData(0, 0, new FullMapData());
				getHtmlOut().currentFMD = getMapArea().getMap().getData(0, 0);
			}
		}
		if(EntryPoint.game.getHtmlOut().imap.size() == 0){
			final Set<String> images = new HashSet<String>(); 
			ma.getMap().iterate(new IFullMapExecute() {
				
				@Override
				public void execute(HashMapData mapdata) {
					for(MapData md : mapdata.removableValues()){
						
						images.add(md.getImage());
						
					}
					
				}
			});
			
			//OutputDirector.timer.setWait(true);
			
			Set<String> s = new HashSet<String>(Arrays.asList(PrefetchImageList.vault.split(",")));
			
			//System.out.println(s);
			HtmlOut.loadImages(s.toArray(new String[0]),null);	
			OutputDirector.timer.setWait(true);
		}
		
		
		buildgame.setupMap(this);
		getHtmlOut().currentFMD.startOnMap(null);
		
		
		List<LivingBeing> listCreate = (List<LivingBeing>)getListCreate(VConstants.savedpeople);
		for(LivingBeing lb : listCreate){
			RandomPersonCreation.setTemplateBasedOnMap(lb);
			lb.getTemplate().clear();
		}
		GoTo.setInitialPos(getHtmlOut().currentFMD, listCreate);
		getHtmlOut().playCurrentMapMusic();
		
		
		
		Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
			int count = 0;
			@Override
			public boolean execute() {
				if(count > 20){
					//Window.alert("Ten seconds and stuff hasn't loaded");
					return false;
				}
				if(!OutputDirector.timer.getWait()){
					OutputDirector.run();
				}
				count++;
				return OutputDirector.timer.getWait();
			}
		}, 100);
	}



	private void firstRun(MapArea ma) {
		ma.getMap().initIfNeeded();
		for (FullMapData fmd : ma.getMap()) {
			
			
			
			if(fmd == null){
				continue;
			}
			fmd.initIfNeeded();
			AttachUtil.run(VConstants.mapInitList, fmd, this);
			
			
			for (LivingBeing person : fmd.getPeople()) {

				for (OObject oo : getInitList()) {
					oo.execute(person.getParent().getParent(), person);
				}

			}

		}
		getHtmlOut().currentFMD = getMapArea().getMap().getData(0, 0);
		Integer xsym=(Integer) getMapArea().getMap().get(VConstants.xfull);
		Integer ysym = (Integer) getMapArea().getMap().get(VConstants.yfull);
		if(xsym != null){
			getHtmlOut().currentFMD = getMapArea().getMap().getData(xsym,ysym);
		}
		if(getHtmlOut().currentFMD == null){
			getMapArea().getMap().setData(0, 0, new FullMapData());
			getHtmlOut().currentFMD = getMapArea().getMap().getData(0, 0);
		}
		AttachUtil.run(AttachUtil.gamestart, getHtmlOut().currentFMD, this);
		PBase human = EntryPoint.game.getType(VConstants.human);
		AttachUtil.run(AttachUtil.mapstart, getHtmlOut().currentFMD, human);
		human.remove(AttachUtil.mapstart);
		AttachUtil.run(AttachUtil.mapstart, getHtmlOut().currentFMD, getMapArea());
	}



	public void setMapArea(int a, int b) {

		//setMapArea( new MapArea(this, getFMDMap().get("generic"), a, b));

	}

	boolean stop;
	public void stop(){
		stop = true;
	}
	public void go(){
		stop = false;
	}
	public boolean run() {
		if(stop){
			return true;
		}
		if (pause) {
			
			AttachUtil.run(AttachUtil.paused, null, this);
			
			return true;
		}
		int turn = getTurn();
//		int timeofday = this.timeofday[turn/150 % this.timeofday.length];
//		if(getInt(VConstants.timeofday) !=timeofday){
//			put(VConstants.timeofday, timeofday);
//			LogDisplay.log("The hour is "+timeofday, 2);
//		}
		//		
		//		
		 getMapArea().runTurn(turn);
		//		
//		if (turn > getTurns()) {
//			return false;
//		}
		setTurn(turn + 1);
		return true;
	}

	public void setTurn(int i) {
		put(VConstants.turn, i);

	}

	private int getTurns() {

		return (Integer) get(VConstants.turns);
	}

	public int getTurn() {
		// TODO Auto-generated method stub
		return (Integer) get(VConstants.turn);
	}

	@Transient
	// essentially we pretend like sleeping does not happen for now
	// eventually we will add it as an option
	 int[] timeofday = new int[16];
	 {
		 for(int a = 0; a <timeofday.length; a++){
		 timeofday[a] =  a;
	 }
			
	 }
	// @Transient
	public String getNextId() {
		int idnum = (Integer) get(VConstants.idnum);
		idnum++;
		put(VConstants.idnum, idnum);
		return idnum + "";
	}

	public boolean pause = false;

	public void pauseToggle() {
		pause = !pause;
	}

	public void pause() {
		pause = true;
	}
	public void unPause() {
		pause = false;
	}

	public boolean isPaused() {
		return pause;
	}

	public void setSpeed(int speed) {
		put(VConstants.speed, speed);
		OutputDirector.timer.cancel();
		OutputDirector.timer.scheduleRepeating(speed);
	}

	public int getSpeed() {
		return (Integer) get(VConstants.speed);
	}

	public String getName() {

		return (String) get(VConstants.name);
	}

	public LocalTemplate getTemplateRootClone() {

		return new LocalTemplate();
	}

	public Map<String, ?> getMap(String type) {

		return (Map<String, ?>) getPBase(type).getObjMap();
	}

	public PBase getPBase(String human) {

		return (PBase) get(human);
	}

	public void setName(String name) {
		put(VConstants.name, name);

	}

	@Override
	public PBase clone() {
		
		return this;
	}

	@Override
	public void put(String key, Object object) {
		
		super.put(key, object);
	}



	public Random getRandom() {
		
		Object random =  get(VConstants.random);
		if(random == null){
			String seed= (String) get(VConstants.seed);
			if(seed != null){
				Long lseed = Long.valueOf(seed); 
				random = new Random(lseed);	
			} else {
				Long l = System.currentTimeMillis();
				put(VConstants.seed,""+l);
				random = new Random(l);
				
			}
			
			put(VConstants.random,random);
		} 
		
		return (Random) random;
	}

	

	public RunTurn getRunTurn() {

		return (RunTurn)getVParams().get(VConstants.runturn);
	}



	public boolean getDebug() {
//		if(!GWT.isProdMode()){
//			return true;
//		}
		return getB(VConstants.debug);
	}



	public PBase getTemporary() {
		
		return getPBase(VConstants.temporary);
	}
	
	public static Map<String,ClientSaveGame> csg = new HashMap<String, ClientSaveGame>();
	public static Map<String,String> jsonCache = new HashMap();

	public Economy getEconomy() {
		return (Economy) get(VConstants.economy);
	}



	public int getTimeOfDay() {
		return getInt(VConstants.timeofday);
	}


	public PBase getTechnology() {
		return getPBase(VConstants.technology);
	}
	
}
