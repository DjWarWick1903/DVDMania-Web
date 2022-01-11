package ro.dvdmania.entities;

import java.time.LocalDate;

public class Employee extends Person {
	private String functie;
	private int salariu;
	private boolean activ;
	private int idMag;

	public Employee() {

	}

	public Employee(final int id, final String nume, final String prenume, final String adresa, final String oras, final LocalDate datan, final String cnp,
			final String telefon, final String email, final String functie, final int salariu, final boolean activ, final int idMag) {
		setId(id);
		setPrenume(prenume);
		setAdresa(adresa);
		setOras(oras);
		setDatan(datan);
		setCnp(cnp);
		setTel(telefon);
		setEmail(email);
		this.functie = functie;
		this.salariu = salariu;
		this.activ = activ;
		this.idMag = idMag;
	}

	public String getFunctie() {
		return functie;
	}

	public void setFunctie(final String functie) {
		this.functie = functie;
	}

	public int getSalariu() {
		return salariu;
	}

	public void setSalariu(final int salariu) {
		this.salariu = salariu;
	}

	public boolean isActiv() {
		return activ;
	}

	public void setActiv(final boolean activ) {
		this.activ = activ;
	}

	public int getIdMag() {
		return idMag;
	}

	public void setIdMag(final int idMag) {
		this.idMag = idMag;
	}
}
