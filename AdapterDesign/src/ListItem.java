import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

public class ListItem implements Comparable<ListItem> {
	// Added valB
	private int valA;
	private int valB;

	// Changed constructor to accept a second value
	public ListItem(int valA, int valB) {
		this.valA = valA;
		this.valB = valB;
	}

	public int getValA() {
		return valA;
	}
	
	// Getter for valB
	public int getValB() {
		return valB;
	}

	public Icon createIcon(int diameter) {
		return new ItemIcon(this, diameter);
	}

	@Override
	public int compareTo(ListItem o) {
		return Integer.compare(o.getValA(), this.valA);
	}

	public class ItemIcon implements Icon {
		private final static int DEFAULT_THICKNESS = 5;
		private final static int DEFAULT_DIAMETER = 50;

		private int width = DEFAULT_DIAMETER;
		private int height = DEFAULT_DIAMETER;

		private int thickness = DEFAULT_THICKNESS;
		private int borderSize = DEFAULT_THICKNESS;

		private int valA;
		private int valB;

		public ItemIcon(int valA, int valB) {
			this.valA = valA;
			this.valB = valB;
		}

		public ItemIcon(int valA, int valB, int diameter) {
			this(valA, valB);
			setDiameter(diameter);
		}

		public ItemIcon(ListItem item) {
			this.valA = item.getValA();
			this.valB = item.getValB();
		}

		public ItemIcon(ListItem item, int diameter) {
			this(item);
			setDiameter(diameter);
		}

		public void setDiameter(int diameter) {
			width = diameter;
			height = diameter;
		}

		public int getIconHeight() {
			return height + 2 * DEFAULT_THICKNESS;
		}

		public int getIconWidth() {
			return width + 2 * DEFAULT_THICKNESS;
		}

		public void paintIcon(Component comp, Graphics g, int x, int y) {
			// get graphics context
			Graphics2D g2 = (Graphics2D) g;

			// set stroke size and color
			g2.setPaint(Color.BLACK);
			Stroke s = new BasicStroke(thickness);
			g2.setStroke(s);

			// draw white-filled circle with red border
			Ellipse2D e1 = new Ellipse2D.Float(x + borderSize, y + borderSize, height, width);
			g2.draw(e1);
			g2.setPaint(Color.WHITE);
			g2.fill(e1);
			g2.setPaint(Color.RED);

			// draw text
			Font f = new Font("Arial", Font.BOLD, 14);
			FontMetrics fm = g.getFontMetrics(f);
			// Added valB to string
			String str = ((Integer) valA).toString() + ", " + ((Integer) valB).toString();
			Rectangle2D rect = fm.getStringBounds(str, g2);

			int textHeight = (int) rect.getHeight();
			int textWidth = (int) rect.getWidth();
			int panelHeight = getIconHeight();
			int panelWidth = getIconWidth();

			// Center text horizontally and vertically
			int offsetX = (panelWidth - textWidth) / 2;
			int offsetY = (panelHeight - textHeight) / 2 + fm.getAscent();

			// Draw the string.
			g2.setFont(f);
			g2.drawString(str, x + offsetX, y + offsetY);
		}
	}
}