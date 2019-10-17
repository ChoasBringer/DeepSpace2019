package frc.robot;

public class Constants {
    //Drivetrain CAN Values
    public static final int RIGHT_MASTER_MOTOR_CAN = 3;
	public static final int RIGHT_SLAVE_MOTOR_CAN = 4;
	public static final int LEFT_MASTER_MOTOR_CAN = 1;
    public static final int LEFT_SLAVE_MOTOR_CAN = 2;
    
    //Drivetrain Pnuematics
	public static final int SHIFTER_OPEN = 4;
    public static final int SHIFTER_CLOSE = 5;
    
    //Elevator
    public static final int ELEVATOR_MOTOR_CAN_1 = 5;
	public static final int ELEVATOR_MOTOR_CAN_2 = 12;
    public static final int ELEVATOR_MOTOR_CAN_3 = 11;
    
    //ClawWheels
    public static final int CLAWWHEELS_MOTOR_CAN = 7;

    //ClawTurn
    public static final int CLAWTURN_MOTOR_CAN = 8;

    //ClawRotation
    public static final int CLAWROTATION_MOTOR_CAN = 9;

    //Stick USB Values
    public static final int DRIVE_USB_PORT = 0;
    public static final int OPERATOR_USB_PORT = 1;

        //Drivetrain Buttons
        public static final int DRIVETRAIN_LOWGEAR = 1;
		public static final int DRIVETRAIN_HIGHGEAR = 2;

        //Elevator Buttons
        //1ST Controller Elevator Buttons
        
        public static final int SPIT = 3;
        public static final int CLOSE = 5;
        public static final int RELEASE = 3; 
        public static final int MANUELDEFENSE = 4;
        public static final int CARGOINTAKE = 6;

        //2ND Controller Elevator Buttons
        public static final int CARGOSTOW = 10;
        public static final int HATCHSTOW = 9;
        public static final int CARGOSHIP = 3;
        public static final int LOW = 1;
        public static final int MID = 2;
        public static final int HIGH = 0;
        public static final int STARTPOSITION = 8;
        public static final int HATCHHIGHRELEASE = 9;
        public static final int HATCHMIDRELEASE = 10;
        public static final int INTAKE = 5;
        
        //ClawWheels Buttons
        public static final int CLAW_SPIT = 3;
        public static final int CLAW_INTAKE = 2;

        //ClawTurn Buttons
        public static final int OPEN = 6;

        //Elevator PID Values
    public static final double Elevator_Average_Ticks_Per_Inch = 1600;//1600, 3346
    public static final double Elevator_Kf = 0;
	public static final double Elevator_Kp = 0.1;//0.1
	public static final double Elevator_Ki = 0;
	public static final double Elevator_Kd = 0;
	public static final double Safe_Position = 0;
	public static final double Safe_Position_High = 0;

	//Wheel Turn PID Values
    public static final double WheelTurn_Average_Ticks_Per_Degree = 0;//19.23
	public static final double Wheel_Turn_Kf = 0;
	public static final double Wheel_Turn_Kp = 0;//0.5
	public static final double Wheel_Turn_Ki = 0;//0.001
	public static final double Wheel_Turn_Kd = 0;
	public static final double Wheel_Turn_Safe_Angle = 0; 

	//Claw Rotation PID Values
	public static final double ClawRotation_Average_Ticks_Per_Degree = 43.55;//42.15
	public static final double Claw_Rotation_Kf = 0;
	public static final double Claw_Rotation_Kp = 0.3;//0.35
	public static final double Claw_Rotation_Ki = 0;
	public static final double Claw_Rotation_Kd = 0;
	public static final double Claw_Rotation_Safe_Angle = 0;

    //Hatch Pickup PID Values
    }