package net.tirimid.skillhub;

import java.awt.*;
import javax.swing.*;

public class HintTextField extends JTextField
{
	private String hint;

	public
	HintTextField(int width, String hint)
	{
		super(width);
		this.hint = hint;
	}

	@Override
	public void
	paint(Graphics g)
	{
		super.paint(g);

		if (getText().isEmpty())
		{
			int h = getHeight();
			Insets ins = getInsets();
			FontMetrics fm = g.getFontMetrics();
			int c0 = getBackground().getRGB();
			int c1 = getForeground().getRGB();
			int m = 0xfefefefe;
			int c2 = ((c0 & m) >> 1) + ((c1 & m) >> 1);
			g.setColor(new Color(c2, true));
			g.drawString(
				hint,
				ins.left,
				h / 2 + fm.getAscent() / 2 - 2
			);
		}
	}
}
