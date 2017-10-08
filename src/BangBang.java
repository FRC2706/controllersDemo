//package controllersDemo;

import java.lang.Math;
//import Controller;

public class BangBang extends Controller {

	private double velocity;
	
	private double frameRate;
	private double cycleRate;
	
	private double onRat;
	
	public static String id = "Bang Bang";
		
	
	/** CONSTRUCTOR **/
	public BangBang ( double deadzone, double frameRate, double pwrCycleRate, double velocity, double onPrct ) {
		this.deadzone = deadzone;
		this.frameRate = frameRate;
		setPwrCycleRate(pwrCycleRate);
		this.velocity = velocity;
		setOnPrct(onPrct);
//System.out.println("pwrCycleRate: "+pwrCycleRate);
	}
	
	public void setVelocity( double newVelocity) {
		if (newVelocity <= 0.0)
			velocity = 0.0; 
			
		velocity = newVelocity;
	}
	
	public double getVelocity() {
		return velocity;
	}

	
	public void setPwrCycleRate( double rate ) {
		this.cycleRate = rate;
	}
	
	public void setOnPrct( double onPrct ) {
		if (onPrct < 0)
			onPrct = 0;
		if (onPrct > 100)
			onPrct = 100;
			
		this.onRat = onPrct / (100 - onPrct);
	}


	private final int OFF = 0;
	private final int FORW = 1;
	private final int BACK = 2;
	private int state = OFF;
	private int onCount = 1;
	private int offCount = 1;
	private int pwrCycleCount = 0;
	
	private void reset(int dir) {
		state = dir;
		onCount = 1;
		offCount = 1;
	}
	
	public double getValue() {
//System.out.println("CR: "+cycleRate+" cycleCount: "+pwrCycleCount);
		pwrCycleCount = ((int)pwrCycleCount +1) % (int) frameRate;
		if (pwrCycleCount % (int) (frameRate / cycleRate) != 0) {
			if (state == OFF)
				{ /* do nothing */ }
			if (state == FORW)
				value += velocity / frameRate;
			if (state == BACK)
				value -= velocity / frameRate; 
		
			return value;
		}
		
		
		if ( Math.abs( value - setpoint ) < deadzone ) {
			reset(OFF);
			return value;
		}
		
		double valDiff = setpoint - value;
		
		
		// just to make sure the patterns don't get too crazy, make sure the pattern is not longer than 1 hz
		if (valDiff > 0) {
			if (onCount + offCount > frameRate) {
				reset(FORW);
			}
			else if (((double)onCount) / (double)offCount > onRat) {
				state = OFF;
				offCount++;
			}
			else if (((double)onCount) / (double)offCount < onRat) {
				state = FORW;
				onCount++;
			}
			else { // equal
				reset(FORW);
			}
		}
		else { // valDiff < 0
			if (onCount + offCount > frameRate) {
				reset(BACK);
			}
			else if (((double)onCount) / (double)offCount > onRat) {
				state = OFF;
				offCount++;
			}
			else if (((double)onCount) / (double)offCount < onRat) {
				state = BACK;
				onCount++;
			}
			else { // equal
				reset(BACK);
			}
			
		}
		
		if (state == OFF)
			{ /* do nothing */ }
		if (state == FORW)
			value += velocity / frameRate;
		if (state == BACK)
			value -= velocity / frameRate; 
		
		return value;
	}
	
}
