// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Climber extends SubsystemBase { // Made By Caputo

  // Initialize SparkMAX objects
  public CANSparkMax L_Arm = new CANSparkMax(9, MotorType.kBrushless);
  public CANSparkMax R_Arm = new CANSparkMax(10, MotorType.kBrushless);   

  // Initialize Solenoid object
  public Solenoid ARM_PISTONS = new Solenoid(11, PneumaticsModuleType.REVPH, 15); 
  
  /** Creates a new Climber subsystem. */
  public Climber() {
    // Invert movement of right climber motor
    R_Arm.setInverted(true);

    // Reduce frequency of motor CAN frames
    L_Arm.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 30);
    L_Arm.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 30);
    R_Arm.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 30);
    R_Arm.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 30);
  } 


  // Methods for movement of left clibmer motor

  /** Set left motor to extend */
  public void ExtendLeft (double power) {
    L_Arm.set(power * 1);
  }
  /** Set left motor to retract */
  public void RetractLeft (double power){
    L_Arm.set(power * -1);
  }
  /** Stop left motor */
  public void StopLeft() {
    L_Arm.stopMotor();
  }


  // Methods for movement of right clibmer motor

  /** Set right motor to extend */
  public void ExtendRight (double power) {
    R_Arm.set(power * 1);
  }

  /** Set right motor to retract */
  public void RetractRight (double power){
    R_Arm.set(power * -1);
  }
  
  /** Stop right motor */
  public void StopRight() {
    R_Arm.stopMotor();
  }  



  /** Toggles the state of the solenoid piston */
  public void toggleArms() {
    ARM_PISTONS.toggle();
  }

  /**  Sets state of solenoid piston to raise climber arms upright */
  public void raiseArms() {
    ARM_PISTONS.set(false);
  }

  /**  Sets state of solenoid piston to lower climber arms down */
  public void lowerArms() {
    ARM_PISTONS.set(true);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run



  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation



  }

}
