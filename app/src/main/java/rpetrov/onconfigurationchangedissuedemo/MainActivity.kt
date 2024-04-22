package rpetrov.onconfigurationchangedissuedemo

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        updateStatus()

        findViewById<TextView>(R.id.startAccessibilityButton).setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        findViewById<TextView>(R.id.startServiceButton).setOnClickListener {
            startService(Intent(this, MyService::class.java))
            updateStatus()
        }
    }

    private fun updateStatus() {
        findViewById<TextView>(R.id.status).text =
            "MyAccessibilityService is running: ${hasAccessibilityPermission(this)}\nMyService is running: ${serviceIsRunning()}"
    }

    private fun serviceIsRunning(): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (MyService::class.java.name.equals(service.service.className)) {
                return true
            }
        }
        return false
    }

    private fun hasAccessibilityPermission(context: Context): Boolean {
        var accessEnabled = true
        try {
            accessEnabled =
                Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Secure.ACCESSIBILITY_ENABLED,
                    0
                ) != 0

            val am =
                context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
            val enabledServices =
                am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)

            accessEnabled = accessEnabled && enabledServices.find {
                val enabledServiceInfo: ServiceInfo = it.resolveInfo.serviceInfo
                enabledServiceInfo.packageName.equals(context.packageName)
            } != null
        } catch (e: Settings.SettingNotFoundException) {
            Log.e("MainActivity", e.message, e)
        }
        return accessEnabled
    }

}