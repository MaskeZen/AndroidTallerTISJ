package com.tisj.tareax;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;

    EditText user;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.login);
        btnLogin.setOnClickListener(this);

        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                String usuario = user.getText().toString();
                Toast.makeText(getApplicationContext(),usuario,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class obtenerLogin extends AsyncTask<String,Void,Void> {

        String resultado;

        @Override
        protected Void doInBackground(String... strings) {


            return null;
        }
    }


}
