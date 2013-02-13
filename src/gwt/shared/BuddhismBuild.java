package gwt.shared;

public class BuddhismBuild {

//	public static Game doBuddhism() {
//			Game game = new Game();
//			
//			
//			game.put(VConstants.name, "Practice");
//			game.setMapArea(new MapArea(game, null, 4, 1));
//			
//			
//			
//	
//			PTemplate waitandattack = addTemplate(game, "waitandattack");
//	
//			
//			addAction(waitandattack, addG("wait", game, new Wait(20)));
//			waitandattack.setMetaoobj(new AttackEnemyMeta());
//			{
//				PTemplate animal = addTemplate(game, "animal");
//				addAction(animal, addG("explore", game, new MoveRandomHashMapData("explore")));
//				animal.setMetaoobj(new AttackEnemyMeta());	
//			}
//			
//			// set
//			{
//				Stats stats = new Stats();
//				stats.put(VConstants.strength, 1);
//				Person monster = new Person("one", stats);
//				createAttack("bash", VConstants.physical, 1, 1, 4, monster, game);
//				stats.put(VConstants.maxhealth, 40);
//				//monster.put(VConstants.level, 1);
//				monster.getTemplate().setRationalChild("main", "attackmove");
//				addG("one", game, monster);
//				monster.put(VConstants.image, "/images/ettin.png");
//				monster.put(VConstants.team, "ettin");
//				monster.put("goldwon", 20);
//				Person clone = monster.clone();
//				clone.setName("2");
//				addG("2", game, clone);
//				
//			}
//			{
//				Stats stats = new Stats();
//	
//				Animal animal = new Animal("butterfly", stats);
//				stats.put(VConstants.strength, 1);
//				stats.put(VConstants.maxhealth, 100);
//				
//				PBase pb=createAttack("bite", VConstants.physical, 1, 1, 5, animal, game);
//				;
//				
//				//animal.put(VConstants.level, 1);
//				animal.getTemplate().setRationalChild("main", "breath");
//				addG("butterfly", game, animal);
//				animal.put(VConstants.team, "butterfly");
//				
//			}
//			
//			{
//				Stats stats = new Stats();
//	
//				Animal animal = new Animal("cow", stats);
//				stats.put(VConstants.strength, 5);
//				stats.put(VConstants.maxhealth, 100);
//				
//				PBase pb=createAttack("bite", VConstants.physical, 1, 1, 5, animal, game);
//				
//				
//				//animal.put(VConstants.level, 1);
//				animal.getTemplate().setRationalChild("main", "animal");
//				addG("cow", game, animal);
//				animal.put(VConstants.team, "cow");
//				
//			}
//	
//	
//			
//			
//			String human = addG("human", game, new PBase());
//			game.put(VConstants.applydamage, new ApplyDamage());
//			final BagMap bagmap = new BagMap(5, 2);
//			String bagmapname = addG("bagmapname", game, bagmap);
//			game.getHtmlOut().getList(HtmlOut.refreshList).add(bagmapname);
//	
//			bagmap.put(BagMap.depositpoint, list(7, 7));
//	//		AttachUtil.attach(AttachUtil.clickfmd, new IfKey(
//	//				VConstants.livingbeing, new PlaySound("greetings")), game
//	//				.getMapArea());
//			TemplateDisableDisplay param = new TemplateDisableDisplay();
//			AttachUtil.attach(AttachUtil.clickfmd, new IfKey(
//					VConstants.livingbeing, param), game.getMapArea());
//			
//			
//			String movetonearestitem = addG("movetonearestitem", game,
//					new MoveClosest(VConstants.items, null, 20));
//			String pickitemsup = addG("pickitemsup", game, new PickUp());
//			PTemplate gatheritems = addTemplate(game, "gatheritems");
//			String gathitems = addG("gatheritems", game, new OobList(
//					movetonearestitem, pickitemsup));
//			addAction(gatheritems, gathitems);
//			gatheritems.setMetaoobj(new AttackEnemyMeta());
//			
//			
//			Person person = new Person("fighter");
//			person.put(VConstants.team, GameUtil.getPlayerTeam());
//			person.getTemplate().setRationalChild("main", "gatheritems");
//	
//			game.put(VConstants.allskills, new PBase());
//			Person sp;
//			{
//				sp = person.clone();
//				String type = "fighter";
//				sp.getStats().put(VConstants.strength, 5);
//				sp.put("gold", 50);
//	
//				sp.put(VConstants.level, 1);
//	
//				sp.getStats().put(VConstants.maxhealth, 100);
//	
//				sp.setType(type);
//				sp.put(VConstants.levelup, new LevelUp());
//	
//				
//				sp.getTemplate().getRationalMap().put("main", "waitandattack");
//				createAttack("sword",VConstants.physical,1,1,10,sp,game);
//				game.getPersonMap().put(sp.getType(), sp);
//	
//				LivingBeing terra = sp.clone();
//				terra.getStats().put(VConstants.strength, 10);
//				terra.getStats().put(ApplyDamage.pevade, 30);
//				terra.put(VConstants.image, "/images/terra.png");
//				terra.setName("terra");
//				Item item = addItem(game,"fancysword",false);
//				item.put(VConstants.type, VConstants.weapon);
//				item.put(VConstants.damageadded, 4);
//				terra.getItems().put(item);
//	//			AttachUtil.attach(AttachUtil.gamestart, new PutMap( 5, 5,
//	//					VConstants.livingbeing, terra, VConstants.person), game);
//				
//				
//				// game.getMapArea().getMap().getData(0, 0).initIfNeeded();
//	
//				sp.getStats().put(VConstants.maxhealth, 100);
//				PBase pb;
//	
//				pb = new PBase();
//				pb.put(VConstants.name, "Strength");
//				pb.put(VConstants.experience, 100);
//				pb.put(VConstants.strength, 10);
//				pb.put(VConstants.rechargetime, 30);
//				pb.put(VConstants.target, VConstants.self);
//				pb.put(VConstants.abilityai, new CheckDamage(pb,100,VConstants.damage));
//				
//				
//				addAbility(pb, game);			
//				game.getPBase(VConstants.allskills).getList(type).add(pb);
//				
//				pb = new PBase();
//				pb.put(VConstants.name, "Dodge");
//				pb.put(VConstants.experience, 70); // adds 10% to physical evade
//				pb.put(ApplyDamage.pevade, 10);
//				
//				addAbility(pb, game);			
//				game.getPBase(VConstants.allskills).getList(type).add(pb);
//				
//				sp.put(VConstants.percenttake, 5);
//	
//				
//				AttachUtil.attach(AttachUtil.gamestart, new PutMap(bagmapname, 0, 0,
//						VConstants.livingbeing, "fighter", VConstants.person), game);
//			}
//			
//			{
//				sp = person.clone();
//				String type = "mage";
//	
//				sp.put("gold", 30);
//	
//				sp.put(VConstants.level, 1);
//				
//				sp.getStats().put(VConstants.maxhealth, 50);
//	
//				sp.setType(type);
//				sp.put(VConstants.levelup, new LevelUp());
//	
//				
//				createAttack("zap",VConstants.magic,1,4,5,sp,game);
//				game.getPersonMap().put(sp.getType(), sp);
//	
//				// game.getMapArea().getMap().getData(0, 0).initIfNeeded();
//	
//				PBase pb = new PBase();
//				pb.put(VConstants.name, "fireball");
//				pb.put(VConstants.damagetype, VConstants.fire);
//				pb.put(VConstants.experience, 50);
//				pb.put(VConstants.damage, 20);
//				pb.put(VConstants.radius, 2);
//				pb.put(VConstants.range,4);
//				pb.put(VConstants.rechargetime, 3);			
//				pb.put(VConstants.target, VConstants.enemy);
//				pb.put(VConstants.abilityai, new CheckAttack(pb,AttachUtil.runpersonbefore));
//				
//				//once used if disabled then set prev attack as default
//				
//				addAbility(pb, game);			
//				game.getPBase(VConstants.allskills).getList(type).add(pb);
//				
//				// pb = new PBase();
//				// pb.put(VConstants.name, "fireball 2");
//				// //maybe put type fireball
//				// pb.put(VConstants.experience, 400);
//				// pb.put(VConstants.strength, 10);
//				// pb.put(VConstants.rechargetime, 30);
//				// sp.getList(VConstants.allskills).add(pb);
//	
//	
//				sp.put(VConstants.percenttake, 5);
//	
//				String addp = addG("addbagperson2" + type, game, new PutMap(
//						bagmapname, 1, 0, VConstants.livingbeing, type,
//						VConstants.person));
//				AttachUtil.attach(AttachUtil.gamestart, addp, game);
//			}
//	
//			{
//				sp = person.clone();
//				String type = "archer";
//	
//				sp.put("gold", 50);
//	
//				sp.put(VConstants.level, 1);
//	
//				sp.getStats().put(VConstants.maxhealth, 70);
//	
//				sp.setType(type);
//				sp.put(VConstants.levelup, new LevelUp());
//	
//				createAttack("bow",VConstants.physical,1,4,10,sp,game);
//				game.getPersonMap().put(sp.getType(), sp);
//	
//				sp.put(VConstants.percenttake, 5);
//	
//				String addp = addG("addbagperson2" + type, game, new PutMap(
//						bagmapname, 2, 0, VConstants.livingbeing, type,
//						VConstants.person));
//				AttachUtil.attach(AttachUtil.gamestart, addp, game);
//	
//				// Item item=addItem(game, "bow", false);
//				// item.put(VConstants.type, "weapon");
//				// item.put(VConstants.image, "/images/doll/bow.png");
//				// sp.getAlterHolder().put("weapon",item.clone());
//			}
//	
//			{
//				sp = person.clone();
//				String type = "healer";
//	
//				sp.put("gold", 50);
//	
//				sp.put(VConstants.level, 1);
//				
//				sp.getStats().put(VConstants.maxhealth, 80);
//	
//				sp.setType(type);
//				sp.put(VConstants.levelup, new LevelUp());
//	
//				createAttack("mace",VConstants.physical,1,1,7,sp,game);
//	
//				game.getPersonMap().put(sp.getType(), sp);
//	
//				// game.getMapArea().getMap().getData(0, 0).initIfNeeded();
//	
//				PBase pb = new PBase();
//				pb.put(VConstants.name, "heal");
//				pb.put(VConstants.experience, 20);
//				pb.put(VConstants.damage, -20);
//				pb.put(VConstants.damagetype, VConstants.white);
//				pb.put(VConstants.rechargetime, 3);
//				pb.put(VConstants.abilityai, new CheckDamage(pb,50,GameUtil.getPlayerTeam(),VConstants.damage));
//				
//				// finds party member to heal and creates attack with special desc
//				// give attack a param that disables and puts on runturn
//				
//				addAbility(pb, game);			
//				game.getPBase(VConstants.allskills).getList(type).add(pb);
//				sp.getList(VConstants.abilitysetup).add(pb.getS(VConstants.name));
//				sp.put(VConstants.percenttake, 5);
//	
//				String addp = addG("addbagperson" + type, game, new PutMap(
//						bagmapname, 3, 0, VConstants.livingbeing, type,
//						VConstants.person));
//				AttachUtil.attach(AttachUtil.gamestart, addp, game);
//			}
//	
//			
//	
//			
//			addItem(game, "corpse", false);
//			String[] type = new String[] { "weapon", "shield", "glove", "armor",
//					"head" };
//			int b = 0;
//			for (String a : items) {
//				Item it = addItem(game, a, false);
//				it.put(VConstants.type, type[b]);
//				if (b > 0) {
//					it.put(VConstants.armor, 1);
//				} else {
//					it.put(VConstants.damageadded, 1);
//				}
//				b++;
//			}
//	
//			Item gold = addItem(game, "gold", false);
//			gold.setAmount(10);
//			gold.setItemValue(1);
//	
//			String score = addG(VConstants.percenttake, game, new Score(
//					VConstants.percenttake, "percentage taken by mercs"));
//			String goldscore = addG("goldscore", game, new Score(VConstants.gold,
//					"gold : ", "game.maparea.map.gold"));
//	
//			game.getHtmlOut().put(
//					VConstants.flextable,
//					list(list(list(0, 1), bagmapname), list(list(0, 2), score),
//							list(list(0, 3), goldscore)));
//			String updateptake = addG("updateptake", game, new AddTogetherPeople(
//					VConstants.percenttake));
//			AttachUtil.attach(AttachUtil.personadded, updateptake, game
//					.getMapArea().getMap());
//			AttachUtil.attach(AttachUtil.death, updateptake, game.getMapArea()
//					.getMap());
//			AttachUtil.attach(AttachUtil.gamestart, goldscore, game);
//			AttachUtil.attach(AttachUtil.gamestart, updateptake, game);
//			AttachUtil.attach(AttachUtil.personadded, score, game.getMapArea()
//					.getMap());
//			AttachUtil.attach(AttachUtil.death, score, game.getMapArea().getMap());
//			AttachUtil.attach(AttachUtil.gamestart, score, game);
//	
//			game.getMapArea().getMap().put("gold", 100);
//			VExpression vexp = new VExpression(
//					"game.maparea.map.gold = game.maparea.map.gold - object.gold");
//	
//			AttachUtil.attach(AttachUtil.clickfmd, vexp, bagmap);
//			AttachUtil.attach(AttachUtil.clickfmd, new CloneDeposit(
//					"game.maparea.map.gold"), bagmap);
//			AttachUtil.attach(AttachUtil.clickfmd, goldscore, bagmap);
//	
//			String itemBuyDisplay = addG("itembuy", game, new ItemBuyDisplay(
//					VConstants.gold, 10, "gloves"));
//			
//	
//			AttachUtil.attach(AttachUtil.death, new VExpression("game.maparea.map.gold = game.maparea.map.gold + object.goldwon"), game.getMapArea()
//					.getMap());
//			AttachUtil.attach(AttachUtil.death, goldscore, game.getMapArea()
//					.getMap());
//			
//			AttachUtil.attach(AttachUtil.exit, new SellAll(), game.getMapArea()
//					.getMap());
//	
//			AttachUtil.attach(AttachUtil.exit, itemBuyDisplay, game.getMapArea()
//					.getMap());
//	
//			
//			String itemequipDisplay=addG("itemequipdisplay", game, new ItemEquipDisplay());
//			AttachUtil.attach(AttachUtil.selectTab, new VEquals(VConstants.vparams,itemequipDisplay+"."+VConstants.personchoicedisplay, 1), game.getHtmlOut());
//			
//			
//			LevelUpDisplay lupDisplay = new LevelUpDisplay(
//					VConstants.experience);
//			String levelUpDisplay = addG("levelup", game, lupDisplay);
//	
//			AttachUtil.attach(AttachUtil.selectTab, new VEquals(VConstants.vparams,
//					levelUpDisplay + "." + VConstants.personchoicedisplay, 3), game
//					.getHtmlOut());
//	
//	
//			game.getHtmlOut().put(VConstants.tab,
//					Arrays.asList(new Object[] { itemequipDisplay,itemBuyDisplay, levelUpDisplay }));
//			String setupAbilities = addG("setupAbilities", game, new SetupAbilities());
//			AttachUtil.attach(AttachUtil.personadded, setupAbilities, game);
//			AttachUtil.attach(AttachUtil.selected, setupAbilities, lupDisplay);
//	
//			
//			
//			
//			
//			//two and one / posture
//			FullMapData map = game.getMapArea().getMap().getData(0,0);
//			
//			PTemplate enemygeneral = addTemplate(game, "enemygeneral");
//			CreateListTurns clt = new CreateListTurns(11,11, "attackmove");
//	
//			clt.add(1, "one");
//			clt.add(2, "2");
//			
//			
//			clt.add(10, "one");
//			clt.add(11, "2");
//			clt.add(14, "one");
//			clt.add(15, "2");
//			
//			
//			clt.add(20, "one");
//			clt.add(21, "2");
//			clt.add(22, "one");
//			clt.add(23, "2");
//			clt.add(27, "one");
//			clt.add(28, "2");
//			clt.add(29, "one");
//			clt.add(30, "2");
//			
//	
//			addG("createlistturns", game, clt);
//			addAction(enemygeneral, "createlistturns");
//			
//			PTemplate attackmove = addTemplate(game, "attackmove");
//			addG("moveto",game, new Move(0,0));
//			addAction(attackmove, "moveto");		
//			attackmove.setMetaoobj(new AttackEnemyMeta());
//			
//			
//			{
//				person = sp.clone();
//				person.setName("enemygeneral");
//				person.getTemplate().setRationalChild("main", "enemygeneral");
//				person.put(VConstants.team, "2");
//	
//				AttachUtil.attach(AttachUtil.gamestart, addG("addgeneral", game, new PutMap( 12, 12,
//						VConstants.livingbeing, person, VConstants.person)), game);			
//			}
//	
//			
//			//breathing
//			//create a one character map to place the face like thing
//			//do a random creation of bats that have the act together meta and the ooblist
//			
//			//on personadded put a doall on runturn
//			FullMapData map1 = game.getMapArea().getMap().getData(1,0);
//			map1.put(VConstants.ysize, 10);
//			//create an all class, put repeat as a variable on runturn
//			addG("moveback", game, new Move(5,5));
//			addG("moveforth", game, new Move(9,5));
//			AttachUtil.attach(AttachUtil.runbefore, new DoAll("butterfly", new OobList("moveback","moveforth"),10), map1);
//			AttachUtil.attach(AttachUtil.personStartOnMap,  new PutMap( 13, 7,
//					VConstants.livingbeing,"butterfly", VConstants.person) , map1);
//			AttachUtil.attach(AttachUtil.personStartOnMap,  new PutMap( 13, 8,
//					VConstants.livingbeing,"butterfly", VConstants.person) , map1);
//			AttachUtil.attach(AttachUtil.personStartOnMap,  new PutMap( 13, 9,
//					VConstants.livingbeing,"butterfly", VConstants.person) , map1);
//			
//			PBase charmap = new PBase();
//			charmap.put("w", new SimpleMD(VConstants.obstacle, "wall"));
//			AttachUtil.attach(AttachUtil.personStartOnMap,new BuildMap(charmap,"       w@" + 
//					"        w@" + 
//					"        w@" + 
//					"         w@" + 
//					"         ww@" + 
//					"        w@" + 
//					"        @" + 
//					"        w@" + 
//					"       w@" + 
//					"      w"),map1);
//	//		AttachUtil.attach(AttachUtil.personStartOnMap, new RandomTypeCreation(
//	//				VConstants.person, 3, "butterfly"),map1);
//			
//			//for the exit panel after destroyed, also have it clean up by doing a reverse of the personstartonmap puts
//			
//			//control
//			FullMapData map2 = game.getMapArea().getMap().getData(2,0);
//			map2.put(VConstants.defaultimage, "/images/grass.png");
//			AttachUtil.attach(AttachUtil.personStartOnMap, new RandomTypeCreation(
//					VConstants.person, 2, "cow"),map2);
//			//add deathyak as cow, make it string and wander aimlessly
//			
//			//mind weeds
//			//randomly create plants with appropriate toughness
//			//add timer that removes all plants once done
//			
//			AttachUtil.attach(AttachUtil.gamestart,
//					new PlaySound("k1"), game);
//			AttachUtil.attach(AttachUtil.personStartOnMap,
//					new PlaySound("k2"),map1);
//			AttachUtil.attach(AttachUtil.personStartOnMap,
//					new PlaySound("k3"),map2);
//	
//			
//			//add an exit tile onto right understanding
//			//create a debug only game for testing the exit tile before expecting it to work
//			return game;
//		}

}
