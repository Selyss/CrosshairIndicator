package dog.crosshairindicator.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
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
	@Unique int scaledHeight = 9;
	@Unique int scaledWidth = 9;

	@Unique Identifier textureLocation = Identifier.of("crosshairindicator", "crosshair.png");

	@Inject(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0))
	private void drawCrosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		if (this.client.targetedEntity instanceof PlayerEntity) {
			RenderSystem.setShaderTexture(0, textureLocation);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			context.drawTexture(textureLocation, (context.getScaledWindowWidth() - scaledWidth) / 2, (context.getScaledWindowHeight() - scaledHeight) / 2, 0, 0, scaledWidth, scaledHeight);
		}
	}
}