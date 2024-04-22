package rpetrov.onconfigurationchangedissuedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}