public class Intake implements cmd{
WPI_TalonSRX intake;
Joystick joystickExtras;

  public Intake(WPI_TalonSRX intake, Joystick joystickExtras){
    this.intake = intake;
    this.joystickExtras = joystickExtras;

  }
  public void autonomousInit(){}
  public void autonomousPeriodic(){}
  public void testInit(){}
  public void testPeriodic(){}
  public void teleopInit(){}
  public void teleopPeriodic(){
    final boolean intakeIn = joystickExtras.getRawButton(9); /* is button is down, print joystick values */
    final boolean intakeOut = joystickExtras.getRawButton(10);

    //moving the intake motor
    if (intakeIn) {
        intake.set(-1.0);
    }else{
        intake.set(0.0);
    }

    if (intakeOut) {
        intake.set(1.0);
    }else{
        intake.set(0.0);
    }

}
