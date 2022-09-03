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

public class TurnDegreesCCW extends CommandBase {

  Drivetrain drivetrain;

  public Timer turnTimer = new Timer();
  public double time = 3.0;
  public double startingAngle;
  public double turningPower;
  public double angle = 90;
  public double targetAngle;


  /**
   * Creates a new TurnDegrees for 90 Degrees.
   */
  public TurnDegreesCCW(Drivetrain drivetrain, double duration) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.time = duration;
    addRequirements(drivetrain);
  }

  /**
   * Creates a new TurnDegrees.
   */
  public TurnDegreesCCW(Drivetrain drivetrain, double duration, double angle) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    this.time = duration;
    this.angle = angle;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);

    // drivetrain.drivetrain = new DifferentialDrive(drivetrain.leftSide, drivetrain.rightSide);

    startingAngle = drivetrain.getGyroHeadingDeg();
    targetAngle = startingAngle + angle;

    turnTimer.reset();
    turnTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Angle Difference: " + (targetAngle - drivetrain.getGyroHeadingDeg()));
    
    turningPower = 0.006 * (targetAngle - drivetrain.getGyroHeadingDeg());
    System.out.println("Turning Power: " + turningPower);

    drivetrain.arcadeDrive(0, turningPower);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drivetrain.arcadeDrive(0.0, 0.0);
    // drivetrain.drivetrain = null;
    targetAngle = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return turnTimer.get() >= time;
  }
}
