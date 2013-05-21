package gwt.client.statisticalciv;

import gwt.client.EntryPoint;
import gwt.client.game.display.UImage;
import gwt.client.game.vparams.DisplayPopup;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.item.Item;
import gwt.client.main.Move;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.PTemplate;
import gwt.client.main.Point;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.main.base.Parameters;
import gwt.client.main.base.SimpleOObject;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.statisticalciv.oobjects.TechnologyAction;
import gwt.shared.ClientBuild;
import gwt.shared.StatisticalCiv;
import gwt.shared.datamodel.VParams;

import java.util.Arrays;
import java.util.Map;

import com.google.gwt.user.client.Window;

public class PeopleRule extends VParams {

	public PeopleRule() {
	}
	
	public PeopleRule(Object... vp) {
		super(vp);
	}
	boolean init;
	public void addTemplate(FullMapData fmd) {
		if(init){
			return;
		}
		init=true;
		{
			PTemplate pt = StatisticalCiv
					.addTemplate(EntryPoint.game, "person");
			String action = StatisticalCiv.addG("techaction", EntryPoint.game,
					new TechnologyAction());
			StatisticalCiv.addAction(pt, action);
		}
		
		{
			PTemplate pt = StatisticalCiv
					.addTemplate(EntryPoint.game, "person");
			String action = StatisticalCiv.addG("techaction", EntryPoint.game,
					new TechnologyAction());
			StatisticalCiv.addAction(pt, action);
		}
		
		int count = TechnologyRule.getDefaultInt(SConstants.people,5);
		for(HashMapData hmd :fmd){
			if(count > 0){
				LivingBeing lb = addPerson();
				FullMapData.addRandomSpot(fmd, lb);
				
				count--;
			}
		}
		
	}

	@Override
	public void execute(Map<String, Object> map) {
		FullMapData fmd = getFMD(map);

		addTemplate(fmd);


	}

