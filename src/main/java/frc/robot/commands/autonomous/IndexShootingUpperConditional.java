// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Index;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.Shooter.*;
import static frc.robot.Constants.Index.*;


/** An example command that uses an example subsystem. */
public class IndexShootingUpperConditional extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  Index index;
  boolean runIndex = true;
  public Timer indexTimer = new Timer();
  double ballCondition = 0;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public IndexShootingUpperConditional(Index index, double condition) {
    this.index = index;
    this.ballCondition = condition;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(index);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    runIndex = true;
    indexTimer.reset();
    indexTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (index.getTopSensor()) {
      runIndex = false;
      index.BallExiting = true;
    }

  
    if (!index.getTopSensor() && index.BallExiting) {
      index.ballCounter--;
      index.BallExiting = false;
    }

    

   
    /*
    if (SHOOTER_READY && Math.abs(primaryYaw) < 6) {
      runIndex = true;
    }
    */

    if (SHOOTER_READY) {
      runIndex = true;
    }

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
    // return BallCounter_Entry.getDouble(0) == ballCondition;
    return indexTimer.get() > 2.5;
  }
}
