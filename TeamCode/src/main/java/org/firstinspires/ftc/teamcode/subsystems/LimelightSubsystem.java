package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

/**
 * Simple Limelight subsystem for AprilTag detection
 */
public class LimelightSubsystem {
    
    private final Limelight3A limelight;
    private final Telemetry telemetry;
    private LLResult latestResult;
    
    public LimelightSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
    }
    
    // Initialize with default pipeline 0
    public void init() {
        limelight.pipelineSwitch(0);
        limelight.start();
    }
    
    // Update result - call this in your loop
    public void update() {
        latestResult = limelight.getLatestResult();
    }
    
    // Check if AprilTag is detected
    public boolean hasTarget() {
        return latestResult != null && latestResult.isValid();
    }
    
    // Get AprilTag ID (-1 if no tag)
    public int getAprilTagID() {
        if (!hasTarget()) return -1;
        
        List<LLResultTypes.FiducialResult> fiducials = latestResult.getFiducialResults();
        if (fiducials.isEmpty()) return -1;
        
        return fiducials.get(0).getFiducialId();
    }
    
    // Get horizontal angle (+ = right, - = left)
    public double getTx() {
        if (!hasTarget()) return 0.0;
        return latestResult.getTx();
    }
    
    // Get vertical angle (+ = above, - = below)
    public double getTy() {
        if (!hasTarget()) return 0.0;
        return latestResult.getTy();
    }
    
    // Display basic telemetry
    public void displayTelemetry() {
        if (hasTarget()) {
            telemetry.addData("AprilTag ID", getAprilTagID());
            telemetry.addData("Angle", "%.1f°", getTx());
        } else {
            telemetry.addData("AprilTag", "No target");
        }
    }
    
    // Stop the Limelight
    public void stop() {
        limelight.stop();
    }
}

