// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Lighting;

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


    if (DriverStation.isDisabled()) {
      if (DriverStation.getAlliance().compareTo(DriverStation.Alliance.Blue) == 0) {
        lights.fullBlue();
      }
      else if (DriverStation.getAlliance().compareTo(DriverStation.Alliance.Red) == 0) {
        lights.fullRed();
      }
      else {
        lights.FullRainbow();
        // lights.fullWhite();c
      }
      
    }

    if (DriverStation.isTeleopEnabled()) {
      lights.FullRainbow();
    }

    if (DriverStation.isAutonomousEnabled()) {
      lights.noColor();
    }

    if (indexing) {
      lights.fullWhite();
      if (DriverStation.isAutonomousEnabled()) {
        lights.noColor();
      }
    }


    if (SHOOTER_READY) {
      lights.fullGreen();
    }

    lights.start();
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    lights.fullWhite();
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
