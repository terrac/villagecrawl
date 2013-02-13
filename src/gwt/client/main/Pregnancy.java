package gwt.client.main;

import gwt.client.item.Item;
import gwt.client.main.base.LivingBeing;
import gwt.client.map.AgeMapData;
import gwt.client.map.HMapData;
import gwt.client.map.MapData;
import gwt.client.personality.Stats;

public class Pregnancy extends AgeMapData {


	public Pregnancy() {
	
	}
	
	public Pregnancy(LivingBeing person,int timetotake) {
		super();
		this.person = person;
		
		setAge((Integer) person.getGroup().getStatic(VConstants.pregnancyTime,timetotake));
	}
	LivingBeing person;
	
	@Override
	public boolean age() {
		if(getAge() == 0){
			Stats stats = new Stats();
			//stats.setSex(VConstants.getRandomEnumString(Stats.Sex.values()));
			
			//LivingBeing value = new LivingBeing(person,stats);
			LivingBeing value = person.getMapArea().addAnimal(person.getValue()); 
			person.getGroup().getFamily().add( value);
			person.getParent().put("baby",value);
			person.getParent().getParent().addPerson(value);
		}
		
		return super.age();
	}
	}
