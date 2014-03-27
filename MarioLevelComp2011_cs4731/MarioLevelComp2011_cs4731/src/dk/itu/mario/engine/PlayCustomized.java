package dk.itu.mario.engine;

import java.awt.*;

import javax.swing.*;

import dk.itu.mario.level.MyLevel;

public class PlayCustomized {

	public static void main(String[] args)
	{
	            MyLevel.argsStyle = args.length == 0 ? null : args[0];
		    	JFrame frame = new JFrame("Mario Experience Showcase");
		    	MarioComponent mario = new MarioComponent(640, 480,true);

		    	frame.setContentPane(mario);
		    	frame.setResizable(false);
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.pack();

		        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		        frame.setLocation((screenSize.width-frame.getWidth())/2, (screenSize.height-frame.getHeight())/2);

		        frame.setVisible(true);

		        mario.start();   
	}	

}
