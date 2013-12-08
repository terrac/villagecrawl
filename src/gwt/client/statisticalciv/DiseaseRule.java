package gwt.client.statisticalciv;

import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.ClientBuild;
import gwt.shared.datamodel.VParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiseaseRule extends VParams {

	public static class DiseaseL extends PBase{
		public Disease getActive() {
			for(Object o : this.getObjMap().values()){
				if(o instanceof Disease){
					if(((Disease) o).isActive()){
						return (Disease) o;
					}
				}
			}
			return null;
		}

		@Override
		public DiseaseL clone() {
			// TODO Auto-generated method stub
			return new DiseaseL().copyProperties(this);
		}

		public static void addDisease(PBase pop, Disease disease) {
			DiseaseL dl = getDiseaseL(pop);
			dl.put(disease.getKey(), disease);
		}

		public static DiseaseL getDiseaseL(PBase pop) {
			DiseaseL dl = (DiseaseL) pop.get(SConstants.disease);
			if(dl == null){
				dl = new DiseaseL();
				dl.put(VConstants.resistance, VConstants.getRandom().nextDouble());
				pop.put(SConstants.disease, dl);
				
			}
			return dl;
		}

		
		
		
	}
	public static class Disease extends PBase{
		static String active = "Active";
		static String death = "Death";
		
		public Disease() {
			// TODO Auto-generated constructor stub
		}
		
		public Disease(String name,double chanceOfActivity, double chanceOfDeath) {
			put(VConstants.name,name);
			put(active,chanceOfActivity);
			put(death,chanceOfDeath);
		}
		public boolean isActive() {
			return VConstants.getRandom().nextDouble() < getDouble(active);
		}

		public String getKey() {
			// TODO Auto-generated method stub
			return SConstants.disease;
		}

		@Override
		public Disease clone() {
			// TODO Auto-generated method stub
			return new Disease().copyProperties(this);
		}

		public void spreadDisease(LivingBeing lb) {
			//if lb is on a farm, spread disease to farm
			MapData farm = lb.getParent().getMapData(VConstants.gate);
		
			if(farm != null && farm.getValue().equals(SConstants.farm)){
				PBase pop=farm.getPBase(VConstants.population);
				DiseaseL.addDisease(pop,this);
			}
			if(PeopleRule.isHuman(lb)){
				DisplayPopup displayPopup = new DisplayPopup(ClientBuild.list(
						 new UImage("/images/swamp.png")));
				displayPopup.displaypopup(lb,  1);							
			}
			DiseaseL.addDisease(lb.getPopulation(), this);
		}

		public void damagePopulation(LivingBeing lb) {
			if(VConstants.getRandom().nextDouble() < getDouble(death)- getDouble(VConstants.resistance)){
				PBase.decrement(lb.getPopulation(), VConstants.size, .1);
			}
			//to start with just do population damage
			//could eventually add actions that
		}
	}
	
	public DiseaseRule() {
	}
	
	public DiseaseRule(Object... vp) {
		super(vp);
	}
	List<Disease> diseaseList = new ArrayList();
	{
		diseaseList.add(new Disease("smallpox",.9,.9));
	}
	int lastIntroduced = 0;
		@Override
	public void execute(Map<String, Object> map) {
			FullMapData fmd = getFMD(map);
			
			lastIntroduced--;
			if(lastIntroduced <= 0){
				Disease d =VConstants.getRandomFromList(diseaseList);
				//eventually might differentiate on type of person
				LivingBeing lb=VConstants.getRandomFromList(fmd.people);
				d.spreadDisease(lb);
				lastIntroduced = 90;
			}
			for(LivingBeing lb : fmd.people){
				if(VConstants.getRandom().nextDouble() > .05){
					continue;
				}
				DiseaseL dl=DiseaseL.getDiseaseL(lb.getPopulation());
				
				Disease d = dl.getActive();
				if(d != null){

					d.damagePopulation(lb);
					HashMapData hmd=fmd.getNearestPerson(lb,2);
					if(hmd != null){
						d.spreadDisease(hmd.getLivingBeing());	
					}
					
				}
			}
	}
	@Override
	public PBase clone() {
		return new DiseaseRule().copyProperties(this);
	}

}
