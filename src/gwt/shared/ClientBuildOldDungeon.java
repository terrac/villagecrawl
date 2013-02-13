//package gwt.shared;
//
//import gwt.client.edit.BagMap;
//import gwt.client.game.AttachUtil;
//import gwt.client.game.AttackEnemyMeta;
//import gwt.client.game.RandomTypeCreation;
//import gwt.client.game.oobjects.BuildUnbuilt;
//import gwt.client.game.oobjects.CheckStat;
//import gwt.client.game.oobjects.Destroy;
//import gwt.client.game.oobjects.HuntAnimal;
//import gwt.client.game.oobjects.ResetStat;
//import gwt.client.game.oobjects.ResourceGather;
//import gwt.client.game.oobjects.TradeItem;
//import gwt.client.game.oobjects.TradeRandom;
//import gwt.client.game.util.PointBase;
//import gwt.client.game.vparams.AddNeed;
//import gwt.client.game.vparams.BuildMap;
//import gwt.client.game.vparams.ChooseFMD;
//import gwt.client.game.vparams.CopySelection;
//import gwt.client.game.vparams.CreateAndDecay;
//import gwt.client.game.vparams.DisplayChoices;
//import gwt.client.game.vparams.QuestGenerator;
//import gwt.client.game.vparams.TradeRoute;
//import gwt.client.game.vparams.MovePerson;
//import gwt.client.game.vparams.SetCurrentFMD;
//import gwt.client.game.vparams.adding.AddBuilding;
//import gwt.client.game.vparams.adding.AddScore;
//import gwt.client.game.vparams.adding.AddToMarket;
//import gwt.client.game.vparams.adding.SendMoney;
//import gwt.client.game.vparams.adding.MarketContains;
//import gwt.client.game.vparams.quest.MarketSubQuest;
//import gwt.client.game.vparams.random.LeaveStayOrDie;
//import gwt.client.game.vparams.random.RandomItemCreation;
//import gwt.client.game.vparams.random.RandomPersonCreation;
//import gwt.client.game.vparams.requirements.DoOnce;
//import gwt.client.game.vparams.requirements.DoTurn;
//import gwt.client.game.vparams.requirements.MapKeyValue;
//import gwt.client.game.vparams.requirements.OutsideTheTown;
//import gwt.client.game.vparams.requirements.PersonKeyValue;
//import gwt.client.game.vparams.requirements.RecentlyDone;
//import gwt.client.game.vparams.ui.DisplayResult;
//import gwt.client.game.vparams.ui.DrawFocus;
//import gwt.client.item.Item;
//import gwt.client.item.SimpleMD;
//import gwt.client.main.ChildsPlay;
//import gwt.client.main.Consume;
//import gwt.client.main.Economy;
//import gwt.client.main.Game;
//import gwt.client.main.MakeItem;
//import gwt.client.main.MapArea;
//import gwt.client.main.Move;
//import gwt.client.main.MoveClosest;
//import gwt.client.main.MoveClosestDifferent;
//import gwt.client.main.MoveRandomFullMapData;
//import gwt.client.main.MoveRandomHashMapData;
//import gwt.client.main.PTemplate;
//import gwt.client.main.PickUp;
//import gwt.client.main.Point;
//import gwt.client.main.VConstants;
//import gwt.client.main.Wait;
//import gwt.client.main.base.OobList;
//import gwt.client.main.base.PBase;
//import gwt.client.map.FullMapData;
//import gwt.client.map.Items;
//import gwt.client.map.MapData;
//import gwt.shared.datamodel.VExecute;
//import gwt.shared.datamodel.VParams;
//
//import java.util.List;
//
//public class ClientBuildOldDungeon extends ClientBuild2 {
//
//	public static Game doBasicDungeon() {
//		Game game = ClientBuild.defaultGameStuff();
//
//		PTemplate explore = addTemplate(game, "explore");
//		addAction(explore,
//				addG("explore", game, new MoveRandomHashMapData("explore")));
//		explore.setMetaoobj(new AttackEnemyMeta());
//		String movetonearestitem = addG("movetonearestitem", game,
//				new MoveClosest(VConstants.items, null, 20));
//		String movetonearestenemy = addG("movenearestenemy", game,
//				new MoveClosestDifferent(VConstants.livingbeing + "."
//						+ VConstants.team, VConstants.team, true, 20));
//		String wait = addG("wait1", game, new Wait(1));
//
//		PTemplate attack = addTemplate(game, "attack");
//		addAction(attack, movetonearestenemy);
//		attack.setMetaoobj(new AttackEnemyMeta());
//
//		PTemplate defend = addTemplate(game, "defend");
//		addAction(defend, wait);
//		defend.setMetaoobj(new AttackEnemyMeta());
//		String pickitemsup = addG("pickitemsup", game, new PickUp());
//		PTemplate gatheritems = addTemplate(game, "gatheritems");
//		String gathitems = addG("gatheritems", game, new OobList(
//				movetonearestitem, pickitemsup));
//		addAction(gatheritems, gathitems);
//		gatheritems.setMetaoobj(new AttackEnemyMeta());
//
//		// templates
//
//		PTemplate animal = addTemplate(game, "hero");
//
//		addAction(
//				animal,
//				addG("explore", game, new OobList(new VExecute(20,
//						new MoveRandomHashMapData("explore")),
//						new MoveRandomFullMapData("explore fmd"))));
//		animal.setMetaoobj(new AttackEnemyMeta());
//
//		PTemplate straight = addTemplate(game, "straight");
//		int exitx = 10;
//		int exity = 10;
//		addAction(straight, addG("straight", game, new Move(exitx, exity)));
//		straight.setMetaoobj(new AttackEnemyMeta());
//
//		PTemplate recruitable = addTemplate(game, "building");
//
//		// addAction(recruitable, addG("recruitablemove", game, new Move(5,5)
//		// ));
//
//		// job has a value that gets set which buildunbuilt uses
//		addAction(recruitable,
//				addG("recruitablebuild", game, new BuildUnbuilt()));
//		// addAction(recruitable, addG("recruitableget", game, new GetJob() ));
//		recruitable.setMetaoobj(new AttackEnemyMeta());
//
//		PTemplate farm = addTemplate(game, "farming");
//
//		addAction(
//				farm,
//				addG("farm", game, new OobList(new ResourceGather(),
//						new HuntAnimal())));
//
////		{
////			PTemplate template = addTemplate(game, "sewing");
////			addAction(template, addG("sew", game, new MakeItem("robe")));
////
////			addAction(template, addG("waitforsew", game, new Wait(20)));
////			addAction(
////					template,
////					addG("movetosew", game, new MoveClosest(VConstants.gate,
////							"sewingmachine", "house")));
////
////		}
//
//		{
//			PTemplate template = addTemplate(game, " `");
//			addAction(template, addG("addmarketbread", game, new AddToMarket()));
//
//			//addAction(template, addG("bake", game, new MakeItem("bread")));
//
//			addAction(template, addG("waitforbake", game, new Wait(20)));
//			addAction(
//					template,
//					addG("movetobakery", game, new MoveClosest(VConstants.gate,
//							"bakery", "bakery")));
//
//		}
//		
//		{
//			PTemplate template = addTemplate(game, "trading");
//			addAction(template, addG("waittrade", game, new Wait(50)));
//			addAction(
//					template,
//					addG("movetotrade", game, new MoveClosest(VConstants.gate,
//							"itemshop", "tradearea")));
//
//		}
//
//		{
//			PTemplate template = addTemplate(game, "singing");
//			addAction(template, addG("singing", game, new Wait("singing", 5)));
//			addAction(
//					template,
//					addG("movetosing", game, new MoveClosest(
//							VConstants.obstacle, "campfire", "main")));
//
//		}
//
//		{
//			PTemplate template = addTemplate(game, "dancing");
//			addAction(template, addG("dancing", game, new Wait("dancing", 5)));
//			addAction(
//					template,
//					addG("movetodance", game, new MoveClosest(
//							VConstants.obstacle, "campfire", "main")));
//
//		}
//
//		{
//			PTemplate template = addTemplate(game, "tradingother");
//			template.put(VConstants.sound, "trading");
//			addAction(template, "exitmove");
//
//			addAction(template, addG("tradeother", game, new TradeRandom()));
//			addAction(
//					template,
//					addG("movetotrade", game, new MoveClosest(VConstants.gate,
//							"itemshop", "tradearea")));
//
//		}
//
//		{
//			PTemplate template = addTemplate(game, "play");
//			addAction(template, addG("childsplay", game, new ChildsPlay()));
//
//		}
//		{
//			PTemplate template = addTemplate(game, "guarding");
//
//			addAction(
//					template,
//					addG("guard", game, new OobList("attack",
//							new MoveRandomFullMapData("guarding"))));
//
//		}
//
//		{
//
//			PTemplate template = addTemplate(game, "hunger");
//
//			addAction(
//					template,
//					addG("hunger", game, new OobList("food", new Consume(),
//							new TradeItem("food"), "farm")));
//
//		}
//		{
//			PTemplate template = addTemplate(game, "trade");
//
//			// addAction(template, addG("trade", game, new Trade() ));
//
//		}
//		{
//			PTemplate template = addTemplate(game, "thirst");
//			template.put(VConstants.sound, VConstants.none);
//
//			addAction(
//					template,
//					addG("thirstmove", game, new OobList("water",
//							new CheckStat(VConstants.thirst),
//							new MoveClosest(VConstants.under, VConstants.water,
//									"main"), new ResetStat(VConstants.thirst))));
//
//		}
//
//		{
//			PTemplate template = addTemplate(game, "sleep");
//			template.put(VConstants.sound, VConstants.none);
//
//			/**
//			 * should eventually claim a bed and then move to that bed
//			 */
//			addAction(
//					template,
//					addG("sleepl", game, new OobList("sleep", new MoveClosest(
//							VConstants.gate, "bed", "house"), new Wait(30))));
//
//		}
//
//		{
//			PTemplate template = addTemplate(game, "movetoexit");
//			template.setMetaoobj(new AttackEnemyMeta());
//			addAction(
//					template,
//					addG("exitmove", game, new OobList(null, new MoveClosest(
//							VConstants.gate, "teleporter")
//					// ,new Destroy()
//							)));
//
//		}
//		// move onto other fmd
//		// explore in a straight line of 10
//		// grab any nearby items
//		// if health is less than a certain amount then move to other fmd
//		String raid = addG("raid", game, new OobList());
//
//		PTemplate traid = addTemplate(game, "raid");
//		addAction(traid, raid);
//		traid.setMetaoobj(new AttackEnemyMeta());
//
//		PBase person = getType(game, VConstants.person);
//
//		PBase stored = getType(person, VConstants.templatemap).getType(
//				VConstants.stored);
//
//		{
//			List<String> farmList = game.getType(VConstants.resource)
//					.getListCreate("tree");
//			farmList.add("acorns");
//			farmList.add("apple");
//			farmList.add("honeycomb");
//			for (String name : farmList) {
//				addFoodItem(game, name);
//			}
//		}
//
//		{
//			List<String> farmList = game.getType(VConstants.resource)
//					.getListCreate("bush");
//			farmList.add("apricot");
//			farmList.add("honeycomb");
//			for (String name : farmList) {
//				addFoodItem(game, name);
//			}
//			farmList.add("snake");
//
//		}
//
//		{
//			List<String> farmList = game.getType(VConstants.resource)
//					.getListCreate("grass");
//			farmList.add("yak");
//			farmList.add("elephant");
//		}
//
//		{
//			List<String> farmList = game.getType(VConstants.resource)
//					.getListCreate("wheatfield");
//			farmList.add("wheat");
//		}
//
//		// {
//		// List<String> farmList = game.getType(VConstants.resource)
//		// .getListCreate("rock");
//		// farmList.add("rock");
//		// farmList.add("iron");
//		// }
//
//		game.put(VConstants.randomitem, new RandomItemCreation(1,
//				"leather armor"));
//
//		List<String> mineList = game.getType(VConstants.resource)
//				.getListCreate("rock");
//		// mineList.add("clay");
//		// mineList.add("gold");
//		mineList.add("iron");
//		mineList.add("copper");
//
//		addItem(game, "meat", VConstants.food, 10);
//		addItem(game, "bread", VConstants.food, 5);
//		addItem(game, "cheese", VConstants.food, 7);
//
//		// addItem(game, "gold",VConstants.basic);
//		addItem(game, "iron", VConstants.money, 2).put(VConstants.money, true);
//		addItem(game, "wood", VConstants.basic);
//		addItem(game, "wheat", VConstants.basic);
//		addItem(game, "copper", VConstants.money, 1)
//				.put(VConstants.money, true);
//		addItem(game, "gold", VConstants.money, 3).put(VConstants.money, true);
//
//		addItem(game, "animal skin", VConstants.body);
//		addItem(game, "leather armor", VConstants.body, 11);
//		// addItem(game, "black coat",VConstants.body);
//		addItem(game, "robe", VConstants.body);
//
//		addItem(game, "cloak", VConstants.cloak);
//		addItem(game, "leather cloak", VConstants.cloak);
//		// addItem(game, "magenta cloak", VConstants.cloak);
//		addItem(game, "ratskin cloak", VConstants.cloak);
//
//		addItem(game, "black gloves", VConstants.gloves, 7);
//		// addItem(game, "short green gloves", VConstants.gloves);
//		addItem(game, "orange gloves", VConstants.gloves);
//
//		addItem(game, "short black beard", VConstants.face);
//		addItem(game, "scarf", VConstants.face);
//
//		addItem(game, "black hat", VConstants.head);
//		addItem(game, "wizard hat", VConstants.head);
//
//		addItem(game, "arwen hair", VConstants.hair);
//		addItem(game, "brown hair", VConstants.hair);
//
//		addItem(game, "pants", VConstants.leg, 8);
//		addItem(game, "dark green pants", VConstants.leg);
//
//		addItem(game, "scimitar", VConstants.weapon);
//		addItem(game, "mace", VConstants.weapon);
//		addItem(game, "sword", VConstants.weapon);
//
//		addItem(game, "leather armor",
//				"kobold leather armor of xxxxx (Replica)", VConstants.body, 50,
//				VConstants.rare,
//				" story about a woman warrior who joined the army and was a twin princess");
//		addItem(game,
//				"sword",
//				"human pot of yyyy (Replica)",
//				VConstants.weapon,
//				50,
//				VConstants.rare,
//				" story about a woman who went to the capital, and first was a cook, "
//						+ "went through some troubles, and then became a doctor");
//		addItem(game,
//				"sword",
//				"Helenic ladel",
//				VConstants.weapon,
//				20,
//				"this province is known for its hardworking and precision filled people",
//				VConstants.normal);
//		addItem(game,
//				"dress",
//				"southern province cloth dress",
//				VConstants.body,
//				15,
//				"this province is known for its 'indentured servant' produced clothing",
//				VConstants.normal);
//		addItem(game,
//				"green book",
//				"aunt edna's bungalow",
//				VConstants.book,
//				13,
//				"a story about the indentured servants in the southern province they are looked down upon"
//						+ "and this is an abolition movement book");
//		addItem(game,
//				"brown book",
//				"taming the shrew",
//				VConstants.book,
//				9,
//				"a human book about taming shrews in order to have a steady source of meat and eggs");
//		addItem(game,
//				"white book",
//				"the story of athena",
//				VConstants.book,
//				10,
//				"a book about a famous princess who was wise, liked to hunt, and developed a new type of bow");
//		addItem(game, "persian tea", VConstants.food, 10,
//				"Coming from a land of blah blah blah");
//		addItem(game, "merfolk pottery", VConstants.decorative, 30,
//				"considered the best and most decorative pottery");
//		addItem(game,
//				"blue book",
//				"merfolk book 'kitsune'",
//				VConstants.book,
//				7,
//				"(a story about a fox that turns into a woman and marries a man and"
//						+ "who goes through several trials and tribulations dealing with the human world(foxes are shapeshifters))");
//		addItem(game, "cheese", "Barbary wine and cheese", VConstants.food, 20,
//				"This province is famed for both and it has a culture of romantic behavior");
//
//		addItem(game,
//				"blue book",
//				"Brittiana boat making book",
//				VConstants.book,
//				25,
//				"It doesn't reveal any real secrets, but is still prized as the authority on boat making");
//
//		addItem(game,
//				"white book",
//				"A treatise on the picture book market of the merfolk region.",
//				VConstants.book,
//				5,
//				"Basically stating how it is very stratified"
//						+ "and much larger than most other markets with specific recommendations on how to reproduce the same thing"
//						+ "for region vespucan");
//
//		Items tItems = new Items();
//		tItems.add(game.getItem("meat"));
//		PBase treas = new SimpleMD();
//		treas.put(tItems.getKey(), tItems);
//		Item gold = game.getItem(VConstants.gold).clone();
//		gold.setAmount(20);
//		tItems.put(gold);
//
//		game.put(VConstants.market, treas);
//		return game;
//	}
//
//	private static Item addItem(Game game, String name, String type, int value) {
//		return addItemI(game, name, VConstants.type, type, VConstants.value,
//				value);
//	}
//
//	private static void addItem(Game game, String name, String type, int value,
//			String description) {
//		addItemI(game, name, VConstants.type, type, VConstants.value, value,
//				VConstants.description, description);
//	}
//
//	private static void addItem(Game game, String basictype, String name,
//			String type, int value, String description, String rarity) {
//		addItemI(game, name, VConstants.basictype, basictype, VConstants.type,
//				type, VConstants.value, value, VConstants.description,
//				description, VConstants.rare, rarity);
//	}
//
//	private static void addItem(Game game, String basictype, String name,
//			String type, int value, String description) {
//		addItemI(game, name, VConstants.basictype, basictype, VConstants.type,
//				type, VConstants.value, value, VConstants.description,
//				description, VConstants.rare, VConstants.normal);
//	}
//
//	private static Item addItemI(Game game, String name, Object... objs) {
//		Item item = new Item();
//		item.setName(name);
//		// item.setConsumable(consumable);
//
//		PBase.setPBase(item, objs);
//		// game.getPBase(VConstants.item).put(item.getKey(), item);
//		addG(item.getKey(), game, item);
//		game.getType(VConstants.randomitem).getListCreate(item.getType())
//				.add(name);
//		return item;
//	}
//
//	private static void addItem(Game game, String name, String type) {
//		addItem(game, name, type, 7);
//	}
//
//	private static void addFoodItem(PBase game, String name) {
//		addItem(game, name, true).put(VConstants.food, true);
//	}
//
//	public static PBase doInitialAdventurers() {
//
//		// beef up the mage with all kinds of summoning and direct damage
//		// abilities
//
//		PBase pb = new PBase();
//
//		MapArea maparea = new MapArea(3, 4);
//		questGenerator(pb, maparea);
//
//		// AttachUtil
//		// .attach(AttachUtil.mapstart, new SetCurrentFMD(1, 1), maparea);
//
//		maparea.getMap().put(VConstants.xfull, 1);
//		maparea.getMap().put(VConstants.yfull, 1);
//		AttachUtil.attach(AttachUtil.runbefore, new CreateAndDecay(), maparea);
//		FullMapData value = new FullMapData(1, 1);
//		value.getData(0, 0).put(new SimpleMD(VConstants.obstacle, "wall"));
//		maparea.getMap().put(VConstants.template, value);
//
//		pb.put(VConstants.maparea, maparea);
//
//		pb.put(VConstants.classname, Game.class.getName());
//		pb.put(VConstants.name, "initialadventurers");
//
////		BagMap bagMap = new BagMap(3, 6);
////		bagMap.getBagMap().setMapData(0, 0,
////				addNeed("itemshop", "trading", 0, 8));
////		bagMap.getBagMap().setMapData(0, 1,
////				addNeed("sewingmachine", "sewing", 0, 8));
////		bagMap.getBagMap()
////				.setMapData(0, 2, addNeed("alarmtrap", "guard", 0, 8));
////		bagMap.getBagMap().setMapData(0, 3,
////				new SimpleMD(VConstants.obstacle, "wall"));
////
////		// do boots + house
////		bagMap.getBagMap().setMapData(0, 4,
////				new SimpleMD(VConstants.gate, "tannery"));
////		bagMap.getBagMap().setMapData(0, 5,
////				new SimpleMD(VConstants.gate, "blacksmith")); // sword + house
////		bagMap.getBagMap().setMapData(0, 1,
////				new SimpleMD(VConstants.gate, "goldsmith")); // ring + house
////		bagMap.getBagMap().setMapData(1, 1,
////				new SimpleMD(VConstants.gate, "bakery")); // bread + house
////		bagMap.getBagMap().setMapData(1, 2,
////				new SimpleMD(VConstants.gate, "masonry")); // rock + house
////
////		bagMap.getBagMap().setMapData(1, 3, addNeed("barn", "farming", 0, 8));
////
////		bagMap.getBagMap().setMapData(1, 4, addHouse());
////
////		bagMap.getBagMap().setMapData(1, 5,
////				new SimpleMD(VConstants.gate, "sell")); // placing this should
////														// sell on the clone
////														// thing
//
////		bagMap.put(VConstants.selectionname, "bagselection");
////		pb.put(VConstants.flextable, ClientBuild.list(
////		
////		ClientBuild.list(ClientBuild.list(1, 0), bagMap)));
//
//		// ClientBuild2.getType(pb, VConstants.templatemap).put(VConstants.type,
//		// "none");
//		ClientBuild2.getType(pb, VConstants.templatemap).put(VConstants.type,
//				"default");
//		PBase charmap = new PBase();
//		{
//			PBase person = new PBase();
//			charmap.put("W", person);
//			person.put(VConstants.traits, "human female wizard");
//			person.put(VConstants.type, VConstants.livingbeing);
//			person.put(VConstants.name, "Terra");
//			person.put(VConstants.team, "1");
//			person.getListCreate(VConstants.equipment).add("wizard hat");
//			person.put(VConstants.image, "/images/terra.png");
//			person.put(VConstants.intelligence, "defensive");
//			person.put(VConstants.templatemap, new PBase());
//		}
//
//		{
//			PBase person = new PBase();
//			charmap.put("D", person);
//			person.put(VConstants.traits, "human female druid");
//			person.put(VConstants.type, VConstants.livingbeing);
//			person.put(VConstants.name, "Mariangela");
//			person.put(VConstants.team, "1");
//			person.getListCreate(VConstants.equipment).add("scimitar");
//
//			person.put(VConstants.intelligence, "defensive");
//			person.put(VConstants.templatemap, new PBase());
//		}
//
//		{
//			PBase person = new PBase();
//			charmap.put("H", person);
//			person.put(VConstants.traits, "human female healer");
//			person.put(VConstants.type, VConstants.livingbeing);
//			person.put(VConstants.name, "Molly Malone");
//			person.put(VConstants.team, "1");
//			person.getListCreate(VConstants.equipment).add("mace");
//
//			person.put(VConstants.intelligence, "defensive");
//			person.put(VConstants.templatemap, new PBase());
//		}
//		charmap.put("U", "stairs");
//		SimpleMD addHouse = addHouse();
//
//		charmap.put("3", addHouse);
//
//		charmap.put("T", addTradeArea());
//		charmap.put("Z", addExtraArea());
//		// charmap.put("g", addHouse(bagMap));
//		charmap.put("G", new SimpleMD(VConstants.under, "grass"));
//		charmap.put("L", new SimpleMD(VConstants.gate, "lavatory"));
//		charmap.put("t", new SimpleMD(VConstants.under, "water"));
//		charmap.put("d", "deep water");
//		charmap.put("w", "wall");
//
//		SimpleMD data = new SimpleMD(VConstants.obstacle, "campfire");
//		AttachUtil.attach(AttachUtil.placed, new AddNeed("singing", 9, 13),
//				data);
//		AttachUtil.attach(AttachUtil.placed, new AddNeed("singing", 9, 13),
//				data);
//		AttachUtil.attach(AttachUtil.placed, new AddNeed("singing", 9, 13),
//				data);
//
//		AttachUtil.attach(AttachUtil.placed, new AddNeed("dancing", 9, 13),
//				data);
//		AttachUtil.attach(AttachUtil.placed, new AddNeed("dancing", 9, 13),
//				data);
//		AttachUtil.attach(AttachUtil.placed, new AddNeed("dancing", 9, 13),
//				data);
//
//		charmap.put("C", data);
//		charmap.put("R", new SimpleMD(VConstants.gate, "market"));
//		// image (make it 50% alpha of a normal gate) (map position is chosen by
//		// choosefmd)
//		SimpleMD pg = new SimpleMD(VConstants.gate, "potential gate");
//		pg.put(VConstants.requirement, VConstants.gate);
//		charmap.put("1", pg);
//
//		BuildMap bm = new BuildMap(charmap, "U    1   1w\\n" + "dt   W    w\\n"
//				+ "dtt DH C  w\\n" + "dt        w\\n" + "dtt       w\\n"
//				+ "dtR      Tw\\n" + "dt        w\\n" + "dtt       w\\n"
//				+ "dt   Z   3w\\n" + "wwwwwwwwwww\\n", "main");
//		bm.getType(VConstants.resource).put(VConstants.image,
//				"/images/terra.png");
//		// bm.put(VConstants.defaultimage, "/images/grass.png");
//
//		// add bagmap to drop stuff
//
//		AttachUtil.attach(AttachUtil.mapstart, new RandomTypeCreation(
//				new SimpleMD(VConstants.gate, "tree"), 10), maparea);
//
//		AttachUtil.attach(AttachUtil.mapstart, bm, maparea);
//
//		// make the bagmap
//		// set the selection to game.bagselection
//		//
//		bm.getType(VConstants.resource).getListCreate(VConstants.leftclick)
//				.add(new CopySelection("bagselection"));
//
//		{
//			TradeRoute monsterentrance = new TradeRoute(2, 1, 9, 0, 0,
//					"leather armor", "black gloves", "pants");
//			monsterentrance.getListCreate(VConstants.list).add("kobold");
//			AttachUtil.attach(AttachUtil.runbefore, monsterentrance, maparea);
//		}
//
//		{
//			TradeRoute monsterentrance = new TradeRoute(2, 1, 9, 7, 3,
//					"cheese", "meat", "bread");
//			monsterentrance.getListCreate(VConstants.list).add("human");
//			AttachUtil.attach(AttachUtil.runbefore, monsterentrance, maparea);
//		}
//
//		Economy economy = new Economy();
//		pb.put(VConstants.economy, economy);
//		Point pos = null;
//		economy.addNeed("building", 0, 8);
//		economy.addNeed("building", 0, 8);
//		economy.addNeed("building", 0, 8);
//
//		return pb;
//	}
//
//	public static void questGenerator(PBase pb, MapArea maparea) {
//		QuestGenerator questGen = new QuestGenerator();
//		pb.put(VConstants.quest, questGen);
//		AttachUtil.attach(AttachUtil.runbefore, questGen, maparea);
////		PersonKeyValue intelOrStr = new PersonKeyValue(
////				"majorstat",
////				null,
////				new DisplayChoices(
////						"majorstat",
////						"1",
////						"person @name@ is coming to a fork in their development.  Should they hit the books, or hoe on the farm?",
////						new PBase(VConstants.message, "Hit the books",
////								VConstants.vparams, new AddStat(
////										VConstants.intelligence, 1)),
////						new PBase(VConstants.message, "Hoe the farm",
////								VConstants.vparams, new AddStat(
////										VConstants.strength, 1))));
////		questGen.addRequirement(intelOrStr);
////
////		PersonKeyValue charismaOrWisdom = new PersonKeyValue(
////				"majorstat",
////				null,
////				new DisplayChoices(
////						"majorstat",
////						"1",
////						"person @name@ can choose to take a one night stand.  Should they take it?",
////						new PBase(VConstants.message, "One night stand",
////								VConstants.vparams, new AddStat(
////										VConstants.charisma, 1)), new PBase(
////								VConstants.message, "Sensibly refrain",
////								VConstants.vparams, new AddStat(
////										VConstants.wisdom, 1))));
////
////		questGen.addRequirement(charismaOrWisdom);
////		{
////
////			PersonKeyValue artifactquest = new PersonKeyValue(
////					"artifactquest",
////					"1",
////					new DisplayResult(
////							"artifactquest",
////							"2",
////							new PBase(
////									VConstants.message,
////									"The leader was killed in a horrific mess and the rest of the team disbaned"
////											+ "due to infighting.  But @name@ came back alive and came and is both richer and a more experienced"
////											+ "fighter", VConstants.vparams,
////									new LeaveStayOrDie(20, 80, 0)),
////
////							new PBase(
////									VConstants.message,
////									"The team was doing really well until half of them were wiped out by a couple of unlucky moments."
////											+ "Unfortunately @name@ was one of the unlucky ones",
////									VConstants.vparams, new LeaveStayOrDie(0,
////											0, 100)),
////
////							new PBase(
////									VConstants.message,
////									"There was no actual quest.  The group stole all of @name@s possessions and left"
////											+ " @name@ quite unconscious.  Lost and alone with nothing @name@ had quite a time",
////									VConstants.vparams, new LeaveStayOrDie(80,
////											20, 0))));
////
////			// quest that rewards with a mysterious artifact of unknown ability
////			// the person has a chance of not coming back
////			PersonKeyValue artifact = new PersonKeyValue("artifactquest", null,
////					new DisplayChoices(
////							"A smart and dashing leader is putting together a quest to get a famous orb"
////									+ "Do you want to join?", new PBase(
////									VConstants.message, "Join", VConstants.key,
////									"artifactquest", VConstants.value, "1"),
////							new PBase(VConstants.message, "Stay safe",
////									VConstants.key, "artifactquest",
////									VConstants.value, "NA")));
////			questGen.addRequirement(artifact);
////			questGen.addRequirement(artifactquest);
////		}
////
////		{
////			RecentlyDone newperson = new RecentlyDone(
////					"newperson",
////					null,
////					new DisplayResult(
////							"newperson",
////							"1",
////							1,
////							new PBase(
////									VConstants.message,
////									"A peson who is naked and lost stumbled onto your town.  They have nothing and feel"
////											+ "too ashamed to go back to where they came.  They want to stay")));
////			questGen.addRequirement(newperson);
////		}
////
////		{
////			// a group of travelers come into town and tell of a specific event
////			// stay and listen?
////		}
////
////		{
////			// going off to school or going off to war
////			// school and war have chances of dying or staying gone (higher
////			// death with war, higher left with school)
////			PersonKeyValue intel2 = new PersonKeyValue(
////					"majorstat",
////					"1",
////					new DisplayChoices(
////							"majorstat",
////							"2",
////							"person @name@ is thinking about going to school"
////									+ "Should $he go to school, or try local books ",
////							new PBase(
////									VConstants.message,
////									"Go to school",
////									VConstants.vparams,
////									new VParams(new LeaveStayOrDie(17, 80, 3),
////											new AddStat(
////													VConstants.intelligence, 1))),
////
////							new PBase(VConstants.message,
////									"Try the local books", VConstants.vparams,
////									new AddStat(VConstants.strength, 1))));
////			questGen.addRequirement(intel2);
////		}
////
////		{
////			// leaving quests
////			// people who have left (in a game list) might ask for or give money
////			// this you don't have a choice over, it just happens
////
////			// take the stats, decide to either send or ask for money with an
////			// appropriate
////			// reason.
////
////			// also a small chance of dying, returning, or ending contact
////
////			OutsideTheTown leave = new OutsideTheTown(
////					"left",
////					"true",
////					new DisplayResult(
////							0,
////
////							new PBase(
////									VConstants.message,
////									"done well for myself, started at a fletching job and can send you some money back",
////									VConstants.vparams, new SendMoney(true)),
////							new PBase(VConstants.message,
////									"I accidentally gambled away my life savings and went heavily into debt."
////											+ "Can you send me some money? ",
////									VConstants.vparams, new SendMoney(false))));
////			questGen.addRequirement(leave);
////		}
//
////		VParams firstquest=		new VParams(
////				new DrawFocus("bakery",200,500),
////				new AddBuilding("bakery",0,0,new AddNeed("makebread",0,8),new Item("bread",1,5)),
////				new DisplayResult(
////						new PBase(
////								VConstants.message,
////								"I am some grizzled old etc etc who wants a bakery for some reason " +
////								"to be filled in later.  Storyline stuff." +
////								"Place the bakery by touching the house with bread and then touching the" +
////								"main area.  Click this to continue",VConstants.image,"bakery")));
////
////		MapKeyValue end = new MapKeyValue(VConstants.gate, "bakery", null,"Success at your first quest!");
////		firstquest.put(VConstants.end, end);
////		
////		{
////			TradeRoute monsterentrance = new TradeRoute(2, 1, 9, 0, 0,
////					"leather armor", "black gloves", "pants");
////			monsterentrance.getListCreate(VConstants.list).add("kobold");
////			VParams trade =new VParams(
////							new SetCurrentFMD(2, 1),
////							monsterentrance,
////							new DisplayResult(
////									new PBase(
////											VConstants.message,
////											"Grizzled old person returns!  This place is a trading outpost to trade our food for the kobold's leather armor." +
////											"A kobold is going to approach you soon.  Make sure to get some of that leather armor")));
////			
////		firstquest.put(VConstants.nextquest, trade);
////		
////		}
////		questGen.getListCreate(VConstants.activelist).add(firstquest);
////		
////		
////		questGen.addSubQuest(new MarketSubQuest());
//	}
//
//	private static SimpleMD addNeed(String building, String need, int minstate,
//			int maxstate) {
//		SimpleMD data = new SimpleMD(VConstants.gate, building);
//		AttachUtil.attach(AttachUtil.placed, new AddNeed(need, minstate,
//				maxstate), data);
//		return data;
//	}
//
//	
//	private static Object addTradeArea() {
//		SimpleMD guardHouse = new SimpleMD(VConstants.gate, "stairs");
//		guardHouse.put(VConstants.requirement, VConstants.gate);
//
//		PBase charmap = new PBase();
//
//		charmap.put("t", new SimpleMD(VConstants.gate, "tree"));
//		charmap.put("S", addNeed("itemshop", "trading", 0, 8));
//		charmap.put("E", new SimpleMD(VConstants.gate, "stairs"));
//		charmap.put("X", new SimpleMD(VConstants.gate, "teleporter",
//				new MovePerson(true)));
//
//		MapData md = new SimpleMD(VConstants.gate, "goblinentrance");
//		md.put(AttachUtil.personadded, new MovePerson());
//		md.put(VConstants.portal, new PointBase(1, 1));
//
//		charmap.put("g", md);
//
//		{
//			PBase person = new PBase();
//			charmap.put("F", person);
//			person.put(VConstants.traits, "human female fighter");
//			person.put(VConstants.type, VConstants.livingbeing);
//			person.put(VConstants.name, "Elizabeth");
//			person.put(VConstants.team, "1");
//			person.getListCreate(VConstants.equipment).add("sword");
//
//			person.put(VConstants.intelligence, "defensive");
//			person.put(VConstants.templatemap, new PBase());
//		}
//		// do a simple start on the buildmap to show the basics for goblins
//		BuildMap bm = new BuildMap(charmap, "g   F    E   \\n"
//				+ " t           \\n" + "    t        \\n" + "T    S      X\\n"
//				+ "             \\n" + "   t         \\n" + "             \\n"
//				+ "        E    ", "tradearea");
//
//		AttachUtil.attach(AttachUtil.placed, new ChooseFMD(bm), guardHouse);
//
//		bm.getType(VConstants.resource).put(VConstants.image,
//				"/images/ogre.png");
//		bm.getType(VConstants.resource).put(VConstants.sound, "tradingmusic");
//		return guardHouse;
//	}
//
//	private static Object addExtraArea() {
//		SimpleMD guardHouse = new SimpleMD(VConstants.gate, "guardentrance");
//		guardHouse.put(VConstants.requirement, VConstants.gate);
//
//		PBase charmap = new PBase();
//
//		MapData md = new SimpleMD(VConstants.gate, "goblinentrance");
//		md.put(AttachUtil.personadded, new MovePerson());
//		md.put(VConstants.portal, new PointBase(1, 1));
//
//		charmap.put("E", md);
//		charmap.put("t", new SimpleMD(VConstants.gate, "tree"));
//		charmap.put("b", new SimpleMD(VConstants.gate, "bush"));
//		charmap.put("g", new SimpleMD(VConstants.gate, "grass"));
//		charmap.put("w", new SimpleMD(VConstants.gate, "wheat"));
//		charmap.put("r", new SimpleMD(VConstants.gate, "rock"));
//		charmap.put("B", addNeed("barn", "farming", 0, 8));
//		// do a simple start on the buildmap to show the basics for goblins
//		BuildMap bm = new BuildMap(charmap, "E          BBB  \\n"
//				+ " gg  bb         \\n" + "                \\n"
//				+ " tttt           \\n" + " tttt      wwwww\\n"
//				+ " tttt      wwwww\\n" + "           wwwww \\n" + "    rrr",
//				"extraarea");
//
//		AttachUtil.attach(AttachUtil.placed, new ChooseFMD(bm), guardHouse);
//
//		bm.getType(VConstants.resource).put(VConstants.image,
//				"/images/skeleton.png");
//
//		return guardHouse;
//	}
//
//	private static SimpleMD addHouse() {
//		SimpleMD guardHouse = new SimpleMD(VConstants.gate, "guardentrance");
//		guardHouse.put(VConstants.requirement, VConstants.gate);
//		guardHouse.put(VConstants.unbuilt, true);
//		PBase charmap = new PBase();
//
//		charmap.put("b", new SimpleMD(VConstants.gate, "bed"));
//		charmap.put("m", new SimpleMD(VConstants.gate, "bush"));
//
//		charmap.put("S", "table");
//		charmap.put("s", "storage");
//		MapData md = new SimpleMD(VConstants.gate, "goblinentrance");
//		md.put(AttachUtil.personadded, new MovePerson());
//		md.put(VConstants.portal, new PointBase(1, 1));
//
//		charmap.put("g", md);
//		SimpleMD object = new SimpleMD(VConstants.under, "arablesoil");
//		charmap.put("f", object);
//
//		// do a simple start on the buildmap to show the basics for goblins
//		BuildMap bm = new BuildMap(charmap, "g  b  b\\n" + "      \\n"
//				+ "mb   bs\\n" + "mm   ss\\n" + "m      \\n" + "  S   b\\n"
//				+ "       \\n" + " b     ", "muck", "house");
//
//		AttachUtil.attach(AttachUtil.placed, new ChooseFMD(bm), guardHouse);
//
//		bm.getType(VConstants.resource).put(VConstants.image,
//				"/images/goblin-male.png");
//
//		return guardHouse;
//	}
//
//	private static SimpleMD addResourceMiner() {
//		SimpleMD buildingName = new SimpleMD(VConstants.gate,
//				"resourceentrance");
//		buildingName.put(VConstants.requirement, VConstants.gate);
//		{
//
//			PBase charmap = new PBase();
//
//			charmap.put("b", new SimpleMD(VConstants.gate, "bed"));
//			charmap.put("m", new SimpleMD(VConstants.gate, "muck"));
//
//			charmap.put("t", "tree");
//			charmap.put("S", "stone");
//			charmap.put("M", "metal");
//
//			charmap.put("s", "storage");
//			MapData md = new SimpleMD(VConstants.gate, "goblinentrance");
//			md.put(AttachUtil.personadded, new MovePerson());
//			md.put(VConstants.portal, new PointBase(1, 1));
//
//			charmap.put("g", md);
//			charmap.put("f", new SimpleMD(VConstants.under, "arablesoil"));
//
//			// do a simple start on the buildmap to show the basics for goblins
//			BuildMap bm = new BuildMap(charmap, "g     b    S  \\n"
//					+ "          SSS \\n" + "m     s    S  \\n"
//					+ "mm   ss       \\n" + "              \\n"
//					+ "              \\n" + "   ttt    M   \\n"
//					+ "  ttttt       ", "muck");
//
//			AttachUtil.attach(AttachUtil.placed, new ChooseFMD(bm),
//					buildingName);
//
//			// the farmer then has a heirarchy of needs that can get added
//			// AttachUtil.attach(AttachUtil.placed, new
//			// AddNeed("farmer"),goblinLair);
//
//			bm.getType(VConstants.resource).put(VConstants.image,
//					"/images/dwarf.png");
//		}
//		return buildingName;
//	}
//
//	public static PBase doNeedHeirarchy() {
//
//		// beef up the mage with all kinds of summoning and direct damage
//		// abilities
//
//		PBase pb = new PBase();
//		pb.put(VConstants.classname, Game.class.getName());
//		// pb.getType(VConstants.needmap).put("farmer", "recruitable");
//		// farm moves(with need)/builds/farms
//
//		pb.put(VConstants.economy, new Economy());
//		PBase needheir = pb.getType(VConstants.needheirarchy);
//		// needheir.put("farmer", "man");
//		// needheir.put(VConstants.man, VConstants.woman);
//		// needheir.put(VConstants.woman, VConstants.children);
//		//
//		// needheir.put(VConstants.farmer, VConstants.farming);
//		//
//		// needheir.put(VConstants.children, VConstants.supervision);
//		// {
//		// PBase ndesc = new PBase(VConstants.name,
//		// VConstants.children,VConstants.size,3);
//		// }
//		//
//		// {
//		// PBase ndesc = new PBase(VConstants.name,
//		// VConstants.farming,"template that does the farming structure.");
//		// }
//		// {
//		// PBase ndesc = new PBase(VConstants.name,
//		// VConstants.supervision,"template that watches the children");
//		// }
//		// //modifiers
//		// supervision only can go on adults
//
//		// otherwise with this structure the farming job should be taken by
//		// everyone and supervision should be taken by one of the adults
//
//		// then the basic instincts should be added to the personal economy as
//		// needs
//
//		// when the guard is introduced the farming parents will go over and
//		// train and the not actively guarding people will just train and do
//		// farm work.
//		// the guard will patrol and shout if needed.
//
//		// then the trader comes in. The trader favors trade and will actively
//		// create favors to sell goods (and eventually have the favors traded
//		// back for money)
//
//		return pb;
//	}
//}
