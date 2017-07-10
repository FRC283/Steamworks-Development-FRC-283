package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.Spark;

public class Lift 
{
	//Constants
	private static final double DEADZONE = 0.1;
	//Motors
	Spark climbSpark;
	
	public Lift()
	{
		climbSpark = new Spark(Constants.CLIMB_CONTROLLER_PORT);
	}
	public void periodic(float triggerMagnitude)
	{
		if(triggerMagnitude <= DEADZONE)
		{
			climbSpark.set(-1 * triggerMagnitude);
		}
	}
}
