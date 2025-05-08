package paulevs.coloredplanks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MCMath;
import net.minecraft.util.maths.Vec3D;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager.Builder;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;
import net.modificationstation.stationapi.api.world.BlockStateView;

import java.util.ArrayList;

public class ColoredPlanksHalfSlabBlock extends ColoredPlanksSlabBlock {
	private static final float TO_RADIANS = (float) (Math.PI / 180.0);
	private static final float PI = (float) Math.PI;
	
	private Block fullBlock;
	
	public ColoredPlanksHalfSlabBlock(Identifier id, Block source, byte index) {
		super(id, source, index);
		NO_AMBIENT_OCCLUSION[this.id] = true;
	}
	
	@Override
	public void appendProperties(Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(SlabUtil.DIRECTION);
	}
	
	public void setFullBlock(Block fullBlock) {
		this.fullBlock = fullBlock;
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		PlayerEntity player = context.getPlayer();
		if (player == null) return getDefaultState();
		HitResult result = raycast(context.getWorld(), player);
		boolean down = result.pos.y - result.y < 0.5;
		Direction side = context.getSide();
		if (side.getAxis() == Axis.Y) down = side == Direction.UP;
		return getDefaultState().with(SlabUtil.DIRECTION, down ? Direction.DOWN : Direction.UP);
	}
	
	@Override
	public void updateBoundingBox(BlockView view, int x, int y, int z) {
		if (!(view instanceof BlockStateView bsView)) {
			this.setBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
			return;
		}
		
		BlockState state = bsView.getBlockState(x, y, z);
		if (!state.isOf(this)) return;
		Direction facing = state.get(SlabUtil.DIRECTION);
		
		int dx = facing.getOffsetX();
		int dy = facing.getOffsetY();
		int dz = facing.getOffsetZ();
		
		float minX = dx == 0 ? 0 : dx > 0 ? 0.5F : 0;
		float minY = dy == 0 ? 0 : dy > 0 ? 0.5F : 0;
		float minZ = dz == 0 ? 0 : dz > 0 ? 0.5F : 0;
		float maxX = dx == 0 ? 1 : dx > 0 ? 1 : 0.5F;
		float maxY = dy == 0 ? 1 : dy > 0 ? 1 : 0.5F;
		float maxZ = dz == 0 ? 1 : dz > 0 ? 1 : 0.5F;
		
		this.setBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	@Override
	public void doesBoxCollide(Level level, int x, int y, int z, Box box, ArrayList list) {
		updateBoundingBox(level, x, y, z);
		super.doesBoxCollide(level, x, y, z, box, list);
		this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public boolean isFullOpaque() {
		return false;
	}
	
	@Override
	public boolean canUse(Level level, int x, int y, int z, PlayerEntity player) {
		ItemStack stack = player.getHeldItem();
		if (stack == null) return false;
		
		Item item = stack.getType();
		if (!(item instanceof BlockItem blockItem)) return false;
		
		if (blockItem.getBlock() != this) return false;
		
		BlockState state = level.getBlockState(x, y, z);
		if (!state.isOf(this)) return false;
		
		Direction facing = state.get(SlabUtil.DIRECTION);
		
		HitResult hit = raycast(level, player);
		if (hit == null || hit.type != HitType.BLOCK) return false;
		
		double dx = hit.pos.x - x;
		double dy = hit.pos.y - y;
		double dz = hit.pos.z - z;
		
		if (dx <= 0.0F || dx >= 1.0F || dy <= 0.0F || dy >= 1.0F || dz <= 0.0F || dz >= 1.0F) return false;
		
		Axis axis = facing.getAxis();
		
		if (axis == Axis.X && Math.abs(dx - 0.5) > 0.0001) return false;
		if (axis == Axis.Y && Math.abs(dy - 0.5) > 0.0001) return false;
		if (axis == Axis.Z && Math.abs(dz - 0.5) > 0.0001) return false;
		
		level.setBlockState(x, y, z, fullBlock.getDefaultState().with(SlabUtil.AXIS, facing.getAxis()));
		level.playSound(x + 0.5, y + 0.5, z + 0.5, sounds.getWalkSound(), 1.0F, 1.0F);
		level.updateBlock(x, y, z);
		
		if (!isCreative(player)) stack.count--;
		
		return true;
	}
	
	@Override
	@Environment(value = EnvType.CLIENT)
	public boolean isSideRendered(BlockView view, int x, int y, int z, int side) {
		if (!(view instanceof BlockStateView bsView)) {
			return super.isSideRendered(view, x, y, z, side);
		}
		
		Direction face = Direction.byId(side);
		BlockState selfState = bsView.getBlockState(x, y, z);
		
		if (selfState.getBlock() instanceof ColoredPlanksHalfSlabBlock) {
			Direction selfDir = selfState.get(SlabUtil.DIRECTION);
			if (face == selfDir || face == selfDir.getOpposite()) {
				return super.isSideRendered(view, x, y, z, side);
			}
		}
		
		BlockState sideState = bsView.getBlockState(x - face.getOffsetX(), y - face.getOffsetY(), z - face.getOffsetZ());
		
		if (sideState.getBlock() instanceof ColoredPlanksHalfSlabBlock && selfState.getBlock() instanceof ColoredPlanksHalfSlabBlock) {
			Direction slab2 = selfState.get(SlabUtil.DIRECTION);
			Direction slab1 = sideState.get(SlabUtil.DIRECTION);
			return slab1 != slab2;
		}
		
		return super.isSideRendered(view, x, y, z, side);
	}
	
	// Item Bounding Box
	@Override
	@Environment(EnvType.CLIENT)
	public void updateRenderBounds() {
		this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}
	
	private static boolean isCreative(PlayerEntity player) {
		if (!FabricLoader.getInstance().isModLoaded("bhcreative")) return false;
		return player.creative_isCreative();
	}
	
	private static HitResult raycast(Level level, PlayerEntity player) {
		double dist = 5.0;
		float pitch = player.prevPitch + (player.pitch - player.prevPitch);
		
		double x = player.prevX + (player.x - player.prevX);
		double y = player.prevY + (player.y - player.prevY) + 1.62 - player.standingEyeHeight;
		double z = player.prevZ + (player.z - player.prevZ);
		Vec3D pos = Vec3D.getFromCacheAndSet(x, y, z);
		
		float yaw = player.prevYaw + (player.yaw - player.prevYaw);
		yaw = -yaw * TO_RADIANS - PI;
		float cosYaw = MCMath.cos(yaw);
		float sinYaw = MCMath.sin(yaw);
		float cosPitch = -MCMath.cos(-pitch * TO_RADIANS);
		
		Vec3D dir = pos.add(
			sinYaw * cosPitch * dist,
			MCMath.sin(-pitch * TO_RADIANS) * dist,
			cosYaw * cosPitch * dist
		);
		
		return level.getHitResult(pos, dir, false);
	}
}

