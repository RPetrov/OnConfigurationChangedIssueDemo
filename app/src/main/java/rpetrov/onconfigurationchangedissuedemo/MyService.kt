package rpetrov.onconfigurationchangedissuedemo

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.e("MyService", "onConfigurationChanged newConfig = $newConfig")
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.i("MyService", "Created.")
    }
}