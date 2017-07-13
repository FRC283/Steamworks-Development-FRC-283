package org.usfirst.frc.team283.napalm;

import edu.wpi.first.wpilibj.Solenoid;

public class GearSubsystem 
{
	//Solenoids
	Solenoid gate;
	Solenoid push;
	Solenoid pouch;	
	
	public GearSubsystem()
	{
		gate = new Solenoid(Constants.GEAR_GATE_SOLENOID_PORT);
		push = new Solenoid(Constants.GEAR_PUSH_SOLENOID_PORT);
		pouch = new Solenoid(Constants.GEAR_POUCH_SOLENOID_PORT);
	}
	public void periodic(boolean gateSol, boolean pushSol, boolean pouchSol)
	{
		if(gateSol == true)
		{
			gate.set(true);
		}
		else
		{
			gate.set(false);
		}
		if(pouchSol == true)
		{
			pouch.set(true);
		}
		else
		{
			pouch.set(false);
		}
		if(pushSol == true)
		{
			//push.set(true);
		}
		else
		{
			//push.set(false);
		}
	}
	
	
	
}
