package gwt.client.map;

import java.util.Arrays;
import java.util.List;

import gwt.client.game.AttachUtil;
import gwt.client.game.VisualDamage;
import gwt.client.game.buildgame;
import gwt.client.item.Item;
import gwt.client.main.IFullMapExecute;
import gwt.client.main.Person;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.main.base.under.FoodGroup;
import gwt.client.main.base.under.Plant;
import gwt.client.main.base.under.Under;
import gwt.client.main.base.under.Water;
import gwt.client.statisticalciv.rules.DemographicRule;
import gwt.client.statisticalciv.rules.DemographicRule.Demographics;

public class HashMapData extends HMapData implements IPhysical {

	public FullMapData getParent() {
		return (FullMapData) super.getParent();
	}

	@Override
	public String toString() {
		if (size() == 0) {
			return "()";
		}
		Demographics demo = DemographicRule.getDemo(this);
		if(demo != null){
			return demo.toString();
		}
		return super.toString();
	}

	public LivingBeing getPerson() {
		return (LivingBeing) get(VConstants.livingbeing);
	}

	public SymbolicMap getSymbolicMap() {
		return getParent().getParent();
	}

	public boolean moveNextAvailableFullMapDataStartHashMap(LivingBeing person) {

		return getParent().moveNextAvailableFullMapDataStartHashMap(person);
	}

	@Override
	public void putEvenIfNotEmpty(String key, MapData value) {
		value.setParent(this);
		super.putEvenIfNotEmpty(key, value);

	}

//	public List<String> getImageList(){
//		MapData visualEffect = getMapData(VConstants.visualdamage);
//		if(visualEffect != null){
//			remove(VConstants.visualdamage);
//			return Arrays.asList(new String[]{visualEffect.getImage()});
//		}
//		List<PBase> pblist = (List<PBase>)getList(VConstants.population);
//		if(pblist == null){
//			return null;
//		}
//		PBase pb=VConstants.getRandomFromList(pblist);
//		if(pb == null){
//			return null;
//		}
//		return pb.getList(VConstants.imagelist);
//	}
	public String getImage() {
		
		
		// should really have items remove itself
		if (get(VConstants.items) != null
				&& ((Items) get(VConstants.items)).size() == 0) {
			remove(VConstants.items);
		}

		for (String a : valueOrder) {
			MapData md = getMapData(a);
			remove(VConstants.visualdamage);
			
			if (md != null) {
				//should replace visual damage with a generic "effect" label
				
				return md.getImage();
			}

		}

		return getUnderValue();
	}

	public String getUnderValue() {
		MapData mapData = getUnder();
		if (mapData == null) {
			String img = getParent().getS(VConstants.defaultimage);
			if (img != null) {
				return img;
			}

			return "/images/none.png";
		}
		return mapData.getImage();
	}

	static String[] valueOrder = new String[] {VConstants.visualdamage,
			VConstants.livingbeing, VConstants.items, VConstants.foodgroup,
			VConstants.gate, VConstants.obstacle, VConstants.under };

	public MapData getAndRemoveOneRadius(String key) {
		return getParent().getNear(key, this, 1).getAndRemove(key);

	}

	public HashMapData getOpenHashMapDataOneRadius() {
		return getParent().getNearby(this, getParent().new getempty(), 2);

	}

	public boolean isBlock() {

		return containsKey("obstacle") || containsKey(VConstants.livingbeing)
				|| getUnder() instanceof Water;
	}

	@Override
	public void iterate(IFullMapExecute ifme) {
		ifme.execute(this);
	}

	public FoodGroup getFoodGroup() {
		return (FoodGroup) get(VConstants.foodgroup);

	}

	public MapData getUnder() {
		return  (MapData) get(VConstants.under);
	}

	public LivingBeing getLivingBeing() {
		return (LivingBeing) get(VConstants.livingbeing);
	}

	public void putLivingBeing(LivingBeing lb) {
		if (lb.getParent() == null
				|| !lb.getParent().getParent().equals(this.getParent())) {
			this.getParent().addPerson(lb);
		}
		put(lb);

		AttachUtil.run(AttachUtil.personadded, lb, this);

	}

	public Items getItems() {

		Items items = (Items) get(VConstants.items);
		// if(items == null){
		// //items = new Items();
		// put(items);
		// }
		return items;
	}

	public Items getItemsCreate() {

		Items items = (Items) get(VConstants.items);
		if (items == null) {
			items = new Items();
			put(items);
		}
		return items;
	}

	public boolean hasItems() {
		Items items = (Items) get(VConstants.items);
		return items != null && items.size() != 0;
	}

	/**
	 * Deep clone
	 */
	// public HashMapData clone( FullMapData parent){
	// HashMapData hmd = new HashMapData();
	// hmd.getObjMap().put("clone", this);
	// hmd.setParent(parent);
	//		
	// for(MapData md : values()){
	//			
	// hmd.put(md.clone());
	// }
	// return hmd;
	// }

	@Override
	public Point getPosition() {
		AreaMap fmd = getParent();
		for (int x = 0; x < fmd.getXsize(); x++) {
			for (int y = 0; y < fmd.getYsize(); y++) {

				if (this.equals(fmd.getData(x, y))) {
					return new Point(x, y);
				}
			}
		}
		return null;
	}

	@Override
	public int getX() {

		return (int) getPosition().getX();
	}

	@Override
	public int getY() {

		return (int) getPosition().getY();
	}

	@Override
	public void setX(int x) {

	}

	@Override
	public void setY(int y) {

	}

	public void putAppropriate(MapData md) {
		if (md == null) {
			return;
		}

		if (md.containsKey(AttachUtil.personadded)) {
			Object o = md.remove(AttachUtil.personadded);
			getListCreate(AttachUtil.personadded).add(o);
		}
		
		if (md.containsKey(VConstants.portal)) {
			Object o = md.remove(VConstants.portal);
			put(VConstants.portal,o);
			getParent().getType(VConstants.cache).remove(VConstants.portal);
		}
		if (md instanceof LivingBeing) {
			buildgame.setItemsParents((LivingBeing) md);
			putLivingBeing((LivingBeing) md);
		} else if (md instanceof Item) {
			getItemsCreate().put(md);
		} else {
			put(md);
		}
		AttachUtil.run(AttachUtil.placed,this, md);
		//md.remove(AttachUtil.placed);
	}

	@Override
	public HashMapData clone() {

		return new HashMapData().copyProperties(this);
	}


	public void removeAppropriate(String key) {
		if (VConstants.livingbeing.equals(key)) {
			getParent().removePerson(getLivingBeing());

		}
		remove(key);
	}
}
