package frc.robot;

import frc.robot.Util.doubleSolenoid;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Constants;

import edu.wpi.first.wpilibj.Joystick;

//This file holds all initilization for hardware and buttons

public class Hardware {

    //Drivetrain
    public static final WPI_TalonSRX rightMasterMotor =
			new WPI_TalonSRX(Constants.RIGHT_MASTER_MOTOR_CAN);
	public static final WPI_VictorSPX rightSlaveMotor = 
			new WPI_VictorSPX(Constants.RIGHT_SLAVE_MOTOR_CAN);
	public static final WPI_TalonSRX leftMasterMotor =
			new WPI_TalonSRX(Constants.LEFT_MASTER_MOTOR_CAN);
	public static final WPI_VictorSPX leftSlaveMotor =
            new WPI_VictorSPX(Constants.LEFT_SLAVE_MOTOR_CAN);
            
    //Shifter
    public static final doubleSolenoid shifter = 
            new doubleSolenoid(Constants.SHIFTER_OPEN, Constants.SHIFTER_CLOSE);

    //Elevator
	public static final TalonSRX elevatorMotor1 = 
        new TalonSRX(Constants.ELEVATOR_MOTOR_CAN_1);
    public static final WPI_VictorSPX elevatorMotor2 =		
        new WPI_VictorSPX(Constants.ELEVATOR_MOTOR_CAN_2);
    public static final WPI_VictorSPX elevatorMotor3 =		
        new WPI_VictorSPX(Constants.ELEVATOR_MOTOR_CAN_3);
    
    //Claw Turn
    public static final TalonSRX clawRotationMotor = 
            new WPI_TalonSRX(Constants.CLAWROTATION_MOTOR_CAN);
    //Wheel Turn
    public static final WPI_TalonSRX wheelTurnMotor = 
            new WPI_TalonSRX(Constants.CLAWTURN_MOTOR_CAN); 
    public static final WPI_VictorSPX clawWheelsMotor = 
            new WPI_VictorSPX(Constants.CLAWWHEELS_MOTOR_CAN);
    
    //Sticks
    public static final Joystick driverPad = 
            new Joystick(Constants.DRIVE_USB_PORT);
    public static final Joystick operatorPad = 
            new Joystick(Constants.OPERATOR_USB_PORT);

    
    





}