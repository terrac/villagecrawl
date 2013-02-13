package gwt.shared;

import gwt.client.game.AttachUtil;
import gwt.client.game.oobjects.TradeItem;
import gwt.client.game.vparams.SelectAndApply;
import gwt.client.game.vparams.SetTemplate;
import gwt.client.game.vparams.adding.AddBuilding;
import gwt.client.game.vparams.adding.AddScore;
import gwt.client.game.vparams.adding.PersonCreation;
import gwt.client.game.vparams.quest.ComplexCityGenerator;
import gwt.client.game.vparams.random.Formula;
import gwt.client.game.vparams.requirements.CultureStats;
import gwt.client.game.vparams.requirements.StoryEvent;
import gwt.client.game.vparams.requirements.TwoOptionEvent;
import gwt.client.item.Item;
import gwt.client.main.MoveClosest;
import gwt.client.main.VConstants;
import gwt.client.main.base.OobList;
import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

public class ClientBuildCultures extends ClientBuild2 {

	public static void doCulture(PBase pb) {
		{
			PBase person = new PBase();
			person.put(VConstants.traits, "human male fighter");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Gilgamesh");
			person.put(VConstants.team, "1");
			person.put(VConstants.image, "/images/titan.png");
			pb.getType(VConstants.person).put("gilgamesh", person);
			// add giant with 20 str for gilgamesh type instead of human
		}

		PBase culture = new PBase();
		addPeopleTypes(culture);
		addLines(culture);
		pb.put(VConstants.culture, culture);

		{
			PBase rtype = new PBase();
			culture.put("orc", rtype);
			rtype.put(VConstants.haggle, 49);
			PBase randomTypes = rtype.getType(VConstants.random);
			randomTypes.put("dairy", 20);
			randomTypes.put("fruit", 40);
			randomTypes.put("book", 20);
			randomTypes.put("leather", 20);
			randomTypes.put("meat", 20);
			randomTypes.put("metal", 20);
		}

		{
			PBase rtype = new PBase();
			culture.put("merfolk", rtype);
			rtype.put(VConstants.haggle, 49);
			PBase randomTypes = rtype.getType(VConstants.random);
			randomTypes.put("dairy", 20);
			randomTypes.put("fruit", 40);
			randomTypes.put("book", 20);
			randomTypes.put("leather", 20);
			randomTypes.put("meat", 20);
			randomTypes.put("metal", 20);
		}

		{
			PBase rtype = new PBase();
			culture.put("dwarf", rtype);
			rtype.put(VConstants.haggle, 49);
			PBase randomTypes = rtype.getType(VConstants.random);
			randomTypes.put("dairy", 20);
			randomTypes.put("fruit", 40);
			randomTypes.put("book", 20);
			randomTypes.put("leather", 20);
			randomTypes.put("meat", 20);
			randomTypes.put("metal", 20);
		}
		addItem(culture, "wheat", 2, "barnfood");

		addItem(culture, "milk", 3, "barnfood", "dairy");
		addItem(culture, "cheese", 4, "cheese", "dairy");

		addItem(culture, "bread", 3, "bread");
		addItem(culture, "banana", 4, "fruit");
		addItem(culture, "apple", 3, "fruit");
		addItem(culture, "grape", 2, "fruit");
		addItem(culture, "honeycomb", 5, "honey");
		addItem(culture, "royaljelly", 10, "honey");
		addItem(culture, "news", 2, "book").put(VConstants.image,
				"/images/white-book.png");
		addItem(culture, "stories", 3, "book").put(VConstants.image,
				"/images/brown-book.png");
		addItem(culture, "steak", 7, "steak");
		addItem(culture, "goldfish", 7, "fish");
		addItem(culture, "meat", 7, "meat");
		addItem(culture, "gold", 20, "metal");
		addItem(culture, "opal", 12, "gem");
		addItem(culture, "sword", 12, "sword");
		addItem(culture, "trident", 12, "trident");
		addItem(culture, "leather armor", 10, "leather");
		addItem(culture, "boots", 3, "leather");
		addItem(culture, "gloves", 3, "leather");
		addItem(culture, "cloak", 3, "leather");
		addItem(culture, "gloves", 3, "leather");
		addItem(culture, "beer", 3, "beer");

		String[] types = new String[] { "dairy", "fruit", "book", "leather",
				"meat", "metal" };
		for (String s : types) {
			culture.getType(VConstants.items).getType(s)
					.getListCreate(VConstants.formula)
					.add(new Formula(100, .9, .2, 0));

		}

		String[] lines = new String[] {
				"I had some issues along the way and need this amount to meet expenses",
				// "this item clearly has issues wrong with it, I can't accept it",
				"the people over in the other town are offering to pay this amount",
				"Pay this price and I'll be your friend",
				"pay this and I will keep on coming back to you",
				"I have to talk this over with my business partner",
				"Help me sell this here.  Otherwise I will go out of business" };

		String[] generic = new String[] {
				"a shortage has caused the price to rise",
				"a crop failure caused the price to rise",
				"I cannot go any lower",
				"This is a good price, you should take it!",
				"You can pay this amount",
				"Here are the goods. It’s a good price. I wanna sell it for this. Let’s do it.",
				"This is a high quality item", "I have to leave soon" };

		String[] lowest = new String[] {
				"I cannot go any lower",
				"Here are the goods. It’s a good price. I wanna sell it for this. Let’s do it.", };

		String[] fruit = new String[] {
				"A new theory says that fruit can cure all kinds of diseases",
				"We had a cool summer and the supply of fruit is limited" };

		String[] book = new String[] {
				"We had a book burning festival and so supplies are limited",
				"Books have fallen out of fashion with the nobility and authors have lost funding." };

		String[] honey = new String[] { "A bee extinction is causing issues",
				"A local bear made off with most of our honey" };

		String[] dairy = new String[] { "A crazed madman is demanding 10% of all dairy just up the road" };

		String[] accept = new String[] {
				"I am glad we can come to an agreement", "Wonderful" };
		String[] decline = new String[] { "I am sorry, but I have to move on",
				"Too bad" };

		// pb.put(VConstants.taggenerator,new TagGenerator(new
		// CountryRandom()));
		pb.getType(VConstants.taggenerator);
		PBase g = new PBase(VConstants.maximum, .7, VConstants.minimum, .1,
				VConstants.list, Arrays.asList(generic));

		PBase low = new PBase(VConstants.maximum, .2, VConstants.minimum,
				.0001, VConstants.list, Arrays.asList(generic));
		List<PBase> pbl = pb.getPBase(VConstants.taggenerator).getListCreate(
				VConstants.list);
		pbl.add(g);
		pbl.add(low);
		PBase hh = new PBase(VConstants.maximum, .999, VConstants.minimum, .5,
				VConstants.list, Arrays.asList(lines));
		pbl.add(hh);

		addSpecific(pb, fruit, "fruit");
		addSpecific(pb, book, "book");
		addSpecific(pb, honey, "honey");
		addSpecific(pb, dairy, "dairy");

		addSpecific(pb, accept, "accept");
		addSpecific(pb, decline, "decline");

		String event = "leader deposed, countryname1";
		PBase country = new PBase();
		pb.put(VConstants.country, country);
		country.put(
				VConstants.list,
				Arrays.asList(new String[] { "food", "leather", "metal",
						"sweet", "savory" }));

		List elist = culture.getListCreate(VConstants.event);
		List storyelist = culture.getListCreate(VConstants.storyevent);

		// elist.add(new StoryEvent(100,"baker@Hi I am a new local baker"));

		// elist.add(new
		// StoryEvent(VConstants.complexity,20,"courier@The priests of the goddess Demeter have issued a new edict over baking bread ",
		// "courier@The new rule is that all bread must be rectangular.  Any other type of bread is morally wicked and will likely cause a drought",
		// "baker@But my kiln is shaped like a circle!"));
		//
		// elist.add(new
		// StoryEvent(VConstants.health,5,"doctor@I can ease the symptoms with these herbs.  But you wont ever get better"));
		//
		// elist.add(new
		// StoryEvent(VConstants.something,5,"gossip@Hey trader.  Are you married?",
		// "gossip@I have a young woman at our temple who is looking!"));
		addStoryEvents(storyelist);
		addPopulationEvents(elist);

		culture.put(VConstants.randomeffect, new PBase(VConstants.list,
				new PBase[] {
						new PBase(VConstants.name, VConstants.haggle,
								VConstants.random, .5, VConstants.money, 10),
						new PBase(VConstants.name, "building", VConstants.type,
								"barn", VConstants.action, "farms",
								VConstants.random, .2, VConstants.money, 10),
						new PBase(VConstants.name, "building", VConstants.type,
								"woodcutter", VConstants.action,
								"resourcegather", VConstants.random, .1,
								VConstants.money, 10),
						new PBase(VConstants.name, "building", VConstants.type,
								"miner", VConstants.action, "resourcegather",
								VConstants.random, .1, VConstants.money, 10),

						new PBase(VConstants.name, "building", VConstants.type,
								"bakery", VConstants.action, "city",
								VConstants.random, .1, VConstants.money, 10),
						new PBase(VConstants.name, "building", VConstants.type,
								"blacksmith", VConstants.action, "city",
								VConstants.random, .1, VConstants.money, 10) }));
		// elist.add(new
		// TwoOptionEvent(1000,"Our king does not leave a son to his father or a girl to her mother."+
		// "Should we find peace by distracting him or by opposing him?","\"it was you, Aruru, who created mankind(?),"
		// +
		// "now create a zikru to it/him." +
		// "Let him be equal to his (Gilgamesh's) stormy heart," +
		// "let them be a match for each other so that Uruk may find peace ","Stormy times are ahead",new
		// AddScore("stability",2),new AddScore("stability",-2)));
		//

		culture.getType(VConstants.resource).put("woodcutter",
				new PBase(VConstants.type, "wood", VConstants.value, "tree"));
		culture.getType(VConstants.resource).put("miner",
				new PBase(VConstants.type, "iron", VConstants.value, "rock"));

		culture.getType(VConstants.sell).put("wheat",
				new String[] { "bakery", "beermaker" });
		culture.getType(VConstants.sell).put("milk",
				new String[] { "cheesemaker" });

		culture.getType(VConstants.itemmap).put("woodcutter", "wood");
		culture.getType(VConstants.itemmap).put("miner", "iron");
		culture.getType(VConstants.itemmap).put("cheesemaker", "cheese");
		culture.getType(VConstants.itemmap).put("bakery", "bread");
		culture.getType(VConstants.itemmap).put("barn", "barnfood");
		culture.getType(VConstants.itemmap).put("blacksmith", "sword");
		culture.getType(VConstants.itemmap).put("tannery", "leather");
		culture.getType(VConstants.itemmap).put("goldsmith", "gem");
		culture.getType(VConstants.itemmap).put("beermaker", "beer");
		culture.getType(VConstants.itemmap).put("papermaker", "book");

		culture.getType(VConstants.equipment).put("beermaker",
				new String[] { "black coat" });
		culture.getType(VConstants.equipment).put("barn",
				new String[] { "animal skin", "dagger" });// sling
		culture.getType(VConstants.equipment).put("blacksmith",
				new String[] { "leather armor", "hammer" });
		culture.getType(VConstants.equipment).put("tannery",
				new String[] { "leather armor", "enchantress_dagger" });
		culture.getType(VConstants.equipment).put(
				"goldsmith",
				new String[] { "green breastplate", "dark green pants",
						"hammer" });
		culture.getType(VConstants.equipment).put("bakery",
				new String[] { "dress" });
		culture.getType(VConstants.equipment).put("cheesemaker",
				new String[] { "dress", "dagger" });
		culture.getType(VConstants.equipment).put("temple",
				new String[] { "red loincloth" });
		culture.getType(VConstants.equipment).put("guard",
				new String[] { "leather armor", "sword" });
		culture.getType(VConstants.equipment).put("papermaker",
				new String[] { "wizard hat", "robe" });

		culture.getType(VConstants.equipment).put("orc",
				new String[] { "leather armor", "sword" });
		culture.getType(VConstants.equipment).put("merfolk",
				new String[] { "robe", "trident" });
		culture.getType(VConstants.equipment).put(
				"dwarf",
				new String[] { "green breastplate", "dark green pants",
						"hammer" });

		// culture.getType(VConstants.stats).put(VConstants.honor, 100);
		// culture.getType(VConstants.stats).put(VConstants.wellness, 100);
		// culture.getType(VConstants.stats).put(VConstants.efficency, 10);
		// culture.getType(VConstants.stats).put(VConstants.nice, 50);
		culture.getType(VConstants.stats).put(VConstants.happiness, 50);
		culture.getType(VConstants.stats).put(VConstants.food, 50);
		culture.getType(VConstants.stats).put(VConstants.water, 50);

		culture.getType(VConstants.needmap).put("beermaker",
				new PBase(VConstants.happiness, 10));
		culture.getType(VConstants.needmap).put("baker",
				new PBase(VConstants.wellness, 10));
		culture.getType(VConstants.needmap).put("barn",
				new PBase(VConstants.wellness, 10));
		culture.getType(VConstants.needmap).put("doctor",
				new PBase(VConstants.wellness, 10));
		culture.getType(VConstants.needmap).put("guard",
				new PBase(VConstants.honor, 10));
		culture.getType(VConstants.needmap).put("temple",
				new PBase(VConstants.honor, 10));

		culture.getListCreate(VConstants.jerk).add("question jerk 1");
		{

			PBase person = new PBase();
			person.put(VConstants.traits, "human male fighter");
			person.put(VConstants.name, "Bob the Beermaker");
			person.put(VConstants.team, "1");
			person.put(VConstants.avatar, "bobthebeermaker");
			pb.getType(VConstants.person).put("bobthebeermaker", person);
			person.put(VConstants.hair, "short red");
			person.put(VConstants.face, "full red");
			person.getListCreate(VConstants.equipment).add("leather armor");

			PBase q = new PBase();
			q.put(VConstants.type, "beermaker");

			// q should have a price
			// an item
			// and possibly a list of lines if you dissagree (or more
			// complicated later maybe
			q.getListCreate(VConstants.list).add("bob initial offer");
			q.getListCreate(VConstants.list).add("bob disagree line 1");
			AttachUtil.attach(VConstants.success, new AddBuilding("beermaker",
					"farms", "bobthebeermaker"), q);
			q.put(VConstants.person, person);

			q.put(VConstants.item, new Item("beer", 5, 10));
			culture.getListCreate(VConstants.quest).add(q);
		}

		{

			PBase person = new PBase();
			person.put(VConstants.traits, "human male fighter");
			person.put(VConstants.name, "Shawn J");
			person.put(VConstants.team, "1");
			// person.put(VConstants.avatar, "bobthebeermaker");
			pb.getType(VConstants.person).put("shawnsalesman", person);
			person.put(VConstants.hair, "short white");
			person.getListCreate(VConstants.equipment).add("leather armor");

			person.put(VConstants.personality, "jerk");
			PBase q = new PBase();
			q.put(VConstants.type, "bakery");

			// q should have a price
			// an item
			// and possibly a list of lines if you dissagree (or more
			// complicated later maybe
			q.getListCreate(VConstants.list).add("shawn initial offer");
			q.getListCreate(VConstants.list).add("shawn disagree line 1");
			AttachUtil.attach(VConstants.success, new AddBuilding("bakery",
					"farms", "shawnsalesman"), q);
			q.put(VConstants.person, person);
			q.put(VConstants.item, new Item("bread", 5, 10));
			culture.getListCreate(VConstants.quest).add(q);

		}

	}

