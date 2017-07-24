package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Napalm extends IterativeRobot //Post-Release merge comment
{
	DriveSubsystem drivetrain;
	GearSubsystem gearSubsystem;
	Joystick xbox;
	Joystick logitech;

	boolean driveTriggerBool;
	
	boolean driveLeftBumper;
		
	@Override
	public void robotInit() 
	{
		drivetrain = new DriveSubsystem();
		gearSubsystem = new GearSubsystem();
		logitech = new Joystick(Constants.DRIVER_CONTROLLER_PORT);
		xbox = new Joystick(Constants.OPERATOR_CONTROLLER_PORT);
	}

	@Override
	public void autonomousInit() 
	{
		
	}

	@Override
	public void autonomousPeriodic() 
	{
		
	}

	@Override
	public void teleopInit() 
	{
		
	}
	
	@Override
	public void teleopPeriodic() 
	{
		drivetrain.periodic(logitech.getRawAxis(LEFT), logitech.getRawAxis(RIGHT), driveRightTrigger, (logitech.getRawAxis(3) >= 0.5), buttonState);
		gearSubsystem.periodic(gateSol, pushSol, pouchSol);
	}
}

