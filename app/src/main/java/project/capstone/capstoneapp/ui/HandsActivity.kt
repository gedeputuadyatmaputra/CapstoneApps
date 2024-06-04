package project.capstone.capstoneapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import project.capstone.capstoneapp.R
import project.capstone.capstoneapp.databinding.ActivityHandsBinding

class HandsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHandsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHandsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonHand.setOnClickListener {
            val intent = Intent(this,HandDetailActivity::class.java)
            startActivity(intent)
        }

        binding.buttonFace.setOnClickListener {
            val intent = Intent(this,FaceActivity::class.java)
            startActivity(intent)
        }
    }
}