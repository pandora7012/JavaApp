package com.quizzz.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.quizzz.game.quizzz;
import com.quizzz.game.sprites.Platform;
import com.quizzz.game.sprites.Player;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import javax.xml.bind.SchemaOutputResolver;

import static java.lang.Double.max;


public class PlayState extends State {

    private Player player;
    private Texture bg ;
    private static int GRAV = - 15 ;
    private Texture cloud ;
    private List<Platform> platforms ;
    private Random rand ;
    private BitmapFont font ;
    int core ;
    int gap;

    private static final int NORMAL_STATE = 0 ;
    private static final int LOCKED_DIREC_STATE = 1;
    private static final int JUMP_STATE = 2;
    public int state ;
    public Vector2 dire;
    public float POWER;
    public boolean isJumping ;
    public String score;

    private Texture target;

    private Music music;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        player = new Player(150,40) ;
        bg = new Texture("bg.png");
        platforms = new ArrayList<Platform>();

        music = Gdx.audio.newMusic(Gdx.files.internal("bg_mu.mp3"));
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
        core = 0 ;
        score = "0" + " m" ;
        font = new BitmapFont(Gdx.files.internal("score.fnt"));
        cam.setToOrtho(false, quizzz.WIDTH, quizzz.HEIGHT);
        rand = new Random();
        state = NORMAL_STATE ;
        gap = 150 ;
        for (int i = 0 ; i < 10 ; i++){
            platforms.add( new Platform( "log.png", new Vector2(rand.nextInt(400), gap)));
            gap += 200;
        }
        dire = new Vector2() ;
        POWER = 0 ;
        target = new Texture("target.png");
        isJumping = false ;
        int score = 0 ;
    }

    int addPower = 1;

    @Override
    protected void handleInput() {
        if ( Gdx.input.isTouched()) {
            dire.x = Gdx.input.getX() ;
            if (cam.position.y < 400)
                dire.y = 800 - Gdx.input.getY();
            else dire.y = 800 - Gdx.input.getY() + cam.position.y -400;
            player.jump(dire);
            System.out.println(dire);
            System.out.println(player.getPosition());
            System.out.println(player.getVelocity() );
            System.out.println(" ");
        }
        else dire = new Vector2(0,0);
    }

    @Override
    public void update(float deltaTime) {
        // player handle and input things
        platformsGenerator();
        if (player.getPosition().y >= 5 && player.getPosition().y <= 1600){
            GRAV = -30;
        }
        else if (player.getPosition().y > 1600 && player.getPosition().y <= 4400){
            GRAV = -25 ;
        }
        else if (player.getPosition().y > 4400 ){
            GRAV = -20 ;
        }
        core = (int) Math.max(core, player.getPosition().y);
        score = Integer.toString(core) + " m" ;
        player.setGRAVITY(GRAV);
        player.update(deltaTime);
        checkifCollitionWithPlatform();
        if (isJumping == false ) {
            handleInput();
            isJumping= true;
        }
        gameOver();
    // debut


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        cam.position.set(cam.viewportWidth/2,Math.max(player.getPosition().y,cam.position.y),0) ;
        if ( cam.position.y < 400 ) cam.position.y = 400 ;
        cam.update();
        sb.begin();
        sb.draw(bg,0,0);
        font.draw(sb,score, cam.viewportWidth/2 , cam.position.y + 350);
        for ( Platform i : platforms){
            sb.draw(i.getTex(),i.getPosFlat().x , i.getPosFlat().y,96,16);
        }
        sb.draw(player.getTex(),player.getPosition().x,player.getPosition().y,66,64);
            sb.draw(target, dire.x , dire.y );
        sb.end();
        dispose();
    }

    @Override
    public void dispose() {

    }

    public void platformsGenerator(){
        for ( Platform i : platforms){
            if (i.getPosFlat().y < cam.position.y -500){
                i.setPosFlat(new Vector2(rand.nextInt(400), gap));
                gap+=200;
                if (i.getPosFlat().y <= 1300) i.setTex(new Texture("log.png"));
                else if (i.getPosFlat().y > 1300 && i.getPosFlat().y <=4400) i.setTex(new Texture("cloud.png"));
                else i.setTex(new Texture("space.png"));
            }
        }
    }

    public void checkifCollitionWithPlatform(){
        if (player.getPosition().y <= 40){
            player.setPosition(new Vector2(player.getPosition().x, 40));
            isJumping = false ;
            player.getVelocity().x = 0 ;
        }

        for ( Platform i: platforms){
            if (player.getPosition().x < i.getPosFlat().x+80 &&
                    player.getPosition().x + 32 > i.getPosFlat().x-20 &&
                        player.getPosition().y - i.getPosFlat().y <=20 &&
                            player.getPosition().y - i.getPosFlat().y >= 10 &&
                                player.getVelocity().y < 20
            )
            {
               player.setVelocity( new Vector2(0, GRAV * -1));
               isJumping = false;
            }
        }

    }

    public void gameOver(){
            if (player.getPosition().x < 0 ){
                player.getPosition().x = 450;
                player.getPosition().y = 200;
                cam.position.set(cam.viewportWidth/2,0,0) ;
                gap = 150 ;
                for ( Platform i : platforms){
                    i.setPosFlat(new Vector2(rand.nextInt(400), gap));
                    gap+=200;
                    i.setTex(new Texture("log.png"));
                }
                core = 0 ;
            }
            else if (player.getPosition().x > 450){
                player.getPosition().x = 0 ;
                player.getPosition().y = 200;
                cam.position.set(cam.viewportWidth/2,0,0) ;
                gap =150 ;
                for ( Platform i : platforms){
                    i.setPosFlat(new Vector2(rand.nextInt(400), gap));
                    i.setTex(new Texture("log.png"));
                    gap+=200;
                }
                core = 0 ;
            }
            else if (player.getPosition().y < cam.position.y-400 ){
                player.getPosition().y = 100;
                cam.position.set(cam.viewportWidth/2,0,0) ;
                gap= 150;
                for ( Platform i : platforms){
                    i.setPosFlat(new Vector2(rand.nextInt(400), gap));
                    gap+=200;
                    i.setTex(new Texture("log.png"));
                }
                core = 0 ;
            }
    }
}
