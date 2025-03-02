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

		dataEntry.setLayout(new GridLayout(0, 1));
		dataEntry.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		dataEntry.add(new JLabel("Data entry"));

		// populate comboboxes with needed values.
		for (Employee.Department d : Employee.Department.values())
			deEmployeeDepartment.addItem(d.name());

		for (Facilitator.Expertise e : Facilitator.Expertise.values())
			deFacilitatorExpertise.addItem(e.name());

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
				tryAddEmployee(e);
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
				tryAddFacilitator(e);
			}
		});
		dataEntry.add(addFacilitator);
	}

	private static void
	tryAddEmployee(ActionEvent e)
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
	tryAddFacilitator(ActionEvent e)
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
	genDataRemoval()
	{
		// setup panel and children.
		dataRemoval.removeAll();

		dataRemoval.setLayout(new GridLayout(0, 1));
		dataRemoval.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		dataRemoval.add(new JLabel("Data removal"));

		// TODO: implement data removal panel gen.
	}

	public static void
	run()
	{
		// make main frame.
		frame.setLayout(new GridLayout(3, 2));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.add(employeeList);
		frame.add(facilitatorList);
		frame.add(workshopList);
		frame.add(dataEntry);
		frame.add(dataRemoval);

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
