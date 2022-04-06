// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.Drivetrain.*;


/** An example command that uses an example subsystem. */
public class PlayMusic extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Drivetrain drivetrain;
  Timer lightTimer = new Timer();
  
  /**
   * Creates a new PlayMusic.
   *
   * @param subsystem The subsystem used by this command.
   */
  public PlayMusic(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.drivetrain = null;
    /*drivetrain.frontLeft.set(TalonFXControlMode.MusicTone, 0);
    drivetrain.frontRight.set(TalonFXControlMode.MusicTone, 0);
    drivetrain.rearLeft.set(TalonFXControlMode.MusicTone, 0);
    drivetrain.rearRight.set(TalonFXControlMode.MusicTone, 0);
    */
    if (drivetrain.currentSong != drivetrain.previousSong) {
      drivetrain.loadMusic(songChooser.getSelected());
      drivetrain.currentSong = songChooser.getSelected();
    }
    drivetrain.playMusic();
    lightTimer.reset();
    lightTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.playMusic();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.pauseMusic();
    drivetrain.drivetrain = null;
    drivetrain.previousSong = drivetrain.currentSong;
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
