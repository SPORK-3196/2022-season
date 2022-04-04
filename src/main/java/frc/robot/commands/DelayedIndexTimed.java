// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Index;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.XboxController.*;
import static frc.robot.Constants.Shooter.*;
import static frc.robot.Constants.Status.*;


/** An example command that uses an example subsystem. */
public class DelayedIndexTimed extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Index index;
  boolean runIndex = false;
  boolean ballPassed = false;
  public Timer indexTimer = new Timer();

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DelayedIndexTimed(Index index) {
    this.index = index;
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(index);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    indexTimer.reset();
    indexTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (index.getIntakeSensor()) {
      runIndex = true;
      index.BallInTransit = true;
      indexTimer.reset();
      indexTimer.start();
    }

    if (index.getMidSensor()) {
      ballPassed = true;
    }


    /*
    if (indexTimer.get() > 0.45 && !index.getMidSensor()) {
      runIndex = false;
      index.BallInTransit = false;
    }
    */

    if (!index.getMidSensor() && ballPassed) {
      runIndex = false;
      ballPassed = false;
      index.BallInTransit = false;
    }

    if (index.getTopSensor()) {
      runIndex = false;
      index.BallInTransit = false;
    }

    if (index.BallInTransit) {
      runIndex = true;
    }

    
    if (runIndex) {
      index.runIndex();
      indexing = true;
    }
    else if (!runIndex) {
      index.stopIndex();
      indexing = false;
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
    return indexTimer.get() > 5;
  }
}
