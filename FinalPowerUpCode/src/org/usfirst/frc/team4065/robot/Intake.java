package org.usfirst.frc.team4065.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake {

	private WPI_TalonSRX intR, intL;

	public Intake() {

		intR = new WPI_TalonSRX(29);
		
		intL = new WPI_TalonSRX(30);

	}

	public void intakeToggleOn() {

		intL.set(-0.15);// toggle on
		intR.set(0.15);
	}

	public void intakeToggleOff() {
		intL.set(0);// toggle off
		intR.set(0);
	}

	public void intakeIn() {

		intL.set(0.9);// suck cube
		intR.set(-0.9);
	}

	public void intakeOut() {
		intL.set(-0.9);// spit cube
		intR.set(0.9);

	}

	public void intakeStop() {
		intL.set(0);
		intR.set(0);
	}
}
