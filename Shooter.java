public class Shooter implements cmd{
  CANSparkMax shootL;
  CANSparkMax shootR;
  Joystick joystick;

  public Shooter(CANSparkMax shootL, CANSparkMax shootR, Joystick joystick){
    this.shootL = shootL;
    this.shootR = shootR;
    this.joystick = joystick;

  }
  public void autonomousInit(){}
  public void autonomousPeriodic(){}
  public void testInit(){}
  public void testPeriodic(){}
  public void teleopInit(){}
  public void teleopPeriodic(){
    final boolean shoot = joystick.getRawButton(1);
    //shooting a ball
    if (shoot){
        shootL.set(1.0);
        shootR.set(-1.0);
        conveyer.set(1.0); 
    }
  }
}
