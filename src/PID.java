//package controllersDemo;

import java.lang.Math;

/** A really simple PID controller written for this demo by
 * Mike Ounsworth
 */
public class PID extends Controller {

	public static String id = "PID";

	private PIDController pid;
	
	private double K_p, K_i, K_d;
	
	public PID ( double deadzone, double K_p, double K_i, double K_d, double max_val ) {
//		this.id = "PID";
		this.deadzone = deadzone;
		this.K_p = K_p;
		this.K_i = K_i;
		this.K_d = K_d;
		
		
		this.pid = new PIDController(K_p, K_i, K_d);
		pid.setInputRange(-max_val, max_val);
		pid.setOutputRange(-max_val, max_val);
		
		// I am going to feed it the difference between the traget and the mouse,
		// not the absolute mouse pos.
		pid.setSetpoint(0);
		
//		pid.setContinuous();
		pid.enable();
	}


	public void setP( double K_p ) {
		this.K_p = K_p;
	}
	
	public void setI( double K_i ) {
		this.K_i = K_i;
	}
	
	public void setD( double K_d ) {
		this.K_d = K_d;
	}

	public double getValue() {
//		pid.setSetpoint(setpoint);
		pid.setSetpoint(0);
		pid.setPID(K_p, K_i, K_d);
		int diff =  (int) value - (int) setpoint;
		
		if ( Math.abs(diff) <= deadzone )
			pid.setInput(0);
		else
			pid.setInput( diff );
		double vel = pid.performPID(); 
		value += vel;
		return value;
	}
}
