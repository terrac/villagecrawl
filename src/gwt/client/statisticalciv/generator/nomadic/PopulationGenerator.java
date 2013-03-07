package gwt.client.statisticalciv.generator.nomadic;

import java.util.List;
import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.game.CreateRandom;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.statisticalciv.ConflictRule;
import gwt.shared.datamodel.VParams;

public class PopulationGenerator extends VParams {

	public PopulationGenerator() {
	}

	/**
	 * 
	 counts up the number of already populated 
	 
	 subtracts and 
	 
	 */
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);
		FullMapData fmd = (FullMapData) map.get(VConstants.fullmapdata);

		List<PBase> population = hmdMain.getList(VConstants.population);
		
		int tPop = ConflictRule.getTPop(population);
		int totalCount = 20;
		int count = 0;
		
		for(PBase p : population){
			int totalsize = p.getInt("totalsize");
			if(totalsize == 0){
				totalsize = 20;
			}
			int size = p.getInt(VConstants.size);
			double d = ((double)size /totalsize);
			int numberPop = (int) (d * 5)+1; 
			while(numberPop > 0){
				LivingBeing lb=RandomPersonCreation.createPerson("human female");
				numberPop--;
				Direction dir=Direction.getRandom();
				
				HashMapData nearestEmpty = fmd.getNearestEmpty(new Point(fmd.getXsize() *dir.getX(),fmd.getYsize()* dir.getY()));
				if(nearestEmpty != null){
					nearestEmpty.putAppropriate(lb);
						
				}
				count++;
				totalCount--;
				if(totalCount < 0){
					break;
				}
			}
		}
		//take the total population for this tile
		//create 20 total people if the total population is max
		//divide them among groups
		//if they are different populations
		
		//then among each group assign actions 4 hunt, 5 gather, 1 shaman 
		//and base it on gender
		
		
//      1 chance of them moving after a while, they mostly just hunt and gather
	    //  2 hunting, animals are created and hunted in a group fashion, 
		//gathering happens nearby and they just walk near food lying
		
		// note that over time as tech just naturally increases
		//the gathering should get larger, and at some point pop over
		//to farming on the statistics side of things
		//more gatherers in the population= more gathering increases
		//which increases general population growth
		
	}

	@Override
	public PBase clone() {
		return new PopulationGenerator().copyProperties(this);
	}
}
