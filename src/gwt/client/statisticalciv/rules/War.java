package gwt.client.statisticalciv.rules;

import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.OobList;
import gwt.client.main.base.PBase;
import gwt.client.main.base.SimpleOObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.statisticalciv.SConstants;
import gwt.client.statisticalciv.oobjects.RemovePerson;
import gwt.shared.ClientBuild;

public class War implements PBaseRule {
	
	public War() {
		// TODO Auto-generated constructor stub
	}

	

	int size = 0;

	@Override
	public boolean run(PBase p, HashMapData hmd, FullMapData fmd) {
		Point location =(Point) VConstants.getRandomFromList(hmd.getMapData(VConstants.gate).getListCreate(VConstants.hatred));
		if(location == null){
			return false;
		}
		if(!fmd.getData(location).containsKey(VConstants.gate)){
			hmd.getMapData(VConstants.gate).getListCreate(VConstants.hatred).remove(location);
		}
		if(p.getDouble(Age.YOUNG_ADULT) < .03||p.getDouble(VConstants.size) < 50){
			return false;
		}
		doYoungMen(p,hmd,location);
		return true;
	}

	private void doYoungMen(PBase p, HashMapData hmd,final Point location) {

		LivingBeing lb = RandomPersonCreation.addRandomPerson(hmd,
				VConstants.human, VConstants.human);
		lb.getAlterHolder().put(VConstants.weapon, new Item("Axe"));
		lb.getAlterHolder().put(VConstants.body, new Item("Leather Armor"));
		lb.getPopulation().put(VConstants.type, SConstants.bandit);
		lb.getPopulation().put(VConstants.size, 17.0);
		Age.remove(p,Age.YOUNG_ADULT,17);
		
		OObject.setCurrent(lb, new SimpleOObject() {
			@Override
			public Returnable execute(FullMapData fullMapData,
					LivingBeing person) {
				HashMapData home = MysticalQuest.getHome(person);
				OobList oobList = new OobList(new Move(fullMapData.getData(location), "raid",MysticalQuest.moveCombat));
				addToList(person, oobList);
				oobList.addNextAction(new MysticalQuest.FightVillage());

				oobList.addNextAction(new RemovePerson());

				// announce return home
				// large movement, potential conflict
				// then move back to create village
				// new BasicStory("The young men return for wives")

				return new Returnable(true);
			}
		});

	}


}
