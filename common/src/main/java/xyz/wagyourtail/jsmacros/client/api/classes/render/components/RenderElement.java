package xyz.wagyourtail.jsmacros.client.api.classes.render.components;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

/**
 * @author Wagyourtail
 */
public interface RenderElement extends Drawable {

    MinecraftClient mc = MinecraftClient.getInstance();

    int getZIndex();

    default void render3D(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        render(matrices, mouseX, mouseY, delta);
    }

    default void setupMatrix(MatrixStack matrices, double x, double y, float scale, float rotation) {
        setupMatrix(matrices, x, y, scale, rotation, 0, 0, false);
    }

    default void setupMatrix(MatrixStack matrices, double x, double y, float scale, float rotation, double width, double height, boolean rotateAroundCenter) {
        matrices.translate(x, y, 0);
        matrices.scale(scale, scale, 1);
        if (rotateAroundCenter) {
            matrices.translate(width / 2, height / 2, 0);
        }
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(rotation));
        if (rotateAroundCenter) {
            matrices.translate(-width / 2, -height / 2, 0);
        }
        matrices.translate(-x, -y, 0);
    }

}
