package gwt.shared;

import gwt.client.game.AttachUtil;
import gwt.client.game.vparams.BuildMap;
import gwt.client.game.vparams.ChooseFMD;
import gwt.client.game.vparams.CopySelection;
import gwt.client.game.vparams.SelectAndApply;
import gwt.client.item.SimpleMD;
import gwt.client.main.VConstants;
import gwt.client.main.base.PBase;

public class StatisticalCivMap {
	public static BuildMap getMap1(){
		PBase charmap = new PBase();

		charmap.put("O", new SimpleMD(VConstants.gate, "orcentrance",
				"/images/grass.png"));

		charmap.put("m", new SimpleMD(VConstants.obstacle, "mountain"));
		charmap.put("r", new SimpleMD(VConstants.gate, "rock"));
		charmap.put("t", new SimpleMD(VConstants.gate, "tree"));
		charmap.put("d", new SimpleMD(VConstants.under, "desert"));
		charmap.put("w", new SimpleMD(VConstants.obstacle, "water"));
		SimpleMD pop = new SimpleMD(VConstants.population,null);
		charmap.put("p", pop);
		
		
		BuildMap bm1 = new BuildMap(charmap, 
				  "wwwwwwwwwwwwwwww\\n"
				+ "wwdddddddddddwww\\n"
				+ "wwdddddddddddddw\\n"
				+ "wwwddddddddddddw\\n"
				+ "wwwww        www\\n"
				+ "wwwwwtrpt t wwww\\n"
				+ "wwwwwttttt  wwww\\n"
				+ "wwwwww  r  wwwww\\n"
				+ "wwwwwww   wwwwww\\n"
				+ "wwwwwwwwwwwwwwww", "mainarea");
		PBase resource = new PBase();
		
		bm1.put(VConstants.resource, resource);

		resource.getType(VConstants.resource)
				.getListCreate(VConstants.leftclick)
				.add(new CopySelection("bagselection"));

		resource.put(VConstants.defaultimage, "/images/grass.png");

		resource.getType(VConstants.resource).put(VConstants.image,
				"/images/itemshop.png");
		resource.getType(VConstants.resource).put(VConstants.sound,
				"tradingmusic");

		return bm1;
	}


	public static BuildMap getMap2(){
		PBase charmap = new PBase();

		charmap.put("O", new SimpleMD(VConstants.gate, "orcentrance",
				"/images/grass.png"));

		charmap.put("m", new SimpleMD(VConstants.obstacle, "mountain"));
		charmap.put("r", new SimpleMD(VConstants.gate, "rock"));
		charmap.put("t", new SimpleMD(VConstants.gate, "tree"));
		charmap.put("d", new SimpleMD(VConstants.under, "desert"));
		charmap.put("w", new SimpleMD(VConstants.obstacle, "water"));
		
		
		BuildMap bm1 = new BuildMap(charmap, 
				  "wwwwwwwwwwwwwwww\n"
				+ "                \n"
				+ "                \n"
				+ "                \n"
				+ "                \n"
				+ "                \n"
				+ "                \n"
				+ "                \n"
				+ "                \n"
				+ "                \n"
				+ "                \n"
				+ "wwwwwwwwwwwwwwww", "mainarea");
		PBase resource = new PBase();
		
		bm1.put(VConstants.resource, resource);


		resource.put(VConstants.defaultimage, "/images/grass.png");

		resource.getType(VConstants.resource).put(VConstants.image,
				"/images/itemshop.png");
		resource.getType(VConstants.resource).put(VConstants.sound,
				"tradingmusic");

		return bm1;
	}
}
