// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Shooter extends SubsystemBase {

  public CANSparkMax L_SHOOTER = new CANSparkMax(12, MotorType.kBrushless); 
  public CANSparkMax R_SHOOTER = new CANSparkMax(14, MotorType.kBrushless);
  public MotorControllerGroup SHOOTER_MOTORS = new MotorControllerGroup(L_SHOOTER, R_SHOOTER);

  public RelativeEncoder LSPARK_Encoder = L_SHOOTER.getEncoder();
  public RelativeEncoder RSPARK_Encoder = R_SHOOTER.getEncoder();
  public PIDController shooterController = new PIDController(5e-5, 1e-6, 0);

  public double shooterVelocity;
  
  
  /** Creates a new Drivetrain. */
  public Shooter() {
    R_SHOOTER.setInverted(true);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    shooterVelocity = (LSPARK_Encoder.getVelocity() + RSPARK_Encoder.getVelocity()) / 2;

    SHOOTER_RPM_Entry.setDouble(shooterVelocity);
    SHOOTER_MPH_Entry.setDouble(((SparkWheelDiameterInches * Math.PI * shooterVelocity) * 60) / 5280);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void run (double power) {
    SHOOTER_MOTORS.set(power * -1);
  }

  public void stop() {
    SHOOTER_MOTORS.stopMotor();
  }

}
