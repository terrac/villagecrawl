/**
 * 
 */
package gwt.client.output.html;

import gwt.client.EntryPoint;
import gwt.client.main.Game;
import gwt.client.output.HtmlOut;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

class StartEdit implements ClickHandler {
	/**
	 * 
	 */
	private final HtmlOut htmlOut;
	String type;

	public StartEdit(HtmlOut htmlOut, String type) {
		super();
		this.htmlOut = htmlOut;
		this.type = type;
	}

	@Override
	public void onClick(ClickEvent event) {
		Game game = EntryPoint.game;
		game.pause();

		this.htmlOut.editPage.init(type);

	}
}