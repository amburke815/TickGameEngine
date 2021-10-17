package tick;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_B;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import tick.util.Time;

public class Window {

  private final int width = 1920;
  private final int height = 1080;
  private final String title = "Tick Game Engine";
  private long glfwWindow;
  public float r = 1;
  public float g = 1;
  public float b = 1;
  public float a = 1;

  private static AScene currentScene;

  public static void changeScene(int newSceneCode) {
    switch (newSceneCode) {
      case 0:
        currentScene = new LevelEditorScene();
        break;
      case 1:
        currentScene = new LevelScene();
        break;
      default:
        assert false : "Unknown new scene code: " + newSceneCode;
    }
  }

  /**
   * Enforces the singleton design pattern
   */
  private static final Window singletonInstance = new Window();

  private Window() {
  }

  public static Window getSingletonInstance() {
    return singletonInstance;
  }

  public void run() {
    System.out.println("Running LWGJGL version " + Version.getVersion() + "!");

    init();
    loop();

    // free memory callbacks used for window
    glfwFreeCallbacks(glfwWindow);
    glfwDestroyWindow(glfwWindow);

    // terminate GLFW and free the error callback
    glfwTerminate();
    glfwSetErrorCallback(null).free();
  }

  private void init() {
    // error callback
    GLFWErrorCallback.createPrint(System.err).set();

    // initialize GLFW
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW library");
    }

    // configure GLFW
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

    // create the window:
    // represents the memory address of the window
    glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL);
    if (glfwWindow == NULL) {
      throw new IllegalStateException("Could not create GLFW window :(");
    }

    // set custom mouse callback functions
    glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
    glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
    glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
    // set custom key callback functions
    glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

    // make the OpenGL context current
    glfwMakeContextCurrent(glfwWindow);

    // enable v-sync -> refresh with whatever rate the monitor is refreshing
    glfwSwapInterval(1); // once a frame

    // make the window visible
    glfwShowWindow(glfwWindow);

    // set up interoperability with OpenGL
    GL.createCapabilities();

    Window.changeScene(0);
  }


  private void loop() {

    float beginTime = Time.getCurrentTime();
    float endTime = Time.getCurrentTime();
    float dt = -1.0f;
    while (!glfwWindowShouldClose(glfwWindow)) {
      // Poll events -> allows engine to listen for key/mouse events
      glfwPollEvents();

      glClearColor(r, g, b, a);
      glClear(GL_COLOR_BUFFER_BIT);

      if (dt >=0 )
        currentScene.updateScene(dt);

      glfwSwapBuffers(glfwWindow);

      endTime = Time.getCurrentTime();
      dt = endTime - beginTime;
      beginTime = endTime;

    }
  }
}
