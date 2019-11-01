package com.vaidik.truesaviour.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.vaidik.truesaviour.R;
import com.vaidik.truesaviour.activites.About;
import com.vaidik.truesaviour.activites.ContactUs;
import com.vaidik.truesaviour.activites.Help;

public class NavFrag extends AppCompatActivity {

    Intent recInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity);


        recInt = getIntent();


        if (recInt.getStringExtra("page").equalsIgnoreCase("profile")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ProfilePage())
                    .commitNow();
        }
        if (recInt.getStringExtra("page").equalsIgnoreCase("contact")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ContactUs())
                    .commitNow();
        }
        if (recInt.getStringExtra("page").equalsIgnoreCase("about")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new About())
                    .commitNow();
        }
        if (recInt.getStringExtra("page").equalsIgnoreCase("help")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Help())
                    .commitNow();
        }


    }
}
