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

	@Inject(method = "hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z", at = @At("HEAD"), cancellable = true, remap = false)
	public void hurt(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> cir) {
		System.out.println(type.getLanguageKey());

		// The fire effect will hurt the player, we dont want this
		if (regenerationTicksRemaining > 0) {
			cir.cancel();
		}

		// Stop Death!
		if (thisAs.getHealth() - damage <= 0 && regensLeft > 0) {
			thisAs.setHealthRaw(thisAs.getMaxHealth());
			regenerationTicksRemaining = 600;
			regensLeft--;
			thisAs.sendMessage("you have " + regensLeft + " regenerations left");
			System.out.println(regenerationTicksRemaining);
			cir.cancel();
		}
	}

	@Inject(method = "tick()V", at = @At("HEAD"), cancellable = true, remap = false)
	public void tick(CallbackInfo ci) {
		// Tick down
		if (regenerationTicksRemaining > 0) {
			regenerationTicksRemaining--;
		}

		// If we are regenerating, do things
		if (regenerationTicksRemaining > 0) {
			thisAs.remainingFireTicks = regenerationTicksRemaining; // Set the player on fire
			for(int i = 0; i < 20; ++i) {
				double d = MASTER_RANDOM.nextGaussian() * 0.02;
				double d1 = MASTER_RANDOM.nextGaussian() * 0.02;
				double d2 = MASTER_RANDOM.nextGaussian() * 0.02;
				double d3 = 10.0;
				thisAs.world.spawnParticle("flame", thisAs.x + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbWidth * 2.0F) - (double)thisAs.bbWidth - d * d3, thisAs.y - 0.5 + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbHeight) - d1 * d3, thisAs.z + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbWidth * 2.0F) - (double)thisAs.bbWidth - d2 * d3, d, d1, d2, 0);
				thisAs.world.spawnParticle("heart", thisAs.x + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbWidth * 2.0F) - (double)thisAs.bbWidth - d * d3, thisAs.y - 0.5 + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbHeight) - d1 * d3, thisAs.z + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbWidth * 2.0F) - (double)thisAs.bbWidth - d2 * d3, d, d1, d2, 0);
				thisAs.world.spawnParticle("fireflyRed", thisAs.x + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbWidth * 2.0F) - (double)thisAs.bbWidth - d * d3, thisAs.y - 0.5 + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbHeight) - d1 * d3, thisAs.z + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbWidth * 2.0F) - (double)thisAs.bbWidth - d2 * d3, d, d1, d2, 0);
				thisAs.world.spawnParticle("lava", thisAs.x + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbWidth * 2.0F) - (double)thisAs.bbWidth - d * d3, thisAs.y - 0.5 + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbHeight) - d1 * d3, thisAs.z + (double)(MASTER_RANDOM.nextFloat() * thisAs.bbWidth * 2.0F) - (double)thisAs.bbWidth - d2 * d3, d, d1, d2, 0);
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
	}

	@Inject(method = "addAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("HEAD"), cancellable = true, remap = false)
	public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
		tag.putInt("regenerations_left", regensLeft);
	}

	}
