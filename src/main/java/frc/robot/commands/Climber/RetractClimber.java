// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** A RetractClimber command that uses a climber subsystem. */
public class RetractClimber extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Climber climber;
  private double power;
  
  /**
   * Creates a new RetractClimber.
   *
   * @param climber The climber subsystem used by this command.
   */
  public RetractClimber (Climber climber, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.power = power;
    this.climber = climber;
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Retract both climber arms at some power at the beginning of the command
    climber.RetractLeft(power);
    climber.RetractRight(power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop retracting climber arms at the end of command
    climber.StopLeft();
    climber.StopRight();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
