package org.usfirst.frc.team4065.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;

public class RightAuto {
	
	private WPI_TalonSRX FL;
	private WPI_TalonSRX FR;
	private WPI_TalonSRX BL;
	private WPI_TalonSRX BR;
	private WPI_TalonSRX lift;
	private DigitalInput limitSwitchTop;
	private AnalogInput distanceSensor;
	private AHRS navX;
	
	int leftEnc = FL.getSelectedSensorPosition(0);
	int rightEnc = FR.getSelectedSensorPosition(0);//
	
	int autoState = 0;// stage of the autonomous program
	double gyroValue = navX.getAngle();
	public RightAuto() {
		
		FL = new WPI_TalonSRX(26);

		BL = new WPI_TalonSRX(25);

		FR = new WPI_TalonSRX(27);

		BR = new WPI_TalonSRX(28);

		lift = new WPI_TalonSRX(24);

		limitSwitchTop = new DigitalInput(0);

		distanceSensor = new AnalogInput(0);

		try {
			navX = new AHRS(SerialPort.Port.kMXP);
		} catch (final RuntimeException ex) {
			DriverStation.reportError("Error " + "instantiating navX MXP: " + ex.getMessage(), true);
		}
		FL.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		FR.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
	}
	
	public void scoreLeft(int encTicks) {
		System.out.println("Front Left Value: " + leftEnc);
		System.out.println("Front Left Value: " + rightEnc);
		System.out.println("Sensor Reading Value: " + distanceSensor.getValue());
		switch (autoState) {
		case 0:
			if (leftEnc > -encTicks) {
				FL.set(0.2);// move forward
				FR.set(0.2);
				BL.set(0.2);// move forward
				BR.set(0.2);
			} else {
				FL.set(0);
				FL.set(0);// stop
				BL.set(0);
				BL.set(0);// stop
				autoState = 1;
			}
			break;
		case 1:
			if (gyroValue > -90) {
				FL.set(-0.2);
				FR.set(0.2);// turn 90
				FL.set(-0.2);
				FR.set(0.2);// turn 90
			}
			if (gyroValue < -90) {
				FL.set(0);// stop
				FR.set(0);
				BL.set(0);// stop
				BR.set(0);

				FL.setSelectedSensorPosition(0, 0, 10);
				FR.setSelectedSensorPosition(0, 0, 10);
				autoState = 2;
			}
			break;
		case 2:
			if (distanceSensor.getValue() <= 300) {
				FL.set(0.0);
				FR.set(0.0);
				BL.set(0.0);
				BR.set(0.0);
				autoState = 3;
			}
			break;
		case 3:
			lift.set(-0.9);// moves up

			if (limitSwitchTop.get() == true) {
				lift.set(0);// stops the motor when limitswitch is pressed

			}
			break;
		}
	}
	public void scoreRight() {}
}
