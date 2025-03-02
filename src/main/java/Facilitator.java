package net.tirimid.skillhub;

public class Facilitator
{
	public int id;
	public String name;
	public String expertise;
	public String email;
	
	public
	Facilitator(int id, String name, String expertise, String email)
	{
		this.id = id;
		this.name = name;
		this.expertise = expertise;
		this.email = email;
	}

	public
	Facilitator(String name, String expertise, String email)
	{
		this.id = State.genFacilitatorId();
		this.name = name;
		this.expertise = expertise;
		this.email = email;
	}
}
