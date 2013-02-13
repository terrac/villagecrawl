package gwt.shared;

import gwt.client.game.AttachUtil;
import gwt.client.game.AttackEnemyMeta;
import gwt.client.game.MoveClosestNot;
import gwt.client.game.RandomTypeCreation;
import gwt.client.game.SellOne;
import gwt.client.game.display.ItemsDisplay;
import gwt.client.game.oobjects.BackgroundTrade;
import gwt.client.game.oobjects.Build;
import gwt.client.game.oobjects.BuildUnbuilt;
import gwt.client.game.oobjects.CheckStat;
import gwt.client.game.oobjects.Destroy;
import gwt.client.game.oobjects.FillNeed;
import gwt.client.game.oobjects.Haggle;
import gwt.client.game.oobjects.Healing;
import gwt.client.game.oobjects.HuntAnimal;
import gwt.client.game.oobjects.MoveStore;
import gwt.client.game.oobjects.MoveRandomGate;
import gwt.client.game.oobjects.ResetStat;
import gwt.client.game.oobjects.ResourceGather;
import gwt.client.game.oobjects.SpecialHaggle;
import gwt.client.game.oobjects.TradeItem;
import gwt.client.game.util.PointBase;
import gwt.client.game.vparams.AddNeed;
import gwt.client.game.vparams.BuildMap;
import gwt.client.game.vparams.ChooseFMD;
import gwt.client.game.vparams.CopySelection;
import gwt.client.game.vparams.Count;
import gwt.client.game.vparams.CreateAndDecay;
import gwt.client.game.vparams.Mercernaries;
import gwt.client.game.vparams.MovePerson;
import gwt.client.game.vparams.PersonTypeEffects;
import gwt.client.game.vparams.QuestGenerator;
import gwt.client.game.vparams.RandomEffects;
import gwt.client.game.vparams.SelectAndApply;
import gwt.client.game.vparams.SetTemplate;
import gwt.client.game.vparams.TradeCultureRoute;
import gwt.client.game.vparams.adding.AddGDP;
import gwt.client.game.vparams.adding.AddToMarket;
import gwt.client.game.vparams.oneoff.Caught;
import gwt.client.game.vparams.oneoff.Jerk;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.game.vparams.random.RandomItemCreation;
import gwt.client.game.vparams.rules.CreateItem;
import gwt.client.game.vparams.rules.GluttonyRule;
import gwt.client.game.vparams.rules.LaborRule;
import gwt.client.game.vparams.rules.ScarcityRule;
import gwt.client.game.vparams.rules.TradeValueRule;
import gwt.client.game.vparams.rules.TypeRule;
import gwt.client.game.vparams.turnbased.Taxation;
import gwt.client.item.Item;
import gwt.client.item.SimpleMD;
import gwt.client.main.ChildsPlay;
import gwt.client.main.Consume;
import gwt.client.main.Economy;
import gwt.client.main.Game;
import gwt.client.main.MakeItem;
import gwt.client.main.MapArea;
import gwt.client.main.Move;
import gwt.client.main.MoveClosest;
import gwt.client.main.MoveClosestDifferent;
import gwt.client.main.MoveRandomFullMapData;
import gwt.client.main.MoveRandomHashMapData;
import gwt.client.main.PTemplate;
import gwt.client.main.PickUp;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.Wait;
import gwt.client.main.WaitMove;
import gwt.client.main.base.OobList;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.shared.datamodel.VExecute;
import gwt.shared.datamodel.VParams;

import java.util.List;

public class ClientBuildDungeon extends ClientBuild2 {

