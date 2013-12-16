package gwt.client.statisticalciv.rules;

import gwt.client.EntryPoint;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.BuildMap;
import gwt.client.game.vparams.CopySelection;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Move;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.PTemplate;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.main.base.Parameters;
import gwt.client.main.base.SimpleOObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.statisticalciv.ConflictRule;
import gwt.client.statisticalciv.FoodRule;
import gwt.client.statisticalciv.SConstants;
import gwt.client.statisticalciv.TechnologyRule;
import gwt.client.statisticalciv.oobjects.TechnologyAction;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;
import gwt.shared.ClientBuild;
import gwt.shared.StatisticalCiv;
import gwt.shared.datamodel.VParams;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.Series.Type;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.widgetideas.graphics.client.Color;

public class DemographicRule extends VParams {

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
	
	private List<PBase> leadersList = new ArrayList();
	private List<PBase> tLeadersList = new ArrayList();
	
	public void init(FullMapData fmd) {
		singleton = this;

		beginningStories.add(new BasicStory("Young men are excessively fighting",new PBase(VConstants.size,.6,Demographics.male,.4,Demographics.averageAge,.3,VConstants.chance,.4,VConstants.conflict,.5),new CauseDeaths(.1)));
		beginningStories.add(new BasicStory("Young women are very fertile",new PBase(Demographics.female,.4,Age.YOUNG_ADULT,.4,VConstants.chance,.4),new Birth(.1)));
		beginningStories.add(new BasicStory("The elders send some young men on a mystical quest",new PBase(VConstants.size,.6,Demographics.male,.4,Demographics.averageAge,.7,VConstants.chance,.4),new MysticalQuest()));
		beginningStories.add(new BasicStory("Trade occurs",.2,new CultureTrade()));
		beginningStories.add(new BasicStory("Flooding causes massive",.1,new CauseDeaths(.4)));

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

		hatredStories.add(new BasicStory("The elders send some young men to fight against a hated town",new War()));

		pbrList.add(new Age());

		
		for(HashMapData hmd : fmd){
			MapData gate = hmd.getMapData(VConstants.gate);
			if(gate != null&&gate.getValue().equals(SConstants.farm)){
				Demographics demo = getDemo(hmd);
				addLeader(getNextLeader(),hmd);
				villageList.add(hmd);
			}
		}

		init = true;
	}

	public void addLeader(PBase leader, HashMapData hmd) {
		// TODO Auto-generated method stub
		Demographics.getCulture(getDemo(hmd)).put(leader.getS(VConstants.name),1.0);
		getType(VConstants.leader).put(leader.getS(VConstants.name), leader);
		hmd.getMapData(VConstants.gate).put(VConstants.overlay,CultureTrade.getOverlay(hmd));
		
	}
	static int count = 0;
	public static void addVillage(HashMapData hmd, HashMapData home){
		villageList.add(hmd);
		SimpleMD farm = new SimpleMD(VConstants.gate, SConstants.farm);
		farm.put(VConstants.name, "name"+count);
		count++;
		hmd.putEvenIfNotEmpty(VConstants.gate,farm);
		
		//essentially this area inits the leader, probably should do it on add village
		Demographics.addCulture(DemographicRule.getDemo(hmd), Demographics.getHighestCultureName(home), .55);
		hmd.getMapData(VConstants.gate).put(VConstants.overlay,CultureTrade.getOverlay(hmd));
		getDemo(hmd).put(DCon.fundamentalism,DemographicRule.getSingleton().getType(VConstants.leader).getPBase(Demographics.getHighestCultureName(hmd)).getDouble(DCon.fundamentalism));		
	}
	public static void removeVillage(HashMapData hmd){
		villageList.remove(hmd);
		hmd.remove(VConstants.gate);
	}
	@Override
	public void execute(Map<String, Object> map) {
		FullMapData fmd = getFMD(map);
		if(!init){
			init(fmd);
		}
		for(HashMapData hmd: villageList){
			Demographics demo = getDemo(hmd);
			
			if(hmd.getMapData(VConstants.gate).containsKey(VConstants.hatred)&&VConstants.getRandom().nextDouble() < .02){
				for(PBaseRule pbr : hatredStories){
					pbr.run(demo, hmd, fmd);
				}
			}
			for(PBaseRule pbr : pbrList){
				pbr.run(demo, hmd, fmd);
			}
			if(VConstants.getRandom().nextDouble() < .03){
				BasicStory.runStory(beginningStories,demo,hmd,fmd);
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
		public static final String old = "old";
		public static final String young = "young";
		public static final String female = "female";
		public static final String male = "male";
		public static final String averageAge = "averageAge";
		public static boolean chart=false;
		public static Chart c =new Chart();{
			c.setType(Type.SPLINE);
		}
		static Demographics current;
		public Demographics() {
			put(Demographics.female, .5);
			put(Demographics.male,.5);
			put(Demographics.averageAge,.5);
		}
		
		public void execute() {
			// TODO Auto-generated method stub
			//if(current != this){
				List<Integer> ageList =this.getListCreate(VConstants.age);
				
				c.removeAllSeries();
				Series s = 	c.createSeries();
				
				for(int a= 0; a < ageList.size(); a++){
					s.addPoint(ageList.get(a));
				}
				c.addSeries(s);
//				current = this;
//			}
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

		public int getMaxSize() {
			return getInt(VConstants.maxsize);
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

}
