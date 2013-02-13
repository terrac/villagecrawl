package gwt.client.game.ability;

import java.util.Map;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.vparams.CheckDamage;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;

public class Heal extends OObject {

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		PBase ability = (PBase) EntryPoint.game.getPBase(VConstants.person).getPBase(VConstants.ability).get(person.getPBase(VConstants.temporary).getS(VConstants.heal));
		
		String aname = ability.getS(VConstants.name);
		if(person.getDisabled(aname)){
			return null;
		}
		//person.toggleDisabled(aname);
		
		CheckDamage action = new CheckDamage(ability,99,person.getTeam(),VConstants.damage);
		for(LivingBeing lb :GameUtil.getPlayerTeam(fullMapData.people)){
			Map<String, Object> map = AttachUtil.createMap(lb,this);
			action.execute(map);
			if(map.get(VConstants.success) != null){
				break;
			}
		}
		
		return new Returnable(0);
	}

	@Override
	public OObject clone() {
		
		return new Heal().copyProperties(this);
	}
}
