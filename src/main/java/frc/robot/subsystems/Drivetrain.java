// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

  public WPI_TalonFX leftUp = new WPI_TalonFX(1);
  public WPI_TalonFX leftDown = new WPI_TalonFX(2);
  public WPI_TalonFX rightDown = new WPI_TalonFX(3);
  public WPI_TalonFX rightUp = new WPI_TalonFX(4);

  MotorControllerGroup leftSide = new MotorControllerGroup(leftUp, leftDown);
  MotorControllerGroup rightSide = new MotorControllerGroup(rightDown, rightUp);

  public DifferentialDrive drivetrain = new DifferentialDrive(leftSide, rightDown);

  public Orchestra drivetrainOrchestra;
  
  /** Creates a new ExampleSubsystem. */
  public Drivetrain() {


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
