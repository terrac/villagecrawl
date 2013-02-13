package gwt.client.main;
import gwt.client.main.base.LivingBeing;

import java.util.Arrays;



public class WildAnimalEat extends AnimalEat{
	
	public WildAnimalEat() {
	
	}
	public WildAnimalEat(String... foods) {
		super();
		this.foods = Arrays.asList(foods);
	}

	


	@Override
	protected void afterEat(LivingBeing animal) {

	}
}
