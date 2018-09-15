package org.usfirst.frc.team4065.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class DriveTrain {
	// constructor will set the values when you create the object
	private WPI_TalonSRX FL;
	private WPI_TalonSRX FR;
	private WPI_TalonSRX BL;
	private WPI_TalonSRX BR;

	double leftStickValue = 0.0;
	double rightStickValue = 0.0;

	public DriveTrain() {

		FL = new WPI_TalonSRX(26);

		BL = new WPI_TalonSRX(25);

		FR = new WPI_TalonSRX(27);

		BR = new WPI_TalonSRX(28);

	}

	public void TankDrive(double leftStickValue, double rightStickValue) {

		if (Math.abs(leftStickValue) > 0.1 || Math.abs(rightStickValue) > 0.1) {
			FL.set(-leftStickValue * 0.6);
			FR.set(-rightStickValue * 0.6);
			BL.set(-leftStickValue * 0.6);
			BR.set(-rightStickValue * 0.6);
		} else {
			FL.set(0);
			FR.set(0);
			BL.set(0);
			BR.set(0);
		}
	}

	public void FlipDrive(double leftStickValue, double rightStickValue) {

		if (Math.abs(leftStickValue) > 0.1 || Math.abs(rightStickValue) > 0.1) {
			FL.set(leftStickValue * 0.6);
			FR.set(rightStickValue * 0.6);
			BL.set(leftStickValue * 0.6);
			BR.set(rightStickValue * 0.6);
		} else {
			FL.set(0);
			FR.set(0);
			BL.set(0);
			BR.set(0);
		}
	}
}
