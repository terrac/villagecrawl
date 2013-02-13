package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;
import gwt.client.output.OutputDirector;

public class ChildsPlay extends OObject{

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		
		//chase other child
		
//		if(person.getItems().size() > 0){
//			addToList(person, new ReturnFood());
//		} 
//		if(VConstants.getRandom().nextInt(10) < 6){
//			LivingBeing child = null;
//			for(LivingBeing lb :person.getGroup().getFamily()){
//				if(!lb.equals(person)&&lb.getType().contains("child")){
//					child = lb;
//				}
//			}
//			addToList(person, new Move(child.getParent(),"chase"));
//		}else {
			addToList(person, new MoveRandomHashMapData("childsplay"));
//		}
		
		
		
		return null;
	}
	@Override
	public OObject clone() {
		return new ChildsPlay().copyProperties(this);
	}
}
