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
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;

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
		game.put(VConstants.score, new UVLabel());

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
	
	public static PBase doActions(BuildMap map) {
		PBase pb = new PBase();
		pb.put(VConstants.name, "actions");
		pb.put(VConstants.classname, Game.class.getName());

		PBase mapArea = new PBase();
		pb.put(VConstants.maparea, mapArea);
		mapArea.put(VConstants.classname, MapArea.class.getName());

		AttachUtil.attach(AttachUtil.runbefore, new RunRules(new DemographicRule()),
				mapArea);

		AttachUtil.attach(AttachUtil.mapstart, map,
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
		PBase charmap = buildCharMap();
		
		
		BuildMap bm1 = new BuildMap(charmap, 
				  ""
				+ "wwwwwwwwwwwwwwww\\n"
				+ "wwww         www\\n"
				+ "   n     n      \\n"
				+ "w            n  \\n"
				+ "ww  n    n     w\\n"
				+ "wwwwwwwwwwww  ww\\n"
				+ "wwwwwwwwwwww  ww\\n"
				+ "wwwwwwwwwwwww ww\\n"
				 + "wwdddddddddddwww\\n"
				+ "wwdddddddddddddw\\n"
				+ "wwwhdddddddddddw\\n"
				+ "wwww        hwww\\n"
				+ "wwwwwtr t t wwww\\n"
				+ "wwwwwtttttr wwww\\n"
				+ "wwwwww  r  wwwww\\n"
				+ "wwwwwww p wwwwww\\n"
				+ "wwwwwwwwwwwwwwww", "mainarea");
		PBase resource = new PBase();
		
		bm1.put(VConstants.resource, resource);

		resource.getListCreate(VConstants.leftclick)
				.add(new CopySelection("bagselection"));

		resource.put(VConstants.defaultimage, "/images/grass.png");

		resource.getType(VConstants.resource).put(VConstants.image,
				"/images/itemshop.png");
		resource.getType(VConstants.resource).put(VConstants.sound,
				"tradingmusic");

		return bm1;
	}
	
	public static BuildMap spreadNorth(){
		PBase charmap = buildCharMap();
		
		
		BuildMap bm1 = new BuildMap(charmap, 
				  "wwdddddddddddwww\\n"
				+ "wwdddddddddddddw\\n"
				+ "wwwhdddddddddddw\\n"
				+ "wwww        hwww\\n"
				+ "wwwwwtr t t wwww\\n"
				+ "wwwwwtttttr wwww\\n"
				+ "wwwwww  r  wwwww\\n"
				+ "wwwwwww p wwwwww\\n"
				+ "wwwwwwwwwwwwwwww", "mainarea");
		PBase resource = new PBase();
		
		bm1.put(VConstants.resource, resource);

		resource.getListCreate(VConstants.leftclick)
				.add(new CopySelection("bagselection"));

		resource.put(VConstants.defaultimage, "/images/grass.png");

		resource.getType(VConstants.resource).put(VConstants.image,
				"/images/itemshop.png");
		resource.getType(VConstants.resource).put(VConstants.sound,
				"tradingmusic");

		return bm1;
	}



	public static PBase buildCharMap() {
		PBase charmap = new PBase();

		charmap.put("O", new SimpleMD(VConstants.gate, "orcentrance",
				"/images/grass.png"));

		charmap.put("m", new SimpleMD(VConstants.obstacle, "mountain"));
		charmap.put("r", new SimpleMD(VConstants.gate, "rock"));
		charmap.put("t", new SimpleMD(VConstants.gate, "tree"));
		SimpleMD desert = new SimpleMD(VConstants.under, "desert");
		desert.put(VConstants.maxsize, .3);
		charmap.put("d", desert);
		SimpleMD fish = new SimpleMD(VConstants.obstacle, "water");
		fish.put("fish", "fish");
		charmap.put("w", new SimpleMD(VConstants.obstacle, "water"));
		charmap.put("f", fish);
		SimpleMD pop = new SimpleMD(VConstants.gate,SConstants.farm);
		charmap.put("p", pop);
		charmap.put("h", new SimpleMD(Demographics.hominids,""));
		charmap.put("n", new SimpleMD(Demographics.neanderthals,""));		
		return charmap;
	}

	public static BuildMap getMap3(){
		PBase charmap = buildCharMap();

			
		BuildMap bm1 = new BuildMap(charmap, 
				  "wwww   d    d      d     fwww\\n"
				+ "ww mdddddddddddddddddddd  www\\n"
				+ "ww   d d ddddddddddddddd  ddw\\n"
				+ "www  v        ptr        dddw\\n"
				+ "wwwwww       s r  t    v  www\\n"
				+ "wwwwwwwf  t  s t r       fwww\\n"
				+ "wwwwwwwwwt sp s  t       wwww\\n"
				+ "wwwwwwwww pp t  s       wwwww\\n"
				+ "wwwwwwwww sspr sss s   wwwwww\\n"
				+ "wwwwwwwwssnpppv sts  wwwwwwww\\n"
				+ "wwwwwwwwwt  p s trt wwwwwwwww\\n"
				+ "wwwwwwwww  s  pp r  wwwwwwwww\\n"
				+ "wwwwwwwwww   t    wwwwwwwwwww\\n"
				+ "wwwwwwwwww       wwwwwwwwwwww\\n"
				+ "wwwwwwwwwwww    fwwwwwwwwwwww\\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwww", "mainarea");
		PBase resource = new PBase();
		
		bm1.put(VConstants.resource, resource);
	
		resource.getListCreate(VConstants.leftclick)
				.add(new CopySelection("bagselection"));
	
		resource.put(VConstants.defaultimage, "/images/grass.png");
	
		resource.getType(VConstants.resource).put(VConstants.image,
				"/images/itemshop.png");
		resource.getType(VConstants.resource).put(VConstants.sound,
				"tradingmusic");
	
		return bm1;
	}
	
	public static BuildMap getMap4(){
		PBase charmap = buildCharMap();
		SimpleMD pop = new SimpleMD(VConstants.gate,SConstants.farm);
		charmap.put("z", pop);
		charmap.getPBase("z").getListCreate(VConstants.technology).add(Demographics.simpleLayrnx);
		charmap.getPBase("z").getListCreate(VConstants.technology).add(Demographics.giant);
		BuildMap bm1 = new BuildMap(charmap, 
				  "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\\n"
				+ "www     wwww      wwwwwwwwwwww         \\n"
				+ "ww   z  www       wwwwwwwww            \\n"
				+ "w       ww         wwwwwww             \\n"
				+ "ww                                     \\n"
				+ "wwww                                   \\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwww            \\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwww            \\n"
				  + "wwww   d    d      d     fwww        \\n"
				+ "ww   d d ddddddddddddddd  ddwwww       \\n"
				+ "www  v        ptr        dddwwwww      \\n"
				+ "wwwwww       s r  t    v  wwwwwwwwwwwww\\n"
				+ "wwwwwwwf  t  s t r       fwwwwwwwwwwwww\\n"
				+ "wwwwwwwwwt sp s  t       wwwwwwwwwwwwww\\n"
				+ "wwwwwwwww pp t  s       wwwwwwwwwwwwwww\\n"
				+ "wwwwwwwww sspr sss s   wwwwwwwwwwwwwwww\\n"
				+ "wwwwwwwwssnpppv sts  wwwwwwwwwwwwwwwwww\\n"
				+ "wwwwwwwwwt  p s trt wwwwwwwwwwwwwwwwwww\\n"
				+ "wwwwwwwww  s  pp r  wwwwwwwwwwwwwwwwwww\\n"
				+ "wwwwwwwwww   t    wwwwwwwwwwwwwwwwwwwww\\n"
				+ "wwwwwwwwww       wwwwwwwwwwwwwwwwwwwwww\\n"
				+ "wwwwwwwwwwww    fwwwwwwwwwwwwwwwwwwwwww\\n"
				+ "wwwwwwwwwwwwwwwwwwwwwwwwwwwww", "mainarea");
		PBase resource = new PBase();
		
		bm1.put(VConstants.resource, resource);
	
		resource.getListCreate(VConstants.leftclick)
				.add(new CopySelection("bagselection"));
	
		resource.put(VConstants.defaultimage, "/images/grass.png");
	
		resource.getType(VConstants.resource).put(VConstants.image,
				"/images/itemshop.png");
		resource.getType(VConstants.resource).put(VConstants.sound,
				"tradingmusic");
	
		return bm1;
	}
}
