package gwt.client.main;

import java.io.Serializable;

import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class CopyOfGame implements Serializable {
	public @Id Long id;
	public CopyOfGame() {
		// TODO Auto-generated constructor stub
	}
}