	public static LivingBeing addPerson() {
		LivingBeing lb = RandomPersonCreation.createPerson("human female");
		lb.getType(VConstants.population).put(VConstants.size, (double)10);
		lb.getAlterHolder().put(VConstants.weapon, new Item("spear"));
		lb.getAlterHolder().put(VConstants.body, new Item("animal skin"));
		
		// set template
		lb.setTemplate("person");
		OObject.setCurrent(lb, new OObject() {
			LivingBeing target;
			@Override
			public Returnable execute(FullMapData fullMapData,
					final LivingBeing person) {
				
				Returnable r = null;
				r =handleConflict(fullMapData, person);
				if(r != null) return r;
				r =handleFishing(fullMapData, person);
				if(r != null) return r;
			
				final HashMapData gotten=fullMapData.getNearby(person, new GetForNearby<HashMapData>(fullMapData) {
					@Override
					public HashMapData get(HashMapData hashmapdata) {
						LivingBeing lb=hashmapdata.getLivingBeing();
						if(lb != null){
							PBase pop=lb.getType(VConstants.population);
							if(isHuman(lb)){
								return null;
							}
							if(getB(SConstants.donthuntlowhealth)){
								int pinc = person.getType(VConstants.population).getInt(VConstants.size)/10+1;
								 
								PBase popg = target.getType(VConstants.population);
								int popsize=popg.getInt(VConstants.size);
								if(pinc > popsize){
									return null;
								}
								
							}
							addToList(person, new Move(hashmapdata, "peoplerule",VConstants.food));
							target = lb;
							return hashmapdata;
						}
						
						return super.get(hashmapdata);
					}

					
				}, 4);
				addToList(person, new SimpleOObject() {


					@Override
					public Returnable execute(FullMapData fullMapData,
							LivingBeing person) {
						
						Double pinc = getGrowth(person);
						person.getPopulation().put(SConstants.huntingsuccess, true);
						if(gotten == null||!Point.nextTo(person,gotten)){
							person.getPopulation().put(SConstants.huntingsuccess, false);
							int food= 0;
							if(PBase.getDefaultBoolean(this,SConstants.gather,true)){
								food=5;
								MoveRandomHashMapData moveRandom = new MoveRandomHashMapData(SConstants.gather);
								moveRandom.put(VConstants.overlay, "happy");
								addToList(person, moveRandom);
								return null;
							}
							
							int pop=person.getType(VConstants.population).getInt(VConstants.size);
							FoodRule.eatByPopulation(food, person.getType(VConstants.population), pinc);
							return null;
						} 
						PBase popg = target.getType(VConstants.population);
						Double popsize=PBase.getDouble(popg,VConstants.size);
						popsize += TechnologyRule.getDefaultInt(person,SConstants.hunting, 0);
						popsize=FoodRule.eatByPopulation(popsize, person.getType(VConstants.population), pinc);
						popg.put(VConstants.size, popsize);
						return null;
					}
					
					
				});
				TechnologyRule.setState(person,SConstants.hunting);
				return new Returnable(true);
			}
			public Returnable handleConflict(FullMapData fullMapData,
					final LivingBeing person) {
				double conflict =((double) TechnologyRule.getDefaultInt(person,VConstants.conflict, 0))/100;
				if(VConstants.getRandom().nextDouble() < conflict){
					HashMapData hmd=fullMapData.getNearby(person, new GetForNearby<HashMapData>(fullMapData) {
						public HashMapData get(HashMapData hashmapdata) {
							if(hashmapdata.getLivingBeing() != null&&isHuman(hashmapdata.getLivingBeing())&&!hashmapdata.getLivingBeing().equals(person)){
								return hashmapdata;									
							}
							return null;
						}
						
					}, 2);
					if(hmd != null){
						hmd.getLivingBeing().put(VConstants.visualdamage, VConstants.damage+"sword");
						
						
						person.put(VConstants.visualdamage, VConstants.damage+"sword");
						
						//for some reason the visual damage stuff isn't showing up
						{
							DisplayPopup displayPopup = new DisplayPopup(ClientBuild.list(
									 new UImage("/images/"+VConstants.damage+"sword.png")));
							displayPopup.displaypopup(person, null, 1);							
						}
						{
							DisplayPopup displayPopup = new DisplayPopup(ClientBuild.list(
									 new UImage("/images/"+VConstants.damage+"sword.png")));
							displayPopup.displaypopup(hmd.getLivingBeing(), null, 1);							
						}

						PBase popAttacked=hmd.getLivingBeing().getPBase(VConstants.population);
						PBase pop = person.getPBase(VConstants.population);
						double sizeAttacking = PBase.getDouble(pop,VConstants.size);
						double sizeAttacked=PBase.getDouble(popAttacked,VConstants.size);
						int damage = (int) (sizeAttacking * conflict);
						pop.put(VConstants.size, sizeAttacking-damage);
						popAttacked.put(VConstants.size, sizeAttacking-damage);
						ConflictRule.checkDeath(person);
						ConflictRule.checkDeath(hmd.getLivingBeing());
						TechnologyRule.setState(person,VConstants.conflict);
						//Window.alert("Damage:"+damage +"Attacking:"+sizeAttacking+"Attacked:"+sizeAttacked);
						return new Returnable(true);
					}
				}
				return null;
			}
			public Returnable handleFishing(FullMapData fullMapData,
					final LivingBeing person) {
				double fishing =(double)( TechnologyRule.getDefaultInt(person,SConstants.fishing, 0))/100;
				fishing += TechnologyRule.addOppositeStates(person,SConstants.fishing,SConstants.hunting);
				if(VConstants.getRandom().nextDouble() < fishing){
					HashMapData hmd=fullMapData.getNearKeyValue(VConstants.obstacle, "goldfish", person, 5);
					if(hmd != null){
						addToList(person, new Move(hmd, "fish",VConstants.water));
						double popsize = PBase.getDouble(person.getPopulation(),VConstants.size);
						double pinc = popsize/10+1;
						int eat = (int) (2+((pinc * fishing) + pinc*TechnologyRule.getDefaultDouble(SConstants.fishingEffectiveness,VConstants.size, .5)));
						FoodRule.eatByPopulation(eat, person.getType(VConstants.population), pinc);
						hmd.getMapData(VConstants.obstacle).getPBase(VConstants.population);
						
					}
					TechnologyRule.setState(person,SConstants.fishing);
					return new Returnable(true);
				}
				return null;
			}
			@Override
			public OObject clone() {
				return this;
			}
		});

		TechnologyRule.addTechs(lb);
		return lb;
	}

	public static boolean isHuman(LivingBeing lb) {
		return VConstants.human.equals(lb.getTeam());
	}
	@Override
	public PBase clone() {
		return new PeopleRule().copyProperties(this);
	}

	public static PBase getPopulation(LivingBeing lb) {
		return lb.getPBase(VConstants.population);
	}
	public static double getGrowth(LivingBeing person){
		String name = person.getType();
		int maxsize=TechnologyRule.getDefaultInt(VConstants.person,name, VConstants.maxsize, 50);
		double growth = TechnologyRule.getDefaultInt(VConstants.person,name, VConstants.growth, 4);
		double size = PBase.getDouble(person.getPopulation(), VConstants.size);
		double inefficency = size/maxsize;
		growth=growth -inefficency * growth;
		if(growth < 1){
			growth = 1;
		}
		return growth;
		
	}
}
