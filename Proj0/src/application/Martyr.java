package application;

import java.util.Date;

public class Martyr implements Comparable<Martyr> {

	// attributes
	private String name;
	private int age;
	private String Elocation;
	private String dateofdeath;
	private String gender;

	// constructors

	public Martyr() {
		super();
	}

	public Martyr(String name, int age, String elocation, String dateofdeath, String gender) {
		super();
		this.name = name;
		this.age = age;
		Elocation = elocation;
		this.dateofdeath = dateofdeath;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getElocation() {
		return Elocation;
	}

	public void setElocation(String elocation) {
		Elocation = elocation;
	}

	public String getDateofdeath() {
		return dateofdeath;
	}

	public void setDateofdeath(String dateofdeath) {
		this.dateofdeath = dateofdeath;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "martyr [name=" + name + ", age=" + age + ", Elocation=" + Elocation + ", dateofdeath=" + dateofdeath
				+ ", gender=" + gender + "]";
	}

	@Override
	public int compareTo(Martyr o) {
		return this.name.compareTo(o.name);
	}

	@Override
	public boolean equals(Object obj) {// to check if the name is on the list to
										// add it or just add the content of it

		Martyr o = (Martyr) obj;
		return name.equals(o.name);// we can use the method compareto here
									// because it do the same
	}

}
