package gwt.client.game.display;

import gwt.client.EntryPoint;
import gwt.client.game.AttachUtil;
import gwt.client.game.GameUtil;
import gwt.client.main.Move;
import gwt.client.main.Point;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class ExitDisplay extends UIVParams {

	Button button;

	public ExitDisplay() {

	}
	
	

	@Override
	public Widget getWidget() {

		return button;
	}

	@Override
	public void init() {
		button = new Button();
		button.setText("exittonextlevel");
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AttachUtil.run(AttachUtil.exit,
						EntryPoint.game.getHtmlOut().currentFMD,
						EntryPoint.game.getHtmlOut().currentFMD.getParent());

				if (EntryPoint.game.getDebug()) {
					for (LivingBeing lb : EntryPoint.game.getHtmlOut().currentFMD.people.toArray(new LivingBeing[0])) {
						if(GameUtil.getPlayerTeam().equals(lb.getTeam())){
							continue;
						}
						lb.death();
					}
				}

				// eventually should just try all points, and not go to any that
				// have the property "disable" (it could also disable properties
				for (LivingBeing lb : GameUtil.getPlayerTeam(EntryPoint.game
						.getHtmlOut().currentFMD.people)) {
					int maxhealth = lb.getStats().getInt(VConstants.maxhealth);
					if (maxhealth == 0) {
						maxhealth = lb.getMapArea()
								.getInt(VConstants.maxhealth);
					}
					lb.getStats().put(VConstants.health, maxhealth);
					Point p = lb.getParent().getParent().getPosition();
					p.x++;

					lb.getTemplate().push(
							new Move(lb.getParent().getParent().getParent()
									.getData(p), "exit"));
				}
			}
		});

	}
}
