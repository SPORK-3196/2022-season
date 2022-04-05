// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Lighting;
import static frc.robot.Constants.Status.*;
import static frc.robot.Constants.Shooter.*;

import static frc.robot.Constants.Vision.*;
import static frc.robot.Constants.XboxController.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;


/** An example command that uses an example subsystem. */
public class LightingControl extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Lighting lights;
   
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public LightingControl (Lighting lights) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.lights = lights;
    addRequirements(lights);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (teleop) {
      if (primaryHasTargets && X2_AButton) {
        lights.redGreenOffset(Math.abs(primaryYaw), 8);
      }
      else {
        lights.FullRainbow();
      }
    }


    if (DriverStation.isDisabled()) {
      if (DriverStation.getAlliance().compareTo(DriverStation.Alliance.Blue) == 0) {
        lights.fullBlue();
      }
      else if (DriverStation.getAlliance().compareTo(DriverStation.Alliance.Red) == 0) {
        lights.fullRed();
      }
      else {
        lights.FullRainbow();
      }
      
    }

    if (DriverStation.isAutonomousEnabled()) {
      lights.fullBlue();
    }

    if (indexing) {
      lights.fullWhite();
    }

    if (rampingUp) {
      lights.fullYellow();
    }

    if (SHOOTER_READY) {
      lights.fullGreen();
    }

    lights.start();
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // lights.FullRed();
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
