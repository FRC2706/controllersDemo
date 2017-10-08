import javax.swing.*; 
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
public class ControllersDemo {

	private static double deadzone = 5; // px
	private static double frameRate = 60; // Hz

	
	private static double BB_vel = 200;
	private static double BB_onPrct = 100;
	private static double BB_pwrCycleRate = 30;
	private static double MA_alpha = 0.2;
	private static double K_p = 0.0;
	private static double K_i = 0.0;
	private static double K_d = 0.0;
	

	private static JFrame frame;
	private static TargetPanel targetPanel = new TargetPanel(new BangBang(deadzone, frameRate, BB_pwrCycleRate, BB_vel, BB_onPrct), new BangBang(deadzone, frameRate, BB_pwrCycleRate, BB_vel, BB_onPrct)); 
	private static JTextField deadzoneTF = null;
	
	private static JPanel pnlBBParams;
	private static JPanel pnlMAParams;
	private static JPanel pnlPIDParams;
	private static JPanel activeParamsPnl = new JPanel();
	
	private static Controller Xcontroller;
	private static Controller Ycontroller;
	
	
	private static String contrType;
	
	private static void setActiveController( String id ) {
		if ( id.equals( BangBang.id ) ) {
			contrType = id;
			Xcontroller = new BangBang( deadzone, frameRate, BB_pwrCycleRate, BB_vel, BB_onPrct );
			Ycontroller = new BangBang( deadzone, frameRate, BB_pwrCycleRate, BB_vel, BB_onPrct );
			activeParamsPnl.removeAll();
			
			
			activeParamsPnl.add(pnlBBParams);
		    frame.pack();
			frame.repaint();
		}
		else if ( id.equals( MovingAverage.id ) ) {
			contrType = id;
			Xcontroller = new MovingAverage( deadzone, MA_alpha );
			Ycontroller = new MovingAverage( deadzone, MA_alpha );
			activeParamsPnl.removeAll();
			
			
			activeParamsPnl.add(pnlMAParams);
		    frame.pack();
		} 
		else if ( id.equals( PID.id ) ) {
			contrType = id;
			Xcontroller = new PID( deadzone, K_p, K_i, K_d, 787 );
			Ycontroller = new PID( deadzone, K_p, K_i, K_d, 232 );
			
			activeParamsPnl.removeAll();
			activeParamsPnl.add(pnlPIDParams);
			frame.pack();
		}
		
		targetPanel.setXcontroller( Xcontroller );
		targetPanel.setYcontroller( Ycontroller );
//System.out.println("The controller is now " + contrType);
	}
	
	
    private static void setDeadzone (double dz) {    	
    	deadzone = dz;
    	Xcontroller.setDeadzone(dz);
    	Ycontroller.setDeadzone(dz);
    }
    
 	private static void setOnPrct( double onPrct ) {
 		BB_onPrct = onPrct;
 		if ( contrType.equals(BangBang.id) ) {
 			((BangBang) Xcontroller).setOnPrct(BB_onPrct);
 			((BangBang) Ycontroller).setVelocity(BB_onPrct);
 		}
 	}
 	
 	private static void setPwrCycleRate ( double rate ) {
 		BB_pwrCycleRate = rate;
 		if ( contrType.equals(BangBang.id) ) {
 			((BangBang) Xcontroller).setPwrCycleRate(BB_pwrCycleRate);
 			((BangBang) Ycontroller).setPwrCycleRate(BB_pwrCycleRate);
 		}
 	}
 	
 	private static void setAlpha( double alpha ) {
 		MA_alpha = alpha;
 		if ( contrType.equals(MovingAverage.id) ) {
 			((MovingAverage) Xcontroller).setAlpha(MA_alpha);
 			((MovingAverage) Ycontroller).setAlpha(MA_alpha);
 		}
 	}
 	
 	private static void setP( double p) {
 		K_p = p;
 		if ( contrType.equals(PID.id) ){
 			((PID) Xcontroller).setP(p);
 			((PID) Ycontroller).setP(p);
 		}
 	}

	
 	private static void setI( double i) {
 		K_i = i;
 		if ( contrType.equals(PID.id) ){
 			((PID) Xcontroller).setI(i);
 			((PID) Ycontroller).setI(i);
 		}
 	}
 	
 	
 	private static void setD( double d) {
 		K_d = d;
 		if ( contrType.equals(PID.id) ){
 			((PID) Xcontroller).setD(d);
 			((PID) Ycontroller).setD(d);
 		}
 	}

