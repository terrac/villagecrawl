package gwt.shared;

import gwt.client.game.ApplyDamage;
import gwt.client.game.AttachUtil;
import gwt.client.game.CreateRandom;
import gwt.client.game.display.UVerticalPanel;
import gwt.client.game.vparams.BuildMap;
import gwt.client.game.vparams.CopySelection;
import gwt.client.game.vparams.RunTurn;
import gwt.client.item.SimpleMD;
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
import gwt.client.statisticalciv.rules.DemographicRule;

public class DemographicCiv extends ClientBuild2 {

	/**
	 * create a new package that has statciv rules in it create a map of a
	 * beginning civ (rough outline of africa add in a process that runs once a
	 * turn on each tile
	 * 
	 * @return
	 */
	public static Game doBasicMap() {
		Game game = new Game();

		game.put(VConstants.main, true);
		game.put(VConstants.name, "Basic");
		game.setMapArea(new MapArea());
		game.getMapArea().setMap(new SymbolicMap());
		game.put(VConstants.applydamage, new ApplyDamage());
		addG(VConstants.runturn, game, new RunTurn());
		game.getMapArea().put(VConstants.turnbased, false);
		AttachUtil.attach(AttachUtil.runpersonbefore, VConstants.runturn,
				game.getMapArea());
		return game;
	}

	

	public static PBase doActions() {
		PBase pb = new PBase();
		pb.put(VConstants.name, "actions");
		pb.put(VConstants.classname, Game.class.getName());

		PBase mapArea = new PBase();
		pb.put(VConstants.maparea, mapArea);
		mapArea.put(VConstants.classname, MapArea.class.getName());

		AttachUtil.attach(AttachUtil.runbefore, new RunRules(new DemographicRule()),
				mapArea);

		AttachUtil.attach(AttachUtil.mapstart, getMap1(),
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

		
		
		return pb;
	}
	public static BuildMap getMap1(){
		PBase charmap = new PBase();

		charmap.put("O", new SimpleMD(VConstants.gate, "orcentrance",
				"/images/grass.png"));

		charmap.put("m", new SimpleMD(VConstants.obstacle, "mountain"));
		charmap.put("r", new SimpleMD(VConstants.gate, "rock"));
		charmap.put("t", new SimpleMD(VConstants.gate, "tree"));
		charmap.put("d", new SimpleMD(VConstants.under, "desert"));
		SimpleMD fish = new SimpleMD(VConstants.obstacle, "water");
		fish.put("fish", "fish");
		charmap.put("w", new SimpleMD(VConstants.obstacle, "water"));
		charmap.put("f", fish);
		SimpleMD pop = new SimpleMD(VConstants.gate,SConstants.farm);
		charmap.put("p", pop);
		
		
		BuildMap bm1 = new BuildMap(charmap, 
				  "wwwwwwwwwwwwwwww\\n"
				+ "wwdddddddddddwww\\n"
				+ "wwdddddddddddddw\\n"
				+ "wwwddddddddddddw\\n"
				+ "wwwww p    p www\\n"
				+ "wwwwwtr t t wwww\\n"
				+ "wwwwwtttttr wwww\\n"
				+ "wwwwww  r  wwwww\\n"
				+ "wwwwwww p wwwwww\\n"
				+ "wwwwwwwwwwwwwwww", "mainarea");
		PBase resource = new PBase();
		
		bm1.put(VConstants.resource, resource);

		resource.getType(VConstants.resource)
				.getListCreate(VConstants.leftclick)
				.add(new CopySelection("bagselection"));

		resource.put(VConstants.defaultimage, "/images/grass.png");

		resource.getType(VConstants.resource).put(VConstants.image,
				"/images/itemshop.png");
		resource.getType(VConstants.resource).put(VConstants.sound,
				"tradingmusic");

		return bm1;
	}

}
