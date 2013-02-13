package gwt.client.item;

import java.io.Serializable;
import java.util.Arrays;



import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.ExitTile;
import gwt.client.main.VConstants;
import gwt.client.map.MapData;
import gwt.client.map.PhysicalMapData;
import gwt.shared.datamodel.VParams;

public class SimpleMD extends PhysicalMapData implements Serializable{
	

	public SimpleMD() {
		// TODO Auto-generated constructor stub
	}
	public SimpleMD(String type, String value) {
		super();
		put(VConstants.type, type);
		put(VConstants.value, value);
		
	}
	
	public SimpleMD(String type, String value,String image) {
		this(type, value);
		put(VConstants.image,image);
		
	}

	public SimpleMD(String type,String value, VParams vp) {
		this(type, value);
		put(AttachUtil.personadded, vp);
		
	}
	public SimpleMD(String type,String value, VParams vp,String image) {
		this(type, value,vp);
		put(VConstants.image,image);
		
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return (String) get(VConstants.type);
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return (String) get(VConstants.value);
	}

	@Override
	public String toString() {
		String a;
		if(getObjMap().size() == 2){
			a = "SimpleMD [type=" + getKey() + ", value=" + getValue() + "]";	
		} else {
			a = ""+getObjMap();
		}
		
			;	//+ getPosition() + " ";
//		if (getParent() != null) {
//			a += "" + getParent().getParent().getValue();
//		}
		return a;
	}

	public MapData clone() {
		SimpleMD simpleMD = new SimpleMD();
		simpleMD.copyProperties(this);
		return simpleMD;
	}

}
