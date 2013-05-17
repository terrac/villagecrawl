package gwt.client.main.base;

import gwt.client.EntryPoint;
import gwt.client.astar.world.Mover;
import gwt.client.game.AlterHolder;
import gwt.client.game.AttachUtil;
import gwt.client.game.util.PUtil;
import gwt.client.item.Item;
import gwt.client.main.Economy;
import gwt.client.main.LocalTemplate;
import gwt.client.main.MapArea;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.under.Plant;
import gwt.client.map.AgeMapData;
import gwt.client.map.HMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IAge;
import gwt.client.map.IPhysical;
import gwt.client.map.Items;
import gwt.client.map.MapData;
import gwt.client.map.SymbolicMap;
import gwt.client.personality.Stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.resources.client.ImageResource;


public class LivingBeing extends AgeMapData implements Mover, IAge, IPhysical {

	public LivingBeing() {

	}

	public boolean isPerson() {
		return false;
	}

	public HashMapData getParent() {
		return (HashMapData) parent;
	}

	public MapArea getMapArea() {
		return EntryPoint.game.getMapArea();
	}

	public void setTemplate(LocalTemplate template) {
		put(VConstants.template, template);
	}

	public LivingBeing(String name, Stats stats) {
		super();
		setType(name);
		put(VConstants.stats, stats);
		// put("body", new Body(stats));
		setAge(356 * 18);

	}

	public void setType(String name) {
		put(VConstants.type, name);

	}

	public void setName(String name) {
		put(VConstants.name, name);

	}

	public LivingBeing(LivingBeing p, Stats stats) {
		this("babyanimal", new Stats());
		setAge(0);
		getTemplate().setRationalChild("aoeu", "babyanimal");
	}

	@Override
	public void setParent(MapData fullMapData, int x, int y) {

		super.setParent(fullMapData, x, y);


	}

	@Override
	public String getValue() {
		return getType();
	}

	// TreeMap prev;

	public void add(String key, String subkey, MapData md, String type) {

		if (!containsKey(key)) {
			put(key, new HMapData());
		}

		((HMapData) get(key)).put(subkey, md);
	}

	public Map<String, Item> getItemsMap() {
		return getMapArea().game.getItemMap();
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return VConstants.livingbeing;
	}

	public String getTemplateName() {
		return getTemplate().getTemplateName();
	}

	public void runAI() {
		
		getMapArea().runAI(this);

	}

	public LocalTemplate getTemplate() {

		LocalTemplate localTemplate = (LocalTemplate) get(VConstants.template);
		if (localTemplate == null) {
			localTemplate = new LocalTemplate();
			setTemplate(localTemplate);

		}
		return localTemplate;
	}

	// public List<Value> getValueList(){
	// return template.values;
	// }
	public String getName() {
		// TODO Auto-generated method stub
		return getS(VConstants.name);
	}

	public String getState(String key) {
		return getMapArea().getState(key);
	}

	public Object getVariable(String key) {

		return variables.get(key);
	}

	public Object setVariable(String key, Object value) {

		return variables.put(key, value);
	}

	Map<String, Object> variables = new HashMap<String, Object>();

	public void unsetVariable(String key) {
		variables.remove(key);

	}

	public void setParent(MapData parent) {
		if (getParent() != null) {
			getParent().remove(this.getKey());
		}
		this.parent = parent;
	}

	// @Override
	// public void iterate(IFullMapExecute ifme) {
	// ifme.execute(getBody());
	// }

	public AlterHolder getAlterHolder() {
		AlterHolder r = (AlterHolder) get(VConstants.alterholder);
		if (r == null) {
			r = new AlterHolder();
			r.setParent(this);
			put(VConstants.alterholder, r);
		}
		
		return r;
	}

	public int getYearsAge() {
		return (int) (getAge() / 356);
	}

	@Override
	public boolean age() {

		return false;
	}

	// @Override
	// public void update() {
	//		
	//		
	//		
	// List<Statistics> slist = (List<Statistics>)
	// getVariable(VConstants.update);
	// if(slist == null){
	// return;
	// }
	// for(Statistics stats : slist){
	// //multiple could be satisfied, but that sounds more like an error than
	// wanted behavior
	// PTemplate key=(PTemplate) stats.checkSatisfied(this);
	//			
	// //need to add variable update rates
	// if(key != null&&template.getRationalChild(VConstants.dominant) == null){
	//				
	// template.setRationalChild(VConstants.dominant,key);
	// break;
	// }
	// }
	//
	// }

	public void consume(Plant md) {
		md.addHealth(-30);
		// getBody().getStats().setHungry(10+getBody().getStats().getHungry());

	}

	public void consume(MapData md) {
		if (md == null) {
			return;
		}
		if (VConstants.foodgroup.equals(md.getKey())) {
			consume((Plant) md);
		}
		if (VConstants.livingbeing.equals(md.getKey())) {
			consume((LivingBeing) md);
		}

	}

	public void consume(LivingBeing md) {
		md.death();

		// getBody().getStats().setHungry(10+getBody().getStats().getHungry());

	}

	public void death() {
		if (getParent() == null) {
			return;
		}

		getParent().removeAppropriate(getKey());
		AttachUtil.run(AttachUtil.death, this, this);
		// Item md = (Item) getItemsMap().get(VConstants.corpse).clone();
		// md.setName(getName() + "corpse");
		// getParent().getItems().put(md);
		// getParent().getItems().add(getItems());

	}

