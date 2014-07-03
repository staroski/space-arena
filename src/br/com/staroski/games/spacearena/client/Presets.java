package br.com.staroski.games.spacearena.client;

import br.com.staroski.games.*;
import br.com.staroski.games.spacearena.*;

final class Presets {

	public void execute() {
		RendererMapper mapper = RendererMapper.get();
		mapper.put(SpaceArena.STAGE_01.getId(), new STAGE_01_Renderer());
		mapper.put(SpaceArena.PLAYER_01.getId(), new PLAYER_01_Renderer());
		mapper.put(SpaceArena.BULLET_01.getId(), new BULLET_01_Renderer());
		mapper.put(SpaceArena.ENERGY_ITEM_01.getId(), new ENERGY_ITEM_01_Renderer());
	}
}
