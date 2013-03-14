package gwt.client.statisticalciv.oobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;

public class TechnologyAction extends OObject {
	static PBase nextTech;
	static LivingBeing nextTechPerson;
	
	static List<String> levels = new ArrayList();
	static PBase tech;
	static {
		tech = new PBase();
		tech.getType(VConstants.action).put("base", "gathering");
		PBase stoneTools = new PBase();
		// image for tool
		// description
		// actions: breaking nuts

		PBase simpleLanguage = new PBase();
		// actions: explain that a hunted animal needs to be carved up
		// after hunting move to other lbs and tell them an animal was killed
		PBase hunting = new PBase();
		// actions: hunt animal
		// behavioral change: follow animals

		PBase flake = new PBase();
		// crude knives
		PBase handaxe = new PBase();
		// handaxe
		// flake or handaxe developes
		// dependent on stone tools
		// actions: preparing leather, basic carving of simple spears

		PBase leather = new PBase();
		// dependent on flake or axe
		PBase fiber = new PBase();
		// adds more clothing items
		PBase fishing = new PBase();
		// action: spear near water

		PBase huts = new PBase();
		// action: set up hut
		addLevel("Lower Paleolithic", stoneTools, simpleLanguage, hunting,
				flake, handaxe, leather, fiber, fishing, huts);

		PBase cooking = new PBase();
		// actions: create a fire, cook over fire

		PBase preservedMeat = new PBase();
		// requires cooking
		// action: smoking meat to store for later eating
		PBase ritualBurial = new PBase();
		// % chance of showing 5
		// action: on a death, they dig with tools and bury a body
		PBase modernLanguage = new PBase();
		// required for next 4
		//
		PBase barter = new PBase();
		// place down paths among the brush
		// action: people occasionally trade basic goods or the other techs
		// behavioral change: occasionally seek out tradable goods
		PBase pigment = new PBase();
		// can be traded, red colored clothing or skin
		// action: take some leather and produce red leather
		PBase jewelry = new PBase();
		// can be traded, worn
		// earliest are shells, can only be created at ocean
		// http://news.bbc.co.uk/2/hi/science/nature/3629559.stm
		// action: use tools to create

		PBase music = new PBase();
		// action: creating flutes, playing music

		PBase slavery = new PBase();// optional
		// action: if a battle occurs, don't kill off all the survivors
		// potential behavioral change: seek out battle more

		// trade ochre and cultural elements like games
		addLevel("Middle Paleolithic", cooking, preservedMeat, ritualBurial,
				modernLanguage, barter, pigment, jewelry, music, slavery);
		PBase fishing3 = new PBase();
		// fishhooks,nets,bows,poisons
		// actions based on these
		PBase carvedEngravedBoneIvoryAntler = new PBase();
		PBase knifeBlades = new PBase();
		// more effective blades
		PBase venusFigurines = new PBase();
		// tradable item
		PBase drillingPiercingTools = new PBase();
		// more effective spears

		PBase cavePaintings = new PBase();
		// action: mark cave when living in them
		PBase projectilePoints = new PBase();
		// more effective spears
		addLevel("Upper Paleolithic", fishing3, carvedEngravedBoneIvoryAntler,
				cavePaintings, venusFigurines, projectilePoints, knifeBlades,
				drillingPiercingTools);
		PBase domesticatedDog = new PBase();
		PBase throwingSpear = new PBase();
		addLevel("Late Stone", throwingSpear, domesticatedDog);
		PBase chiefdoms = new PBase();
		PBase bow = new PBase();
		PBase microlith = new PBase();
		PBase canoe = new PBase();
		PBase socialStratification = new PBase();
		PBase forestGardens = new PBase();
		PBase highways = new PBase();
		addLevel("Mesolithic", canoe, bow, microlith, forestGardens,
				socialStratification, chiefdoms, highways);
		PBase astrology = new PBase();
		PBase agriculture = new PBase();
		PBase prostitution = new PBase();
		PBase animalDomestication = new PBase();
		PBase villages = new PBase();
		PBase beer = new PBase();
		PBase bread = new PBase();
		PBase tools = new PBase();
		addLevel("Neolithic", villages, agriculture, animalDomestication,
				tools, beer, bread, prostitution, astrology);

	}

	@Override
	public Returnable execute(FullMapData fullMapData, LivingBeing person) {
		// if techs are not set then set tech gathering
		// add the template gathering at the same time
		if (!person.containsKey(VConstants.technology)) {
			PBase tech = person.getType(VConstants.technology);
			addTech(tech, person);
			return null;
		}
		PBase tech = person.getType(VConstants.technology);

		// one tech at a time is incremented
		//if the next tech is empty then there is a random chance
		//that the current lb will become the next person
		//this chance is greater if they are near something associated
		//with the tech

		//the next tech is randomly picked from the current level
		//if all of the techs for that level are picked then up the level
		//the livingbeing has a tech level on their tech stuff
		
		//technology spreads upon interaction (conflict,trade, or just moving next to another lb)
		
		//when a tech is newly researched, that is logged, a sound plays and
		//a popup on the population appears explaining the tech
		
		//The growth rule converts to running through living beings
		//it takes the food and increases population sizes
		//the conflict rule also converts to lbs
		//it checks whether or not two people are next to each other
		//and determines how they interact
		//it also determines when a population splits
		
		return null;
	}

	private static void addLevel(String string, PBase... techs) {
	}

	private void incrementTech(PBase p, LivingBeing person) {
		// each tech has its own way of incrementing
		// if none is specificed then it simply increments over time
		PBase pb = p.getPBase("increment");

		if (pb == null) {
			p.increment("incrementNum");
			if (p.getInt("incrementNum") > 100) {
				p.remove("incrementNum");
				addTech(p, person);
			}
		}
	}

	private void addTech(PBase tech, LivingBeing person) {
		// gets the tech

		// gets any associated template mapping and applies them
		for (Entry<String, Object> e : tech.getType(VConstants.action)
				.getObjMap().entrySet()) {
			person.getTemplate().setRationalChild(e.getKey(),
					(String) e.getValue());
		}

	}

	@Override
	public OObject clone() {
		return new TechnologyAction().copyProperties(this);
	}
}
