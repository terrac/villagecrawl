package gwt.client.main;

import gwt.client.main.base.BaseMoveMap;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.FullMapData;

public class MoveRandomFullMapData extends BaseMoveMap{
	
	public MoveRandomFullMapData(String descr) {
		super(1,descr);
	}
	
	public MoveRandomFullMapData(int scope,String descr) {
		super(scope,descr);
	}
	public MoveRandomFullMapData() {
	
	}
@Override
protected FullMapData findNewMD(FullMapData fullMapData, LivingBeing person) {
	int count = 0;
	FullMapData md = null;
	while(md == null&&count < 10){
		int xr = VConstants.getRandom().nextInt(getRadius()*2 +1);
		int yr = VConstants.getRandom().nextInt(getRadius()*2+1);
		xr -= getRadius();
		yr -= getRadius();
		count++;
		if(xr == 0&& yr== 0){
			continue;
		}
		Point p = fullMapData.getPosition();
		if(p == null){
			continue;
		}
		md =fullMapData.getParent().getData(p.x + xr, p.y + yr);
		if(md != null&&md.getXsize() == 1 && md.getYsize()  == 1){
			md = null;
		}
		
	}
	return md;
}


	
	@Override
	public OObject clone() {
		
		return (OObject) new MoveRandomFullMapData().copyProperties(this);
	}
}
