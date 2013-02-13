package gwt.client.main;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.personality.Stats;


public class MakeBaby extends OObject{

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		
		if(person.getGroup().readyForBaby(person)){
			makeBaby(person);
		}
		return new Returnable(1);
	}
	
	public boolean makeBaby(LivingBeing person){
		//if(person.getStats().getSex().equals(Stats.Sex.Female.name())){
			put("pregnancy", new Pregnancy(person,90));
			return true;
		//}
		//return false;
	}
	
}
