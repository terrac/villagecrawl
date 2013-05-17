package gwt.client.game;

import java.util.List;
import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.game.vparams.random.CreateMD;
import gwt.client.game.vparams.random.RandomCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.MapArea;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.MapObject;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class RandomTypeCreation extends VParams {

	public RandomTypeCreation() {
		// TODO Auto-generated constructor stub
	}

	public RandomTypeCreation(Object type, int totalValue) {
		super();

		put(VConstants.type, type);

		put(VConstants.value, totalValue);
	}

	public List<String> getItems() {
		return (List<String>) get(VConstants.items);
	}

	@Override
	public void execute(Map<String, Object> map) {
		FullMapData fmd = (FullMapData) map.get(AttachUtil.OBJECT);

		int totalValue = getInt(VConstants.value);
		int count = 10;
		while (totalValue > 0 && count > 0) {
			HashMapData hmd = ComplexCityGenerator.getRandom(fmd);
			if (hmd.isBlock()) {
				continue;
			}

			VParams type = (VParams) get(VConstants.type);
			Object o;
			List list = type.getList(VConstants.list);
			if (list == null||list.size() == 0) {
				break;
			}
			if (VConstants.random.equals(type.getS(VConstants.type))) {
				o = VConstants.getRandomFromList(list);
			} else {

				o = list.remove(0);
			}

			Map<String, Object> ma = AttachUtil.createMap(hmd, this);
			ma.put(VConstants.item, o);
			type.execute(ma);
			totalValue -= type.getInt(VConstants.value);
			hmd.putAppropriate((MapData) ma.get(VConstants.item));
			count--;
			
		}

	}

	public PBase clone() {
		return new RandomTypeCreation().copyProperties(this);
	}
}
