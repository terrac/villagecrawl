package gwt.shared;

import java.util.Arrays;
import java.util.List;


import gwt.client.game.ApplyDamage;
import gwt.client.game.AttachUtil;
import gwt.client.game.CreateListTurns;
import gwt.client.game.GameUtil;
import gwt.client.game.LevelUp;
import gwt.client.game.PlaySound;
import gwt.client.game.RandomTypeCreation;
import gwt.client.game.VExpression;
import gwt.client.game.display.UIVParams;
import gwt.client.game.vparams.AlterFilter;
import gwt.client.game.vparams.BodyStats;
import gwt.client.game.vparams.BuildMap;
import gwt.client.game.vparams.CallTab;
import gwt.client.game.vparams.CategoryMap;
import gwt.client.game.vparams.CheckAttack;
import gwt.client.game.vparams.CheckDamage;
import gwt.client.game.vparams.DisplayAndOk;
import gwt.client.game.vparams.Exit;
import gwt.client.game.vparams.ExitTile;
import gwt.client.game.vparams.GoTo;
import gwt.client.game.vparams.PutMap;
import gwt.client.game.vparams.ResourceProducer;

import gwt.client.game.vparams.SymbolicMapBuild;
import gwt.client.game.vparams.random.RandomOverMapCreator;
import gwt.client.game.vparams.random.RandomItemCreation;
import gwt.client.game.vparams.random.RandomPersonCreation;
import gwt.client.game.vparams.random.RandomSceneCreation;
import gwt.client.game.vparams.rules.NeedRule;
import gwt.client.game.vparams.rules.TradeValueRule;
import gwt.client.game.vparams.ui.ChatWindow;
import gwt.client.game.vparams.ui.DisplayDesc;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.Economy;
import gwt.client.main.Game;
import gwt.client.main.MapArea;
import gwt.client.main.Move;
import gwt.client.main.PTemplate;
import gwt.client.main.Person;
import gwt.client.main.Say;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.shared.datamodel.VParams;

public class ClientBuild2 extends ClientBuild {

