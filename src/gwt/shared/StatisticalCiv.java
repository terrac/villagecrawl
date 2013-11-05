package gwt.shared;

import gwt.client.game.ApplyDamage;
import gwt.client.game.AttachUtil;
import gwt.client.game.CreateRandom;
import gwt.client.game.display.UVerticalPanel;
import gwt.client.game.vparams.RunTurn;
import gwt.client.main.Game;
import gwt.client.main.MapArea;
import gwt.client.main.PTemplate;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.SymbolicMap;
import gwt.client.statisticalciv.ConflictRule;
import gwt.client.statisticalciv.CreateInternal;
import gwt.client.statisticalciv.FoodRule;
import gwt.client.statisticalciv.PeopleRule;
import gwt.client.statisticalciv.RunRules;
import gwt.client.statisticalciv.SConstants;
import gwt.client.statisticalciv.TechnologyRule;
import gwt.client.statisticalciv.UVLabel;
import gwt.client.statisticalciv.VillageRule;
import gwt.client.statisticalciv.oobjects.TechnologyAction;

public class StatisticalCiv extends ClientBuild2 {
	private static final String cowAmount = "Cow amount";
	public static final String personAmount = "Person amount";
	public static final String growthIteration = "Growth Iteration";

	/**
	 * create a new package that has statciv rules in it create a map of a
	 * beginning civ (rough outline of africa add in a process that runs once a
	 * turn on each tile
	 * 
	 * @return
	 */
	public static Game doBasicMap() {
		Game game = new Game();

		// game.getMapInitList().add(new ExitTile(0,0,10,0));
		game.put(VConstants.main, true);
		game.put(VConstants.name, "Basic");
		// game.put(VConstants.symbolicmap, true);
		game.setMapArea(new MapArea());
		game.getMapArea().setMap(new SymbolicMap());
		game.put(VConstants.applydamage, new ApplyDamage());

		game.getMapArea().getMap().put(VConstants.xfull, 17);
		game.getMapArea().getMap().put(VConstants.yfull, 15);
		addG(VConstants.runturn, game, new RunTurn());
		game.getMapArea().put(VConstants.turnbased, false);
		AttachUtil.attach(AttachUtil.runpersonbefore, VConstants.runturn,
				game.getMapArea());

		{
			PTemplate pt = addTemplate(game, "person");

			String exp = addG("use", game, new CreateRandom("snake", "rat"));
			addAction(pt, exp);
		}
		PTemplate pt = addTemplate(game, "technology");

		String action = addG("technology", game, new TechnologyAction());
		addAction(pt, action);

		// add a growth rule
		// test watching the people grow across africa
		// lower growth in desert
		// formation of cities

		// AttachUtil.attach(AttachUtil.mapstart, StatisticalCivMap.getMap1(),
		// game.getMapArea().getMap().getData(0, 0));
		AttachUtil.attach(AttachUtil.clickfmd, new CreateInternal(), game
				.getMapArea().getMap());

		
		return game;
	}

	public static PBase doStatMap() {
		return new PBase();
	}

	public static PBase doActions() {
		PBase pb = new PBase();
		pb.put(VConstants.name, "actions");
		pb.put(VConstants.classname, Game.class.getName());

		PBase mapArea = new PBase();
		pb.put(VConstants.maparea, mapArea);
		mapArea.put(VConstants.classname, MapArea.class.getName());

		AttachUtil.attach(AttachUtil.runbefore, new RunRules(new PeopleRule(),
				new FoodRule(),new VillageRule()),
				mapArea);

		AttachUtil.attach(AttachUtil.mapstart, StatisticalCivMap.getMap1(),
				mapArea);
		return pb;
	}

	public static PBase doActionsBigMap() {
		PBase pb = new PBase();
		pb.put(VConstants.name, "actions");
		pb.put(VConstants.classname, Game.class.getName());

		PBase mapArea = new PBase();
		pb.put(VConstants.maparea, mapArea);
		mapArea.put(VConstants.classname, MapArea.class.getName());

		AttachUtil.attach(AttachUtil.runbefore, new RunRules(new PeopleRule(),
				new FoodRule(),new VillageRule()),
				mapArea);

		AttachUtil.attach(AttachUtil.mapstart, StatisticalCivMap.getMap3(),
				mapArea);

		return pb;
	}

	public static PBase doTechnology() {
		PBase pb = new PBase();
		pb.put(VConstants.score, new UVLabel());
		
		pb.put(VConstants.name, VConstants.technology);
		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.main, true);

		PBase tech = new PBase();
		pb.put(VConstants.technology, tech);

		addTechPRoot(tech,VConstants.person,VConstants.human+" female", VConstants.maxsize, 50);
		addTechPRoot(tech,VConstants.person,"sheep", VConstants.maxsize, 400);
		addTechPRoot(tech,VConstants.person,"sheep", VConstants.growth, 40);
		
		
		addTechRoot(tech, SConstants.growthIteration, growthIteration, 400, -1);
		addTechRoot(tech, SConstants.people, personAmount, 1, -1);
		addTechRoot(tech, SConstants.cows, cowAmount, 5, -1);
		addTechRoot(tech, "maxwheat", "maxwheat", 35, -1);
		
