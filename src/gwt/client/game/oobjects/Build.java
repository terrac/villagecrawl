package gwt.client.game.oobjects;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.display.LogDisplay;
import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.MoveClosest;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
import gwt.client.main.WaitMove;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.map.getters.KeyValue;

public class Build extends OObject {
	public Build() {
	}

	public Build(MapData md, int radius) {
		put(VConstants.item, md);
		put(VConstants.radius, radius);
	}

	HashMapData hmd;

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		hmd = (HashMapData) person.getType(VConstants.temporary).get("build");

		// person.getTemplate().stack
		if (hmd == null) {
			// PBase type =
			// EntryPoint.game.getType(VConstants.culture).getType(VConstants.stats);
			// int addedRadius=type.getInt(VConstants.efficency)/5;
			//
			int addedRadius = 0;
			hmd = fullMapData.getNearO(person, getInt(VConstants.radius)
					+ addedRadius, new KeyValue(VConstants.obstacle, true),
					new KeyValue(VConstants.livingbeing, true), new KeyValue(
							VConstants.gate, true));
			//
			// hmd = person.getParent();
			if (hmd == null) {
				return null;
			}
			person.getType(VConstants.temporary).put("build", hmd);

		}
		// Item wood = person.getItemsCreate().getItem("wood");
		//
		// if(wood == null||wood.getAmount() <3){
		// addToList(person, new MoveClosest(VConstants.gate, "tree"));
		// addToList(person, new WaitMove("axe", 2));
		// wood = EntryPoint.game.getItem("wood");
		// wood.setAmount(3);
		// person.getItems().add(wood);
		// } else {
		// wood.subtract(3);
		addToList(person, new Move(hmd, "build"));
		addToList(person, new OObject() {

			@Override
			public Returnable execute(FullMapData fullMapData,
					LivingBeing person) {
				if(hmd == null){
					Window.alert(person.getS(VConstants.owned)+" is missing a hmd on build");
					return null;
				}
				hmd.put(VConstants.owned, person.getS(VConstants.owned));
				hmd.put(Build.this.getMapData(VConstants.item));
				person.getType(VConstants.temporary).remove("build");

				return null;
			}
		});
		// }

		// add resources and remove tree
		// addToList(person, new MoveClosest(VConstants.gate, "rock"));
		// addToList(person, new Wait("build", 20));

		return null;
	}

	@Override
	public OObject clone() {

		return new Build().copyProperties(this);
	}
}
