package net.tirimid.skillhub;

import javax.swing.*;

public class Util
{
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
