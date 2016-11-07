package arekkuusu.stratoprism.client.fx;

import arekkuusu.stratoprism.client.proxy.ResourceLocations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT) //Taken from Fire Particle
public class CrystalParticle extends Particle {

	private final float crystalScale;

	public CrystalParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int timelive) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		motionX = motionX * 0.009999999776482582D + xSpeedIn;
		motionY = motionY * 0.009999999776482582D + ySpeedIn;
		motionZ = motionZ * 0.009999999776482582D + zSpeedIn;
		posX += ((rand.nextFloat() - rand.nextFloat()) * 0.05F);
		posY += ((rand.nextFloat() - rand.nextFloat()) * 0.05F);
		posZ += ((rand.nextFloat() - rand.nextFloat()) * 0.05F);
		crystalScale = particleScale;
		particleAlpha = 0.99F;
		particleGravity = 0;
		particleRed = 1F;
		particleGreen = 1F;
		particleBlue = 1F;
		particleMaxAge = timelive;

		TextureAtlasSprite atlasSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(ResourceLocations.TEXTURE_CRYSTAL_PARTICLE.toString());

		setParticleTexture(atlasSprite);
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@Override
	public void moveEntity(double x, double y, double z) {
		this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
		this.resetPositionToBB();
	}

	@Override
	public void renderParticle(VertexBuffer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		float f = (float)(motionX + motionY + motionZ) / particleMaxAge;
		particleScale = crystalScale * (1.0F - f * f * 0.5F);
		super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		float f = (particleAge + p_189214_1_) / particleMaxAge;
		f = MathHelper.clamp_float(f, 0.0F, 1.0F);
		int i = super.getBrightnessForRender(p_189214_1_);
		int j = i & 255;
		int k = i >> 16 & 255;
		j = j + (int) (f * 15.0F * 16.0F);

		if (j > 240) {
			j = 240;
		}

		return j | k << 16;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		if (particleAge++ >= particleMaxAge) {
			setExpired();
		}

		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9599999785423279D;
		motionY *= 0.9599999785423279D;
		motionZ *= 0.9599999785423279D;

		if (isCollided) {
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
	}
}
