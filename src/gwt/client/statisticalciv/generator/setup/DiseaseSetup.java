package gwt.client.statisticalciv.generator.setup;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gwt.client.game.AttachUtil;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.shared.datamodel.VParams;

public class DiseaseSetup extends VParams{

	public DiseaseSetup() {
		// TODO Auto-generated constructor stub
	}
	
	public DiseaseSetup(int i) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Map<String, Object> map) {
		HashMapData hmdMain = (HashMapData) map.get(VConstants.main);
		HashMapData h = (HashMapData) map.get(AttachUtil.OBJECT);
		
		
		
	}
		@Override
	public PBase clone() {
		return new DiseaseSetup().copyProperties(this);
	}
}
