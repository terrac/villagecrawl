package gwt.client.game.ability;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;

public class Summon extends OObject {

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		PBase ability = (PBase) EntryPoint.game.getPBase(VConstants.person).getPBase(VConstants.ability).get(person.getPBase(VConstants.temporary).getS(VConstants.summon));
		String aname = ability.getS(VConstants.name);
		if(person.getDisabled(aname)){
			return null;
		}
		person.toggleDisabled(aname);
		int size = ability.getInt(VConstants.size);
		if(size == 0){
			size =1;
		}
		for(int a = 0; a < size; a++){
			LivingBeing createPerson = RandomPersonCreation.createPerson(ability.getS(VConstants.target));
			createPerson.put(VConstants.team, person.getTeam());
			
			PBase summonabil=ability.getPBase(VConstants.summon);
			createPerson.put(VConstants.summon, true);
			createPerson.getAlterHolder().put(summonabil.getS(VConstants.name),summonabil);
			EntryPoint.game.getRunTurn().add(createPerson, summonabil);
			
			
			fullMapData.getNearestEmpty(person).putAppropriate(createPerson);
		}
		EntryPoint.game.getRunTurn().add(person, ability);
		return new Returnable(1);
	}

	@Override
	public OObject clone() {
		
		return new Summon().copyProperties(this);
	}
}
