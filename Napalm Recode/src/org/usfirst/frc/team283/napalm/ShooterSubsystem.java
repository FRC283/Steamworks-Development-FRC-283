package org.usfirst.frc.team283.napalm;

public class ShooterSubsystem 
{
	Hopper hopper;
	Flywheel flywheel;
	Turret turret;
	
	//Constants
	/** The speed at which the hopper and feed motors run */
	private final float FEED_SPEED = 1; 
	

	
	ShooterSubsystem()
	{
		Hopper hopper = new Hopper();
		Flywheel flywheel = new Flywheel();
		Turret turret = new Turret();
		
	}
	
	public void feed(boolean buttonState) //Runs the hopper and feed motors towards the flywheel at a fixed rate
	{
		
	}
	
	public void speed(float stickPosition) //Cumulatively adjusts flywheel speed based on input
	{
		
	}
}
