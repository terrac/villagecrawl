package gwt.client.game.vparams.random;

import java.util.List;

import gwt.client.item.Item;
import gwt.client.map.FullMapData;
import gwt.client.map.MapData;

public interface RandomCreation {

	public MapData randomize(List<String> categories);

}