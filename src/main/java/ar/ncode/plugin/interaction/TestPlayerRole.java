package ar.ncode.plugin.interaction;

import ar.ncode.plugin.accessors.PlayerAccessors;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.spatial.SpatialResource;
import com.hypixel.hytale.component.spatial.SpatialStructure;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.BlockPosition;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class TestPlayerRole extends SimpleInstantInteraction {

	public static final BuilderCodec<TestPlayerRole> CODEC = BuilderCodec.builder(
			TestPlayerRole.class, TestPlayerRole::new, SimpleInstantInteraction.CODEC
	).build();

	private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	@Override
	protected void firstRun(
			@NonNullDecl InteractionType interactionType, @NonNullDecl InteractionContext ctx,
			@NonNullDecl CooldownHandler cooldownHandler
	) {
		CommandBuffer<EntityStore> commandBuffer = ctx.getCommandBuffer();
		if (commandBuffer == null) {
			ctx.getState().state = InteractionState.Failed;
			LOGGER.atInfo().log("CommandBuffer is null");
			return;
		}

		BlockPosition targetBlock = ctx.getTargetBlock();
		if (targetBlock == null) {
			ctx.getState().state = InteractionState.Failed;
			return;
		}

		Vector3d blockPosition = new Vector3d(targetBlock.x, targetBlock.y, targetBlock.z);


		SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource =
				commandBuffer.getResource(EntityModule.get().getPlayerSpatialResourceType());
		SpatialStructure<Ref<EntityStore>> spatialStructure = playerSpatialResource.getSpatialStructure();

		Ref<EntityStore> closest = spatialStructure.closest(blockPosition);
		if (closest == null || !closest.isValid()) {
			return;
		}

		World world = commandBuffer.getExternalData().getWorld();
		world.execute(() -> {
			var transformComponent = commandBuffer.getComponent(closest, TransformComponent.getComponentType());
			if (transformComponent == null) {
				return;
			}

			var player = PlayerAccessors.getPlayerFrom(closest);
			if (player.isEmpty()) {
				return;
			}

			var distance = blockPosition.distanceTo(transformComponent.getPosition());
			player.get().component().sendMessage(Message.raw("Distance: " + distance));
			if (distance > 1.1) {
				return;
			}


			player.get().component().sendMessage(Message.raw(player.get().info().getCurrentRoundRole().getId()));
		});
	}
}
