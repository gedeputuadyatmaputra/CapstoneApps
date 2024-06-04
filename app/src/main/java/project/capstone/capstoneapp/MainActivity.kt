package project.capstone.capstoneapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import project.capstone.capstoneapp.databinding.ActivityMainBinding
import project.capstone.capstoneapp.ui.FaceActivity
import project.capstone.capstoneapp.ui.HandsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnFace.setOnClickListener {
            val intent = Intent(this, FaceActivity::class.java)
            startActivity(intent)
        }

        binding.BtnHands.setOnClickListener {
            val intent = Intent(this,HandsActivity::class.java)
            startActivity(intent)
        }
    }
}