	 public static void main(String[] args) {
		
		//Create and set up the window.
        frame = new JFrame("Controllers Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout ( new BorderLayout() );
	
	
		JPanel controllerSelectPanel = new JPanel();
		controllerSelectPanel.setLayout( new GridLayout(1,8) );
        //Add the ubiquitous "Hello World" label.
        JLabel lblController = new JLabel("Controller: ");
		lblController.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

	
		
		// setup combo box
		String[] comboBoxStr = new String[3];
		comboBoxStr[0] = BangBang.id;
		comboBoxStr[1] = MovingAverage.id;
		comboBoxStr[2] = PID.id;
		

		//Create the combo box, select item at index 0.
		JComboBox controllerListBox = new JComboBox(comboBoxStr);
		controllerListBox.setSelectedIndex(0);
		controllerListBox.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox)e.getSource();
					String id = (String)cb.getSelectedItem();
				
					setActiveController( id );
				}	
			} );
		
		
		JLabel lblDZ = new JLabel("Deadzone: ");
		lblDZ.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		
		final JTextField tfDZ = new JTextField( ""+(int)deadzone, 5);
		tfDZ.addActionListener ( new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					double newDZ = 0;
					try {
						newDZ = Double.parseDouble(tfDZ.getText() );
					} catch (NumberFormatException e) {
						tfDZ.setText("0");
						return;
					}
					
					if (newDZ < 0) {
						newDZ = 0;
						tfDZ.setText("0");
					}
					
					setDeadzone(newDZ);
				}
			});
		
		JLabel lblPx = new JLabel("px");
				
        controllerSelectPanel.add(lblController);
		controllerSelectPanel.add(controllerListBox);
		controllerSelectPanel.add(lblDZ);
		controllerSelectPanel.add(tfDZ);
		controllerSelectPanel.add(lblPx);
		
		// setup the parameters panels
		
		
		
		// Bang Bang parameters
		
		pnlBBParams = new JPanel();
		pnlBBParams.setLayout( new GridLayout(6,1) );
	
		JLabel lblBB_descr0 = new JLabel("A Bang Bang controller works on an on-off type device.\n ");
		JLabel lblBB_descr1 = new JLabel("Cycle the power quickly leaving it on for some % of the time,");
		JLabel lblBB_descr2 = new JLabel("then you can emulate a variable speed controller.");
		JLabel lblBB_descr3 = new JLabel("This is great for things like flywheels which want full power");
		JLabel lblBB_descr4 = new JLabel("(power = voltage = torque) but not full speed.");
		JLabel lblBB_descr5 = new JLabel("(Hint: lower the frame rate to see that it is on-off movement)");
	
		JLabel lblOnPrct = new JLabel("Speed (%): ");
		lblOnPrct.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
	
		JSlider sldrOnPrct = new JSlider(JSlider.HORIZONTAL,
	                                  /*min*/0, /*max*/100, /*init*/(int)BB_onPrct);
	  	sldrOnPrct.setMajorTickSpacing(10);
		sldrOnPrct.setMinorTickSpacing(10);
		sldrOnPrct.setPaintTicks(true);
		sldrOnPrct.setPaintLabels(true);
	
		sldrOnPrct.addChangeListener( new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider)e.getSource();
					if (!source.getValueIsAdjusting()) {
						setOnPrct( (double)source.getValue() );
					}
				}
			});
			
		JLabel lblFrameRate = new JLabel("Cycle Rate (Hz): ");
		lblFrameRate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
	
		JSlider sldrFrameRate = new JSlider(JSlider.HORIZONTAL,
	                                  /*min*/0, /*max*/40, /*init*/(int)BB_pwrCycleRate);
	  	sldrFrameRate.setMajorTickSpacing(5);
		sldrFrameRate.setMinorTickSpacing(1);
		sldrFrameRate.setPaintTicks(true);
		sldrFrameRate.setPaintLabels(true);
	
		sldrFrameRate.addChangeListener( new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider)e.getSource();
					if (!source.getValueIsAdjusting()) {
						setPwrCycleRate ( (double)source.getValue() );
					}
				}
			});
	
	
		pnlBBParams.add(lblBB_descr0);
		pnlBBParams.add(lblBB_descr1);
		pnlBBParams.add(lblBB_descr2);
		pnlBBParams.add(lblBB_descr3);
		pnlBBParams.add(lblBB_descr4);
		pnlBBParams.add(lblBB_descr5);
		pnlBBParams.add(lblOnPrct);
		pnlBBParams.add(sldrOnPrct);
		pnlBBParams.add(lblFrameRate);
		pnlBBParams.add(sldrFrameRate);
				
		
		// Moving Average parameters
		
		pnlMAParams = new JPanel();
		pnlMAParams.setLayout( new GridLayout(4,2) );
		
		JLabel lblMA_descr0 = new JLabel("A moving average controller is a smoother - it's good if the input is\n ");
		JLabel lblMA_descr1 = new JLabel("noisy, jittery, or changes too quickly, (ex. driver has shakey hands).\n ");
		JLabel lblMA_descr2 = new JLabel("The one parameter 'alpha' controls how strongly it takes\n ");
		JLabel lblMA_descr3 = new JLabel("the new value compared to the accumulated average.");
		JLabel lblMA_descr4 = new JLabel("value = alpha*newValue + (1-alpha)*value");
	
		JLabel lblAlpha = new JLabel("alpha (%): ");
		lblAlpha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
	
		JSlider sldrAlpha = new JSlider(JSlider.HORIZONTAL,
	                                  /*min*/0, /*max*/100, /*init*/20);
	  	sldrAlpha.setMajorTickSpacing(10);
		sldrAlpha.setMinorTickSpacing(15);
		sldrAlpha.setPaintTicks(true);
		sldrAlpha.setPaintLabels(true);
	
		sldrAlpha.addChangeListener( new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider)e.getSource();
					if (!source.getValueIsAdjusting()) {
						setAlpha( (double)source.getValue() / 100.0 );
					}
				}
			});
	
	
	
		pnlMAParams.add(lblMA_descr0);		
		pnlMAParams.add(lblMA_descr1);		
		pnlMAParams.add(lblMA_descr2);		
		pnlMAParams.add(lblMA_descr3);		
		pnlMAParams.add(lblMA_descr4);
		pnlMAParams.add(new JLabel("") );		
		pnlMAParams.add(lblAlpha);
		pnlMAParams.add(sldrAlpha);
		
		
		// PID parameters
		
		pnlPIDParams = new JPanel();
		pnlPIDParams.setLayout( new GridLayout(6,1) );
		
		JLabel lblPID_descr0 = new JLabel("A PID controller is a far more complicated beast.");
		JLabel lblPID_descr1 = new JLabel("This example (chasing a mouse) is too simple to show off its power.");
		
		JLabel lblP = new JLabel("P (%): for converting units (ex. rpm to voltage) ");
		lblP.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
	
		JSlider sldrP = new JSlider(JSlider.HORIZONTAL,
	                                  /*min*/0, /*max*/100, /*init*/(int)(100*K_p));
	  	sldrP.setMajorTickSpacing(10);
		sldrP.setMinorTickSpacing(5);
		sldrP.setPaintTicks(true);
		sldrP.setPaintLabels(true);
	
		sldrP.addChangeListener( new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider)e.getSource();
					if (!source.getValueIsAdjusting()) {
						setP( (double)source.getValue() / 100.0 );
					}
				}
			});
		
		
		JLabel lblI = new JLabel("I (%): moving average");
		lblI.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
	
		JSlider sldrI = new JSlider(JSlider.HORIZONTAL,
	                                  /*min*/0, /*max*/100, /*init*/(int)(100*K_i));
	  	sldrI.setMajorTickSpacing(10);
		sldrI.setMinorTickSpacing(5);
		sldrI.setPaintTicks(true);
		sldrI.setPaintLabels(true);
	
		sldrI.addChangeListener( new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider)e.getSource();
					if (!source.getValueIsAdjusting()) {
						setI( (double)source.getValue() / 100.0 );
					}
				}
			});
			
		JLabel lblD = new JLabel("D (%): response to fast change");
		lblD.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
	
		JSlider sldrD = new JSlider(JSlider.HORIZONTAL,
	                                  /*min*/0, /*max*/100, /*init*/(int)(100*K_d));
	  	sldrD.setMajorTickSpacing(10);
		sldrD.setMinorTickSpacing(5);
		sldrD.setPaintTicks(true);
		sldrD.setPaintLabels(true);
	
		sldrD.addChangeListener( new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider)e.getSource();
					if (!source.getValueIsAdjusting()) {
						setD( (double)source.getValue() / 100.0 );
					}
				}
			});
		
		
		pnlPIDParams.add(lblPID_descr0);
		pnlPIDParams.add(lblPID_descr1);
		pnlPIDParams.add(lblP);
		pnlPIDParams.add(sldrP);
		pnlPIDParams.add(lblI);
		pnlPIDParams.add(sldrI);
		pnlPIDParams.add(lblD);
		pnlPIDParams.add(sldrD);
		
		
		
		
		// add stuff to the frame
		
		frame.add(controllerSelectPanel, BorderLayout.NORTH);
				
		frame.add(activeParamsPnl, BorderLayout.CENTER);
		
		// add the mouse-interactive window
		
		frame.add( targetPanel, BorderLayout.SOUTH );
		
		
        //Display the window.
        frame.pack();
        frame.setVisible(true);
		
		
		setActiveController( BangBang.id );
		
		while (true) {
		
			
			frame.repaint();
			try {
				Thread.sleep( (int) (1000/ frameRate) ); 
			}catch (InterruptedException e) {
			}
		}
		
	
	}


}
