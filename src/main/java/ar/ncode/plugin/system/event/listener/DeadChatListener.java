package ar.ncode.plugin.system.event.listener;

import ar.ncode.plugin.component.PlayerGameModeInfo;
import ar.ncode.plugin.component.enums.PlayerRole;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Separates chat between alive and dead (spectator) players.
 * - Dead players can only see messages from other dead players
 * - Alive players can only see messages from alive players
 *
 * Closes #8
 */
public class DeadChatListener implements Consumer<PlayerChatEvent> {

    @Override
    public void accept(PlayerChatEvent event) {
        PlayerRef sender = event.getSender();

        // Get sender's reference and check if dead
        Ref<EntityStore> senderRef = sender.getReference();
        if (senderRef == null || !senderRef.isValid()) return;

        PlayerGameModeInfo senderInfo = senderRef.getStore()
            .getComponent(senderRef, PlayerGameModeInfo.componentType);
        if (senderInfo == null) return;

        boolean isSenderDead = PlayerRole.SPECTATOR.equals(senderInfo.getRole());

        // Filter targets to only include players with same alive/dead status
        List<PlayerRef> filteredTargets = event.getTargets().stream()
            .filter(target -> {
                Ref<EntityStore> targetRef = target.getReference();
                if (targetRef == null || !targetRef.isValid()) return false;

                PlayerGameModeInfo targetInfo = targetRef.getStore()
                    .getComponent(targetRef, PlayerGameModeInfo.componentType);
                if (targetInfo == null) return false;

                boolean isTargetDead = PlayerRole.SPECTATOR.equals(targetInfo.getRole());
                return isSenderDead == isTargetDead;
            })
            .collect(Collectors.toList());

        event.setTargets(filteredTargets);

        // Add [DEAD] prefix for dead player messages
        if (isSenderDead) {
            event.setFormatter((playerRef, msg) ->
                Message.join(
                    Message.raw("[DEAD] ").color(0x888888),
                    Message.raw(playerRef.getUsername() + ": " + msg)
                )
            );
        }
    }
}
