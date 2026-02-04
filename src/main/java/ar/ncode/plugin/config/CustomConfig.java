package ar.ncode.plugin.config;

import ar.ncode.plugin.model.TranslationKey;
import ar.ncode.plugin.model.enums.RoleGroup;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomConfig {

	public static final BuilderCodec<CustomConfig> CODEC =
			BuilderCodec.builder(CustomConfig.class, CustomConfig::new)
					.append(new KeyedCodec<>("RequiredPlayersToStartRound", Codec.INTEGER),
							(config, value, _) -> config.requiredPlayersToStartRound = value,
							(config, _) -> config.requiredPlayersToStartRound)
					.add()
					.append(new KeyedCodec<>("TimeBeforeRoundInSeconds", Codec.INTEGER),
							(config, value, _) -> config.timeBeforeRoundInSeconds = value,
							(config, _) -> config.timeBeforeRoundInSeconds)
					.add()
					.append(new KeyedCodec<>("RoundDurationInSeconds", Codec.INTEGER),
							(config, value, _) -> config.roundDurationInSeconds = value,
							(config, _) -> config.roundDurationInSeconds)
					.add()
					.append(new KeyedCodec<>("TimeAfterRoundInSeconds", Codec.INTEGER),
							(config, value, _) -> config.timeAfterRoundInSeconds = value,
							(config, _) -> config.timeAfterRoundInSeconds)
					.add()
					.append(new KeyedCodec<>("TimeToVoteMapInSeconds", Codec.INTEGER),
							(config, value, _) -> config.timeToVoteMapInSeconds = value,
							(config, _) -> config.timeToVoteMapInSeconds)
					.add()
					.append(new KeyedCodec<>("TimeBeforeChangingMapInSeconds", Codec.INTEGER),
							(config, value, _) -> config.timeBeforeChangingMapInSeconds = value,
							(config, _) -> config.timeBeforeChangingMapInSeconds)
					.add()
					.append(new KeyedCodec<>("KarmaStartingValue", Codec.INTEGER),
							(config, value, _) -> config.karmaStartingValue = value,
							(config, _) -> config.karmaStartingValue)
					.add()
					.append(new KeyedCodec<>("KarmaForDisconnectingMiddleRound", Codec.INTEGER),
							(config, value, _) -> config.karmaForDisconnectingMiddleRound = value,
							(config, _) -> config.karmaForDisconnectingMiddleRound)
					.add()
					.append(new KeyedCodec<>("KaramPointsForKillingSameRoleGroup", Codec.INTEGER),
							(config, value, _) -> config.karamPointsForKillingSameRoleGroup = value,
							(config, _) -> config.karamPointsForKillingSameRoleGroup)
					.add()
					.append(new KeyedCodec<>("KaramPointsForKillingOppositeRoleGroup", Codec.INTEGER),
							(config, value, _) -> config.karamPointsForKillingOppositeRoleGroup = value,
							(config, _) -> config.karamPointsForKillingOppositeRoleGroup)
					.add()
					.append(new KeyedCodec<>("PlayerGraveId", Codec.STRING),
							(config, value, _) -> config.playerGraveId = value,
							(config, _) -> config.playerGraveId)
					.add()
					.append(new KeyedCodec<>("LootBoxBlockId", Codec.STRING),
							(config, value, _) -> config.lootBoxBlockId = value,
							(config, _) -> config.lootBoxBlockId)
					.add()
					.append(new KeyedCodec<>("RoundsPerMap", Codec.INTEGER),
							(config, value, _) -> config.roundsPerMap = value,
							(config, _) -> config.roundsPerMap)
					.add()
					.append(new KeyedCodec<>("MapsInARowForVoting", Codec.INTEGER),
							(config, value, _) -> config.mapsInARowForVoting = value,
							(config, _) -> config.mapsInARowForVoting)
					.add()
					.append(new KeyedCodec<>("WorldTemplatesFolder", Codec.STRING),
							(config, value, _) -> config.worldTemplatesFolder = value,
							(config, _) -> config.worldTemplatesFolder)
					.add()
					.append(new KeyedCodec<>("Roles", ArrayCodec.ofBuilderCodec(CustomRole.CODEC, CustomRole[]::new)),
							(config, value, _) -> config.roles = value,
							(config, _) -> config.roles)
					.add()
					.build();

	private final int itemsInARowForTheShop = 5;
	// Sets required amount of players to start a round
	private int requiredPlayersToStartRound = 3;
	// Time in seconds before the round starts
	private int timeBeforeRoundInSeconds = 10;
	private int roundDurationInSeconds = 10 * 60;
	private int timeAfterRoundInSeconds = 5;
	private int timeToVoteMapInSeconds = 30;
	private int timeBeforeChangingMapInSeconds = 5;
	// Sets the starting value for each component's karma
	private int karmaStartingValue = 1000;
	private int karmaForDisconnectingMiddleRound = -100;
	private int karamPointsForKillingOppositeRoleGroup = 10;
	private int karamPointsForKillingSameRoleGroup = -10;

	private CustomRole[] roles = new CustomRole[]{
			CustomRole.builder()
					.id("detective")
					.translationKey(TranslationKey.getWithPrefix("hud_current_role_detective"))
					.customGuiColor("#1F5CC4")
					.minimumAssignedPlayersWithRole(0)
					.ratio(11)
					.startingCredits(1)
					.secretRole(false)
					.roleGroup(RoleGroup.INNOCENT)
					.storeItems(new String[]{
							"Weapon_Staff_Frost:1",
							"Weapon_Deployable_Healing_Totem:1"
					})
					.build(),
			CustomRole.builder()
					.id("innocent")
					.translationKey(TranslationKey.getWithPrefix("hud_current_role_innocent"))
					.minimumAssignedPlayersWithRole(1)
					.ratio(1)
					.secretRole(true)
					.roleGroup(RoleGroup.INNOCENT)
					.build(),
			CustomRole.builder()
					.id("traitor")
					.translationKey(TranslationKey.getWithPrefix("hud_current_role_traitor"))
					.minimumAssignedPlayersWithRole(1)
					.ratio(4)
					.startingCredits(1)
					.secretRole(true)
					.roleGroup(RoleGroup.TRAITOR)
					.storeItems(new String[]{
							"Weapon_Daggers_Doomed:1"
					})
					.build()

	};
	private String playerGraveId = "Player_Grave";
	private String lootBoxBlockId = "Furniture_Human_Ruins_Chest_Small";
	private int roundsPerMap = 8;
	private int mapsInARowForVoting = 3;
	private String worldTemplatesFolder = "universe/templates";

	public static List<ItemStack> parseItemEntry(String line) {
		if (line == null || line.isBlank()) {
			throw new IllegalArgumentException("Store entry cannot be null or blank");
		}

		List<ItemStack> items = new ArrayList<>();

		String[] itemTokens = line.split("\\|");
		for (String token : itemTokens) {
			if (token.isBlank()) continue;

			String[] parts = token.split(":", 2);
			String itemId = parts[0].trim();

			int amount = 1;
			if (parts.length == 2) {
				try {
					amount = Integer.parseInt(parts[1].trim());
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
							"Invalid amount in store entry: '" + token + "'"
					);
				}
			}

			items.add(new ItemStack(itemId, amount));
		}

		if (items.isEmpty()) {
			throw new IllegalArgumentException("Store entry produced no items: " + line);
		}

		return items;
	}

	public List<List<ItemStack>> getItemsGroups(String[] configuredValues) {
		List<List<ItemStack>> stacks = new ArrayList<>();

		if (configuredValues == null) {
			return stacks;
		}

		for (String configuredValue : configuredValues) {
			stacks.add(parseItemEntry(configuredValue));
		}

		return stacks;
	}


	public List<ItemStack> getItems(String[] configuredValues) {
		List<ItemStack> stacks = new ArrayList<>();

		if (configuredValues == null) {
			return stacks;
		}

		for (String configuredValue : configuredValues) {
			stacks.addAll(parseItemEntry(configuredValue));
		}

		return stacks;
	}

}
