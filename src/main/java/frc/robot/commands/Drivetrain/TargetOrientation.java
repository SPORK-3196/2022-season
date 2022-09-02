// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import static frc.robot.Constants.Drivetrain.AutoP;
import static frc.robot.GlobalVars.Drivetrain.DT_PowerConstant;
import static frc.robot.GlobalVars.Vision.DISTANCE_FROM_TARGET;
import static frc.robot.GlobalVars.Vision.RUN_VISION;
import static frc.robot.GlobalVars.Vision.primaryCamera;
import static frc.robot.GlobalVars.Vision.primaryHasTargets;
import static frc.robot.GlobalVars.Vision.primaryYaw;
import static frc.robot.GlobalVars.XboxController.X1_LJX;
import static frc.robot.GlobalVars.XboxController.X1_LJY;
import static frc.robot.subsystems.Drivetrain.Auto_PIDController;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;



/** A TargetOrientation command that uses a drivetrain subsystem. */
public class TargetOrientation extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;
  
  double speedControl;
  double rotationControl;

  /**
   * Creates a new TargetOrientation, that turns drivetrain to target vision targets.
   *
   * @param drivetrain The drivetrain used by this command.
   */
  public TargetOrientation(Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.driveModeSet = true;

    // Sets RUN_VISION to true, activating vision pipeline and turning on LEDs
    RUN_VISION = true;

    // Set PIDController for drivetrain to AutoP constant
    Auto_PIDController.setP(AutoP);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() { 

    Auto_PIDController.setP(AutoP);

    // Changes target setpoint based off robot distance from vision target
    if (DISTANCE_FROM_TARGET > 2.5) {
      Auto_PIDController.setSetpoint(drivetrain.getFarTargetOffset());
    }
    else {
      Auto_PIDController.setSetpoint(drivetrain.getCloseTargetOffset());
    }

    RUN_VISION = true;

    // Set driving values to joystick values multiplied by drivetrain power 
    speedControl = X1_LJY * -DT_PowerConstant; 
    rotationControl = X1_LJX * -DT_PowerConstant;

    // Set driver mode to false, activating vision processing
    primaryCamera.setDriverMode(false);
    
    // Change rotation control if vision target identified
    if (primaryHasTargets) {
      rotationControl = Auto_PIDController.calculate(primaryYaw);
    }

    // Slight incremental changes for aiming within certain values
    if (primaryYaw > 1.0) {
      rotationControl -= 0.02;
    }
    else if (primaryYaw < 1.0) {
      rotationControl += 0.02;
    }
    
    // Control drivetrain, sensitive to smaller values
    drivetrain.arcadeDriveAI(speedControl, rotationControl);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RUN_VISION = false;
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.driveModeSet = false;
    Auto_PIDController.setD(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
