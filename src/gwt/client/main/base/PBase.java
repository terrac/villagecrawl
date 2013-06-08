package gwt.client.main.base;
import gwt.client.game.generator.MarkerInterface;
import gwt.client.game.util.PUtil;
import gwt.client.main.VConstants;
import gwt.client.map.MapData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PBase implements MarkerInterface {

	public PBase() {
		
	}
	public PBase(Map map) {
		objMap = map;
	}
	public PBase(Object ...objects){
		setPBase(this,objects);
	}
	public static void setPBase(PBase pb,Object... objects) {
		for(int a = 0 ; a < objects.length;a+=2){
			pb.put((String) objects[a], objects[a+1]);
		}
	}
	
	private Map<String, Object> objMap = new HashMap<String, Object>();

	public void setObjMap(Map<String, Object> objMap) {
		this.objMap = objMap;
	}

	public Map<String, Object> getObjMap() {
		return objMap;
	}

	public void put(String key, Object object) {
		// for multiple variables just use bottom.rotting = 50 for example. This
		// can be optimized later.

		objMap.put(key, object);
	}

	public Object get(String key) {

		return objMap.get(key);
	}

	public PBase getPBase(String key) {

		return (PBase) objMap.get(key);
	}

	public int getInt(String key) {
		Integer a = (Integer) objMap.get(key);
		if (a == null) {
			a = 0;
		}
		return a;
	}

	public boolean getB(String key) {
		Boolean b = (Boolean) objMap.get(key);
		if (b == null) {
			return false;
		}
		return b;
	}

	public String getS(String key) {
		return (String) objMap.get(key);
	}

	public MapData getMapData(String key) {
		return (MapData) objMap.get(key);
	}

	public boolean containsKey(String key) {

		return objMap.containsKey(key);
	}

	public Object remove(String key) {

		return objMap.remove(key);
	}

	public String[] editConfiguration() {
		return null;
	}

	public String[] editConfigurationTypes() {
		return null;
	}

	public <T extends PBase> T copyDeepProperties(T item) {
		// System.out.println(this.getClass().getName());
		this.objMap.putAll(item.objMap);
		for (Entry<String, Object> ent : this.objMap.entrySet()) {
			if (ent.getValue() instanceof PBase) {
				ent.setValue(((PBase) ent.getValue()).clone());
			}
			if (ent.getValue() instanceof List) {
				ent.setValue(new ArrayList((List)ent.getValue()));
			}
			
		}
		return (T) this;
	}

	public <T extends PBase> T copyProperties(T item) {
		// System.out.println(this.getClass().getName());
		this.objMap.putAll(item.objMap);

		return (T) this;
	}

	public List getListCreate(String preset) {
		if (get(preset) == null) {
			put(preset, new ArrayList());
		}
		return (List) get(preset);
	}
	public List getList(String preset) {
		
		return (List) get(preset);
	}

	public String export() {
		StringBuffer ret = new StringBuffer();
		export(ret);
		return ret.toString();

	}

	/**
	 * Exports the current object into a json format
	 * @param sb
	 */
	private void export(StringBuffer sb) {

	    sb.append("{");
	    boolean first = false;
	    sb.append("\"classname\": \"" + this.getClass().getName() + "\"");
	    for (String key : objMap.keySet()) {
	      if (first) {
	        first = false;
	      } else {
	        sb.append(", ");
	      }
  
	      sb.append("\""+key+"\"");
	      
	      
	      sb.append(":");
	      fexport(sb,get(key));
	    }
	    sb.append("}");
		
		
	}

	private void fexport(StringBuffer ret, Object value) {

		if (value == null) {
			ret.append("null");
		} else if (value instanceof PBase) {
			((PBase) value).export(ret);

		} else if (value instanceof HashMap) {
			PBase pb = new PBase();
			pb.objMap = (Map<String, Object>) value;
			pb.export(ret);
		} else if (value instanceof List) {
			listToString(ret, (List) value);
		} else if (value instanceof Object[]) {
			
			// necessary due to bug found in GWT
			// Doing an Array.asList() was returning incorrect results when accessed
			List l = new ArrayList();
			for (Object o : (Object[]) value) {
				l.add(o);
			}

			listToString(ret, l);
		}

		else if (value instanceof Integer) {
			ret.append(value);
		} else if (value instanceof Double) {
			ret.append(value);
		} else if (value instanceof Boolean) {
			ret.append(value);
		} else {

			
			ret.append("\"" + value + "\"");

		}

	}

	/**
	 * Creates a json array list
	 * @param sb StringBuffer
	 * @param list list to turn into a string
	 */
	private void listToString(StringBuffer sb, List<Object> list) {

		sb.append("[");
		for (int i = 0, c = list.size(); i < c; i++) {
			if (i > 0) {
				sb.append(",");
			}
			fexport(sb, list.get(i));
		}
		sb.append("]");

	}

	public PBase clone() {
		// Reflection create = (Reflection) GWT.create( Reflection.class );
		// return create.instantiate( this.getClass() ).copyProperties(this);
		return new PBase().copyProperties(this);
	}

	@Override
	public String toString() {

		return  PUtil.pToStringLimited("PBase", this);	}

	public PBase getType(String person) {
		PBase pBase = getPBase(person);
		if (pBase == null) {
			pBase = new PBase();
			put(person, pBase);
		}
		return pBase;
	}
	public void increment(String incname) {
		int in=getInt(incname);
		in++;
		put(incname,in);
		// TODO Auto-generated method stub
		
	}
	public void add(String incname,int amount) {
		int in=getInt(incname);
		in+= amount;
		put(incname,in);
		// TODO Auto-generated method stub
		
	}
//	static StringBuffer escape(String s) {
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < s.length(); i++) {
//			char ch = s.charAt(i);
//			switch (ch) {
//			case '"':
//				sb.append("\\\"");
//				break;
//			case '\\':
//				sb.append("\\\\");
//				break;
//			case '\b':
//				sb.append("\\b");
//				break;
//			case '\f':
//				sb.append("\\f");
//				break;
//			case '\n':
//				sb.append("\\n");
//				break;
//			case '\r':
//				sb.append("\\r");
//				break;
//			case '\t':
//				sb.append("\\t");
//				break;
//			case '/':
//				sb.append("\\/");
//				break;
//			default:
//				// Reference: http://www.unicode.org/versions/Unicode5.1.0/
//				if ((ch >= '\u0000' && ch <= '\u001F')
//						|| (ch >= '\u007F' && ch <= '\u009F')
//						|| (ch >= '\u2000' && ch <= '\u20FF')) {
//					String ss = Integer.toHexString(ch);
//					sb.append("\\u");
//					for (int k = 0; k < 4 - ss.length(); k++) {
//						sb.append('0');
//					}
//					sb.append(ss.toUpperCase());
//				} else {
//					sb.append(ch);
//				}
//			}
//		}// for
//		return sb;
//	}
	public static void increment(PBase type,String ts, Integer value) {
		Integer base=type.getInt(ts);
		type.put(ts, base+value);
	}
	public static void increment(PBase type,String ts, Double value) {
		Double base=type.getDouble(ts);
		
		type.put(ts, base+value);
	}
	public static int getDefaultInt(PBase pb, String name,
			int i) {
		Integer n = (Integer) pb.get(name);
		if(n == null){
			return i;
		}
		
		return n;
	}
	public static PBase getDefaultPBase(PBase obj, String string,
			PBase pb) {
		PBase t = obj.getPBase(string);
		if(t != null){
			return t;
		}
		return pb;
	}
	public static Double getDefaultDouble(PBase obj, String string,
			double d) {
		Double t = (Double) obj.get(string);
		if(t != null){
			return t;
		}
		return d;
	}
	public static boolean getDefaultBoolean(PBase obj, String string,boolean def) {
		Boolean t = (Boolean) obj.get(string);
		if(t != null){
			return t;
		}
		
		return def;
	}
	public static void decrement(PBase type,String ts, Integer value) {
		Integer base=type.getInt(ts);
		type.put(ts, base-value);
	}
	public static void decrement(PBase type,String ts, Double value) {
		Double base=type.getDouble(ts);
		type.put(ts, base-value);
	}
	public static double getDouble(PBase pop, String size) {
		Object object = pop.get(size);
		if(object instanceof Integer){
			object = new Double((Integer)object);
		}
		Double doub = (Double) object;
		if(doub == null){
			return 0;
		}
		return doub;
	}
	public double getDouble(String size) {
		return PBase.getDouble(this, size);
	}
	

//	@Override
//	public boolean equals(Object obj) {
//		if(this == obj){
//			return true;
//		}
//		if(!(obj instanceof PBase)){
//			return false;
//		}
//		PBase pb = (PBase) obj;
//		if(pb.getObjMap().size() != pb.getObjMap().size()){
//			return false;
//		}
//		for(Entry<String,Object> ent :getObjMap().entrySet()){
//			Object o=pb.get(ent.getKey());
//	if(o == null||!o.equals(ent.getValue())){
//				return false;
//			}
//		}
//		return true;
//	}
}
