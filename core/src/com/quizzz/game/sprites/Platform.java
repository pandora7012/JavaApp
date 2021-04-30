package com.quizzz.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Platform {
    private Texture tex;
    private Vector2 posFlat;
    public Platform(String s, Vector2 pos){
        tex = new Texture(s);
        posFlat = pos;
    }

    public Texture getTex() {
        return tex;
    }

    public void setTex(Texture tex) {
        this.tex = tex;
    }

    public Vector2 getPosFlat() {
        return posFlat;
    }

    public void setPosFlat(Vector2 posFlat) {
        this.posFlat = posFlat;
    }
}
