package gwt.client.main.base.under;

import gwt.client.main.VConstants;
import gwt.client.map.Direction;
import gwt.client.map.HashMapData;
import gwt.client.map.PhysicalMapData;

public class FoodGroup extends PhysicalMapData{
	@Override
	public HashMapData getParent() {
		return (HashMapData) super.getParent();
	}
public int getHealth() {
return health;
}
public String type;

int health = 100;
static final int maxHealth = 100;

//@Override
//public void update() {
//
//	Under soil=(Under) this.getParent().get(VConstants.under);
//	
//	if(soil.subtractNutrients()){
//		return;
//	}
//	addHealth(10);
//	
//	
//	Direction d =Direction.values()[VConstants.getRandom().nextInt(Direction.values().length)];
//	HashMapData hmd=getParent().getParent().getData(getX()+d.getX(), getY()+d.getY());
//	
//	if(hmd == null){
//		return;
//	}
//	if(!hmd.getUnder().getValue().equals(getUnder())){
//		return;
//	}
//	System.out.println("1"+this.getParent().getPosition());
//	System.out.println(hmd.getPosition());
//	if(hmd.get(getKey()) != null){
//		((FoodGroup)hmd.get(getKey())).addHealth(-10);
//		addHealth(10);
//	}
//	
//	if(hmd.get(getKey()) == null&&!hmd.containsKey(VConstants.obstacle)&&hmd.get(VConstants.gate) == null ){
////		if(hmd.getParent().getX() == 1&&hmd.getParent().getY() == 0){
////			System.out.println();
////		}
//		soil=(Under) hmd.get(VConstants.under);
//		if(soil.nutrients < 50){
//			soil.nutrients += 20;
//			return;
//		}
//		
//		
//		hmd.put(this.clone());
//		
//	}
//
//	
//	
//}
public String getUnder(){
	return VConstants.soil;
}

@Override
public String getKey() {
	
	return VConstants.foodgroup;
}

@Override
public String toString() {

	return getValue()+" health:"+health;
}
public void addHealth(int i) {
	health += i;
	if(health > maxHealth){
		health = maxHealth;
	}
	if(health <= 0){
		getParent().remove(this.getKey());
	}
}

}
