package net.tirimid.skillhub;

public class Facilitator
{
	public enum Expertise
	{
		LEADERSHIP,
		DATA_ANALYTICS,
		COMMUNICATION_SKILLS
	};

	public int id;
	public String name;
	public Expertise expertise;
	public String email;
	
	public
	Facilitator(int id, String name, Expertise expertise, String email)
	{
		this.id = id;
		this.name = name;
		this.expertise = expertise;
		this.email = email;
	}

	public
	Facilitator(String name, Expertise expertise, String email)
	{
		this.id = State.genFacilitatorId();
		this.name = name;
		this.expertise = expertise;
		this.email = email;
	}
}
