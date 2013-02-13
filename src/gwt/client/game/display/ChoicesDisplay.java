package gwt.client.game.display;

import com.google.gwt.user.client.ui.Widget;

public class ChoicesDisplay extends UIVParams{

	@Override
	public Widget getWidget() {
		// TODO Auto-generated method stub
		return null;
	}
	


			


//	public List<Choice> choiceStack = new ArrayList<Choice>();
//	public void registerDecision(LivingBeing person,int priority, String type, IDecision oo) {
//		//List<Object> o=oo.getChoices(person);
//		if(true){
//			return;
//		}
//		Game game = person.getParent().getParent().getParent().getParent().game;
//		if(!game.isPaused()){
//			game.pauseToggle();
//		}
////		if(o.get(0) instanceof Point){
////			drawAreaMapChoice(oo, person,o);
////		}else {
////			//display a list of choices in a series of buttons
////		}
//		choiceStack.add(new Choice(oo, person));
//		
//		//add a clickhandler that will then take the idecision, set the choices on it, and then continue with the game.
//			
//	}
//	public class Choice {
//		public IDecision dec;
//		public Choice(IDecision dec, LivingBeing p) {
//			super();
//			this.dec = dec;
//			this.p = p;
//		}
//		public LivingBeing p;
//	}
//	
//	
//
//		
//
//	boolean choosing = false;
//	VerticalPanel choicesPanel;
//
//	public void execute(Map<String, Object> map) {	
//		if (panel == null) {
//			return;
//		}
//		if (choicesPanel == null) {
//			choicesPanel = new VerticalPanel();
//			//panel.setWidget(HtmlOutRow, 6, choicesPanel);
//
//		}
//		if (choiceStack.size() < 1 || choosing) {
//
//			return;
//		}
//		choosing = true;
//		final IDecision oo = choiceStack.get(0).dec;
//		final LivingBeing person = choiceStack.get(0).p;
//		choiceStack.remove(0);
//		List<Object> o = oo.getChoices(person);
//
//		int a = 0;
//		for (final Object obj : o) {
//			Button button = new Button("choice " + a + ": " + obj);
//			choicesPanel.add(button);
//
//			button.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					oo.setChoice(obj, person);
//					choicesPanel.clear();
//					choosing = false;
//					if (choiceStack.size() == 0) {
//						person.getParent().getParent().getParent().getParent().game
//								.pauseToggle();
//					}
//				}
//			});
//			a++;
//		}
//
//	}
//
//	}
//
//
//
//	@Override
//	public Widget getWidget() {
//		// TODO Auto-generated method stub
//		return but;
//	}
	
	
}
