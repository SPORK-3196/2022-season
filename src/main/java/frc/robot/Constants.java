// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // Retrieve shuffleboard tabs
    public static ShuffleboardTab X1_TAB =  Shuffleboard.getTab("Xbox Controller #1");
    public static ShuffleboardTab X2_TAB =  Shuffleboard.getTab("Xbox Controller #2");
    public static ShuffleboardTab SENSOR_TAB =  Shuffleboard.getTab("Sensor Diagnostics");
    public static ShuffleboardTab MOTOR_TAB =  Shuffleboard.getTab("Motor Diagnostics");
    public static ShuffleboardTab AUTO_TAB =  Shuffleboard.getTab("Autonomous Controls");
    public static ShuffleboardTab DT_TAB = Shuffleboard.getTab("Drivetrain Info");
    public static ShuffleboardTab AI_TAB = Shuffleboard.getTab("Autonomous Info");
    public static ShuffleboardTab SH_TAB = Shuffleboard.getTab("Shooter Info");

    //Create variables and NetworkTableEntries to store input from xbox controller (0)
    
    public static final class XboxController {

        public static double X1_RTValue = 0;
        public static double X1_LTValue = 0;

        public static boolean X1_RB = false;
        public static boolean X1_LB = false;

        public static double X1_LJX = 0;
        public static double X1_LJY = 0;
        public static double X1_RJX = 0;
        public static double X1_RJY = 0;

        public static boolean X1_XButton = false;
        public static boolean X1_YButton = false;
        public static boolean X1_AButton = false;
        public static boolean X1_BButton = false;

        public static double X2_RTValue = 0;
        public static double X2_LTValue = 0;

        public static boolean X2_RB = false;
        public static boolean X2_LB = false;

        public static double X2_LJX = 0;
        public static double X2_LJY = 0;
        public static double X2_RJX = 0;
        public static double X2_RJY = 0;

        public static boolean X2_LJS = false;

        public static boolean X2_XButton = false;
        public static boolean X2_YButton = false;
        public static boolean X2_AButton = false;
        public static boolean X2_BButton = false;

        public static NetworkTableEntry X1_RT_Entry = X1_TAB.add("Right Trigger", 0.0).getEntry();
        public static NetworkTableEntry X1_LT_Entry = X1_TAB.add("Left Trigger", 0.0).getEntry();

        public static NetworkTableEntry X1_RB_Entry = X1_TAB.add("Right Bumper", false).getEntry();
        public static NetworkTableEntry X1_LB_Entry = X1_TAB.add("Left Bumper", false).getEntry();

        public static NetworkTableEntry X1_LJX_Entry = X1_TAB.add("Left Joystick X", 0.0).getEntry();
        public static NetworkTableEntry X1_LJY_Entry = X1_TAB.add("Left Joystick Y", 0.0).getEntry();
        public static NetworkTableEntry X1_RJX_Entry = X1_TAB.add("Right Joystick X", 0.0).getEntry();
        public static NetworkTableEntry X1_RJY_Entry = X1_TAB.add("Right Joystick Y", 0.0).getEntry();

        public static NetworkTableEntry X1_XButtonEntry = X1_TAB.add("X Button", false).getEntry();
        public static NetworkTableEntry X1_YButtonEntry = X1_TAB.add("Y Button", false).getEntry();
        public static NetworkTableEntry X1_AButtonEntry = X1_TAB.add("A Button", false).getEntry();
        public static NetworkTableEntry X1_BButtonEntry = X1_TAB.add("B Button", false).getEntry();

        
        public static NetworkTableEntry X2_RT_Entry = X2_TAB.add("Right Trigger", 0.0).getEntry();
        public static NetworkTableEntry X2_LT_Entry = X2_TAB.add("Left Trigger", 0.0).getEntry();

        public static NetworkTableEntry X2_RB_Entry = X2_TAB.add("Right Bumper", false).getEntry();
        public static NetworkTableEntry X2_LB_Entry = X2_TAB.add("Left Bumper", false).getEntry();

        public static NetworkTableEntry X2_LJX_Entry = X2_TAB.add("Left Joystick X", 0.0).getEntry();
        public static NetworkTableEntry X2_LJY_Entry = X2_TAB.add("Left Joystick Y", 0.0).getEntry();
        public static NetworkTableEntry X2_RJX_Entry = X2_TAB.add("Right Joystick X", 0.0).getEntry();
        public static NetworkTableEntry X2_RJY_Entry = X2_TAB.add("Right Joystick Y", 0.0).getEntry();

        public static NetworkTableEntry X2_XButtonEntry = X2_TAB.add("X Button", false).getEntry();
        public static NetworkTableEntry X2_YButtonEntry = X2_TAB.add("Y Button", false).getEntry();
        public static NetworkTableEntry X2_AButtonEntry = X2_TAB.add("A Button", false).getEntry();
        public static NetworkTableEntry X2_BButtonEntry = X2_TAB.add("B Button", false).getEntry();
    }

    public static final class Field {

        public static double UPPER_HUB_HEIGHT_M = Units.inchesToMeters(104);
        public static NetworkTableEntry MatchTimeEntry = AI_TAB
            .add("Match Time", 135.0)
            .withWidget(BuiltInWidgets.kDial)
            .withProperties(Map.of("min", 0, "max", 135))
            .getEntry();
    }

    public static final class Robot {
        public static double SparkWheelRadiusInches = 3;
        public static double SparkWheelDiameterInches = 6;

        public static double DrivetrainWheelDiameterIn = 6;
        public static double DrivetrainWheelRadiusIn = 3;

        public static double DrivetrainTrackWidthMeters;

        public static double countsPerRevolution = 2048;
        public static double gearRatio = (2500/288);

        public static double CAMERA_ANGLE_RADIANS = Units.degreesToRadians(35);
        public static double CAMERA_HEIGHT_M = Units.inchesToMeters(19.5);

        public static double TestHub = Units.inchesToMeters(103.5);
    }
    
    public static final class Vision {
        // public static NetworkTable LimelightTable; 
        public static HttpCamera PrimaryVideoFeed;
        public static HttpCamera BackupVideoFeed;

        public static PhotonCamera primaryCamera;

        public static PhotonPipelineResult primaryCameraResult;

        public static boolean primaryHasTargets;
        public static PhotonTrackedTarget primaryTrackedTarget;

        public static double primaryYaw;
        public static double primaryPitch;
        public static double primaryPitchRadians;

       
        public static PhotonCamera backupCamera;

        public static PhotonPipelineResult backupCameraResult;

        public static boolean backupHasTargets;
        public static PhotonTrackedTarget backupTrackedTarget;

        public static double backupYaw;
        public static double backupPitch;
        public static double backupPitchRadians;

        

        

        public static double DISTANCE_FROM_TARGET;

        public static NetworkTableEntry AI_DISTANCE_ENTRY = AI_TAB
            .add("Distance From Target Meters", 0.0)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 6))
            .getEntry();

        public static boolean RUN_VISION = false;
        // This is the distance from the Limelight to the target

        public static int RedAllianceBallPipeline = 0;
        public static int BlueAllianceBallPipeline = 0;
    }

    public static final class Drivetrain { 
        public static NetworkTableEntry DT_FrontLeftEntry = DT_TAB.add("Front Left Power", 0).getEntry();
        public static NetworkTableEntry DT_rearLeftEntry = DT_TAB.add("rear Left Power", 0).getEntry();
        public static NetworkTableEntry DT_FrontRightEntry = DT_TAB.add("Front Right Power", 0).getEntry();
        public static NetworkTableEntry DT_rearRightEntry = DT_TAB.add("rear Right Power", 0).getEntry();



        public static double DT_PowerConstant = 1.0;
        public static NetworkTableEntry DT_PowerConstantEntry = DT_TAB
            .addPersistent("Drive Power Percentage", 55)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 100))
            .getEntry();

        public static NetworkTableEntry DT_MusicPlaying = DT_TAB.add("Music Playing", false)
            .withWidget(BuiltInWidgets.kBooleanBox)
            .getEntry();

        public static SendableChooser<String> songChooser = new SendableChooser<String>();
        }

       
        

        
        
        /*
        public static NetworkTableEntry DT_Field = DT_TAB
            .add("Field", gameField)
            .withWidget(BuiltInWidgets.kField)
            .getEntry();
        }
        */

    public static final class Status {
        public static boolean teleop;
        public static boolean autonomous;
        public static boolean disabled;
        public static boolean shooting;
        public static boolean indexing;
        public static boolean rampingUp;
        

    }

    public static final class Index {
        public static boolean IndexEmpty = false;

        public static boolean IndexRunning;

        public static NetworkTableEntry BallCounter_Entry = SENSOR_TAB.add("# Balls", 0).getEntry();

        public static NetworkTableEntry intakeSensor_Entry = SENSOR_TAB.add("Intake Sensor", false).getEntry();
        public static NetworkTableEntry midSensor_Entry = SENSOR_TAB.add("Middle Sensor", false).getEntry();
        public static NetworkTableEntry topSensor_Entry = SENSOR_TAB.add("Top Sensor", false).getEntry();
        
        public static NetworkTableEntry SN_BALL_COUNTER = SENSOR_TAB
            .add("Ball Counter", 0)
            .withWidget(BuiltInWidgets.kNumberBar)
            .withProperties(Map.of("min", 0, "max", 2))
            .getEntry();

    }

    public static final class Shooter{

        public static NetworkTableEntry SH_SHOOTER_RPM_Entry = SH_TAB
            .add("Shooter RPM", 0)
            .withWidget(BuiltInWidgets.kDial)
            .withProperties(Map.of("min", 0, "max", 5700))
            .getEntry();


        public static boolean SHOOTER_READY;

        public static double TeleComputedRPM;
        public static double AutoComputedRPM;
        
        public static double SH_ShooterPower = 0.0;
        
    }

    public static final class Autonomous {   
        public static SendableChooser<Command> autoChooser = new SendableChooser<Command>();
    }
}