// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

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

    //Create variables and NetworkTableEntries to store input from xbox controller (0)
    
    public static double X1_RTValue;
    public static double X1_LTValue;

    public static boolean X1_RB;
    public static boolean X1_LB;

    public static double X1_LJX;
    public static double X1_LJY;
    public static double X1_RJX;
    public static double X1_RJY;

    public static boolean X1_XButton;
    public static boolean X1_YButton;
    public static boolean X1_AButton;
    public static boolean X1_BButton;

    public static double X2_RTValue;
    public static double X2_LTValue;

    public static boolean X2_RB;
    public static boolean X2_LB;

    public static double X2_LJX;
    public static double X2_LJY;
    public static double X2_RJX;
    public static double X2_RJY;

    public static boolean X2_XButton;
    public static boolean X2_YButton;
    public static boolean X2_AButton;
    public static boolean X2_BButton;

    public static double DT_PowerConstant = 1.0;

    public static double MT_ShooterPower = 0.0;

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

    public static NetworkTableEntry DT_FrontLeftEntry = DT_TAB.add("Front Left Power", 0).getEntry();
    public static NetworkTableEntry DT_BackLeftEntry = DT_TAB.add("Back Left Power", 0).getEntry();
    public static NetworkTableEntry DT_FrontRightEntry = DT_TAB.add("Front Right Power", 0).getEntry();
    public static NetworkTableEntry DT_BackRightEntry = DT_TAB.add("Back Right Power", 0).getEntry();

    public static NetworkTableEntry DT_PowerConstantEntry = DT_TAB.add("Drive Power Percentage", 0).getEntry();

    public static NetworkTableEntry MT_ShooterPowerEntry = MOTOR_TAB.add("Shooter Power Percentage", 0).getEntry();
}
