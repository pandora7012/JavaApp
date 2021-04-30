package com.quizzz.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.quizzz.game.states.PlayState;

public class Player {
    private Texture tex ;
    private Animation playeranim ;
    private Vector2 position ;
    private Vector2 velocity;
    private int GRAVITY ;
    public Boolean isFlying;


    private Sound jp;
    public static final int IDLE_STATE = 0;
    public static final int JUMP_STATE = 1;
    public static final int FALL_STATE = 2;
    public static final int CROUNCH_STATE = 3 ;

    public static final int LEFT = 0 ;
    public static final int RIGHT = 1 ;

    int state ;
    int direction ;
    public boolean hold;
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Player(int x, int y){
        tex = new Texture("player-idle.png");
        position = new Vector2(x,y) ;
        velocity = new Vector2(0,0) ;
        isFlying = false ;
        playeranim = new Animation(new TextureRegion(tex), 4,0.5f,true);
        state = IDLE_STATE ;
        hold = false;
        direction = RIGHT ;
        jp = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float deltaTime){
        velocity.add(0,GRAVITY);
        velocity.scl(deltaTime);
        position.add(velocity.x, velocity.y);
        velocity.scl(1/deltaTime);
        playeranim.update(deltaTime);
        switchState();
    }


    public Animation getPlayeranim() {
        return playeranim;
    }

    public void setPlayeranim(Animation playeranim) {
        this.playeranim = playeranim;
    }

    public Boolean getFlying() {
        return isFlying;
    }

    public void setFlying(Boolean flying) {
        isFlying = flying;
    }

    public TextureRegion getTex() {
        return playeranim.getFrame();
    }

    public void setTex(Texture tex) {
        this.tex = tex;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public int getGRAVITY() {
        return GRAVITY;
    }

    public void setGRAVITY(int GRAVITY) {
        this.GRAVITY = GRAVITY;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isHold() {
        return hold;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    public void switchState(){
        if ( velocity.y < 0 && position.y > 50){
            state = FALL_STATE;
        }
        else if (velocity.y > 0 ){
            state = JUMP_STATE;
        }
        else if (hold == true){
            state = CROUNCH_STATE;
        }
        else state = IDLE_STATE;
        switch (state){
            case 0:
                tex = new Texture("player-idle.png");
                playeranim = new Animation(new TextureRegion(tex),4,0.5f,true);
                break;
            case 1:
                tex = new Texture("player-jump-1.png");
                playeranim = new Animation(new TextureRegion(tex),1,1,true);
                break;
            case 2:
                tex = new Texture("player-jump-2.png");
                playeranim = new Animation(new TextureRegion(tex),1,1,true);
                break;
            case 3 :
                tex = new Texture("player_croucht.png");
                playeranim = new Animation(new TextureRegion(tex),2,0.75f,true);
                break;
        }
    }

    public void jump(Vector2 dire){
        Vector2 newVT = new Vector2(dire.x - position.x   , dire.y - position.y);
        float d = (float) Math.sqrt( (dire.x-position.x) * (dire.x-position.x) + (dire.x-position.y) * (dire.x-position.y));
//        newVT.x/=d;
//        newVT.y/=d;
//
        velocity.y =   1000 ;
        velocity.x  = newVT.x;
        System.out.println(newVT);
        if ( dire.x < position.x && direction == RIGHT ) {
            playeranim.flip();
            direction = LEFT ;
        }
        else if ( dire.x >= position.x && direction == LEFT){
            playeranim.flip();
            direction = RIGHT;
        }
        jp.play(0.2f);
    }

}
