// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Drivetrain.*;
import static frc.robot.Constants.Robot.*;
  
public class Drivetrain extends SubsystemBase {

  public WPI_TalonFX frontLeft = new WPI_TalonFX(1);
  public WPI_TalonFX frontRight = new WPI_TalonFX(2);
  public WPI_TalonFX rearLeft = new WPI_TalonFX(3);
  public WPI_TalonFX rearRight = new WPI_TalonFX(4);

  public PigeonIMU gyroscope = new PigeonIMU(12);

  public MotorControllerGroup leftSide = new MotorControllerGroup(frontLeft, rearLeft);
  public MotorControllerGroup rightSide = new MotorControllerGroup(frontRight, rearRight);

  public DifferentialDrive drivetrain;

  private boolean playingMusic = false;

  public String currentSong = "placeholder current";
  public String previousSong = "placeholder previous";

  public boolean driveModeSet = false;

  // private final DifferentialDriveKinematics drivetrain_kinematics = new DifferentialDriveKinematics(DrivetrainTrackWidthMeters);

  /* private final DifferentialDrivePoseEstimator drivetrain_poseEstimator = new DifferentialDrivePoseEstimator(
    gyroscope.getYaw(), 
    new Pose2d(), 
    stateStdDevs, 
    localMeasurementStdDevs, 
    visionMeasurementStdDevs);
  */
  
  private DifferentialDriveOdometry drivetrain_odometry;
  // private DifferentialDriveOdometry drivetrain_odometry;
  private Pose2d robot_pose;

  public Orchestra drivetrainOrchestra = new Orchestra();
  
  public static PIDController Auto_PIDController = new PIDController(0.0065, 0, 0);
  

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    leftSide.setInverted(true);



    drivetrainOrchestra.addInstrument(frontLeft);
    drivetrainOrchestra.addInstrument(frontRight);
    drivetrainOrchestra.addInstrument(rearLeft);
    drivetrainOrchestra.addInstrument(rearRight);

    // gyroscope.setYaw(0);

    Auto_PIDController.setSetpoint(0);
    Auto_PIDController.setTolerance(0);

    drivetrain_odometry = new DifferentialDriveOdometry(new Rotation2d(Units.degreesToRadians(gyroscope.getYaw())));
    songChooser.addOption("Thomas The Tank Engine", "Thomas The Tank Engine.chrp");
    songChooser.addOption("All Star", "All Star.chrp");
    songChooser.addOption("Crab Rave", "Crab Rave.chrp");
    songChooser.addOption("Friends On The Other Side", "Friends On The Other Side.chrp");
    songChooser.addOption("Party In The USA", "Party In The USA.chrp");
    songChooser.addOption("A Thousand Miles", "A Thousand Miles.chrp");
    songChooser.addOption("Sir Duke", "Sir Duke.chrp");
    songChooser.setDefaultOption("Ain't No Mountain High Enough", "Ain't No Mountain High Enough.chrp");
    songChooser.addOption("Despacito", "Despacito.chrp");
    songChooser.addOption("Gravity Falls", "Gravity Falls.chrp");
    songChooser.addOption("Never Gonna Give You Up", "Never Gonna Give You Up.chrp");

    drivetrainOrchestra.loadMusic(songChooser.getSelected());

    robot_pose = new Pose2d(30, 20, new Rotation2d(getGyroHeading()));
    drivetrain_odometry = new DifferentialDriveOdometry(new Rotation2d(Units.degreesToRadians(gyroscope.getYaw())), robot_pose);
  }

  public void loadMusic(String song) {
    drivetrainOrchestra.loadMusic(song);
  }

  public void playMusic() {
    drivetrainOrchestra.play();
    playingMusic = true;
  }

  public void stopMusic() {
    drivetrainOrchestra.stop();
    playingMusic = false;
  }

  public void pauseMusic() {
    drivetrainOrchestra.pause();
    playingMusic = false;
  }

  public boolean isPlaying() {
    return playingMusic;
  }



  public double getYaw() {
    return gyroscope.getYaw();
  }

  public double getGyroHeading(){
    return -gyroscope.getYaw();
  }

  public double getDistanceRawSensor() {
    return rearLeft.getSelectedSensorPosition();
  }

  public double getLeftDistanceTravelled() {
    return (rearLeft.getSelectedSensorPosition() / 18000) * (2 * Math.PI * DrivetrainWheelRadiusIn);
  }

  public double getRightDistanceTravelled() {
    return (rearRight.getSelectedSensorPosition() / 18000) * (2 * Math.PI * DrivetrainWheelRadiusIn);
  }

  public double sensorUnitsToMeters(double sensor_counts) {
    double motorRotations = (double)sensor_counts / countsPerRevolution;
    double wheelRotations = motorRotations / gearRatio;
    double positionMeters = wheelRotations * (2 * Math.PI * Units.inchesToMeters(DrivetrainWheelRadiusIn));
    return positionMeters;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (playingMusic) {
      DT_MusicPlaying.setBoolean(true);
      leftSide.setInverted(true);
      frontLeft.setNeutralMode(NeutralMode.Coast);
      rearLeft.setNeutralMode(NeutralMode.Coast);
      frontRight.setNeutralMode(NeutralMode.Coast);
      rearRight.setNeutralMode(NeutralMode.Coast);
      return ;
    }
    else {
      DT_MusicPlaying.setBoolean(false);
    }

    DT_FrontLeftEntry.setNumber(frontLeft.getMotorOutputPercent());
    DT_rearLeftEntry.setNumber(rearLeft.getMotorOutputPercent());
    DT_FrontRightEntry.setNumber(frontRight.getMotorOutputPercent());
    DT_rearRightEntry.setNumber(rearRight.getMotorOutputPercent());
    // System.out.println(rearLeft.getSelectedSensorPosition());

    // drivetrain_poseEstimator.update(gyroscope.getYaw(), wheelVelocitiesMetersPerSecond, distanceLeftMeters, distanceRightMeters)
    robot_pose = drivetrain_odometry.update(new Rotation2d(getGyroHeading()), sensorUnitsToMeters(rearLeft.getSelectedSensorPosition()), sensorUnitsToMeters(rearRight.getSelectedSensorPosition()));
    gameField.setRobotPose(robot_pose);
  } 

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
