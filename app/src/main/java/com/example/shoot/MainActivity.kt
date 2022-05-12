package com.example.shoot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.coroutines.*
import android.graphics.Canvas
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
public  final class MyAppGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity()
{
    lateinit var img : ImageView
    lateinit var game : Game
    var flag:Boolean = false
    lateinit var job : Job

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img = findViewById(R.id.img)
        game = findViewById(R.id.game)

        img.setOnClickListener{
            if (flag)
            {
                flag = false
                img.setImageResource(R.drawable.start)
                job.cancel()
            }
            else
            {
                flag = true
                img.setImageResource(R.drawable.stop)
                job = GlobalScope.launch(Dispatchers.Main)
                {
                    while (flag)
                    {
                        delay(10)
                        game.fly.update()
                        var canvas: Canvas = game.surfaceHolder.lockCanvas()
                        game.drawSomething(canvas)
                        game.surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }

        val img2:ImageView = findViewById(R.id.img2)
        GlideApp.with(this)
            .load(R.drawable.myphoto)
            .circleCrop()
            .override(100, 100)
            .into(img2)

    }
}

