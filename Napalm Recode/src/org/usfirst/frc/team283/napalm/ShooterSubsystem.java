package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;

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
		Flywheel flywheel = new Flywheel(new Spark(999), new Encoder(999, 999), 360);
		TurretAxis turret = new TurretAxis(new Spark(998), new Encoder(999, 999), 200, 400).addLimits(min, max);.configureTargeting(k, e);
	}
	
	public void feed(boolean buttonState) //Runs the hopper and feed motors towards the flywheel at a fixed rate
	{
		
	}
	
	public void speed(float stickPosition) //Cumulatively adjusts flywheel speed based on input
	{
		
	}
}
