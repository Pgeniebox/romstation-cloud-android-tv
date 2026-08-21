package com.world.cloudxsolution.romstation;

import android.view.KeyEvent;
import android.view.MotionEvent;
import java.util.HashMap;
import java.util.Map;

public class GamepadMapper {

    // Maps physical Android KeyCodes to internal protocol IDs (1-25)
    public Map<Integer, Integer> buttonToIdMap = new HashMap<>();

    // Maps Axis descriptions ("AxisID:Direction") to protocol IDs (1-25)
    // Direction is 1 or -1
    public Map<String, Integer> axisToIdMap = new HashMap<>();

    public GamepadMapper() {
        resetToDefaults();
    }

    public void resetToDefaults() {
        buttonToIdMap.clear();
        axisToIdMap.clear();

        // Default Buttons
        buttonToIdMap.put(KeyEvent.KEYCODE_BUTTON_A, 5);
        buttonToIdMap.put(KeyEvent.KEYCODE_BUTTON_B, 6);
        buttonToIdMap.put(KeyEvent.KEYCODE_BUTTON_Y, 7);
        buttonToIdMap.put(KeyEvent.KEYCODE_BUTTON_X, 8);
        buttonToIdMap.put(KeyEvent.KEYCODE_BUTTON_L1, 9);
        buttonToIdMap.put(KeyEvent.KEYCODE_BUTTON_R1, 10);
        buttonToIdMap.put(KeyEvent.KEYCODE_BUTTON_SELECT, 11);
        buttonToIdMap.put(KeyEvent.KEYCODE_BUTTON_START, 12);
        buttonToIdMap.put(KeyEvent.KEYCODE_HOME, 13);
        buttonToIdMap.put(KeyEvent.KEYCODE_BUTTON_THUMBL, 14);
        buttonToIdMap.put(KeyEvent.KEYCODE_BUTTON_THUMBR, 15);

        // Default Axes (Joysticks and Triggers)
        axisToIdMap.put(MotionEvent.AXIS_X + ":-1", 16); // Left Stick Left
        axisToIdMap.put(MotionEvent.AXIS_X + ":1", 17);  // Left Stick Right
        axisToIdMap.put(MotionEvent.AXIS_Y + ":-1", 18); // Left Stick Up
        axisToIdMap.put(MotionEvent.AXIS_Y + ":1", 19);  // Left Stick Down

        axisToIdMap.put(MotionEvent.AXIS_Z + ":-1", 21); // Right Stick Left
        axisToIdMap.put(MotionEvent.AXIS_Z + ":1", 22);  // Right Stick Right
        axisToIdMap.put(MotionEvent.AXIS_RZ + ":-1", 23); // Right Stick Up
        axisToIdMap.put(MotionEvent.AXIS_RZ + ":1", 24);  // Right Stick Down

        axisToIdMap.put(MotionEvent.AXIS_BRAKE + ":1", 20); // L2
        axisToIdMap.put(MotionEvent.AXIS_GAS + ":1", 25);   // R2

        // Standard D-pad Axes (HAT) - many gamepads use these instead of KeyEvents
        axisToIdMap.put(MotionEvent.AXIS_HAT_Y + ":-1", 1); // Pad Up
        axisToIdMap.put(MotionEvent.AXIS_HAT_Y + ":1", 2);  // Pad Down
        axisToIdMap.put(MotionEvent.AXIS_HAT_X + ":-1", 3); // Pad Left
        axisToIdMap.put(MotionEvent.AXIS_HAT_X + ":1", 4);  // Pad Right

        // Fallback D-pad Buttons
        buttonToIdMap.put(KeyEvent.KEYCODE_DPAD_UP, 1);
        buttonToIdMap.put(KeyEvent.KEYCODE_DPAD_DOWN, 2);
        buttonToIdMap.put(KeyEvent.KEYCODE_DPAD_LEFT, 3);
        buttonToIdMap.put(KeyEvent.KEYCODE_DPAD_RIGHT, 4);
    }

    public static String getInputName(int id) {
        switch (id) {
            case 1: return "Pad Up";
            case 2: return "Pad Down";
            case 3: return "Pad Left";
            case 4: return "Pad Right";
            case 5: return "A / Cross";
            case 6: return "B / Circle";
            case 7: return "Y / Triangle";
            case 8: return "X / Square";
            case 9: return "L1 / LB";
            case 10: return "R1 / RB";
            case 11: return "Select / Back";
            case 12: return "Start";
            case 13: return "Home / PS";
            case 14: return "L3 (Left Stick Click)";
            case 15: return "R3 (Right Stick Click)";
            case 16: return "Left Stick Left";
            case 17: return "Left Stick Right";
            case 18: return "Left Stick Up";
            case 19: return "Left Stick Down";
            case 20: return "L2 / LT (Trigger)";
            case 21: return "Right Stick Left";
            case 22: return "Right Stick Right";
            case 23: return "Right Stick Up";
            case 24: return "Right Stick Down";
            case 25: return "R2 / RT (Trigger)";
            default: return "Unknown (" + id + ")";
        }
    }
}