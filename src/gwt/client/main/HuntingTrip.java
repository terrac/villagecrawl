package gwt.client.main;

import gwt.client.game.oobjects.HuntAnimal;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.Participants;
import gwt.client.main.base.PossibleParticipants;
import gwt.client.map.FullMapData;

public class HuntingTrip extends OObjectB implements PossibleParticipants{

	
	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		addToList(person, new MoveRandomFullMapData(1,"hunting"));
		addToList(person, new HuntAnimal());
		addToList(person, new ReturnFood());
		//go to nearest kiln
		
		//if kiln is occupied and there are no other kilns within a radius of 10
		//then create
		
		//if kiln not created create it
		//make item (item waits appropriate turns
		
		
		return null;
	}

	@Override
	public Participants getPartcipants() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OObject clone() {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
