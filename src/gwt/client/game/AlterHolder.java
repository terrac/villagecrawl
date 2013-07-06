package gwt.client.game;

import java.util.List;

import gwt.client.EntryPoint;
import gwt.client.game.util.PUtil;
import gwt.client.item.Item;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.HMapData;
import gwt.client.map.MapData;

public class AlterHolder extends MapData{

	
	@Override
	public void put(String key, Object value) {
		if(containsKey(key)){
			return;
		}
		
		
		super.put(key, value);
		alter((PBase)value,true);
		
		
	}
	
	public void put(String key, MapData value) {
		if(containsKey(key)){
			return;
		}
		if(value != null&&value.parent == null){
			value.setParent(this);
		}
		super.put(key, value);
		alter((PBase)value,true);
	}


	@Override
	public Object remove(String key) {
		 
		PBase remove = (PBase) super.remove(key);
		
		alter(remove,false);
		return remove;
	}
	
	@Override
	public LivingBeing getParent() {
		// TODO Auto-generated method stub
		return (LivingBeing) super.getParent();
	}
	protected void alter(PBase value, boolean b) {
		if(value == null){
			return;
		}
		getParent().remove(VConstants.imagecache);
		ApplyDamage ad = (ApplyDamage) getParent().getMapArea().game
		.get(VConstants.applydamage);
		List<String> types =ad.getListCreate(VConstants.altertypes);
		for(String ty : types){
			if(VConstants.summon.equals(ty)&&value.getB(ty)){
				if(!b){
					getParent().death();
				}
				continue;
			}
			
			int itemProp = value.getInt(ty);
			if(itemProp == 0){
				continue;
			}
			int prev=getParent().getStats().getInt(ty);
			if(b){
				prev += itemProp;
			} else {
				prev -= itemProp;
			}
			
			getParent().getStats().put(ty, prev);
		}
	}	
	
	
	public void remove(Item md) {
		
		remove((String) md.get(VConstants.type));
	}
	@Override
	public String toString() {
		return PUtil.pToString(getKey(), this);
	}
	@Override
	public MapData clone() {
		return new AlterHolder().copyDeepProperties(this);
	}
}
