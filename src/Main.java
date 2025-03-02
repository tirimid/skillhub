package net.tirimid.skillhub;

public class Main
{
	public static void
	main(String[] Args)
	{
		State.read();
		State.removeOrphans();
		if (State.validate() != 0)
			return;
		UI.run();
	}
}
