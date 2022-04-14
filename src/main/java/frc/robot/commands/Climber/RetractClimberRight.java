// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** A command that uses a climber subsystem. */
public class RetractClimberRight extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Climber climber;
  private double power;
  
  /**
   * Creates a new RetractClimberRight.
   *
   * @param subsystem The subsystem used by this command.
   */
  public RetractClimberRight (Climber subsystem, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.power = power;
    this.climber = subsystem;
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   climber.RetractRight(power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.StopRight();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