	private static final String[] CLASSES = new String[] { "fighter", "healer",
			"archer", "fire-mage" ,"druid","barbarian","paladin","rogue","necromancer"};

//	public static PBase doScenes() {
//
//		PBase pb = new PBase();
//
//		MapArea maparea = new MapArea(3, 1);
//		pb.put(VConstants.maparea, maparea);
//
//		pb.put(VConstants.classname, Game.class.getName());
//		pb.put(VConstants.name, "scenes");
//
//		List scene3 = list("undead kingdom");
//		List scene4 = list("enemy's lair");
//
//		// build a list of maps and such for each map,
//		// design a couple of lines of dialogue too
//
//		PBase charmap = new PBase();
//
//		charmap.put("H", "hero");
//		charmap.put("b", "fire");
//		charmap.put("h", "house");
//		charmap.put("e", "enemy");
//		charmap.put("X", new SimpleMD(VConstants.gate, "exit", new ExitTile(
//				"city")));
//
//		charmap.put("G", "enemy general");
//		charmap.put("g", new SimpleMD(VConstants.gate, "grass"));
//		charmap.put("t", "tree");
//
//		BuildMap villagedestroyed = new BuildMap(charmap,
//				"                  \\n" + "  H    hb   X          \\n"
//						+ "    b gg  h          \\n"
//						+ "gggggggg   e b      \\n" + " te h G eht g       \\n"
//						+ "  gggebggg         \\n" + "  h t  t   h        \\n"
//						+ "            ");
//
//		charmap = new PBase();
//		charmap.put("w", "wall");
//		charmap.put("H", "Hero");
//		charmap.put("E", "enemy general");
//		BuildMap dungeonenemy = new BuildMap(charmap, "wwwwwwwwwwwww\\n"
//				+ "w\\n" + "w  H  E\\n" + "w\\n" + "w\\n" + "w\\n"
//				+ "wwwwwwwwwwww");
//
//		{
//			String vgd = "village gets destroyed";
//			List scene1 = list(vgd);
//			RandomSceneCreation rsc = new RandomSceneCreation(list(0),
//					list(scene1));
//			rsc.add(vgd, villagedestroyed);
//			rsc.add(vgd, new PlaySound("ahha ha I am destroying your village"));
//			rsc.add(vgd, new RandomItemCreation(1, items));
//
//			AttachUtil.attach(AttachUtil.gamestart, rsc, pb);
//		}
//
//		// put into stored hero, enemy, enemy general
//
//		return pb;
//	}
//
//	public static PBase doStory() {
//
//		PBase pb = new PBase();
//
//		MapArea maparea = new MapArea(3, 1);
//		pb.put(VConstants.maparea, maparea);
//		getType(pb, VConstants.templatemap).put(VConstants.type, "none");
//		
//		pb.put(VConstants.classname, Game.class.getName());
//		pb.put(VConstants.name, "story");
//		//pb.getType(VConstants.jsoncache).put("event", doEvent());
//		VParams defrand;
//		{
//			PBase charmap = new PBase();
//
//			charmap.put("S", VConstants.enter);
//			charmap.put("X", VConstants.exit);
//			
//			BuildMap map = new BuildMap(charmap, "              \\n"
//					+ "              \\n" 
//					+ "              \\n" 
//					+ "              \\n" + 
//					"              \\n" 
//					+ "S             \\n"
//					+ "              \\n" 
//					+ "             X\\n" 
//					+ "          ");
//			
//			map.put(VConstants.defaultimage, "/images/grass.png");
//			defrand = map;
//			//create some large ones too
//	
//		}
//				// build a list of maps and such for each map,
//		// design a couple of lines of dialogue too
//		BuildMap orcinimap;
//		{
//			PBase charmap = new PBase();
//
//			charmap.put("S", VConstants.enter);
//			charmap.put("X", VConstants.exit);
//			charmap.put("o", VConstants.livingbeing+" orc");
//
//			orcinimap = new BuildMap(charmap, "     S     \\n"
//					+ "           \\n" + "           \\n" + "   oooo    \\n"
//					+ "  o  X  o");
//			orcinimap.getListCreate(VConstants.categories).add("orc");
//			orcinimap.put(VConstants.name, "orcs initial");
//		}
//		BuildMap kenkumap;
//		{
//			PBase charmap = new PBase();
//
//			charmap.put("S", VConstants.enter);
//			charmap.put("X", VConstants.exit);
//			charmap.put("k", VConstants.livingbeing+" kenku");
//			charmap.put("t", "temple");
//			kenkumap = new BuildMap(charmap, "              \\n"
//					+ "              \\n" 
//					+ "              \\n" 
//					+ "              \\n" + 
//					"              \\n" 
//					+ "S      t      \\n"
//					+ "       k      \\n" 
//					+ "      k k    X\\n" 
//					+ "     k   k");
//			kenkumap.put(VConstants.defaultimage, "/images/grass.png");
//			kenkumap.getListCreate(VConstants.categories).add("kenku");
//			kenkumap.put(VConstants.name, "kenku raid");
//			
//		}
//		BuildMap earthmap;
//		{
//			PBase charmap = new PBase();
//
//			charmap.put("S", VConstants.enter);
//			charmap.put("X", VConstants.exit);
//
//			earthmap = new BuildMap(charmap, "              \\n"
//					+ "              \\n" + "S            X\\n"
//					+ "              \\n" + "              \\n" + "          ");
//			//earthmap.put(VConstants.defaultimage, "/images/mud.png");
//			earthmap.getListCreate(VConstants.categories)
//					.add("earth elemental");
//			earthmap.put(VConstants.name, "a forgotten battle");
//		}
//		BuildMap villagedestroyed;
//		{
//			PBase charmap = new PBase();
//			
//			
//			charmap.put("b","fire");
//			charmap.put("h","house");
//			charmap.put("e",VConstants.livingbeing+" orc");
//			charmap.put("E",VConstants.livingbeing+" orc healer");
//			charmap.put("S", VConstants.enter);
//			charmap.put("X", VConstants.exit);
//
//
//
//	 
//			villagedestroyed=new BuildMap(charmap,"                  \\n" + 
//					" S    hb   X          \\n" + 
//					"    b ee  h          \\n" + 
//					"      ee     b      \\n" + 
//					"    h E  h          \\n" + 
//					"      b            \\n" + 
//					"  h        h        \\n" + 
//					"            ");
//			villagedestroyed.getListCreate(VConstants.categories)
//			.add("house");
//
//		}
//		
//		{
////			String orcini = "orcs initial";
////			List scene1 = list(orcini);
////
////			String earth = "earth elementals";
////			String kenku = "kenku";
////			List scene2 = list(earth, kenku);
////
////			String orcout = "orc outpost";
////			List scene3 = list(orcout);
//
//			// RandomSceneCreation rsc = new RandomSceneCreation(list(0,1,1,2),
//			// list(scene1,scene2,scene3));
//			// rsc.add(orcini, orcinimap);
//			// rsc.add(orcini, new
//			// CategoryMap("funny","these orcs are a joke, ha ha ha"));
//			//			
//			// rsc.add(earth, earthmap);
//			// rsc.add(earth, new RandomTypeCreation(new
//			// RandomItemCreation(),5));
//			// rsc.add(earth, new RandomTypeCreation(new
//			// RandomPersonCreation(1,"earth elemental"),5));
//			// rsc.add(kenku, kenkumap);
//			// rsc.add(orcout, orcoutmap);
//
//			RandomOverMapCreator rec = new RandomOverMapCreator();
//
//			rec.getListCreate(VConstants.event).add(orcinimap);
//			rec.getListCreate(VConstants.event).add(earthmap);
//			rec.getListCreate(VConstants.event).add(kenkumap);
//			rec.getListCreate(VConstants.event).add(villagedestroyed);
//			for(Object o :rec.getList(VConstants.event)){
//				((PBase) o).put(VConstants.unique,true);
//			}
//			AttachUtil.attach(VConstants.list, new RandomTypeCreation(
//					new RandomItemCreation(2,items), 5), earthmap);
//			AttachUtil.attach(VConstants.list, new RandomTypeCreation(
//					new RandomPersonCreation("earth elemental"), 5),
//					earthmap);
//
//			AttachUtil.attach(VConstants.list, new RandomTypeCreation(
//					new RandomPersonCreation("earth elemental"), 5),
//					earthmap);
//			
//			VParams snakesAndRats=new VParams(defrand,new RandomTypeCreation(
//					new RandomPersonCreation("snake","rat","goblin"), 3));
//			rec.getListCreate(VConstants.event).add(snakesAndRats);
//			snakesAndRats.getListCreate(VConstants.categories)
//			.add("snake");
//
//			PBase charmap = new PBase();
//
//			charmap.put("S", VConstants.enter);
//			charmap.put("X", VConstants.exit);
//			
//			charmap.put("t", "temple");
//			BuildMap map = new BuildMap(charmap,
//					"              \\n"
//					+ "              \\n" + 
//					"S             \\n"
//					+ "      t       \\n" + 
//					"             X\\n" + 
//					"          ");
//			map.put(VConstants.defaultimage, "/images/grass.png");
//			
//			map.put(VConstants.overmap, true);
//			AttachUtil.attach(AttachUtil.mapstart, map, maparea);
//			AttachUtil.attach(AttachUtil.mapstart, rec, maparea);
//		}
//
//		//pb.put(VConstants.templatemap, null);
//		
//		// put into stored hero, enemy, enemy general
//
//		return pb;
//	}
//
//	public static PBase doCity() {
//
//		PBase pb = new PBase();
//
//		MapArea maparea = new MapArea(1, 1);
//		pb.put(VConstants.maparea, maparea);
//
//		pb.put(VConstants.classname, Game.class.getName());
//		pb.put(VConstants.name, "city");
//
//		PBase charmap = new PBase();
//		charmap.put("g", new SimpleMD(VConstants.gate, "grass"));
//		charmap.put("t", "tree");
//		charmap.put("G", "gate");
//		charmap.put("W", "water");
//		charmap.put("r", new SimpleMD(VConstants.gate, "none"));
//		charmap.put("M", "magetower");
//		// item shop logo
//		charmap.put("m", new SimpleMD(VConstants.gate, "itemshop",
//				new ExitTile("merchantarea")));
//		charmap.put("E", new SimpleMD(VConstants.gate, "exit", new ExitTile(
//				"story")));
//		charmap.put("H", "house");
//		
//		charmap.put("U", new SimpleMD(VConstants.gate, "cemetary", new ExitTile(
//		"undeaddefense")));
//		charmap.put("w", "wall");
//		charmap.put("Z", "zoo");
//		charmap.put("S", "slums");
//		charmap.put("D", "dock");
////		charmap.put("A", VConstants.livingbeing+" human healer");
////		charmap.put("B", VConstants.livingbeing+" human fighter");
////		charmap.put("C", VConstants.livingbeing+" human archer");
//		
//
//		BuildMap bm = new BuildMap(charmap, 
//				"wwwwwwwwwwww\\n"
//				+ "WWHtgUt   Sw\\n"
//				+ "WWgggrgg  rw\\n" +
//				  "DDrrmrrrrrrE\\n"
//				+ "WWHHr HHHHrw\\n" + 
//				  "WWH r  HHHMw\\n" + 
//				  "wwwwGwwwwwww");
//
//		bm.put(VConstants.overmap, true);
//		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
//		getType(pb, VConstants.templatemap).put(VConstants.type, "none");
//		return pb;
//	}
//
//	public static PBase doInn() {
//
//		PBase pb = new PBase();
//
//		MapArea maparea = new MapArea(1, 1);
//		pb.put(VConstants.maparea, maparea);
//
//		pb.put(VConstants.classname, Game.class.getName());
//		pb.put(VConstants.name, "inn");
//
//		getType(pb, VConstants.templatemap).put(VConstants.type, "none");
//		PBase charmap = new PBase();
//		// charmap.put("E", new SimpleMD(VConstants.gate, VConstants.entrance,
//		// new ExitTile(
//		// "city", "exit")));
//		charmap.put("w", "wall");
//
//		BuildMap bm = new BuildMap(charmap, "wwwwwwwww\\n" + "w       w\\n"
//				+ "w       w\\n" + "wwwwwwwww");
//
//		bm
//				.getType(VConstants.resource)
//				.getListCreate(VConstants.leftclick)
//				.add(
//						new DisplayAndOk(
//								new DisplayDesc("object.livingbeing.name",
//										"object.livingbeing.stats",
//										"object.livingbeing.attributes", ""),
//								new VExpression(
//										"game.human.gold = game.human.gold - object.livingbeing.gold",
//										new VExpression(
//												"object.livingbeing.team=1")),
//								new Exit("merchantarea")));
//		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
//		AttachUtil.attach(AttachUtil.mapstart, new RandomTypeCreation(
//				new RandomPersonCreation("none", 1, CLASSES,new String[]{"human"}), 40), maparea);
//		bm.put(VConstants.overmap, true);
//		return pb;
//	}
//
//
//	
//	
//	public static PBase doInitialPick(boolean debug) {
//
//		PBase pb = new PBase();
//
//		MapArea maparea = new MapArea(1, 1);
//		pb.put(VConstants.maparea, maparea);
//
//		pb.put(VConstants.classname, Game.class.getName());
//		pb.put(VConstants.name, "initialpick");
//		
//		pb.getType(VConstants.jsoncache).getListCreate(VConstants.list).add("inn");
//		pb.getType(VConstants.jsoncache).getListCreate(VConstants.list).add("story");
//		pb.getType(VConstants.jsoncache).getListCreate(VConstants.list).add("merchantarea");
//		pb.getType(VConstants.jsoncache).getListCreate(VConstants.list).add("city");
//		pb.getType(VConstants.jsoncache).getListCreate(VConstants.list).add("event");
//		
//
//		PBase charmap = new PBase();
//		charmap.put("E", new SimpleMD(VConstants.gate, VConstants.enter,
//				new ExitTile("city", "exit")));
//		charmap.put("w", "wall");
//
//		getType(pb, VConstants.templatemap).put(VConstants.type, "none");
//		// LivingBeing ghost = new Person();
//		// ghost.setTeam(GameUtil.getPlayerTeam());
//		// ghost.put(VConstants.image, "/images/flyingskull.png");
//		// charmap.put("g", ghost);
//
//		BuildMap bm = new BuildMap(charmap, "wwwwwwwww\\n" + "w       w\\n"
//				+ "w       w\\n" + "w       w\\n" + "w       w\\n"
//				+ "wwwwwwwww");
//
//		CategoryMap categorymap = new CategoryMap("funny",
//				"I am making a joke, ha ha ha");
//		bm.getType(VConstants.resource)
//				.put(VConstants.categorymap, categorymap);
//
//		categorymap.put("young", "I am young and naieve");
//		categorymap.put("old", "I am old and wise");
//		categorymap.put("worldly", "I am worldly and sophisticated");
//		categorymap.put("big", "I am big and strong");
//		categorymap.put("lancer", "I am the right hand person");
//		// add remove to displayandok to remove skull from playerlist
//		VParams[] vp =null;
//			
//		if(debug){
//			vp=new VParams[]{new RandomPersonCreation("1", 1, CLASSES,new String[]{"human"}), new VExpression("object.livingbeing.team=1"), new Exit("city")};
//		} else {
//			vp=new VParams[]{new VExpression("object.livingbeing.team=1"), new Exit("city")};
//			pb.put(VConstants.speed, 400);
//		}
//		
//		bm
//				.getType(VConstants.resource)
//				.getListCreate(VConstants.leftclick)
//				.add(
//						new DisplayAndOk(new UIVParams[] {
//								new DisplayDesc("person.templatemap","object.livingbeing.name",
//									"object.livingbeing.attributes",
//										"object.livingbeing.stats"),
//										
//								new ChatWindow() },vp));
//
//		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
//		AttachUtil.attach(AttachUtil.mapstart, new RandomTypeCreation(
//				new RandomPersonCreation("none", 1, CLASSES,new String[]{"human"}), 70), maparea
//				);
//
//		return pb;
//	}
//
//	public static PBase doCircus() {
//		PBase pb = new PBase();
//
//		pb.put(VConstants.name, "circus");
//		pb.put(VConstants.classname, Game.class.getName());
//		PBase charmap = new PBase();
//		charmap.put("g", "grass");
//		charmap.put("t", "tent");
//		charmap.put("G", "gnome");
//
//		charmap.put("E", new SimpleMD(VConstants.gate, VConstants.enter,
//				new ExitTile("merchantarea", "exit")));
//		charmap.put("X", "exit");
//		BuildMap circBM = new BuildMap(charmap, "gggtttttttggg\\n"
//				+ "ggt       tgg\\n" + "gt         tg\\n" + "tE         Xt\\n"
//				+ "t           t\\n" + "gt         tg\\n" + "ggt   G   tgg\\n"
//				+ "gggtttttttggg");
//
//		{
//			Person mon = new Person("illusion");
//			mon.getStats().put(VConstants.strength, 4);
//			mon.getStats().put(VConstants.maxhealth, 200);
//			mon.put(VConstants.level, 1);
//
//			mon.getTemplate().setRationalChild("type", "animal");
//			addG("illusion", pb, mon);
//			mon.put(VConstants.team, "gnome");
//
//			createAttack("illusion sword", VConstants.physical, 1, 1, 2,
//					pb);
//		}
//		{
//			Person mon = new Person("iIIusion");
//			mon.getStats().put(VConstants.strength, 4);
//			mon.getStats().put(VConstants.maxhealth, 100);
//			mon.put(VConstants.image, "/images/illusion.png");
//			mon.put(VConstants.level, 1);
//			mon.getTemplate().setRationalChild("type", "animal");
//			addG("iIIusion", pb, mon);
//			mon.put(VConstants.team, "gnome");
//			mon.getStats().put(VConstants.defaultattack, "sword");
//		}
//
//		{
//			Person mon = new Person("gnome");
//			mon.getStats().put(VConstants.strength, 4);
//			mon.getStats().put(VConstants.maxhealth, 100);
//			mon.put(VConstants.level, 1);
//			mon.put(VConstants.image, "/images/illusion.png");
//			mon.getTemplate().setRationalChild("type", "animal");
//			addG("gnome", pb, mon);
//			mon.put(VConstants.team, "gnome");
//			mon.getStats().put(VConstants.defaultattack, "sword");
//		}
//		String circ = "scene circus with illusions";
//		List scene2 = list(circ);
//		RandomSceneCreation rsc = new RandomSceneCreation(list(0), list(scene2));
//		rsc.add(circ, circBM);
//		rsc.add(circ, new RandomTypeCreation(VConstants.person, 5, "illusion",
//				"iIIusion"));
//
//		AttachUtil.attach(AttachUtil.gamestart, rsc, pb);
//		// put
//		return pb;
//	}

