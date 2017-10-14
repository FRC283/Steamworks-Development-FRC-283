package org.usfirst.frc.team283.napalm;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team283.napalm.Constants;
import org.usfirst.frc.team283.napalm.Scheme.Schema;
import com.ctre.CANTalon.TalonControlMode;

import com.ctre.CANTalon;

public class ShooterSubsystem 
{
	UsbCamera camera;
	Hopper hopper;
	TurretAxis turret;
	CANTalon flywheelController;
	CANTalon flywheelFollower; //Provides extra power to the flywheel. Mirrors the main controller
	
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
		this.turret = new TurretAxis(new Spark(Constants.TURRET_CONTROLLER_PORT));
		turret.addLimits(new DigitalInput(Constants.CCW_LIMIT_SWITCH_PORT), new DigitalInput(Constants.CW_LIMIT_SWITCH_PORT), true);
		turret.reverseController();
		flywheelController = new CANTalon(Constants.FLYWHEEL_CONTROLLER_PORT_A);
		flywheelFollower = new CANTalon(Constants.FLYWHEEL_CONTROLLER_PORT_B);
		flywheelController.setControlMode(TalonControlMode.Speed.getValue());
		//flywheelController.setControlMode(TalonControlMode.PercentVbus.getValue());
		//flywheelController.configNominalOutputVoltage(+0.0f, -0.0f);
        //flywheelController.configPeakOutputVoltage(+12.0f, -12.0f);
		
		flywheelFollower.setControlMode(TalonControlMode.Follower.getValue());
		flywheelController.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		flywheelController.configEncoderCodesPerRev(FLYWHEEL_TICKS);
		flywheelController.setP(FLYWHEEL_P_CONSTANT);
		flywheelController.setI(0);
		flywheelController.setD(0);
		flywheelController.setF(FLYWHEEL_F_CONSTANT);
		flywheelController.configNominalOutputVoltage(0, 0);
		flywheelController.configPeakOutputVoltage(12, -12);
	}
	
	public void periodic()
	{
		double s = this.dSpeed + (flywheelController.getOutputVoltage()/12);//(0.08 * MAX_RPM)); //Add the delta-s to the current rpm. (Has to convert the rpm back to power-units)
		//System.out.println("    flywheelController.get() " + this.flywheelController.get());
		//System.out.println("    Power before RPM Conversion: " + s);
		//System.out.println("    getSpeed" + flywheelController.getSpeed());
		//System.out.println("    getOutputVoltage" + flywheelController.getOutputVoltage());
		//System.out.println("    getOutputVoltage" + flywheelController.getError());
		
		s = (s < 0) ? 0 : s; //If it's less than 0, 0 it
		System.out.println("    Power (s) is now " + s);
		flywheelController.set(s);// * 0.08 * MAX_RPM); //Rescale for proper values
		//System.out.println("    FController now set to " + (s * 0.08 * MAX_RPM));
		flywheelFollower.set(Constants.FLYWHEEL_CONTROLLER_PORT_A); //Logic ripped from old code.
		turret.periodic();
	}
	
	public void feed(boolean buttonState) //Runs the hopper and feed motors towards the flywheel at a fixed rate
	{
		
	}
	
	/**
	 * Cumulatively adjusts flywheel speed based on input
	 * @param d - controller magnitude to adjust by
	 */
	@Schema(value = Scheme.XBOX_RIGHT_Y, desc = "adjust flywheel speed")
	public void speed(double d)
	{
		this.dSpeed = Rescaler.deadzone(d, 0.05) * this.DSPEED_CONSTANT;
		System.out.println("    dSpeed = " + this.dSpeed);
	}
	
	@Schema(value = Scheme.XBOX_RIGHT_X, desc = "control swivel motion of turret")
	public void manualAim(double axisInput)
	{
		SmartDashboard.putNumber("Swivel Value", axisInput);
		turret.setPower(Rescaler.deadzone(axisInput, DEADZONE));
	}
	
	@Schema(value = Scheme.XBOX_LEFT_Y, desc = "roll balls into flywheel")
	@Schema(value = Scheme.XBOX_LEFT_X, desc = "roll balls second queue")
	public void feedIn(double infeedMag, double intakeMag)
	{
		this.hopper.infeedPower(infeedMag);
		this.hopper.hopperPower(-1 * intakeMag);
	}
}
