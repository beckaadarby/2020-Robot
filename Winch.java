public class Winch implements cmd{
  WPI_TalonSRX winchL;
  WPI_TalonSRX winchR;
  Joystick joystickExtras;

  public Winch(  WPI_TalonSRX winchL, WPI_TalonSRX winchR, Joystick joystickExtras){
    this.winchL = winchL;
    this.winchR = winchR;
    this.joystickExtras = joystickExtras;

  }
  public void autonomousInit(){}
  public void autonomousPeriodic(){}
  public void testInit(){}
  public void testPeriodic(){}
  public void teleopInit(){}
  public void teleopPeriodic(){

    double up = -1 * joystickExtras.getRawAxis(1); //moving the winches up and down by push
    final boolean endgame = joystickExtras.getRawButton(1);

    if (Math.abs(up) < 0.10) {
        up = 0;
    }
    
    //start to climb at endgame
            if (endgame) {
                winchL.set(up);
                winchR.set(up);
            }else{
                winchL.set(0.0);
                winchR.set(0.0);
            }
  }
}
