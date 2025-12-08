package org.teleops;

import static dev.nextftc.bindings.Bindings.button;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.bindings.Button;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

import org.firstinspires.ftc.teamcode.subsystems.shoulder;

@TeleOp(name = "shoulder teleop")
public class shoulderTeleop extends NextFTCOpMode {

    private Button lowButton;
    private Button midButton;
    private Button highButton;

    public shoulderTeleop() {
        addComponents(
                new SubsystemComponent(shoulder.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onStartButtonPressed() {
        lowButton = button(() -> gamepad1.a);
        lowButton.whenBecomesTrue(() -> shoulder.INSTANCE.setTargetTicks(shoulder.LOW_TICKS));

        midButton = button(() -> gamepad1.b);
        midButton.whenBecomesTrue(() -> shoulder.INSTANCE.setTargetTicks(shoulder.MID_TICKS));

        highButton = button(() -> gamepad1.y);
        highButton.whenBecomesTrue(() -> shoulder.INSTANCE.setTargetTicks(shoulder.HIGH_TICKS));

    }

    @Override
    public void onUpdate() {
        shoulder.INSTANCE.telemetryLoop(telemetry);
        telemetry.update();
    }
}

