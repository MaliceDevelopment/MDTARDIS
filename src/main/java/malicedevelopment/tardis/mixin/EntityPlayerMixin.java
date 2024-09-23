package malicedevelopment.tardis.mixin;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static malicedevelopment.tardis.Tardis.MASTER_RANDOM;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {

	@Unique
	private final EntityPlayer thisAs = (EntityPlayer) ((Object) this);

	int regenerationTicksRemaining = 0;
	int regensLeft = 12;
	final int REGEN_DURATION = 200;

	@Inject(method = "hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z", at = @At("HEAD"), cancellable = true, remap = false)
	public void hurt(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> cir) {

		// The fire effect will hurt the player, we dont want this
		if (regenerationTicksRemaining > 0) {
			cir.cancel();
		}

		// Stop Death!
		if (thisAs.getHealth() - damage <= 0 && regensLeft > 0) {
			thisAs.setHealthRaw(thisAs.getMaxHealth());
			thisAs.world.playSoundAtEntity(thisAs, thisAs, "tardis.regen", 0.3F, 1.0F / (MASTER_RANDOM.nextFloat() * 0.4F + 0.8F));
			regenerationTicksRemaining = REGEN_DURATION;
			regensLeft--;
			thisAs.sendMessage("You have " + regensLeft + " regenerations left");
			cir.cancel();
		}
	}

	@Inject(method = "tick()V", at = @At("HEAD"), cancellable = true, remap = false)
	public void tick(CallbackInfo ci) {
		tickRegeneration(thisAs);
	}

	private void tickRegeneration(EntityPlayer player) {
		// Tick down
		if (regenerationTicksRemaining > 0) {
			regenerationTicksRemaining--;
		}

		// If we are regenerating, do things
		if (regenerationTicksRemaining > 0) {
			player.remainingFireTicks = regenerationTicksRemaining; // Set the player on fire

			for(int i = 0; i < 20; ++i) {
				double d = MASTER_RANDOM.nextGaussian() * 0.02;
				double d1 = MASTER_RANDOM.nextGaussian() * 0.02;
				double d2 = MASTER_RANDOM.nextGaussian() * 0.02;
				double d3 = 10.0;
				player.world.spawnParticle("flame", player.x + (double)(MASTER_RANDOM.nextFloat() * player.bbWidth * 2.0F) - (double)player.bbWidth - d * d3, player.y - 0.5 + (double)(MASTER_RANDOM.nextFloat() * player.bbHeight) - d1 * d3, player.z + (double)(MASTER_RANDOM.nextFloat() * player.bbWidth * 2.0F) - (double)player.bbWidth - d2 * d3, d, d1, d2, 0);
				player.world.spawnParticle("heart", player.x + (double)(MASTER_RANDOM.nextFloat() * player.bbWidth * 2.0F) - (double)player.bbWidth - d * d3, player.y - 0.5 + (double)(MASTER_RANDOM.nextFloat() * player.bbHeight) - d1 * d3, player.z + (double)(MASTER_RANDOM.nextFloat() * player.bbWidth * 2.0F) - (double)player.bbWidth - d2 * d3, d, d1, d2, 0);
				player.world.spawnParticle("fireflyRed", player.x + (double)(MASTER_RANDOM.nextFloat() * player.bbWidth * 2.0F) - (double)player.bbWidth - d * d3, player.y - 0.5 + (double)(MASTER_RANDOM.nextFloat() * player.bbHeight) - d1 * d3, player.z + (double)(MASTER_RANDOM.nextFloat() * player.bbWidth * 2.0F) - (double)player.bbWidth - d2 * d3, d, d1, d2, 0);
				player.world.spawnParticle("lava", player.x + (double)(MASTER_RANDOM.nextFloat() * player.bbWidth * 2.0F) - (double)player.bbWidth - d * d3, player.y - 0.5 + (double)(MASTER_RANDOM.nextFloat() * player.bbHeight) - d1 * d3, player.z + (double)(MASTER_RANDOM.nextFloat() * player.bbWidth * 2.0F) - (double)player.bbWidth - d2 * d3, d, d1, d2, 0);
			}
		}
	}

	@Inject(method = "isMovementBlocked()Z", at = @At("HEAD"), cancellable = true, remap = false)
	public void isMovementBlocked(CallbackInfoReturnable<Boolean> cir) {
		if (regenerationTicksRemaining > 0) {
			cir.setReturnValue(true);
		}
	}


	@Inject(method = "readAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("HEAD"), cancellable = true, remap = false)
	public void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
		regensLeft = tag.getInteger("regenerations_left");
		regenerationTicksRemaining = tag.getInteger("regeneration_timer");
	}

	@Inject(method = "addAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("HEAD"), cancellable = true, remap = false)
	public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
		tag.putInt("regenerations_left", regensLeft);
		tag.putInt("regeneration_timer", regenerationTicksRemaining);
	}

	}
