// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Lighting;

import frc.robot.subsystems.Lighting;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** A ToggleClimberLighting command that uses a lighting subsystem. */
public class ToggleClimberLighting extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  Lighting lights;
  Timer lightTimer = new Timer();

  /**
   * Creates a new ToggleClimberLighting.
   *
   * @param lights The subsystem used by this command.
   */
  public ToggleClimberLighting (Lighting lights) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.lights = lights;
    addRequirements(lights);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    lights.fullYellow();
    lightTimer.reset();
    lightTimer.start();
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lights.fullWhite();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return lightTimer.get() > 2.0;
  }
}
