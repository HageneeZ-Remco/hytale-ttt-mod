package ar.ncode.plugin.model;

import ar.ncode.plugin.component.GraveStoneWithNameplate;
import ar.ncode.plugin.component.enums.RoundState;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static ar.ncode.plugin.TroubleInElfTownGameModePlugin.config;

public class GameModeState {

	public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("mm:ss");
	public RoundState roundState = RoundState.PREPARING;
	public int traitorsAlive = 0;
	public int innocentsAlive = 0;
	public Map<UUID, Integer> karmaUpdates = new HashMap<>();
	public LocalDateTime roundStateUpdatedAt;
	public List<GraveStoneWithNameplate> graveStones = new ArrayList<>();

	public void addGraveStone(GraveStoneWithNameplate graveStone) {
		this.graveStones.add(graveStone);
	}

	public Duration getElapsedTimeSinceRoundUpdate() {
		if (roundStateUpdatedAt == null) {
			return Duration.of(0, ChronoUnit.SECONDS);
		}

		LocalDateTime currentDateTime = LocalDateTime.now();
		return Duration.between(roundStateUpdatedAt, currentDateTime);
	}

	public LocalTime getRoundRemainingTime() {
		Duration roundElapsedTime = getElapsedTimeSinceRoundUpdate();
		long remainingTime = config.get().getRoundDurationInSeconds() - roundElapsedTime.toSeconds();

		long minutes = remainingTime / 60; // Remainder minutes after full hours
		long seconds = remainingTime % 60; // Remainder seconds after full minutes

		if (minutes < 0) {
			minutes = 0;
		}

		if (seconds < 0) {
			seconds = 0;
		}

		return LocalTime.of(0, (int) minutes, (int) seconds);
	}

}
