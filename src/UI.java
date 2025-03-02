package net.tirimid.skillhub;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class UI
{
	private static final Color BORDER_COLOR = Color.black;
	private static final int TEXT_FIELD_WIDTH = 40;

	private static JFrame frame = new JFrame("Skillhub");

	// main program panels.
	private static JPanel employeeList = new JPanel();
	private static JPanel facilitatorList = new JPanel();
	private static JPanel workshopList = new JPanel();
	private static JPanel dataEntry = new JPanel();
	private static JPanel dataRemoval = new JPanel();

	// data entry state.
	private static JTextField deEmployeeName = new JTextField(TEXT_FIELD_WIDTH);
	private static JComboBox deEmployeeDepartment = new JComboBox();
	private static JTextField deEmployeeEmail = new JTextField(TEXT_FIELD_WIDTH);
	private static JTextField deFacilitatorName = new JTextField(TEXT_FIELD_WIDTH);
	private static JComboBox deFacilitatorExpertise = new JComboBox();
	private static JTextField deFacilitatorEmail = new JTextField(TEXT_FIELD_WIDTH);
	private static JTextField deWorkshopTitle = new JTextField(TEXT_FIELD_WIDTH);
	private static JComboBox deWorkshopFacilitatorId = new JComboBox();
	private static JComboBox deWorkshopLocation = new JComboBox();
	private static JComboBox deWorkshopTiming = new JComboBox();

	private static void
	genEmployeeList()
	{
		// setup panel.
		employeeList.removeAll();

		employeeList.setLayout(new GridLayout(0, 1));
		employeeList.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		employeeList.add(new JLabel("Employee list"));

		// populate with employee data.
		ArrayList<String[]> employeeData = new ArrayList<String[]>();
		for (Employee e : State.employees)
		{
			String[] data =
			{
				Integer.toString(e.id),
				e.name,
				e.department.name(),
				e.email
			};
			employeeData.add(data);
		}

		String[] colNames = {"ID", "Name", "Department", "Email"};

		JTable tab = new JTable(employeeData.toArray(new String[0][0]), colNames);
		employeeList.add(tab.getTableHeader());
		employeeList.add(tab);
	}

	private static void
	genFacilitatorList()
	{
		// setup panel.
		facilitatorList.removeAll();

		facilitatorList.setLayout(new GridLayout(0, 1));
		facilitatorList.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		facilitatorList.add(new JLabel("Facilitator list"));

		// populate with facilitator data.
		ArrayList<String[]> facilitatorData = new ArrayList<String[]>();
		for (Facilitator f : State.facilitators)
		{
			String[] data =
			{
				Integer.toString(f.id),
				f.name,
				f.expertise.name(),
				f.email
			};
			facilitatorData.add(data);
		}

		String[] colNames = {"ID", "Name", "Expertise", "Email"};

		JTable tab = new JTable(facilitatorData.toArray(new String[0][0]), colNames);
		facilitatorList.add(tab.getTableHeader());
		facilitatorList.add(tab);
	}

	private static void
	genWorkshopList()
	{
		// setup panel.
		workshopList.removeAll();

		workshopList.setLayout(new GridLayout(0, 1));
		workshopList.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		workshopList.add(new JLabel("Workshop list"));

		// populate with workshop data.
		for (Workshop w : State.workshops)
		{
			workshopList.add(new JLabel(w.title));

			String[] colNames =
			{
				"ID",
				"Title",
				"Facilitator ID",
				"Facilitator name",
				"Location",
				"Timing"
			};

			String[][] workshopData =
			{
				{
					Integer.toString(w.id),
					w.title,
					Integer.toString(w.facilitatorId),
					State.getFacilitator(w.facilitatorId).name,
					w.location.name(),
					w.timing.name()
				}
			};

			JTable tab = new JTable(workshopData, colNames);
			workshopList.add(tab.getTableHeader());
			workshopList.add(tab);

			ArrayList<String[]> employeeData = new ArrayList<String[]>();
			for (int employeeId : w.employees)
			{
				Employee employee = State.getEmployee(employeeId);
				String[] data =
				{
					Integer.toString(employeeId),
					employee.name
				};
				employeeData.add(data);
			}

			String[] employeeColNames = {"ID", "Name"};

			JTable employeeTab = new JTable(employeeData.toArray(new String[0][0]), employeeColNames);
			workshopList.add(employeeTab.getTableHeader());
			workshopList.add(employeeTab);
		}
	}

	private static void
	genDataEntry()
	{
		// setup panel and children.
		dataEntry.removeAll();
		deEmployeeName.setText("");
		deEmployeeDepartment.removeAllItems();
		deEmployeeEmail.setText("");
		deFacilitatorName.setText("");
		deFacilitatorExpertise.removeAllItems();
		deFacilitatorEmail.setText("");
		deWorkshopTitle.setText("");
		deWorkshopFacilitatorId.removeAllItems();
		deWorkshopLocation.removeAllItems();
		deWorkshopTiming.removeAllItems();

		dataEntry.setLayout(new GridLayout(0, 1));
		dataEntry.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		dataEntry.add(new JLabel("Data entry"));

		// populate comboboxes with needed values.
		for (Employee.Department d : Employee.Department.values())
			deEmployeeDepartment.addItem(d.name());

		for (Facilitator.Expertise e : Facilitator.Expertise.values())
			deFacilitatorExpertise.addItem(e.name());

		for (Facilitator f : State.facilitators)
		{
			if (f.getAssignedWorkshop() == null)
				deWorkshopFacilitatorId.addItem(Integer.toString(f.id));
		}

		for (Workshop.Location l : Workshop.Location.values())
			deWorkshopLocation.addItem(l.name());

		for (Workshop.Timing t : Workshop.Timing.values())
			deWorkshopTiming.addItem(t.name());

		// populate panel with data entry functionality.
		dataEntry.add(new JLabel("Create new employee"));
		dataEntry.add(deEmployeeName);
		dataEntry.add(deEmployeeDepartment);
		dataEntry.add(deEmployeeEmail);
		JButton addEmployee = new JButton("Add employee");
		addEmployee.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				tryAddEmployee();
			}
		});
		dataEntry.add(addEmployee);

		dataEntry.add(new JLabel("Create new facilitator"));
		dataEntry.add(deFacilitatorName);
		dataEntry.add(deFacilitatorExpertise);
		dataEntry.add(deFacilitatorEmail);
		JButton addFacilitator = new JButton("Add facilitator");
		addFacilitator.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				tryAddFacilitator();
			}
		});
		dataEntry.add(addFacilitator);

		dataEntry.add(new JLabel("Create new workshop"));
		dataEntry.add(deWorkshopTitle);
		dataEntry.add(deWorkshopFacilitatorId);
		dataEntry.add(deWorkshopLocation);
		dataEntry.add(deWorkshopTiming);
		JButton addWorkshop = new JButton("Add workshop");
		addWorkshop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				tryAddWorkshop();
			}
		});
		dataEntry.add(addWorkshop);
	}

	private static void
	tryAddEmployee()
	{
		String name = deEmployeeName.getText().trim();
		if (name.isEmpty())
		{
			Util.showError("a name must be specified for the employee!");
			return;
		}

		Employee.Department department = Employee.Department.valueOf((String)deEmployeeDepartment.getSelectedItem());

		String email = deEmployeeEmail.getText().trim();
		if (email.isEmpty())
		{
			Util.showError("an email must be specified for the employee!");
			return;
		}

		Employee newEmployee = new Employee(name, department, email);
		State.employees.add(newEmployee);
		State.write();
		genEmployeeList();
		genDataEntry();
		frame.pack();
	}

	private static void
	tryAddFacilitator()
	{
		String name = deFacilitatorName.getText().trim();
		if (name.isEmpty())
		{
			Util.showError("a name must be specified for the facilitator!");
			return;
		}

		Facilitator.Expertise expertise = Facilitator.Expertise.valueOf((String)deFacilitatorExpertise.getSelectedItem());

		String email = deFacilitatorEmail.getText().trim();
		if (email.isEmpty())
		{
			Util.showError("an email must be specified for the facilitator!");
			return;
		}

		Facilitator newFacilitator = new Facilitator(name, expertise, email);
		State.facilitators.add(newFacilitator);
		State.write();
		genFacilitatorList();
		genDataEntry();
		frame.pack();
	}
	
	private static void
	tryAddWorkshop()
	{
		String title = deWorkshopTitle.getText().trim();
		if (title.isEmpty())
		{
			Util.showError("a title must be specified for the workshop!");
			return;
		}

		int facilitatorId = Integer.parseInt((String)deWorkshopFacilitatorId.getSelectedItem());
		Workshop.Location location = Workshop.Location.valueOf((String)deWorkshopLocation.getSelectedItem());
		Workshop.Timing timing = Workshop.Timing.valueOf((String)deWorkshopTiming.getSelectedItem());

		Workshop newWorkshop = new Workshop(title, facilitatorId, location, timing);
		State.workshops.add(newWorkshop);
		State.write();
		genWorkshopList();
		genDataEntry();
		frame.pack();
	}

	private static void
	genDataRemoval()
	{
		// setup panel and children.
		dataRemoval.removeAll();

		dataRemoval.setLayout(new GridLayout(0, 1));
		dataRemoval.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		dataRemoval.add(new JLabel("Data removal"));

		// TODO: implement data removal panel gen.
	}

	private static void
	addScrollablePanel(JPanel panel)
	{
		JScrollPane scrollPane = new JScrollPane(
			panel,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
		);
		scrollPane.setPreferredSize(new Dimension(600, 300));
		frame.getContentPane().add(scrollPane);
	}

	public static void
	run()
	{
		// make main frame.
		frame.setLayout(new GridLayout(2, 3));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1800, 900));
		frame.setResizable(false);

		addScrollablePanel(employeeList);
		addScrollablePanel(facilitatorList);
		addScrollablePanel(workshopList);
		addScrollablePanel(dataEntry);
		addScrollablePanel(dataRemoval);

		// generate initial state for everything.
		genEmployeeList();
		genFacilitatorList();
		genWorkshopList();
		genDataEntry();
		genDataRemoval();

		frame.pack();
		frame.setVisible(true);
	}
}
