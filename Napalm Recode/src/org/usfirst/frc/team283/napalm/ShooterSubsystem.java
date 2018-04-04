package org.usfirst.frc.team283.napalm;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team283.napalm.Constants;
import org.usfirst.frc.team283.napalm.Scheme.Schema;

public class ShooterSubsystem 
{
	UsbCamera camera;
	Hopper hopper;
	Spark turretController;
	Spark flywheelController;
	//CANTalon flywheelFollower; //Provides extra power to the flywheel. Mirrors the main controller
	
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
	/** Take a guess. The expected max rpm cap */
	private final double MAX_RPM = 5000;
	/** Max rpm in native units per 100 ms */
	private final double NATIVE_MAX_RPM = 120; 
	/** F-Gain scaling based on maximum rpm. Max "power" is 1023. */
	private final double FLYWHEEL_F_CONSTANT = 1023/NATIVE_MAX_RPM;
	
	//Vars
	/** Change in speed per cycle */
	private double dSpeed = 0;
	/** Scales from controller power to dSpeed */
	private final double DSPEED_CONSTANT = 0.1;
	
	ShooterSubsystem()
	{
		this.camera = CameraServer.getInstance().startAutomaticCapture(Constants.TURRET_CAMERA_PORT);
		this.hopper = new Hopper();
		flywheelController = new Spark(Constants.FLYWHEEL_CONTROLLER_PORT_TWO);
		turretController = new Spark(Constants.TURRET_CONTROLLER_PORT);
	}
	
	public void periodic()
	{
		
	}
	
	/**
	 * Controls flywheel speed directly
	 * @param magnitude - input joystick speed
	 */
	@Schema(value = Scheme.XBOX_RIGHT_Y, desc = "adjust flywheel speed")
	public void wheel(double magnitude)
	{
		flywheelController.set(magnitude);
		System.out.println("Set the controller to: " + flywheelController.get());
	}
	
	public void feed(boolean buttonState) //Runs the hopper and feed motors towards the flywheel at a fixed rate
	{
		
	}
	
	/**
	 * Cumulatively adjusts flywheel speed based on input
	 * @param d - controller magnitude to adjust by
	 */
	@Deprecated
	public void speed(double d)
	{
		this.dSpeed = Rescaler.deadzone(d, 0.05) * this.DSPEED_CONSTANT;
		System.out.println("    dSpeed = " + this.dSpeed);
	}
	
	@Schema(value = Scheme.XBOX_RIGHT_X, desc = "control swivel motion of turret")
	public void manualAim(double axisInput)
	{
		SmartDashboard.putNumber("Swivel Value", axisInput);
		turretController.set(axisInput);
	}
	
	@Schema(value = Scheme.XBOX_LEFT_Y, desc = "roll balls into flywheel")
	@Schema(value = Scheme.XBOX_LEFT_X, desc = "roll balls second queue")
	public void feedIn(double infeedMag, double intakeMag)
	{
		this.hopper.infeedPower(-1 * infeedMag);
		this.hopper.hopperPower(-1 * intakeMag);
	}
}