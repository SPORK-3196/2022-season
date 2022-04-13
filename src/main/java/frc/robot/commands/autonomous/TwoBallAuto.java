// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/** An example command that uses an example subsystem. */
public class TwoBallAuto extends SequentialCommandGroup {
    public TwoBallAuto(Drivetrain drivetrain, Shooter shooter, Intake intake, Index index, Climber climber) {
      super(
        new InstantCommand(index::startWithOneBall, index),
        new DelayedIndexConditional(index, 2).alongWith(new DriveToPickup(drivetrain, shooter, climber, intake, 4.0, -0.4)),
        // new AutoHorizontalAim(drivetrain, 2.5),
        new AutonomousShootConditional(shooter, index, 0).alongWith(new IndexShootingUpperConditional(index, 0))
      );
    }
}
