package net.tirimid.skillhub;

public class Employee
{
	public enum Department
	{
		Sales,
		Marketing,
		Operations
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

	public static Department
	parseDepartment(String str) throws Exception
	{
	}
}
