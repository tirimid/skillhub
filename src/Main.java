package net.tirimid.skillhub;

import java.awt.*;
import javax.swing.*;

public class Main
{
	public static void
	main(String[] Args)
	{
		State.read();
		if (State.validate() != 0)
			return;
		State.dump();
	}

	/*
	public static void
	main(String[] Args)
	{
		// make employees panel.
		{
		}

		// make main panel.
		JPanel mainPanel = new JPanel();
		{
			panel.add(btn);
			panel.setOpaque(true);
		}

		JFrame frame = new JFrame("SkillHub");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(mainPanel);
		frame.pack();
		frame.setVisible(true);
	}
	*/
}
