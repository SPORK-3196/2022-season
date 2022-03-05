// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Shooter.*;
import static frc.robot.Constants.RobotConstants.*;

public class Shooter extends SubsystemBase { // Made By Caputo
  public CANSparkMax leftShooter = new CANSparkMax(17, MotorType.kBrushless);
  public CANSparkMax rightShooter = new CANSparkMax(14, MotorType.kBrushless);

  public RelativeEncoder shooterEncoder = leftShooter.getEncoder();

  public PIDController shooterPIDController = new PIDController(0.000015, 0.0003481, 0);
  // public PIDController shooterPIDController = new PIDController(0.00005, 0.0002, 5.0);

  public double sparkVelocityRPM;
  
  /** Creates a new SparkTest. */
  public Shooter() {
    rightShooter.setInverted(true);
    shooterPIDController.setTolerance(150);
    // shooterPIDController.setFeedForward(0.00000481);
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
    shooterPIDController.setSetpoint(-1 * RPM);
  }

  public double calculate(double currentRPM) {
    return shooterPIDController.calculate(currentRPM);
  }

  public double getVelocity() {
    return shooterEncoder.getVelocity();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    sparkVelocityRPM = shooterEncoder.getVelocity();

    SH_SHOOTER_RPM_Entry.setDouble(sparkVelocityRPM);

    SH_SHOOTER_RPM_Entry.setDouble( ((sparkVelocityRPM * SparkWheelDiameterInches) * 60 * Math.PI) / 63360 );
    
    // System.out.println(sparkVelocityRPM);

    if (shooterPIDController.atSetpoint()) {
      SHOOTER_READY = true;
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