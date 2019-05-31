package task9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
	private static JLabel solutionLabel = new JLabel();
	private static ArrayList<JButton> buttons = new ArrayList<>();

	private static int length;
	private static int[] solution;
	private static int numberOff = 0;
	private static ArrayList<int[]> edges = new ArrayList<>();

	private static int step = 0;
	private ArrayList<String> stepArray;

	public Task9(){
		generateGUI();
	}

	private void reloadLights(){
		stepArray = null;
		step = 0;
		solutionLabel.setVisible(false);
		if(lightField.getText().isEmpty()){
			JOptionPane.showMessageDialog(this, "Your lights cannot be empty.");
			return;
		}
		lights.clear();
		lightpanel.removeAll();
		load(lightField, connectionField);
		setupSolution();
	}

	private void load(JTextField lightField, JTextField connectionField){
		buttons.clear();
		lightString = lightField.getText().split(" ");
		connections = connectionField.getText().split(" ");
		for(int i = 0; i < lightString.length; i++){
			Light l = new Light(lightString[i], ON, i);
			for(Light lite : lights){
				if(lite.getLetter().equalsIgnoreCase(l.getLetter())){
					JOptionPane.showMessageDialog(this, "You cannot have duplicate light letters");
					return;
				}
			}
			lights.add(l);
		}
		if(connections.length >= 1){
			if(connections[0].length() == 2) {
				for (int i = 0; i < connections.length; i++) {
					if (connections[i].length() > 2 || connections[i].length() < 2) {
						JOptionPane.showMessageDialog(this, "Your connections are incorrect.");
						return;
					}
					for (Light light : lights) {
						if (connections[i].startsWith(light.getLetter())) {
							String lightChar = connections[i].substring(1, 2);
							int lightIndex = 0;
							for (Light l : lights) {
								if (l.getLetter().equalsIgnoreCase(lightChar)) {
									lightIndex = lights.indexOf(l);
								}
							}
							Light connectedLight = lights.get(lightIndex);
							light.addConnection(connectedLight);
						}
					}

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

		JButton loadButton = new JButton("Load Lights & Connections (Reset)");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reloadLights();
			}
		});

		JButton solveStepsButton = new JButton("Solve Step By Step");
		solveStepsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showStepByStep();
			}
		});

		JButton showButtonsToPress = new JButton("Show Buttons To Press To Solve");
		showButtonsToPress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSolution();
			}
		});

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

		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 5;
		solutionLabel.setVisible(false);
		solutionpanel.add(solutionLabel, constraints);

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
		repaint();
		setVisible(true);
	}

	private void generateLightsGUI(){

		ButtonListener buttonListener = new ButtonListener();

		for(int i = 0; i < lights.size(); i++){
			final int j = i;
			JButton button = new JButton(lights.get(i).getLetter());
			button.addActionListener(buttonListener);

			buttons.add(button);
			lightpanel.add(button);
			pack();
		}
		for(Light l : lights){
			l.calculateCoords();
		}
		repaint();
	}

	private void setupSolution(){
		edges.clear();
		numberOff = 0;
		length = lights.size();
		String[] edge = connections;
		int[] lightConfig = new int[length];
		int[] switchConfig = new int[length];
		for(int i = 0; i < length; i++){
			lightConfig[i] = 1;
			switchConfig[i] = 0;
			int[] local = new int[length];
			for (int j = 0; j < length; j++) {
				if(i==j) local[j] = 1;
				else local[j] = 0;
			}
			edges.add(local);
		}

		setSolution(switchConfig);

		if(connections.length >= 1){
			if(connections[0].length() == 2) {
				for (String a : edge) {
					String[] corners = a.split("");
					if (corners.length != 2) {
						JOptionPane.showMessageDialog(this, "Your connections are incorrect.");
						return;
					}
					String first = corners[0].toUpperCase();
					String second = corners[1].toUpperCase();
					int index = -1, index2 = -1;
					for (int i = 0; i < length; i++) if (lights.get(i).getLetter().equalsIgnoreCase(first)) index = i;
					for (int i = 0; i < length; i++) if (lights.get(i).getLetter().equalsIgnoreCase(second)) index2 = i;
					if (index < 0 || index2 < 0) {
						JOptionPane.showMessageDialog(this, "Your connections are incorrect.");
						return;
					}
					edges.get(index)[index2] = 1;
				}
			}
		}


		new Node(switchConfig, lightConfig);

		stepArray = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		sb.append("Press switches in order to solve: ");
		boolean comma = false;
		for(int i = 0; i < solution.length; i++){
			if(solution[i] != 0){
				if(comma) sb.append(", ");
				sb.append(lights.get(i).getLetter().toUpperCase());
				stepArray.add(lights.get(i).getLetter().toUpperCase());
				comma = true;
			}
		}
		sb.append(".");
		solutionLabel.setText("<html>" + sb.toString() + "</html>");
	}

	private void showSolution(){
		solutionLabel.setVisible(true);
	}

	private void showStepByStep(){
		if(step == 0) reloadLights();
		if(step >= stepArray.size()) {
			JOptionPane.showMessageDialog(this, "Already solved!");
			return;
		}
		for(int i = step; i < length; i++){
			for(int j = i; j < length; j++){
				if(stepArray.get(i).equalsIgnoreCase(lights.get(j).getLetter())){
					step++;
					lights.get(j).toggleLight();
					return;
				}
			}
		}
	}

	public static void main(String[] args) {
		new Task9();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(Light l : lights){
			l.paint(g);
			for(JButton b : buttons){
				if(b.getText().equalsIgnoreCase(l.getLetter())){
					g.setColor(Color.BLACK);
					g.drawLine(b.getLocation().x + 36, b.getLocation().y + 50, l.x + (l.width/2), l.y);
					for(Light conn : l.getConnections()){
						g.setColor(Color.BLACK);
						g.drawLine(b.getLocation().x + 36, b.getLocation().y + 50, conn.x + (conn.width/2), l.y);
					}
				}
			}
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

	private static void setSolution(int[] switches) {
		solution = new int[length];
		System.arraycopy(switches, 0, solution, 0, length);
	}

	public static class Node {
		int[] switches;
		int[] lights;
		Node childOn, childOff, parent;
		int depth;
		boolean on;

		int[] getSwitches() {
			return switches;
		}

		int[] getLights() {
			return lights;
		}

		int getDepth() {
			return depth;
		}

		Node(boolean on, Node parent) {
			if(numberOff == length) return;
			this.parent = parent;
			this.switches = parent.getSwitches();
			this.lights = parent.getLights();
			this.depth = parent.getDepth() + 1;
			if(on) {
				for (int i = 0; i < length; i++) lights[i] += edges.get(depth)[i];
				switches[depth]++;
			} else {
				for (int i = 0; i < length; i++) lights[i] -= edges.get(depth)[i];
				switches[depth]--;
			}
			int count = 0;
			for(int onOff : lights) if (onOff % 2 == 0) count++;
			if(count > numberOff) {
				setSolution(getSwitches());
				numberOff = count;
			}
			if(this.depth < length - 1) this.childOn = new Node(true, this);
			if(this.depth < length - 1) this.childOff = new Node(false, this);
			this.on = on;
		}

		Node(int[] switchConfig, int[] lightConfig) {
			this.switches = switchConfig;
			this.lights = lightConfig;
			this.depth = -1;
			this.childOn = new Node(true, this);
			this.childOff = new Node(false, this);
		}
	}

	private class Light{

		private boolean drawConnection;
		private boolean on;
		private ArrayList<Light> connections = new ArrayList<>();
		private String letter;
		private int num;
		private int x, y, width, height;

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
			repaint();
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

		public void setDrawConnection(boolean draw){
			this.drawConnection = draw;
		}

		public void calculateCoords(){
			x = 50 + (num * ((BUTTON_WIDTH-100) / (lights.size())));
			y = HEIGHT / 3;
			width = (BUTTON_WIDTH - 200)/lights.size();
			height = width;
		}

		public void paint(Graphics g){
			//Draws light circle
			if(getOn()) g.setColor(Color.YELLOW);
			else g.setColor(Color.BLACK);

			g.fillOval(x, y, width, height);

			//Draws connections
			if(drawConnection){
				g.setColor(Color.RED);
				g.drawOval(x, y, width, height);
			}

		}

	}

}
