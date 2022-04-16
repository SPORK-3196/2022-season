// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Shooter;
import frc.robot.commands.Intake.IntakeBalls;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/** An example command that uses an example subsystem. */
public class ThreeBallAuto extends SequentialCommandGroup {
    public ThreeBallAuto(Drivetrain drivetrain, Shooter shooter, Intake intake, Index index, Climber climber) {
      super(
        new InstantCommand(index::startWithOneBall, index),
        // Start index off with one ball
        
        new DelayedIndexConditional(index, 2)
          .alongWith(new DriveToPickup(drivetrain, shooter, climber, intake, 4.0, -0.4)),
        // Drive backwards and pickup ball behind you till 2 balls in index

        // new DriveToPickup(drivetrain, shooter, climber, intake, 4.0, -0.4),
        

        new AutonomousShootConditional(shooter, index, 0).alongWith(new IndexShootingUpperConditional(index, 0)),
        // Shoot into upper hub till empty

        new TurnDegreesCCW(drivetrain, 3, -25),
        // Turn a number of degrees (CCW is positive)
        
        new DelayedIndexConditional(index, 1)
          .alongWith(new DriveToPickup(drivetrain, shooter, climber, intake, 4.0, -0.4)),

        new AutonomousShootConditional(shooter, index, 0).alongWith(new IndexShootingUpperConditional(index, 0))
      );
    }
}
