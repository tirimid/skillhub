package net.tirimid.skillhub;

import javax.swing.*;

public class Util
{
	public static void
	showReport(String msg)
	{
		JOptionPane.showMessageDialog(
			null,
			msg,
			"Skillhub - Report",
			JOptionPane.INFORMATION_MESSAGE
		);
	}

	public static void
	showError(String msg)
	{
		JOptionPane.showMessageDialog(
			null,
			msg,
			"Skillhub - Error",
			JOptionPane.ERROR_MESSAGE
		);
	}
}
