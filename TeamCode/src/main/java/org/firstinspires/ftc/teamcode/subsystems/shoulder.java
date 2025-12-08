package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.impl.MotorEx;


public class shoulder implements Subsystem {
    public static final shoulder INSTANCE = new shoulder();

    public String MASTER_NAME = "master";
    public String SLAVE_MOTOR = "slave";

    public static final int MIN_TICKS = 0;
    public static final int MAX_TICKS = 3000;

    private final MotorEx MASTER;
    private final MotorEx SLAVE;

    private int targetTicks = 0;

    public shoulder() {
        MASTER = new MotorEx(MASTER_NAME);
        MASTER.zeroed();
        MASTER.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SLAVE = new MotorEx(SLAVE_MOTOR);
        SLAVE.zeroed();
        SLAVE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    public void setTargetTicks(int ticks) {
        targetTicks = Math.max(MIN_TICKS, Math.min(MAX_TICKS, ticks));
        MASTER.atPosition(targetTicks);
        SLAVE.atPosition(targetTicks);
        MASTER.setPower(0.5);
        SLAVE.setPower(0.5);

    }


    public int getTargetTicks() {
        return targetTicks;
    }

    public void telemetryLoop(Telemetry telemetry) {
        telemetry.addData("shoulder target height (ticks)", targetTicks);
    }
}
