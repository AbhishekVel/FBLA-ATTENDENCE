import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;

public class GUI extends JFrame implements KeyListener{

	JFrame frame;
	int screenWidth;
	int screenHeight;
	JTextField tf = new JTextField();
	JLabel message = new JLabel();
	public GUI() throws IOException {
		frame = new JFrame("FBLA Attendence");
		Toolkit tk = Toolkit.getDefaultToolkit();
		screenWidth = (int)tk.getScreenSize().getWidth();
		screenHeight = (int)tk.getScreenSize().getHeight();
		frame.setSize(screenWidth, screenHeight);//1920 and 1080 for my laptop
		frame.setResizable(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		InputStream is	 = getClass().getResourceAsStream("/images/background.png");
		Image image  = ImageIO.read(is);
		frame.setContentPane(new ImagePanel(image));
		frame.setVisible(true);
		
		tf.setBounds(200, screenHeight/2, screenWidth-300, 100);
		tf.setFont(new Font("SansSerif", Font.ITALIC, 80));
		tf.setForeground(Color.red);;
		tf.setHorizontalAlignment(JTextField.CENTER);
		tf.setOpaque(false);
		frame.add(tf);
		
		/*message.setBounds(200, 900, 1000, 80);
		message.setFont(new Font("SansSerif", Font.ITALIC, 50));
		message.setHorizontalAlignment(JLabel.CENTER);
		frame.add(message);*/ // USING A POP UP BOX instead
		
		
		
		tf.addKeyListener(this);
		
		
	}
	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getSource() == tf) {
			if (ke.getKeyCode() == 10) {
				//handle the entry, send name to retrieve result
				try {
					Client.connect();
					Client.askForResult(tf.getText());
					String result = Client.readResponse();
					String[] titleAndMessage = result.split(":");
					handleMessage(titleAndMessage[0], titleAndMessage[1]);
				} catch (IOException e) {
					handleMessage("Error", "There was an error when handling entry!");
					e.printStackTrace();
				}
				tf.setText("");
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	private static void handleMessage(String title, String message) {
		Color color;
		if (title.equalsIgnoreCase("error"))
			color = Color.red;
		else
			color = Color.blue;
		
		 UIManager.put("OptionPane.background", color);
		 UIManager.put("Panel.background", color);
		 JTextPane jtp = new JTextPane();
		 jtp.setEditable(false);
		 jtp.setText(message);
		 jtp.setFont(new Font("Sanserif", Font.BOLD, 30));;
		 jtp.setBackground(color);
		JOptionPane.showMessageDialog(null, jtp, title, JOptionPane.INFORMATION_MESSAGE);	
	}
	

}
