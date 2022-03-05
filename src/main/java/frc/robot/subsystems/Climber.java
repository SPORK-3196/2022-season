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

public class Climber extends SubsystemBase {

  public CANSparkMax L_Arm = new CANSparkMax(19, MotorType.kBrushless);
  public CANSparkMax R_Arm = new CANSparkMax(15, MotorType.kBrushless);  
  /** Creates a new Drivetrain. */
  
  public Climber() {
    L_Arm.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void ExtendLeft (double power) {
    L_Arm.set(power * -1);
  }

  public void RetractLeft (double power){
    L_Arm.set(power * 1);
  }

  public void StopLeft() {
    L_Arm.stopMotor();
  }

  
  public void ExtendRight (double power) {
    R_Arm.set(power * -1);
  }
  public void RetractRight (double power){
    R_Arm.set(power * 1);
  }
  
  public void StopRight() {
    R_Arm.stopMotor();
  }  

}
