// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Robot.DrivetrainTrackWidthMeters;
import static frc.robot.Constants.Robot.DrivetrainWheelDiameterIn;
import static frc.robot.Constants.Robot.DrivetrainWheelRadiusIn;
import static frc.robot.Constants.Robot.countsPerRevolution;
import static frc.robot.Constants.Robot.gearRatio;
import static frc.robot.GlobalVars.Drivetrain.DT_FrontLeftEntry;
import static frc.robot.GlobalVars.Drivetrain.DT_FrontRightEntry;
import static frc.robot.GlobalVars.Drivetrain.DT_MusicPlaying;
import static frc.robot.GlobalVars.Drivetrain.DT_TargetOffsetAngle;
import static frc.robot.GlobalVars.Drivetrain.DT_rearLeftEntry;
import static frc.robot.GlobalVars.Drivetrain.DT_rearRightEntry;
import static frc.robot.GlobalVars.Drivetrain.songChooser;
import static frc.robot.GlobalVars.Vision.DISTANCE_FROM_TARGET;

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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
  
public class Drivetrain extends SubsystemBase {

  // Initialize TalonFX motor controller objects based off CAN IDs
  public WPI_TalonFX frontLeft = new WPI_TalonFX(1);
  public WPI_TalonFX frontRight = new WPI_TalonFX(2);
  public WPI_TalonFX rearLeft = new WPI_TalonFX(3);
  public WPI_TalonFX rearRight = new WPI_TalonFX(4);

  // Intialize gyroscope object
  public PigeonIMU gyroscope = new PigeonIMU(12);
  
  // Intialize gyroscope object
  public MotorControllerGroup leftSide = new MotorControllerGroup(frontLeft, rearLeft);
  public MotorControllerGroup rightSide = new MotorControllerGroup(frontRight, rearRight);

  // Intialize simulator objects 
  public TalonFXSimCollection frontLeftSim = frontLeft.getSimCollection();
  public TalonFXSimCollection frontRightSim = frontRight.getSimCollection();
  public TalonFXSimCollection rearLeftSim = rearLeft.getSimCollection();
  public TalonFXSimCollection rearRightSim = rearRight.getSimCollection();
  public BasePigeonSimCollection gyroscopeSim = gyroscope.getSimCollection();

  // Intialize DifferentialDrive with MotorController objects
  public DifferentialDrive drivetrain = new DifferentialDrive(leftSide, rightSide);
  
