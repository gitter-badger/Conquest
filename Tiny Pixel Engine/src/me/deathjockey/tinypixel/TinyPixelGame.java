package me.deathjockey.tinypixel;

import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Abstract layer of the core game. TinyPixelGame, as its name suggests,
 * uses pixel rendering. Each instance of TinyPixelGame should be wrapped
 * in a suitable wrapper depending on the deployment environment.
 * <p>
 * Aside from wrapper dimensions, the game render context dimensions and
 * various other properties are adjusted from here.
 *
 * @author Kevin Yang
 */
public abstract class TinyPixelGame extends Canvas implements Runnable {

    /**
     * Game Wrapper object, this is obtained automatically upon being added to a wrapper
     */
    protected TinyPixelGameWrapper wrapper;

    /**
     * Title of game (displayed on wrapper if applicable)
     */
    protected String title;

    /**
     * Engine state, will only render & update if running
     */
    protected volatile boolean running = false;

    /**
     * Engine render & update thread
     */
    protected Thread gameThread;

    /**
     * FPS to aim for when rendering. Note that actual performance may vary
     */
    protected int targetFps = 60;

    /**
     * Debug option: toggles FPS output in console
     */
    protected boolean fpsVerbose = false;

    /**
     * Set the resize effect.
     *
     * @see ResizePolicy
     */
    protected ResizePolicy resizePolicy = ResizePolicy.RESIZE_SCREEN_CONTENT;
    protected Dimension minimumSize, maximumSize, size;
    protected boolean wrapperResizable = false;

    /**
     * Engine update limiter, will not update upon paused
     */
    protected boolean paused = false;

    /**
     * Size of the BufferStrategy
     */
    protected int numBuffer = 2;

    /**
     * Graphics scale upon rendering, based on n:1 ratio
     */
    protected int graphicsScale = 1;

    /**
     * Game render/update timer, keeps track of delta time
     */
    protected long lastUpdateTime;

    /**
     * Main render tool, contains displayable pixel data
     */
    private RenderContext renderContext;

    /**
     * Game input handler
     */
    private Input input;

    /**
     * Creates an instance of the game with given title, width and height (for context only)
     *
     * @param title  Title of the game (displayed on wrapper if applicable)
     * @param width  Width of the render context
     * @param height Height of the render context
     */
    public TinyPixelGame(String title, int width, int height) {
        size = new Dimension(width, height);
        setSize(width, height);
        input = new Input();
        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
        setMinimumSize(width, height);
        this.title = title;
    }

    /**
     * Invoked upon startup, called once to initialize game elements
     */
    protected abstract void init(TinyPixelGame game, RenderContext renderContext);

    /**
     * Main drawing method, draws all components onto the RenderContext
     */
    protected abstract void gameRender(RenderContext renderContext);

    /**
     * Main logic method, updates all game object when possible
     */
    protected abstract void gameUpdate();

    @Override
    public void run() {
        renderContext = new RenderContext(size.width / graphicsScale, size.height / graphicsScale);

        init(this, renderContext);

        long fpsTimer = System.currentTimeMillis();
        int fps = 0;
        double unprocessed = 0;
        double then = System.nanoTime();
        double nsPerTick = 1000000000.0d / targetFps;
        lastUpdateTime = System.currentTimeMillis();

        while (running) {
            if (fpsVerbose && System.currentTimeMillis() - fpsTimer > 1000) {
                System.out.printf("%d fps%n", fps);
                fps = 0;
                fpsTimer += 1000;
            }

            if (paused) {
                then = System.nanoTime();
                lastUpdateTime = System.currentTimeMillis();
                continue;
            }

            boolean render = false;
            double now = System.nanoTime();
            unprocessed += (now - then) / nsPerTick;
            then = now;

            //process game updates
            while (unprocessed >= 1) {
                gameUpdate();
                input.update();
                lastUpdateTime = System.currentTimeMillis();
                render = true;
                unprocessed--;
            }
            Time.setDelta(unprocessed);

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (render) {
                render();
                fps++;
            }
        }
    }

