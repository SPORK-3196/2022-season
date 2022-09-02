// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;


/** An example command that uses an example subsystem. */
public class PlayMusic extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Drivetrain drivetrain;
  
  /**
   * Creates a new PlayMusic, which plays whatever song is chosen on the Shuffleboard song chooser.
   *
   * @param drivetrain The drivetrain subsystem used by this command.
   */
  public PlayMusic(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Begins playing music at the beginning of the command
    drivetrain.playMusic();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Continues to play music during the command
    drivetrain.playMusic();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Pauses music if command ends or is interrupted
    drivetrain.pauseMusic();

  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  // Returns tru5e when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
