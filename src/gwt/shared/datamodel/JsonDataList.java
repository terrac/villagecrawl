package gwt.shared.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDataList implements IClientObject{
	public JsonDataList() {
		// TODO Auto-generated constructor stub
	}
	public List<String> jsonData = new ArrayList<String>();
	public String currentJson;
	public Map<String,String> jsonCache = new HashMap<String, String>();

	public JsonDataList(List<String> jsonData) {
		super();
		this.jsonData = jsonData;
	}
}