  // Intialize DifferentialDrive with robot physical constants
  public DifferentialDrivetrainSim drivetrainSim = new DifferentialDrivetrainSim(
    DCMotor.getFalcon500(2), 
    gearRatio, 
    10,
    Units.lbsToKilograms(200), 
    Units.inchesToMeters(DrivetrainWheelDiameterIn), 
    DrivetrainTrackWidthMeters, 
    VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005));

  // Boolean for checking if robot is playing music
  private boolean playingMusic = false;

  // Variables for song queue system
  public String currentSong = "placeholder current";
  public String previousSong = "placeholder previous";

  // Chek if TeleOp driving mode set
  public boolean driveModeSet = false;

  // DIfferential Drive Estimation Objects
  
  // private final DifferentialDriveKinematics drivetrain_kinematics = new DifferentialDriveKinematics(DrivetrainTrackWidthMeters);

  /* private final DifferentialDrivePoseEstimator drivetrain_poseEstimator = new DifferentialDrivePoseEstimator(
    gyroscope.getYaw(), 
    new Pose2d(), 
    stateStdDevs, 
    localMeasurementStdDevs, 
    visionMeasurementStdDevs);
  */

  // Initialize Field2d object
  public static Field2d gameField;

  // Initalize robot initial pose 
  private Pose2d robot_pose = new Pose2d(7, 2.7, new Rotation2d(Units.degreesToRadians(26)));

  // Create DifferentialDriveOdometry object to track robot movement on the field
  private DifferentialDriveOdometry drivetrain_odometry = new DifferentialDriveOdometry(new Rotation2d(getGyroHeadingRads()), robot_pose);
  
  // Create an Orchestra object for playing music with TalonFX (Falcon 500s)
  public Orchestra drivetrainOrchestra = new Orchestra();
  
  // Create PIDController fror automatic aiming at vision targets using Drivetrain
  public static PIDController Auto_PIDController = new PIDController(0.0065, 0, 0);
  

  /** Creates a new Drivetrain. */
  public Drivetrain() {

    // Invert movement of the left side of the drivetrain (Left side due to the nature of motor orientation)
    leftSide.setInverted(true);
    
    // Construct new Field2d object
    gameField = new Field2d();
    
    // Reset sensor values of drivetrain motors & gyroscope for odometry
    frontLeft.setSelectedSensorPosition(0);
    rearLeft.setSelectedSensorPosition(0);
    frontRight.setSelectedSensorPosition(0);
    rearRight.setSelectedSensorPosition(0);
    gyroscope.setYaw(0);

    // Set target & tolerance within target of drivetrain PID Controller aim to 0, 
    Auto_PIDController.setSetpoint(0);
    Auto_PIDController.setTolerance(0);

    // Add TalonFXs to music orchestra
    drivetrainOrchestra.addInstrument(frontLeft);
    drivetrainOrchestra.addInstrument(frontRight);
    drivetrainOrchestra.addInstrument(rearLeft);
    drivetrainOrchestra.addInstrument(rearRight);


    // Add included .chrp files for playing music with orchestra to Shuffleboard Chooser
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

    // Load selected music from autonomous chooser
    loadMusic(songChooser.getSelected());

    // Add Field2D object to shuffleboard tab "Drivetrain Info"
    Shuffleboard.getTab("Drivetrain Info")
      .add(gameField);

    // Set TalonFXs to coast, to reduce grinding from aluminum gears
    frontLeft.setNeutralMode(NeutralMode.Coast);
    frontRight.setNeutralMode(NeutralMode.Coast);
    rearLeft.setNeutralMode(NeutralMode.Coast);
    rearRight.setNeutralMode(NeutralMode.Coast);
  }

  /** Loads song based off filename */
  public void loadMusic(String song) {
    drivetrainOrchestra.loadMusic(song);
  }

  /** Inverse kinematics curvature drive method (Just like regular curvatureDrive(), but doesn't cause music issues) */
  public void curvatureDriveIK(double speed, double rotation) {
    DifferentialDrive.WheelSpeeds wheelSpeeds = DifferentialDrive.curvatureDriveIK(speed, rotation, true);
    leftSide.set(wheelSpeeds.left);
    rightSide.set(wheelSpeeds.right);
  }

  /** Curvature drive method, AKA Cheezy Drive (Causes music issues if DIfferentialDrive object is defined)  */
  public void curvatureDrive(double speed, double rotation) {
    drivetrain.curvatureDrive(speed, rotation, true);
  }

  /** Inverse kinematics arcade drive method (Just like regular arcadeDrive(), but doesn't cause music issues) */
  public void arcadeDriveIK(double speed, double rotation) {
    DifferentialDrive.WheelSpeeds wheelSpeeds = DifferentialDrive.arcadeDriveIK(speed, rotation, true);
    leftSide.set(wheelSpeeds.left);
    rightSide.set(wheelSpeeds.right);
  }

  /** Arcade drive method (Causes music issues if DIfferentialDrive object is defined)  */
  public void arcadeDrive(double speed, double rotation) {
    drivetrain.arcadeDrive(speed, rotation, true);
  }

  /** Arcade drive method that doesn't square inputs to allow for smaller inputs */
  public void arcadeDriveAI(double speed, double rotation) {
    drivetrain.arcadeDrive(speed, rotation, false);
  }

  /** Tank drive method for driving based off voltage */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftSide.setVoltage(leftVolts);
    rightSide.setVoltage(rightVolts);
  }

  /** Starts playing loaded music */
  public void playMusic() {
    drivetrainOrchestra.play();
    playingMusic = true;
  }

  /** Stops playing loaded music */
  public void stopMusic() {
    drivetrainOrchestra.stop();
    playingMusic = false;
  }
  
  /** Pauses playing loaded music */
  public void pauseMusic() {
    drivetrainOrchestra.pause();
    playingMusic = false;
  }

  /** Returns whether or not robot is playing music*/
  public boolean isPlaying() {
    return playingMusic;
  }

  /** Resets gyrscope heading to 0 */
  public void zeroGyro() {
    gyroscope.setYaw(0);
  }

  /** Retrieve yaw/heading from Gyroscope */
  public double getYaw() {
    return gyroscope.getYaw();
  }

  /** Retrieve yaw/heading in degrees from Gyroscope */
  public double getGyroHeadingDeg(){
    return getYaw();
  }

  /** Retrieve yaw/heading in radians from Gyroscope */
  public double getGyroHeadingRads(){
    return Units.degreesToRadians(getGyroHeadingDeg());
  }

  /** Resets value of encoders of drivetrain (Also works in Sim) */
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

  /** Resets drivetrain odometry to new Pose2d Object */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    drivetrain_odometry.resetPosition(pose, new Rotation2d(getGyroHeadingRads()));
  }

  /** Retrieve current Pose2d object of robot */
  public Pose2d getPose() {
    return robot_pose;
  }
  
  
  /** Converts drivetrain motor sensor units to meters */
  public double sensorUnitsToMeters(double sensor_counts) {
    double motorRotations = (double)sensor_counts / countsPerRevolution;
    double wheelRotations = motorRotations / gearRatio;
    double positionMeters = wheelRotations * (2 * Math.PI * Units.inchesToMeters(DrivetrainWheelRadiusIn));
    return positionMeters;
  }
  
  /** Converts units to drivetrain motor sensor unit */
  public int metersToSensorUnits(double meters) {
    double wheelRotations = meters / (2 * Math.PI * Units.inchesToMeters(DrivetrainWheelRadiusIn));
    double motorRotations = wheelRotations * gearRatio;
    int sensor_counts = (int) (motorRotations * countsPerRevolution);
    return sensor_counts;
  }

  /** Retrieve DiiferentialDriveWheelSpeeds object based off robot velocity */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(sensorUnitsToMeters(-1 * rearLeft.getSelectedSensorVelocity()), sensorUnitsToMeters(rearRight.getSelectedSensorVelocity()));
  }
  
  /** Returns target offset when closer to upper hub (Inspired by YETI Robotics) */
  public double getCloseTargetOffset() {
    // return Math.atan( CAMERA_TARGET_OFFSET_CLOSE_M / DISTANCE_FROM_TARGET );
    return -5;
  }
  /** Returns target offset when further from upper hub (Inspired by YETI Robotics) */
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

    // Set NetworkTableEntry values to motor output percentages
    DT_FrontLeftEntry.setDouble(frontLeft.getMotorOutputPercent());
    DT_rearLeftEntry.setDouble(-rearLeft.getMotorOutputPercent());
    DT_FrontRightEntry.setDouble(frontRight.getMotorOutputPercent());
    DT_rearRightEntry.setDouble(-rearRight.getMotorOutputPercent());

    // Set target offset angle based on distance being greater than or less than 2.5 meters
    if (DISTANCE_FROM_TARGET > 2.5) {
      DT_TargetOffsetAngle.setDouble(getFarTargetOffset());
    }
    else {
      DT_TargetOffsetAngle.setDouble(getCloseTargetOffset());
    }

    // System.out.println(sensorUnitsToMeters(rearLeft.getSelectedSensorVelocity()));

    // drivetrain_poseEstimator.update(gyroscope.getYaw(), wheelVelocitiesMetersPerSecond, distanceLeftMeters, distanceRightMeters)
    
    // Set robot pose on game field
    gameField.setRobotPose(robot_pose);

    // Update robot pose based on  drivetrain odometry
    robot_pose = drivetrain_odometry.update(new Rotation2d(getGyroHeadingRads()), sensorUnitsToMeters(-1 * rearLeft.getSelectedSensorPosition()), sensorUnitsToMeters(rearRight.getSelectedSensorPosition()));
  } 

  
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation

    // Update simluation values based simulated motor inputs & outputs
    drivetrainSim.setInputs(leftSide.get() * RobotController.getInputVoltage(), rightSide.get() * RobotController.getInputVoltage());
    drivetrainSim.update(0.02);

    rearLeftSim.setIntegratedSensorRawPosition(metersToSensorUnits(-drivetrainSim.getLeftPositionMeters()));
    rearRightSim.setIntegratedSensorRawPosition(metersToSensorUnits(drivetrainSim.getRightPositionMeters()));

    
    rearLeftSim.setIntegratedSensorVelocity(metersToSensorUnits(drivetrainSim.getLeftVelocityMetersPerSecond()));
    rearRightSim.setIntegratedSensorVelocity(metersToSensorUnits(drivetrainSim.getRightVelocityMetersPerSecond()));
    gyroscopeSim.setRawHeading(-drivetrainSim.getHeading().getDegrees());
  }
  
}
