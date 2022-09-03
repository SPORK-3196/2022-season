// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/** A DriveToPickUpAim command that uses the shooter, drivetrain, intake, index, and climber subsystems. */
public class DriveToPickupAim extends ParallelCommandGroup {
    public DriveToPickupAim(Drivetrain drivetrain, Index index, Shooter shooter, Climber climber, Intake intake, double duration, double driveSpeed) {
      super(
        new AutoCargoOrientation(drivetrain, duration),
        new PickupCargo(intake, duration)
      );
    }
}
