package gwt.client.game;

import java.util.List;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.vparams.Poison;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.OutputDirector;
import gwt.client.personality.Stats;

public class ApplyDamage extends PBase{

	//public static final String pevade = "pevade";
	public static final String mevade = "mevade";
	public static final String resistance = "resistance";
	
	{
		List<String> alist=getListCreate(VConstants.altertypes);
		alist.add(VConstants.damageadded);
		alist.add(VConstants.armor);
		alist.add(VConstants.evade);
		alist.add(VConstants.strength);
		alist.add(VConstants.summon);
	}

	public static int getRange(PBase attack){
		Integer range = null;
		if(attack != null){
			range=(Integer) attack.get(VConstants.range);
		}
		
		if (range == null) {
			range = 1;
		}
		return range;
	}
	public void execute(LivingBeing p1, LivingBeing p2,PBase attack) {
//		if (Point.distance(p1, p2) <= getRange(attack)) {
//			return;
//		}
		int damage= 0;
		if(attack != null){
			damage += attack.getInt(VConstants.damage);
		}

		Integer rechargetime = (Integer) attack.get(VConstants.rechargetime);
		if(rechargetime != null){
			// also add a disable on applydamage if there is a recharge time
			p1.toggleDisabled(attack.getS(VConstants.name));

			// make applydamage do some more stuff like apply status changes, and do
			p1.getAlterHolder().put(attack.getS(VConstants.name),attack);

			// apply damage also disables and puts on runturn
			EntryPoint.game.getRunTurn().add(p1,attack);
			
			// self targeting as well (IE no second person)
			// but still with exp gain
			if(p2 == null){
				experience(p1);
				return;
			}
		}
		
		
		String type = null;
		if(attack != null){
			type= (String) attack.get(VConstants.damagetype);
		}
		if(type == null){
			type = (String) p1.getMapArea().get(VConstants.damagetype);
			if(type == null){
				type = VConstants.physical;
			}
		}
		
		
		int radius = attack.getInt(VConstants.radius);
		if(radius > 1){
			final int fd = damage;
			final String ftype = type;
			final LivingBeing fp1 =p1; 
			final PBase fattack = attack;
			p2.getParent().getParent().getNearby(p2, new GetForNearby<HashMapData>(p2.getParent().getParent()) {
				public HashMapData get(HashMapData hashmapdata) {
					LivingBeing lb2 = hashmapdata.getLivingBeing();
					if(lb2 != null){
						setDamage(fd, ftype, fp1, lb2,fattack);
					}
					return null;
					
					
				}
			}, radius);
		} else {
			setDamage(damage, type,p1,p2,attack);	
		}
		
		
		experience(p1);		
		
		
		
		
	}

	private boolean setDamage(int damage, String type,LivingBeing p1, LivingBeing p2, PBase attack) {
		int i=attack.getInt(VConstants.poison);
		if( i != 0){
						
			if(p2.getStats().getInt(VConstants.poison+VConstants.resistance) < 1){
				if(p1.getStats().getInt(VConstants.poison) == 0){
					AttachUtil.attach(AttachUtil.runpersonbefore, new Poison(), p1.getMapArea());	
				}
				p1.getStats().put(VConstants.poison,i+ p1.getStats().getInt(VConstants.poison));
				
			}
		}
		
		if(damage > 0){
			if(VConstants.physical.equals(type)){
				damage += p1.getStats().getInt(VConstants.strength);	
			} else {
				damage += p1.getStats().getInt(VConstants.intelligence);
			}
			
			damage -= p2.getStats().getInt(VConstants.armor);
		}
		
		
		
		String imagename = attack.getS(VConstants.image);
		if(imagename == null){
			imagename = attack.getS(VConstants.name);
		}
		
		
		p2.put(VConstants.visualdamage, VConstants.damage+imagename);
		OutputDirector.soundPlayer.playOnce(type);
		Stats s1= p1.getStats();
		Stats s2 = p2.getStats();
		Integer health = (Integer) s2.get(VConstants.health);
		if(health == null){
			health =(Integer) s2.get(VConstants.maxhealth);
			if(health == null){
				Window.alert(""+ p2.getType()+" has no max health value");
			}
		}
		
		Integer evade =(Integer) s2.get(VConstants.evade);
//		if(!VConstants.physical.equals(type)){
//			evade =(Integer)s1.get(mevade);
//		}
		if(evade != null&&VConstants.getRandom().nextInt(100) <= evade){
			//do miss animation and return
			damage = 0;
			return false;
		}
		
		Integer resistance=(Integer) s2.get(type+ApplyDamage.resistance);
		//could go further and add stats that modify the amount of damage
		
		if(resistance != null){
			//multiply and determine damage
		}
		
		AttachUtil.run(AttachUtil.damage, p2, p2);
		PBase teamh=EntryPoint.game.getPBase(VConstants.team);
		if(teamh != null){
			PBase team=teamh.getPBase(p2.getTeam());
			if( team != null){
				AttachUtil.run(AttachUtil.damage, p2, team);
			}
			
		}
		health = (int) (health - damage);
		
		if(health < 0){
			p2.death();
		}
		int maxhealth = s2.getInt(VConstants.maxhealth);
		if(health > maxhealth){
			health = maxhealth;
		}
		s2.put(VConstants.health, health);
		return true;
	}

	private void experience(LivingBeing p1) {
		p1.put(VConstants.experience, p1.getInt(VConstants.experience) + 5);
		LevelUp lu=(LevelUp) EntryPoint.game.getPBase(VConstants.person).get(VConstants.levelup);
		if(lu != null){
			lu.execute(p1);
		}
	}

	@Override
	public PBase clone() {
		
		return new ApplyDamage().copyProperties(this);
	}
}
