package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GYROmain extends ApplicationAdapter {

    OrthographicCamera ocCam;
    public Rectangle recBDown, recBUp, recBLeft, recBRight;
    SpriteBatch bChar;
    private Character character;
    int picID = 1;
    float fGyroY;
    BitmapFont bmGyroV;

    @Override
    public void create() {
        bmGyroV = new BitmapFont(Gdx.files.internal("luke.fnt"));
        bmGyroV.setColor(Color.WHITE);
        bmGyroV.getData().setScale(5, 5);
        ocCam = new OrthographicCamera();
        ocCam.setToOrtho(false);
        bChar = new SpriteBatch();
        character = new Character();
        character.setPosition(200, 100);
        System.out.println(Gdx.graphics.getWidth() + "" + Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        //https://github.com/libgdx/libgdx/wiki/Accelerometer
        fGyroY = Gdx.input.getAccelerometerY();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        bChar.setProjectionMatrix(ocCam.combined);
        bChar.begin();
        if (picID == 1) {
            character.draw1(bChar);
        } else {
            character.draw2(bChar);
        }
        bmGyroV.draw(bChar, Float.toString(fGyroY), 1000, 1300);
        bChar.end();

        recBDown = new Rectangle(0, 0, Gdx.graphics.getWidth(), 10);
        recBUp = new Rectangle(0, Gdx.graphics.getHeight() - 10, Gdx.graphics.getWidth(), 10);
        recBLeft = new Rectangle(0, 0, 10, Gdx.graphics.getHeight());
        recBRight = new Rectangle(Gdx.graphics.getWidth() - 10, 0, 10, Gdx.graphics.getHeight());

        //Updates
        character.update(Gdx.graphics.getDeltaTime());

        //Boundaries
        if (character.bounds(recBDown) == 1) {
            character.action(1, 0, 10);
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                character.jump();
            }
        }

        if (character.bounds(recBUp) == 1) {
            character.action(4, 0, Gdx.graphics.getHeight() - 10);
        }

        if (character.bounds(recBLeft) == 1) {
            character.action(2, 10, 0);
        }

        if (character.bounds(recBRight) == 1) {
            character.action(3, Gdx.graphics.getWidth() - 10, 0);
        }

        //Gyro Controls
        if (fGyroY < -1.5) {
            character.moveLeft(Gdx.graphics.getDeltaTime());
            if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1)) {
            } else {
                picID = 2;
            }
        }
        if (fGyroY > 1.5) {
            character.moveRight(Gdx.graphics.getDeltaTime());
            if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1)) {
            } else {
                picID = 1;
            }


            //ANDROID CONTROLS
            /*		if (Gdx.input.isTouched(0) && Gdx.input.getX(0) < Gdx.graphics.getWidth() / 2
                || Gdx.input.isTouched(1) && Gdx.input.getX(1) < Gdx.graphics.getWidth() / 2) {
			character.moveLeft(Gdx.graphics.getDeltaTime());
			if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1)) {
			} else {
				picID = 2;
			}
		}
		if (Gdx.input.isTouched(0) && Gdx.input.getX(0) > Gdx.graphics.getWidth() / 2
				|| Gdx.input.isTouched(1) && Gdx.input.getX(1) > Gdx.graphics.getWidth() / 2) {
			character.moveRight(Gdx.graphics.getDeltaTime());
			if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1)) {
			} else {
				picID = 1;
			}
		}
		*/


        }
    }

    @Override
    public void dispose() {
        bChar.dispose();
    }
}