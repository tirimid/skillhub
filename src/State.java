package net.tirimid.skillhub;

import java.io.*;
import java.util.*;

public class State
{
	private static final String employeesFile = "employees.txt";
	private static final String facilitatorsFile = "facilitators.txt";
	private static final String workshopsFile = "workshops.txt";

	public static ArrayList<Employee> employees = new ArrayList<Employee>();
	public static ArrayList<Facilitator> facilitators = new ArrayList<Facilitator>();
	public static ArrayList<Workshop> workshops = new ArrayList<Workshop>();

	public static int
	read()
	{
		// the error messages here are going to be utterly crap.
		// this is more or less fine, as they would only ever really
		// come around as one-time flukes, or if the user has gone
		// digging around in the files used by the program; in which
		// case, they can go fix it themself.

		// read employees.
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(employeesFile));

			while (br.readLine() != null)
			{
				int id = Integer.parseInt(br.readLine());
				String name = br.readLine();
				Employee.Department department = Employee.Department.valueOf(br.readLine());
				String email = br.readLine();
				employees.add(new Employee(id, name, department, email));
			}
		}
		catch (Exception e)
		{
			Util.showError("invalid employee data! fix this before proceeding");
			return 1;
		}

		// read facilitators.
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(facilitatorsFile));

			while (br.readLine() != null)
			{
				int id = Integer.parseInt(br.readLine());
				String name = br.readLine();
				Facilitator.Expertise expertise = Facilitator.Expertise.valueOf(br.readLine());
				String email = br.readLine();
				facilitators.add(new Facilitator(id, name, expertise, email));
			}
		}
		catch (Exception e)
		{
			Util.showError("invalid facilitator data! fix this before proceeding");
			return 1;
		}

		// read workshops.
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(workshopsFile));

			while (br.readLine() != null)
			{
				int id = Integer.parseInt(br.readLine());
				String title = br.readLine();
				int facilitatorId = Integer.parseInt(br.readLine());
				Workshop.Location location = Workshop.Location.valueOf(br.readLine());
				Workshop.Timing timing = Workshop.Timing.valueOf(br.readLine());

				Workshop workshop = new Workshop(id, title, facilitatorId, location, timing);

				int employeeCnt = Integer.parseInt(br.readLine());
				for (int i = 0; i < employeeCnt; ++i)
				{
					int employee = Integer.parseInt(br.readLine());
					workshop.employees.add(employee);
				}

				workshops.add(workshop);
			}
		}
		catch (Exception e)
		{
			Util.showError("invalid workshop data! fix this before proceeding");
			return 1;
		}

		return 0;
	}

	public static int
	write()
	{
		// TODO: implement state write.
		return 1;
	}

	public static void
	dump()
	{
		// dump employees.
		{
			System.out.println("employees:");
			for (Employee e : employees)
				System.out.println("\tid=" + e.id + " name=" + e.name + " dept=" + e.department + " email=" + e.email);
		}

		// dump facilitators.
		{
			System.out.println("facilitators:");
			for (Facilitator f : facilitators)
				System.out.println("\tid=" + f.id + " name=" + f.name + " expertise=" + f.expertise + " email=" + f.email);
		}

		// dump workshops.
		{
			System.out.println("workshops:");
			for (Workshop w : workshops)
				System.out.println("\tid=" + w.id + " title=" + w.title + " fid=" + w.facilitatorId + " location=" + w.location + " timing=" + w.timing + " employees=" + w.employees);
		}
	}

	public static int
	validate()
	{
		// validate employees.
		for (int i = 0; i < employees.size(); ++i)
		{
			for (int j = i + 1; j < employees.size(); ++j)
			{
				if (employees.get(i).id == employees.get(j).id)
				{
					Util.showError("employees " + employees.get(i).name + " and " + employees.get(j).name + " have the same ID! fix what you did before proceeding");
					return 1;
				}
			}
		}

		// validate facilitators.
		for (int i = 0; i < facilitators.size(); ++i)
		{
			for (int j = i + 1; j < facilitators.size(); ++j)
			{
				if (facilitators.get(i).id == facilitators.get(j).id)
				{
					Util.showError("facilitators " + facilitators.get(i).name + " and " + facilitators.get(j).name + " have the same ID! fix what you did before proceeding");
					return 1;
				}
			}
		}

		// validate workshops.
		for (int i = 0; i < workshops.size(); ++i)
		{
			for (int j = i + 1; j < workshops.size(); ++j)
			{
				if (workshops.get(i).id == workshops.get(j).id)
				{
					Util.showError("workshops " + workshops.get(i).title + " and " + workshops.get(j).title + " have the same ID! fix what you did before proceeding");
					return 1;
				}
			}
		}

		return 0;
	}

	public static int
	genEmployeeId()
	{
		int highest = 0;
		for (Employee e : employees)
		{
			if (e.id > highest)
				highest = e.id;
		}
		return highest + 1;
	}

	public static int
	genFacilitatorId()
	{
		int highest = 0;
		for (Facilitator f : facilitators)
		{
			if (f.id > highest)
				highest = f.id;
		}
		return highest + 1;
	}

	public static int
	genWorkshopId()
	{
		int highest = 0;
		for (Workshop w : workshops)
		{
			if (w.id > highest)
				highest = w.id;
		}
		return highest + 1;
	}
}
