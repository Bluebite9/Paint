package main;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import buttons.Button;
import drawings.Drawings;
import ids.ButtonIDs;
import toolBars.ColorToolBar;
import toolBars.FillToolBar;
import toolBars.ShapeToolBar;

public class Main {

	public static Main window;

	private ShapeToolBar shapeToolBar;
	private ColorToolBar colorToolBar;
	private FillToolBar fillToolBar;
	private Drawings drawings;

	Main() {
		// create the frame with panels; each panel represents an element in the frame
		JFrame frame = new JFrame("Titlu");
		JPanel mainPanel = new JPanel();
		JPanel panel1 = new JPanel(), panel2 = new JPanel(), panel3 = new JPanel(), panel4 = new JPanel();
		drawings = new Drawings();

		try {
			shapeToolBar = new ShapeToolBar();
			colorToolBar = new ColorToolBar();
			fillToolBar = new FillToolBar();

			panel1.add(shapeToolBar.getShapeToolBar());

			panel2.add(new Button(ButtonIDs.Clear).getButton());
			panel2.add(new Button(ButtonIDs.Undo).getButton());
			panel2.add(new Button(ButtonIDs.Redo).getButton());

			panel3.add(colorToolBar.getColorToolBar());

			panel4.add(fillToolBar.getToolBar());

			mainPanel.add(panel1);
			mainPanel.add(panel3);
			mainPanel.add(panel4);
			mainPanel.add(panel2);
			frame.getContentPane().add(mainPanel, BorderLayout.NORTH);
			frame.add(drawings);
		} catch (IOException e) {
			e.printStackTrace();
		}

		frame.setPreferredSize(new Dimension(1600, 900));
		frame.setMaximumSize(new Dimension(1600, 900));
		frame.setMinimumSize(new Dimension(1600, 900));

		frame.setResizable(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		window = new Main();
	}

	public ShapeToolBar getToolBar() {
		return shapeToolBar;
	}

	public Drawings getDrawings() {
		return drawings;
	}

}