	public static Game doBasicDungeon() {
		Game game = ClientBuild.defaultGameStuff();

		PTemplate explore = addTemplate(game, "explore");
		addAction(explore,
				addG("explore", game, new MoveRandomHashMapData("explore")));
		explore.setMetaoobj(new AttackEnemyMeta());
		String movetonearestitem = addG("movetonearestitem", game,
				new MoveClosest(VConstants.items, null, 3));

		String movetonearestenemy = addG("movenearestenemy", game,
				new MoveClosestDifferent(VConstants.livingbeing + "."
						+ VConstants.team, VConstants.team, true, 20));
		String wait = addG("wait1", game, new Wait(1));

		PTemplate attack = addTemplate(game, "attack");
		addAction(attack, movetonearestenemy);
		attack.setMetaoobj(new AttackEnemyMeta());

		PTemplate defend = addTemplate(game, "defend");
		addAction(defend, wait);
		defend.setMetaoobj(new AttackEnemyMeta());
		String pickitemsup = addG("pickitemsup", game, new PickUp());
		PTemplate gatheritems = addTemplate(game, "gatheritems");
		String gathitems = addG("gatheritems", game, new OobList(
				movetonearestitem, pickitemsup));
		addAction(gatheritems, gathitems);
		// gatheritems.setMetaoobj(new AttackEnemyMeta());

		// templates

		PTemplate animal = addTemplate(game, "hero");

		addAction(
				animal,
				addG("explore", game, new OobList(new VExecute(20,
						new MoveRandomHashMapData("explore"))
						)));
		animal.setMetaoobj(new AttackEnemyMeta());

		PTemplate straight = addTemplate(game, "straight");
		int exitx = 10;
		int exity = 10;
		addAction(straight, addG("straight", game, new Move(exitx, exity)));
		straight.setMetaoobj(new AttackEnemyMeta());

		PTemplate recruitable = addTemplate(game, "building");

		// addAction(recruitable, addG("recruitablemove", game, new Move(5,5)
		// ));

		// job has a value that gets set which buildunbuilt uses
		addAction(recruitable,
				addG("recruitablebuild", game, new BuildUnbuilt()));
		// addAction(recruitable, addG("recruitableget", game, new GetJob() ));
		recruitable.setMetaoobj(new AttackEnemyMeta());

		PTemplate farm = addTemplate(game, "resourcegather");

		addAction(farm,
				addG("movebackstore", game, new MoveStore(VConstants.home)));
		
		addAction(
				farm,
				addG("resourcegather", game,new ResourceGather()));



		{
			PTemplate template = addTemplate(game, "basiccreating");
			addAction(template,
					addG("movetobuilding", game, new MoveStore(VConstants.home)));
			addAction(template, addG("waitformake", game, new WaitMove(20)));
			// addAction(
			// template,
			// addG("movetoshop", game, new MoveClosest(VConstants.gate,
			// "itemshop")));

		}

		{
			PTemplate template = addTemplate(game, "trading");
			addAction(template, addG("waittrade", game, new Wait("trade",50)));
			addAction(
					template,
					addG("movetotrade", game, new MoveClosest(VConstants.gate,
							"itemshop")));

		}

		{
			PTemplate template = addTemplate(game, "singing");
			addAction(template, addG("singing", game, new Wait("singing", 5)));
			addAction(
					template,
					addG("movetosing", game, new MoveClosest(
							VConstants.obstacle, "campfire", "main")));

		}

		{
			PTemplate template = addTemplate(game, "dancing");
			addAction(template, addG("dancing", game, new Wait("dancing", 5)));
			addAction(
					template,
					addG("movetodance", game, new MoveClosest(
							VConstants.obstacle, "campfire", "main")));

		}

//		{
//			PTemplate template = addTemplate(game, "doctor");
//
//			MoveRandomGate movefarm = new MoveRandomGate("farm");
//			AttachUtil.attach(AttachUtil.runbefore, new AddGDP(1), movefarm);
//			movefarm.put(VConstants.oObject, new MakeItem("doctor bill"));
//			addAction(template, addG("movefarm", game, movefarm));
//			addAction(template, addG("heal", game, new Healing()));
//
//		}

		{
			PTemplate template = addTemplate(game, "mafioso");

			MoveRandomGate movefarm = new MoveRandomGate("farm");
			AttachUtil.attach(AttachUtil.runbefore, new AddGDP(-1), movefarm);
			AttachUtil.attach(AttachUtil.runbefore, new Caught(5), movefarm);
			addAction(template, addG("movefarmsteal", game, movefarm));

		}
		{
			PTemplate template = addTemplate(game, "farms");
			addAction(template,
					addG("farms", game,
							new OobList("build", 
									new MoveStore(VConstants.home),
									new Build(new SimpleMD(
							VConstants.gate, "farm"),2),
							new MoveStore(VConstants.home),
							new Count(3, new SetTemplate("gatherowneditem")
							)
							)
					)
					);



		}
		
		{
			PTemplate template = addTemplate(game, "city");
			addAction(template,
					addG("city", game,
							new OobList(null, 
									new MoveStore(VConstants.home),
									new Wait(300),
									new MakeItem()
									
									)
							)
					
					);



		}

		{
			PTemplate template = addTemplate(game, "jerk");

			addAction(template, addG("jerk", game, new Jerk()));

		}
		{
			PTemplate template = addTemplate(game, "gatherowneditem");
			addAction(template, addG("jerk", game, new CreateItem("farms")));

//			String gatherbread = addG("gatherowneditem", game, new OobList("food"
//					,0, movetonearestitem, pickitemsup, "movebackstore"));
//			addAction(template, gatherbread);
		}

		{
			PTemplate template = addTemplate(game, "guard");

			addAction(
					template,
					addG("guard2", game,
							new OobList("attack", new HuntAnimal())));

		}

		{
			PTemplate template = addTemplate(game, "tradingother");
			template.put(VConstants.sound, "trading");
			addAction(template, "exitmove");

			addAction(template, addG("haggleother", game, new Haggle()));
			addAction(
					template,
					addG("movetotrade", game, new MoveClosest(VConstants.gate,
							"itemshop")));

		}

		{
			PTemplate template = addTemplate(game, "haggle");
			template.put(VConstants.sound, "trading");
			addAction(template,addG("haggle",game,new OobList("trade",new MoveClosest(VConstants.gate,
					"itemshop"),new Haggle(),"exitmove","destroy1")));
			

		}

		{
			PTemplate template = addTemplate(game, "backgroundbuy");
			template.put(VConstants.sound, "trading");
			addAction(template,"destroy1");
			
			addAction(template, "exitmove");

			addAction(template, addG("tradeother", game, new BackgroundTrade(true)));
			addAction(
					template,
					addG("movetotrade", game, new MoveClosest(VConstants.gate,
							"itemshop")));

		}
		
		
		{
			PTemplate template = addTemplate(game, "specialhaggle");
			template.put(VConstants.sound, "trading");
			addAction(template, addG("destroy1", game, new Destroy()));
			
			addAction(template, "exitmove");

			addAction(template, addG("tradeother2", game, new SpecialHaggle()));
			addAction(template, "movetotrade");

		}

		{
			PTemplate template = addTemplate(game, "play");
			addAction(template, addG("childsplay", game, new ChildsPlay()));

		}

		{

			PTemplate template = addTemplate(game, "hunger");

			addAction(
					template,
					addG("hunger", game, new OobList("food", new Consume(),
							new TradeItem("food"), "farm")));

		}
		{
			PTemplate template = addTemplate(game, "trade");

			// addAction(template, addG("trade", game, new Trade() ));

		}
		
		{
			PTemplate template = addTemplate(game, "sellone");
			addAction(template, addG("sellone", game, new OobList("trade", new SellOne(),new MoveStore(VConstants.home)) ));
			
			 //template.put(VConstants.random, .5);
			 
			 
		}

		{
			PTemplate template = addTemplate(game, "fillneed");

			addAction(template, addG("fillneed", game, new FillNeed()));

		}
		{
			PTemplate template = addTemplate(game, "thirst");
			template.put(VConstants.sound, VConstants.none);

			addAction(
					template,
					addG("thirstmove", game, new OobList("water",
							new CheckStat(VConstants.thirst),
							new MoveClosest(VConstants.under, VConstants.water,
									"main"), new ResetStat(VConstants.thirst))));

		}

		{
			PTemplate template = addTemplate(game, "sleep");
			template.put(VConstants.sound, VConstants.none);

			/**
			 * should eventually claim a bed and then move to that bed
			 */
			addAction(
					template,
					addG("sleepl", game, new OobList("sleep", new MoveClosest(
							VConstants.gate, "bed", "house"), new Wait(30))));

		}

		{
			PTemplate template = addTemplate(game, "movetoexit");
			template.setMetaoobj(new AttackEnemyMeta());
			addAction(
					template,
					addG("exitmove", game, new OobList(null,
							new MoveStore(VConstants.exit)
					// ,new Destroy()
							)));

		}
		// move onto other fmd
		// explore in a straight line of 10
		// grab any nearby items
		// if health is less than a certain amount then move to other fmd
		String raid = addG("raid", game, new OobList());

		PTemplate traid = addTemplate(game, "raid");
		addAction(traid, raid);
		traid.setMetaoobj(new AttackEnemyMeta());

		PBase person = getType(game, VConstants.person);

		PBase stored = getType(person, VConstants.templatemap).getType(
				VConstants.stored);

		{
			List<String> farmList = game.getType(VConstants.resource)
					.getListCreate("tree");
			farmList.add("acorns");
			farmList.add("apple");
			farmList.add("honeycomb");
			for (String name : farmList) {
				addFoodItem(game, name);
			}
		}

		{
			List<String> farmList = game.getType(VConstants.resource)
					.getListCreate("bush");
			farmList.add("apricot");
			farmList.add("honeycomb");
			for (String name : farmList) {
				addFoodItem(game, name);
			}
			farmList.add("snake");

		}

		{
			List<String> farmList = game.getType(VConstants.resource)
					.getListCreate("grass");
			farmList.add("yak");
			farmList.add("elephant");
		}

		{
			List<String> farmList = game.getType(VConstants.resource)
					.getListCreate("wheatfield");
			farmList.add("wheat");
		}

		// {
		// List<String> farmList = game.getType(VConstants.resource)
		// .getListCreate("rock");
		// farmList.add("rock");
		// farmList.add("iron");
		// }

		game.put(VConstants.randomitem, new RandomItemCreation(1,
				"leather armor"));

		List<String> mineList = game.getType(VConstants.resource)
				.getListCreate("rock");
		// mineList.add("clay");
		// mineList.add("gold");
		mineList.add("iron");
		mineList.add("copper");

		addItem(game, "meat", VConstants.food, 10);
		addItem(game, "bread", VConstants.food, 5);
		addItem(game, "cheese", VConstants.food, 7);

		// addItem(game, "gold",VConstants.basic);
		addItem(game, "iron", VConstants.money, 2).put(VConstants.money, true);
		addItem(game, "wood", VConstants.basic);
		addItem(game, "wheat", VConstants.basic);
		addItem(game, "copper", VConstants.money, 1)
		.put(VConstants.money, true);
		addItem(game, VConstants.debt, VConstants.money, 1)
		.put(VConstants.money, true);

		addItem(game, "gold", VConstants.money, 3).put(VConstants.money, true);

		addItem(game, "animal skin", VConstants.body);
		addItem(game, "rags", VConstants.body);
		addItem(game, "leather armor", VConstants.body, 11);
		addItem(game, "black coat", VConstants.body);
		addItem(game, "robe", VConstants.body);
		addItem(game, "green breastplate", VConstants.body, 11);
		addItem(game, "dress", VConstants.body, 11);

		addItem(game, "cloak", VConstants.cloak);
		addItem(game, "leather cloak", VConstants.cloak);
		// addItem(game, "magenta cloak", VConstants.cloak);
		addItem(game, "ratskin cloak", VConstants.cloak);

		addItem(game, "black gloves", VConstants.gloves, 7);
		// addItem(game, "short green gloves", VConstants.gloves);
		addItem(game, "orange gloves", VConstants.gloves);

		addItem(game, "short black beard", VConstants.face);
		addItem(game, "scarf", VConstants.face);

		addItem(game, "black hat", VConstants.head);
		addItem(game, "wizard hat", VConstants.head);

		addItem(game, "arwen hair", VConstants.hair);
		addItem(game, "brown hair", VConstants.hair);

		addItem(game, "pants", VConstants.leg, 8);
		addItem(game, "dark green pants", VConstants.leg);

		addItem(game, "scimitar", VConstants.weapon);
		addItem(game, "mace", VConstants.weapon);
		addItem(game, "sword", VConstants.weapon);
		addItem(game, "hammer", VConstants.weapon);
		addItem(game, "enchantress dagger", VConstants.weapon);
		addItem(game, "dagger", VConstants.weapon);

		addItem(game, "leather armor",
				"kobold leather armor of xxxxx (Replica)", VConstants.body, 50,
				VConstants.rare,
				" story about a woman warrior who joined the army and was a twin princess");
		addItem(game,
				"sword",
				"human pot of yyyy (Replica)",
				VConstants.weapon,
				50,
				VConstants.rare,
				" story about a woman who went to the capital, and first was a cook, "
						+ "went through some troubles, and then became a doctor");
		addItem(game,
				"sword",
				"Helenic ladel",
				VConstants.weapon,
				20,
				"this province is known for its hardworking and precision filled people",
				VConstants.normal);
		addItem(game,
				"dress",
				"southern province cloth dress",
				VConstants.body,
				15,
				"this province is known for its 'indentured servant' produced clothing",
				VConstants.normal);
		addItem(game,
				"green book",
				"aunt edna's bungalow",
				VConstants.book,
				13,
				"a story about the indentured servants in the southern province they are looked down upon"
						+ "and this is an abolition movement book");
		addItem(game,
				"brown book",
				"taming the shrew",
				VConstants.book,
				9,
				"a human book about taming shrews in order to have a steady source of meat and eggs");
		addItem(game,
				"white book",
				"the story of athena",
				VConstants.book,
				10,
				"a book about a famous princess who was wise, liked to hunt, and developed a new type of bow");
		addItem(game, "persian tea", VConstants.food, 10,
				"Coming from a land of blah blah blah");
		addItem(game, "merfolk pottery", VConstants.decorative, 30,
				"considered the best and most decorative pottery");
		addItem(game,
				"blue book",
				"merfolk book 'kitsune'",
				VConstants.book,
				7,
				"(a story about a fox that turns into a woman and marries a man and"
						+ "who goes through several trials and tribulations dealing with the human world(foxes are shapeshifters))");
		addItem(game, "cheese", "Barbary wine and cheese", VConstants.food, 20,
				"This province is famed for both and it has a culture of romantic behavior");

		addItem(game,
				"blue book",
				"Brittiana boat making book",
				VConstants.book,
				25,
				"It doesn't reveal any real secrets, but is still prized as the authority on boat making");

		addItem(game,
				"white book",
				"A treatise on the picture book market of the merfolk region.",
				VConstants.book,
				5,
				"Basically stating how it is very stratified"
						+ "and much larger than most other markets with specific recommendations on how to reproduce the same thing"
						+ "for region vespucan");

		Items tItems = new Items();
		tItems.add(game.getItem("meat"));
		PBase treas = new SimpleMD();
		treas.put(tItems.getKey(), tItems);
		Item gold = game.getItem(VConstants.gold).clone();
		gold.setAmount(20);
		tItems.put(gold);

		game.put(VConstants.market, treas);
		
		game.getHtmlOut().put(VConstants.score, new ItemsDisplay("Gilgamesh"));
		return game;
	}

