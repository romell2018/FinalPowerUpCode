/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4065.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4065.robot.DriveTrain;
import org.usfirst.frc.team4065.robot.Intake;
import org.usfirst.frc.team4065.robot.Lift;
import org.usfirst.frc.team4065.robot.LeftAuto;
import org.usfirst.frc.team4065.robot.MiddleAuto;
import org.usfirst.frc.team4065.robot.RightAuto;

public class Robot extends IterativeRobot {

	private static final String DriveStraight = "Default";
	private static final String StartingLeft = "LeftAuto";
	private static final String StartingMiddle = "MiddleAuto";
	private static final String StartingRight = "RightAuto";
	private final SendableChooser<String> chooser = new SendableChooser<>();
	private String autoSelected;
	boolean toggleOn = false;
	boolean togglePressed = false;
	double leftStickValue, rightStickValue;

	AHRS navX;
	WPI_TalonSRX FL, FR, BL, BR, lift, intR, intL;
	DigitalInput limitSwitchTop, limitSwitchBtm;
	AnalogInput distanceSensor;
	Joystick driver, manipulator;

	LeftAuto leftauto = new LeftAuto();
	MiddleAuto middleauto = new MiddleAuto();
	RightAuto rightauto = new RightAuto();
	DriveTrain drivetrain = new DriveTrain();
	Intake intake = new Intake();
	Lift _lift = new Lift();

	enum ControllerFlip {
		NormalControl, FlipControl
	}

	ControllerFlip controllerFlip = ControllerFlip.NormalControl;

	@Override
	public void robotInit() {

		final CameraServer server = CameraServer.getInstance();// starts camera
		server.startAutomaticCapture("cam0: ", 0);// camera input

		chooser.addDefault("Default Auto", DriveStraight);
		chooser.addObject("Left Side", StartingLeft);
		chooser.addObject("Middle Side", StartingMiddle);
		chooser.addObject("Right Side", StartingRight);

		SmartDashboard.putData("Auto choices", chooser);

		driver = new Joystick(0);
		manipulator = new Joystick(1);

	}

	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		System.out.println("Auto selected: " + autoSelected);

		FL.setSelectedSensorPosition(0, 0, 10);// resets
		FR.setSelectedSensorPosition(0, 0, 10);

		navX.reset();

	}

	@Override
	public void autonomousPeriodic() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();

		switch (autoSelected) {
		case StartingLeft:
			if (gameData.length() > 0) {
				if (gameData.charAt(0) == 'L') {
					leftauto.scoreLeft(40000);
				} else if (gameData.charAt(0) == 'R') {
					leftauto.scoreRight();
				}
			}
			break;
		case StartingMiddle:
			if (gameData.length() > 0) {
				if (gameData.charAt(0) == 'L') {
					middleauto.scoreLeft();
				} else if (gameData.charAt(0) == 'R') {
					middleauto.scoreLeft();
				}
			}
			break;
		case StartingRight:
			if (gameData.length() > 0) {
				if (gameData.charAt(0) == 'L') {
					rightauto.scoreLeft(40000);
				} else if (gameData.charAt(0) == 'R') {
					rightauto.scoreRight();
				}
			}

			break;
		case DriveStraight:
		default:
			// Put custom auto code here
			break;
		}
	}

	@Override
	public void teleopPeriodic() {

		switch (controllerFlip) {
		case NormalControl:
			drivetrain.TankDrive(driver.getRawAxis(1), driver.getRawAxis(5));
			break;
		case FlipControl:
			drivetrain.FlipDrive(driver.getRawAxis(5), driver.getRawAxis(1));
		}

		
			
		if (driver.getRawButton(2)) {
			if (!togglePressed) {
				toggleOn = !toggleOn;
				togglePressed = true;
			}
		} else {
			togglePressed = false;
		}
		if (toggleOn) {
			//("Toggle On ");
			controllerFlip=ControllerFlip.FlipControl;
		} else {
			//("Toggle Off ");
			controllerFlip=ControllerFlip.NormalControl;
			}
		
		if (manipulator.getRawButton(4)) {
			_lift.liftUp(0.2);
		} else if (manipulator.getRawButton(1)) {
			_lift.liftDown(0.2);
		} else {
			_lift.liftStop(0);
		}
		// ToggleButtonForIntake
		if (manipulator.getRawButton(2)) {
			if (!togglePressed) {
				toggleOn = !toggleOn;
				togglePressed = true;
			}
		} else {
			togglePressed = false;
		}
		if (toggleOn) {
			System.out.println("Toggle On ");
			intake.intakeToggleOn();
		} else {
			System.out.println("Toggle Off ");
			intake.intakeToggleOff();
			// INTAKE
			if (manipulator.getRawButton(5)) {
				intake.intakeIn();
			} else if (manipulator.getRawButton(6)) {
				intake.intakeOut();
			} else {
				intake.intakeStop();

			}

		}

	}
}
