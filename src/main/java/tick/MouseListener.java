package tick;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.glfw.GLFW;

public class MouseListener {

  private static final MouseListener singletonInstance = new MouseListener();
  private double scrollX;
  private double scrollY;
  private double xPos, yPos,lastX, lastY;
  private boolean isDragging;
  private final boolean mouseButtonPressed[] = new boolean[3];

  private MouseListener() {
    scrollX = 0;
    scrollY = 0;
    xPos = 0;
    yPos = 0;
    lastX = 0;
    lastY = 0;
    isDragging = false;
  }

  public static MouseListener getSingletonInstance() {
    return singletonInstance;
  }

  public static void mousePosCallback(long window, double newXPos, double newYPos) {
    // update the last x and y positions to their most recent value
    getSingletonInstance().lastX = singletonInstance.xPos;
    getSingletonInstance().lastY = singletonInstance.yPos;

    // update the current x and y positions to the arguments passed
    getSingletonInstance().xPos = newXPos;
    getSingletonInstance().yPos = newYPos;

    // set the dragging flag
    getSingletonInstance().isDragging =
        getSingletonInstance().mouseButtonPressed[0]
        || getSingletonInstance().mouseButtonPressed[1]
        || getSingletonInstance().mouseButtonPressed[2];
  }


  public static void mouseButtonCallback(long window, int buttonIdx, int action, int mods) {
    if (action == GLFW.GLFW_PRESS) {
      getSingletonInstance().mouseButtonPressed[buttonIdx] = true;
    }
    else if (action == GLFW.GLFW_RELEASE) {
      if (buttonIdx < singletonInstance.mouseButtonPressed.length) {
        getSingletonInstance().mouseButtonPressed[buttonIdx] = false;
        getSingletonInstance().isDragging = false;
      }
    }
  }

  public static void mouseScrollCallback(long window, double dx, double dy) {
    getSingletonInstance().scrollX = dx;
    getSingletonInstance().scrollY = dy;
  }

  public static void endFrame() {
    getSingletonInstance().scrollX = 0;
    getSingletonInstance().scrollY = 0;
    getSingletonInstance().lastX = 0;
    getSingletonInstance().lastY = 0;
    getSingletonInstance().isDragging = false;
  }

  public static float getX() {
    return (float)getSingletonInstance().xPos;
  }

  public static float getY() {
    return (float)getSingletonInstance().yPos;
  }

  public static float getDx() {
    return (float)(getSingletonInstance().xPos - getSingletonInstance().lastX);
  }

  public static float getDy() {
    return (float)(getSingletonInstance().yPos - getSingletonInstance().lastY);
  }

  public static float getScrollX() {
    return (float)getSingletonInstance().scrollX;
  }

  public static float getScrollY() {
    return (float)getSingletonInstance().scrollY;
  }

  public static boolean isDragging() {
    return getSingletonInstance().isDragging;
  }

  public static boolean mouseButtonDown(int buttonIdx) {
    if (buttonIdx < getSingletonInstance().mouseButtonPressed.length) {
      return getSingletonInstance().mouseButtonPressed[buttonIdx];
    }
    return false;
  }




}
