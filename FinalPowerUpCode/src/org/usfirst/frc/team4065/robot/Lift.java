package org.usfirst.frc.team4065.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class Lift {
	private WPI_TalonSRX lift;
	private DigitalInput limitSwitchTop, limitSwitchBtm;

	public Lift() {
		
		lift = new WPI_TalonSRX(24);
		
		limitSwitchTop = new DigitalInput(0);
		
		limitSwitchBtm = new DigitalInput(1);

	}

	public void liftUp(double speed) {

		lift.set(-1 * speed);// move up
		if (limitSwitchTop.get() == true) {
			lift.set(0);
		} else {
			lift.set(0);
		}
	}

	public void liftDown(double speed) {
		lift.set(speed);// move down
		if (limitSwitchBtm.get() == true) {
			lift.set(0);
		} else {
			lift.set(0);
		}

	}

	public void liftStop(double speed) {
		lift.set(speed);
	}

}
