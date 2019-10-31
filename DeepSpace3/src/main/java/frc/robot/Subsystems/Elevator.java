package frc.robot.Subsystems;

import frc.robot.Constants;
import frc.robot.Hardware;
import frc.robot.Lib.SubsystemFramework;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.Subsystems.ClawRotation.ClawRotationState;
import frc.robot.Subsystems.WheelTurn.WheelTurnState;
import frc.robot.Subsystems.WheelTurn;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends SubsystemFramework {
	// Hardware
    private static TalonSRX elevatorMotorOne;
    private static VictorSPX elevatorMotorTwo;
    private static VictorSPX elevatorMotorThree;

    // Constants
	private static double Average_Ticks_Per_Inch;
	private double Elevator_Kf;
	private double Elevator_Kp;
	private double Elevator_Ki;
	private double Elevator_Kd;

    //public ElevatorState prevState;

	public static ElevatorState state = ElevatorState.StartPosition;

    public Elevator(TalonSRX elevatorMotorOne, VictorSPX elevatorMotorTwo, WPI_VictorSPX elevatorMotorThree,
                            double Average_Ticks_Per_Inch, 
						    double Elevator_Kf, double Elevator_Kp, 
						    double Elevator_Ki, double Elevator_Kd){
        
    //    this.prevState = prevState;

        this.elevatorMotorOne = elevatorMotorOne;
        this.elevatorMotorTwo = elevatorMotorTwo;
        this.elevatorMotorThree = elevatorMotorThree;

        this.Average_Ticks_Per_Inch = Average_Ticks_Per_Inch;

		this.Elevator_Kf = Elevator_Kf;
		this.Elevator_Kp = Elevator_Kp;
		this.Elevator_Ki = Elevator_Ki;
		this.Elevator_Kd = Elevator_Kd;
	}
	
	public Elevator(TalonSRX elevatorMotorOne, VictorSPX elevatorMotorTwo) {
		this.elevatorMotorOne = elevatorMotorOne;
        this.elevatorMotorTwo = elevatorMotorTwo;
        this.elevatorMotorThree = elevatorMotorThree;

    }
	
	public enum ElevatorState {
        StartPosition, CargoStowPosition, HatchStowPosition , HatchIntakePosition, CargoIntakePosition, HatchLowPosition, HatchMidPosition,
        HatchHighPosition, CargoShipPosition, RocketLowPosition, RocketMidPosition, RocketHighPosition,  HatchMidRelease, HatchHighRelease, HatchLowRelease;
	}

	public double setPosition(double pos) {
		return pos * Average_Ticks_Per_Inch;
	}

	public static boolean testSafePosition(double Safe_Position) {
		return (Safe_Position * Average_Ticks_Per_Inch) < elevatorMotorOne.getSelectedSensorPosition(0);
	}

	public static boolean testSafeHighPosition (double Safe_High_Position) {
		return (Safe_High_Position * Average_Ticks_Per_Inch) >= elevatorMotorOne.getSelectedSensorPosition(0);
	}

    public static boolean StartPosition(){
        return(Hardware.driverPad.getRawButton(Constants.MANUELDEFENSE));
    }
    public static boolean CargoStowPosition(){
        return(Hardware.operatorPad.getRawButton(Constants.CARGOSTOW));
    }
    public static boolean HatchStowPosition(){
        return(Hardware.operatorPad.getRawButton(Constants.HATCHSTOW));
    }
    public static boolean CargoIntakePosition(){
        return(Hardware.operatorPad.getRawButton(Constants.CARGOINTAKE));
    }
    public static boolean RocketLowPosition(){
        return(Hardware.operatorPad.getRawButton(Constants.LOW));
    }
    public static boolean RocketMidPosition(){
        return(Hardware.operatorPad.getRawButton(Constants.MID));
    }
    public static boolean RocketHighPosition(){
        return(Hardware.operatorPad.getRawButton(Constants.HIGH));
    }
    public static boolean HatchIntakePosition(){
        return(Hardware.operatorPad.getRawButton(Constants.INTAKE));
    }
    public static boolean HatchLowPosition(){
        return(Hardware.operatorPad.getRawButton(Constants.LOW));
    }
    public static boolean HatchMidPosition(){
        return(Hardware.operatorPad.getRawButton(Constants.MID));
    }
    public static boolean HatchHighPosition(){
        return(Hardware.operatorPad.getRawButton(Constants.HIGH));
    }
    public static boolean CargoShipPosition(){
        return(Hardware.operatorPad.getRawButton(Constants.CARGOSHIP));
    }
    /*public static boolean HatchMidRelease(){
        return(Hardware.driverPad.getRawButton(Constants.RELEASE));
    }
    public static boolean HatchHighRelease(){
        return(Hardware.driverPad.getRawButton(Constants.RELEASE));
    }*/public static boolean Release(){
        return(Hardware.driverPad.getRawButton(Constants.RELEASE));
    }

	@Override
	public void update() {
        /*if(Hardware.driverPad.getRawButton(5)){
            elevatorMotorOne.set(0.7);
        }

        if(Hardware.driverPad.getRawButton(6)){
            elevatorMotorOne.set(0);
        } 
        else if(Hardware.driverPad.getRawButton(7)){
            elevatorMotorOne.set(-0.7);
        } */
         ElevatorState newState = state;

		    switch (state) {
            case StartPosition:
                setupDown();    
               // if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
				elevatorMotorOne.set(ControlMode.Position, setPosition(0));
               // }
                
                ClawRotation.state = ClawRotationState.StartPosition;
                WheelTurn.state = WheelTurnState.HatchClose;
                
				if(CargoStowPosition()){
                    newState = ElevatorState.CargoStowPosition;
                }else if(HatchStowPosition()){
                    newState = ElevatorState.HatchStowPosition;
                }else if(StartPosition()){
                    newState = ElevatorState.StartPosition;
                }
	break;
            case HatchIntakePosition:
            setupDown();    
               // if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
				    
            elevatorMotorOne.set(ControlMode.Position, setPosition(-8));//8
            ClawRotation.state = ClawRotationState.HatchPlacePosition;

                //}
 
                if(HatchHighPosition()) {
					newState = ElevatorState.HatchHighPosition;
				}else if(HatchLowPosition()) {
					newState = ElevatorState.HatchLowPosition;
				}else if(HatchMidPosition()) {
					newState = ElevatorState.HatchMidPosition;
				}else if(HatchStowPosition()){
                    newState = ElevatorState.HatchStowPosition;
                }else if(CargoStowPosition()){
                    newState = ElevatorState.CargoStowPosition;
                }else if(StartPosition()){
                    newState = ElevatorState.StartPosition;
                }else if(Release()) {
                    newState = ElevatorState.HatchLowRelease;
                }   
    break;
            case CargoIntakePosition:
            setupDown();    
                //if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
				    
            elevatorMotorOne.set(ControlMode.Position, setPosition(0));
            ClawRotation.state = ClawRotationState.CargoFloorIntake;

        //}
                
                    
        if(CargoShipPosition()) {
            newState = ElevatorState.CargoShipPosition;
        }else if(RocketHighPosition()) {
            newState = ElevatorState.RocketHighPosition;
        }else if(RocketMidPosition()) {
            newState = ElevatorState.RocketMidPosition;
        }else if(RocketLowPosition()) {
            newState = ElevatorState.RocketLowPosition;
        }else if(CargoStowPosition()){
            newState = ElevatorState.CargoStowPosition;
        }else if(HatchStowPosition()){
            newState = ElevatorState.HatchStowPosition;
        }else if(StartPosition()){
            newState = ElevatorState.StartPosition;
        }
        break;

            case CargoShipPosition:
            setupUp();
          //      if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
			    
            elevatorMotorOne.set(ControlMode.Position, setPosition(-36));
                
                ClawRotation.state = ClawRotationState.CargoShipPosition;
            //    }

            if(CargoIntakePosition()){
                newState = ElevatorState.CargoIntakePosition;
            }else if(CargoStowPosition()){
                newState = ElevatorState.CargoStowPosition;
            }else if(StartPosition()){
                newState = ElevatorState.StartPosition;
            }else if(HatchStowPosition()){
                newState = ElevatorState.HatchStowPosition;
            }else if(StartPosition()){
                newState = ElevatorState.StartPosition;
            }

    break;

            case HatchHighPosition:
            setupUp();    
              //  if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
			    
            //elevatorMotorOne.set(ControlMode.Position, setPosition(-65));

                //ClawRotation.state = ClawRotationState.HatchPlacePosition;
               
                //}

                if(HatchIntakePosition()) {
					newState = ElevatorState.HatchIntakePosition;
				}else if(CargoIntakePosition()){
                    newState = ElevatorState.CargoIntakePosition;
                }else if(HatchStowPosition()){
                    newState = ElevatorState.HatchStowPosition;
                }else if(StartPosition()){
                    newState = ElevatorState.StartPosition;
                }else if(Release()){
                    newState =ElevatorState.HatchHighRelease;
                }else if(CargoStowPosition()){
                    newState = ElevatorState.CargoStowPosition;
                }
        break;
    
            case HatchMidPosition:
            setupDown();
           //     if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
			    
            elevatorMotorOne.set(ControlMode.Position, setPosition(-35));
            
                ClawRotation.state = ClawRotationState.HatchPlacePosition;
                //  }

             if(HatchIntakePosition()) {
                newState = ElevatorState.HatchIntakePosition;
            }else if(CargoIntakePosition()){
                newState = ElevatorState.CargoIntakePosition;
            }else if(HatchStowPosition()){
                newState = ElevatorState.HatchStowPosition;
            }else if(StartPosition()){
                newState = ElevatorState.StartPosition;
            }else if(Release()){
                newState = ElevatorState.HatchMidRelease;
            }else if(CargoStowPosition()){
                newState = ElevatorState.CargoStowPosition;
            }
        break;

            case HatchLowPosition:
            setupDown();    
                //if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
			    
            elevatorMotorOne.set(ControlMode.Position, setPosition(-9));//-12
                
                ClawRotation.state = ClawRotationState.HatchPlacePosition;
                
                //}

                if(HatchIntakePosition()) {
					newState = ElevatorState.HatchIntakePosition;
				}else if(CargoIntakePosition()){
                    newState = ElevatorState.CargoIntakePosition;
                }else if(HatchStowPosition()){
                    newState = ElevatorState.HatchStowPosition;
                }else if(StartPosition()){
                    newState = ElevatorState.StartPosition;
                }else if(CargoStowPosition()){
                    newState = ElevatorState.CargoStowPosition;
                }else if(Release()){
                    newState = ElevatorState.HatchMidRelease;
                }
        break;
    
            case RocketHighPosition:
            setupUp();    
               // if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
			    
            elevatorMotorOne.set(ControlMode.Position, setPosition(-36));
                
                ClawRotation.state = ClawRotationState.CargoPlacePosition;
                
                //}

				if(CargoIntakePosition()){
                    newState = ElevatorState.CargoIntakePosition;
                }else if(HatchStowPosition()){
                    newState = ElevatorState.HatchStowPosition;
                }else if(StartPosition()){
                    newState = ElevatorState.StartPosition;
                }else if(CargoStowPosition()){
                    newState = ElevatorState.CargoStowPosition;
                }    
        break;

            case RocketLowPosition:
            setupDown();    
                //if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
			    
            elevatorMotorOne.set(ControlMode.Position, setPosition(-12));//-12
            
                ClawRotation.state = ClawRotationState.CargoPlacePosition;
                 
                //}
                if(CargoIntakePosition()) {
					newState = ElevatorState.CargoIntakePosition;
				}else if(CargoIntakePosition()){
                    newState = ElevatorState.CargoIntakePosition;
                }else if(HatchStowPosition()){
                    newState = ElevatorState.HatchStowPosition;
                }else if(StartPosition()){
                    newState = ElevatorState.StartPosition;
                }else if(CargoStowPosition()){
                    newState = ElevatorState.CargoStowPosition;
                }    
        break;
        
            case RocketMidPosition:
            setupDown();    
                //if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
			    
            elevatorMotorOne.set(ControlMode.Position, setPosition(-36));//59
            ClawRotation.state = ClawRotationState.MidCargoPlacePosition;
                //ClawRotation.state = ClawRotationState.CargoPlacePosition;
                
               // }    
            if(CargoIntakePosition()){
                newState = ElevatorState.CargoIntakePosition;
            }else if(HatchStowPosition()){
                newState = ElevatorState.HatchStowPosition;
            }else if(RocketLowPosition()){
                newState = ElevatorState.RocketLowPosition;
            }else if(CargoStowPosition()){
                newState = ElevatorState.CargoStowPosition;
            }else if(StartPosition()){
                newState = ElevatorState.StartPosition;
            }    
        break;

            case CargoStowPosition:
            setupDown();    
               // if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
			    
            elevatorMotorOne.set(ControlMode.Position, setPosition(0));

                ClawRotation.state = ClawRotationState.StowPosition;
                //}    
                if(CargoIntakePosition()){
                    newState = ElevatorState.CargoIntakePosition;
                }else if(CargoShipPosition()) {
                    newState = ElevatorState.CargoShipPosition;
                }else if(RocketHighPosition()) {
                    newState = ElevatorState.RocketHighPosition;
                }else if(RocketMidPosition()) {
                    newState = ElevatorState.RocketMidPosition;
                }else if(RocketLowPosition()) {
                    newState = ElevatorState.RocketLowPosition;
                }else if(HatchStowPosition()){
                    newState = ElevatorState.HatchStowPosition;
                }else if(StartPosition()){
                    newState = ElevatorState.StartPosition;
                }
        break;


        case HatchStowPosition:
        setupDown();    
           // if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
            
        elevatorMotorOne.set(ControlMode.Position, setPosition(0));

            ClawRotation.state = ClawRotationState.StowPosition;
            //}    
            if(HatchIntakePosition()){
                newState = ElevatorState.HatchIntakePosition;
            }else if(HatchLowPosition()) {
                newState = ElevatorState.HatchLowPosition;
            }else if(HatchHighPosition()) {
                newState = ElevatorState.HatchHighPosition;
            }else if(HatchMidPosition()) {
                newState = ElevatorState.HatchMidPosition;
            }else if(CargoStowPosition()) {
                newState = ElevatorState.CargoStowPosition;
            }else if(StartPosition()){
                newState = ElevatorState.StartPosition;
            }
    break;
        case HatchMidRelease:
        setupDown();    
           // if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
            
        //elevatorMotorOne.set(ControlMode.Position, setPosition(-34.5));
        SmartDashboard.putString("Release", "HatchMidRelease");
        ClawRotation.state = ClawRotationState.ReleasePosition;
            //}    
            if(HatchIntakePosition()) {
                newState = ElevatorState.HatchIntakePosition;
            }else if(CargoIntakePosition()){
                newState = ElevatorState.CargoIntakePosition;
            }else if(HatchStowPosition()){
                newState = ElevatorState.HatchStowPosition;
            }else if(StartPosition()){
                newState = ElevatorState.StartPosition;
            }else if(CargoStowPosition()){
                newState = ElevatorState.CargoStowPosition;
            }else if(StartPosition()){
                newState = ElevatorState.StartPosition;
            }
    break;

    case HatchHighRelease:
    setupUp();    
       // if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
        
    //elevatorMotorOne.set(ControlMode.Position, setPosition(0));

        ClawRotation.state = ClawRotationState.ReleasePosition;
        //}    
        if(HatchIntakePosition()) {
            newState = ElevatorState.HatchIntakePosition;
        }else if(CargoIntakePosition()){
            newState = ElevatorState.CargoIntakePosition;
        }else if(HatchStowPosition()){
            newState = ElevatorState.HatchStowPosition;
        }else if(StartPosition()){
            newState = ElevatorState.StartPosition;
        }else if(CargoStowPosition()){
            newState = ElevatorState.CargoStowPosition;
        }
break;

case HatchLowRelease:
setupDown();    
   // if(ClawRotation.testSafeAngleHigh(15) && ClawRotation.testSafeAngleLow(120)){
    
    //elevatorMotorOne.set(ControlMode.Position, setPosition(-8.5));

    ClawRotation.state = ClawRotationState.ReleasePosition;
    //}    
    if(HatchIntakePosition()) {
        newState = ElevatorState.HatchIntakePosition;
    }else if(CargoIntakePosition()){
        newState = ElevatorState.CargoIntakePosition;
    }else if(HatchStowPosition()){
        newState = ElevatorState.HatchStowPosition;
    }else if(StartPosition()){
        newState = ElevatorState.StartPosition;
    }else if(CargoStowPosition()){
        newState = ElevatorState.CargoStowPosition;
    }
    
 break;   
            default:
            newState = ElevatorState.HatchStowPosition;
        break;
            }


            if (newState != state) {
                state = newState;
            }

            outputToSmartDashboard(); 
    } 

	public void setupUp() {
		elevatorMotorOne.configNominalOutputForward(0, 10);
		elevatorMotorOne.configNominalOutputReverse(0, 10);
		elevatorMotorOne.configPeakOutputForward(1, 10);
		elevatorMotorOne.configPeakOutputReverse(-1, 10);	
	}
	
	public void setupDown() {
		elevatorMotorOne.configNominalOutputForward(0, 10);
		elevatorMotorOne.configNominalOutputReverse(0, 10);
		elevatorMotorOne.configPeakOutputForward(0.5, 10);
		elevatorMotorOne.configPeakOutputReverse(-0.5, 10);
	}
    
    
    
	@Override
	public void outputToSmartDashboard() {
        SmartDashboard.putNumber("Elevator", elevatorMotorOne.getSelectedSensorPosition(0));
	}

	@Override
	public void setupSensors() {

        //Set Motor Relationship
        elevatorMotorTwo.follow(elevatorMotorOne);
        elevatorMotorThree.follow(elevatorMotorOne);
    
        elevatorMotorOne.setSensorPhase(true);
        elevatorMotorTwo.setInverted(false);
        elevatorMotorThree.setInverted(true);
       // elevatorMotorFour.setInverted(true);

		// Set Position To 0 When The Robot Turns On
		elevatorMotorOne.setSelectedSensorPosition(0, 0, 10);
				
		//Setup Sensor To Absolute
		elevatorMotorOne.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		
		//Set peak and nominal outputs
		elevatorMotorOne.configNominalOutputForward(0, 10);
		elevatorMotorOne.configNominalOutputReverse(0, 10);
		elevatorMotorOne.configPeakOutputForward(-1, 10);
		elevatorMotorOne.configPeakOutputReverse(1, 10);
		
		//Set allowable closed-loop error
		elevatorMotorOne.configAllowableClosedloopError(0, 0, 10);
		
		//Set closed loop gains in PID slot 0
		elevatorMotorOne.config_kF(0, Elevator_Kf, 10);
		elevatorMotorOne.config_kP(0, Elevator_Kp, 10);
		elevatorMotorOne.config_kI(0, Elevator_Ki, 10);
		elevatorMotorOne.config_kD(0, Elevator_Kd, 10);
	}
}