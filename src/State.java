package net.tirimid.skillhub;

import java.io.*;
import java.util.*;

public class State
{
	private static final String EMPLOYEES_FILE = "employees.txt";
	private static final String FACILITATORS_FILE = "facilitators.txt";
	private static final String WORKSHOPS_FILE = "workshops.txt";
	private static final int MAX_WORKSHOP_MEMBERS = 5;

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
		try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEES_FILE)))
		{
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
			Util.showError("failed to read employee data!");
			return 1;
		}

		// read facilitators.
		try (BufferedReader br = new BufferedReader(new FileReader(FACILITATORS_FILE)))
		{
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
			Util.showError("failed to read facilitator data!");
			return 1;
		}

		// read workshops.
		try (BufferedReader br = new BufferedReader(new FileReader(WORKSHOPS_FILE)))
		{
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
			Util.showError("failed to read workshop data!");
			return 1;
		}

		return 0;
	}

	public static int
	write()
	{
		// write employees.
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(EMPLOYEES_FILE)))
		{
			for (Employee e : employees)
			{
				bw.newLine();
				bw.write(Integer.toString(e.id) + "\n");
				bw.write(e.name + "\n");
				bw.write(e.department.name() + "\n");
				bw.write(e.email + "\n");
				bw.flush();
			}
		}
		catch (Exception e)
		{
			Util.showError("failed to write employee data!");
			return 1;
		}
		
		// write facilitators.
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FACILITATORS_FILE)))
		{
			for (Facilitator f : facilitators)
			{
				bw.newLine();
				bw.write(Integer.toString(f.id) + "\n");
				bw.write(f.name + "\n");
				bw.write(f.expertise.name() + "\n");
				bw.write(f.email + "\n");
				bw.flush();
			}
		}
		catch (Exception e)
		{
			Util.showError("failed to write facilitator data!");
			return 1;
		}

		// write workshops.
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(WORKSHOPS_FILE)))
		{
			for (Workshop w : workshops)
			{
				bw.newLine();
				bw.write(Integer.toString(w.id) + "\n");
				bw.write(w.title + "\n");
				bw.write(Integer.toString(w.facilitatorId) + "\n");
				bw.write(w.location.name() + "\n");
				bw.write(w.timing.name() + "\n");

				bw.write(Integer.toString(w.employees.size()) + "\n");
				for (int employee : w.employees)
					bw.write(Integer.toString(employee) + "\n");
			}
		}
		catch (Exception e)
		{
			Util.showError("failed to write workshop data!");
			return 1;
		}
		
		return 0;
	}

	public static void
	dump()
	{
		// dump employees.
		System.out.println("employees:");
		for (Employee e : employees)
			System.out.println("\tid=" + e.id + " name=" + e.name + " dept=" + e.department + " email=" + e.email);

		// dump facilitators.
		System.out.println("facilitators:");
		for (Facilitator f : facilitators)
			System.out.println("\tid=" + f.id + " name=" + f.name + " expertise=" + f.expertise + " email=" + f.email);

		// dump workshops.
		System.out.println("workshops:");
		for (Workshop w : workshops)
			System.out.println("\tid=" + w.id + " title=" + w.title + " fid=" + w.facilitatorId + " location=" + w.location + " timing=" + w.timing + " employees=" + w.employees);
	}

	// delete all nonexistent employee IDs from workshops, as well as all
	// workshops which reference a nonexistent facilitator ID.
	public static void
	removeOrphans()
	{
		// remove orphaned workshops.
		for (int i = 0; i < workshops.size(); ++i)
		{
			if (getFacilitator(workshops.get(i).facilitatorId) == null)
			{
				workshops.remove(i);
				--i;
			}
		}

		// remove orphaned employees.
		for (Workshop w : workshops)
		{
			for (int i = 0; i < w.employees.size(); ++i)
			{
				if (getEmployee(w.employees.get(i)) == null)
				{
					w.employees.remove(i);
					--i;
				}
			}
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
			if (workshops.get(i).employees.size() > MAX_WORKSHOP_MEMBERS)
			{
				Util.showError("workshop " + workshops.get(i).title + " has too many members! fix what you did before proceeding");
				return 1;
			}

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

	public static Employee
	getEmployee(int id)
	{
		for (Employee e : employees)
		{
			if (e.id == id)
				return e;
		}
		return null;
	}

	public static Facilitator
	getFacilitator(int id)
	{
		for (Facilitator f : facilitators)
		{
			if (f.id == id)
				return f;
		}
		return null;
	}

	public static Workshop
	getWorkshop(int id)
	{
		for (Workshop w : workshops)
		{
			if (w.id == id)
				return w;
		}
		return null;
	}
}
