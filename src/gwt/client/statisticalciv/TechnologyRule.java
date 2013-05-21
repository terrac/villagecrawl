package gwt.client.statisticalciv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.display.LogDisplay;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.statisticalciv.generator.setup.DiseaseSetup;
import gwt.client.statisticalciv.generator.setup.FishingSetup;
import gwt.client.statisticalciv.generator.setup.PromoteTechs;
import gwt.client.statisticalciv.generator.setup.TradingSetup;
import gwt.shared.datamodel.VParams;

public class TechnologyRule extends VParams {

	public TechnologyRule() {
		// TODO Auto-generated constructor stub
	}
	
	
	int countBeforeNewEra = 1;//5000
	int count;
	int level = 0;
	VillageRule vr;
	@Override
	public void execute(Map<String, Object> map) {
		count++;
		if(count > countBeforeNewEra){
			if(level < 1){
				level++;
			}
			if(level == 1&& vr == null){
				vr = new VillageRule();
			}
			count =0;
		}
		if(vr != null){
			vr.execute(map);
		}
		for (LivingBeing person : getFMD(map).people.toArray(new LivingBeing[0])) {
//			if(VConstants.getRandom().nextDouble()< .01){
//				//take person and add random new technology
//			}
			if(!PeopleRule.isHuman(person)){
				continue;
			}
			if(!person.containsKey(VConstants.population)){
				continue;
			}
			List <PBase> techL = getTechList(person);
			if(techL.size() == 0){
				continue;
			}
			OObject o =person.getTemplate().getCurrent();
			
			String state = null;
			while(state == null||o != null){
				if(o == null){
					break;
				}
				state = o.getS(SConstants.state);
				o = o.getOParent();
			}
			if(state == null){
				state = VConstants.general;
			}
			
			String techName=(String)VConstants.getRandomFromList(EntryPoint.game.getPBase(VConstants.technology).getPBase(SConstants.tree).getPBase(""+level).getListCreate(state));
			//String techNameChoice=(String)VConstants.getRandomFromList(EntryPoint.game.getPBase(VConstants.technology).getPBase(SConstants.tree).getType(SConstants.choice).getPBase(""+level).getListCreate(state));
			incrementTechnology(person,techName);
			//incrementTechnologyChoice(person,techNameChoice);
			System.out.println(state);
		}

	}
	
	
	private void incrementTechnology(LivingBeing person, String techName) {
		if(techName == null){
			return;
		}
		//technology has a maximum that cannot be incremented above
		//technology is incremented one at a time		
		PBase tech=getTech(person).getPBase(techName);
		if(tech == null){
			tech = EntryPoint.game.getPBase(VConstants.technology).getPBase(VConstants.map).getPBase(techName);
			PBase ptech = tech.clone();
			getTech(person).put(techName, ptech);
//			if(tech.containsKey(SConstants.available)){
//				getTech(person).getListCreate(SConstants.available+"l").add(techName);
//			}
			
			int variability = ptech.getInt(SConstants.variability);
			ptech.put(SConstants.variability,VConstants.getRandom().nextInt(variability * 2) - variability);
			tech = ptech;
		}
		
		double size = PBase.getDouble(tech,SConstants.research);
		System.out.println(size+techName);
		
		if(level<= size){
			return;
		}
		size += .01;
		tech.put(SConstants.research, size);
	}
		
	public static void transferTechnology(PBase pop, PBase pop2,String type) {
		//when this is called the population has a random chance of picking a technology
		//and transferring it
		
		//they also have a random chance of starting an entirely new technology
		//it picks from any tech in the current era, if all are chosen then
		//it has a chance of incrementing the era
	}
	
	@Override
	public PBase clone() {
		return new TechnologyRule().copyProperties(this);
	}
	public static PBase getTech(LivingBeing person) {
		return person.getPBase(VConstants.population).getPBase(VConstants.technology);
	}
	public static void addTechs(LivingBeing lb) {
		for(PBase t : getTechList()){
			if(!t.getB(VConstants.enabled)){
				continue;
			}
			PeopleRule.getPopulation(lb).getType(VConstants.technology).getType(VConstants.map).put(t.getS(VConstants.name), t);
		}
	}
	
	public static List<PBase> getTechList(){
		return new ArrayList(EntryPoint.game.getPBase(VConstants.technology).getPBase(VConstants.map).getObjMap().values());
	}
	public static List<PBase> getTechList(LivingBeing person) {
		return (List<PBase>)new ArrayList(getTech(person).getPBase(VConstants.map).getObjMap().values());
	}
	public static void setState(LivingBeing person, String conflict) {
		person.getTemplate().getCurrent().put(SConstants.state,conflict);
		
	}


	public static int getDefaultInt(String people, int i) {
		PBase pb= EntryPoint.game.getPBase(VConstants.technology).getPBase(VConstants.map).getPBase(people);
		if(pb == null){
			return i;
		}
		return PBase.getDefaultInt(pb, VConstants.size, i);
				
	}
	
	public static int getDefaultInt(String type,String name,String key, int i) {
		PBase pb= EntryPoint.game.getPBase(VConstants.technology).getPBase(type).getPBase(name);
		if(pb == null){
			return i;
		}
		return PBase.getDefaultInt(pb, key, i);
				
	}

	public static int getDefaultInt(LivingBeing person,String people, int i) {		
		return PBase.getDefaultInt(getTech(person).getPBase(VConstants.map).getPBase(people),VConstants.size,i);
	}


	public static double getDefaultDouble(String fishingeffectiveness,
			String size, double d) {
		PBase pb= EntryPoint.game.getPBase(VConstants.technology).getPBase(VConstants.map).getPBase(fishingeffectiveness);
		return PBase.getDefaultDouble(pb, VConstants.size, d);
	}


	public static double addOppositeStates(LivingBeing person, String fishing,
			String hunting) {
		double f1=PBase.getDouble(getTech(person).getPBase(VConstants.map).getPBase(fishing),SConstants.research);
		double h1=PBase.getDouble(getTech(person).getPBase(VConstants.map).getPBase(hunting),SConstants.research);
		
		return f1 - h1;
	}
}
