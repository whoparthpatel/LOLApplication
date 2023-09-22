package com.patel.lolapplication.activitis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.patel.lolapplication.R
import com.patel.lolapplication.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private var binding: ActivityUserBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)
    }
}