package gwt.client.statisticalciv.rules;

import gwt.client.EntryPoint;
import gwt.client.game.display.LogDisplay;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.statisticalciv.SConstants;
import gwt.shared.datamodel.VParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.Series.Type;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions;

import com.google.appengine.api.datastore.Entity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DemographicRule extends VParams {

	public static final List<String> currentTechs = Arrays.asList(new String[]{Demographics.protect_women,Demographics.gang_warfare});


	public DemographicRule() {
	}
	
	public DemographicRule(Object... vp) {
		super(vp);
	}
	boolean init = false;
	static List<HashMapData> villageList = new ArrayList<HashMapData>();
	List<PBaseRule> pbrList = new ArrayList<PBaseRule>();
	
	List<PBaseRule> beginningStories = new ArrayList<PBaseRule>();
	
	
	List<PBaseRule> hatredStories = new ArrayList<PBaseRule>();
	
	public static Map<String,PBase> techMap = new HashMap();
	{
//		  a slavery (cultures with tech take over villages, cultures without destroy villages, though slavery cultures can still destroy villages)
//		  b prostitution (cultures with tech have a high chance to convert outsiders, but not enemies, based on conflict, conflict is a double)
//		  c early disease (cultures with tech spread the tech, the tech damages based on liberalism, but burns itself out)
//		  d domestication (precursor to disease, gives population boost)
//		  e archery (extra damage, population boost)
//		  f copper (extra damage, population boost)
//		  g tin (population boost if tin is nearby)
//		  h bronze (requires copper, tin, needs tin nearby, damage, pop boost)
//		  i long distance trade (can procure long distance resources, but potentially fragile)
//		  j religious story techs increase max size, but slavery then reduces that
//		  k tolerance (requires slavery, revolts cause damage instead of destroying rule of law)
		addTech(Demographics.slavery,"purple");
		addTech(Demographics.prostution,"yellow");
		addTech(Demographics.earlyDisease,"orange");
		addTech(Demographics.gang_warfare, "red");
		addTech(Demographics.protect_women, "blue");
		
	}
	
	private List<PBase> leadersList = new ArrayList();
	
	private List<PBase> tLeadersList = new ArrayList();
	
	public static BasicStory flood = new BasicStory("waterdamage","Flooding causes massive damage",.1,new CauseDeaths(.85,.30));
	public static BasicStory fight = new BasicStory("damagefireball","Young men are excessively fighting",.1,new CauseDeaths(.60,.5));
	public static BasicStory fertility = new BasicStory("damageheal","Extra young women causes a fertility boost",.1,new Birth(.20));
	public static BasicStory settle = new BasicStory("airdamage","Young men go on a mystical quest",.1,new MysticalQuest());
	
	
	public void init(FullMapData fmd) {
		singleton = this;
		
		for(HashMapData hmd : fmd){
			String name = null;
			if(hmd.containsKey(Demographics.hominids)){
				name = Demographics.hominids;
			} else if(hmd.containsKey(Demographics.neanderthals)){
				name = Demographics.neanderthals;
			}
			if(name != null){
				ProtoHuman.addProtoHuman(hmd, name);
			}
		}
		
		beginningStories.add(new BasicStory("damagesword","Young men are excessively fighting",new PBase(VConstants.size,.6,Demographics.male,.4,Demographics.averageAge,.3,VConstants.chance,.4,VConstants.conflict,.5),new CauseDeaths(.1,.1)));
		beginningStories.add(new BasicStory("damageheal","Young women are very fertile",new PBase(Demographics.female,.4,Age.YOUNG_ADULT,.4,VConstants.chance,.4),new Birth(.1)));
		beginningStories.add(new BasicStory("damagepunch","The elders send some young men on a mystical quest",new PBase(VConstants.size,.6,Demographics.male,.4,Demographics.averageAge,.7,VConstants.chance,.4),new MysticalQuest()));
		beginningStories.add(new BasicStory("gold","Trade occurs",.2,new CultureTrade()));
		beginningStories.add(flood);

		leadersList.add(new PBase(VConstants.name,"Montezuma",VConstants.overlay,"attack",VConstants.conflict,.1));
		leadersList.add(new PBase(VConstants.name,"Ghandi",VConstants.overlay,"heart",VConstants.conflict,-.1));
		leadersList.add(new PBase(VConstants.name,"Washington",VConstants.overlay,"food"));
		leadersList.add(new PBase(VConstants.name,"a",VConstants.overlay,"axe"));
		leadersList.add(new PBase(VConstants.name,"b",VConstants.overlay,"build"));
		leadersList.add(new PBase(VConstants.name,"c",VConstants.overlay,"dancing"));
		leadersList.add(new PBase(VConstants.name,"d",VConstants.overlay,"lavatory"));
		leadersList.add(new PBase(VConstants.name,"e",VConstants.overlay,"singing"));
		leadersList.add(new PBase(VConstants.name,"f",VConstants.overlay,"sleep"));
		leadersList.add(new PBase(VConstants.name,"g",VConstants.overlay,"trade"));
		leadersList.add(new PBase(VConstants.name,"h",VConstants.overlay,"water"));

		hatredStories.add(new BasicStory("damagesword","The elders send some young men to fight against a hated town",new War()));

		pbrList.add(new Age());

		
		for(HashMapData hmd : fmd){
			MapData gate = hmd.getMapData(VConstants.gate);
			if(gate != null&&gate.getValue().equals(SConstants.farm)){
				Demographics demo = getDemo(hmd);
				addLeader(getNextLeader(),hmd);
				villageList.add(hmd);
				EntryPoint.game.getHtmlOut().displayMapData(hmd);
				for(Object a : gate.getListCreate(VConstants.technology)){
					DemographicTechRule.getSingleton().addTech((String)a, demo,.5);
				}
				DemographicTechRule.getSingleton().addTech(Demographics.gang_warfare, demo,.5);
				DemographicTechRule.getSingleton().addTech(Demographics.protect_women, demo,.5);
				
				Age.ageYears(50,getDemo(hmd),hmd);

			}
		}
		new DemographicRandomEffects();
		
		toRefactor();
		
		init = true;
	}

	public void toRefactor() {
		EntryPoint.game.put(VConstants.intro, "Welcome, place down the items on the left to alter the map");
		String intro = EntryPoint.game.getS(VConstants.intro);
		if(null != intro){
			final PopupPanel pp=new PopupPanel();
			VerticalPanel vp = new VerticalPanel();
			pp.add(vp);
			Label introL = new Label(intro);
			vp.add(introL);
			CheckBox checkbox = new CheckBox("Don't show Again");
		
			checkbox.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					CheckBox c = (CheckBox) event.getSource();
					Cookies.setCookie(VConstants.intro,""+ c.getValue());
				}
			});
			vp.add(checkbox);
			introL.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					EntryPoint.game.pauseToggle();
					pp.hide();
				}
			});
			if(!Boolean.FALSE.equals(Cookies.getCookie(VConstants.intro))){
				pp.center();
				EntryPoint.game.pause();
			}
		}
		
		EntryPoint.game.put(Demographics.birth, .5);
		EntryPoint.game.put(Demographics.combat, .5);
		
	}

	private void addTech(String prostution,String color) {
		techMap.put(prostution, new PBase(VConstants.color,color));
	}
	public PBase getTech(String tech){
		return techMap.get(tech);
	}

	public void addLeader(PBase leader, HashMapData hmd) {
		// TODO Auto-generated method stub
		if(getShowLeader()){
			return;
		}
		
		Demographics.getCulture(getDemo(hmd)).put(leader.getS(VConstants.name),1.0);
		getType(VConstants.leader).put(leader.getS(VConstants.name), leader);
		hmd.getMapData(VConstants.gate).put(VConstants.overlay,CultureTrade.getOverlay(hmd));
		
	}
	boolean getShowLeader() {
		return false;
	}
	static int count = 0;
	public static void addVillage(HashMapData hmd, HashMapData home){
		Demographics demo = DemographicRule.getDemo(home);
		if(demo == null){
			return;
		}
		villageList.add(hmd);
		SimpleMD farm = new SimpleMD(VConstants.gate, SConstants.farm);
		farm.put(VConstants.name, "name"+count);
		count++;
		hmd.putEvenIfNotEmpty(VConstants.gate,farm);
		
		//essentially this area inits the leader, probably should do it on add village
		Demographics.addCulture(DemographicRule.getDemo(hmd), Demographics.getHighestCultureName(home), .55);
		hmd.getMapData(VConstants.gate).put(VConstants.overlay,CultureTrade.getOverlay(hmd));
		//getDemo(hmd).put(DCon.fundamentalism,DemographicRule.getSingleton().getType(VConstants.leader).getPBase(Demographics.getHighestCultureName(hmd)).getDouble(DCon.fundamentalism));	
		
		Demographics demoNew = getDemo(hmd);
		demoNew.getType(Demographics.technologyMap).copyDeepProperties(demo.getType(Demographics.technologyMap));
		for(Object o : demoNew.getPBase(Demographics.technologyMap).getObjMap().values()){
			PBase p = (PBase) o;
			double pct=p.getDouble(VConstants.percent);
			p.put(VConstants.percent, Math.abs(pct+(VConstants.getRandom().nextDouble()*.4 - .2)));
		}
		
		//getDemo(hmd).getListCreate(Demographics.technologyColor).addAll(demo.getListCreate(Demographics.technologyColor));
		Age.ageYears(50,getDemo(hmd),hmd);
	}
	public static void removeVillage(HashMapData hmd){
		if(villageList.size() == 1){
			return;
		}
		villageList.remove(hmd);
		hmd.remove(VConstants.gate);
	}
	DemographicTimeRule dtr = new DemographicTimeRule();
	protected Boolean showIntro = true;
	@Override
	public void execute(Map<String, Object> map) {
		FullMapData fmd = getFMD(map);
		if(!init){
			init(fmd);
		}
		dtr.execute(map);
		int count = villageList.size();
		for(HashMapData hmd: villageList){
			Demographics demo = getDemo(hmd);
			
			if(hmd.getMapData(VConstants.gate).containsKey(VConstants.hatred)&&VConstants.getRandom().nextDouble() < .02){
				for(PBaseRule pbr : hatredStories){
					pbr.run(demo, hmd, fmd);
				}
			}
			if(DemographicTimeRule.yearChange){
				for(PBaseRule pbr : pbrList){
					pbr.run(demo, hmd, fmd);
				}
			}
			
			if(VConstants.getRandom().nextDouble() < .03){
				BasicStory.runStory(beginningStories,demo,hmd,fmd);
			}
			if(count != villageList.size()){
				break;
			}
		}

		for(LivingBeing person : fmd.people.toArray(new LivingBeing[0])){
			//run technology list (added from time)
			// starting with aging
			// aggressive young men (greater chance to pull from conflict stories)
			// fertile young women (greater chance to have more babies)
			// -aggressive elderly men (greater chance to pull from conflict reduction stories, 
			// -fertile elderly women (greater chance to pull from fertility reduction stories)
			// disasters just occasionally happen, they determine reductions or increases in damage
			// diseases just happen, but stories tell whether they grow or die out or steady state
			//  so for example elderly have a disease/disaster reduction stat
			// which means that more positive disease stories are told (ie successfully controlled
			// disease outbreak by the elderly deciding to forbid pork, or the yearly hill
			// ritual causes the flooding to be much less severe (the yearly bathing ritual
			// not so much, though bathing at the right times of year might be a benefit)
			
			//an initial game might offer you the choice between gilgamesh and einkadu
			//einkadu offers an extra wilderness disaster reduction, but has increased 
			//disease vulrnabliity.  Gilgamesh is resistant to disease, but causes more conflict
			//disasters (ie two groups of local youths fight over a girl)
			//choosing shamat would increase disease length chance, but reduce conflict
			//choosing moses would offer huge disease resistance, but would decrease
			//fertility
			//and then just take all the individual stories, turn them generic, and have 
			//them pop up (you can control the speed by a slider and have an option to select
			//for pause on pop up)
			
			//while that is going on occasionally there will be a split story
			//splits might be where elderly send off youths to explore
			//then an actual group is created which braves the wilderness.  It has a 
			//chance of finding a good spot and doing additional stories which
			//lead to drawing away people to a new seperate village
			//then there would be interaction stories.
			//there will be a young split story, an even split story, and a
			//fundamentalist/liberal divide split story
			//when there are multiple towns on the map you can then have intra town conflict
			//stories (rivalires, trade, cultural pressure, conflict)
			
			//long term, material wealth, supply and demand issues (ie too much making things, too little)
			//cold/warm disasters, make drought,flood location dependent
			
			//for each village alter the demographics
			// shift all the ages up 1 
			
			//for each person token do the same(but have two different maps to
			//seperate testing)
			
			//Do a series of predefined actions based on the ages
			//pull from beginning user choice
			//pull from normal list
			//(young men adds 1 to aggression)
			//(young women add to potential fertility)
			//(older men/women reduce aggression and reduce fertility)
			//over time the list will expand, it will be based in the leader village
			//to allow for distinct areas with distinct behaviors
			
			//the basic principles are
			//conflict (sending out temporary leaders that gather troops and fight.  Note that this would increase dissent and potentially cause splitting)
			//disaster (flooding,drought,short seasons)
			//disease (affects specific populations, comes from animals, can die off, techs reduce chance of spreading)
			// (spreading is primarily a demographic thing)
			
		}


	}


	PBase getNextLeader() {
		if(tLeadersList.isEmpty()){
			tLeadersList.addAll(leadersList);
		}
		PBase leader = VConstants.getRandomFromList(tLeadersList);
		tLeadersList.remove(leader);
		//demo.put(VConstants.leader, leader.clone());
		return leader;
	}
	public static Demographics getDemo(HashMapData hmd) {
		return getDemo(hmd, true);
	}
		
	public static Demographics getDemo(HashMapData hmd,boolean create) {
		if(hmd == null){
			return null;
		}
		MapData mapData = hmd.getMapData(VConstants.gate);
		if(mapData == null||!mapData.getValue().equals(SConstants.farm)){
			return null;
		}
		Demographics demo = (Demographics) mapData.getPBase(VConstants.demographics);
		if(demo == null){
			if(!create){
				return null;
			}
			demo = new Demographics();
			mapData.put(VConstants.demographics, demo);
		}
		return demo;
	}
	
	public static Demographics getDemo(PBase p) {
		Demographics demo = (Demographics) p.getPBase(VConstants.demographics);
		if(demo == null){
			demo = new Demographics();
			p.put(VConstants.demographics, demo);
		}
		return demo;
	}


	@Override
	public PBase clone() {
		return new DemographicRule().copyProperties(this);
	}	
	public static class Demographics extends PBase{
		public static final String combat = "combat";
		public static final String birth = "birth";
		public static final String technologyMap = "techMap";
		public static final String protect_women = "Protect Women";
		public static final String gang_warfare = "Gang Warfare";
		public static final String earlyDisease = "earlyDisease";
		public static final String prostution = "prostitution";
		public static final String slavery = "slavery";
		public static final String old = "old";
		public static final String young = "young";
		public static final String female = "female";
		public static final String male = "male";
		public static final String averageAge = "averageAge";
		public static final String technologyColor ="technologyColor";
		public static final String genocide = "genocide";
		public static final String simpleLayrnx = "simpleLayrnx";
		public static final String giant = "giant";
		public static final String hominids = "hominids";
		public static final String neanderthals = "neanderthals";
		public static boolean chart=false;
		public static Series seriesPop;
		public static Series seriesTech1;
		public static Series seriesTech2;
		public static Chart c =new Chart();
		static{
			c.setWidth("20em");
			c.setType(Type.COLUMN);
			c.getXAxis().setCategories("Population","Sexual Freedom","Gang Warfare");
			
			c.getYAxis().setMin(0);
			c.getYAxis().setMax(200);

			seriesPop=c.createSeries();
			c.addSeries(seriesPop);
			seriesTech1=c.createSeries();
			c.addSeries(seriesTech1);
			seriesTech2=c.createSeries();
			c.addSeries(seriesTech2);
			

			seriesTech1.setOption("color", "red");
			seriesTech2.setOption("color", "blue");
		}
		static Demographics current;
		public Demographics() {
			put(Demographics.female, .5);
			put(Demographics.male,.5);
			put(Demographics.averageAge,.5);
		}
		long currentTime;
		public void execute() {
			if(currentTime > new Date().getTime()){
				return;
			}
			currentTime = new Date().getTime() + 1000;
			// TODO Auto-generated method stub
			//if(current != this){
				List<Integer> ageList =this.getListCreate(VConstants.age);
				seriesPop.setPoints(new Number[]{getDouble(VConstants.size)});
				seriesTech1.setPoints(new Number[]{DemographicTechRule.getTechScore(this,Demographics.gang_warfare)*100});
				seriesTech2.setPoints(new Number[]{DemographicTechRule.getTechScore(this,Demographics.protect_women)*100});
				
				current = this;
//			}
		}

		
		public String getTechColor(String key){
			return techMap.get(key).getS(VConstants.color);
		}
		NumberFormat d = NumberFormat.getFormat("##%");
		NumberFormat de = NumberFormat.getFormat("##");
		@Override
		public String toString() {
			String ret="Size:"+de.format(getDouble(VConstants.size));
			for(String s : Age.names){
				ret +="\n"+s+": "+ d.format(getDouble(s));
			}
			//ret +="\n"+ get(VConstants.leader);
			ret +="\n"+ get(VConstants.culture);
			ret +="\n"+ get(VConstants.technology);
			
			return ret;
		}
		public static String getHighestCultureName(HashMapData home) {
			Demographics demo = getDemo(home,false);
			return getHighestCultureName(demo);
		}
		public static String getHighestCultureName(Demographics demo) {

			if(demo == null){
				return null;
			}
		
			PBase culture =getCulture(demo);
			String highestName= "";
			double highestCulture= 0;
			for(Entry<String, Object> e: culture.getObjMap().entrySet()){
				Double value = (Double) e.getValue();
				if(value > highestCulture){
					highestName = e.getKey();
					highestCulture = value;
				}
			}
			return highestName;
		}
		public static PBase getCulture(Demographics demo) {
			return demo.getType(VConstants.culture);
		}
		public static void addCulture(Demographics demo,String cultureName, double e) {
			PBase.increment(getCulture(demo),cultureName, e);
		}
		public static double compareCultureHighest(HashMapData home,
				HashMapData parent) {
			//get the highest culture for both, if they are similar return 0
			//if they are different return 1.0
			if(getHighestCultureName(home).equals(getHighestCultureName(parent))){
				return 0;
			}
			return 1;
		}

		public static double getTileSize(HashMapData hmd) {
			
			double maxsize = hmd.getDouble(VConstants.maxsize);
			if(maxsize == 0){
				return 1.0;
			}
			return maxsize;
		}

		public double getSize() {
			return getDouble(VConstants.size);
		}

		public String getLeader() {
			return getHighestCultureName(this);
		}

		public void setLeader(String newLeader) {
			getCulture(this).put(newLeader, 1.0);
			getCulture(this).remove(getLeader());
		}

		public  int getConflict(HashMapData home) {
			return getLeaderPBase().getInt(VConstants.conflict);
		}

		public PBase getLeaderPBase() {
			return DemographicRule.getSingleton().getLeader(getLeader());
		}

		public int getMaxVillages() {
			return getLeaderPBase().getInt(DCon.maxvillages)+1;
		}

		public int getMaxVillageSize() {
			return getInt(DCon.maxvillages);
		}
		public boolean hasTech(String tech){
			return getListCreate(VConstants.technology).contains(tech);
		}
		public double getTechProgression() {
			if(hasTech(Demographics.simpleLayrnx)){
				return .05;
			}
			return 1.0;
		}
	}
	public static boolean isVillage(HashMapData hashmapdata) {
		MapData gate = hashmapdata.getMapData(VConstants.gate);
		return gate != null && gate.getValue().equals(SConstants.farm);
	}
	
	public PBase getLeader(String leader) {
		return getPBase(VConstants.leader);
	}
	static DemographicRule singleton;
	public static DemographicRule getSingleton() {
		return singleton;
	}

	public int getVillageCount(Demographics demo) {
		String leader = demo.getLeader();
		int amt = 0;
		for(HashMapData hmd : villageList){
			if(leader.equals(DemographicRule.getDemo(hmd).getLeader())){
				amt++;
			}
		}
		return amt;
	}

	public void enableTech(String s) {
		HashMapData hmd=VConstants.getRandomFromList(villageList);
		
		//simple layrnx = no techs other than initially set
		Demographics demo = getDemo(hmd);
		if(demo.hasTech(Demographics.simpleLayrnx)){
			for(HashMapData h : villageList){
				if(!demo.hasTech(Demographics.simpleLayrnx)){
					hmd = h;
					break;
				}
			}
		}
		DemographicTechRule.getSingleton().addTech(s, demo,.5);
	}

	


	public static boolean isSameLeader(HashMapData village1,
			HashMapData village2) {
		
		String highestCultureName = Demographics.getHighestCultureName(village1);
		if(highestCultureName == null){
			return false;
		}
		return highestCultureName.equals(Demographics.getHighestCultureName(village2));
	}

	public PBase getLeader(Demographics p) {
		return getLeader(Demographics.getHighestCultureName(p));
		
	}
	
	public void message(String message){
		final PopupPanel popup = new PopupPanel();
		final CheckBox cb = new CheckBox("Don't show again");
	    
		VerticalPanel vp = new VerticalPanel();
	    vp.add(new Label(message));
	    Button button = new Button("Close", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				popup.hide();
				showIntro =cb.getValue();
			}
		});
	    vp.add(cb);
	    vp.add(button);
	    popup.center();
		
	}
}
