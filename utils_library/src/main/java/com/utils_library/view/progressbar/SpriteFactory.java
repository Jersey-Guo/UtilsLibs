package com.utils_library.view.progressbar;

import com.utils_library.view.progressbar.sprite.Sprite;
import com.utils_library.view.progressbar.style.ChasingDots;
import com.utils_library.view.progressbar.style.Circle;
import com.utils_library.view.progressbar.style.CubeGrid;
import com.utils_library.view.progressbar.style.DoubleBounce;
import com.utils_library.view.progressbar.style.FadingCircle;
import com.utils_library.view.progressbar.style.FoldingCube;
import com.utils_library.view.progressbar.style.MultiplePulse;
import com.utils_library.view.progressbar.style.MultiplePulseRing;
import com.utils_library.view.progressbar.style.Pulse;
import com.utils_library.view.progressbar.style.PulseRing;
import com.utils_library.view.progressbar.style.RotatingCircle;
import com.utils_library.view.progressbar.style.RotatingPlane;
import com.utils_library.view.progressbar.style.ThreeBounce;
import com.utils_library.view.progressbar.style.WanderingCubes;
import com.utils_library.view.progressbar.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}
