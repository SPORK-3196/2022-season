// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSimCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.sensors.BasePigeonSimCollection;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Drivetrain.*;
import static frc.robot.Constants.Robot.*;
import static frc.robot.GlobalVars.Drivetrain.*;
import static frc.robot.GlobalVars.Vision.*;
  
public class Drivetrain extends SubsystemBase {

  public WPI_TalonFX frontLeft = new WPI_TalonFX(1);
  public WPI_TalonFX frontRight = new WPI_TalonFX(2);
  public WPI_TalonFX rearLeft = new WPI_TalonFX(3);
  public WPI_TalonFX rearRight = new WPI_TalonFX(4);

  

  public PigeonIMU gyroscope = new PigeonIMU(12);

  public MotorControllerGroup leftSide = new MotorControllerGroup(frontLeft, rearLeft);
  public MotorControllerGroup rightSide = new MotorControllerGroup(frontRight, rearRight);

  
  
  public TalonFXSimCollection frontLeftSim = frontLeft.getSimCollection();
  public TalonFXSimCollection frontRightSim = frontRight.getSimCollection();
  public TalonFXSimCollection rearLeftSim = rearLeft.getSimCollection();
  public TalonFXSimCollection rearRightSim = rearRight.getSimCollection();
  public BasePigeonSimCollection gyroscopeSim = gyroscope.getSimCollection();

  public DifferentialDrive drivetrain = new DifferentialDrive(leftSide, rightSide);

