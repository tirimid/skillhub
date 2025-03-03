package net.tirimid.skillhub;

import java.util.*;

public class Workshop
{
	public enum Location
	{
		BOARDROOM,
		CONFERENCE_HALL,
		TRAINING_ROOM
	};

	public enum Timing
	{
		MORNING,
		AFTERNOON,
		FULL_DAY
	};

	public enum Attendance
	{
		ATTENDED,
		DID_NOT_ATTEND
	};

	public int id;
	public String title;
	public int facilitatorId;
	public Location location;
	public Timing timing;

	// the program requirements actually wanted the assigned employees to
	// be stored separately from the workshop data.
	// however, that is such an amazingly horrid idea, that I simply cannot
	// do it in good conscience, so I have taken my liberties as a
	// programmer and implemented it differently.
	// the end-user experience is really the same.
	public ArrayList<Integer> employees;
	public ArrayList<Attendance> attendance;

	public
	Workshop(int id, String title, int facilitatorId, Location location, Timing timing)
	{
		this.id = id;
		this.title = title;
		this.facilitatorId = facilitatorId;
		this.location = location;
		this.timing = timing;
		this.employees = new ArrayList<Integer>();
		this.attendance = new ArrayList<Attendance>();
	}

	public
	Workshop(String title, int facilitatorId, Location location, Timing timing)
	{
		this.id = State.genWorkshopId();
		this.title = title;
		this.facilitatorId = facilitatorId;
		this.location = location;
		this.timing = timing;
		this.employees = new ArrayList<Integer>();
		this.attendance = new ArrayList<Attendance>();
	}
}