	public static PBase doMerchantArea() {

		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "merchantarea");
		//pb.getType(VConstants.jsoncache).put("inn", doInn());
		PBase charmap = new PBase();

		charmap.put("E", new SimpleMD(VConstants.gate, "exit", new ExitTile(
				"circus", VConstants.entrance)));
		charmap.put("X", new SimpleMD(VConstants.gate, "exit", new ExitTile(
				"city", VConstants.entrance)));
		charmap.put("C", "tent");
		SimpleMD sm = new SimpleMD(VConstants.gate, "itemshop");
		sm.put(AttachUtil.personadded, new CallTab(new RandomItemCreation(3,
				items), "buy"));
		charmap.put("S", sm);

		// sm = new SimpleMD(VConstants.gate, "inn");
		// sm.put(VConstants.image, "/images/house.png");
		// sm
		// .put(AttachUtil.personadded, new CallTab(
		// new RandomPersonCreation(3, CLASSES), "buy"));
		charmap.put("I", new SimpleMD(VConstants.gate, "house", new ExitTile(
				"inn", VConstants.entrance)));

		BuildMap bm = new BuildMap(charmap, 
				  "       I       CCC \\n"
				+ "X             CCCCC\\n"
				+ "   S           CEC\\n"
				+ "                   ");
		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
		bm.put(VConstants.overmap, true);
		return pb;
	}

	public static PBase doMageTower() {

		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "magetower");

		PBase charmap = new PBase();

		charmap.put("X", new SimpleMD(VConstants.gate, "teleporter",
				new ExitTile("endlesswaltz", VConstants.entrance)));
		charmap.put("E", new SimpleMD(VConstants.gate, "exit", new ExitTile(
				"city")));
		charmap.put("w", "wall");

		BuildMap bm = new BuildMap(charmap, "wwwww\\n" + "   Xw\\n"
				+ "E   w\\n" + "wwwww");
		AttachUtil.attach(AttachUtil.gamestart, bm, pb);

		return pb;
	}