	public void consume(Item it) {
		it.getParent().remove(it.getKey());
		// getBody().getStats().setHungry(10+getBody().getStats().getHungry());

	}

	GGroup ggroup;

	public GGroup getGroup() {
		return ggroup;
	}

	public void setGroup(GGroup ggroup) {
		this.ggroup = ggroup;
	}

	public void registerDecision(int priority, String type, String group,
			OObject... objects) {

		List<LivingBeing> list = (List<LivingBeing>) getGroup().get(group);
		if (list == null) {
			throw new IllegalArgumentException();
		}

		for (OObject oo : objects) {
			// if(oo instanceof IDecision){
			// OutputDirector.mpane.registerDecision(this,priority,type,(IDecision)oo);
			// }
		}
		getGroup().askAll(objects, this, list);

	}

	@Override
	public Point getPosition() {

		return getParent().getPosition();
	}

	@Override
	public int getX() {

		return getPosition().getX();
	}

	@Override
	public int getY() {

		return getPosition().getY();
	}

	@Override
	public void setX(int x) {

	}

	@Override
	public void setY(int y) {

	}

	@Override
	public LivingBeing clone() {
		// TODO Auto-generated method stub
		return (LivingBeing) super.clone();
	}

	// protected LivingBeing clone(LivingBeing toClone) {
	// LivingBeing person = (LivingBeing) toClone.copyProperties(this);
	// // person.put(VConstants.template, toClone.getTemplate().clone());
	// // person.put(VConstants.stats, toClone.getStats().clone());
	// // person.put(VConstants.items, toClone.getItems().clone());
	// //person.getTemplate().getRationalMap().getObjMap().putAll(this.getTemplate().getRationalMap().getObjMap());
	// //person.put("body", new Body(new Stats()));
	// // person.put(VConstants.stats,new Stats());
	// // person.getStats().copyProperties(this.getStats());
	// //
	// // person.put(VConstants.items, new Items());
	// // for(MapData md :toClone.getItems().values()){
	// // person.getItems().put(md.clone());
	// // }
	// return person;
	// }
	{
		Items it = getItems();

		Stats st = getStats();
		if (st == null) {
			put(VConstants.stats, new Stats());
		}
	}

	public Items getItems() {
		Items it = (Items) get(VConstants.items);
//		if (it == null) {
//			it = new Items();
//			put(VConstants.items, it);
//
//		}
		return it;
	}
	
	public Items getItemsCreate() {
		Items it = (Items) get(VConstants.items);
		if (it == null) {
			it = new Items();
			put(VConstants.items, it);

		}
		return it;
	}

	public String getAvatar(){
		String object =  getImageString(getS(VConstants.avatar));
		if(object == null){
			object = getImage();
		}
		return object;
	}
	
	public String getEquipmentImage() {
		return "local"+getId();
	}
	
	
	
	
	
	
	public Stats getStats() {
		return (Stats) get(VConstants.stats);
	}

	public PBase getAttributes() {
		return getPBase(VConstants.attributes);
	}

	@Override
	public String toString() {

		return getTemplate().getRationalMap().getObjMap() + "\n\n"
				+"current:"+ getTemplate().getCurrent() + "\n\n"
				+"pop:"+ getType(VConstants.population) + "\n\n"
				//+"currentTemplate"+ getTemplate().getTemplateName() + "\n\n"
				//+ PUtil.pToString(VConstants.attributes, getAttributes())
				//+ "\n\n" + getStats() 
				//+"alter:"+getAlterHolder()
				+"id:"+getId()+"\n"
				//+ "\n\n money:"+(get(VConstants.money))+"\n\n"
				+ getItems() + "\n\nteam:" + getTeam();
	}

	public String getType() {

		return getS(VConstants.type);
	}

	public String getTeam() {

		return getS(VConstants.team);
	}

	public void setTeam(String claname) {
		put(VConstants.team, claname);
	}

	public void addItems(MapData clone) {
		if(getItems() == null){
			put(VConstants.items,new Items());
		}
		getItems().put(clone);
	}
	public void toggleDisabled(String ability){
		PBase type = getType(VConstants.disabled);
		boolean disabled=type.getB(ability);
		type.put(ability, !disabled);
	}
	public boolean getDisabled(String ability){
		PBase type = getType(VConstants.disabled);
		return type.getB(ability); 
	}
	public Economy getEconomy(){
		Economy ec = (Economy) get(VConstants.economy);
//		if(ec == null){
//			ec = new Economy();
//			put(VConstants.economy,ec);
//		}
		return ec;
	}

	public String getId() {
		if (get(VConstants.idnum) == null) {

			put(VConstants.idnum, EntryPoint.game.getNextId());
		}
		return (String) get(VConstants.idnum);
	}

	public String getBigImage() {
		return "/images/" + getType().split(" ")[0]
				+ "-big.png";
	}

	public void setTemplate(String value) {
		getTemplate().getRationalMap().put(VConstants.main,value);
	}

	public PBase getPopulation() {
		// TODO Auto-generated method stub
		return getPBase(VConstants.population);
	}
	
}
