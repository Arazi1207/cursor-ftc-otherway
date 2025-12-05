package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.List;

/**
 * Simple Limelight subsystem for AprilTag detection
 */
public class LimelightSubsystem{
    
    private final Limelight3A limelight;
    private final Telemetry telemetry;
    private static final int APRIL_TAG_PIPELINE_INDEX = 0; // slot 0 must host "april_tag_pattern"
    private LLResult latestResult;
    
    public LimelightSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
    }
    
    // Initialize with default pipeline 0
    public void init() {
        limelight.pipelineSwitch(APRIL_TAG_PIPELINE_INDEX);
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

    public double getTa() {
        if (!hasTarget()) return 0.0;
        return latestResult.getTa();
    }

    public double getDistanceMeters() {
        if (!hasTarget()) return 0.0;
        Pose3D pose = latestResult.getBotpose();
        if (pose == null) return 0.0;
        Position meters = pose.getPosition().toUnit(DistanceUnit.METER);
        double x = meters.x;
        double y = meters.y;
        double z = meters.z;
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    // Display basic telemetry
    public void displayTelemetry() {
        if (hasTarget()) {
            telemetry.addData("AprilTag ID", getAprilTagID());
            telemetry.addData("tx", getTx());
            telemetry.addData("ty", getTy());
            telemetry.addData("ta", getTa());
            telemetry.addData("Distance (m)", getDistanceMeters());
        } else {
            telemetry.addData("AprilTag", "No target");
        }
    }
    
    // Stop the Limelight
    public void stop() {
        limelight.stop();
    }
}