//package controllersDemo

public abstract class Controller {
	
	protected double setpoint;
	
	protected double value;
	
	protected double deadzone;
	
	public static String id = "Controller";
	
	/** CONSTRUCTOR **/
	public Controller( ) {
		setpoint = value = 50;
		
		deadzone = 0.0;
	}
	
	public String id() {
		return this.id;
	}
	
	public void setDeadzone( double newDZ ) {
		deadzone = newDZ;
	}

	
	public void setSetpoint (double newSetpoint) {
		setpoint = newSetpoint;
	}
	
	public abstract double getValue();
	

}
