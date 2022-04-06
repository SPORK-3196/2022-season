// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Shooter.*;

import static frc.robot.Constants.Status.*;
import static frc.robot.Constants.XboxController.*;

public class Shooter extends SubsystemBase { // Oguntola Trademark
  public CANSparkMax leftShooter = new CANSparkMax(5, MotorType.kBrushless);
  public CANSparkMax rightShooter = new CANSparkMax(6, MotorType.kBrushless);

  public RelativeEncoder leftShooterEncoder = leftShooter.getEncoder();
  public RelativeEncoder rightShooterEncoder = rightShooter.getEncoder();

  public SparkMaxPIDController leftPIDController = leftShooter.getPIDController();
  public SparkMaxPIDController rightPIDController = rightShooter.getPIDController();
  public SimpleMotorFeedforward ffController = new SimpleMotorFeedforward(0, 0);
  // new PIDController(0.000015, 0.0004, 0);
  
  // public PIDController leftPIDController = new PIDController(0.00005, 0.0002, 5.0);

  public double leftTargetRPM;
  public double rightTargetRPM;
  public double tolerance = 50;
  public Timer PIDTimer = new Timer();
  public double sparkVelocityRPM;
  
  /** Creates a new SparkTest. */
  public Shooter(double tolerance) {
    leftPIDController.setP(0.00006);
    leftPIDController.setI(0.0000004);
    leftPIDController.setD(0.004);
    leftPIDController.setIZone(0);
    leftPIDController.setFF(0.000015);
    leftPIDController.setOutputRange(-1, 1);

    rightPIDController.setP(0.00006);
    rightPIDController.setI(0.0000004);
    rightPIDController.setD(0.004);
    rightPIDController.setIZone(0);
    rightPIDController.setFF(0.000015);
    rightPIDController.setOutputRange(-1, 1);
    this.tolerance = tolerance;
  }
  
 
  public void runShooter(double power) {
    leftShooter.set(power);
    rightShooter.set(power);
  }

  public void stopShooter() {    
    leftShooter.stopMotor();
    rightShooter.stopMotor();
    leftTargetRPM = 0;
    rightTargetRPM = 0;
  } 
  
  public void feedForwardShoot(double RPM) {
    double voltageValue = ffController.calculate(-1 * RPM);
    leftShooter.setVoltage(voltageValue);
    rightShooter.setVoltage(voltageValue);
  }

  public void setTolerance(double tolerance) {
    this.tolerance = tolerance;
  }

  public void setLeftSetpoint(double RPM) { 
    leftPIDController.setReference(-1 * RPM, CANSparkMax.ControlType.kVelocity);
    leftTargetRPM = -1 * RPM;
  }

  public void setRightSetpoint(double RPM) { 
    rightPIDController.setReference(-1 * RPM, CANSparkMax.ControlType.kVelocity);
    rightTargetRPM = -1 * RPM;
  }
  
  public void setSetpoint(double RPM) {
    setLeftSetpoint(RPM);
    setRightSetpoint(RPM);
  }
  
  public boolean leftAtSetpoint() {
    return ((leftTargetRPM - tolerance) < leftShooterEncoder.getVelocity()) &&  (leftShooterEncoder.getVelocity() < (leftTargetRPM + tolerance));
  }

  public boolean rightAtSetpoint() {
    return ((rightTargetRPM - tolerance) < rightShooterEncoder.getVelocity()) &&  (rightShooterEncoder.getVelocity() < (rightTargetRPM + tolerance));
  }

  public boolean atSetpoint() {
    return (leftAtSetpoint() && rightAtSetpoint());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    sparkVelocityRPM = -leftShooterEncoder.getVelocity();
    SH_SHOOTER_RPM_Entry.setDouble(sparkVelocityRPM);

    // SH_SHOOTER_RPM_Entry.setDouble( ((sparkVelocityRPM * SparkWheelDiameterInches) * 60 * Math.PI) / 63360 );
    
    rampingUp = X1_AButton;
    

    if (sparkVelocityRPM > 100) {
      if (atSetpoint()) {
        SHOOTER_READY = true;
      }
      else {
        PIDTimer.reset();
        SHOOTER_READY = false;
      }
    }

    SH_ShooterPower = (((leftShooterEncoder.getVelocity() + rightShooterEncoder.getVelocity()) / 2) / 5700) * 100;
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}