package gwt.client.output;

import gwt.client.main.base.LivingBeing;

import java.util.List;

public interface IDecision {
	public List<Object> getChoices(LivingBeing person);
	public void setChoice(Object o, LivingBeing person);
}
