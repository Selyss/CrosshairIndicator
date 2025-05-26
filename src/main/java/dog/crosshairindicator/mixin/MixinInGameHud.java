package dog.crosshairindicator.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;

@Mixin(InGameHud.class)
public class MixinInGameHud {
	@Shadow @Final private MinecraftClient client;
	@Unique Identifier CUSTOM_CROSSHAIR = Identifier.of("crosshairindicator", "crosshair");

	@Inject(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V" ))
	private void drawCrosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (this.client.targetedEntity != null && this.client.targetedEntity.isAlive()) {
            int scaledWidth = 15;
            int scaledHeight = 15;

            context.drawGuiTexture(RenderLayer::getCrosshair, CUSTOM_CROSSHAIR, (context.getScaledWindowWidth() - scaledWidth) / 2, (context.getScaledWindowHeight() - scaledHeight) / 2, scaledWidth, scaledHeight);
        }
    }

}
