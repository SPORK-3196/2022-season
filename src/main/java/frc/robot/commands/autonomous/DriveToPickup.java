// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import frc.robot.subsystems.Shooter;
import frc.robot.commands.Climber.LowerArms;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

/** A DriveToPiclup command that uses the shooter, drivetrain, intake, index, and climber subsystems. */
public class DriveToPickup extends ParallelCommandGroup {
    public DriveToPickup(Drivetrain drivetrain, Shooter shooter, Climber climber, Intake intake, double duration, double driveSpeed) {
      super(

        new DriveForwardTimed(drivetrain, duration, driveSpeed),
        new PickupCargo(intake, duration),
        new LowerArms(climber)
      );
    }
}