	private static Item addItem(Game game, String name, String type, int value) {
		return addItemI(game, name, VConstants.type, type, VConstants.value,
				value);
	}

	private static void addItem(Game game, String name, String type, int value,
			String description) {
		addItemI(game, name, VConstants.type, type, VConstants.value, value,
				VConstants.description, description);
	}

	private static void addItem(Game game, String basictype, String name,
			String type, int value, String description, String rarity) {
		addItemI(game, name, VConstants.basictype, basictype, VConstants.type,
				type, VConstants.value, value, VConstants.description,
				description, VConstants.rare, rarity);
	}

	private static void addItem(Game game, String basictype, String name,
			String type, int value, String description) {
		addItemI(game, name, VConstants.basictype, basictype, VConstants.type,
				type, VConstants.value, value, VConstants.description,
				description, VConstants.rare, VConstants.normal);
	}

	private static Item addItemI(Game game, String name, Object... objs) {
		Item item = new Item();
		item.setName(name);
		// item.setConsumable(consumable);

		PBase.setPBase(item, objs);
		// game.getPBase(VConstants.item).put(item.getKey(), item);
		addG(item.getKey(), game, item);
		game.getType(VConstants.randomitem).getListCreate(item.getType())
				.add(name);
		return item;
	}

	private static void addItem(Game game, String name, String type) {
		addItem(game, name, type, 7);
	}

