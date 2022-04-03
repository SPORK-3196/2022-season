// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Lighting;
import static frc.robot.Constants.Status.*;
import static frc.robot.Constants.Shooter.*;

import edu.wpi.first.networktables.NetworkTableInstance;
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
    lights.FullRed();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (teleop) {
      lights.FullRainbow();
    }

    if (disabled) {
      if (NetworkTableInstance.getDefault().getTable("FMSInfo").getEntry("IsRedAlliance").getBoolean(false)) {
        lights.FullRed();
      }
      else {
        lights.FullBlue();
      }
      
    }

    if (autonomous) {
      lights.FullBlue();
    }

    if (indexing) {
      lights.FullWhite();
    }

    if (rampingUp) {
      lights.FullYellow();
    }

    if (SHOOTER_READY) {
      lights.FullGreen();
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
