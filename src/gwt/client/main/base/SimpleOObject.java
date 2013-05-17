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


public abstract class SimpleOObject extends OObject{


	@Override
	public OObject clone() {
		return this;
	}
}