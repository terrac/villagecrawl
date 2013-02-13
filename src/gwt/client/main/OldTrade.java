package gwt.client.main;
//package main;
//import gui.Log;
//
//import item.Item;
//
//import java.awt.Point;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import main.base.OObject;
//
//import util.PositionUtil;
//
//
//public class Trade extends OObject{
//	
//	@Override
//	public void init(String[] pars) {
//		
//	}
//	@Override
//	public boolean shouldWait(HashMapData map, Person person) {
////		if("merchant".equals(person.getTemplateName())){
////			return false;
////		}
//		if(!map.containsKey("town")){
//			return false;
//		}
//		PickPerson p = new PickPerson();		
//		per=p.execute(person.getParent().getParent(), map,person);
//		
//		boolean b = per == null;
//		
//		return b;
//		
//	}
//	Person per;
//	
//	@Override
//	public boolean execute(HashMapData map, Person person) {
//		
//		
//		if(per == null){
//			return true;
//		}
//		
//
//		HMapData prop=(HMapData) person.get("property");
//		HMapData propoth=(HMapData) per.get("property");
//		
//		List<Item> oth=tradeOffer(per,person);		
//		List<Item> thi=tradeOffer(person,per);
//		
//		
//		//if either is zero there can be no trade
//		
//
//		Log.log("trade","begin"+ per);
//		Log.log("trade","begin"+ person);
//		if(thi.size() == 0||oth.size() == 0){
//			return true;
//		}
//
//		moveItems(prop, propoth, thi, oth);
//		//moveItems(propoth, prop, oth, thi);
//		
////		cleanItems(prop);
////		cleanItems(propoth);
//		Log.log("trade","end"+ per);
//		Log.log("trade","end"+ person);
//		
//		per = null;
//		return true;
//	}
//	protected void cleanItems(HashMapData propoth) {
//		// TODO Auto-generated method stub
//		for(MapData ito: propoth.map.values()){
//			
//			Item it = (Item) ito;
//			if(it.number < 1){
//				propoth.remove(it.name);
//			}
//			
//			
//		}	
//	}
//	
//	protected void moveItems(HMapData prop, HMapData propoth,
//			List<Item> thi, List<Item> oth) {
//		for(Item item : thi){
//			for(Item ito: oth){
//				
//					if(item.number == 0||ito.number == 0){
//						continue;
//					}
//					item.add( 1,propoth);
//					item.subtract(1,prop);
//					ito.add(1, prop);
//					
//					ito.subtract(1,propoth);
//
//				
//				
//			}
//		}
//	}
//	public List<Item> tradeOffer(Person person, Person per2){
//		HMapData property=(HMapData) person.get("property");
//		List<Item> il = new ArrayList<Item>();
//		for(MapData md :property.map.values()){
//			Item item = (Item) md;
//			
//			int val = 0;
//			for(Object po: person.getValueList()){
//				Value v = (Value) po;
//				val+=v.value(item, person, per2);
//				
//			}
//			Item item2 = (Item) per2.getProperty().get(item.name);
//			if(item2 == null){
//				// seems inefficent
//				item2 = item.clone();
//				item2.number = 0;
//			}
//			int val2 =0;
//			for(Object po: per2.getValueList()){
//				Value v = (Value) po;
//				
//				val2+=v.value(item2, person, per2);
//				
//			}
//			if(val < val2){
//				il.add(item);
//			}
//			
//		}
//		
//		return il;
//	}
//	
//}
