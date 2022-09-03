/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Timer; 
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveForwardTimed extends CommandBase {

  Drivetrain drivetrain;

  public Timer driveTimer = new Timer();
  public double time = 3.0;
  public double drivePower = 0.6;
  public double startingAngle;
  public double turningPower;


  /**
   * Creates a new DriveForwardTimed.
   */
  public DriveForwardTimed(Drivetrain drivetrain, double duration, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.time = duration;
    this.drivePower = speed;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);

    startingAngle = drivetrain.getGyroHeadingDeg();

    driveTimer.reset();
    driveTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turningPower = 0.03 * (startingAngle - drivetrain.getGyroHeadingDeg());
    drivetrain.arcadeDriveAI(drivePower, turningPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.arcadeDrive(0.0, 0.0);
    // drivetrain.drivetrain = null;
    // drivetrain.sensorUnitsToMeters(drivetrain.frontLeft.getSelectedSensorPosition());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return driveTimer.get() >= time;
  }
}