	public static void addLines(PBase culture) {
		PBase lines = culture.getType(VConstants.lines);
		lines.getListCreate(VConstants.list).add("jerk");
		lines.getListCreate(VConstants.list).add("talented");
		lines.getListCreate(VConstants.list).add("sickly");
		lines.getListCreate(VConstants.list).add("sociable");

		{
			PBase pb = lines.getType(VConstants.jerk);
			pb.put(VConstants.list,
					new String[] {
							"I think @otherperson@ doesn't like me",
							"I don't like @otherperson@'s type",
							"I am upset that @otherperson@ isn't doing more for me",
							"(dialog box) I think @otherperson@ should leave, do you agree?" });
		}

		{
			PBase pb = lines.getType("talented");
			pb.put(VConstants.list, new String[] { "Let me focus",
					"That doesn't seem too difficult" });
		}
		
		{
			PBase pb = lines.getType("sickly");
			pb.put(VConstants.list, new String[] {   "I feel a bit sniffly","I feel sick","I feel better now" 
 });
		}
		
		{
			PBase pb = lines.getType("sociable");
			pb.put(VConstants.list, new String[] { "How are you doing today @otherperson@"
					  , "Nice weather we are having @otherperson@"   });
		}
	}

	public static void addPeopleTypes(PBase culture) {
		List<PBase> pblist = culture.getType(VConstants.person).getListCreate(
				VConstants.list);

		pblist.add(new PBase(VConstants.lines, "I am entj",
				VConstants.extrovert, 50, VConstants.sensing, 50,
				VConstants.thinking, 50, VConstants.judging, 50));
		pblist.add(new PBase(VConstants.lines, "I am infp",
				VConstants.extrovert, -50, VConstants.sensing, -50,
				VConstants.thinking, -50, VConstants.judging, -50));
		pblist.add(new PBase(VConstants.lines, "I am esfp",
				VConstants.extrovert, 50, VConstants.sensing, 50,
				VConstants.thinking, -50, VConstants.judging, -50));
		pblist.add(new PBase(VConstants.lines, "I am isfp",
				VConstants.extrovert, -50, VConstants.sensing, 50,
				VConstants.thinking, -50, VConstants.judging, -50));

		culture.getType(VConstants.person).getType(VConstants.extrovert)
				.put("beermaker", new AddScore(VConstants.happiness, 1));

	}

