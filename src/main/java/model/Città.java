package model;

import java.util.Set;

public class Città {

	private String nome;
	private String colore;
	private Set<Emporio> empori;
	private Re re;
	private Set<Città> cittàVicina;
	private Set<Bonus> bonus;
}
