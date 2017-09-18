package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.Spark;

public class Hopper 
{
	/***/
	private final double DEADZONE = 0.1;
	
	/** Controls feeding balls to flywheel */
	private Spark infeedController;
	
	/** Controls feeding balls to infeed */
	private Spark hopperController;
	
	Hopper()
	{
		this.infeedController = new Spark(Constants.INFEED_CONTROLLER_PORT);
		this.hopperController = new Spark(Constants.HOPPER_CONTROLLER_PORT);
	}
	
	public void hopperPower( double hopperMag)
	{//rescale
		this.hopperController.set(Rescaler.deadzone(hopperMag, DEADZONE));
	}
	
	public void infeedPower(double infeedMag)
	{//rescale
		this.infeedController.set(Rescaler.deadzone(infeedMag, DEADZONE));
	}
}