	public static void addNeeds(PBase culture) {
		culture.getType(VConstants.need)
				.getType(VConstants.stats)
				.put(VConstants.happiness,
						new CultureStats(
								VConstants.happiness,
								40,
								new SelectAndApply(
										new StoryEvent(
												"Ahh you startled me.  Don't sneak up on me like that"),
										new StoryEvent(
												"low niceness complaint #2"),
										new StoryEvent(
												"low niceness complaint #3")),
								true));

		culture.getType(VConstants.need).getType(VConstants.stats)
				.put(VConstants.food, new TradeItem("food"));

		culture.getType(VConstants.need)
				.getType(VConstants.stats)
				.put(VConstants.water,
						new OobList(VConstants.water, new MoveClosest(
								VConstants.under, VConstants.water)));

		List lines = culture.getListCreate(VConstants.lines);
		lines.add(new CultureStats(
				VConstants.nice,
				40,
				new SelectAndApply(
						new StoryEvent(
								"Ahh you startled me.  Don't sneak up on me like that"),
						new StoryEvent("low niceness complaint #2"),
						new StoryEvent("low niceness complaint #3"))));

		lines.add(new CultureStats(VConstants.honor, 30, new SelectAndApply(
				new StoryEvent("low corruption complaint #1"), new StoryEvent(
						"low corruption complaint #2"), new StoryEvent(
						"low corruption complaint #3")), false));

		lines.add(new CultureStats(VConstants.honor, 40, new SelectAndApply(
				new StoryEvent("high corruption complaint #1"), new StoryEvent(
						"high corruption complaint #2"), new StoryEvent(
						"high corruption complaint #3"))));

		lines.add(new CultureStats(VConstants.honor, 20, new SelectAndApply(
				new StoryEvent("somewhat high corruption complaint #1"),
				new StoryEvent("somewhat high corruption complaint #2"),
				new StoryEvent("somewhat high corruption complaint #3"),
				new StoryEvent("somewhat high corruption complaint #4"),
				new StoryEvent("somewhat high corruption complaint #5"))));

		// lines.add(new IfTeam(new SelectAndApply(
		// new StoryEvent("sameteam #1"), new StoryEvent(
		// "sameteam #2"))));
		//
		//
		// lines.add(new IfTeam(new SelectAndApply(
		// new StoryEvent("diffteam #1"), new StoryEvent(
		// "diffteam #2"))));
		//
		//
		// lines.add(new IfLeader(new SelectAndApply(
		// new StoryEvent("leaders responsibilites #1"), new StoryEvent(
		// "leaders responsibilities #2"))));
		//
		//
		// lines.add(new CheckStats(VConstants.team,true,VConstants.exists,new
		// SelectAndApply(
		// new
		// TwoOptionEvent("choice team corruption higher","choice team corruption lower"))));
		//
		//
		//
		// // these additionally alter stats
		// lines.add(new CheckStats(VConstants.team,20,VConstants.nice)
		// new StoryEvent("1 person talk behind another's back"));
		// lines.add(new CheckStats(VConstants.team,20,VConstants.honor)
		// new StoryEvent("1 person cheats a person on the same team"));
		// lines.add(new CheckStats(VConstants.team,40,VConstants.honor)
		// new StoryEvent("1 person cheats a person on a different team"));
		//
		// lines.add(new CheckStats(VConstants.team,40,VConstants.nice)
		// new FindPerson(VConstants.nice,20,new
		// StoryEvent("1 person talk behind another's back who has a low niceness level"));
		//
		//
		// lines.add(new CheckStats(VConstants.team,20,VConstants.nice),
		// new StoryEvent("one person helps another with something"));
		// lines.add(new CheckStats(VConstants.team,20,VConstants.nice),
		// new StoryEvent("two people share a general positive chat"));

	}

