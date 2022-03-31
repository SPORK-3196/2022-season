// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Constants.XboxController.*;
import static frc.robot.Constants.Drivetrain.*;


/** An example command that uses an example subsystem. */
public class PlayMusic extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Drivetrain drivetrain;
  
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
    drivetrain.loadMusic(songChooser.getSelected());
    drivetrain.playMusic();
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
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return X1_BButton;
  }
}
