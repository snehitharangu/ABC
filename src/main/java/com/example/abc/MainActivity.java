package com.example.abc;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/*public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        //Intent intent1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
        //startActivity(intent1);
    }

    @Override
    public void onClick(View v) {

    }
}*/
public class MainActivity extends AppCompatActivity implements  View.OnClickListener,View.OnKeyListener{
    Boolean Signupactive = true;
    EditText h ;
    EditText p ;
    TextView login;
    String g;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login=findViewById(R.id.button1);
        login.setOnClickListener(this);
        p=findViewById(R.id.e1);
        p.setOnKeyListener(this);
        LinearLayout rl=findViewById(R.id.login);
        rl.setOnClickListener(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("577bad952c9173c89cd31d76856c6ae2d3269a3e")
                // if defined
                .clientKey("091ca001131a2b3624cac49637a42608f275c1ad")
                .server("http://18.222.250.118:80/parse")
                .build()
        );
        if(ParseUser.getCurrentUser()!=null){
            setintent();
        }

    }
    public void setintent(){
        Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
        ParseUser current=ParseUser.getCurrentUser();
        String j=current.getUsername();
        intent.putExtra("user",j);
        startActivity(intent);
    }

    public void sighup(View view) {
        h = findViewById(R.id.e1);
        p = findViewById(R.id.e2);
        if (h.getText().toString().matches("") || p.getText().toString().matches("")) {
            Toast.makeText(this, "enter user name and password", Toast.LENGTH_SHORT).show();
        } else {
            if (Signupactive) {
                ParseUser user = new ParseUser();
                user.setUsername(h.getText().toString());
                user.setPassword(p.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e == null) {
                                                    Log.i("done", "done");
                                                    setintent();
                                                } else {
                                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                );
            } else {
                ParseUser.logInInBackground(h.getText().toString(), p.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e == null){
                            Log.i("done","logined");
                            setintent();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();;
                        }

                    }
                });
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            Button n = findViewById(R.id.button);
            login=findViewById(R.id.button1);
            if (Signupactive) {
                n.setText("Login");
                Signupactive = false;
                login.setText("or,signup");

            } else {
                n.setText("signup");
                Signupactive = true;
                login.setText("or,login");

            }

        }
        else if(v.getId()==R.id.login)
        {
            InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
            sighup(v);
        }


        return false;
    }
}

