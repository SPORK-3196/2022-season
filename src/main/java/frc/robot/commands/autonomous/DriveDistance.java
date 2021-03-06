/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


public class DriveDistance extends CommandBase {

  Drivetrain drivetrain;
  double distance = 100;
  double startingDistance;
  double finalDistance;

  public static PIDController Auto_PIDController = new PIDController(0.003, 0, 0);

  /**
   * Creates a new DriveForwardTimed.
   */
  public DriveDistance(Drivetrain drivetrain, double distance_cm) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.distance = distance_cm;
    Auto_PIDController.setTolerance(10);
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.frontRight.setNeutralMode(NeutralMode.Brake);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Brake);
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Brake);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Brake);

    // drivetrain.drivetrain = new DifferentialDrive(drivetrain.leftSide, drivetrain.rightSide);

    startingDistance = drivetrain.sensorUnitsToMeters(drivetrain.rearRight.getSelectedSensorPosition());

    finalDistance = startingDistance + distance;
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.drivetrain.arcadeDrive(Auto_PIDController.calculate(finalDistance - drivetrain.sensorUnitsToMeters(drivetrain.rearRight.getSelectedSensorPosition())), 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Auto_PIDController.atSetpoint();
    drivetrain.drivetrain = null;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Auto_PIDController.atSetpoint();
  }
}
