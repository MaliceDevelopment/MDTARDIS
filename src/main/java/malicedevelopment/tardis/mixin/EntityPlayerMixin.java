package malicedevelopment.tardis.mixin;

import com.mojang.nbt.CompoundTag;
import malicedevelopment.tardis.access.RegenerationDataAccess;
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
public abstract class EntityPlayerMixin implements RegenerationDataAccess {

	@Unique
	private final EntityPlayer thisAs = (EntityPlayer) ((Object) this);

	int regenerationTicksElapsed = -1;
	int regensLeft = 12;
	final int REGEN_DURATION = 200;

	@Inject(method = "hurt(Lnet/minecraft/core/entity/Entity;ILnet/minecraft/core/util/helper/DamageType;)Z", at = @At("HEAD"), cancellable = true, remap = false)
	public void hurt(Entity attacker, int damage, DamageType type, CallbackInfoReturnable<Boolean> cir) {

		// The fire effect will hurt the player, we dont want this
		if (regenerationTicksElapsed > 0) {
			cir.cancel();
		}

		// Stop Death!
		if (thisAs.getHealth() - damage <= 0 && regensLeft > 0) {
			thisAs.setHealthRaw(thisAs.getMaxHealth());
			thisAs.world.playSoundAtEntity(thisAs, thisAs, "tardis.regen", 0.3F, 1.0F / (MASTER_RANDOM.nextFloat() * 0.4F + 0.8F));
			regenerationTicksElapsed = 0;
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
		// Tick up
		if (regenerationTicksElapsed >= 0 && regenerationTicksElapsed < REGEN_DURATION) {
			regenerationTicksElapsed++;
			if (regenerationTicksElapsed >= REGEN_DURATION) {
				regenerationTicksElapsed = -1;
			}
		}
	}

	@Inject(method = "isMovementBlocked()Z", at = @At("HEAD"), cancellable = true, remap = false)
	public void isMovementBlocked(CallbackInfoReturnable<Boolean> cir) {
		if (regenerationTicksElapsed > 0) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "readAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("HEAD"), cancellable = true, remap = false)
	public void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
		regensLeft = tag.getInteger("regenerations_left");
		regenerationTicksElapsed = tag.getInteger("regeneration_timer");
	}

	@Inject(method = "addAdditionalSaveData(Lcom/mojang/nbt/CompoundTag;)V", at = @At("HEAD"), cancellable = true, remap = false)
	public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
		tag.putInt("regenerations_left", regensLeft);
		tag.putInt("regeneration_timer", regenerationTicksElapsed);
	}

	@Override
	public int getRegenerationTicksElapsed() {
		return regenerationTicksElapsed;
	}

	@Override
	public int getRegensLeft() {
		return regensLeft;
	}

	@Override
	public void setRegenerationTicksElapsed(int regenerationTicksElapsed) {
		this.regenerationTicksElapsed = regenerationTicksElapsed;
	}

	@Override
	public void setRegensLeft(int regensLeft) {
		this.regensLeft = regensLeft;
	}

	public boolean isRegenerating() {
		return regenerationTicksElapsed > 0;
	}
}
