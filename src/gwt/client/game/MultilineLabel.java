package gwt.client.game;

import com.google.gwt.user.client.ui.HTML;

public class MultilineLabel extends HTML{

	public MultilineLabel() {
		super();
	}

	
	@Override
	public void setHTML(String html) {
		html = html.replace("\n", "<br>");
		super.setHTML(html);
	}
	
}
