import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Window {

	private JFrame frame;	
	private JPanel control_panel;
	public Drawing view_panel;
	public Action action;
	
	private JLabel actualPoiLabel;
	private JTextField actualPoiTextField;
	private JTextField hiddenPoiTextField;
	private JTextField photoNumberTextField;
	private JTextField coverageThresholdTextField;
	
	private JLabel cameraAngleLable;
	private JTextField cameraAngleTextField;
	private JLabel focusLabel;
	private JTextField focusTextField;
	private JLabel rangeLabel;
	private JTextField rangeTextField;
	private JButton deleteButton;
	private JButton addButton;
	private JLabel poiDeleteLabel;
	private JTextField poiDeleteTextField;
	private JLabel poiAddLabel;
	private JTextField poiAddTextField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
		
		action = new Action(this);
		view_panel = new Drawing(this);
		view_panel.setBackground(Color.WHITE);
		view_panel.setBounds(Constant.minX, Constant.minY, Constant.maxX-Constant.minX, Constant.maxY-Constant.minY);
		frame.getContentPane().add(view_panel);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, Constant.Width, Constant.Height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		control_panel = new JPanel();
		control_panel.setBackground(new Color(100,200,255));
		control_panel.setBounds(0, 0, 227, 667);
		frame.getContentPane().add(control_panel);
		control_panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		actualPoiLabel = new JLabel("Actual POI");
		actualPoiLabel.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(actualPoiLabel);
		
		actualPoiTextField = new JTextField();
		actualPoiTextField.setHorizontalAlignment(SwingConstants.CENTER);
		actualPoiTextField.setText("8");
		control_panel.add(actualPoiTextField);
		actualPoiTextField.setColumns(10);
		
		JLabel hiddenPoiLabel = new JLabel("Hidden POI");
		hiddenPoiLabel.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(hiddenPoiLabel);
		
		hiddenPoiTextField = new JTextField();
		hiddenPoiTextField.setHorizontalAlignment(SwingConstants.CENTER);
		hiddenPoiTextField.setText("3");
		control_panel.add(hiddenPoiTextField);
		hiddenPoiTextField.setColumns(10);
		
		JLabel photoNumberLabel = new JLabel("Number of Photos");
		photoNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(photoNumberLabel);
		
		photoNumberTextField = new JTextField();
		photoNumberTextField.setHorizontalAlignment(SwingConstants.CENTER);
		photoNumberTextField.setText("70");
		control_panel.add(photoNumberTextField);
		photoNumberTextField.setColumns(10);
		
		JLabel coverageThresholdLabel = new JLabel("Coverage Threshold (Degree)");
		coverageThresholdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(coverageThresholdLabel);
		
		coverageThresholdTextField = new JTextField();
		coverageThresholdTextField.setHorizontalAlignment(SwingConstants.CENTER);
		coverageThresholdTextField.setText("30");
		control_panel.add(coverageThresholdTextField);
		coverageThresholdTextField.setColumns(10);
		
		poiDeleteLabel = new JLabel("POI Delete Threshold (Photo Num)");
		poiDeleteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(poiDeleteLabel);
		
		poiDeleteTextField = new JTextField();
		poiDeleteTextField.setText("5");
		poiDeleteTextField.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(poiDeleteTextField);
		poiDeleteTextField.setColumns(10);
		
		poiAddLabel = new JLabel("POI Add Threshold (Degree)");
		poiAddLabel.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(poiAddLabel);
		
		poiAddTextField = new JTextField();
		poiAddTextField.setText("80");
		poiAddTextField.setHorizontalAlignment(SwingConstants.CENTER);
		poiAddTextField.setColumns(10);
		control_panel.add(poiAddTextField);
		
		cameraAngleLable = new JLabel("Camera Angle (Degree)");
		cameraAngleLable.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(cameraAngleLable);
		
		cameraAngleTextField = new JTextField();
		cameraAngleTextField.setHorizontalAlignment(SwingConstants.CENTER);
		cameraAngleTextField.setText("60");
		control_panel.add(cameraAngleTextField);
		cameraAngleTextField.setColumns(10);
		
		focusLabel = new JLabel("Focus Length (Pixel)");
		focusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(focusLabel);
		
		focusTextField = new JTextField();
		focusTextField.setHorizontalAlignment(SwingConstants.CENTER);
		focusTextField.setText("100");
		control_panel.add(focusTextField);
		focusTextField.setColumns(10);
		
		rangeLabel = new JLabel("Photo Range (Pixel)");
		rangeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		control_panel.add(rangeLabel);
		
		rangeTextField = new JTextField();
		rangeTextField.setHorizontalAlignment(SwingConstants.CENTER);
		rangeTextField.setText("160");
		control_panel.add(rangeTextField);
		rangeTextField.setColumns(10);
		

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Constant.focus=Integer.parseInt(focusTextField.getText());
				Constant.range=Integer.parseInt(rangeTextField.getText());
				Constant.angle=Integer.parseInt(cameraAngleTextField.getText());
				
				action.DrawScenario(Integer.parseInt(actualPoiTextField.getText()),
						Integer.parseInt(hiddenPoiTextField.getText()),
						Integer.parseInt(coverageThresholdTextField.getText()),
						Integer.parseInt(photoNumberTextField.getText()),
						Integer.parseInt(poiDeleteTextField.getText()),
						Integer.parseInt(poiAddTextField.getText()));
				action.POIPhotoPair();
				action.DeletePOI();
				action.AddPOI();
				
				view_panel.Operation = Constant.DrawScenario;
				view_panel.repaint();
			}
		});
		control_panel.add(submitButton);
		
		JButton drawScenerioButton = new JButton("Draw Scenario");
		drawScenerioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view_panel.Operation = Constant.DrawScenario;
				view_panel.repaint();
			}
		});
		control_panel.add(drawScenerioButton);
		
		JButton pairButton = new JButton("POI-Photo Pair");
		pairButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				view_panel.Operation = Constant.POIPhotoPair;
				view_panel.repaint();
			}
		});
		control_panel.add(pairButton);
		
		deleteButton = new JButton("Delete POI");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view_panel.Operation = Constant.DeletePOI;
				view_panel.repaint();
			}
		});
		control_panel.add(deleteButton);
		
		addButton = new JButton("Add POI");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view_panel.Operation = Constant.AddPOI;		
				view_panel.repaint();
			}
		});
		control_panel.add(addButton);
	}
}
