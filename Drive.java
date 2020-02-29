public class Drive implements cmd{

WPI_TalonSRX rtFront;
WPI_TalonSRX rtFollower;
WPI_TalonSRX leftFront;
WPI_TalonSRX leftFollower;
Joystick joystick;
DifferentialDrive diffDrive;
  public Drive(WPI_TalonSRX rtFront, WPI_TalonSRX rtFollower, WPI_TalonSRX leftFront, WPI_TalonSRX leftFollower, Joystick joystick){
    this.rtFront= rtFront;
    this.rtFollower= rtFollower;
    this.leftFront= leftFront;
    this.leftFollower= leftFollower;
    this.joystick = joystick;
    diffDrive = new DifferentialDrive(leftFront, rtFront);
  }
  public void autonomousInit(){}
  public void autonomousPeriodic(){}
  public void testInit(){}
  public void testPeriodic(){}
  public void teleopInit(){}
  public void teleopPeriodic(){
    /* get gamepad stick values */
    double forw = -1 * joystick.getRawAxis(1); /* positive is forward */
    double turn = +1 * joystick.getRawAxis(2); /* positive is right */

    diffDrive.arcadeDrive(forw, turn);

    /* deadband gamepad 10% */
    if (Math.abs(forw) < 0.10) {
        forw = 0;
    }
    if (Math.abs(turn) < 0.10) {
        turn = 0;
    }
  }
}
