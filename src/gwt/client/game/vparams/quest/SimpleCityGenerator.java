//package gwt.client.game.vparams.quest;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
//
//import gwt.client.EntryPoint;
//import gwt.client.game.AttachUtil;
//import gwt.client.game.GameUtil;
//import gwt.client.game.vparams.random.RandomPersonCreation;
//import gwt.client.item.Item;
//import gwt.client.item.SimpleMD;
//import gwt.client.main.Economy;
//import gwt.client.main.Point;
//import gwt.client.main.VConstants;
//import gwt.client.main.base.LivingBeing;
//import gwt.client.main.base.PBase;
//import gwt.client.main.base.PercentageMap;
//import gwt.client.main.base.under.FoodGroup;
//import gwt.client.map.FullMapData;
//import gwt.client.map.HashMapData;
//import gwt.client.map.Items;
//import gwt.client.map.runners.GetForNearby;
//import gwt.shared.datamodel.VParams;
//
//public class SimpleCityGenerator extends VParams{
//	
//	public SimpleCityGenerator() {
//	}
//	
//	
//	public static int cDone = 0;
//	@Override
//	public void execute(Map<String, Object> map) {
//		int gdp = EntryPoint.game.getInt(VConstants.GDP);
//		List<Integer> gdpl = Arrays.asList(new Integer[]{100,200,300,400,500});
//		final List<String> buildingL = Arrays.asList(new String[]{"tannery","blacksmith","bakery","goldsmith","masonry"});
//		final FullMapData fmd = EntryPoint.game.getHtmlOut().currentFMD;
//		
//		//list of gdp numbers, num for last done, and an
//		//additional list of building names
//		// if the 2nd to last done is above then remove the last done
//		// if the next done is below then add the next
//		if(gdpl.size() <= cDone){
//			return;
//		}
//		if(gdpl.get(cDone) <gdp){
//
//			// on add get nearest no building two radius
//			// put next done
//			fmd.getNearby(new Point(5,5), new GetForNearby<HashMapData>(fmd) {
//				
//				@Override
//				public HashMapData get(HashMapData hashmapdata) {
//					HashMapData h=fmd.getNearby(hashmapdata, new GetForNearby<HashMapData>(fmd) {
//						@Override
//						public HashMapData get(HashMapData hashmapdata) {
//							if(hashmapdata.containsKey(VConstants.gate)){
//								return hashmapdata;
//							}
//							return null;
//						}
//					}, 2);
//					if(h == null){
//						hashmapdata.put(new SimpleMD(VConstants.gate, buildingL.get(cDone)));
//
//						((Economy) EntryPoint.game.get(VConstants.economy)).addNeed("basiccreating", 0, 16);
//						RandomPersonCreation.addRandomPerson(hashmapdata, VConstants.human, GameUtil.getPlayerTeam());
//						return hashmapdata;
//					}
//					return null;
//				}
//				
//			}, 10);
//			//add need
//			//add person
//			cDone++;
//		}
//	}
//	@Override
//	public PBase clone() {
//
//		return new SimpleCityGenerator().copyProperties(this);
//	}
//
//}
