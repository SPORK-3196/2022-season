// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Shooter;
import frc.robot.commands.Intake.IntakeCargos;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/** An example command that uses an example subsystem. */
public class FourCargoAuto extends SequentialCommandGroup {
    public FourCargoAuto(Drivetrain drivetrain, Shooter shooter, Intake intake, Index index, Climber climber) {
      super(
        new InstantCommand(index::startWithOneCargo, index),
        // Start index off with one Cargo
        
        new DelayedIndexConditional(index, 2)
          .alongWith(new DriveToPickup(drivetrain, shooter, climber, intake, 1.50, -0.6)),
        // Drive backwards and pickup Cargo behind you till 2 cargo in index

        // new DriveToPickup(drivetrain, shooter, climber, intake, 4.0, -0.4),
        
        new AutoHorizontalAim(drivetrain, 1),


        new AutonomousShootConditional(shooter, index, 0).alongWith(new IndexShootingUpperConditional(index, 0)),
        // Shoot into upper hub till empty

        new InstantCommand(climber::raiseArms, climber),

        new InstantCommand(index::startWithNoCargos, index),

        
        new DelayedIndexConditional(index, 1)
          .alongWith(
            new DriveToPickupAim(drivetrain, index, shooter, climber, intake, 3.5, -0.4).withInterrupt(index::getIntakeSensor)
              .andThen(new PickupCargo(intake, 3)).andThen(new TurnDegreesCCW(drivetrain, 1, 15).andThen(new DriveForwardTimed(drivetrain, 1, 0.6)).alongWith(new PickupCargos(intake, 3)))),

        new AutoHorizontalAim(drivetrain, 2),

        new InstantCommand(index::startWithOneCargo, index),

        new AutonomousShootConditional(shooter, index, 0, 2000).alongWith(new IndexShootingUpperConditional(index, 0))

      );
    }
}
