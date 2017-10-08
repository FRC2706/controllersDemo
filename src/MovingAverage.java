//package controllersDemo;

//import Controller;
import java.lang.Math;

public class MovingAverage extends Controller {

	private double alpha = 0.2;
	
	public static String id = "Moving Average";
	
	public MovingAverage ( double deadzone, double alpha ) {
//		this.id = "MovingAverage";
		this.deadzone = deadzone;
		this.alpha = alpha;
	}
	
	public void setAlpha( double newAlpha) {
		if (newAlpha >= 1.0) 
			alpha = 1.0;
			
		if (newAlpha <= 0.0)
			alpha = 0.0; 
			
		alpha = newAlpha;
	}
	
	public double getAlpha() {
		return alpha;
	}

	public double getValue() {
		if ( Math.abs( value - setpoint ) < deadzone )
			return value;
	
		value = alpha*setpoint + (1.0-alpha)*value;	
		return value;
	}
}