	private static void addStoryEvents(List elist) {
		// give an efficency worth a radius of 4
		// additionally give the resources to build
		// place 3 breadmakers, and 3 barns
		// also place gilgamesh
		elist.add(new StoryEvent(
				"Supreme over other kings, lordly in appearance,\\n"
						+ "he is the hero, born of Uruk, the goring wild bull.\\n"
						+ "He walks out in front, the leader,\\n"
						+ "and walks at the rear, trusted by his companions.\\n"
						+ "Mighty net, protector of his people,\\n"
						+ "raging flood-wave who destroys even walls of stone!\\n"
						+ "", new SetTemplate("Gilgamesh", "mafia")));
		// gilgamesh uses the mafia template and gives a little gdp subtraction
		// image when he visits a farm
		// wait 200
		elist.add(new StoryEvent(
				"There is no rival who can raise a weapon against him.\\n"
						+ "His fellows stand (at the alert), attentive to his (orders !),\\n"
						+ "Gilgamesh does not leave a son to his father,\\n"
						+ "day and night he arrogantly ...\\n"
						+ "Is he the shepherd of Uruk-Haven,\\n"
						+ "is he their shepherd...\\n"
						+ "bold, eminent, knowing, and wise,\\n"
						+ "Gilgamesh does not leave a girl to her mother(?)!"));
		// wait 200
		{
			PBase person = new PBase();
			person.put(VConstants.traits, "giant male fighter");
			person.put(VConstants.type, VConstants.livingbeing);
			person.put(VConstants.name, "Einkadu");
			person.put(VConstants.team, "1");
			person.put(VConstants.templatemap, new PBase(VConstants.main,
					"animalexplore"));
			person.put(VConstants.image, "/images/stonegiant.png");

			elist.add(new StoryEvent(
					"it was you, Aruru, who created mankind(?),\\n"
							+ "now create a rival to him.\\n"
							+ "Let him be equal to Gilgamesh's stormy heart,\\n"
							+ "let them be a match for each other so that Uruk may find peace!",
					new PersonCreation(person)));

			// create some animals with moveclosest("livingbeing.name.Einkadu")

		}
		// wait 200
		elist.add(new StoryEvent(
				"Go, set off to Uruk,"
						+ "tell Gilgamesh of this Man of Might."
						+ "He will give you the harlot Shamhat, take her with you."
						+ "The woman will overcome the fellow (?) as if she were strong."
						+ "When the animals are drinking at the watering place"
						+ "have her take off her robe and expose her sex."
						+ "When he sees her he will draw near to her,"
						+ "and his animals, who grew up in his wilderness, will be alien to him.",
				null));
		// add building temple
		// prostitute converts wildman (
		// they wait a while
		// make the animals run off

		// give einkadu a template that chases the animals off (not the main
		// template)

		// the part about the prostitute talking about gilgamesh
		// an action where gilgamesh and them move and have sex

		// 5 create a beermaker
		// a some statement about beer
		// b the prostitute and wild man go there
		// c they get drunk and stuff
		// 6 they both travel off (wait a wihle)
		// a they come back with cedar and make a gate
		// b einkadu becomes sick and dies
		// c gilgamesh wanders off again
		//

		// do an icarus one

		// do a story about a guard who finds out a person's name and offers the
		// choice to threaten the person with it
	}

