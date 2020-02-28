/**
 * Phoenix Software License Agreement
 *
 * Copyright (C) Cross The Road Electronics.  All rights
 * reserved.
 * 
 * Cross The Road Electronics (CTRE) licenses to you the right to 
 * use, publish, and distribute copies of CRF (Cross The Road) firmware files (*.crf) and 
 * Phoenix Software API Libraries ONLY when in use with CTR Electronics hardware products
 * as well as the FRC roboRIO when in use in FRC Competition.
 * 
 * THE SOFTWARE AND DOCUMENTATION ARE PROVIDED "AS IS" WITHOUT
 * WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT
 * LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO EVENT SHALL
 * CROSS THE ROAD ELECTRONICS BE LIABLE FOR ANY INCIDENTAL, SPECIAL, 
 * INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, COST OF
 * PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR SERVICES, ANY CLAIMS
 * BY THIRD PARTIES (INCLUDING BUT NOT LIMITED TO ANY DEFENSE
 * THEREOF), ANY CLAIMS FOR INDEMNITY OR CONTRIBUTION, OR OTHER
 * SIMILAR COSTS, WHETHER ASSERTED ON THE BASIS OF CONTRACT, TORT
 * (INCLUDING NEGLIGENCE), BREACH OF WARRANTY, OR OTHERWISE
 */

/**
 * Enable robot and slowly drive forward.
 * [1] If DS reports errors, adjust CAN IDs and firmware update.
 * [2] If motors are spinning incorrectly, first check gamepad (hold down btn1)
 * [3] If motors are still spinning incorrectly, correct motor inverts.
 * [4] Now that motors are driving correctly, check sensor phase.  If sensor is out of phase, adjust sensor phase.
 * [4] Is only necessary if you have sensors.
 */
package frc.robot;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Robot extends TimedRobot {
    /*
     * --- [1] Update CAN Device IDs and use WPI_VictorSPX where necessary ------
     */
    WPI_TalonSRX _rghtFront = new WPI_TalonSRX(1);
    WPI_TalonSRX _rghtFollower = new WPI_TalonSRX(2);
    WPI_TalonSRX _leftFront = new WPI_TalonSRX(3);
    WPI_TalonSRX _leftFollower = new WPI_TalonSRX(4);

    CANSparkMax shootL= new CANSparkMax(5, MotorType.kBrushless);
    CANSparkMax shootR= new CANSparkMax(6, MotorType.kBrushless);

    WPI_TalonSRX intake = new WPI_TalonSRX(7);

    WPI_TalonSRX conveyer = new WPI_TalonSRX(8);

    WPI_TalonSRX winchL = new WPI_TalonSRX(9);
    WPI_TalonSRX winchR = new WPI_TalonSRX(10);

    //drives
    Joystick _joystick = new Joystick(0);

    //intakes, conveyers, and shoots
    Joystick joystickExtras = new Joystick(1);

    DifferentialDrive _diffDrive = new DifferentialDrive(_leftFront, _rghtFront);


    @Override
    public void teleopPeriodic() {

        /* get gamepad stick values */
        double forw = -1 * _joystick.getRawAxis(1); /* positive is forward */
        double turn = +1 * _joystick.getRawAxis(2); /* positive is right */
        final boolean intakeIn = joystickExtras.getRawButton(9); /* is button is down, print joystick values */
        final boolean intakeOut = joystickExtras.getRawButton(10);
        final boolean conveyerIn = joystickExtras.getRawButton(11);
        final boolean conveyerOut = joystickExtras.getRawButton(12);
        final boolean halfSpeedBtn = joystickExtras.getRawButton(2);
        double up = -1 * joystickExtras.getRawAxis(1); //moving the winches up and down by push
        final boolean endgame = joystickExtras.getRawButton(1);
        final boolean shoot = _joystick.getRawButton(1);

        /* deadband gamepad 10% */
        if (Math.abs(forw) < 0.10) {
            forw = 0;
        }
        if (Math.abs(turn) < 0.10) {
            turn = 0;
        }
        if (Math.abs(up) < 0.10) {
            up = 0;
        }

        /* drive robot */
        _diffDrive.arcadeDrive(forw, turn);
        
        /*
         * [2] Make sure Gamepad Forward is positive for FORWARD, and GZ is positive for
         * RIGHT
         */
        


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

        //shooting a ball
        if (shoot){
            shootL.set(1.0);
            shootR.set(-1.0);
            conveyer.set(1.0);
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

    @Override
    public void robotInit() {
        /* factory default values */
        _rghtFront.configFactoryDefault();
        _rghtFollower.configFactoryDefault();
        _leftFront.configFactoryDefault();
        _leftFollower.configFactoryDefault();

        /* set up followers */
        _rghtFollower.follow(_rghtFront);
        _leftFollower.follow(_leftFront);

        /* [3] flip values so robot moves forward when stick-forward/LEDs-green */
        _rghtFront.setInverted(true); // !< Update this
        _leftFront.setInverted(false); // !< Update this

        /*
         * set the invert of the followers to match their respective master controllers
         */
        _rghtFollower.setInverted(InvertType.FollowMaster);
        _leftFollower.setInverted(InvertType.FollowMaster);

        /*
         * [4] adjust sensor phase so sensor moves positive when Talon LEDs are green
         */
        _rghtFront.setSensorPhase(true);
        _leftFront.setSensorPhase(true);

        /*
         * WPI drivetrain classes defaultly assume left and right are opposite. call
         * this so we can apply + to both sides when moving forward. DO NOT CHANGE
         */
        _diffDrive.setRightSideInverted(false);
    }
}
