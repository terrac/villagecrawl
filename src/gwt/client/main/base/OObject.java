package gwt.client.main.base;
import gwt.client.edit.params.IParams;
import gwt.client.main.PTemplate;
import gwt.client.main.Returnable;
import gwt.client.main.VConstants;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.IPhysical;
import gwt.client.map.MapData;
import gwt.client.output.OutputDirector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;


public abstract class OObject extends PBase implements Serializable,ActionObject,IParams{


	public abstract Returnable execute(FullMapData fullMapData, LivingBeing person);

	protected ActionObject parent;
	public OObject getOParent(){
		if(parent instanceof OObject){
			return (OObject) parent;
		}
		return null;
	}
	
	public OObject getTopOParent(){
		
		
		if(parent instanceof OObject){
			return ((OObject) parent).getTopOParent();
		}
		if(parent == null){
			return this;
		}
		if(parent instanceof PTemplate){
			return this;
		}
		return null;
	}
	public PTemplate getTParent(){
		if(parent instanceof PTemplate){
			return  (PTemplate) parent;
		}
		//eventually might want to move getParent to actionObject
		if(parent != null){
			return ((OObject)parent).getTParent();
		}
		return null;
	}
	public void setParent(ActionObject parent){
		if(this == parent){
			System.out.println();
		}
		//
		if(getOParent() != null){
			getOParent().list.remove(this);
		}
		this.parent = parent;
		
		
	}
	
	public void addChild(OObject oobject){
		oobject.setParent(this);
		list.add(oobject);
	}
	public boolean hasChildren(){
		return list.size() != 0;
	}
	transient List<OObject> list = new ArrayList<OObject>();
	
	
	
	public OObject clone()  {
		
		throw new IllegalArgumentException();
		
		
	}
	public void firePostEvent(PTemplate template, FullMapData fullMapData,
			LivingBeing person, Returnable ret) {
		
		if(!hasChildren()){
			template.postExecute(fullMapData, person, this,ret);
			
			OObject parent2 = getOParent();
			if(parent2 != null){
				setParent(null);
				parent2.firePostEvent(template, fullMapData, person,ret);
			}
		}
	}
	public boolean instanceOf(Class class1) {
		if(getClass().equals(class1)){
			return true;
		}
		if(getOParent() != null){
			return getOParent().instanceOf(class1);
		} else {
			return false;
		}
	}
	public static void addToList(LivingBeing person, OObject makeItem) {
		if(makeItem == null){
			throw new IllegalArgumentException();
		}
		person.getTemplate().pending.add(makeItem);
	}
	
	public List<String> getNeeds(){
		return null;
	}
	
	public void oInit(LivingBeing person){
		if(null !=get(VConstants.init)){
			return;
		}
		put(VConstants.init, true);
		
		init(person);
	}
	protected void init(LivingBeing person) {
		// TODO Auto-generated method stub
		
	}
	
	public Parameters getParameterTypes(){return null;}
	public void setParameters(Object ...pars){}
	public ActionObject getParent() {

		return parent;
	}
	
	
	
	public String getDisplayImage(){
		return "/images/redsquare.png";
	}
	public IPhysical getDisplayPosition(){
		IPhysical iPhysical = (IPhysical) get(VConstants.display);
		
		return iPhysical;
	}
	

//	Map<String,Object> objMap = new HashMap<String, Object>();
//	public Map<String, Object> getObjMap() {
//		return objMap;
//	}
//	public void put(String key,Object object){
//		//for multiple variables just use bottom.rotting = 50 for example.  This can be optimized later.		
//		
//		objMap.put(key,object);
//	}
//	public Object get(String key){
//		
//		return objMap.get(key);
//	}
//	public boolean containsKey(String key){
//		
//		return objMap.containsKey(key);
//	}
//	public Object remove(String key) {
//		
//		return objMap.remove(key);
//	}

}