package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import org.usfirst.frc.team283.napalm.Constants;
import org.usfirst.frc.team283.napalm.Scheme.Schema;

public class ShooterSubsystem 
{
	Hopper hopper;
	Flywheel flywheel;
	TurretAxis turret;
	
	//Constants
	/** The speed at which the hopper and feed motors run */
	private final float FEED_SPEED = 1; 
	

	
	ShooterSubsystem()
	{
		Hopper hopper = new Hopper();
		//Flywheel flywheel = new Flywheel(new Spark(999), new Encoder(999, 999), 360);
		TurretAxis turret = new TurretAxis(new Spark(Constants.TURRET_CONTROLLER_PORT));
		turret.addLimits(new DigitalInput(Constants.CCW_LIMIT_SWITCH_PORT), new DigitalInput(Constants.CW_LIMIT_SWITCH_PORT));
		//turret.configureTargeting(1/1000, 50);
	}
	
	public void periodic()
	{
		//flywheel.periodic();
		turret.periodic();
	}
	
	public void feed(boolean buttonState) //Runs the hopper and feed motors towards the flywheel at a fixed rate
	{
		
	}
	
	public void speed(float stickPosition) //Cumulatively adjusts flywheel speed based on input
	{
		
	}
	
	@Schema(Scheme.XBOX_LEFT_X)
	public void manualAim(double axisInput)
	{
		turret.setPower(axisInput);
	}
}
