package my.com.tm.dashboard;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import my.dashboard.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar Calendar_Object = Calendar.getInstance();
        Calendar_Object.set(Calendar.MONTH, 8);
        Calendar_Object.set(Calendar.YEAR, 2017);
        Calendar_Object.set(Calendar.DAY_OF_MONTH, 27);

        Calendar_Object.set(Calendar.HOUR_OF_DAY, 0);
        Calendar_Object.set(Calendar.MINUTE, 0);
        Calendar_Object.set(Calendar.SECOND, 0);


        // MyView is my current Activity, and AlarmReceiver is the
        // BoradCastReceiver
        Intent myIntent = new Intent(MainActivity.this, ScheduleFragment.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,
                0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		/*
		 * The following sets the Alarm in the specific time by getting the long
		 * value of the alarm date time which is in calendar object by calling
		 * the getTimeInMillis(). Since Alarm supports only long value , we're
		 * using this method.
		 */

        alarmManager.set(AlarmManager.RTC, Calendar_Object.getTimeInMillis(),
                pendingIntent);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar_Object.getTimeInMillis(), AlarmManager.INTERVAL_DAY*30, pendingIntent);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_camera);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  MenuInflater getMenuInflater = getSupportMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), PofileActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void displaySelectedScreen(int id) {
        Fragment fragment = null;

        switch(id) {
            case R.id.nav_schedule:
                fragment = new ScheduleFragment();
                break;
            case R.id.nav_camera:
                fragment = new FaultRatefragment();
                break;
//            case R.id.nav_slideshow:
//                fragment = new MTTRFragment();
//                break;
//           case R.id.nav_gated:
//               fragment = new GatedFragment();
//               break;
            /*     fragment = new Bank();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                startActivity(new Intent(NavigationActivity.this, Bank.class));
                drawer.closeDrawers();
                break;*/

            case R.id.nav_manage:

               // ImageView imageView = (ImageView) findViewById(R.id.imageView1);
                firebaseAuth.getInstance().signOut();
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View view = factory.inflate(R.layout.alertdialog,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setView(view);

                //alertDialogBuilder.setMessage("Are you sure you want to Log Out  ?");

                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //signout();
                                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
              //  alertDialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimary);

                break;
        }

        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    /*private void signout() {
        SharedPrefManager.getInstance(this).logout();
        finish();
    }*/



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id);

        return true;
    }
}
