package com.quizzz.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.quizzz.game.quizzz;

import java.util.Random;

public class MenuState extends State{
    private Texture background;
    private BitmapFont font;
    String text ;
    private BitmapFont tt1 ;
    private BitmapFont tt2;
    private Random rand;



    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bgplay.png") ;
        font = new BitmapFont(Gdx.files.internal("font.fnt")) ;
        text = "TOUCH TO PLAY";
        tt1 = new BitmapFont(Gdx.files.internal("fontas1.fnt"));
        tt2 = new BitmapFont(Gdx.files.internal("fontas2.fnt"));
        cam.setToOrtho(false, quizzz.WIDTH, quizzz.HEIGHT);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm)) ;
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,0,0, quizzz.WIDTH,quizzz.HEIGHT);
        font.draw(sb, text, quizzz.WIDTH/2 -70,105);
        tt2.draw(sb,"QUIZZZ", 40,600);
        tt1.draw(sb,"P1: Learn to fly",quizzz.WIDTH/2-130 ,500 );
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
    }
}
