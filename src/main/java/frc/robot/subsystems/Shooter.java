// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Shooter extends SubsystemBase {

  public CANSparkMax leftShooter = new CANSparkMax(12, MotorType.kBrushless); 
  public CANSparkMax rightShooter = new CANSparkMax(14, MotorType.kBrushless);
  

  MotorControllerGroup shooterMotors = new MotorControllerGroup(leftShooter, rightShooter);
  
  
  /** Creates a new Drivetrain. */
  public Shooter() {
    rightShooter.setInverted(true);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    MT_ShooterPowerEntry.setNumber(MT_ShooterPower * 100.0);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
