// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.GlobalVars.Index.CargoCounter_Entry;
import static frc.robot.GlobalVars.Index.IndexRunning;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Index extends SubsystemBase {  // Made By Caputo & Oguntola
  
  // Initialize SparkMax Object
  public CANSparkMax indexMotor = new CANSparkMax(7, MotorType.kBrushless);

  // Initialize boolean variables for tracking Cargo movement through index
  public boolean CargoInTransit = false;
  public boolean CargoExiting = false;
  public boolean CargoPassed = false;

  // Initialize integer variable for counting cargo in index
  public double CargoCounter = 0;
  
  // Initialize DigitalInput objects for reading infrared sensor values
  private static DigitalInput sensorAlpha = new DigitalInput(0);
  private static DigitalInput sensorBeta = new DigitalInput(1);
  private static DigitalInput sensorSigma = new DigitalInput(2);

  // Intialize DigitalInput array for index based access of sensor values
  private static DigitalInput[] sensors = new DigitalInput[3];

  
  
  
  /** Creates a new Index subsystem. */
  public Index() {
    // Set each array position the value of intialized sensors
    sensors[0] = sensorAlpha;
    sensors[1] = sensorBeta;
    sensors[2] = sensorSigma;

    // Invert movement of index motor
    indexMotor.setInverted(true);

    // Reduce CANFrame frequency of the index
    indexMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 30);
    indexMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 30);
   
  }
  

  
  /** Gets boolean of object presence at each sensor based off index in array */
  public boolean getSensor(int sensorNumber) {
    return !sensors[sensorNumber].get();
  }

  /** Get value of object presence at intake sensor */
  public boolean getIntakeSensor() {
    return !sensorSigma.get();
  }

  /** Get value of object presence at middle sensor */
  public boolean getMidSensor() {
    return !sensorBeta.get();
  }

  /** Get value of object presence at top index sensor */
  public boolean getTopSensor() {
    return !sensorAlpha.get();
  } 
  


  /** Set index motor speed to move cargo in index */
  public void runIndex() {
    indexMotor.set(0.65);
  }

  /** Set index motor speed to eject cargo from index */
  public void removeCargo() {
    indexMotor.set(-0.6);
  }

  /** Set index motor speed to intake cargo into index */
  public void feedCargo() {
    indexMotor.set(0.4);
  }

  /** Stop index motor */
  public void stopIndex() {
    indexMotor.stopMotor();
  }

  /** Check if the index moor is moving at greater than 10% power */
  public boolean isRunning() {
    return (Math.abs(indexMotor.get()) > 0.1);
  }




  /** Set cargo counter to 2 (Meant for running autonomous commands at the beginning of a match) */
  public void startWithTwoCargo() {
    CargoCounter = 2;
  }

  /** Set cargo counter to 1 (Meant for running autonomous commands at the beginning of a match) */
  public void startWithOneCargo() {
    CargoCounter = 1;
  }

  /** Set cargo counter to 0 (Meant for running autonomous commands at the beginning of a match) */
  public void startWithNoCargo() {
    CargoCounter = 0;
  }



  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Set NetworkTableEntry to value of cargo counted
    CargoCounter_Entry.setDouble(CargoCounter);

    // Set IndexRunning boolean to true when the index is running, and false if otherwise
    if (Math.abs(indexMotor.get()) > 0.1) {
      IndexRunning = true;
    }
    else {
      IndexRunning = false;
    }

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }


}




