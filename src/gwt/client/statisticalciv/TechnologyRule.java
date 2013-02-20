package gwt.client.statisticalciv;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.statisticalciv.generator.setup.DiseaseSetup;
import gwt.client.statisticalciv.generator.setup.FishingSetup;
import gwt.client.statisticalciv.generator.setup.PromoteTechs;
import gwt.client.statisticalciv.generator.setup.TradingSetup;
import gwt.shared.datamodel.VParams;

public class TechnologyRule extends VParams{

	public TechnologyRule() {
		// TODO Auto-generated constructor stub
	}
	PBase pb= new PBase();
	{
		if(false){
		//basically tech starts out with the shaman
		//the shaman has various things that they teach
		//curiosity/xenophobia(promotes health)
		//homosexuality,feminism,transgender support, (starts off as support of differences, liberalism)
		//religious offerings (reduces population growth slightly, increases health.  The explanation is that if they knew they didn't have enough food for another child they would promote religious offerings to stave off starvation related conditions
		//revenge killings,tribal dispute resolutions (favor family in disputes)
		//fishing,acorn washing, cooking
		//tribal movement
		//animal skin/green skirt
		
		//on cooking fully leveled the society then can start agricultural settling and the next tech tree
		//then the tech splits into priest/warrior and each one of them have things to teach
		//temple prostitution, bread, beer,masonry, pottery,stonehenge,
		//hospitality/xenophobia/informal justice
		//agricultural settling->farms
		//disease
		
		//then priest splits into priest/merchant
		//liberalism/fundamentalism(comes from disease, progresses to banning behaviors that negatively affect population growth)/xenophobia/ formal written justice
		PBase shaman=pb.getType("shaman");
		List<PBase> shamanlist=shaman.getListCreate(VConstants.list);
		PBase liberalism=new PBase(VConstants.name,"liberalism");
		liberalism.getListCreate(VConstants.disabled).add("fundamentalism");
		PBase fundamentalism=new PBase(VConstants.name,"fundamentalism");
		fundamentalism.getListCreate(VConstants.disabled).add("liberalism");
		fundamentalism.put(VConstants.next, new PBase(VConstants.name,"natural order of things"));
		
		PBase revenge=new PBase(VConstants.name,"revenge killings");
		revenge.put(VConstants.next, new PBase(VConstants.name,"eye for an eye"));
	
		//http://en.wikipedia.org/wiki/Shinto
		PBase natureSpiritualism=new PBase(VConstants.name,"animism");
		addNext("animism","shrines","offerings","purification rites","afterlife","talismans","dancing","demons","gods","divination","spirit possession","shamanistic healing","ceremonies");
		addNext("purification rites","water purification","taboo");
		addNext("shrines","home shrines","stone henge","door shrine");
		shaman.put(VConstants.list, Arrays.asList(new PBase[]{liberalism,fundamentalism,revenge,natureSpiritualism}));
		
		addNext("tools","knapped stone tools","bone","wood");
		addNext("weapons","hand axe","bow","spear");
		addNext("clothing","fiber","leather");
		addNext("fire","cooking");
		addNext("fishing","spear fishing","rafts");
		// (animal husbandry to start)addNext("domestication","dogs","cats","animal husbandry");
		addNext("animal husbandry","aurochs","sheep","goats","disease");
		addNext("disease","disease resistance");
		addNext("trading");
		addNext("communal decision making");
		addNext("no division of labor");
		addNext("philosophy","liberalism","fundamentalism","pacifism","punctuality","sociability","passion","logic","honor","corruption");
		addNext("goals","technology","military","commerce");
		addNext("justice","revenge killings","an eye for an eye","forgiveness");
		
		//these control the chance of conflict
		addAttributes("revenge killings","Conflict",5,VConstants.description,"Thats the chicago way");
		addAttributes("an eye for an eye","Conflict",2,VConstants.description,"The law of porportional retaliation");
		addAttributes("forgiveness","Conflict",0,VConstants.description,"Do unto others as you would have them do unto you");
		
		addAttributes("spear",VConstants.strength,3,VConstants.description,"A strong weapon");
		addAttributes("hand axe",VConstants.strength,1,VConstants.description,"A weapon");
		addAttributes("bow",VConstants.strength,4,VConstants.description,"Spears can be thrown,but bows can shoot further");
		
		addAttributes("knapped stone tools",VConstants.growth,1,VConstants.description,"Tools are useful");
		
		addAttributes("fiber",VConstants.armor,1,VConstants.description,"helps");
		addAttributes("leather armor",VConstants.armor,2,VConstants.description,"");
		
		
		addAttributes("sheep",VConstants.growth,1);
		addAttributes("aurochs",VConstants.growth,1);
		addAttributes("goats",VConstants.growth,1);
		addAttributes("disease",new DiseaseSetup(1));//big occasional losses, small occasional losses
		addAttributes("disease resistance","diseaseresistance",1);//reduces the losses
		addAttributes("shrines",VConstants.growth,1);
		addAttributes("offerings",VConstants.growth,1);
		addAttributes("purification rites",VConstants.growth,1);
		addAttributes("talismans",VConstants.growth,1);
		addAttributes("dancing",VConstants.growth,1);
		addAttributes("trading",VConstants.growth,1, new TradingSetup());
		addAttributes("fishing",VConstants.growth,1, new FishingSetup());
		
		
		addAttributes("fundamentalism",new PromoteTechs("revenge killings","eye for an eye","purification rites","disease resistance","anything with growth"));
		addAttributes("liberalism",new PromoteTechs("forgiveness","dancing","disease"));
		
		//make the default to be growth 1 or maybe .5
		//have a default "would you like to look more into"
	
		addQuestion("revenge killings","Bob says that his father was killed by the outsiders.  Should we take revenge?");
		addQuestion("fundamentalism","Bob is worried about this new problem.  He suggests making a rule to stop it.  Should we?");
		addQuestion("liberalism","Bob thinks that we need to be more flexible to deal with this new problem.  Do you agree?");
		}
		}
	
	//population health (reduces according to size, low pop health = no growth)
	//population combat ability
	//
	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);
		
		
		if(everyIteration(map,10)){
			//run through every population
			//start with 20 totalsize
			//run through all techs and add that to the total size
		}
		//run through every population
		//increment based on size of population
		//and whether or not another population in the tile has that technology
		
	}
	private boolean everyIteration(Map<String, Object> map, int i) {
		int count = (Integer) map.get("count");
		return count % i == 0;
	}
	private void addQuestion(String string, String string2) {
		// TODO Auto-generated method stub
		
	}
	private void addAttributes(String string, Object ... objects) {
		// TODO Auto-generated method stub
		
	}
	private void addNextHeirarchy(String prev,String from,String ... sA) {
		PBase pbt = getByName(pb,prev);
		PBase shaman=pbt.getType(from);
		List<PBase> shamanlist=shaman.getListCreate(VConstants.list);
		for(String a : sA){
			PBase liberalism=new PBase(VConstants.name,a);
			shamanlist.add(liberalism);	
		}
	}
		
	private PBase getByName(PBase pb2, String prev) {
		String name=pb2.getS(VConstants.name);
		if(prev.equals(name)){
			return pb2;
		}
		List<PBase> n = pb2.getList(VConstants.next);
		if(n == null){
			return null;
		}
		for(PBase p : n){
			PBase pb=getByName(p, prev);
			if(pb != null){
				return pb;
			}
		}
		return null;
	}
	private void addNext(String from,String ... sA) {
		addNextHeirarchy("shaman",from, sA);
		
		
	}
	@Override
	public PBase clone() {
		return new TechnologyRule().copyProperties(this);
	}
}
