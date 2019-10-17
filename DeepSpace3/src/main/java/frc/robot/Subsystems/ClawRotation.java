package frc.robot.Subsystems;

import frc.robot.Hardware;
import frc.robot.Lib.SubsystemFramework;
import frc.robot.Util.PID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClawRotation extends SubsystemFramework{
	private static TalonSRX clawRotationMotor;
	//If motor is constricted i may have to comment all this out except the bits of output to smartdashboard
	//PID
	private PID Claw_Rotation_PI;
	
	//Constants
	private static double Average_Ticks_Per_Degree;
	private double ClawRotation_Kf;
	private double ClawRotation_Kp;
	private double ClawRotation_Ki;
	private double ClawRotation_Kd;
	
	public static ClawRotationState state = ClawRotationState.StartPosition; 
	
	public ClawRotation(TalonSRX clawRotationMotor, double Average_Ticks_Per_Degree, 
						double ClawRotation_Kf, double ClawRotation_Kp, 
						double ClawRotation_Ki, double ClawRotation_Kd){
		this.clawRotationMotor = clawRotationMotor;
		
		this.Average_Ticks_Per_Degree = Average_Ticks_Per_Degree;
		
		this.ClawRotation_Kf = ClawRotation_Kf;
		this.ClawRotation_Kp = ClawRotation_Kp;
		this.ClawRotation_Ki = ClawRotation_Ki;
		this.ClawRotation_Kd = ClawRotation_Kd;
	}
	
	public ClawRotation(TalonSRX clawRotationMotor, double Average_Ticks_Per_Degree, 
			double Claw_Rotation_Kp, double Claw_Rotation_Ki){
		this.clawRotationMotor = clawRotationMotor;

		this.Average_Ticks_Per_Degree = Average_Ticks_Per_Degree;
		
		this.Claw_Rotation_PI = new PID(Claw_Rotation_Kp, Claw_Rotation_Ki);
	}
		
	public enum ClawRotationState{
		StartPosition, IntakePosition, StowPosition, HatchPlacePosition, CargoShipPosition, CargoPlacePosition, ReleasePosition, MidCargoPlacePosition, CargoFloorIntake;  
	}		
	
	public double setPosition(double angle) {

		return angle * Average_Ticks_Per_Degree; 
	}
	
	public static boolean testSafeAngleHigh(double Safe_Angle_High) {
		return (Safe_Angle_High * Average_Ticks_Per_Degree) >= clawRotationMotor.getSelectedSensorPosition(0);
	}
	public static boolean testSafeAngleLow(double Safe_Angle_Low) {
		return (Safe_Angle_Low * Average_Ticks_Per_Degree) <= clawRotationMotor.getSelectedSensorPosition(0);
	}
	
	@Override
	public void update() {
		ClawRotationState newState = state;
		
		switch(state){			
			case StartPosition:
				SmartDashboard.putString("State", "Start");
				
				clawRotationMotor.set(ControlMode.Position, setPosition(0));

	break;
			
			case StowPosition:
				SmartDashboard.putString("State", "Stow");
                
					clawRotationMotor.set(ControlMode.Position, setPosition(-5));//-10
	break;			
	
			case IntakePosition:
				SmartDashboard.putString("State", " Hatch Intake");
                
				clawRotationMotor.set(ControlMode.Position, setPosition(-90));//-106

	break;
			
			case HatchPlacePosition:
				SmartDashboard.putString("State", "Hatch Place");
			
					clawRotationMotor.set(ControlMode.Position, setPosition(-73));//-85

	break;		
          
            case CargoShipPosition:
            SmartDashboard.putString("State", "RocketPlace");
                
                clawRotationMotor.set(ControlMode.Position, setPosition(-90));//-100
            
	break;	
	
			case CargoPlacePosition :
				SmartDashboard.putString("State", "CargoPlace");

				clawRotationMotor.set(ControlMode.Position, setPosition(-60));//-60

	break;
			
			case ReleasePosition :
			SmartDashboard.putString("State", "ReleasePosition");

			clawRotationMotor.set(ControlMode.Position, setPosition(-83));//-95

	break;
	
			case MidCargoPlacePosition :
			SmartDashboard.putString("State", "MidCargoPlacePosition");

			clawRotationMotor.set(ControlMode.Position, setPosition(-50));

	break;

			case CargoFloorIntake :
			SmartDashboard.putString("State", "CargoFloorIntake");

			clawRotationMotor.set(ControlMode.Position, setPosition(-94));

			
			default:
				newState = ClawRotationState.StowPosition;
			break;
		}
		
		if (newState != state) {
			state = newState;
		}

		outputToSmartDashboard(); 
	}
	
	
	
	
	@Override
	public void outputToSmartDashboard() {		
		SmartDashboard.putNumber("Claw Rotation", clawRotationMotor.getSelectedSensorPosition(0));
	}

	@Override
	public void setupSensors() {
		//Set Position To Zero When Started
		clawRotationMotor.setSelectedSensorPosition(0, 0, 10);
				
		//Absolute Allows Position To State The Same After Restart
		clawRotationMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		
		//Set peak and nominal outputs
		clawRotationMotor.configNominalOutputForward(0, 10);
		clawRotationMotor.configNominalOutputReverse(0, 10);
		clawRotationMotor.configPeakOutputForward(0.5, 10);
		clawRotationMotor.configPeakOutputReverse(-0.5, 10);

		//Set allowable closed-loop error
		clawRotationMotor.configAllowableClosedloopError(0, 0, 10);

		//Set closed loop gains in PID slot 0
		clawRotationMotor.config_kF(0, ClawRotation_Kf, 10);
		clawRotationMotor.config_kP(0, ClawRotation_Kp, 10);
		clawRotationMotor.config_kI(0, ClawRotation_Ki, 10);
		clawRotationMotor.config_kD(0, ClawRotation_Kd, 10);
	}
}