	private static void addFoodItem(PBase game, String name) {
		addItem(game, name, true).put(VConstants.food, true);
	}

	public static PBase doInitialAdventurers() {

		// beef up the mage with all kinds of summoning and direct damage
		// abilities

		PBase pb = new PBase();

		MapArea maparea = new MapArea(3, 4);

		pb.put(VConstants.maparea, maparea);

		ClientBuildCultures.doCulture(pb);
		pb.put(VConstants.startmessage,
				"Welcome to the game Villagecrawl. Make choices and place buildings");
		questGenerator(pb, maparea);

		maparea.getMap().put(VConstants.xfull, 1);
		maparea.getMap().put(VConstants.yfull, 1);
		// AttachUtil.attach(AttachUtil.runbefore, new CreateAndDecay(),
		// maparea);
		FullMapData value = new FullMapData(1, 1);
		value.getData(0, 0).put(new SimpleMD(VConstants.obstacle, "wall"));
		maparea.getMap().put(VConstants.template, value);

		pb.put(VConstants.classname, Game.class.getName());
		pb.put(VConstants.name, "initialadventurers");

		ClientBuild2.getType(pb, VConstants.templatemap).put(VConstants.type,
				"default");
		// PBase charmap = new PBase();
		// {
		// PBase person = new PBase();
		// //charmap.put("W", person);
		// person.put(VConstants.traits, "human female wizard");
		// person.put(VConstants.type, VConstants.livingbeing);
		// person.put(VConstants.name, "Terra");
		// person.put(VConstants.team, "1");
		// person.getListCreate(VConstants.equipment).add("wizard hat");
		// person.put(VConstants.image, "/images/terra.png");
		// person.put(VConstants.intelligence, "defensive");
		// person.put(VConstants.templatemap, new PBase());
		// }
		//
		// {
		// PBase person = new PBase();
		// //charmap.put("D", person);
		// person.put(VConstants.traits, "human female druid");
		// person.put(VConstants.type, VConstants.livingbeing);
		// person.put(VConstants.name, "Mariangela");
		// person.put(VConstants.team, "1");
		// person.getListCreate(VConstants.equipment).add("scimitar");
		//
		// person.put(VConstants.intelligence, "defensive");
		// person.put(VConstants.templatemap, new PBase());
		// }
		//
		// {
		// PBase person = new PBase();
		// //charmap.put("H", person);
		// person.put(VConstants.traits, "human female healer");
		// person.put(VConstants.type, VConstants.livingbeing);
		// person.put(VConstants.name, "Molly Malone");
		// person.put(VConstants.team, "1");
		// person.getListCreate(VConstants.equipment).add("mace");
		//
		// person.put(VConstants.intelligence, "defensive");
		// person.put(VConstants.templatemap, new PBase());
		// }
		// charmap.put("U", "stairs");
		//
		//
		// charmap.put("T", addTradeArea());
		// // charmap.put("g", addHouse(bagMap));
		// charmap.put("G", new SimpleMD(VConstants.under, "grass"));
		// charmap.put("L", new SimpleMD(VConstants.gate, "lavatory"));
		// charmap.put("t", new SimpleMD(VConstants.under, "water"));
		// charmap.put("d", "deep water");
		// charmap.put("w", "wall");
		//
		// SimpleMD data = new SimpleMD(VConstants.obstacle, "campfire");
		//
		//
		// charmap.put("C", data);
		// charmap.put("R", new SimpleMD(VConstants.gate, "market"));
		// // image (make it 50% alpha of a normal gate) (map position is chosen
		// by
		// // choosefmd)
		// SimpleMD pg = new SimpleMD(VConstants.gate, "potential gate");
		// pg.put(VConstants.requirement, VConstants.gate);
		// charmap.put("1", pg);
		//
		// BuildMap bm = new BuildMap(charmap, "U    1   1w\\n" +
		// "dt   W    w\\n"
		// + "dtt DH C  w\\n" + "dt        w\\n" + "dtt       w\\n"
		// + "dtR      Tw\\n" + "dt        w\\n" + "dtt       w\\n"
		// + "dt   Z   3w\\n" + "wwwwwwwwwww\\n", "main");
		// bm.getType(VConstants.resource).put(VConstants.image,
		// "/images/terra.png");
		// // bm.put(VConstants.defaultimage, "/images/grass.png");
		//
		// // add bagmap to drop stuff
		//
		// AttachUtil.attach(AttachUtil.mapstart, new RandomTypeCreation(
		// new SimpleMD(VConstants.gate, "tree"), 10), maparea);

		AttachUtil.attach(AttachUtil.mapstart, addTradeArea(), maparea);

		// make the bagmap
		// set the selection to game.bagselection
		//

		// {
		// TradeCultureRoute tcr = new TradeCultureRoute(1, 1, 9, 0, 3, 9, 10,
		// "kobold");
		//
		// AttachUtil.attach(AttachUtil.runbefore, tcr, maparea);
		// }

		Economy economy = new Economy();
		pb.put(VConstants.economy, economy);
		Point pos = null;
		// economy.addNeed("building", 0, 8);
		// economy.addNeed("building", 0, 8);
		// economy.addNeed("building", 0, 8);

		AttachUtil.attach(AttachUtil.runbefore,
				new ComplexCityGenerator("firstmap", new PBase(
						VConstants.action, "farms", VConstants.type, "bakery"),
						new PBase(VConstants.action, "doctor", VConstants.type,
								"doctor"),

						new PBase(VConstants.action, "guard", VConstants.type,
								"guard"), new PBase(VConstants.action, "guard",
								VConstants.type, "gilgamesh"),

						new PBase(VConstants.action, "farms", VConstants.type,
								"blacksmith"), new PBase(VConstants.action,
								"farms", VConstants.type, "tannery"),
						new PBase(VConstants.action, "farms", VConstants.type,
								"goldsmith"), new PBase(VConstants.action,
								"farms", VConstants.type, "beermaker"),
						new PBase(VConstants.action, "farms", VConstants.type,
								"barn"), new PBase(VConstants.action, "farms",
								VConstants.type, "papermaker")), pb
						.getPBase(VConstants.maparea));

		TradeCultureRoute tcr = new TradeCultureRoute(1, 1, 80, 
				new PBase(VConstants.start, "dwarfentrance", VConstants.exit,
				"merfolkentrance", VConstants.race, "dwarf",VConstants.economy,
				
				new Economy(new TypeRule("meat",3,"weapon",3,"armor",3,
						                 "cheese",2,"beer",2,
						                 "wheat",1,"milk",1),new GluttonyRule(1,1,100))),
				
				new PBase(VConstants.start, "merfolkentrance", VConstants.exit,
				"dwarfentrance", VConstants.race, "merfolk",VConstants.disabled,true),
				new PBase(VConstants.start, "orcentrance", VConstants.exit,
				"orcentrance", VConstants.race, "orc",VConstants.disabled,true));

		AttachUtil.attach(AttachUtil.runbefore, "tcr",
				pb.getPBase(VConstants.maparea));
		pb.getType(VConstants.vparams).put("tcr", tcr);

		pb.put(VConstants.randomeffect, new RandomEffects());

		AttachUtil.attach(AttachUtil.runbefore, new Taxation(),
				pb.getPBase(VConstants.maparea));
		AttachUtil.attach(AttachUtil.runbefore, new PersonTypeEffects(),
				pb.getPBase(VConstants.maparea));
		AttachUtil.attach(AttachUtil.runbefore, new Mercernaries(),
				pb.getPBase(VConstants.maparea));
		return pb;
	}

