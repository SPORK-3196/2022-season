// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** A ToggleArms command that uses a climber subsystem. */
public class ToggleArms extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Climber climber;
  
  /**
   * Creates a new ToggleArms.
   *
   * @param climber The climber subsystem used by this command.
   */
  public ToggleArms(Climber climber) {
    this.climber = climber;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(climber);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    climber.ARM_PISTONS.toggle();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
