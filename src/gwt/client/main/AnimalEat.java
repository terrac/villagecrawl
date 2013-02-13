package gwt.client.main;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.Parameters;
import gwt.client.main.base.under.Plant;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class AnimalEat extends OObjectB{
	
	@Override
	public void setParameters(Object... pars) {
		foods = (List<String>)pars[0];		 
	}
	@Override
	public Parameters getParameterTypes() {
		return new Parameters("foods",foods);
	}
	public AnimalEat() {
	}
	public AnimalEat(String... foods) {
		super();
		this.foods = Arrays.asList(foods);
	}


	List<String> foods = new ArrayList<String>();
	
	
	
	public Returnable execute(final FullMapData map,LivingBeing animal) {
		
		
		
		HashMapData nearestToEat=map.getNearby(animal, new GetForNearby() {
			
			@Override
			public MapData get(int x1, int y1) {
				if(map.outside(x1, y1)){
					return null;
				}
				HashMapData hmd=map.getData(x1, y1);
				MapData md=hmd.getMapData(getType());
				if(md != null){
					if(foods.contains(md.getValue())){
						if(md instanceof Plant){
							if(((Plant)md).getHealth() < 30){
								return null;
							}
						}
						
						return md.getParent();
					}					
				}
				
				
				return null;
			}
		}, 20);
		if(nearestToEat == null){
			return new Returnable(false,1);
		}
		animal.getTemplate().pending.add(new Move(nearestToEat,"movetoeat"));
		//animal.getTemplate().pending.add(new Consume(getType(),nearestToEat));
		afterEat(animal);
		return new Returnable(false, 0);
	}


	protected String getToEatKey() {

		return VConstants.foodgroup;
	}


	protected void afterEat(LivingBeing animal) {
		addToList(animal, new Move(animal.getParent(),"movetohome"));
		addToList(animal, new Wait(40));
	}


	protected String getType() {
		return VConstants.foodgroup;
	}

	
}