  public DifferentialDrivetrainSim drivetrainSim = new DifferentialDrivetrainSim(
    DCMotor.getFalcon500(2), 
    gearRatio, 
    10,
    Units.lbsToKilograms(200), 
    Units.inchesToMeters(DrivetrainWheelDiameterIn), 
    DrivetrainTrackWidthMeters, 
    VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005));

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
  public static Field2d gameField;
  private Pose2d robot_pose = new Pose2d(7, 2.7, new Rotation2d(Units.degreesToRadians(26)));
  private DifferentialDriveOdometry drivetrain_odometry = new DifferentialDriveOdometry(new Rotation2d(getGyroHeadingRads()), robot_pose);
  

  public Orchestra drivetrainOrchestra = new Orchestra();
  
  public static PIDController Auto_PIDController = new PIDController(0.0065, 0, 0);
  

  /** Creates a new Drivetrain. */
  public Drivetrain() {

    leftSide.setInverted(true);
    
    
    gameField = new Field2d();
  
    frontLeft.setSelectedSensorPosition(0);
    rearLeft.setSelectedSensorPosition(0);
    frontRight.setSelectedSensorPosition(0);
    rearRight.setSelectedSensorPosition(0);
    gyroscope.setYaw(0);

    Auto_PIDController.setSetpoint(0);
    Auto_PIDController.setTolerance(0);

    // Music Stuff
    drivetrainOrchestra.addInstrument(frontLeft);
    drivetrainOrchestra.addInstrument(frontRight);
    drivetrainOrchestra.addInstrument(rearLeft);
    drivetrainOrchestra.addInstrument(rearRight);

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

    loadMusic(songChooser.getSelected());


    Shuffleboard.getTab("Drivetrain Info")
      .add(gameField);

    frontLeft.setNeutralMode(NeutralMode.Coast);
    frontRight.setNeutralMode(NeutralMode.Coast);
    rearLeft.setNeutralMode(NeutralMode.Coast);
    rearRight.setNeutralMode(NeutralMode.Coast);
  }

  public void loadMusic(String song) {
    drivetrainOrchestra.loadMusic(song);
  }

  public void curvatureDriveIK(double speed, double rotation) {
    DifferentialDrive.WheelSpeeds wheelSpeeds = DifferentialDrive.curvatureDriveIK(speed, rotation, true);
    leftSide.set(wheelSpeeds.left);
    rightSide.set(wheelSpeeds.right);
  }

  public void curvatureDrive(double speed, double rotation) {
    drivetrain.curvatureDrive(speed, rotation, true);
  }

  public void arcadeDriveIK(double speed, double rotation) {
    DifferentialDrive.WheelSpeeds wheelSpeeds = DifferentialDrive.arcadeDriveIK(speed, rotation, true);
    leftSide.set(wheelSpeeds.left);
    rightSide.set(wheelSpeeds.right);
  }

  public void arcadeDrive(double speed, double rotation) {
    drivetrain.arcadeDrive(speed, rotation, true);
  }

  public void arcadeDriveAI(double speed, double rotation) {
    drivetrain.arcadeDrive(speed, rotation, false);
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


  public void zeroGyro() {
    gyroscope.setYaw(0);
  }

  public double getYaw() {
    return gyroscope.getYaw();
  }

  public double getGyroHeadingDeg(){
    return getYaw();
  }

  public double getGyroHeadingRads(){
    return Units.degreesToRadians(getGyroHeadingDeg());
  }


  public void resetEncoders() {
    frontLeft.setSelectedSensorPosition(0);
    frontLeftSim.setIntegratedSensorRawPosition(0);
    frontRight.setSelectedSensorPosition(0);
    frontRightSim.setIntegratedSensorRawPosition(0);
    rearLeft.setSelectedSensorPosition(0);
    rearLeftSim.setIntegratedSensorRawPosition(0);
    rearRight.setSelectedSensorPosition(0);
    rearRightSim.setIntegratedSensorRawPosition(0);
  }


  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    drivetrain_odometry.resetPosition(pose, new Rotation2d(getGyroHeadingRads()));
  }

  public Pose2d getPose() {
    return robot_pose;
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftSide.setVoltage(leftVolts);
    rightSide.setVoltage(rightVolts);
  }

  public double sensorUnitsToMeters(double sensor_counts) {
    double motorRotations = (double)sensor_counts / countsPerRevolution;
    double wheelRotations = motorRotations / gearRatio;
    double positionMeters = wheelRotations * (2 * Math.PI * Units.inchesToMeters(DrivetrainWheelRadiusIn));
    return positionMeters;
  }
  
  public int metersToSensorUnits(double meters) {
    double wheelRotations = meters / (2 * Math.PI * Units.inchesToMeters(DrivetrainWheelRadiusIn));
    double motorRotations = wheelRotations * gearRatio;
    int sensor_counts = (int) (motorRotations * countsPerRevolution);
    return sensor_counts;
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(sensorUnitsToMeters(-1 * rearLeft.getSelectedSensorVelocity()), sensorUnitsToMeters(rearRight.getSelectedSensorVelocity()));
  }

  public double getCloseTargetOffset() {
    // return Math.atan( CAMERA_TARGET_OFFSET_CLOSE_M / DISTANCE_FROM_TARGET );
    return -5;
  }

  public double getFarTargetOffset() {
    // return Math.atan( CAMERA_TARGET_OFFSET_FAR_M / DISTANCE_FROM_TARGET );
    return -3;
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

    DT_FrontLeftEntry.setDouble(frontLeft.getMotorOutputPercent());
    DT_rearLeftEntry.setDouble(-rearLeft.getMotorOutputPercent());
    DT_FrontRightEntry.setDouble(frontRight.getMotorOutputPercent());
    DT_rearRightEntry.setDouble(-rearRight.getMotorOutputPercent());

    if (DISTANCE_FROM_TARGET > 2.5) {
      DT_TargetOffsetAngle.setDouble(getFarTargetOffset());
    }
    else {
      DT_TargetOffsetAngle.setDouble(getCloseTargetOffset());
    }

    // System.out.println(sensorUnitsToMeters(rearLeft.getSelectedSensorVelocity()));

    // drivetrain_poseEstimator.update(gyroscope.getYaw(), wheelVelocitiesMetersPerSecond, distanceLeftMeters, distanceRightMeters)
    gameField.setRobotPose(robot_pose);

    robot_pose = drivetrain_odometry.update(new Rotation2d(getGyroHeadingRads()), sensorUnitsToMeters(-1 * rearLeft.getSelectedSensorPosition()), sensorUnitsToMeters(rearRight.getSelectedSensorPosition()));
  } 

  
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    drivetrainSim.setInputs(leftSide.get() * RobotController.getInputVoltage(), rightSide.get() * RobotController.getInputVoltage());
    drivetrainSim.update(0.02);

    rearLeftSim.setIntegratedSensorRawPosition(metersToSensorUnits(-drivetrainSim.getLeftPositionMeters()));
    rearRightSim.setIntegratedSensorRawPosition(metersToSensorUnits(drivetrainSim.getRightPositionMeters()));

    
    rearLeftSim.setIntegratedSensorVelocity(metersToSensorUnits(drivetrainSim.getLeftVelocityMetersPerSecond()));
    rearRightSim.setIntegratedSensorVelocity(metersToSensorUnits(drivetrainSim.getRightVelocityMetersPerSecond()));
    gyroscopeSim.setRawHeading(-drivetrainSim.getHeading().getDegrees());
  }
  
}
