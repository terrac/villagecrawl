package gwt.client.game.display;

import gwt.client.EntryPoint;
import gwt.client.edit.BagMap;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;
import gwt.client.output.HtmlOut;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;

public class PersonChoiceDisplay extends UIVParams {

	public PersonChoiceDisplay() {

	}

	public BagMap getBm() {
		return (BagMap) get(VConstants.bagmap);
	}

	int peoplesize;

	@Override
	public void execute(final Map<String, Object> map) {

		List<LivingBeing> people2 = EntryPoint.game.getHtmlOut().currentFMD.people;

		List<LivingBeing> people = GameUtil.getPlayerTeam(people2);

		if (!(peoplesize == people.size() || people.size() == 0)) {
			peoplesize = people.size();
			getBm().getBagMap().setYsize(people.size());
			getBm().getBagMap().init();
			getBm().bagCanvas.setCoordinateSpaceHeight(getBm().getSize()
					* people.size());
			int y = 0;
			for (LivingBeing lb : people) {
				SimpleMD pb = new SimpleMD();
				Object object = lb.getImage();
				pb.put(VConstants.image, object);
				pb.put(VConstants.livingbeing, lb);
				getBm().getBagMap().setData(0, y, pb);
				// call the first person on the list for the level up display
				if (y == 0) {
					AttachUtil.run(AttachUtil.clickfmd, lb, getBm());
				}
				y++;
			}
 
		} 
		else if(people.size() > 0){
			AttachUtil.run(AttachUtil.clickfmd, people.get(0), getBm());
		}
		
		getBm().update();

	}

	@Override
	public Widget getWidget() {

		return getBm().getWidget();
	}

	@Override
	public UIVParams clone() {

		return new PersonChoiceDisplay().copyProperties(this);
	}

	@Override
	public void init() {
		put(VConstants.bagmap, new BagMap(1, 3, HtmlOut.imagesize * 2));
		getBm().init();

	}
	@Override
	public void update() {
		getBm().update();
	}
}
