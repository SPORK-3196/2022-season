// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DrivetrainValues.*;

public class Drivetrain extends SubsystemBase {

  public WPI_TalonFX frontLeft = new WPI_TalonFX(1);
  public WPI_TalonFX backLeft = new WPI_TalonFX(2);
  public WPI_TalonFX frontRight = new WPI_TalonFX(3);
  public WPI_TalonFX backRight = new WPI_TalonFX(4);

  public MotorControllerGroup leftSide = new MotorControllerGroup(frontLeft, backLeft);
  public MotorControllerGroup rightSide = new MotorControllerGroup(frontRight, backRight);

  public DifferentialDrive drivetrain = new DifferentialDrive(leftSide, rightSide);

  public Orchestra drivetrainOrchestra;
  
  /** Creates a new Drivetrain. */
  public Drivetrain() {
    rightSide.setInverted(true);
    
    frontLeft.setNeutralMode(NeutralMode.Brake);
    backLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.setNeutralMode(NeutralMode.Brake);
    backRight.setNeutralMode(NeutralMode.Brake);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    DT_FrontLeftEntry.setNumber(frontLeft.getMotorOutputPercent());
    DT_BackLeftEntry.setNumber(backLeft.getMotorOutputPercent());
    DT_FrontRightEntry.setNumber(frontRight.getMotorOutputPercent());
    DT_BackRightEntry.setNumber(backRight.getMotorOutputPercent());
    DT_PowerConstantEntry.setNumber(DT_PowerConstant * 100.0);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
