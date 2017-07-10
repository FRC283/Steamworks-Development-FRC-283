package org.usfirst.frc.team283.napalm;

public abstract class Constants 
{
	//Computer Ports
		//Controller Ports
			public static final int DRIVER_CONTROLLER_PORT = 0;
			public static final int OPERATOR_CONTROLLER_PORT = 1;
	//Robot Ports
		//PWM
			public static final int LEFT_DRIVE_CONTROLLER_PORT = 0;
			public static final int RIGHT_DRIVE_CONTROLER_PORT = 1;
			public static final int TURRET_CONTROLLER_PORT = 3;
			public static final int HOPPER_CONTROLLER_PORT = 4;
			public static final int CLIMB_CONTROLLER_PORT = 5;
			public static final int INTAKE_CONTROLLER_PORT = 6;
			public static final int INFEED_CONTROLLER_PORT = 7;
		//PCM
			public static final int SPEED_SHIFT_SOLENOID_PORT = 0;
			public static final int GEAR_PUSH_SOLENOID_PORT = 1;
			public static final int GEAR_POUCH_SOLENOID_PORT = 2;
			public static final int GEAR_GATE_SOLENOID_PORT = 3;
			public static final int HOOD_DEFLECTOR_SOLENOID_PORT = 4;
		//DIO
			public static final int LEFT_DRIVE_ENCODER_PORT_A = 0;
			public static final int LEFT_DRIVE_ENCODER_PORT_B = 1;
			public static final int CW_LIMIT_SWITCH_PORT = 2;
			public static final int CCW_LIMIT_SWITCH_PORT = 3;
			public static final int RIGHT_DRIVE_ENCODER_PORT_A = 7;
			public static final int RIGHT_DRIVE_ENCODER_PORT_B = 8;
}
