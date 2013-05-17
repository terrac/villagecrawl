package gwt.shared;

import gwt.client.game.AttachUtil;
import gwt.client.game.CreateListTurns;
import gwt.client.game.RandomTypeCreation;
import gwt.client.game.vparams.BuildMap;
import gwt.client.game.vparams.CategoryMap;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.game.vparams.ExitTile;
import gwt.client.game.vparams.random.RandomItemCreation;
import gwt.client.game.vparams.ui.ChatWindow;
import gwt.client.item.SimpleMD;
import gwt.client.main.Game;
import gwt.client.main.MapArea;
import gwt.client.main.PTemplate;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;

public class ClientBuildAvZ {
	public static PBase doInitialAdventurers(boolean debug) {
		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "initialadventurers");
	
		pb.getType(VConstants.jsoncache).getListCreate(VConstants.list).add("adventurerstory");
		pb.getType(VConstants.jsoncache).getListCreate(VConstants.list).add("firenecromancer");
		pb.getType(VConstants.jsoncache).getListCreate(VConstants.list).add("illusiongnome");
		//do a buildmap of the 4 adventurers
		
		//cleric = mainly heals, no attack enemy meta script
		//sage = shoots fireballs and zaps
		//fighter = direct fighter.  Can strengthen occasionally once upgraded
		//ranger = summon wolf and arrows (wolf sticks around for a long while
		
		//initially fighting some undead that are being summoned by a necromancer
		// (undead are just a list that gets exhausted eventually
		//there is an exit behind the necromancer
		
		
		PTemplate enemygeneral = ClientBuild2.addTemplate(pb, "enemygeneral");
		CreateListTurns clt = new CreateListTurns(5,0, "attack");

		clt.add(1, "zombie");
		clt.add(2, "zombie");
		clt.add(3, "zombie");
		clt.add(4, "zombie");
		
		clt.add(11, "skeleton");
		clt.add(12, "skeleton");
		clt.add(13, "skeleton");
		clt.add(14, "skeleton");

		ClientBuild2.addG("createlistturns", pb, clt);
		ClientBuild2.addAction(enemygeneral, "createlistturns");

		PBase charmap = new PBase();
		{
			PBase person = new PBase();
			charmap.put("S", person);
			person.put(VConstants.traits, "human fire-mage");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Terra");
			person.put(VConstants.team, "1");
			person.put(VConstants.image, "/images/terra.png");
			person.put(VConstants.intelligence, "greedy");
			person.put(VConstants.dontdrawequipment, true);
			
		}
		{
			PBase person = new PBase();
			charmap.put("R", person);
			person.put(VConstants.traits, "elf ranger");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Todd");
			person.put(VConstants.team, "1");
			person.put(VConstants.intelligence, "defensive");
			person.put(VConstants.dontdrawequipment, true);
			
		}
		{
			PBase person = new PBase();
			charmap.put("F", person);
			person.put(VConstants.traits, "human fighter");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Elizabeth");
			person.put(VConstants.image, "/images/elizabeth.png");
			person.put(VConstants.team, "1");
			person.put(VConstants.intelligence, "aggressive");
			person.put(VConstants.dontdrawequipment, true);
			
		}
		{
			PBase person = new PBase();
			charmap.put("C", person);
			person.put(VConstants.traits, "human healer");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Connie");
			person.put(VConstants.team, "1");
			person.put(VConstants.intelligence, "defensive");
			person.put(VConstants.dontdrawequipment, true);
			
		}
		
		{
			PBase person = new PBase();
			charmap.put("N", person);
			person.put(VConstants.traits, "human necromancer");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Herald");
			person.put(VConstants.team, "undead");
			PBase templatemap = new PBase();
			templatemap.put(VConstants.main, "enemygeneral");
			person.put(VConstants.templatemap, templatemap);
			person.put(VConstants.dontdrawequipment, true);
			
		}
		
		if(debug){
			charmap.put("N", "wall");
		}
		
		//all traits should be able to be described this way
		
		charmap.put("t", "tree");
		charmap.put("p", "plant");

