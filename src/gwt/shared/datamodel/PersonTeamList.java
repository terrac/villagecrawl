package gwt.shared.datamodel;

import java.util.List;

public class PersonTeamList implements IClientObject{
	public PersonTeamList() {
		
	}
	public List<String> personTeam;

	public PersonTeamList(List<String> personTeam) {
		super();
		this.personTeam = personTeam;
	}
}
