package org.usfirst.frc.team283.napalm;

public abstract class Constants 
{
	//Computer Ports
		public static final int DRIVER_CONTROLLER_PORT = 0;
		public static final int OPERATOR_CONTROLLER_PORT = 1;
	//Joystick Ports
		//Buttons
			public static final int A = 1;
			public static final int B = 2;
			public static final int X = 3;
			public static final int Y = 4;
			public static final int LEFT_BUMPER = 5;
			public static final int RIGHT_BUMPER = 6;
			public static final int BACK = 7;
			public static final int START = 8;
			public static final int LEFT_STICK_BUTTON = 9;
			public static final int RIGHT_STICK_BUTTON = 10;
		//Sticks
			public static final int LEFT_X = 0;
			public static final int LEFT_Y = 1;
			public static final int LEFT_TRIGGER = 2;
			public static final int RIGHT_TRIGGER = 3;
			public static final int RIGHT_X = 4;
			public static final int RIGHT_Y = 5;
	//Robot Ports
		//PWM
			public static final int LEFT_DRIVE_CONTROLLER_PORT = 0;
			public static final int RIGHT_DRIVE_CONTROLER_PORT = 1;
			//LABELED 'GEARCONVEYOR'
			public static final int TURRET_CONTROLLER_PORT = 8; //
			public static final int HOPPER_CONTROLLER_PORT = 4; //Hopper Bottom
			public static final int CLIMB_CONTROLLER_PORT = 5;
			//public static final int INTAKE_CONTROLLER_PORT = 6; //LABELED 'ELEVATOR'
			public static final int INFEED_CONTROLLER_PORT = 7; //
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
			//Auto Selector Switches
			//public static final int AUTO_SWITCH_LEFT = 90;
			//public static final int AUTO_SWITCH_MIDDLE = 91;
			//public static final int AUTO_SWITCH_RIGHT = 92;
		//"Device ID"
			public static final int FLYWHEEL_CONTROLLER_PORT_A = 0; //Ripped from old C++ code
			public static final int FLYWHEEL_CONTROLLER_PORT_B = 1; //Ripped from old C++ code
		//Camera IDs
			public static final int TURRET_CAMERA_PORT = 0;
			public static final int GEAR_CAMERA_PORT = 1;
}
