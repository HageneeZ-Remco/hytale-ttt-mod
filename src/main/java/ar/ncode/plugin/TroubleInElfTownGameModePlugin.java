package ar.ncode.plugin;

import ar.ncode.plugin.commands.Shop;
import ar.ncode.plugin.commands.SpectatorMode;
import ar.ncode.plugin.component.GraveStoneWithNameplate;
import ar.ncode.plugin.component.PlayerGameModeInfo;
import ar.ncode.plugin.component.death.ConfirmedDeath;
import ar.ncode.plugin.component.death.LostInCombat;
import ar.ncode.plugin.config.CustomConfig;
import ar.ncode.plugin.interaction.ShowDeadPlayerInteraction;
import ar.ncode.plugin.model.GameModeState;
import ar.ncode.plugin.packet.filter.GuiPacketsFilter;
import ar.ncode.plugin.system.event.FinishCurrentRoundEvent;
import ar.ncode.plugin.system.event.StartNewRoundEvent;
import ar.ncode.plugin.system.event.handler.FinishCurrentRoundEventHandler;
import ar.ncode.plugin.system.event.handler.StartNewRoundEventHandler;
import ar.ncode.plugin.system.event.listener.*;
import ar.ncode.plugin.system.scheduled.DoubleTapDetector;
import ar.ncode.plugin.system.scheduled.PlayerHudUpdateSystem;
import ar.ncode.plugin.system.scheduled.WorldRoundTimeSystem;
import com.hypixel.hytale.event.EventRegistration;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.registry.Registration;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.command.system.CommandRegistration;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.io.adapter.PacketFilter;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.util.Config;

import javax.annotation.Nonnull;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This class serves as the entrypoint for your plugin. Use the setup method to register into game registries or add
 * event listeners.
 */
public class TroubleInElfTownGameModePlugin extends JavaPlugin {

	private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
	public static TroubleInElfTownGameModePlugin instance;
	public static Map<UUID, GameModeState> gameModeStateForWorld = new HashMap<>();
	public static Config<CustomConfig> config;

	@SuppressWarnings("rawtypes")
	private List<EventRegistration> events = new ArrayList<>();
	private List<CommandRegistration> commands = new ArrayList<>();
	private List<PacketFilter> inboundPacketFilters = new ArrayList<>();

	public TroubleInElfTownGameModePlugin(@Nonnull JavaPluginInit init) {
		super(init);
		instance = this;
		config = this.withConfig("config", CustomConfig.CODEC);
		LOGGER.atInfo().log("Starting plugin: " + this.getName() + " - version " + this.getManifest().getVersion().toString());
	}

	@Override
	protected void setup() {
		Path configFilePath = Paths.get(getDataDirectory().toString(), "/config.json");
		if (!Files.exists(getDataDirectory()) || !Files.exists(configFilePath)) {
			config.save().thenRun(() -> LOGGER.atInfo().log("Saved default config"));
		}

		config.load().thenRun(() -> LOGGER.atInfo().log("Config loaded."));

		PlayerGameModeInfo.componentType = getEntityStoreRegistry().registerComponent(PlayerGameModeInfo.class, "PlayerGameModeInfo", PlayerGameModeInfo.CODEC);
		GraveStoneWithNameplate.componentType = getChunkStoreRegistry().registerComponent(GraveStoneWithNameplate.class,
				"GraveStoneWithNameplate", GraveStoneWithNameplate.CODEC);
		ConfirmedDeath.componentType = getEntityStoreRegistry().registerComponent(ConfirmedDeath.class, "ConfirmedDeath",
				ConfirmedDeath.CODEC);
		LostInCombat.componentType = getEntityStoreRegistry().registerComponent(LostInCombat.class, "LostInCombat",
				LostInCombat.CODEC);

		events.add(getEventRegistry().registerGlobal(PlayerReadyEvent.class, new PlayerReadyEventListener()));
		events.add(getEventRegistry().registerGlobal(PlayerDisconnectEvent.class, new PlayerDisconnectEventListener()));
		events.add(getEventRegistry().registerGlobal(StartNewRoundEvent.class, new StartNewRoundEventHandler()));
		events.add(getEventRegistry().registerGlobal(FinishCurrentRoundEvent.class, new FinishCurrentRoundEventHandler()));

		commands.add(getCommandRegistry().registerCommand(new ExampleCommand(this.getName(), this.getManifest().getVersion().toString())));
		commands.add(getCommandRegistry().registerCommand(new SpectatorMode()));
		commands.add(getCommandRegistry().registerCommand(new Shop()));

		getCodecRegistry(Interaction.CODEC)
				.register("show_dead_player_info", ShowDeadPlayerInteraction.class, ShowDeadPlayerInteraction.CODEC);

		HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(() -> {
			try {
				DoubleTapDetector.getInstance().tick();

			} catch (Exception e) {
				LOGGER.atWarning().log("Error in double-tap detector: " + e.getMessage());
			}

		}, 100L, 50L, TimeUnit.MILLISECONDS);

		LOGGER.atInfo().log("Plugin " + this.getName() + " setup completed!");
	}

	@Override
	protected void start() {
		getEntityStoreRegistry().registerSystem(new SpectatorModeDamageListener());
		getEntityStoreRegistry().registerSystem(new PlayerDeathListener());
		getEntityStoreRegistry().registerSystem(new PlayerRespawnListener());
		getEntityStoreRegistry().registerSystem(new PlayerHudUpdateSystem());
		getEntityStoreRegistry().registerSystem(new WorldRoundTimeSystem());

		inboundPacketFilters.add(PacketAdapters.registerInbound(new GuiPacketsFilter()));

		Universe.get().getWorlds().forEach((s, world) -> {
			gameModeStateForWorld.put(world.getWorldConfig().getUuid(), new GameModeState());
		});

		LOGGER.atInfo().log("Plugin started!");
	}

	@Override
	protected void shutdown() {
		inboundPacketFilters.forEach(PacketAdapters::deregisterInbound);
		commands.forEach(Registration::unregister);
		events.forEach(Registration::unregister);
		LOGGER.atInfo().log("Plugin shutting down!");
	}

}