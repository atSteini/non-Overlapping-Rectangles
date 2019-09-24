package pack2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class Frame2 extends JFrame {

	public static JPanel topPanel, contentPane;
	public static JTextField txtCount, txtSize, txtMaxIter;
	public static JCheckBox chckExclude;
	public static JButton btnRun;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame2 frame = new Frame2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame2() {
		setTitle("Application 2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550);
		
		getLookAndFeel();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("File");
		menuBar.add(mnMenu);
		
		JMenuItem mntmClearPanel = new JMenuItem("Reset");
		mntmClearPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCount.setBackground(var.boxShooterPanel.getBackground());
				txtSize.setBackground(var.boxShooterPanel.getBackground());
				txtMaxIter.setBackground(var.boxShooterPanel.getBackground());
				txtCount.setText("100");
				txtSize.setText("35");
				txtMaxIter.setText("50000");
				chckExclude.setSelected(true);
				
				var.boxShooterPanel.clear();
				var.boxShooterPanel.repaint();
			}
		});
		mnMenu.add(mntmClearPanel);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Exit");
				
				System.exit(0);
			}
		});
		
		JSeparator separator = new JSeparator();
		mnMenu.add(separator);
		mnMenu.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		var.boxShooterPanel = new Panel2();
		FlowLayout flowLayout = (FlowLayout) var.boxShooterPanel.getLayout();
		flowLayout.setAlignOnBaseline(true);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		var.boxShooterPanel.setBackground(Color.WHITE);
		
		topPanel = new JPanel();
		topPanel.setBorder(null);
		topPanel.setBackground(SystemColor.window);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(var.boxShooterPanel, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
				.addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(var.boxShooterPanel, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
		);
		topPanel.setLayout(null);
		
		JLabel lblCount = DefaultComponentFactory.getInstance().createLabel("Count:");
		lblCount.setBounds(10, 18, 69, 14);
		topPanel.add(lblCount);
		
		txtCount = new JTextField();
		txtCount.setToolTipText("How many Rectangles there should be.");
		txtCount.setText("100");
		txtCount.setBounds(60, 15, 60, 20);
		topPanel.add(txtCount);
		txtCount.setColumns(10);
		
		JLabel lblSize = DefaultComponentFactory.getInstance().createLabel("Size:");
		lblSize.setBounds(150, 18, 69, 14);
		topPanel.add(lblSize);
		
		txtSize = new JTextField();
		txtSize.setToolTipText("How long each side of the Rectangle is.");
		txtSize.setText("35");
		txtSize.setBounds(200, 15, 60, 20);
		topPanel.add(txtSize);
		txtSize.setColumns(10);

		JLabel lblMaxIterations = DefaultComponentFactory.getInstance().createLabel("Max Iterations:");
		lblMaxIterations.setBounds(300, 18, 80, 14);
		topPanel.add(lblMaxIterations);
		
		txtMaxIter = new JTextField();
		txtMaxIter.setToolTipText("The Maximal Iterations for finding the perfect Alignment of each Rectangle.");
		txtMaxIter.setText("50000");
		txtMaxIter.setBounds(400, 15, 60, 20);
		topPanel.add(txtMaxIter);
		txtMaxIter.setColumns(10);
		
		JLabel lblExclude = DefaultComponentFactory.getInstance().createLabel("Exclude UI:");
		lblExclude.setBounds(510, 18, 80, 14);
		topPanel.add(lblExclude);
		
		chckExclude = new JCheckBox("exclude UI");
		chckExclude.setBounds(580, 14, 21, 21);
		chckExclude.setBackground(var.boxShooterPanel.getBackground());
		chckExclude.setSelected(true);
		topPanel.add(chckExclude);
		
		btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nStr = txtCount.getText();
				String sStr = txtSize.getText();
				String iStr = txtMaxIter.getText();
				boolean nCor = false, sCor = false, iCor = false;
				
				var.excludeUI = chckExclude.isSelected();
				var.exOnce = false;
				
				if(nStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in the Input Field: Count.", "Error: Empty Field", JOptionPane.ERROR_MESSAGE);
					var.correctInput = false;
					nCor = false;
				}else if(!isInt(nStr)) {
					JOptionPane.showMessageDialog(null, "The Input Value should be a whole Number. It may not contain any Letters.", "Error: Number Format", JOptionPane.ERROR_MESSAGE);
					var.correctInput = false;
					nCor = false;
				}else {
					if(Integer.parseInt(nStr) > 0) {
						var.inputCount = Integer.parseInt(nStr);
						var.correctInput = true;
						nCor = true;
					}else {
						var.correctInput = false;
						nCor = false;
					}
				}
				
				if(sStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in the Input Field: Size.", "Error: Empty Field", JOptionPane.ERROR_MESSAGE);
					var.correctInput = false;
					sCor = false;
				}else if(!isInt(sStr)) {
					JOptionPane.showMessageDialog(null, "The Input Value should be a whole Number. It may not contain any Letters.", "Error: Number Format", JOptionPane.ERROR_MESSAGE);
					var.correctInput = false;
					sCor = false;
				}else {
					if(Integer.parseInt(sStr) > 0) {
						var.inputSize = Integer.parseInt(sStr);
						var.correctInput = true;
						sCor = true;
					}else {
						var.correctInput = false;
						sCor = false;
					}
				}
				
				if(iStr.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in the Input Field: Max Iterations.", "Error: Empty Field", JOptionPane.ERROR_MESSAGE);
					var.correctInput = false;
					iCor = false;
				}else if(!isInt(iStr)) {
					JOptionPane.showMessageDialog(null, "The Input Value should be a whole Number. It may not contain any Letters.", "Error: Number Format", JOptionPane.ERROR_MESSAGE);
					var.correctInput = false;
					iCor = false;
				}else {
					if(Integer.parseInt(iStr) > 0) {
						var.maxIterations = Integer.parseInt(iStr);
						var.correctInput = true;
						iCor = true;
					}else {
						var.correctInput = false;
						iCor = false;
					}
				}
				
				txtCount.setBackground(nCor ? new Color(150, 255, 150) :  nStr.isEmpty() ? new Color(255, 255, 150) : new Color(255, 150, 150));	
				txtSize.setBackground(sCor ? new Color(150, 255, 150) :  sStr.isEmpty() ? new Color(255, 255, 150) : new Color(255, 150, 150));
				txtMaxIter.setBackground(iCor ? new Color(150, 255, 150) :  iStr.isEmpty() ? new Color(255, 255, 150) : new Color(255, 150, 150));
				
				var.boxShooterPanel.repaint();
			}
		});
		btnRun.setBounds(var.buttonX, 8, 90, 33);
		topPanel.add(btnRun);
		
		contentPane.setLayout(gl_contentPane);
	}
	
	/** Überprüft ob ein String ein Integer ist.
	 * @param string the String.
	 * @return the result.
	 */
	public static boolean isInt(String string) {
	    try {
	        Integer.parseInt(string);
	    } catch (NumberFormatException | NullPointerException n) {
	        return false;
	    }
	    return true;
	}
	
	/** Aus meiner andere Applikation herauskopiert (Quelle: StackOverFlow)
	 */
	public void getLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