		addTechProperty(tech,SConstants.fishing,0);
		addTechProperty(tech,VConstants.conflict,.80);
		
		
		
		addBTech(tech,SConstants.hunting,0);
		//items
		addBTech(tech,SConstants.shepherd,400);
		addBTech(tech,SConstants.farm,800);
		
		
		return pb;
	}
	private static void addBTech(PBase tech, String hunting, double i) {
		// TODO Auto-generated method stub
		tech.getListCreate(VConstants.list).add(new PBase(VConstants.name,hunting,VConstants.turn,i));
	}

	public static PBase doOldTechnology() {
		PBase pb = new PBase();
		pb.put(VConstants.name, VConstants.technology);
		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.main, true);

		PBase tech = new PBase();
		pb.put(VConstants.technology, tech);

		addTechPRoot(tech,VConstants.person,VConstants.human+" female", VConstants.maxsize, 50);
		addTechPRoot(tech,VConstants.person,"sheep", VConstants.maxsize, 400);
		addTechPRoot(tech,VConstants.person,"sheep", VConstants.growth, 40);
		
		
		addTechRoot(tech, SConstants.growthIteration, growthIteration, 400, -1);
		addTechRoot(tech, SConstants.people, personAmount, 1, -1);
		addTechRoot(tech, SConstants.cows, cowAmount, 5, -1);
		addTechRoot(tech, SConstants.fishingEffectiveness,
				SConstants.fishingEffectiveness, .5, -1);
		addTechRoot(tech, "maxwheat", "maxwheat", 35, -1);

		// increases overall effectiveness a little
		addTech(tech, "Simple Language", 10, 0);
		// chance of hunting
		addTech(tech, "hunting", SConstants.hunting, 10, 0,20);
		// chance of gathering
		addTech(tech, "Gathering", VConstants.general, 10, 0);
		// tradable decoration, more from meat
		addTech(tech, "Handaxe", SConstants.fishing, 10, 0);
		// plus to hunting/fighting
		addTech(tech, "spear", SConstants.hunting, 10, 0);

		// basic animal skin, higher means more complicated armor and hats and
		// gloves and capes
		addTech(tech, "Leather", SConstants.hunting, 10, 0);
		// add white skirt
		addTech(tech, "Fiber", SConstants.fishing, 10, 0);
		// greater chance of moving to water, and also of gaining food from
		// fishing
		addTech(tech, SConstants.fishing, SConstants.fishing, 30, 0,20);
		// increased health, places down hut
		addTech(tech, "Huts", 10, 0);
		// chance of picking a fight with other humans, small boost to combat
		// effectiveness
		addTech(tech, VConstants.conflict, 20, 0);

		addTech(tech, "Cooking", 10, 1);
		addTech(tech, "Preserved Meat", 10, 1);
		addTech(tech, "Ritual Burial", 10, 1);
		addTech(tech, "Modern Language", 10, 1);

		addTech(tech, "barter", 10, 1);
		addTech(tech, "Pigment", 10, 1);
		addTech(tech, "Jewelry", 10, 1);
		addTech(tech, "Flutes", 10, 1);
		addTech(tech, "Slavery", 10, 1);

		addTech(tech, "liberalism", 10, 2);
		addTech(tech, "fundamentalism", 10, 2);
		addTech(tech, "", 10, 1);

		
		addChoice(tech, new String[]{"hunting","fishing",VConstants.conflict,"barter","liberalism","fundamentalism"},"fishing","hunting","barter",VConstants.conflict,"liberalism","fundamentalism");
		return pb;
	}

	public static void addTech(PBase tech, String name, double amount, int level) {
		addTech(tech, name, VConstants.general, amount, level);
	}


	public static void addTech(PBase tech, String name, String state,
			double amount, int level) {
		addTech(tech, name, state, amount, level,3);
	}
	
	public static void addTech(PBase tech, String name, String state,
			double amount, int level,int variability) {
			PBase t = new PBase(VConstants.name, name, VConstants.size,amount,
				SConstants.state, state, VConstants.enabled, true,SConstants.variability,variability);;
		tech.getType(VConstants.map).put(name, t);
		tech.getType(SConstants.tree).getType("" + level).getListCreate(state)
		.add(name);
		
	}

	public static void addTechRoot(PBase tech, String iName, String name,
			Object amount, int level) {
		PBase t = new PBase(VConstants.name, name, VConstants.size, amount);
		tech.getType(VConstants.map).put(iName, t);
	}
	
	public static void addTechPRoot(PBase tech,String maintype,String type, String name,
			Object amount) {
		tech.getType(maintype).getType(type).put(name, amount);
	}

	public static void addChoice(PBase tech, String[] nameA,String... opposite) {
		int count = 0;
		for (String a : nameA) {
			PBase t=tech.getType(VConstants.map).getPBase(a);
			t.put(SConstants.available, a);
			t.put(SConstants.opposite, opposite[count]);
			count++;
		}
	}
	
	
	public static void addTechProperty(PBase tech, String name, double amount) {
		tech.getType(VConstants.main).put(name, amount);
	}
}
