package com.max.PicCropper;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class PicCropperMain extends Game {
	private SpriteBatch batch;
	private Texture image;

	OrthographicCamera cam;
	FitViewport viewPort;

	Pixmap main;
	float height;
	float width;
	float croppedWidth;
	float croppedHeidght;

	Array<Texture> textureArray;
	Array<Pixmap> pixmapArray;
	float divby;

	@Override
	public void create() {
		cam = new OrthographicCamera();

		batch = new SpriteBatch();
		image = new Texture("badlogic.png");

		main = new Pixmap(Gdx.files.local("orion_nebula.jpg"));

		width = main.getWidth();
		height = main.getHeight();

		if(height > width){
			divby = height/2048;
		}else{divby = width/2048;}

		System.out.println(width + " " + height);

		viewPort = new FitViewport(width, height, cam);

		cam.position.set(width/2, height/2, 0);

		croppedWidth = width/divby;
		croppedHeidght = height/divby;

		textureArray = new Array<>();
		pixmapArray = new Array<>();

		/*for(int row = 0; row < divby; row++){
			for(int col = 0; col < divby; col++){
				Pixmap pixmap = new Pixmap((int) croppedWidth, (int)croppedHeidght, Pixmap.Format.RGB888);

				pixmap.drawPixmap(main, 0, 0, col * (int) croppedWidth, row * (int)croppedHeidght,
						(int) croppedWidth, (int)croppedHeidght);

				Texture texture = new Texture(pixmap);
				textureArray.add(texture);
				pixmapArray.add(pixmap);
				FileHandle fileHandle = new FileHandle(("orion_nebula_"+col+"_"+row+".png"));
				PixmapIO.writePNG(fileHandle, pixmap);

			}
		}*/

		Pixmap pixmap = new Pixmap((int) croppedWidth, (int)croppedHeidght, Pixmap.Format.RGB888);
		pixmap.drawPixmap(main, 0,0, main.getWidth(), main.getHeight(), 0,0, (int)croppedWidth, (int)croppedHeidght);

		FileHandle fileHandle = new FileHandle(("orion_nebula_cropped.png"));
		PixmapIO.writePNG(fileHandle, pixmap);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		viewPort.apply();
		cam.update();
		//batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(image, 165, 180);
		for(Texture texture : textureArray){
			for(int row = 0; row < 2; row++){
				for(int col = 0; col < 2; col++){
					batch.draw(texture, row * croppedWidth, col * croppedHeidght);
				}
			}
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewPort.update(width, height);
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
		image.dispose();
		main.dispose();

		for(Texture texture : textureArray){
			texture.dispose();
		}
		for (Pixmap pixmap : pixmapArray){
			pixmap.dispose();
		}
	}


}