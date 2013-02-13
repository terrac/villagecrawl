package gwt.client.game.display;

import gwt.client.map.HashMapData;

import com.google.gwt.user.client.ui.Widget;

/**
 * This class is for adding to the map when another select on the object has been used
 * @author terra
 *
 */
public class SelectAddDisplay extends UIVParams{
	

	public void init(){

	}

//	if (currentlySelected != null) {
//	return;
//	if (keySelected == null) {
//		switchMD(md, (HashMapData) currentlySelected);
//
//	} else {
//
//		bagMap.getData(x / 30, y / 30).put(
//				currentlySelected.remove(keySelected));
//	}
//	select(null, null);
//} else {
//	HashMapData clone = md.clone(bagMap);
	// clone.setParent(bagMap);
	
//}


	public void execute(HashMapData mapData){
	//	lList.clear();

		//might want later as an optional option
//		if (currentlySelected == null) {
//			// Window.alert(""+mapData.size());
//			select(mapData, null);
//		}
//		if (currentlySelected != null) {
//			
//			//			if (keySelected == null) {
//				if (!mapData.equals(currentlySelected)) {
//
//					HashMapData hmd = (HashMapData) currentlySelected;
//					if (!AttachUtil.run(VConstants.preset, hmd, this)) {
//						return;
//					}
//					switchMD(mapData, hmd);
//					hmd.getParent().addPerson(hmd.getLivingBeing());
//					select(null, null);
//					htmlOut.refreshFmds();
//				}
//			} else if (!mapData.containsKey(keySelected)) {
//
//				mapData.put(currentlySelected.remove(keySelected));
//				htmlOut.refreshFmds();
//				select(null, null);
//			}

		}

	@Override
	public Widget getWidget() {
		// TODO Auto-generated method stub
		return null;
	}

//		ClickHandler handler = new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				Label label = (Label) event.getSource();
//				select(mapData, label.getText());
//				// take md and set it as currently selected for the moving
//				// between hmds
//
//				// additionally display an additional list of edits
//				// from a map based on the type of the mapdata
//			}
//		};
//
//		for (String key : mapData.keySet()) {
//			Label label = new Label(key);
//
//			label.addClickHandler(handler);
//			lList.add(label);
//		}


//	}



//	@Override
//	public Widget getWidget() {
//		// TODO Auto-generated method stub
//		return but;
//	}
//	protected void switchMD(final HashMapData mapData, HashMapData hmd) {
//		Point p = hmd.getPosition();
//		if (p == null) {
//			p = ((HashMapData) hmd.objMap.get("clone")).getPosition();
//			hmd.objMap.remove("clone");
//		}
//		FullMapData parent = hmd.getParent();
//		mapData.getParent().setData(mapData.getPosition(), hmd);
//		if (!parent.equals(bagMap)) {
//
//			parent.setData(p, mapData);
//		}
//	}	
	
//	protected void select(final HashMapData mapData, String string) {
//		keySelected = null;
//		if (mapData != null) {
//			if (string == null) {
//				currentlySelected = mapData;
//				selectedButton.setText("Unseelect currently selected:"
//						+ ((HashMapData) currentlySelected).getPosition());
//			} else {
//
//				selectedButton
//						.setText("Unseelect currently selected:" + string);
//				keySelected = string;
//				currentlySelected = mapData;
//			}
//
//		} else {
//			currentlySelected = null;
//			selectedButton.setText("Currently selected: None");
//			lList.clear();
//		}
//
//	}
	
	
//	vhmd = new VerticalPanel();
//	lList = new VerticalPanel();
//	selectedButton = new Button("Currently selected: None");
//	selectedButton.addClickHandler(new ClickHandler() {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			select(null, null);
//		}
//	});
//	vhmd.add(selectedButton);
//	vhmd.add(lList);
//
//	htmlOut = (HtmlOut) OutputDirector.mpane;
//	htmlOut.panel.setWidget(EditMapRow, 4, vhmd);
//	VerticalPanel vp = new VerticalPanel();
//	htmlOut.panel.setWidget(EditMapRow, 3, vp);
}
