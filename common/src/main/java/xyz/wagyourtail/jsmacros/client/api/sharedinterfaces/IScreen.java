package xyz.wagyourtail.jsmacros.client.api.sharedinterfaces;

import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;

import xyz.wagyourtail.jsmacros.client.api.helpers.TextHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.gui.ButtonWidgetHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.ItemStackHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.gui.CheckBoxWidgetHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.gui.CyclingButtonWidgetHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.gui.LockButtonWidgetHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.gui.SliderWidgetHelper;
import xyz.wagyourtail.jsmacros.client.api.helpers.gui.TextFieldWidgetHelper;
import xyz.wagyourtail.jsmacros.client.api.sharedclasses.PositionCommon;
import xyz.wagyourtail.jsmacros.client.api.sharedclasses.RenderCommon.Item;
import xyz.wagyourtail.jsmacros.client.api.sharedclasses.RenderCommon.Rect;
import xyz.wagyourtail.jsmacros.core.MethodWrapper;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Wagyourtail
 * @since 1.2.7
 */
public interface IScreen extends IDraw2D<IScreen> {
    
    /**
     * @since 1.2.7
     * @return
     */
    default String getScreenClassName() {
        return this.getClass().getSimpleName();
    }
    
    /**
     * @since 1.0.5
     * @return
     */
    String getTitleText();
    
    /**
     * in {@code 1.3.1} updated to work with all button widgets not just ones added by scripts.
     * @since 1.0.5
     * @return
     */
    List<ButtonWidgetHelper<?>> getButtonWidgets();
    
    /**
     * in {@code 1.3.1} updated to work with all text fields not just ones added by scripts.
     * @since 1.0.5
     * @return
     */
    List<TextFieldWidgetHelper> getTextFields();
    
    /**
     * @since 1.0.5
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     */
    ButtonWidgetHelper<?> addButton(int x, int y, int width, int height, String text, MethodWrapper<ButtonWidgetHelper<?>, IScreen, Object, ?> callback);
    
