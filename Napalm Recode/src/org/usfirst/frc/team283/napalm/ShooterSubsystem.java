package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import org.usfirst.frc.team283.napalm.Constants;
import org.usfirst.frc.team283.napalm.Scheme.Schema;
import com.ctre.CANTalon.TalonControlMode;

import com.ctre.CANTalon;

public class ShooterSubsystem 
{
	Hopper hopper;
	TurretAxis turret;
	CANTalon flywheelController;
	
	
	//Constants
	/** The speed at which the hopper and feed motors run */
	private final float FEED_SPEED = 1; 
	
	/** The number of encoder ticks per revolution in the flywheel */
	private final int FLYWHEEL_TICKS = 360; //Ripped from old c++, but that code notes that it's incorrect. Some confusion here.
	
	/** Maximum regular flywheel RPM */
	private final double MAX_FLYWHEEL_RPM = 4000;
	
	/** P-Control coefficient for flywheel */
	private final double FLYWHEEL_P_CONSTANT = 1/(MAX_FLYWHEEL_RPM); //Value of 0.5 from old code.
	
	/** Aiming Deadzone for flywheel and axis */
	private final double DEADZONE = 0.1;
	
	
	ShooterSubsystem()
	{
		this.hopper = new Hopper();
		this.turret = new TurretAxis(new Spark(Constants.TURRET_CONTROLLER_PORT));
		turret.addLimits(new DigitalInput(Constants.CCW_LIMIT_SWITCH_PORT), new DigitalInput(Constants.CW_LIMIT_SWITCH_PORT), true);
		turret.reverseController();
		
		flywheelController = new CANTalon(Constants.FLYWHEEL_CONTROLLER_PORT_A);
		flywheelController.setControlMode(TalonControlMode.Speed.getValue());
		flywheelController.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		//flywheelController.getSpeed();
		flywheelController.configEncoderCodesPerRev(FLYWHEEL_TICKS);
		flywheelController.setP(FLYWHEEL_P_CONSTANT);
		flywheelController.setI(0);
		flywheelController.setD(0);
		flywheelController.setF(0);
	}
	
	public void periodic()
	{
		turret.periodic();
		System.out.println("ShooterSS: Flywheel RPM: " + flywheelController.getSpeed());
	}
	
	public void feed(boolean buttonState) //Runs the hopper and feed motors towards the flywheel at a fixed rate
	{
		
	}
	
	public void speed(float stickPosition) //Cumulatively adjusts flywheel speed based on input
	{
		flywheelController.set(stickPosition * MAX_FLYWHEEL_RPM);
	}
	
	@Schema(Scheme.XBOX_LEFT_X)
	public void manualAim(double axisInput)
	{
		turret.setPower(Rescaler.deadzone(axisInput, DEADZONE));
	}
}
