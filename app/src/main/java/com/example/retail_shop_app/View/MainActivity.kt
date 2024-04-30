package com.example.retail_shop_app.View

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.retail_shop_app.R
import com.example.retail_shop_app.View.Fragments.Accounts_Fragments
import com.example.retail_shop_app.View.Fragments.Add_Customers_Fragment
import com.example.retail_shop_app.View.Fragments.HomeFragment
import com.example.retail_shop_app.View.Fragments.Payment_Fragment
import com.example.retail_shop_app.View.Fragments.Pos_Fragment
import com.example.retail_shop_app.View.Fragments.StuffFragment
import com.example.retail_shop_app.View.Fragments.View_Customer_Fragment
import com.example.retail_shop_app.View.Fragments.Add_product_fragment
import com.example.retail_shop_app.View.Fragments.View_product_fragment
import com.google.android.material.navigation.NavigationView








class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.open_nav,
            R.string.close_nav
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()


            R.id.nav_add_product -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Add_product_fragment()).commit()

            R.id.nav_view_product -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, View_product_fragment()).commit()



            R.id.nav_accounts -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Accounts_Fragments()).commit()
            R.id.nav_add_customers -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Add_Customers_Fragment()).commit()

            R.id.nav_view_customers-> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, View_Customer_Fragment()).commit()


            R.id.nav_Payment-> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Payment_Fragment()).commit()

            R.id.nav_Pos-> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Pos_Fragment()).commit()


            R.id.nav_Stuff-> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, StuffFragment()).commit()



            R.id.nav_logout -> {

                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish() // Finish the current activity
            }



        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}