package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Napalm extends IterativeRobot //Post-Release merge comment
{
<<<<<<< HEAD
=======
	//Intermediary Comment
>>>>>>> branch 'master' of https://github.com/Benr444/Napalm-Java-FRC-283
	DriveSubsystem drivetrain;
<<<<<<< HEAD
	GearSubsystem gearSubsystem;
	ShooterSubsystem shooterSubsystem;
	
	Joystick xbox;
	Joystick logitech;
=======
>>>>>>> branch 'master' of https://github.com/Benr444/Napalm-Java-FRC-283
	
	@Override
	public void robotInit() 
	{
		drivetrain = new DriveSubsystem();
<<<<<<< HEAD
		gearSubsystem = new GearSubsystem();
		shooterSubsystem = new ShooterSubsystem();
		
		logitech = new Joystick(Constants.DRIVER_CONTROLLER_PORT);
		xbox = new Joystick(Constants.OPERATOR_CONTROLLER_PORT);
=======
>>>>>>> branch 'master' of https://github.com/Benr444/Napalm-Java-FRC-283
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
<<<<<<< HEAD
		drivetrain.drive(logitech.getRawAxis(Constants.LEFT_Y), logitech.getRawAxis(Constants.RIGHT_Y),(logitech.getRawAxis(Constants.RIGHT_TRIGGER) >= 0.5));
		drivetrain.lift(xbox.getRawAxis(Constants.RIGHT_TRIGGER));
		drivetrain.shiftGear(logitech.getRawButton(Constants.LEFT_BUMPER));
		gearSubsystem.pouch(xbox.getRawButton(Constants.RIGHT_BUMPER));
		//UNKNOWN IF LOCKOUT IS CORRECT
		gearSubsystem.release(xbox.getRawButton(Constants.LEFT_BUMPER));
		shooterSubsystem.aim(xbox.getRawAxis(Constants.RIGHT_X));
=======
		
>>>>>>> branch 'master' of https://github.com/Benr444/Napalm-Java-FRC-283
	}
}

