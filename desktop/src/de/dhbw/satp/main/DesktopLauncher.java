package de.dhbw.satp.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import javax.swing.*;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	private static JFrame frame;

	public static void main (String[] arg) {

		frame = new JFrame();
		frame.setBounds(600,400, 600,400);
		frame.setLayout(new FlowLayout());
		frame.setLayout(null);
		JLabel jLabel = new JLabel();
		jLabel. setBounds(600,400,600,400);
		frame.setTitle("Craniums Adventure: Launcher");
		frame.setVisible(true);
		JButton jButton = new lButton("");
		jButton.setText("START");
		jButton.setBounds(250,250,10,15);
		jButton.setSize(100,50);
		frame.add(jLabel);
		frame.add(jButton);
		jButton.addActionListener(new ButtonListener());
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.BLACK);
		Image icon = new ImageIcon("assets/menu/ui/pngwing.png").getImage();
		frame.setIconImage(icon);

	}

		public static  class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
				config.setTitle("Craniums Adventure");
				config.setForegroundFPS(FinalStatics.FOREGROUND_FPS);
				config.setWindowedMode(FinalStatics.WINDOW_WIDTH, FinalStatics.WINDOW_HEIGHT);
				frame.dispose();
				new Lwjgl3Application(new JumpAndRunMain(), config);
			}


		}

}


