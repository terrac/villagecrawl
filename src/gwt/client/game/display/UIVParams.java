package gwt.client.game.display;

import java.util.List;

import gwt.client.main.base.PBase;
import gwt.shared.datamodel.VParams;

import com.google.gwt.user.client.ui.Widget;

public abstract class UIVParams extends VParams{
	public  abstract Widget getWidget();
	
	public void update(){}
	
	public void init(){}
	
	public Widget getWidgetAndInit(){
		init();
		return getWidget();
	}
	
	@Override
	public UIVParams clone() {

		return (UIVParams) super.clone();
	}
	
	public void setList(List l){};
	public void setObject(Object o){};
}
