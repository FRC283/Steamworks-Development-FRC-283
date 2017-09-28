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
		
		turret.periodic();
	}
	
	public void feed(boolean buttonState) //Runs the hopper and feed motors towards the flywheel at a fixed rate
	{
		
	}
	
	@Schema(value = Scheme.XBOX_RIGHT_Y, desc = "adjust flywheel speed")
	public void speed(double d) //Cumulatively adjusts flywheel speed based on input
	{
		flywheelController.set(Rescaler.deadzone(d, 0.05) * 0.08 * MAX_RPM);
		flywheelFollower.set(Constants.FLYWHEEL_CONTROLLER_PORT_A); //Logic ripped from old code.
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
