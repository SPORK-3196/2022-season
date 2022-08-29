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
public class ThreeCargoAuto extends SequentialCommandGroup {
    public ThreeCargoAuto(Drivetrain drivetrain, Shooter shooter, Intake intake, Index index, Climber climber) {
      super(
        new InstantCommand(index::startWithOneCargo, index),
        // Start Index off with one Cargo
        
        new DelayedIndexConditional(index, 2)
          .alongWith(new DriveToPickup(drivetrain, shooter, climber, intake, 1.50, -0.3)),
        // Drive backwards and pickup cargo till you have enough 
        
        new AutoHorizontalAim(drivetrain, 1),
        // Auto aim towards the target for 1 second

        new InstantCommand(climber::raiseArms, climber),

        new AutonomousShootConditional(shooter, index, 0, 1850).alongWith(new IndexShootingUpperConditional(index, 0)),
        // Shoot into upper hub till empty

       
        // Raise climber arms

        new InstantCommand(index::startWithNoCargos, index),
        // Reset Cargo counter to 0

        new TurnDegreesCCW(drivetrain, 1, 15),

        // Works up till here 3:20PM, 4/22/2022
        
        new DelayedIndexConditional(index, 1)
          .alongWith(
            new DriveToPickupAim(drivetrain, index, shooter, climber, intake, 3, -0.15).withInterrupt(index::getIntakeSensor)),
        // Drive while using vision towards cargo, stopping if a Cargo is detected at the intake sensor
 
        new TurnDegreesCCW(drivetrain, 1, 15)
          .alongWith(new PickupCargo(intake, 1)),
        // Turn 15 degrees counter-clockwise for 1 second while running the intake
        
        new DriveToPickup(drivetrain, shooter, climber, intake, 1, 0.2),
        // Drive forward for 1 second while still running the intake, in case there's a Cargo there

        new AutoHorizontalAim(drivetrain, 2),
        // Aim at the target for 2 seconds

        new InstantCommand(index::startWithOneCargo, index),
        // Set index Cargo counter to one

        new InstantCommand(climber::raiseArms, climber),
        // Raise climber arms

        new AutonomousShootConditional(shooter, index, 0, 2000).alongWith(new IndexShootingUpperConditional(index, 0))
        // Shoot into index until empty

      );
    }
}
