package com.game15.game.states;

import java.awt.Color;
import java.awt.Graphics;

import com.game15.game.Game;
import com.game15.game.inputs.Button;

public class SettingState extends State {
	
	private Button back;

	public SettingState(Game game) {
		super(game);
		
		init();
	}
	
	private void init() {
		int sizeX = 80, sizeY = 40;
		
		back = new Button(game,"Back", game.width()-sizeX, 0, sizeX,sizeY);
		back.offSetX(-sizeX/4+4);
	}

	@Override
	public void tick() {
		back.tick();
		if (back.isClicked()) {
			game.chgState();
		}
		else if (back.isHovered())
			back.setColor(Color.orange, Color.black);
		else
			back.setColor(null, Color.black);
	}

	@Override
	public void render(Graphics g) {
		back.render(g);
	}

}
