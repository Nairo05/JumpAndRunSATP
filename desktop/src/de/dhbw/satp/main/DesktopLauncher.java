package de.dhbw.satp.main;

import static de.dhbw.satp.main.SelectionsCheck.combobox;
import static de.dhbw.satp.main.SelectionsCheck.l1;
import static de.dhbw.satp.main.SelectionsCheck.l2;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import javax.swing.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	public static final String LOGO_PATH = "playersprite/logo.png";

	public static JFrame frame;
	public static JRadioButton r1;
	public static JRadioButton r2;
	public static JRadioButton r3;
	public static JComboBox debugSelection;
	public static JComboBox resolutionSelection;


	public static void main (String[] arg) {

		frame = new JFrame();
		frame.setBounds(600,400, 600,400);
		frame.setTitle("Craniums Adventure: Launcher");
		Image icon = new ImageIcon(LOGO_PATH).getImage();
		frame.setIconImage(icon);

		JPanel p1 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();

		JLabel fps =new JLabel("   FPS:");
        r1 = new JRadioButton("30");
		r2 = new JRadioButton("60");
		r3 = new JRadioButton("144");
		r2.setSelected(true);
		p1.add(fps);
		p1.add(r1);
		p1.add(r2);
		p1.add(r3);
		ButtonGroup bg=new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		bg.add(r3);

	    p1.setLayout(new GridLayout(4,3));
		JButton jButton = new lButton("");
		jButton.setText("START");
		jButton.setSize(100,60);

		SelectionsCheck obj = new SelectionsCheck();
		frame.setLayout(new FlowLayout());
		frame.setLayout(null);
		JLabel entwickler = new JLabel("Entwicklermodus: ");
		String[] s3 = new String[]{"aus", "an"};
		debugSelection = new JComboBox<>(s3);

		JLabel graphik = new JLabel("Grafik :");
		String[] s2 = new String[]{"hoch","niedrig"};
		resolutionSelection = new JComboBox <>(s2);
		String[] s1 = new String[]{"1200*624","1280*720","1920*1080"};

		combobox = new JComboBox <>(s1);
		combobox.addItemListener(obj);
		l1 = new JLabel("Fenster");
		l2 = new JLabel("1200*624");
		JPanel p2 = new JPanel();
		l2.setForeground(Color.gray);

		p2.add(l1);
		p2.add(combobox);
		p2.add(l2);

        JLabel leer = new JLabel("");
		JLabel leer1 = new JLabel("");
		jButton.setBounds(500,500,200,100);

		p4.add(leer);
		p4.add(leer1);
		p4.add(jButton);

        p2.add(graphik);
		p2.add(resolutionSelection);
		p2.setLayout(new GridLayout(6 ,2));

		p4.setLayout(new GridLayout(3,3));
		p4.setLocation(500,350);

		p3.add(entwickler);
		p3.add(debugSelection);


		frame.add(p1);
		frame.add(p2);
		frame.add(p3);
		frame.add(p4);

		r1.addActionListener(new ButtonListener2());
		r3.addActionListener(new ButtonListener3());
		debugSelection.addActionListener(new ButtonListener4());
		jButton.addActionListener(new ButtonListener1());
		resolutionSelection.addActionListener(new ButtonListener5());

		frame.setResizable(false);
		frame.setLayout(new GridLayout(2,2));
		frame.setVisible(true);
	}

		public static  class ButtonListener1 implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
				config.setTitle("Craniums Adventure");
				config.setForegroundFPS(NotFinalStatics.FOREGROUND_FPS);
				config.setWindowedMode(NotFinalStatics.WINDOW_WIDTH, NotFinalStatics.WINDOW_HEIGHT);
				config.setWindowIcon(LOGO_PATH);
				frame.dispose();
				new Lwjgl3Application(new JumpAndRunMain(), config);
			}
		}

		public static class ButtonListener2 implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				NotFinalStatics.FOREGROUND_FPS = 30 ;
			}
		}

		public static class ButtonListener3 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			NotFinalStatics.FOREGROUND_FPS = 144 ;
			}
		}

		public static class ButtonListener4 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			String item= (String) debugSelection.getSelectedItem();
			String o = "an";
			if (o.equals(item)) {
				NotFinalStatics.debug = true;
			}
		}

	}

	public static class ButtonListener5 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			String item = (String) resolutionSelection.getSelectedItem();
			String a = "4096*2160";
			String b = "1280*720";
			String c = "1920*1080";
			if (a.equals(item)) {
				NotFinalStatics.WINDOW_WIDTH = 4096;
				NotFinalStatics.WINDOW_HEIGHT = 2160;
			} else if (b.equals(item)) {
				NotFinalStatics.WINDOW_WIDTH = 1280;
				NotFinalStatics.WINDOW_HEIGHT = 720;
			} else if (c.equals(item)) {
				NotFinalStatics.WINDOW_WIDTH = 1920;
				NotFinalStatics.WINDOW_HEIGHT = 1080;
			}
		}
	}





}


