package cn.woolsen.ourstu.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.commit
import cn.woolsen.ourstu.databinding.ActivityMainBinding
import cn.woolsen.ourstu.ui.main.timetable.TimetableFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ComposeView(this).apply {
            setContent {
                Text(text = "Hello")
            }
        })
    }


}