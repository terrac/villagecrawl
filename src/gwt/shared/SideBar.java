/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package gwt.shared;

import gwt.client.rpc.GetObject;
import gwt.client.rpc.GetObjectAsync;
import gwt.client.rpc.LoginInfo;
import gwt.shared.datamodel.IClientObject;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public abstract class SideBar {

	
	VerticalPanel layout = new VerticalPanel();
	
	public void sideLoad(String[] first, String[] second) {

		
		GetObjectAsync goa = GWT.create(GetObject.class);
		
	
		// Initialize the options in the menu
		goa.getObject(first, second,
				new AsyncCallback<Map<String, IClientObject>>() {

					@Override
					public void onSuccess(Map<String, IClientObject> result) {

						LoginInfo loginInfo = (LoginInfo) result.get("login");
						
						Anchor signInLink;
						
						if(loginInfo != null){
							String gkey=Window.Location.getParameter("gkey");
							if (!loginInfo.isLoggedIn()) {
								layout.add(new HTML(getClientRep("Sign In",loginInfo.getLoginUrl(),gkey)));
								//Window.alert(loginInfo.getLoginUrl());
							} else {
								layout.add(new HTML(getClientRep("Sign Out",loginInfo.getLogoutUrl(),gkey)));
								//Window.alert(loginInfo.getLogoutUrl());
							}
						}

						RootPanel mp = RootPanel.get("mainpage");
						if(mp == null){
							return;
						}
						loadMain(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						Window
								.alert("Failure of getting the objects for edit: "
										+ caught.getMessage());

					}
				});
		VerticalPanel panel = new VerticalPanel();
		panel.add(layout);
		panel.add(new HTML("<br><br>"));
		RootPanel rootPanel = RootPanel.get("side");
		if(rootPanel != null){
			rootPanel.add(panel);
		}
	}

	protected abstract void loadMain(Map<String, IClientObject> result);
	
	/**
	 * Copied from viewSelectionSource
	 * @param req 
	 * @return
	 */
	
	public static String getServletRep(String signinrep,String url, String gkey){
		
		return "<html>" +
				"<head>" +
//				"<script type=\"text/javascript\" language=\"javascript\" src=\"villagedc/villagedc.nocache.js\"></script>" +
//				"<link type=\"text/css\" rel=stylesheet href=villagedc.css>" +
				"</head>" +
				"<body>"+getClientRep(signinrep, url,gkey);
	}
	
	public static String getRep(String signinrep,String url){
		return 
				"<a href=\""+url+"\" class=\"gwt-Anchor\" tabindex=\"0\">"+signinrep+"</a>";
	}
	public static String getClientRep(String signinrep,String url,String gkey){
		String share =" "+ getRep("leaderboard", "/leaderboard");
		if(gkey!= null){
			share=" "+ getRep("share", "/leaderboard?gkey="+gkey);
		} 
		return 
		getRep(signinrep, url) + " " + getRep("gamelist", "/displaypersongames") + " " + getRep("profile", "/profile") + share;
	}


	
}
