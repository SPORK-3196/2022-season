// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drivetrain;

import static frc.robot.GlobalVars.Drivetrain.DT_PowerConstant;
import static frc.robot.GlobalVars.Vision.RUN_BACKUP_VISION;
import static frc.robot.GlobalVars.Vision.backupCamera;
import static frc.robot.GlobalVars.Vision.backupHasTargets;
import static frc.robot.GlobalVars.Vision.backupYaw;
import static frc.robot.GlobalVars.XboxController.X1_LJX;
import static frc.robot.GlobalVars.XboxController.X1_LJY;
import static frc.robot.subsystems.Drivetrain.Auto_PIDController;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;



/** An example command that uses an example subsystem. */
public class CargoOrientation extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drivetrain drivetrain;

  double speedControl;
  double rotationControl;

  /**
   * Creates a new CargoOrientation. Tele-Operated command for automatic drivetrain orientation towards allied cargo targets.
   *
   * @param drivetrain The drivetrain subsystem used by this command.
   */
  public CargoOrientation(Drivetrain subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    drivetrain = subsystem;
    
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Auto_PIDController.setSetpoint(0);
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.driveModeSet = true;
    RUN_BACKUP_VISION = true;

    // Set PIDController to P values for following Cargo
    Auto_PIDController.setP(0.0075);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    RUN_BACKUP_VISION = true;

    speedControl = X1_LJY * -DT_PowerConstant; 
    rotationControl = X1_LJX * -DT_PowerConstant;
    
    backupCamera.setDriverMode(false);
    
    if (backupHasTargets) {
      rotationControl = Auto_PIDController.calculate(backupYaw);
    }

    drivetrain.arcadeDriveAI(speedControl, rotationControl);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // drivetrain.drivetrain = null;
    RUN_BACKUP_VISION = false;
    drivetrain.frontLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearLeft.setNeutralMode(NeutralMode.Coast);
    drivetrain.frontRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.rearRight.setNeutralMode(NeutralMode.Coast);
    drivetrain.driveModeSet = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
