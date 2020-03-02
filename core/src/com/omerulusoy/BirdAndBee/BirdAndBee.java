package com.omerulusoy.BirdAndBee;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class BirdAndBee extends ApplicationAdapter {
	Random random;

	SpriteBatch batch;
	Texture background;
	BitmapFont font;
	BitmapFont font2;

	Circle birdCircle;
	Circle[] enemyCircle;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;
	Circle[] enemyCircle4;
	Circle[] enemyCircle5;
	Circle[] enemyCircle6;

	int gameState = 0;
	int numberOfEnemies = 6;
	int score = 0;
	int scoredEnemy = 0;

	float birdX;
	float birdY;
	float velocity = 0;
	float gravity = 0.3f;
	float elapsed = 0;
	float[] enemyX = new float[numberOfEnemies];
	float distance = 0;
	float birdWidth = 0;
	float enemyVelocity = 0;
	float[] enemyOffSet = new float[numberOfEnemies];
	float[] enemyOffSet2 = new float[numberOfEnemies];
	float[] enemyOffSet3 = new float[numberOfEnemies];
	float[] enemyOffSet4 = new float[numberOfEnemies];
	float[] enemyOffSet5 = new float[numberOfEnemies];
	float[] enemyOffSet6 = new float[numberOfEnemies];

	Animation<TextureRegion> bird;
	Animation<TextureRegion> ground;
	Animation<TextureRegion> bee1;
	Animation<TextureRegion> bee2;
	Animation<TextureRegion> bee3;
	Animation<TextureRegion> bee4;
	Animation<TextureRegion> bee5;
	Animation<TextureRegion> bee6;

	@Override
	public void create () {
		random = new Random();
		batch = new SpriteBatch();
		background = new Texture("background.png");
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);
		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(4);

		bird = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gbird.gif").read());
		ground = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gGround.gif").read());
		bee1 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gbee.gif").read());
		bee2 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gbee2.gif").read());
		bee3 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gbee.gif").read());
		bee4 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gbee2.gif").read());
		bee5 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gbee.gif").read());
		bee6 = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("gbee2.gif").read());

		birdX = Gdx.graphics.getWidth()/100*10;
		birdY = Gdx.graphics.getHeight()/2;
		birdCircle = new Circle();

		enemyCircle = new Circle[numberOfEnemies];
		enemyCircle2 = new Circle[numberOfEnemies];
		enemyCircle3 = new Circle[numberOfEnemies];
		enemyCircle4 = new Circle[numberOfEnemies];
		enemyCircle5 = new Circle[numberOfEnemies];
		enemyCircle6 = new Circle[numberOfEnemies];

		distance = Gdx.graphics.getWidth() / 2;
		birdWidth = (Gdx.graphics.getHeight()/14);
		enemyVelocity = Gdx.graphics.getWidth()/200;

		for (int i = 0 ; i<numberOfEnemies; i++){

			enemyOffSet[i] = (random.nextFloat() -0.5f )* Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
			enemyOffSet2[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
			enemyOffSet3[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
			enemyOffSet4[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
			enemyOffSet5[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
			enemyOffSet6[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
			enemyX[i] = Gdx.graphics.getWidth() - birdWidth/2 + i*distance;
			enemyCircle[i] = new Circle();
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();
			enemyCircle4[i] = new Circle();
			enemyCircle5[i] = new Circle();
			enemyCircle6[i] = new Circle();
		}
	}

	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(ground.getKeyFrame(elapsed), 0 , 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		if (gameState == 1){
			if (enemyX[scoredEnemy] < birdX){
				score++;
				if (scoredEnemy < numberOfEnemies - 1){
					scoredEnemy++;
				}
				else{
					scoredEnemy = 0;
				}
			}

			if (Gdx.input.justTouched()){
				velocity = -(Gdx.graphics.getWidth()/100)+(Gdx.graphics.getWidth()/700);
			}
			for (int i = 0; i < numberOfEnemies; i++){
				System.out.println("enemyX = "+enemyX[i]);
				if (enemyX[i] < 0){
					enemyX[i]+=(numberOfEnemies*distance);
					enemyOffSet[i] = (random.nextFloat() -0.5f )* Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
					enemyOffSet2[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
					enemyOffSet3[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
					enemyOffSet4[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
					enemyOffSet5[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
					enemyOffSet6[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
				}else{
					enemyX[i] -= enemyVelocity;
				}
				batch.draw(bee1.getKeyFrame(elapsed), enemyX[i] , (Gdx.graphics.getHeight()/2)+enemyOffSet[i],birdWidth,birdWidth);
				batch.draw(bee2.getKeyFrame(elapsed), enemyX[i] , (Gdx.graphics.getHeight()/2)+enemyOffSet2[i],birdWidth,birdWidth);
				batch.draw(bee3.getKeyFrame(elapsed), enemyX[i] , (Gdx.graphics.getHeight()/2)+enemyOffSet3[i],birdWidth,birdWidth);
				batch.draw(bee4.getKeyFrame(elapsed), enemyX[i] , (Gdx.graphics.getHeight()/2)+enemyOffSet4[i],birdWidth,birdWidth);
				batch.draw(bee5.getKeyFrame(elapsed), enemyX[i] , (Gdx.graphics.getHeight()/2)+enemyOffSet5[i],birdWidth,birdWidth);
				batch.draw(bee6.getKeyFrame(elapsed), enemyX[i] , (Gdx.graphics.getHeight()/2)+enemyOffSet6[i],birdWidth,birdWidth);

				enemyCircle[i] = new Circle(enemyX[i]+birdWidth/2,(Gdx.graphics.getHeight()/2)+enemyOffSet[i]+birdWidth/2,birdWidth/2);
				enemyCircle2[i] = new Circle(enemyX[i]+birdWidth/2,(Gdx.graphics.getHeight()/2)+enemyOffSet2[i]+birdWidth/2,birdWidth/2);
				enemyCircle3[i] = new Circle(enemyX[i]+birdWidth/2,(Gdx.graphics.getHeight()/2)+enemyOffSet3[i]+birdWidth/2,birdWidth/2);
				enemyCircle4[i] = new Circle(enemyX[i]+birdWidth/2,(Gdx.graphics.getHeight()/2)+enemyOffSet4[i]+birdWidth/2,birdWidth/2);
				enemyCircle5[i] = new Circle(enemyX[i]+birdWidth/2,(Gdx.graphics.getHeight()/2)+enemyOffSet5[i]+birdWidth/2,birdWidth/2);
				enemyCircle6[i] = new Circle(enemyX[i]+birdWidth/2,(Gdx.graphics.getHeight()/2)+enemyOffSet6[i]+birdWidth/2,birdWidth/2);
			}
			if (birdY > Gdx.graphics.getHeight()/10 && birdY < Gdx.graphics.getHeight()*1.03f){
				velocity += gravity;
				birdY -= velocity;
			}
			else{
				gameState = 2;
			}
		}else if (gameState == 0){
			if (Gdx.input.justTouched()){
				gameState = 1;

			}
		}
		else{
			font2.draw(batch,"Tap to Restart",Gdx.graphics.getWidth()/2.5f,Gdx.graphics.getHeight()/2);
			if (Gdx.input.justTouched()){
				gameState = 1;

			}
			birdY = Gdx.graphics.getHeight()/2;
			for (int i = 0 ; i<numberOfEnemies; i++){

				enemyOffSet[i] = (random.nextFloat() -0.5f )* Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
				enemyOffSet2[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
				enemyOffSet3[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
				enemyOffSet4[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
				enemyOffSet5[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
				enemyOffSet[i] = (random.nextFloat() -0.5f ) * Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/100);
				enemyX[i] = Gdx.graphics.getWidth() - birdWidth/2 + i*distance;
				enemyCircle[i] = new Circle();
				enemyCircle2[i] = new Circle();
				enemyCircle3[i] = new Circle();
				enemyCircle4[i] = new Circle();
				enemyCircle5[i] = new Circle();
				enemyCircle6[i] = new Circle();
			}
			velocity = 0;
			scoredEnemy = 0;
			score = 0;
		}
		batch.draw(bird.getKeyFrame(elapsed), birdX , birdY,birdWidth,birdWidth);
		font.draw(batch,String.valueOf(score),Gdx.graphics.getWidth()/100*8,Gdx.graphics.getHeight()/100*20);
		batch.end();
		birdCircle.set(birdX+birdWidth/2,birdY+birdWidth/2,birdWidth/2);
		for (int i = 0 ; i < numberOfEnemies; i++){

			if (Intersector.overlaps(birdCircle,enemyCircle[i])
					|| Intersector.overlaps(birdCircle,enemyCircle2[i])
					|| Intersector.overlaps(birdCircle,enemyCircle3[i])
					|| Intersector.overlaps(birdCircle,enemyCircle4[i])
					|| Intersector.overlaps(birdCircle,enemyCircle5[i])
					|| Intersector.overlaps(birdCircle,enemyCircle6[i]) ){
				gameState = 2;
			}
		}
	}
	
	@Override
	public void dispose () {

	}
}
