// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.GlobalVars.Shooter.AutoComputedRPM;
import static frc.robot.GlobalVars.Shooter.SHOOTER_READY;
import static frc.robot.GlobalVars.Shooter.SH_SHOOTER_RPM_Entry;
import static frc.robot.GlobalVars.XboxController.X2_AButton;
import static frc.robot.GlobalVars.XboxController.X2_LJS;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase { // Oguntola Trademark
  /* 
  The shooter subsystem opts for the use of a feed forward controller, instead of a PID controller
  to regulate the power of the motors. This choice was made in consideration of the 0.1s delay from
  the hall effect sensor built into NEO motors, which makes PID controllers less efficient compared
  to the responsiveness of a feed forward controller.
  */

  // Left and Right are with the shooter and limelight facing forward
  public CANSparkMax leftShooter = new CANSparkMax(5, MotorType.kBrushless); // Clockwise
  public CANSparkMax rightShooter = new CANSparkMax(6, MotorType.kBrushless); // Counter Clockwise

  // Encoders for the left and right motors, respectively
  public RelativeEncoder leftShooterEncoder = leftShooter.getEncoder();
  public RelativeEncoder rightShooterEncoder = rightShooter.getEncoder();

  // Feed Foward Controller for shooter motors
  public SimpleMotorFeedforward ffController = new SimpleMotorFeedforward(0.027218, 0.12757, 0.12757);

  // Target RPM for the left motor
  public double leftTargetRPM;

  // Target RPM for the right motor
  public double rightTargetRPM;

  // Tolerance of shooter Target RPM
  public double RPM_Tolerance = 50;

  public double sparkVelocityRPM;
  

  /** Creates a new Shooter subsystem with a parameter for RPM tolerance. */
  public Shooter(double tolerance) {
    this.RPM_Tolerance = tolerance;
    
    // Reduce the periodic frame rate of the left and right shooters
    leftShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 10);
    leftShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 50);
    rightShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 10);
    rightShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 50);
  }

  /** Creates a new Shooter subsystem with the default tolerance of 50 RPM */
  public Shooter() {
    // Reduce the periodic frame rate of the left and right shooters
    leftShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 10);
    leftShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 50);
    rightShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 10);
    rightShooter.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 50);
  }
  


  /** Set shooter motor speed to some percentage speed */ 
  public void runShooter(double power) {
    leftShooter.set(power);
    rightShooter.set(power);
  }

  /** Set shooter motor speed to some voltage allowing for a consistent speed despite battery voltage sag */ 
  public void setShooterVoltage(double voltage) {
    leftShooter.setVoltage(-1 * voltage);
    rightShooter.setVoltage(voltage);
  }

  /** Stops shooter motors, and sets target RPM of both shooters to zero. */
  public void stopShooter() {    
    leftShooter.stopMotor();
    rightShooter.stopMotor();

    // Set target shooter RPM to 0.
    leftTargetRPM = 0;
    rightTargetRPM = 0;
  } 
  
  /** Set shooter motor speed to some voltage based off input RPM */ 
  public void feedForwardShoot(double RPM) {
    double voltageValue = ffController.calculate(-1 * RPM / 60);

    leftTargetRPM = 1 * RPM;
    rightTargetRPM = 1 * RPM;

    setShooterVoltage(voltageValue);
  }

  /** Set the RPM tolerance of the shooter */
  public void setTolerance(double tolerance) {
    this.RPM_Tolerance = tolerance;
  }

  /** Set the target RPM for the left shooter motor */
  public void setLeftSetpoint(double RPM) { 
    leftTargetRPM = 1 * RPM;
  }

  /** Set the target RPM for the right shooter motor */
  public void setRightSetpoint(double RPM) { 
    rightTargetRPM = -1 * RPM;
  }
  
  /** Returns true if value is within a given rolerance, and false otherwise.*/
  public boolean isWithinTolerance(double value, double tolerance) {
    return ((value - tolerance) < value && value < (value + tolerance));
  }

  /** Set the target RPM for both shooter motors */
  public void setSetpoint(double RPM) {
    setLeftSetpoint(RPM);
    setRightSetpoint(RPM);
  }
  
  /** Returns true when the left shooter is within target RPM tolerance */
  public boolean leftAtSetpoint() {
    return isWithinTolerance(Math.abs(leftShooterEncoder.getVelocity()), RPM_Tolerance);
  }

  /** Returns true when the right shooter is within target RPM tolerance */
  public boolean rightAtSetpoint() {
    return isWithinTolerance(Math.abs(rightShooterEncoder.getVelocity()), RPM_Tolerance);
  }

  /** Returns true when both shooters are within target RPM tolerance */
  public boolean atSetpoint() {
    return leftAtSetpoint() && rightAtSetpoint();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Set the network table entry to the Limelight calculated RPM
    SH_SHOOTER_RPM_Entry.setDouble(AutoComputedRPM);   

    // If either shooting button is held, or the target RPM of the shooter is greater than 0, continue
    if ((X2_AButton || X2_LJS) || (leftTargetRPM > 0 && rightTargetRPM > 0)) {
      // If the shooter is at the target RPM, set SHOOTER_READY to true, otherwise keep it false.
      if (atSetpoint()) {
        SHOOTER_READY = true;
      }
      else {
        SHOOTER_READY = false;
      }
    }
    else {
      SHOOTER_READY = false;
    }

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

}