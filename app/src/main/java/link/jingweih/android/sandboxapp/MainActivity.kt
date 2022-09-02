package link.jingweih.android.sandboxapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import link.jingweih.android.identity.ui.FirebaseLoginActivityContract
import link.jingweih.android.sandboxapp.databinding.ActivityMainBinding
import link.jingweih.android.sandboxapp.ui.composeexample.ComposeExampleActivity
import link.jingweih.android.sandboxapp.ui.mvvmexample.MvvmExampleActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    private val createLoginActivity =
        registerForActivityResult(FirebaseLoginActivityContract()) { result ->
            // parseResult will return this as string?
            if (result) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login Fail", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        val drawerLayout = binding.drawerLayout
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_mvvm -> {
                val intent = Intent(this, MvvmExampleActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_login -> {
                createLoginActivity.launch(getString(R.string.app_name))
            }
            R.id.nav_compose -> {
                val intent = Intent(this, ComposeExampleActivity::class.java)
                startActivity(intent)
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}