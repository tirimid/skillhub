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
		// read employees.
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(employeesFile));

			while (br.readLine() != null)
			{
				int id = Integer.parseInt(br.readLine());
				String name = br.readLine();
				String dept = br.readLine();
				String email = br.readLine();
				employees.add(new Employee(id, name, dept, email));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				String expertise = br.readLine();
				String email = br.readLine();
				facilitators.add(new Facilitator(id, name, expertise, email));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				String location = br.readLine();
				Workshop.Timing timing = Workshop.parseTiming(br.readLine());
				workshops.add(new Workshop(id, title, facilitatorId, location, timing));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				System.out.println("\tid=" + w.id + " title=" + w.title + " fid=" + w.facilitatorId + " location=" + w.location + " timing=" + Workshop.timingToString(w.timing));
		}
	}

	public static int
	genEmployeeId()
	{
		// TODO: implement employee ID gen.
		return 0;
	}

	public static int
	genFacilitatorId()
	{
		// TODO: implement facilitator ID gen.
		return 0;
	}

	public static int
	genWorkshopId()
	{
		// TODO: implement workshop ID gen.
		return 0;
	}
}
