package task9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Task9 extends JFrame {

	private ArrayList<Light> lights = new ArrayList<>();
	private static final boolean ON = true, OFF = false;
	private static final int WIDTH = 1200, HEIGHT = 500;
	private static final int BUTTON_WIDTH = 900;
	private static final int SOLN_WIDTH = 300;
	String[] lightString;
	String[] connections;
	private static JPanel solutionpanel;
	private static JPanel lightpanel;
	private static JTextField lightField;
	private static JTextField connectionField;

	public Task9(){
		generateGUI();
	}

	private void reloadLights(){
		if(lightField.getText().isEmpty()){
			JOptionPane.showMessageDialog(this, "Your lights cannot be empty.");
			return;
		}
		lights.clear();
		lightpanel.removeAll();
		load(lightField, connectionField);
	}

	private void load(JTextField lightField, JTextField connectionField){
				lightString = lightField.getText().split(" ");
				connections = connectionField.getText().split(" ");
				for(int i = 0; i < lightString.length; i++){
					Light l = new Light(lightString[i], ON, i);
					lights.add(l);
				}

				for(int i = 0; i < connections.length; i++){
					for(Light light : lights) {
						if(connections[i].startsWith(light.getLetter())) {
							String lightChar = connections[i].substring(1,2);
							int lightIndex = 0;
							for(Light l : lights){
								if(l.getLetter().equalsIgnoreCase(lightChar)){
									lightIndex = lights.indexOf(l);
								}
							}
							Light connectedLight = lights.get(lightIndex);
							light.addConnection(connectedLight);
						}
					}
				}

		for(Light light : lights){
			light.setOn();
		}
		generateLightsGUI();
	}

	public void generateGUI(){
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		JPanel mainpanel = new JPanel();
		mainpanel.setLayout(layout);

		solutionpanel = new JPanel();
		solutionpanel.setPreferredSize(new Dimension(SOLN_WIDTH, HEIGHT));
		solutionpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		solutionpanel.setLayout(new GridBagLayout());

		JLabel lightLabel = new JLabel("Lights: ");
		lightField = new JTextField();
		lightField.setPreferredSize(new Dimension(150, 24));
		lightField.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reloadLights();
			}
		});

		JLabel connectionLabel = new JLabel("Connections: ");
		connectionField = new JTextField();
		connectionField.setPreferredSize(new Dimension(150, 24));
		connectionField.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reloadLights();
			}
		});

		JButton loadButton = new JButton("Load Lights & Connections");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reloadLights();
			}
		});

		JButton solveStepsButton = new JButton("Solve Step By Step");

		JButton showButtonsToPress = new JButton("Show Buttons To Press To Solve");

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		solutionpanel.add(lightLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		solutionpanel.add(lightField, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		solutionpanel.add(connectionLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		solutionpanel.add(connectionField, constraints);

		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 2;
		solutionpanel.add(loadButton, constraints);

		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 3;
		solutionpanel.add(solveStepsButton, constraints);

		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 4;
		solutionpanel.add(showButtonsToPress, constraints);

		lightpanel = new JPanel();
		lightpanel.setLayout(new FlowLayout());
		lightpanel.setPreferredSize(new Dimension(BUTTON_WIDTH, HEIGHT));

		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		mainpanel.add(lightpanel, constraints);

		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		mainpanel.add(solutionpanel, constraints);

		setLayout(new SpringLayout());
		setTitle("Etude 9 - Lights With GUI");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		getContentPane().add(mainpanel);
		pack();
		setVisible(true);
	}

	private void generateLightsGUI(){

		ButtonListener buttonListener = new ButtonListener();

		for(int i = 0; i < lights.size(); i++){
			JButton button = new JButton(lights.get(i).getLetter());
			button.addActionListener(buttonListener);
			lightpanel.add(button);
			this.pack();
		}
		repaint();
	}

	public static void main(String[] args) {
		new Task9();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(Light l : lights){
			l.paint(g);
		}
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e){
			JButton button = (JButton) e.getSource();
			int i = 0;
			for(Light l : lights){
				if(l.getLetter().equalsIgnoreCase(button.getText())){
					l.toggleLight();
					repaint();
				}
			}
		}
	}

	private class Light{

		private boolean on;
		private ArrayList<Light> connections = new ArrayList<>();
		private String letter;
		private int num;

		public Light(String letter, boolean state, int num){
			this.letter = letter;
			this.on = state;
			this.num = num;
		}

		public void setOn(){
			on = true;
		}

		public void setOff(){
			on = false;
		}

		public void toggleLight(){
			on = !on;
			if(connections != null){
				for(Light l : connections){
					l.toggleLightNotConnections();
				}
			}
		}

		public void toggleLightNotConnections(){
			this.on = !on;
		}

		public boolean getOn(){
			return on;
		}

		public String getLetter(){
			return letter;
		}

		public void addConnection(Light light){
			if(!connections.contains(light)) connections.add(light);
		}

		public ArrayList<Light> getConnections(){
			return connections;
		}

		public void paint(Graphics g){
			if(getOn()) g.setColor(Color.YELLOW);
			else g.setColor(Color.BLACK);
			int x = 50 + (num * ((BUTTON_WIDTH-100) / (lights.size())));
			int y = HEIGHT / 3;
			int width = (BUTTON_WIDTH - 200)/lights.size();
			int height = width;
			g.fillOval( x, y, width, height);
		}

	}

}