	public static void questGenerator(PBase pb, MapArea maparea) {
		QuestGenerator questGen = new QuestGenerator();
		pb.put(VConstants.quest, questGen);
		// AttachUtil.attach(AttachUtil.runbefore, questGen, maparea);

		// VParams firstquest= new VParams(
		// new DrawFocus("bakery",200,500),
		// new AddBuilding("bakery",0,0,new AddNeed("makebread",0,8),new
		// Item("bread",1,5)),
		// new DisplayResult(
		// new PBase(
		// VConstants.message,
		// "I am some grizzled old etc etc who wants a bakery for some reason "
		// +
		// "to be filled in later.  Storyline stuff." +
		// "Place the bakery by touching the house with bread and then touching the"
		// +
		// "main area.  Click this to continue",VConstants.image,"bakery")));
		//
		// MapKeyValue end = new MapKeyValue(VConstants.gate, "bakery",
		// null,"Success at your first quest!");
		// firstquest.put(VConstants.end, end);
		//
		// {
		// TradeRoute monsterentrance = new TradeRoute(2, 1, 9, 0, 0,
		// "leather armor", "black gloves", "pants");
		// monsterentrance.getListCreate(VConstants.list).add("kobold");
		// VParams trade =new VParams(
		// new SetCurrentFMD(2, 1),
		// monsterentrance,
		// new DisplayResult(
		// new PBase(
		// VConstants.message,
		// "Grizzled old person returns!  This place is a trading outpost to trade our food for the kobold's leather armor."
		// +
		// "A kobold is going to approach you soon.  Make sure to get some of that leather armor")));
		//
		// firstquest.put(VConstants.nextquest, trade);
		//
		// }
		// questGen.getListCreate(VConstants.activelist).add(firstquest);
		//
		//
		// questGen.addSubQuest(new MarketSubQuest());
	}

