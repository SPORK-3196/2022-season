// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Drivetrain.*;
  
public class Drivetrain extends SubsystemBase {

  public WPI_TalonFX frontLeft = new WPI_TalonFX(1);
  public WPI_TalonFX frontRight = new WPI_TalonFX(2);
  public WPI_TalonFX rearLeft = new WPI_TalonFX(3);
  public WPI_TalonFX rearRight = new WPI_TalonFX(4);

  public PigeonIMU gyroscope = new PigeonIMU(12);

  public MotorControllerGroup leftSide = new MotorControllerGroup(frontLeft, rearLeft);
  public MotorControllerGroup rightSide = new MotorControllerGroup(frontRight, rearRight);

  public DifferentialDrive drivetrain = new DifferentialDrive(leftSide, rightSide);

  public Orchestra drivetrainOrchestra = new Orchestra();
  
  

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    leftSide.setInverted(true);
    drivetrain.setDeadband(0.1);
    frontLeft.setNeutralMode(NeutralMode.Brake);
    rearLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.setNeutralMode(NeutralMode.Brake);
    rearRight.setNeutralMode(NeutralMode.Brake);

    drivetrainOrchestra.addInstrument(frontLeft);
    drivetrainOrchestra.addInstrument(frontRight);
    drivetrainOrchestra.addInstrument(rearLeft);
    drivetrainOrchestra.addInstrument(rearRight);

    drivetrainOrchestra.loadMusic("Crab.chrp");
    

  }

  public void playMusic() {
    drivetrainOrchestra.play();
  }

  public double getYaw() {
    return gyroscope.getYaw();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    DT_FrontLeftEntry.setNumber(frontLeft.getMotorOutputPercent());
    DT_rearLeftEntry.setNumber(rearLeft.getMotorOutputPercent());
    DT_FrontRightEntry.setNumber(frontRight.getMotorOutputPercent());
    DT_rearRightEntry.setNumber(rearRight.getMotorOutputPercent());
    DT_PowerConstantEntry.setNumber(DT_PowerConstant * 100.0);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