//	public static PBase doEndlessWaltz() {
//
//		PBase pb = new PBase();
//
//		MapArea maparea = new MapArea(1, 1);
//		pb.put(VConstants.maparea, maparea);
//
//		pb.put(VConstants.classname, Game.class.getName());
//		pb.put(VConstants.name, "endlesswaltz");
//
//		String vgd = "randomgen";
//		List scene1 = list(vgd);
//		PBase charmap = new PBase();
//		String rkey = "randomscenecreation";
//		charmap.put("X", new SimpleMD(VConstants.gate, "teleporter",
//				new VParams(rkey)));
//		charmap.put("E", new SimpleMD(VConstants.gate, "exit", new ExitTile(
//				"city")));
//
//		BuildMap villagedestroyed = new BuildMap(charmap,
//				"               X \\n" + "                 \\n"
//						+ "                 \\n" + "                 \\n"
//						+ "                 \\n" + "                 \\n"
//						+ "                 \\n" + " E               ");
//		RandomSceneCreation rsc = new RandomSceneCreation(list(0, 1), list(
//				scene1, scene1));
//
//		// rsc setmaparea(fmd,fmd,fmd)
//		// each fmd contains a buildmap on personstartonmap
//		// as well as a discrete type if necessary
//		// both of those get run, and even possibly some randomizers as well
//		// afterwords
//		// then the scene is created
//
//		// and eventually some dialog + categories is created to have some cool
//		// dialogue
//
//		rsc.add(vgd, villagedestroyed);
//
//		rsc.add(vgd, new RandomItemCreation(1, items));
//		RandomPersonCreation rpc = new RandomPersonCreation( "slug");
//		rsc.add(vgd, new RandomTypeCreation(rpc, 5));
//
//		getType(pb, VConstants.vparams).put(rkey, rsc);
//		AttachUtil.attach(AttachUtil.gamestart, rkey, pb);
//
//		// then do more stuff in here
//		// and apply it to others
//
//		Person sp = new Person();
//
//		// person.getTemplate().setRationalChild("type", "defend");
//		sp.getTemplate().setRationalChild("main", "gatheritems");
//
//		sp.put("gold", 50);
//
//		sp.getStats().put(VConstants.damage, 5);
//		sp.getStats().put(VConstants.armor, 5);
//		sp.getStats().put(VConstants.speed, 5);
//		sp.getStats().put(VConstants.intelligence, 5);
//
//		sp.put(VConstants.level, 1);
//
//		sp.getStats().put(VConstants.maxhealth, 100);
//
//		sp.put(VConstants.levelup, new LevelUp());
//		createAttack("bite", VConstants.physical, 1, 1, 1, sp, pb);
//		rpc.put(VConstants.livingbeing, sp);
//
//		return pb;
//	}

	public static PBase doRandomOvermap() {

		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "randomovermap");

		// create the random places/events

		// 1 Friendly person being attacked by monsters
		// (takes the single category (monsters, and turns it into a discrete
		// type)

		// add to events top level
		// create buildmap with F and M
		{
			PBase charmap = new PBase();

			// charmap.put("F", VConstants.friendly);
			charmap.put("M", "monster");
			BuildMap bm = new BuildMap(charmap, "  M      M\\n"
					+ "          \\n" + "        M \\n" + "   M      ");
			bm.getListCreate(VConstants.list)
					.add(
							new CategoryMap("funny",
									"these orcs are a joke, ha ha ha"));
			pb.getListCreate(VConstants.event).add(bm);
			bm.getListCreate(VConstants.categories).add("monster");
			// could attach an altering vp to get different random with same
			// basic structure
		}

		{
			PBase charmap = new PBase();

			charmap.put("S", VConstants.enter);
			charmap.put("X", VConstants.exit);
			charmap.put("h", "orc default");

			BuildMap bm = new BuildMap(charmap, "     S    \\n"
					+ "           \\n" + "           \\n"
					+ "   hhhh        \\n" + "  h  X  h");

			charmap.put("p", "orc priest");
			charmap.put("w", "orc warrior" + "");
			charmap.put("s", "orc sorcerer");
			BuildMap bm2 = new BuildMap(charmap, "    S     \\n" + "    \\n"
					+ " hhhhhhhhh\\n" + "  p w  w p\\n" + "   s   s");

			SymbolicMapBuild symbolicMapBuild = new SymbolicMapBuild(bm, bm2);
			pb.getListCreate(VConstants.event).add(symbolicMapBuild);
			symbolicMapBuild.getListCreate(VConstants.categories).add("orc");
		}

		// do the rest of the scenes, add images to the vps

		// then the initial map should be ready for debugging

		RandomOverMapCreator rec = new RandomOverMapCreator();

		AttachUtil.attach(AttachUtil.gamestart, rec, pb);

		return pb;
	}

	public static PBase doEvent() {
		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);
		getType(pb, VConstants.templatemap).put(VConstants.type, "default");
		
		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "event");
		return pb;
	}

	public static PBase doPeople() {
		PBase game = new PBase();

		game.put(VConstants.main, true);
		game.put(VConstants.classname, Game.class.getName());
		game.put(VConstants.name, "people");

		
		
		
		PBase person = getType(game, VConstants.person);

		Person sp= new Person();
		
		
		sp.put(VConstants.economy,	new Economy(new TradeValueRule(),new NeedRule(VConstants.food,1)));
		//sp.getTemplate().getRationalMap().put(VConstants.need, "fillneed");
		//AttachUtil.attach(AttachUtil.runpersonbefore, new BodyStats(), sp);
		//sp.put(VConstants.economy, new Economy());
//		sp.getEconomy().addNeed(VConstants.hunger, 2,9);
//		sp.getEconomy().addNeed(VConstants.thirst, 2,9);
//		sp.getEconomy().addNeed(VConstants.sleep, 14,16);
//		sp.getStats().put(VConstants.strength, 5);
		sp.getStats().put(VConstants.armor, 5);
		sp.getStats().put(VConstants.intelligence, 5);
		sp.getStats().put(VConstants.strength, 5);
		sp.getStats().put(VConstants.charisma, 5);
		//sp.put("money",50);
		sp.put(VConstants.experience, 20);
		sp.put(VConstants.level, 1);
		sp.getStats().put(VConstants.maxhealth, 100);
		sp.put(VConstants.percenttake, 5);
		sp.getStats().put(VConstants.defaultattack,"punch");
		createAttack("punch",VConstants.physical,1,1,8,game);
		person.put(VConstants.livingbeing,sp);
		sp.put(VConstants.attributes, new PBase());
		List<String> personality = person.getListCreate(VConstants.personality);
		personality.add("young");
		personality.add("old");
		personality.add("worldly");
		personality.add("big");
		personality.add("funny");
		personality.add("lancer");
		List<String> ai = person.getListCreate(VConstants.intelligence);
		ai.add("aggressive");
		//ai.add("defensive");
//		ai.add("bullheaded");
		//ai.add("exploratory");
		//ai.add("greedy");

		List<String> mnames = person.getType(VConstants.name).getListCreate(VConstants.male);
		mnames.add("Brian");
		mnames.add("Chris");
		mnames.add("Sean");
		
		
		List<String> fnames = person.getType(VConstants.name).getListCreate(VConstants.female);
		fnames.add("Elizabeth");
		fnames.add("Terra");
		fnames.add("Tesla");

		getType(game, VConstants.templatemap).put("none", null);
		getType(game, VConstants.templatemap).put(VConstants.type, "default");
		
		getType(person, VConstants.percentagemap).getType("human").put("toughness", .5);
		PBase type =getType(person, VConstants.templatemap).getType(VConstants.type);
		
		PBase classes =getType(person, VConstants.templatemap).getType(VConstants.classes);
		PBase traits =getType(person, VConstants.templatemap).getType(VConstants.traits);
		PBase def = new PBase();
		def.put("aggressive", "attack");
		def.put("bullheaded", "attack");
		def.put("greedy", "gatheritems");
		def.put("defensive", "defend");
		def.put("exploratory", "explore");
		//def.put("cowardly", "defend");
		getType(game, VConstants.templatemap).put("default", def);
		
		createTemplate(traits, "thick hide",VConstants.armor, 1);
		addDescription(traits,"thick hide","You have a naturally thick hide.  Enjoy the extra 10th of a second that it takes for a sword to slice through you");
		

//		createTemplate(traits, "mute",VConstants.talk, "mute");
//		addDescription(traits,"mute","You cannot speak");
		
		createTemplate(traits, "tail");
		addDescription(traits,"tail","You were born with a tail.  It gives you no special advantage or disadvantage");

		createTemplate(traits, "buddhist",VConstants.speed, 1);
		addDescription(traits,"buddhist","You focus on the present.  Freeing you from distractions that would slow you down");
		
		
		createTemplate(traits, "half demon strength",VConstants.type,"demon",VConstants.strength,1);
		addDescription(traits,"half demon strength","Your half demon nature gives you some interesting abilities");
		
//		createTemplate(traits, "christian",VConstants.charisma, 1);
//		addDescription(traits,"christian","You focus on forgiving others.  Your forgiving nature makes you easier to get along with");
		
		createTemplate(type, "giant",VConstants.strength,20);
		setHumanoidOrAnimal(type, "giant",true);
		addDescription(type,"giant","Giants are big");

		
		createTemplate(type, "merfolk",VConstants.armor, -1, VConstants.charisma,1);
		setHumanoidOrAnimal(type, "merfolk",true);
		
		createTemplate(type, "dwarf",VConstants.armor, -1, VConstants.charisma,1);
		setHumanoidOrAnimal(type, "dwarf",true);
		
		
		createTemplate(type, "human",VConstants.armor, -1, VConstants.charisma,1);
		setHumanoidOrAnimal(type, "human",true);
		setWealth(type, "human", 50);
		addDescription(type,"human","Humans are poorly armored, but tend to be highly social creatures");

		createTemplate(type, "kobold",VConstants.speed,1);
		setHumanoidOrAnimal(type, "kobold", true);
		addDescription(type,"kobold","Goblin description");
		setWealth(type, "kobold", 30);
		
		
		createTemplate(type, "orc",VConstants.strength, 1, VConstants.charisma,-1);
		setHumanoidOrAnimal(type, "orc", true);
		addDescription(type,"orc","orcs are strong but smelly");
		setWealth(type, "orc", 45);
		
		createTemplate(type, "kenku",VConstants.speed, 2, VConstants.armor, -2);
		setHumanoidOrAnimal(type, "kenku", true);
		addDescription(type,"kenku","kenku are speedy, but weakly armored");
		setWealth(type, "kenku", 55);
		
		createTemplate(type, "elemental",VConstants.type, "elemental");		
		addDescription(type,"elemental","elementals come in many forms");
		
		createTemplate(type, "wolf",VConstants.type, VConstants.animal);		
		addDescription(type,"wolf","wolves!!!");
		
		createTemplate(type, "skeleton",VConstants.type, VConstants.undead,VConstants.maxhealth,50);		
		addDescription(type,"skeleton","skeletons!!!");
		
		createTemplate(type, "zombie",VConstants.type, VConstants.undead,VConstants.speed, -3);		
		addDescription(type,"zombie","zombies");
		
		createTemplate(type, "illusion",VConstants.image, "/images/illusion.png");		
		addDescription(type,"illusion","They all look pretty similar");

		createTemplate(classes, "fighter",VConstants.strength, 2,VConstants.armor,2,VConstants.defaultattack,"sword");		
		addDescription(classes,"fighter","fighters are strong and tough");
		createAttack("sword",VConstants.physical,1,1,10,game);
		
		createTemplate(classes, "ranger",VConstants.speed, 2,VConstants.defaultattack,"bow");		
		addDescription(classes,"ranger","archers are speedy and do ranged damage");
		createAttack("bow",VConstants.physical,1,3,10,game);
		
		
		createTemplate(classes, "fire-mage",VConstants.intelligence, 2,VConstants.defaultattack,"zap");		
		addDescription(classes,"fire-mage","fire mages are intelligent and do ranged damage.  They get the fireball ability");
		createAttack("zap",VConstants.magic,1,3,5,game);

		createTemplate(classes, "healer",VConstants.armor,2,VConstants.defaultattack,"mace");		
		addDescription(classes,"healer","healers can heal and are tough");
		createAttack("mace",VConstants.physical,1,1,10,game);
		
		createTemplate(classes, "druid",VConstants.defaultattack,"sword");		
		addDescription(classes,"druid","druids can summon animals");
		
		createTemplate(classes, "necromancer",VConstants.defaultattack,"zap");		
		addDescription(classes,"necromancer","necromancers can summon skeletons");
		
		createTemplate(classes, "barbarian",VConstants.defaultattack,"sword",VConstants.armor,2,VConstants.strength,2,VConstants.speed,2);		
		addDescription(classes,"barbarian","barbarians can jump to land a devistating blow");
		
		createTemplate(classes, "paladin",VConstants.defaultattack,"sword",VConstants.armor,4);		
		addDescription(classes,"paladin","paladins can do a weak heal, and are very armored");
		
		createTemplate(classes, "rogue",VConstants.defaultattack,"sword",VConstants.evade,20);		
		addDescription(classes,"rogue","rogues can make critical attacks for more damage on unarmored targets");
		
		createTemplate(classes, "tough",VConstants.maxhealth,200,VConstants.armor,2);		
		addDescription(classes,"tough","They can take an incredible beating");
		
		createTemplate(classes, "strong",VConstants.strength,6);		
		addDescription(classes,"strong","They can give a strong beating");
		
		
		createTemplate(type, "rat",VConstants.strength, -2, VConstants.armor, -2);		
		addDescription(type,"rat","rats are weak and not very tough");
		
		createTemplate(type, "snake",VConstants.speed, 2, VConstants.type,VConstants.poison,VConstants.armor,-15);		
		addDescription(type,"snake","Snakes are speedy and poisonous");
		setHumanoidOrAnimal(type, "snake", false);
		
		
		createTemplate(type, "elephant",VConstants.speed, -2, VConstants.armor,-6);		
		setHumanoidOrAnimal(type, "elephant", false);
		
		createTemplate(type, "yak",VConstants.speed, -1, VConstants.armor,-10);		
		setHumanoidOrAnimal(type, "yak", false);
		
		PBase allSkills = getType(getType(game, VConstants.person),VConstants.allskills);
		{
			PBase pb;

			pb = new PBase();
			pb.put(VConstants.name, "Strength");
			pb.put(VConstants.experience, 100);
			pb.put(VConstants.strength, 10);
			pb.put(VConstants.rechargetime, 30);
			pb.put(VConstants.target, VConstants.self);
			pb.put(VConstants.abilityai, new CheckDamage(pb,100,VConstants.damage));
			
			
			addAbility(pb, game);			
			allSkills.getListCreate("fighter").add(pb);
			
			pb = new PBase();
			pb.put(VConstants.name, "Dodge");
			pb.put(VConstants.experience, 70); // adds 10% to physical evade			
			pb.put(VConstants.evade, 10);
			
			addAbility(pb, game);			
			allSkills.getListCreate("fighter").add(pb);	
		}
		

		{
			PBase pb = new PBase();
			pb.put(VConstants.name, "fireball");
			pb.put(VConstants.damagetype, VConstants.fire);
			pb.put(VConstants.experience, 50);
			pb.put(VConstants.damage, 20);
			pb.put(VConstants.radius, 2);
			pb.put(VConstants.range,4);
			pb.put(VConstants.rechargetime, 10);			
			pb.put(VConstants.target, VConstants.enemy);
			pb.put(VConstants.abilityai, new CheckAttack(pb,AttachUtil.runpersonbefore));
			
			//once used if disabled then set prev attack as default
			
			addAbility(pb, game);			
			allSkills.getListCreate("fire-mage").add(pb);	
		}
		
		{
			PBase pb = new PBase();
			pb.put(VConstants.name, "stab");
			pb.put(VConstants.description, "a powerful attack");
			pb.put(VConstants.experience, 20);
			pb.put(VConstants.damage, 30);					
			pb.put(VConstants.rechargetime, 10);			
			pb.put(VConstants.target, VConstants.enemy);
			pb.put(VConstants.abilityai, new CheckAttack(pb,AttachUtil.runpersonbefore));
			
			//once used if disabled then set prev attack as default
			
			addAbility(pb, game);			
			allSkills.getListCreate("rogue").add(pb);	
		}
		
		{
			PBase pb = new PBase();
			pb.put(VConstants.name, "heal");
			pb.put(VConstants.experience, 20);
			pb.put(VConstants.damage, -20);
			pb.put(VConstants.damagetype, VConstants.white);
			pb.put(VConstants.rechargetime, 200);
			
			CheckDamage action = new CheckDamage(pb,70,GameUtil.getPlayerTeam(),VConstants.damage);
			action.put(VConstants.actor, VConstants.self);
			pb.put(VConstants.abilityai, action);
			pb.put(VConstants.template, "heal");
			// finds party member to heal and creates attack with special desc
			// give attack a param that disables and puts on runturn
			
			addAbility(pb, game);			
			allSkills.getListCreate("healer").add(pb);
			sp.getListCreate(VConstants.abilitysetup).add("heal");
		}
		
		{
			PBase pb = new PBase();
			pb.put(VConstants.name, "weak heal");
			pb.put(VConstants.experience, 20);
			pb.put(VConstants.damage, -10);
			pb.put(VConstants.damagetype, VConstants.white);
			pb.put(VConstants.rechargetime, 6);
			
			
			
			pb.put(VConstants.template, "heal");
			// finds party member to heal and creates attack with special desc
			// give attack a param that disables and puts on runturn
			
			addAbility(pb, game);			
			allSkills.getListCreate("paladin").add(pb);
			//sp.getListCreate(VConstants.abilitysetup).add("heal");
		}
		
		{
			PBase pb = new PBase();
			pb.put(VConstants.name, "summon wolf");
			pb.put(VConstants.experience, 20);
			
			
			pb.put(VConstants.rechargetime, 2000);
			PBase sabil=new PBase();
			pb.put(VConstants.summon,sabil);
			sabil.put(VConstants.name, VConstants.summon);
			sabil.put(VConstants.summon, true);
			sabil.put(VConstants.rechargetime, 2000);
			pb.put(VConstants.template, "summon");
			pb.put(VConstants.target,"wolf");
			addAbility(pb, game);			
			allSkills.getListCreate("druid").add(pb);
			allSkills.getListCreate("ranger").add(pb);
			//sp.getListCreate(VConstants.abilitysetup).add("summon wolf");
		}
		
		{
			PBase pb = new PBase();
			pb.put(VConstants.name, "summon skeletons");
			pb.put(VConstants.experience, 20);
			
			
			pb.put(VConstants.rechargetime, 100);
			PBase sabil=new PBase();
			pb.put(VConstants.summon,sabil);
			sabil.put(VConstants.name, VConstants.summon);
			sabil.put(VConstants.summon, true);
			sabil.put(VConstants.rechargetime, 50);
			pb.put(VConstants.template, "summon");
			pb.put(VConstants.target,"skeleton");
			pb.put(VConstants.size,2);
			addAbility(pb, game);			
			allSkills.getListCreate("necromancer").add(pb);
			sp.getListCreate(VConstants.abilitysetup).add("summon skeletons");
			
		}
		
		
		person.put(VConstants.levelup, new LevelUp());
//		
				
		sp.getListCreate(VConstants.abilitysetup).add("summon wolf2");
		return game;
	}

	static void createTemplate(PBase type, String string, Object ...rest) {
		type.getType(string).put(VConstants.name, string);
		
		for(int a = 0 ; a < rest.length;a+=2){
			type.getType(string).put((String) rest[a], rest[a+1]);
		}
		
		
	}
	static PBase createNN(String name,Integer num,Integer stats){
		return new PBase(VConstants.name,name,VConstants.size,num,VConstants.stats,stats);
	}
	static PBase createNN(String name,Integer num,Integer stats,String key){
		return new PBase(VConstants.name,name,VConstants.size,num,VConstants.stats,stats,VConstants.key,key);
	}
	static PBase createNN(String name,Integer num,Integer stats,boolean communal){
		return new PBase(VConstants.name,name,VConstants.size,num,VConstants.stats,stats,VConstants.communal,communal);
	}
	
	static void addDescription(PBase type, String string, String string2) {
		type.getPBase(string).put(VConstants.description, string2);
	}

	public static PBase doCityStrategy() {

		PBase pb = new PBase();

		MapArea maparea = new MapArea(1, 1);
		pb.put(VConstants.maparea, maparea);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "citystrategy");

		PBase charmap = new PBase();

		SimpleMD goldmine = new SimpleMD(VConstants.gate, "saltmine",
				new VExpression("gate.team = livingbeing.team"));
		goldmine.put(VConstants.resource, list("salt", 20));
		charmap.put("g", goldmine);
		charmap.put("E", list(new SimpleMD(VConstants.gate, "evilcity"),
				"evilenemy"));
		charmap.put("E",
				list(new SimpleMD(VConstants.gate, "goodcity"), "Hero"));

		charmap.put("o", new SimpleMD(VConstants.gate, "1",
				"/images/outpost.png"));
		charmap.put("O", new SimpleMD(VConstants.gate, "evil",
				"/images/outpost.png"));

		// make vexp attach to a not negative attacher on vexp
		// charmap.put("a", new SimpleMD(VConstants.gate, "attack", //vexp
		// executes if the resulting value is above 0, and it does not set the
		// value if it is not
		// new
		// VExpression("game.team.[livingbeing.team].salt = game.team.current.salt- 60",new
		// RandomPersonCreation("attacktemplate","categories","totalvalue"))));
		// charmap.put("b", new SimpleMD(VConstants.gate, "expand",
		// new
		// RandomPersonCreation("attacktemplate","categories","totalvalue")));
		// charmap.put("c", new SimpleMD(VConstants.gate, "defend",
		// new
		// RandomPersonCreation("attacktemplate","categories","totalvalue")));
		// charmap.put("C", new SimpleMD(VConstants.gate, "defendenemy",
		// new
		// RandomPersonCreation("attacktemplate","categories","totalvalue")));

		// all troops are packaged like the overmap, and there can be up to 3
		// fmds showing at once.
		// expand rotates between the two gold mines
		// attack goes for enemy main character (the character will literally
		// have a tag of main on it
		// defend stays at the team's outpost outpust

		// the main issues are
		// 1 producing the resourceproduce
		// 2 making a randompersoncreation with the right variables
		// a making the people generated turn into a person with a list of
		// people stored in an overmapvariable on it
		// b creating a total value that generates units of the right strength
		// (something like value * level for each unit)
		// b allowing it to set the maintemplate as well

		// 3 building the templates
		// a attack = overmapattack("main")
		// b defend = moveclosest(gate,"1")
		// c defendenemy = moveclosest(gate,"evil")
		// d expand = olist( new move(0,0), new move (10,10)
		// 4 making combat work through the overmap
		// a set a overmapcombat variable on a fmd
		// b create an overmap attack that creates an ocmmand on move that takes
		// an unused overmapcombat, and populates it with that unit's team, and
		// the opposing units team
		// c the two then fight

		// 5 making the number of fmds settable

		BuildMap bm = new BuildMap(charmap, "g          aE\\n"
				+ "           bC\\n" + "          O\\n" + "   o       \\n"
				+ "ab         \\n" + "Gc          g");

		AttachUtil.attach(AttachUtil.gamestart, bm, pb);

		return pb;
	}

	private static void setHumanoidOrAnimal(PBase type, String string,
			boolean hora) {
		type.getPBase(string).put(VConstants.humanoid, hora);
	}
	
	private static void setWealth(PBase type, String string,
			int wealth) {
		type.getPBase(string).put(VConstants.wealth, wealth);
	}
	// either sets the initial types, or the additional modifiers on top of it.
	public static void createTemplate(PBase t, String maintype, double d,
			double e, double f, double g, Integer level, String... types) {
		PBase pb = new PBase();
		pb.put(VConstants.type, maintype);
		pb.put(VConstants.armor, d);
		pb.put(VConstants.damage, e);
		pb.put(VConstants.speed, f);
		pb.put(VConstants.intelligence, g);
		pb.put(VConstants.level, level);
		pb.put(VConstants.list, Arrays.asList(types));
		t.put(maintype, pb);
	}

	public static void createTemplate(PBase t, String maintype, double d,
			double e, double f, double g, String... string) {
		createTemplate(t, maintype, d, e, f, g, null, string);
	}

	public static void createSubTypeTemplate(PBase t, String name, String type,
			int min, int max) {
		PBase pb = new PBase();
		pb.put(VConstants.type, type);
		pb.put(VConstants.name, name);
		pb.put(VConstants.minimum, min);
		pb.put(VConstants.maximum, max);
		t.put(name, pb);
	}

	public static PBase doDebug() {

		PBase pb = new PBase();

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "debug");

		// build a list of maps and such for each map,
		// design a couple of lines of dialogue too

		PBase charmap = new PBase();

		charmap.put("H", "hero");
		charmap.put("h", "healer");
		charmap.put("X", new SimpleMD(VConstants.gate, "exit", new ExitTile(
				"story")));

		BuildMap start = new BuildMap(charmap, "HX  \\n" + "h   ");

		AttachUtil.attach(AttachUtil.gamestart, start, pb);

		// put into stored hero, enemy, enemy general

		return pb;
	}

}
