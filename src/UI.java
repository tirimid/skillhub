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
	private static JPanel dataModification = new JPanel();
	private static JPanel reports = new JPanel();

	// NOTE: all combobox items are stored as strings.
	// this is suboptimal as it entains potentially unnecessary conversions,
	// however for uniformity I've just done it this way.
	// in a real program I would generally avoid doing something this wasteful.

	// data entry state.
	private static HintTextField deEmployeeName = new HintTextField(TEXT_FIELD_WIDTH, "Name");
	private static JComboBox deEmployeeDepartment = new JComboBox();
	private static HintTextField deEmployeeEmail = new HintTextField(TEXT_FIELD_WIDTH, "Email");
	private static HintTextField deFacilitatorName = new HintTextField(TEXT_FIELD_WIDTH, "Name");
	private static JComboBox deFacilitatorExpertise = new JComboBox();
	private static HintTextField deFacilitatorEmail = new HintTextField(TEXT_FIELD_WIDTH, "Email");
	private static HintTextField deWorkshopTitle = new HintTextField(TEXT_FIELD_WIDTH, "Title");
	private static JComboBox deWorkshopFacilitatorId = new JComboBox();
	private static JComboBox deWorkshopLocation = new JComboBox();
	private static JComboBox deWorkshopTiming = new JComboBox();
	private static JComboBox deAssignEmployee = new JComboBox();
	private static JComboBox deAssignWorkshop = new JComboBox();

	// data modification state.
	private static JComboBox dmEmployeeId = new JComboBox();
	private static HintTextField dmEmployeeName = new HintTextField(TEXT_FIELD_WIDTH, "Name");
	private static JComboBox dmEmployeeDepartment = new JComboBox();
	private static HintTextField dmEmployeeEmail = new HintTextField(TEXT_FIELD_WIDTH, "Email");
	private static JComboBox dmFacilitatorId = new JComboBox();
	private static HintTextField dmFacilitatorName = new HintTextField(TEXT_FIELD_WIDTH, "Name");
	private static JComboBox dmFacilitatorExpertise = new JComboBox();
	private static HintTextField dmFacilitatorEmail = new HintTextField(TEXT_FIELD_WIDTH, "Email");
	private static JComboBox dmWorkshopId = new JComboBox();
	private static HintTextField dmWorkshopTitle = new HintTextField(TEXT_FIELD_WIDTH, "Title");
	private static JComboBox dmWorkshopLocation = new JComboBox();
	private static JComboBox dmWorkshopTiming = new JComboBox();
	private static JComboBox dmAttendanceWorkshopId = new JComboBox();
	private static JComboBox dmAttendanceEmployeeId = new JComboBox();
	private static JComboBox dmAttendance = new JComboBox();

	// reports state.
	// initial requirements state that the report selection should be based
	// on names rather than IDs, however I have opted for IDs as they are
	// both less ambiguous for the user and easier to implement.
	private static JComboBox rWorkshopId = new JComboBox();
	private static JComboBox rEmployeeId = new JComboBox();
	private static JComboBox rTiming = new JComboBox();

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
			for (int i = 0; i < w.employees.size(); ++i)
			{
				Employee employee = State.getEmployee(w.employees.get(i));
				String[] data =
				{
					Integer.toString(w.employees.get(i)),
					employee.name,
					w.attendance.get(i).name()
				};
				employeeData.add(data);
			}

			String[] employeeColNames = {"ID", "Name", "Attendance"};

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
		deAssignEmployee.removeAllItems();
		deAssignWorkshop.removeAllItems();

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

		for (Employee e : State.employees)
			deAssignEmployee.addItem(Integer.toString(e.id));

		for (Workshop w : State.workshops)
			deAssignWorkshop.addItem(Integer.toString(w.id));

		// populate panel with data entry functionality.
		dataEntry.add(new JLabel(" "));
		dataEntry.add(new JLabel("Create new employee"));
		dataEntry.add(deEmployeeName);
		dataEntry.add(deEmployeeDepartment);
		dataEntry.add(deEmployeeEmail);
		JButton btnAddEmployee = new JButton("Add employee");
		btnAddEmployee.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				addEmployee();
			}
		});
		dataEntry.add(btnAddEmployee);

		dataEntry.add(new JLabel(" "));
		dataEntry.add(new JLabel("Create new facilitator"));
		dataEntry.add(deFacilitatorName);
		dataEntry.add(deFacilitatorExpertise);
		dataEntry.add(deFacilitatorEmail);
		JButton btnAddFacilitator = new JButton("Add facilitator");
		btnAddFacilitator.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				addFacilitator();
			}
		});
		dataEntry.add(btnAddFacilitator);

		dataEntry.add(new JLabel(" "));
		dataEntry.add(new JLabel("Create new workshop"));
		dataEntry.add(deWorkshopTitle);
		dataEntry.add(deWorkshopFacilitatorId);
		dataEntry.add(deWorkshopLocation);
		dataEntry.add(deWorkshopTiming);
		JButton btnAddWorkshop = new JButton("Add workshop");
		btnAddWorkshop.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				addWorkshop();
			}
		});
		dataEntry.add(btnAddWorkshop);

		dataEntry.add(new JLabel(" "));
		dataEntry.add(new JLabel("Assign employee to workshop"));
		dataEntry.add(deAssignEmployee);
		dataEntry.add(deAssignWorkshop);
		JButton btnAssignEmployee = new JButton("Assign employee");
		btnAssignEmployee.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				assignEmployee();
			}
		});
		dataEntry.add(btnAssignEmployee);
	}

	private static void
	addEmployee()
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
		genDataModification();
		frame.pack();
	}

	private static void
	addFacilitator()
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
		genDataModification();
		frame.pack();
	}
	
	private static void
	addWorkshop()
	{
		if (deWorkshopFacilitatorId.getItemCount() == 0)
		{
			Util.showError("no available facilitators exist!");
			return;
		}

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
		genDataModification();
		frame.pack();
	}

	private static void
	assignEmployee()
	{
		if (deAssignEmployee.getItemCount() == 0)
		{
			Util.showError("no employees exist!");
			return;
		}

		if (deAssignWorkshop.getItemCount() == 0)
		{
			Util.showError("no workshops exist!");
			return;
		}

		int employeeId = Integer.parseInt((String)deAssignEmployee.getSelectedItem());
		int workshopId = Integer.parseInt((String)deAssignWorkshop.getSelectedItem());

		Workshop workshop = State.getWorkshop(workshopId);

		if (workshop.employees.size() >= State.MAX_WORKSHOP_MEMBERS)
		{
			Util.showError("workshops cannot have more than " + State.MAX_WORKSHOP_MEMBERS + " attendees!");
			return;
		}

		if (workshop.employees.contains(employeeId))
		{
			Util.showError("employee is already assigned to workshop!");
			return;
		}

		workshop.employees.add(employeeId);
		State.write();
		genWorkshopList();
		genDataEntry();
		frame.pack();

		return;
	}

	private static void
	genDataModification()
	{
		// setup panel and children.
		dataModification.removeAll();
		dmEmployeeId.removeAllItems();
		dmEmployeeName.setText("");
		dmEmployeeDepartment.removeAllItems();
		dmEmployeeEmail.setText("");
		dmFacilitatorId.removeAllItems();
		dmFacilitatorName.setText("");
		dmFacilitatorExpertise.removeAllItems();
		dmFacilitatorEmail.setText("");
		dmWorkshopId.removeAllItems();
		dmWorkshopTitle.setText("");
		dmWorkshopLocation.removeAllItems();
		dmWorkshopTiming.removeAllItems();
		dmAttendanceWorkshopId.removeAllItems();
		dmAttendanceEmployeeId.removeAllItems();
		dmAttendance.removeAllItems();

		dataModification.setLayout(new GridLayout(0, 1));
		dataModification.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		dataModification.add(new JLabel("Data modification"));

		// populate comboboxes with needed values.
		for (Employee e : State.employees)
		{
			dmEmployeeId.addItem(Integer.toString(e.id));
			dmAttendanceEmployeeId.addItem(Integer.toString(e.id));
		}

		for (Employee.Department d : Employee.Department.values())
			dmEmployeeDepartment.addItem(d.name());

		for (Facilitator f : State.facilitators)
			dmFacilitatorId.addItem(Integer.toString(f.id));

		for (Facilitator.Expertise e : Facilitator.Expertise.values())
			dmFacilitatorExpertise.addItem(e.name());

		for (Workshop w : State.workshops)
		{
			dmWorkshopId.addItem(Integer.toString(w.id));
			dmAttendanceWorkshopId.addItem(Integer.toString(w.id));
		}

		for (Workshop.Location l : Workshop.Location.values())
			dmWorkshopLocation.addItem(l.name());

		for (Workshop.Timing t : Workshop.Timing.values())
			dmWorkshopTiming.addItem(t.name());

		for (Workshop.Attendance a : Workshop.Attendance.values())
			dmAttendance.addItem(a.name());

		// populate panel with data modification functionality.
		dataModification.add(new JLabel(" "));
		dataModification.add(new JLabel("Edit employee"));
		dataModification.add(dmEmployeeId);
		dataModification.add(dmEmployeeName);
		dataModification.add(dmEmployeeDepartment);
		dataModification.add(dmEmployeeEmail);
		JButton btnModifyEmployee = new JButton("Modify employee");
		btnModifyEmployee.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				modifyEmployee();
			}
		});
		dataModification.add(btnModifyEmployee);
		JButton btnDeleteEmployee = new JButton("Delete employee");
		btnDeleteEmployee.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				deleteEmployee();
			}
		});
		dataModification.add(btnDeleteEmployee);

		dataModification.add(new JLabel(" "));
		dataModification.add(new JLabel("Edit facilitator"));
		dataModification.add(dmFacilitatorId);
		dataModification.add(dmFacilitatorName);
		dataModification.add(dmFacilitatorExpertise);
		dataModification.add(dmFacilitatorEmail);
		JButton btnModifyFacilitator = new JButton("Modify facilitator");
		btnModifyFacilitator.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				modifyFacilitator();
			}
		});
		dataModification.add(btnModifyFacilitator);
		JButton btnDeleteFacilitator = new JButton("Delete facilitator");
		btnDeleteFacilitator.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				deleteFacilitator();
			}
		});
		dataModification.add(btnDeleteFacilitator);

		dataModification.add(new JLabel(" "));
		dataModification.add(new JLabel("Edit workshop"));
		dataModification.add(dmWorkshopId);
		dataModification.add(dmWorkshopTitle);
		dataModification.add(dmWorkshopLocation);
		dataModification.add(dmWorkshopTiming);
		JButton btnModifyWorkshop = new JButton("Modify workshop");
		btnModifyWorkshop.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				modifyWorkshop();
			}
		});
		dataModification.add(btnModifyWorkshop);
		JButton btnDeleteWorkshop = new JButton("Delete workshop");
		btnDeleteWorkshop.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				deleteWorkshop();
			}
		});
		dataModification.add(btnDeleteWorkshop);

		dataModification.add(new JLabel(" "));
		dataModification.add(new JLabel("Record employee attendance"));
		dataModification.add(dmAttendanceWorkshopId);
		dataModification.add(dmAttendanceEmployeeId);
		dataModification.add(dmAttendance);
		JButton btnSetAttendance = new JButton("Set attendance");
		btnSetAttendance.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				setAttendance();
			}
		});
		dataModification.add(btnSetAttendance);
	}

	private static void
	modifyEmployee()
	{
		if (dmEmployeeId.getItemCount() == 0)
		{
			Util.showError("no employees exist!");
			return;
		}

		int employeeId = Integer.parseInt((String)dmEmployeeId.getSelectedItem());

		String name = dmEmployeeName.getText();
		if (name.isEmpty())
		{
			Util.showError("no name given for employee!");
			return;
		}

		String email = dmEmployeeEmail.getText();
		if (email.isEmpty())
		{
			Util.showError("no email given for employee!");
			return;
		}

		Employee.Department department = Employee.Department.valueOf((String)dmEmployeeDepartment.getSelectedItem());

		Employee employee = State.getEmployee(employeeId);
		employee.name = name;
		employee.department = department;
		employee.email = email;

		State.write();
		genEmployeeList();
		genWorkshopList();
		genDataModification();
		frame.pack();
	}

	private static void
	deleteEmployee()
	{
		if (dmEmployeeId.getItemCount() == 0)
		{
			Util.showError("no employees exist!");
			return;
		}

		int employeeId = Integer.parseInt((String)dmEmployeeId.getSelectedItem());

		for (int i = 0; i < State.employees.size(); ++i)
		{
			if (State.employees.get(i).id == employeeId)
			{
				State.employees.remove(i);
				--i;
			}
		}

		State.removeOrphans();
		State.write();
		genEmployeeList();
		genWorkshopList();
		genDataEntry();
		genDataModification();
		genReports();
		frame.pack();
	}

	private static void
	modifyFacilitator()
	{
		if (dmFacilitatorId.getItemCount() == 0)
		{
			Util.showError("no facilitators exist!");
			return;
		}

		int facilitatorId = Integer.parseInt((String)dmFacilitatorId.getSelectedItem());

		String name = dmFacilitatorName.getText();
		if (name.isEmpty())
		{
			Util.showError("no name given for facilitator!");
			return;
		}

		String email = dmFacilitatorEmail.getText();
		if (email.isEmpty())
		{
			Util.showError("no email given for facilitator!");
			return;
		}

		Facilitator.Expertise expertise = Facilitator.Expertise.valueOf((String)dmFacilitatorExpertise.getSelectedItem());

		Facilitator facilitator = State.getFacilitator(facilitatorId);
		facilitator.name = name;
		facilitator.expertise = expertise;
		facilitator.email = email;

		State.write();
		genFacilitatorList();
		genDataModification();
		frame.pack();
	}

	private static void
	deleteFacilitator()
	{
		if (dmFacilitatorId.getItemCount() == 0)
		{
			Util.showError("no facilitators exist!");
			return;
		}

		int facilitatorId = Integer.parseInt((String)dmFacilitatorId.getSelectedItem());

		for (int i = 0; i < State.facilitators.size(); ++i)
		{
			if (State.facilitators.get(i).id == facilitatorId)
			{
				State.facilitators.remove(i);
				--i;
			}
		}

		State.removeOrphans();
		State.write();
		genFacilitatorList();
		genWorkshopList();
		genDataEntry();
		genDataModification();
		genReports();
		frame.pack();
	}

	private static void
	modifyWorkshop()
	{
		if (dmWorkshopId.getItemCount() == 0)
		{
			Util.showError("no workshops exist!");
			return;
		}

		int workshopId = Integer.parseInt((String)dmWorkshopId.getSelectedItem());

		String title = dmWorkshopTitle.getText();
		if (title.isEmpty())
		{
			Util.showError("no title entered for workshop!");
			return;
		}

		Workshop.Location location = Workshop.Location.valueOf((String)dmWorkshopLocation.getSelectedItem());
		Workshop.Timing timing = Workshop.Timing.valueOf((String)dmWorkshopTiming.getSelectedItem());

		Workshop workshop = State.getWorkshop(workshopId);
		workshop.title = title;
		workshop.location = location;
		workshop.timing = timing;

		State.write();
		genWorkshopList();
		genDataModification();
		frame.pack();
	}

	private static void
	deleteWorkshop()
	{
		if (dmWorkshopId.getItemCount() == 0)
		{
			Util.showError("no workshops exist!");
			return;
		}

		int workshopId = Integer.parseInt((String)dmWorkshopId.getSelectedItem());

		for (int i = 0; i < State.workshops.size(); ++i)
		{
			if (State.workshops.get(i).id == workshopId)
			{
				State.workshops.remove(i);
				--i;
			}
		}

		State.write();
		genWorkshopList();
		genDataEntry();
		genDataModification();
		genReports();
		frame.pack();
	}

	private static void
	setAttendance()
	{
		Workshop.Attendance attendance = Workshop.Attendance.valueOf((String)dmAttendance.getSelectedItem());

		if (dmAttendanceWorkshopId.getItemCount() == 0)
		{
			Util.showError("no workshops exist!");
			return;
		}

		int workshopId = Integer.parseInt((String)dmAttendanceWorkshopId.getSelectedItem());

		if (dmAttendanceEmployeeId.getItemCount() == 0)
		{
			Util.showError("no workshops exist!");
			return;
		}

		int employeeId = Integer.parseInt((String)dmAttendanceEmployeeId.getSelectedItem());

		Workshop workshop = State.getWorkshop(workshopId);
		for (int i = 0; i < workshop.employees.size(); ++i)
		{
			if (workshop.employees.get(i) == employeeId)
			{
				workshop.attendance.set(i, attendance);
				State.write();
				genWorkshopList();
				genDataModification();
				frame.pack();
				return;
			}
		}

		Util.showError("the selected employee is not registered for the selected workshop!");
	}

	private static void
	genReports()
	{
		// setup panel and children.
		reports.removeAll();
		rWorkshopId.removeAllItems();
		rEmployeeId.removeAllItems();
		rTiming.removeAllItems();

		reports.setLayout(new GridLayout(0, 1));
		reports.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		reports.add(new JLabel("Reports"));

		// populate comboboxes with needed values.
		for (Workshop w : State.workshops)
			rWorkshopId.addItem(Integer.toString(w.id));

		for (Employee e : State.employees)
			rEmployeeId.addItem(Integer.toString(e.id));

		for (Workshop.Timing t : Workshop.Timing.values())
			rTiming.addItem(t.name());
		
		// populate panel with report generation functionality.
		reports.add(new JLabel(" "));
		reports.add(new JLabel("Get employees assigned to workshop"));
		reports.add(rWorkshopId);
		JButton getEmployees = new JButton("Get employees");
		getEmployees.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				displayEmployees();
			}
		});
		reports.add(getEmployees);

		reports.add(new JLabel(" "));
		reports.add(new JLabel("Get employee's workshops"));
		reports.add(rEmployeeId);
		JButton getWorkshops = new JButton("Get workshops");
		getWorkshops.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				displayWorkshops();
			}
		});
		reports.add(getWorkshops);

		reports.add(new JLabel(" "));
		reports.add(new JLabel("Get workshop details by timing"));
		reports.add(rTiming);
		JButton getTimingDetails = new JButton("Get timing details");
		getTimingDetails.addActionListener(new ActionListener()
		{
			@Override
			public void
			actionPerformed(ActionEvent e)
			{
				displayTimingDetails();
			}
		});
		reports.add(getTimingDetails);
	}

	private static void
	displayEmployees()
	{
		if (rWorkshopId.getItemCount() == 0)
		{
			Util.showError("no workshops exist!");
			return;
		}

		int workshopId = Integer.parseInt((String)rWorkshopId.getSelectedItem());
		Workshop workshop = State.getWorkshop(workshopId);

		StringBuilder report = new StringBuilder();
		report.append("workshop " + workshop.title + " will be attended by:\n");
		for (int employeeId : workshop.employees)
		{
			Employee employee = State.getEmployee(employeeId);
			report.append("- [ID " + employeeId + "] " + employee.name + "\n");
		}

		Util.showReport(report.toString());
		genReports();
		frame.pack();
	}

	private static void
	displayWorkshops()
	{
		if (rEmployeeId.getItemCount() == 0)
		{
			Util.showError("no employees exist!");
			return;
		}

		int employeeId = Integer.parseInt((String)rEmployeeId.getSelectedItem());
		Employee employee = State.getEmployee(employeeId);

		StringBuilder report = new StringBuilder();
		report.append("employee " + employee.name + " will attend:\n");
		for (Workshop w : State.workshops)
		{
			if (w.employees.contains(employeeId))
				report.append("- [ID " + w.id + "] " + w.title + "\n");
		}

		Util.showReport(report.toString());
		genReports();
		frame.pack();
	}

	private static void
	displayTimingDetails()
	{
		Workshop.Timing timing = Workshop.Timing.valueOf((String)rTiming.getSelectedItem());

		StringBuilder report = new StringBuilder();
		for (Workshop w : State.workshops)
		{
			if (w.timing != timing)
				continue;

			report.append("workshop [ID " + w.id + "] " + w.title + " will be attended by:\n");
			for (int employeeId : w.employees)
			{
				Employee employee = State.getEmployee(employeeId);
				report.append("- [ID " + employee.id + "] " + employee.name + "\n");
			}
		}

		Util.showReport(report.toString());
		genReports();
		frame.pack();
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
		addScrollablePanel(dataModification);
		addScrollablePanel(reports);

		// generate initial state for everything.
		genEmployeeList();
		genFacilitatorList();
		genWorkshopList();
		genDataEntry();
		genDataModification();
		genReports();

		frame.pack();
		frame.setVisible(true);
	}
}
