package gwt.client.game;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;

public interface OCommand {
	public boolean execute(OObject oo, LivingBeing person);
}
