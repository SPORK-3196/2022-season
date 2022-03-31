// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Index;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.XboxController.*;
import static frc.robot.Constants.Shooter.*;
import static frc.robot.Constants.Index.*;


/** An example command that uses an example subsystem. */
public class IndexControl extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Index index;
  boolean runIndex = false;
  public Timer indexTimer = new Timer();

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public IndexControl(Index index) {
    this.index = index;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(index);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    if (!index.getIntakeSensor() && !index.getMidSensor() && !index.getTopSensor()) {
      runIndex = false;
      IndexEmpty = true;
    }

    

    
    if (index.getIntakeSensor() || index.BallInTransit) {
      runIndex = true;
      index.BallInTransit = true;
      IndexEmpty = false;
    }

    if (index.getIntakeSensor() && X2_BButton) {
      runIndex = false;
      index.BallInTransit = false;
      IndexEmpty = false;
    }


    
    if (index.getMidSensor()) {
      runIndex = true;
      index.BallInTransit = true;
      IndexEmpty = false;
      indexTimer.reset();
      indexTimer.start();
    }

    
    if (!index.getIntakeSensor() && !index.getMidSensor() && index.BallInTransit && indexTimer.get() > 0.1) {
      runIndex = false;
      index.BallInTransit = false;
      IndexEmpty = false;
    }
    

    if (index.getMidSensor() && index.getIntakeSensor()) {
      runIndex = true;
      index.BallInTransit = true;
    }

    if (index.getTopSensor()) {
      runIndex = false;
      index.BallInTransit = false;
      IndexEmpty = false;
    }

    if (X2_AButton) {
      if (SHOOTER_READY) {
        runIndex = true;
      }
      if (!SHOOTER_READY) {
        runIndex = false;
      }
    }

    // runIndex = true;
    

    if (runIndex) {
      index.runIndex();
    }
    else if (!runIndex) {
      index.stopIndex();
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    index.stopIndex();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
