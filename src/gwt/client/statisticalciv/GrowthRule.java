package gwt.client.statisticalciv;

import java.util.List;
import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VParams;

public class GrowthRule extends VParams{

	public GrowthRule() {
		}
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);
		
		//take the growth of the tile and use it to increase each population according
		//to their cultural/physical growth
		int growth = hmdMain.getInt("growth");
		List<PBase> population;
		try {
			population = hmdMain.getList(VConstants.population);
		} catch (ClassCastException e) {
			hmdMain.remove(VConstants.population);
			population=hmdMain.getListCreate(VConstants.population);
			population.add(ConflictRule.randomize(new PBase()));
		}
		if(population == null){
			
			return;
		}
		for(PBase p : population){
			int size=p.getInt(VConstants.size);
			if(!hmdMain.containsKey("nearcity")){
				growth=growth/2 +1;
				HashMapData toMove = ConflictRule.findNewMD(hmdMain.getParent(), hmdMain);
				if (toMove != null && !toMove.containsKey(VConstants.obstacle)) {
					population.remove(p);
					toMove.getListCreate(VConstants.population).add(p);
					break;
				}
			}else {
				
			}
			size += growth;
			p.put(VConstants.size, size);
		}
		
		
		//as they grow they exchange or moderate physical traits, but also extend and
		//create new ones
	}
@Override
public PBase clone() {
	return new GrowthRule().copyProperties(this);
}
}
