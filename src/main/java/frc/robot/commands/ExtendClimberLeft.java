// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** An example command that uses an example subsystem. */
public class ExtendClimberLeft extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Climber climber;
  private double power;
  
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ExtendClimberLeft (Climber subsystem, double power) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.power = power;
    this.climber = subsystem;
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climber.ExtendLeft(power);
    climber.ExtendRight(power);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // climber.ExtendLeft(power);
    // climber.ExtendRight(power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.StopLeft();
    climber.StopRight();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