    /**
     * The main draw method that handles all underlying mechanisms required for drawing
     * game objects / components. Actual rendering is done in <strong>gameRender()</strong>
     * method.
     */
    protected void render() {
        BufferStrategy bs = this.getBufferStrategy();

        //Initialization check
        boolean canRender = true;
        if (bs == null) {
            createBufferStrategy(numBuffer);
            requestFocus();
            canRender = false;
        }

        if (!canRender) return;
        //--END init check

        Graphics g = bs.getDrawGraphics();
        int[] clearRGBA = Colors.fromInt(renderContext.getClearColor());
        g.setColor(new Color(clearRGBA[0], clearRGBA[1], clearRGBA[2], clearRGBA[3]));
        g.fillRect(0, 0, getWidth(), getHeight());
        renderContext.clearAll();
        gameRender(renderContext); //Game rendering logic

        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gdevice = genv.getDefaultScreenDevice();
        GraphicsConfiguration gconfig = gdevice.getDefaultConfiguration();
        BufferedImage nativeImg = gconfig.createCompatibleImage(renderContext.getWidth(), renderContext.getHeight());
        nativeImg.setRGB(0, 0, renderContext.getWidth(), renderContext.getHeight(), renderContext.getPixelData(), 0,
                renderContext.getWidth());
        g.drawImage(nativeImg, 0, 0, getWidth(), getHeight(), null);
        bs.show();
        g.dispose();
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Input getInput() {
        return input;
    }

    protected void setWrapper(TinyPixelGameWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public TinyPixelGameWrapper getWrapper() {
        return wrapper;
    }

    public void pause() {
        paused = true;
    }

    public void unpause() {
        paused = false;
    }

    public int getNumBuffer() {
        return numBuffer;
    }

    public void setNumBuffer(int numBuffer) {
        this.numBuffer = numBuffer;
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * Starts the game engine.
     */
    protected void start() {
        if (!running) {
            running = true;
            gameThread = new Thread(this, "TinyPixelGame_CoreEngine");
            gameThread.setPriority(Thread.MAX_PRIORITY);
            gameThread.start();
        }
    }

    /**
     * Terminates the game engine.
     */
    protected void stop() {
        if (running) {
            running = false;
        }
    }

    public ResizePolicy getResizePolicy() {
        return resizePolicy;
    }

    public void setResizePolicy(ResizePolicy resizePolicy) {
        this.resizePolicy = resizePolicy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

        if (wrapper != null)
            wrapper.setTitle(title);
    }

    public void setMinimumSize(int width, int height) {
        this.minimumSize = new Dimension(width, height);
        if (wrapper != null)
            wrapper.setMinimumSize(width, height);
    }

    @Override
    public Dimension getMinimumSize() {
        return minimumSize;
    }

    public void setMaximumSize(int width, int height) {
        this.maximumSize = new Dimension(width, height);
        if (wrapper != null)
            wrapper.setMaximumSize(width, height);
    }

    @Override
    public Dimension getMaximumSize() {
        return maximumSize;
    }

    public int getGraphicsScale() {
        return graphicsScale;
    }

    public void setGraphicsScale(int graphicsScale) {
        this.graphicsScale = graphicsScale;
        if (renderContext != null) {
            renderContext = new RenderContext(size.width, size.height);
        }
    }

    public int getTargetFps() {
        return targetFps;
    }

    public void setTargetFps(int targetFps) {
        this.targetFps = targetFps;
    }

    public boolean isFpsVerbose() {
        return fpsVerbose;
    }

    public void setFpsVerbose(boolean fpsVerbose) {
        this.fpsVerbose = fpsVerbose;
    }

    public boolean isResizable() {
        return wrapperResizable;
    }

    public void setResizable(boolean wrapperResizable) {
        this.wrapperResizable = wrapperResizable;

        if (wrapper != null)
            wrapper.setResizable(wrapperResizable);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        if (wrapper != null)
            wrapper.setSize(width, height);
    }
}