		charmap.put("X", new SimpleMD(VConstants.gate, "exit", new ExitTile(
		"adventurerstory")));
		BuildMap bm = new BuildMap(charmap, 
				"      N      \\n" + 
				"ttt      t p \\n" + 
				"  tp ttt     \\n" + 
				"   t         \\n" + 
				"   tt      pp\\n" + 
				" R  pttttt pp\\n" + 
				"     p     pp\\n" + 
				"t   F\\n" + 
				"S   C t   p  \\n" + 
				"X            ");
		bm.put(VConstants.defaultimage, "/images/grass.png");
		CategoryMap categorymap = new CategoryMap();
		bm.getType(VConstants.resource)
		.put(VConstants.script, categorymap);
		categorymap.addLine("elizabeth",
		"Hey, enemies!");
		categorymap.addLine("terra",
		"I call first dibs on the items");

		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
		AttachUtil.attach(AttachUtil.mapstart, new RandomTypeCreation(
				new RandomItemCreation(2,ClientBuild2.items), 5), maparea);
		
		
		AttachUtil.attach(AttachUtil.mapstart, new DisplayAndOk(new ChatWindow()), maparea);
		
		return pb;
	}
	public static PBase doAdventurerScenes(boolean debug) {
		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "adventurerstory");
	
		//buildmap with a simple dungeon that has different types of undead in it
		
		//the initial scene is fighting off undead at a town, and then this is going on the offensive.
		//gold here will symbolize treasure stores and then it will just have
		
		PBase charmap = new PBase();
		charmap.put("S", VConstants.enter);
		charmap.put("w","wall" );
		

		charmap.put("1", new SimpleMD(VConstants.gate, "efreet", new ExitTile(
		"firenecromancer",true)));
		charmap.put("2", new SimpleMD(VConstants.gate, "gnome", new ExitTile(
		"illusiongnome")));
		charmap.put("3", new SimpleMD(VConstants.gate, "lich", new ExitTile(
		"001")));
		charmap.put("4", new SimpleMD(VConstants.gate, "ereshkigal", new ExitTile(
		"001")));
		charmap.put("5", new SimpleMD(VConstants.gate, "iron giant", new ExitTile(
		"001")));
		charmap.put("6", new SimpleMD(VConstants.gate, "asmodeus", new ExitTile(
		"001")));

		
		charmap.put("A", new SimpleMD(VConstants.gate, "gold", new ExitTile(
		"treasure1")));
		charmap.put("B", new SimpleMD(VConstants.gate, "gold", new ExitTile(
		"treasure2")));
		charmap.put("C", new SimpleMD(VConstants.gate, "gold", new ExitTile(
		"treasure3")));
		charmap.put("D", new SimpleMD(VConstants.gate, "gold", new ExitTile(
		"treasure4")));
		
		
		BuildMap bm = new BuildMap(charmap, 
				"wwwwwwwwwwwww\\n" + 
				"wwwww6wwwwwww\\n" + 
				"wC4      5Dww\\n" + 
				"www wwwwwwwww\\n" + 
				"ww        2 w\\n" + 
				"ww3wwwwwwww w\\n" + 
				"wwBww 1     A\\n" + 
				"wwwww wwwwwww\\n" +
				"wwwwwSwwwwwww");
						
		if(debug){
			bm = new BuildMap(charmap, 
					"wwwwwwwwwwwww\\n" + 
					"wwwww6wwwwwww\\n" + 
					"wC4      5Dww\\n" + 
					"www wwwwwwwww\\n" + 
					"ww        2 w\\n" + 
					"ww3wwwww ww w\\n" + 
					"wwBww 1     A\\n" + 
					"wwwww   wwwww\\n" +
					"wwwwwSwwwwwww");
		}
		bm.put(VConstants.overmap, true);
		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
		ClientBuild2.getType(pb, VConstants.templatemap).put(VConstants.type, "none");
		return pb;
	}
	
	public static PBase doFireNecromancer() {
		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "firenecromancer");
	
				
		
		PTemplate enemygeneral = ClientBuild2.addTemplate(pb, "enemygeneral2");
		CreateListTurns clt = new CreateListTurns(5,0, "attack");

		clt.add(1, "fire vortex");
		clt.add(7, "fire elemental");
		clt.add(11, "molten gargoyle");
		
		clt.add(21, "fire vortex");
		clt.add(17, "fire elemental");
		clt.add(31, "molten gargoyle");
		
		ClientBuild2.addG("createlistturns2", pb, clt);
		ClientBuild2.addAction(enemygeneral, "createlistturns2");

		PBase charmap = new PBase();		
		{
			PBase person = new PBase();
			charmap.put("E", person);
										
			person.put(VConstants.traits, "efreet");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Herald");
			person.put(VConstants.team, "fire");
			PBase templatemap = new PBase();
			templatemap.put(VConstants.main, "enemygeneral2");
			person.put(VConstants.templatemap, templatemap);
		}
				
		//all traits should be able to be described this way
		charmap.put("X", new SimpleMD(VConstants.gate, "exit", new ExitTile(
		"adventurerstory")));
		charmap.put("d", new SimpleMD(VConstants.gate,"dry fountain"));
		charmap.put("l", "lava");
		charmap.put("t", "tree dead");
		charmap.put("w", "wall");
		charmap.put("T", "tree lightred");
		charmap.put("S", VConstants.enter);
		charmap.put("X", new SimpleMD(VConstants.gate, "exit", new ExitTile(
		"adventurerstory")));
		BuildMap bm = new BuildMap(charmap, 
				"wwwwwwwwwwwww\\n" + 
				"wT   E    Xtw\\n" + 
				"w       d   w\\n" + 
				"wlllll lllllw\\n" + 
				"wlllll lllllw\\n" + 
				"wlllll lllllw\\n" + 
				"w           w\\n" + 
				"w  T     d  w\\n" + 
				"w    S   t  w\\n" + 
				"wwwwwwwwwwwww");
		bm.put(VConstants.defaultimage, "/images/volcanic-floor.png");
		
		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
		
		CategoryMap categorymap = new CategoryMap();
		bm.getType(VConstants.resource)
		.put(VConstants.script, categorymap);
		categorymap.addLine("asmodeus",
		"Use your firepower efreet.  One of the adventurers is a fire mage");
		categorymap.addLine("efreet", "Why do you stand in the way of progress good adventurers?  I only want to put a bit of heat and industry into your lives.");

		pb.getType(VConstants.templatemap).put(VConstants.type, "default");
		AttachUtil.attach(AttachUtil.mapstart, new DisplayAndOk(new ChatWindow()), maparea);
		return pb;
	}
	
	public static PBase doIllusionGnome() {
		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "illusiongnome");
	
				
		
		PTemplate enemygeneral = ClientBuild2.addTemplate(pb, "enemygeneral3");
		CreateListTurns clt = new CreateListTurns(5,0, "attack");

		clt.add(1, "illusion tough");
		clt.add(2, "illusion strong");
						
		clt.add(11, "illusion strong");
		clt.add(12, "illusion tough");
		
		clt.add(22, "illusion tough");
		clt.add(23, "illusion strong");
		
		
		ClientBuild2.addG("createlistturns3", pb, clt);
		ClientBuild2.addAction(enemygeneral, "createlistturns3");

		PBase charmap = new PBase();		
		{
			PBase person = new PBase();
			charmap.put("E", person);
										
			person.put(VConstants.traits, "gnome");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Herald");
			person.put(VConstants.team, "illusion");
			PBase templatemap = new PBase();
			templatemap.put(VConstants.main, "enemygeneral3");
			person.put(VConstants.templatemap, templatemap);
		}
				
		//all traits should be able to be described this way
		
		charmap.put("i", new SimpleMD(VConstants.gate,"illusion"));
		
		charmap.put("w", "wall");
		charmap.put("S", VConstants.enter);
		charmap.put("X", new SimpleMD(VConstants.gate, "exit", new ExitTile(
		"adventurerstory")));
		BuildMap bm = new BuildMap(charmap, 
				"wwwwwwwwwwwwwwwww\\n" + 
				"wwwwwww E wwwwXww\\n" + 
				"wwiww   w   wwiiw\\n" + 
				"www   wwww    iiw\\n" + 
				"ww   ww iww    ww\\n" + 
				"w   ww    ww    w\\n" + 
				"ww             ww\\n" + 
				"www           www\\n" + 
				"wwwwwwwwSwwwwwwww");
		//bm.put(VConstants.defaultimage, "/images/volcanic-floor.png");
		
		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
		
		CategoryMap categorymap = new CategoryMap();
		bm.getType(VConstants.resource)
		.put(VConstants.script, categorymap);
		categorymap.addLine("asmodeus",
		"Make sure you use your strong and tough illusions to trick the enemy gnome");
		categorymap.addLine("gnome", "Some may call me dishonest or manipulative, but I say I am just freeing you from the burden of too many choices");

		pb.getType(VConstants.templatemap).put(VConstants.type, "default");
		AttachUtil.attach(AttachUtil.mapstart, new DisplayAndOk(new ChatWindow()), maparea);
		return pb;
	}
	
	public static PBase doUndead() {
		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "undead");
	
				
		
		PTemplate enemygeneral = ClientBuild2.addTemplate(pb, "enemygeneral2");
		CreateListTurns clt = new CreateListTurns(5,0, "attack");

		//add negative fire resistance
		clt.add(1, "freezing wraith");
		clt.add(7, "freezing wraith");
		clt.add(11, "freezing wraith");
		
		//well armored
		clt.add(21, "skeleton warrior");
		clt.add(27, "skeleton warrior");
		clt.add(31, "skeleton warrior");
		
		//strong, but extremely weak against fire
		clt.add(31, "mummy");
		clt.add(37, "mummy");
		clt.add(41, "mummy");
		
		
		ClientBuild2.addG("createlistturns2", pb, clt);
		ClientBuild2.addAction(enemygeneral, "createlistturns2");

		PBase charmap = new PBase();		
		{
			PBase person = new PBase();
			charmap.put("E", person);
										
			person.put(VConstants.traits, "efreet");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Herald");
			person.put(VConstants.team, "fire");
			PBase templatemap = new PBase();
			templatemap.put(VConstants.main, "enemygeneral2");
			person.put(VConstants.templatemap, templatemap);
		}
				
		//all traits should be able to be described this way
		charmap.put("X", new SimpleMD(VConstants.gate, "exit", new ExitTile(
		"adventurerstory")));
		charmap.put("d", new SimpleMD(VConstants.gate,"dry fountain"));
		charmap.put("l", "lava");
		charmap.put("t", "tree dead");
		charmap.put("w", "wall");
		charmap.put("T", "tree lightred");
		charmap.put("S", VConstants.enter);
		charmap.put("X", new SimpleMD(VConstants.gate, "exit", new ExitTile(
		"adventurerstory")));
		BuildMap bm = new BuildMap(charmap, 
				"wwwwwwwwwwwww\\n" + 
				"wT   E    Xtw\\n" + 
				"w       d   w\\n" + 
				"wlllll lllllw\\n" + 
				"wlllll lllllw\\n" + 
				"wlllll lllllw\\n" + 
				"w           w\\n" + 
				"w  T     d  w\\n" + 
				"w    S   t  w\\n" + 
				"wwwwwwwwwwwww");
		bm.put(VConstants.defaultimage, "/images/volcanic-floor.png");
		
		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
		
		CategoryMap categorymap = new CategoryMap();
		bm.getType(VConstants.resource)
		.put(VConstants.script, categorymap);
		categorymap.addLine("asmodeus",
		"Chill them to the bone lich");
		categorymap.addLine("lich", "People think of death as a bad thing, but it is working pretty well for me!");

		pb.getType(VConstants.templatemap).put(VConstants.type, "default");
		AttachUtil.attach(AttachUtil.mapstart, new DisplayAndOk(new ChatWindow()), maparea);
		return pb;
	}
}
