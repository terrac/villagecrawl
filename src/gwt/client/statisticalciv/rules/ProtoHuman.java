package gwt.client.statisticalciv.rules;

import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.OobList;
import gwt.client.main.base.SimpleOObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.statisticalciv.SConstants;
import gwt.client.statisticalciv.oobjects.RemovePerson;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;
import gwt.client.statisticalciv.rules.MysticalQuest.FightVillage;

public class ProtoHuman {

	public static void addProtoHuman(HashMapData hmd,String name){
		//get the under next to it so that it still looks correct
		String type;
		boolean doHair = true;
		if(name.equals(Demographics.hominids)){
			type="orc";
		}else {
			type="giant";
			doHair = false;
		}
		
		LivingBeing lb = RandomPersonCreation.addRandomPerson(hmd,
				type, type,doHair);
//		lb.getAlterHolder().put(VConstants.weapon, new Item("dagger"));
//		lb.getAlterHolder().put(VConstants.body, new Item("Animal Skin"));
		lb.getPopulation().put(VConstants.type, type);
		int amount = 10;
		lb.getPopulation().put(VConstants.size, amount);
		OObject.setCurrent(lb, new SimpleOObject() {
			@Override
			public Returnable execute(FullMapData fullMapData,
					LivingBeing person) {

					OobList oobList = new OobList(new MoveRandomHashMapData(){
						protected void addMove(LivingBeing person) {
							addToList(person, new Move( getToMove(), "moveto "+person.getPosition(),MysticalQuest.moveCombat));
						};
					}, new Wait("happy", 1));
					addToList(person, oobList);

				return new Returnable(true);
			}
		});

	}
}
