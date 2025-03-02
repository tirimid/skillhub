package net.tirimid.skillhub;

public class Workshop
{
	public enum Timing
	{
		Morning,
		Afternoon,
		FullDay
	};

	public int id;
	public String title;
	public int facilitatorId;
	public String location;
	public Timing timing;

	public
	Workshop(int id, String title, int facilitatorId, String location, Timing timing)
	{
		this.id = id;
		this.title = title;
		this.facilitatorId = facilitatorId;
		this.location = location;
		this.timing = timing;
	}

	public
	Workshop(String title, int facilitatorId, String location, Timing timing)
	{
		this.id = State.genWorkshopId();
		this.title = title;
		this.facilitatorId = facilitatorId;
		this.location = location;
		this.timing = timing;
	}

	public static Timing
	parseTiming(String str) throws Exception
	{
		if (str.equals("morning"))
			return Timing.Morning;
		else if (str.equals("afternoon"))
			return Timing.Afternoon;
		else if (str.equals("full day"))
			return Timing.FullDay;
		else
			throw new Exception("invalid timing value");
	}

	public static String
	timingToString(Timing timing)
	{
		switch (timing)
		{
		case Morning:
			return "morning";
		case Afternoon:
			return "afternoon";
		case FullDay:
			return "full day";
		}

		return null; // shut up compiler.
	}
}
