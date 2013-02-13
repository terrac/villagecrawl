package gwt.client.main;
//package main;
//
//import main.base.OObject;
//
//public class Herd extends OObject{
//
//	String animalType;
//	int speed;
//	@Override
//	public void init(String[] pars) {
//		animalType = pars[0];
//		speed = Integer.parseInt(pars[1]);
//	}
//	@Override
//	public boolean execute(HashMapData map, Person person) {
//		MapCountType struct =Util.get(map, "grass");
//		
//		
//		if(struct == null){
//			struct = new MapCountType("grazed"){
//				int growback = 2;
//				public void update() {
//					growback--;
//					
//					if(growback <= 0){
//						count--;
//					}
//				}
//			};
//			map.put(animalType, struct);
//		}
//		
//		struct.count++;
//		if(struct.count > 5){
//			return true;
//		}
//		
//		
//		return false;
//	}
//}
