package ar.ncode.plugin.system.event.listener;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.RespawnSystems;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import javax.annotation.Nonnull;

public class PlayerRespawnListener extends RespawnSystems.OnRespawnSystem {

	private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

	@Nonnull
	@Override
	public Query<EntityStore> getQuery() {
		return Query.and(PlayerRef.getComponentType(), Player.getComponentType());
	}

	@Override
	public void onComponentRemoved(@NonNullDecl Ref<EntityStore> reference, @NonNullDecl DeathComponent deathComponent, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {

	}
}
