package net.tirimid.skillhub;

public class Employee
{
	public enum Department
	{
		SALES,
		MARKETING,
		OPERATIONS
	};

	public int id;
	public String name;
	public Department department;
	public String email;

	public
	Employee(int id, String name, Department department, String email)
	{
		this.id = id;
		this.name = name;
		this.department = department;
		this.email = email;
	}

	public
	Employee(String name, Department department, String email)
	{
		this.id = State.genEmployeeId();
		this.name = name;
		this.department = department;
		this.email = email;
	}
}
