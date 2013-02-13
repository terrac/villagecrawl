package gwt.client.game;

import gwt.client.EntryPoint;
import gwt.client.item.Item;
import gwt.client.main.VConstants;
import gwt.client.main.base.LivingBeing;

import java.util.ArrayList;
import java.util.List;

public class GameUtil {

	public static List<LivingBeing> getPlayerTeam(List<LivingBeing> people2) {
		String team = "1";
		List<LivingBeing> people = getTeam(people2, team);
		return people;
	}
	public static List<LivingBeing> getPlayerTeamAndSummon(List<LivingBeing> people2) {
		String team = "1";
		List<LivingBeing> people = getTeamAndSummon(people2, team);
		return people;
	}
	public static List<LivingBeing> getTeam(List<LivingBeing> people2,
			String team) {
		List<LivingBeing> people = new ArrayList<LivingBeing>();
		for(LivingBeing lb : people2){
			
			if(team.equals(lb.get(VConstants.team))&&!lb.getB(VConstants.summon)){
				people.add(lb);
			}
			
		}
		return people;
	}
	
	public static List<LivingBeing> getTeamAndSummon(List<LivingBeing> people2,
			String team) {
		List<LivingBeing> people = new ArrayList<LivingBeing>();
		for(LivingBeing lb : people2){
			
			if(team.equals(lb.get(VConstants.team))){
				people.add(lb);
			}
			
		}
		return people;
	}
	public static String getPlayerTeam(){
		return "1";
	}
	public static Item getItemFavor(Item item){
		Item favor=EntryPoint.game.getItem(VConstants.favor);
		favor.setAmount(item.getTotalValue());
		return favor;
	}
}
