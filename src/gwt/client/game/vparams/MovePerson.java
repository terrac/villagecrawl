package gwt.client.game.vparams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.game.util.PointBase;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.PBase;
import gwt.client.map.Direction;
import gwt.client.map.FullMapData;
import gwt.client.map.HashMapData;
import gwt.client.map.MapData;
import gwt.client.map.runners.GetForNearby;
import gwt.client.output.OutputDirector;
import gwt.shared.datamodel.VParams;

public class MovePerson extends GoTo {

	public MovePerson() {
	}

	public MovePerson(int i, int j) {
		put(VConstants.xsymbolic, i);
		put(VConstants.ysymbolic, j);
	}
	public MovePerson(Point pos){
		this(pos.x,pos.y);
	}

	public MovePerson(int i, int j, VParams vp) {
		this(i, j);

		AttachUtil.attach(VConstants.list, vp, this);
	}

	public MovePerson(boolean b) {
		put(VConstants.destroy, b);
	}

	@Override
	public void execute(Map<String, Object> map) {

		LivingBeing lb = (LivingBeing) map.get(AttachUtil.OBJECT);
		if(getB(VConstants.destroy)){
			lb.death();
		}
		if(lb.getTemplate().getCurrent() != null&&VConstants.not.equals(lb.getTemplate().getCurrent().getS(VConstants.portal))){
			return;
		}
		HashMapData hmd = lb.getParent();
		if(hmd == null){
			return;
		}
		Point pos= Point.getPoint( (PointBase) hmd.getPBase(VConstants.portal));
		FullMapData fmd = hmd.getParent().getParent().getData(pos);

		HashMapData hgo=fmd.getAllKeyValue(VConstants.portal, new PointBase(lb.getParent().getParent().getPosition()));
		if(hgo == null){
			hgo=fmd.getData(0,0);
		}
		hgo=fmd.getNearestEmptyNotEvent(hgo);
		hgo.putLivingBeing(lb);			
		
//		List l = new ArrayList();
//		l.add(lb);
//		setInitialPos(fmd, l);
	}


	@Override
	public PBase clone() {

		return new MovePerson().copyProperties(this);
	}
}
