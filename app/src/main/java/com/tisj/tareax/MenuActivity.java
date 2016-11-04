package com.tisj.tareax;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tisj.tareax.auxiliar.Usuario;
import com.tisj.tareax.modelo.Estudiante;

import org.w3c.dom.Text;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BienvenidaFragment.OnFragmentInteractionListener {

    TextView nombreUsuario;
    TextView mailUsuario;
    ImageView imagenUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        FrameLayout frame = new FrameLayout(this);
        frame.setId(R.id.frame);
        if (savedInstanceState == null) {
            Fragment fragment = null;
            fragment =  new BienvenidaFragment();

            Bundle args = new Bundle();
            Bundle extras = getIntent().getExtras();
            String paramCedula = extras.getString("cedula");
            args.putString("cedula",paramCedula);
            fragment.setArguments(args);
            getFragmentManager().beginTransaction().add(R.id.frame, fragment).commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String cedula = extras.getString("cedula");
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        nombreUsuario = (TextView)findViewById(R.id.nombreUsuario);
        mailUsuario = (TextView)findViewById(R.id.mailUsuario);
        imagenUsuario = (ImageView)findViewById(R.id.imagenUsuario);

        Estudiante estudiante = Usuario.getEstudiante();
        if(estudiante != null){
            nombreUsuario.setText(estudiante.getNombre());
            mailUsuario.setText(estudiante.getMail());
            imagenUsuario.setImageBitmap(estudiante.getImagen());
        }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.menu_practicos) {
            fragment =  new ListarPracticosFragment();
            getFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
        } else if (id == R.id.menu_estudiantes) {
            fragment =  new ListarEstudiantesFragment();
            getFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
        } else if (id == R.id.menu_teoricos) {
            fragment =  new ListarTeoricosFragment();
            getFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
        } else if (id == R.id.menu_clases) {
            fragment =  new ListarClasesFragment();
            getFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
        } else if (id == R.id.menu_perfil) {

        } else if (id == R.id.menu_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
