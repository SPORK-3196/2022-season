// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** A ExtendClimber command that uses a climber subsystem. */
public class ExtendClimber extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Climber climber;
  private double power;
  
  /**
   * Creates a new ExtendClimber.
   *
   * @param climber The climber subsystem used by this command.
   */
  public ExtendClimber (Climber climber, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.power = power;
    this.climber = climber;
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Extend both climber arms at some power at the beginning of the command
    climber.ExtendLeft(power);
    climber.ExtendRight(power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop extending climber arms at the end of command
    climber.StopLeft();
    climber.StopRight();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
