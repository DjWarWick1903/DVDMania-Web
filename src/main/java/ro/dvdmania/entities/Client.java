package ro.dvdmania.entities;

import java.time.LocalDate;

public class Client extends Person {
	private int loialitate;

	public Client() {

	}

	// client cu email
	public Client(final int id, final String nume, final String prenume, final String adresa, final String oras, final LocalDate datan, final String cnp,
			final String tel, final String email, final int loialitate) {
		setId(id);
		setNume(nume);
		setPrenume(prenume);
		setAdresa(adresa);
		setOras(oras);
		setDatan(datan);
		setCnp(cnp);
		setTel(tel);
		setEmail(email);
		this.loialitate = loialitate;
	}

	// client fara email
	public Client(final int id, final String nume, final String prenume, final String adresa, final String oras, final LocalDate datan, final String cnp,
			final String tel, final int loialitate) {
		setId(id);
		setNume(nume);
		setPrenume(prenume);
		setAdresa(adresa);
		setOras(oras);
		setDatan(datan);
		setCnp(cnp);
		setTel(tel);
		this.loialitate = loialitate;
	}

	public int getLoialitate() {
		return loialitate;
	}

	public void setLoialitate(final int loialitate) {
		this.loialitate = loialitate;
	}
}
