// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Shooter.*;
import static frc.robot.Constants.XboxController.*;
import static frc.robot.Robot.*;

public class Shooter extends SubsystemBase { // Oguntola Trademark
  public CANSparkMax leftShooter = new CANSparkMax(5, MotorType.kBrushless);
  public CANSparkMax rightShooter = new CANSparkMax(6, MotorType.kBrushless);

  public RelativeEncoder leftShooterEncoder = leftShooter.getEncoder();
  public RelativeEncoder rightShooterEncoder = rightShooter.getEncoder();

  public SparkMaxPIDController leftPIDController = leftShooter.getPIDController();
  public SparkMaxPIDController rightPIDController = rightShooter.getPIDController();
  // new PIDController(0.000015, 0.0004, 0);
  
  // public PIDController leftPIDController = new PIDController(0.00005, 0.0002, 5.0);

  public double targetRPM;
  public double tolerance = 50;
  public Timer PIDTimer = new Timer();
  
  /** Creates a new SparkTest. */
  public Shooter(double tolerance) {
    // rightShooter.setInverted(false);
    leftPIDController.setP(0.00006);
    leftPIDController.setI(0.0000004);
    leftPIDController.setD(0.0035);
    leftPIDController.setIZone(0);
    leftPIDController.setFF(0.000015);
    leftPIDController.setOutputRange(-1, 1);

    rightPIDController.setP(0.00006);
    rightPIDController.setI(0.0000004);
    rightPIDController.setD(0.0035);
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
  } 

  public void setSetpoint(double RPM) {
    leftPIDController.setReference(-1 * RPM, CANSparkMax.ControlType.kVelocity);
    rightPIDController.setReference(-1 * RPM, CANSparkMax.ControlType.kVelocity);
    targetRPM = -1 * RPM;
  }

  public void setTolerance(double tolerance) {
    this.tolerance = tolerance;
  }
  
  /*
  public double calculate(double currentRPM) {
    return leftPIDController.calculate(currentRPM);
  }
  */


  public boolean leftAtSetpoint() {
    return ((targetRPM - tolerance) < leftShooterEncoder.getVelocity()) &&  (leftShooterEncoder.getVelocity() < (targetRPM + tolerance));
  }

  public boolean rightAtSetpoint() {
    return ((targetRPM - tolerance) < rightShooterEncoder.getVelocity()) &&  (rightShooterEncoder.getVelocity() < (targetRPM + tolerance));
  }

  public boolean atSetpoint() {
    return (leftAtSetpoint() && rightAtSetpoint());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // SH_SHOOTER_RPM_Entry.setDouble(sparkVelocityRPM);

    // SH_SHOOTER_RPM_Entry.setDouble( ((sparkVelocityRPM * SparkWheelDiameterInches) * 60 * Math.PI) / 63360 );
    
    

    
    if (atSetpoint()) {
      SHOOTER_READY = true;
      if (X2_AButton) {
        for (int i = 0; i < LIGHT_BUFFER.getLength(); i++) {
          LIGHT_BUFFER.setRGB(i, 0, 0, 255);
        }
        LIGHTS.setData(LIGHT_BUFFER);
      }
    }
    else {
      PIDTimer.reset();
      SHOOTER_READY = false;
    }

    SH_ShooterPower = (((leftShooterEncoder.getVelocity() + rightShooterEncoder.getVelocity()) / 2) / 5700) * 100;
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}