	private static SimpleMD addNeed(String building, String need, int minstate,
			int maxstate) {
		SimpleMD data = new SimpleMD(VConstants.gate, building);
		AttachUtil.attach(AttachUtil.placed, new AddNeed(need, minstate,
				maxstate), data);
		return data;
	}

	private static Object addTradeArea() {
		SimpleMD guardHouse = new SimpleMD(VConstants.gate, "stairs");
		guardHouse.put(VConstants.requirement, VConstants.gate);

		PBase charmap = new PBase();

		charmap.put("D", new SimpleMD(VConstants.gate, "dwarfentrance",
				"/images/grass.png"));
		charmap.put("M", new SimpleMD(VConstants.gate, "merfolkentrance",
				"/images/grass.png"));
		charmap.put("O", new SimpleMD(VConstants.gate, "orcentrance",
				"/images/grass.png"));

		charmap.put("m", new SimpleMD(VConstants.obstacle, "mountain"));
		charmap.put("r", new SimpleMD(VConstants.gate, "rock"));
		charmap.put("t", new SimpleMD(VConstants.gate, "tree"));
		charmap.put("w", new SimpleMD(VConstants.obstacle, "water"));
		charmap.put("S", addNeed("itemshop", "trading", 0, 8));
		charmap.put("E", new SimpleMD(VConstants.gate, "stairs"));
		// charmap.put("X", new SimpleMD(VConstants.gate, "teleporter",
		// new MovePerson(true)));

		MapData md = new SimpleMD(VConstants.gate, "goblinentrance");
		md.put(AttachUtil.personadded, new MovePerson());
		md.put(VConstants.portal, new PointBase(1, 1));

		charmap.put("g", md);

		{
			PBase person = new PBase();
			charmap.put("F", person);
			person.put(VConstants.traits, "giant male fighter");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Gilgamesh");
			person.put(VConstants.team, "1");
			person.put(VConstants.templatemap, new PBase(VConstants.main,
					"trading"));
			person.put(VConstants.image, "/images/titan.png");
			person.put(VConstants.dontdrawequipment, true);
			person.put(VConstants.owned, "itemshop");
			Items its = new Items();
			its.add(new Item("cheese", 30, 5));
			its.add(new Item("copper", 200, 1));
			its.getItem(VConstants.copper).put(VConstants.money, true);
			person.put(VConstants.items, its);
			person.put(VConstants.economy,	new Economy(new TradeValueRule(),new ScarcityRule(), new LaborRule()));
		}
		// do a simple start on the buildmap to show the basics for goblins
		BuildMap bm1 = new BuildMap(charmap, "wwwwF        mmm\\n"
				+ "ww         tr m \\n" + "M           mm  \\n"
				+ "w    S      X  D\\n" + "ww             m\\n"
				+ "w        m    mm\\n" + "w       mmmr    \\n"
				+ "         m    m \\n" + "t    m     mm   \\n"
				+ "ttmmmmmO  mm mm ", "tradearea");

		BuildMap bm2 = new BuildMap(charmap, "g E Fttwwttt     \\n"
				+ " t ttttwwtt     \\n" + "    ttttwwtt    \\n"
				+ "T    ttwwt  rt  \\n" + "  S ttwwttttttt \\n"
				+ "   ttwwttttttt  \\n" + "    ttwwtt tt   \\n"
				+ "    ttwwwttttt  \\n" + "     twwtttt    \\n"
				+ "  X twwtttt     ", "tradearea");

		BuildMap bm3 = new BuildMap(charmap, "tttEttttttttt  t\\n"
				+ "tttttttt tttt tt\\n" + "tttttwwttttttt  \\n"
				+ "tttttwwwttttFttt\\n" + "ttttwwSwwttttttt\\n"
				+ "tttttwwwwwtttttt\\n" + "tttttttwwwtttrtt\\n"
				+ "tttt tttwttttttt\\n" + " ttttttttttttttt\\n"
				+ "   tttttttt tttt\\n" + "   tttttttttXttt", "tradearea");

		PBase resource = new PBase();
		// SelectAndApply saa =new SelectAndApply(resource,bm2,bm3);
		SelectAndApply saa = new SelectAndApply(bm1);
		saa.put(VConstants.resource, resource);
		AttachUtil.attach(AttachUtil.placed, new ChooseFMD(resource),
				guardHouse);

		resource.getType(VConstants.resource)
				.getListCreate(VConstants.leftclick)
				.add(new CopySelection("bagselection"));

		resource.put(VConstants.defaultimage, "/images/grass.png");

		resource.getType(VConstants.resource).put(VConstants.image,
				"/images/itemshop.png");
		resource.getType(VConstants.resource).put(VConstants.sound,
				"tradingmusic");
		return saa;
	}

}
