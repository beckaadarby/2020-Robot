public class Conveyer implements cmd{
WPI_TalonSRX conveyer;
Joystick joystickExtras;

  public Conveyer(WPI_TalonSRX conveyer, Joystick joystickExtras){
    this.conveyer = conveyer;
    this.joystickExtras = joystickExtras;

  }
  public void autonomousInit(){}
  public void autonomousPeriodic(){}
  public void testInit(){}
  public void testPeriodic(){}
  public void teleopInit(){}
  public void teleopPeriodic(){
    final boolean conveyerIn = joystickExtras.getRawButton(11);
    final boolean conveyerOut = joystickExtras.getRawButton(12);
    final boolean halfSpeedBtn = joystickExtras.getRawButton(2);

    //moving the conveyer motor
    if (conveyerIn) {
        if (halfSpeedBtn){
            conveyer.set(0.5);
        }else{
            conveyer.set(-1.0);
        }
    }else{
        conveyer.set(0.0);
    }
    if (conveyerOut) {
        if (halfSpeedBtn){
            conveyer.set(0.5);
        }else{
            conveyer.set(1.0);
        }
    }else{
        conveyer.set(0.0);
    }

  }
}