    /**
     * @since 1.4.0
     * @param x
     * @param y
     * @param width
     * @param height
     * @param zIndex
     * @param text
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     */
    ButtonWidgetHelper<?> addButton(int x, int y, int width, int height, int zIndex, String text, MethodWrapper<ButtonWidgetHelper<?>, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text
     * @param checked
     * @param showMessage
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     *
     * @since 1.9.0
     */
    CheckBoxWidgetHelper addCheckbox(int x, int y, int width, int height, String text, boolean checked, boolean showMessage, MethodWrapper<CheckBoxWidgetHelper, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text
     * @param checked
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     *
     * @since 1.9.0
     */
    CheckBoxWidgetHelper addCheckbox(int x, int y, int width, int height, String text, boolean checked, MethodWrapper<CheckBoxWidgetHelper, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param zIndex
     * @param text
     * @param checked
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     *
     * @since 1.9.0
     */
    CheckBoxWidgetHelper addCheckbox(int x, int y, int width, int height, int zIndex, String text, boolean checked, MethodWrapper<CheckBoxWidgetHelper, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param zIndex
     * @param text
     * @param checked
     * @param showMessage
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     *
     * @since 1.9.0
     */
    CheckBoxWidgetHelper addCheckbox(int x, int y, int width, int height, int zIndex, String text, boolean checked, boolean showMessage, MethodWrapper<CheckBoxWidgetHelper, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text
     * @param value
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @param steps
     * @return
     *
     * @since 1.9.0
     */
    SliderWidgetHelper addSlider(int x, int y, int width, int height, String text, double value, MethodWrapper<SliderWidgetHelper, IScreen, Object, ?> callback, int steps);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param zIndex
     * @param text
     * @param value
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @param steps
     * @return
     *
     * @since 1.9.0
     */
    SliderWidgetHelper addSlider(int x, int y, int width, int height, int zIndex, String text, double value, MethodWrapper<SliderWidgetHelper, IScreen, Object, ?> callback, int steps);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param text
     * @param value
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     *
     * @since 1.9.0
     */
    SliderWidgetHelper addSlider(int x, int y, int width, int height, String text, double value, MethodWrapper<SliderWidgetHelper, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param zIndex
     * @param text
     * @param value
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     *
     * @since 1.9.0
     */
    SliderWidgetHelper addSlider(int x, int y, int width, int height, int zIndex, String text, double value, MethodWrapper<SliderWidgetHelper, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param textureStartX
     * @param textureStartY
     * @param hoverOffset
     * @param texture
     * @param textureWidth
     * @param textureHeight
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     *
     * @since 1.9.0
     */
    ButtonWidgetHelper<TexturedButtonWidget> addTexturedButton(int x, int y, int width, int height, int textureStartX, int textureStartY, int hoverOffset, String texture, int textureWidth, int textureHeight, MethodWrapper<ButtonWidgetHelper<TexturedButtonWidget>, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param zIndex
     * @param textureStartX
     * @param textureStartY
     * @param hoverOffset
     * @param texture
     * @param textureWidth
     * @param textureHeight
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     *
     * @since 1.9.0
     */
    ButtonWidgetHelper<TexturedButtonWidget> addTexturedButton(int x, int y, int width, int height, int zIndex, int textureStartX, int textureStartY, int hoverOffset, String texture, int textureWidth, int textureHeight, MethodWrapper<ButtonWidgetHelper<TexturedButtonWidget>, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param textureStartX
     * @param textureStartY
     * @param texture
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     *
     * @since 1.9.0
     */
    ButtonWidgetHelper<TexturedButtonWidget> addTexturedButton(int x, int y, int width, int height, int textureStartX, int textureStartY, String texture, MethodWrapper<ButtonWidgetHelper<TexturedButtonWidget>, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     *
     * @since 1.9.0
     */
    LockButtonWidgetHelper addLockButton(int x, int y, MethodWrapper<LockButtonWidgetHelper, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param zIndex
     * @param callback calls your method as a {@link Consumer}&lt;{@link ButtonWidgetHelper}&gt;
     * @return
     *
     * @since 1.9.0
     */
    LockButtonWidgetHelper addLockButton(int x, int y, int zIndex, MethodWrapper<LockButtonWidgetHelper, IScreen, Object, ?> callback);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param callback
     * @param values
     * @return
     *
     * @since 1.9.0
     */
    CyclingButtonWidgetHelper<?> addCyclingButton(int x, int y, int width, int height, MethodWrapper<CyclingButtonWidgetHelper<?>, IScreen, Object, ?> callback, String[] values, String initial);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param zIndex
     * @param callback
     * @param values
     * @return
     *
     * @since 1.9.0
     */
    CyclingButtonWidgetHelper<?> addCyclingButton(int x, int y, int width, int height, int zIndex, MethodWrapper<CyclingButtonWidgetHelper<?>, IScreen, Object, ?> callback, String[] values, String initial);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param zIndex
     * @param callback
     * @param values
     * @param alternatives
     * @return
     *
     * @since 1.9.0
     */
    CyclingButtonWidgetHelper<?> addCyclingButton(int x, int y, int width, int height, int zIndex, MethodWrapper<CyclingButtonWidgetHelper<?>, IScreen, Object, ?> callback, String[] values, String[] alternatives, String initial, String prefix);

    /**
     * @param x
     * @param y
     * @param width
     * @param height
     * @param zIndex
     * @param callback
     * @param values
     * @param alternatives
     * @param initial
     * @param prefix
     * @param alternateToggle
     * @return
     *
     * @since 1.9.0
     */
    CyclingButtonWidgetHelper<?> addCyclingButton(int x, int y, int width, int height, int zIndex, MethodWrapper<CyclingButtonWidgetHelper<?>, IScreen, Object, ?> callback, String[] values, String[] alternatives, String initial, String prefix, MethodWrapper<?, ?, Boolean, ?> alternateToggle);

    /**
     * @since 1.0.5
     * @param btn
     * @return
     */
     @Deprecated
    IScreen removeButton(ButtonWidgetHelper<?> btn);
    
    /**
     * @since 1.0.5
     * @param x
     * @param y
     * @param width
     * @param height
     * @param message
     * @param onChange calls your method as a {@link Consumer}&lt;{@link String}&gt;
     * @return
     */
    TextFieldWidgetHelper addTextInput(int x, int y, int width, int height, String message, MethodWrapper<String, IScreen, Object, ?> onChange);
    
    /**
     * @since 1.0.5
     * @param x
     * @param y
     * @param width
     * @param height
     * @param zIndex
     * @param message
     * @param onChange calls your method as a {@link Consumer}&lt;{@link String}&gt;
     * @return
     */
    TextFieldWidgetHelper addTextInput(int x, int y, int width, int height, int zIndex, String message, MethodWrapper<String, IScreen, Object, ?> onChange);
    
    /**
     * @since 1.0.5
     * @param inp
     * @return
     */
     @Deprecated
    IScreen removeTextInput(TextFieldWidgetHelper inp);
    
    /**
     * @since 1.2.7
     * @param onMouseDown calls your method as a {@link BiConsumer}&lt;{@link PositionCommon.Pos2D}, {@link Integer}&gt;
     * @return
     */
    IScreen setOnMouseDown(MethodWrapper<PositionCommon.Pos2D, Integer, Object, ?> onMouseDown);
    
    /**
     * @since 1.2.7
     * @param onMouseDrag calls your method as a {@link BiConsumer}&lt;{@link PositionCommon.Vec2D}, {@link Integer}&gt;
     * @return
     */
    IScreen setOnMouseDrag(MethodWrapper<PositionCommon.Vec2D, Integer, Object, ?> onMouseDrag);
    
    /**
     * @since 1.2.7
     * @param onMouseUp calls your method as a {@link BiConsumer}&lt;{@link PositionCommon.Pos2D}, {@link Integer}&gt;
     * @return
     */
    IScreen setOnMouseUp(MethodWrapper<PositionCommon.Pos2D, Integer, Object, ?> onMouseUp);
    
    /**
     * @since 1.2.7
     * @param onScroll calls your method as a {@link BiConsumer}&lt;{@link PositionCommon.Pos2D}, {@link Double}&gt;
     * @return
     */
    IScreen setOnScroll(MethodWrapper<PositionCommon.Pos2D, Double, Object, ?> onScroll);
    
    /**
     * @since 1.2.7
     * @param onKeyPressed calls your method as a {@link BiConsumer}&lt;{@link Integer}, {@link Integer}&gt;
     * @return
     */
    IScreen setOnKeyPressed(MethodWrapper<Integer, Integer, Object, ?> onKeyPressed);
    
    /**
     * @since 1.2.7
     * @param onClose calls your method as a {@link Consumer}&lt;{@link IScreen}&gt;
     * @return
     */
    IScreen setOnClose(MethodWrapper<IScreen, Object, Object, ?> onClose);
    
    /**
     * @since 1.1.9
     */
    void close();
    
    /**
     * @since 1.2.0
     */
    @Override
    Rect addRect(int x1, int y1, int x2, int y2, int color);
    
    /**
     * @since 1.2.0
     */
     @Override
    Rect addRect(int x1, int y1, int x2, int y2, int color, int alpha);
    
    /**
     * @since 1.2.0
     */
    Rect addRect(int x1, int y1, int x2, int y2, int color, int alpha, double rotation);
    
    /**
    * @since 1.2.0
     */
     @Override
     @Deprecated
    IScreen removeRect(Rect r);
    
    /**
     * @since 1.2.0
     */
    @Override
    Item addItem(int x, int y, String id);
    
    /**
     * @since 1.2.0
     */
    @Override
    Item addItem(int x, int y, String id, boolean overlay);
    
    /**
     * @since 1.2.0
     */
    Item addItem(int x, int y, String id, boolean overlay, double scale, double rotation);
    
    /**
     * @since 1.2.0
     */
    @Override
    Item addItem(int x, int y, ItemStackHelper item);
    
    /**
     * @since 1.2.0
     */
    @Override
    Item addItem(int x, int y, ItemStackHelper item, boolean overlay);
    
    /**
     * @since 1.2.0
     */
    Item addItem(int x, int y, ItemStackHelper item, boolean overlay, double scale, double rotation);
    
    /**
     * @since 1.2.0
     */
     @Override
     @Deprecated
    IScreen removeItem(Item i);
    
    /**
     * calls the screen's init function re-loading it.
     * @since 1.2.7
     */
    IScreen reloadScreen();

    /**
     * @since 1.9.0
     */
    ButtonWidgetHelper.ButtonBuilder getButtonBuilder();

    /**
     * @since 1.9.0
     */
    CheckBoxWidgetHelper.CheckBoxBuilder getCheckBoxBuilder();

    /**
     * @since 1.9.0
     */
    CyclingButtonWidgetHelper.CyclicButtonBuilder<?> getCyclicButtonBuilder(MethodWrapper<Object, ?, TextHelper, ?> valueToText);

    /**
     * @since 1.9.0
     */
    LockButtonWidgetHelper.LockButtonBuilder getLockButtonBuilder();

    /**
     * @since 1.9.0
     */
    SliderWidgetHelper.SliderBuilder getSliderBuilder();

    /**
     * @since 1.9.0
     */
    TextFieldWidgetHelper.TextFieldBuilder getTextFieldBuilder();

    /**
     * @since 1.9.0
     */
    ButtonWidgetHelper.TexturedButtonBuilder getTexturedButtonBuilder();
    
    /**
     * DON'T TOUCH
     * @since 1.4.1
     */
    void onRenderInternal(MatrixStack matrices, int mouseX, int mouseY, float delta);

    MethodWrapper<IScreen, Object, Object, ?> getOnClose();
}
