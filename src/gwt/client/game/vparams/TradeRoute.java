package gwt.client.game.vparams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.VExpression;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class TradeRoute extends VParams {

	public TradeRoute() {

	}

	public TradeRoute(int x, int y,int i, int j,int interval,String ...items) {
		put(VConstants.xfull, i);
		put(VConstants.yfull, j);
		put(VConstants.xsymbolic, x);
		put(VConstants.ysymbolic, y);
		put(VConstants.items,Arrays.asList(items));
		put(VConstants.interval,interval);
	}
	
	int c2 = 40;
	static int count = 0;
	@Override
	public void execute(Map<String, Object> map) {
		// if not family
		//then create family
		//
		if(EntryPoint.game.getTimeOfDay() %5 != getInt(VConstants.interval)){
			return;
		}
		if(c2 < 40){
			c2++;
			return;
		}
		FullMapData fullMapData = EntryPoint.game.getMapArea().getMap().getData(getInt(VConstants.xsymbolic), getInt(VConstants.ysymbolic));
		int x = getInt(VConstants.xfull);
		int y = getInt(VConstants.yfull);
		
		double nextDouble = VConstants.getRandom().nextDouble();
		if(nextDouble > .95){
			if(count > 5){
				return;
			}
			Object name=VConstants.getRandomFromList((List<String>)getList(VConstants.list));
			// or get trade area
			//fullMapData = EntryPoint.game.getMapArea().getMap().getData(2, 0);
			String gender = VConstants.male;
			if(VConstants.getRandom().nextBoolean()){
				gender = VConstants.female;
			}
	
			addMon(fullMapData,x,y,name+" "+gender,GameUtil.getPlayerTeam());
			count++;
		}  else if(nextDouble < .05) {
			Object name=VConstants.getRandomFromList((List<String>)getList(VConstants.list));
			// or get trade area
			//fullMapData = EntryPoint.game.getMapArea().getMap().getData(2, 0);
			String gender = VConstants.male;
			if(VConstants.getRandom().nextBoolean()){
				gender = VConstants.female;
			}
			LivingBeing lb=addMon(fullMapData,x,y,(String)name+" "+gender,"enemy");
			lb.getStats().put(VConstants.health, 20);
			lb.put(VConstants.economy, null);
			lb.getTemplate().setRationalChild("main", "movetoexit");
		} else {
			// add trader
			
			Object name=VConstants.getRandomFromList((List<String>)getList(VConstants.list));
			// or get trade area
			//fullMapData = EntryPoint.game.getMapArea().getMap().getData(2, 0);
			String gender = VConstants.male;
			if(VConstants.getRandom().nextBoolean()){
				gender = VConstants.female;
			}
			LivingBeing lb=addMon(fullMapData,x,y,(String)name+" "+gender,VConstants.neutral);
			lb.put(VConstants.economy, null);
			lb.getTemplate().setRationalChild("main", "tradingother");
			
			//lb.getItemsCreate().add(EntryPoint.game.getRandomItemCreation().randomize(VConstants.basic));
			Item item=EntryPoint.game.getItemMap().get(VConstants.getRandomFromList(getList(VConstants.items))).clone();
			
			lb.getItemsCreate().add(item);
		}
		c2 = 0;
		
		
		
	
	}

	private LivingBeing addMon(FullMapData fullMapData,int x, int y, String monname,String team) {
		LivingBeing lb = RandomPersonCreation.createPerson(monname);
		lb.setTeam(team);
		//lb.put(VConstants.image, "/images/goblin.png");
		//lb.getTemplate().setRationalChild("main", "recruitable");
		HashMapData nearestEmpty = fullMapData.getNearestEmpty(
				new Point(x, y));
		if(nearestEmpty != null){
			
			nearestEmpty
			.putAppropriate(lb);	
		}
		return lb;
	}
	int enemycount = 3;
	private String getRandomPBase(PBase countMap) {
		// for each determine an upper and a lower number
		// generate a random number in between the total and 0

		// then take the list and determine a upper and a lower number

		// "none" does nothing, any other creates the appropriate monster

		// monsters have a class that sets the template map
		// and in addition the class might set the relative
		// hunger/sleepiness/etc

		// int total = 0;
		//		
		// for(Entry<String, Object> ent : countMap.getObjMap().entrySet()){
		// total +=(Integer) ent.getValue();
		//			
		// }
		//		
		// int rand=VConstants.getRandom().nextInt(total);
		//		
		// int lower = 0;
		// for(Entry<String, Object> ent : countMap.getObjMap().entrySet()){
		// int higher = lower +(Integer) ent.getValue();
		// if(rand > lower && rand < higher){
		// return ent.getKey();
		//				
		// }
		// lower += higher+1;
		// }
		String[] arr = countMap.getObjMap().keySet().toArray(new String[0]);
		int rand = VConstants.getRandom().nextInt(arr.length);
		return arr[rand];
	}

	@Override
	public PBase clone() {

		return new TradeRoute().copyProperties(this);
	}

	public PBase getFriends() {
		
		return getType(VConstants.friends);
	}

	public PBase getEnemies() {

		return getType(VConstants.enemy);
	}

}
