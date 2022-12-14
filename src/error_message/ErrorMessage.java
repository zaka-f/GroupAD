package error_message;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//Here builder design pattern was used

public class ErrorMessage extends JFrame{
	
	private JLabel message;
	private JPanel panel;


	// constructor
	public ErrorMessage(String text) {

		// password label
		message = new JLabel();
		message.setText(text);


		// Creating a panel
		panel = new JPanel();
		panel.setLayout(null);

		// set the position of the user name label
		BoundsBuilder oldBounds = new OldBoundsBuilder();
		BoundsPlanner boundsPlanner = new BoundsPlanner(oldBounds);
		boundsPlanner.makeBounds();
		ErrorBounds errorBounds = boundsPlanner.getErrorBounds();
		message.setBounds(errorBounds.getX(), errorBounds.getY(), errorBounds.getWidth(), errorBounds.getHeight());
		panel.add(message);


		// setting the panel in the frame
		add(panel);
		setTitle("Login Page");
		setSize(490, 170);
		setLocation(400,400);
		setVisible(true);
	}

}