	// needs more specifics
	public static void addPopulationEvents(List elist) {

		elist.add(new TwoOptionEvent(
				"this is some choice that adds 30 to honor to circumvent the middle part of the game.",
				"", "", new AddScore(VConstants.honor, 30), null));

		// basically you can opt to get specific industries on your side or aim
		// for the whole populace
		elist.add(new TwoOptionEvent(
				"Sam, from the Lapid house is upset that some merchants are copying the beer that his hou"
						+ "se has been making for 30 years.  Should we help him out?",
				"And thus let it be known that piracy of beer is illegal", "",
				new AddScore(VConstants.honor, 3), new AddScore(
						VConstants.efficency, 3)));
		elist.add(new TwoOptionEvent(
				"John, a local smith is arguing that the smithing industry really needs "
						+ "a temporary tarriff on foriegn iron in order to save it",
				new VParams(new AddScore("smith", 5), new AddScore(
						VConstants.honor, 5)), null));
		elist.add(new TwoOptionEvent(
				"Bob the one eyed banker wants less regulations on banks."
						+ "He promises that the banks will be extra friendly to the nobility.",
				new VParams(new AddScore("banking", 5), new AddScore(
						VConstants.honor, 5)), null));

		elist.add(new TwoOptionEvent(
				"Some libertines are arguing that they don't want to pay taxes.  Do they have to pay taxes because they are owned by the state"
						+ "     or because the state is looking out for their best interests?",
				new AddScore(VConstants.honor, 5), new AddScore(
						VConstants.efficency, 5)));
		elist.add(new TwoOptionEvent("a progressive tax or a flat tax?",
				new AddScore(VConstants.efficency, 5), new AddScore(
						VConstants.honor, -3)));

		// going against any of these increases corruption
		// going for gives various results
		elist.add(new TwoOptionEvent(
				"The villagers want to enshrine their traditions about proving innocence into law.",
				"If any one bring an accusation against a man, and the accused go to the river and leap"
						+ "into the river, if he sink in the river his accuser shall take possession of his house. But if"
						+ "the river prove that the accused is not guilty, and he escape unhurt, then he who had"
						+ "brought the accusation shall be put to death, while he who leaped into the river shall take"
						+ "possession of the house that had belonged to his accuser.",
				"The river take you!", new AddScore(VConstants.religion, 5,
						VConstants.economy, 5), new AddScore(VConstants.honor,
						5)));
		elist.add(new TwoOptionEvent(
				"A judge has found to be in error about his judgement.  It looks like it might be the judges fault.  Should we punish him?",
				"If a judge try a case, reach a decision, and present his judgment in writing; if later error"
						+ "shall appear in his decision, and it be through his own fault, then he shall pay twelve"
						+ "times the fine set by him in the case, and he shall be publicly removed from the judge's"
						+ "bench, and never again shall he sit there to render judgement.",
				"3. If any one bring an accusation of any crime before the elders, and does not prove what"
						+ "he has charged, he shall, if it be a capital offense charged, be put to death.",

				new AddScore(VConstants.authority, -5, VConstants.honor, 5),
				new AddScore("authority", 5)));
		elist.add(new TwoOptionEvent(
				"We have levied a fine in a crime.  Should it go to the accuser?",
				"If he satisfy the elders to impose a fine of grain or money, he shall receive the fine that"
						+ "the action produces.",
				"Redress belongs to the state", new AddScore("military", 10),
				new AddScore("arts", 10)));
		elist.add(new TwoOptionEvent(
				"A man has accused a merchant of stealing, but he does not have witnesses."
						+ "Should this man be put to death?",
				"If the owner do not bring witnesses to identify the lost article, he is an evil-doer, he"
						+ "has traduced, and shall be put to death.",
				"Mercy to the accuser", new AddScore(VConstants.honor, -5),
				new AddScore(VConstants.honor, 5)));

		elist.add(new TwoOptionEvent(
				"a prominent figure wants to create a university for an educated populace, but they need a patron",
				new VParams(new AddScore("science", 5), new AddScore(
						"tax burden", 5)), null));
		elist.add(new TwoOptionEvent(
				"some dissidents who don't like your position on Ensoulment want to come in and write about it.  Refuse them entry?",
				new VParams(new AddScore("stability", 5), new AddScore(
						"resentment", 5)), null));

		// elist.add(new TwoOptionEvent(1000,"Do we value a spartan lifestyle?",
		// "THIS IS SPARTA!!!","Well then" +
		// " maybe we should just build a temple or something.",new
		// AddScore(VConstants.health,3),new
		// AddScore(VConstants.efficency,-3)));

	}

	public static void addSpecific(PBase pb, String[] fruit, String ty) {
		PBase tpb = new PBase(VConstants.maximum, .6, VConstants.minimum, .2,
				VConstants.list, Arrays.asList(fruit));
		pb.getPBase(VConstants.taggenerator).getType(ty)
				.put(VConstants.list, Arrays.asList(tpb));
	}

	private static Item addItem(PBase culture, String string, int k,
			String... string2) {
		Item item = new Item(string, 1, k);

		item.put(VConstants.type, Arrays.asList(string2));
		item.setItemValue(k);
		item.setAmount(1);
		for (String a : string2) {
			culture.getType(VConstants.items).getType(a)
					.getListCreate(VConstants.list).add(item);
		}
		return item;
	}
}
