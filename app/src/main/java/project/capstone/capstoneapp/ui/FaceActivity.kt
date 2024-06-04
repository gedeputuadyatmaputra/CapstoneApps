package project.capstone.capstoneapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import project.capstone.capstoneapp.databinding.ActivityFaceBinding

class FaceActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonStartface.setOnClickListener {
            val intent = Intent(this,FaceDetailActivity::class.java)
            startActivity(intent)
        }

        binding.BtnHands.setOnClickListener {
            val intent = Intent(this,HandsActivity::class.java)
            startActivity(intent)
        }
    }
}