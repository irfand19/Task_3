package irfandp.task_3;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_pass, et_email;
    Button btn_login;
    TextView edt_reg, tv_respond3;
    int cek = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_pass = (EditText) findViewById(R.id.et_pass);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_login = (Button) findViewById(R.id.btn_login);
        edt_reg = (TextView) findViewById(R.id.edt_reg);
        tv_respond3 = (TextView) findViewById(R.id.tv_respond3);

        btn_login.setOnClickListener(this);
        edt_reg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                tv_respond3.setText("");
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:3000")
                        .baseUrl("https://private-9989a0-task33.apiary-mock.com/users/") //ipan
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                final UserApi user_api = retrofit.create(UserApi.class);

                Call<Users> call = user_api.getUsers();
                call.enqueue(new Callback<Users>() {

                                 @Override
                                 public void onResponse(Call<Users> call, Response<Users> response) {
//                                     int status = response.code();
//                                     if (status == 200) {
//                                         tv_respond3.setText(status);
//                                     }

                                     //this extract data from retrofit with for() loop
                                     for (Users.UserItem user : response.body().getUsers()) {
                                         if ((et_email.getText().toString().equals(user.getEmail())) && (et_pass.getText().toString().equals(user.getPassword()))) {
                                             cek = 1;
                                             SharedPreferences set_shared_preference = getSharedPreferences("authentication", MODE_PRIVATE);
                                             SharedPreferences.Editor sp_editor = set_shared_preference.edit();
                                             sp_editor.putString("token_authentication","fd@3jfD83#dfaksdfweqoru#LEWlkj");
                                             sp_editor.putString("name",user.getName());
                                             sp_editor.putString("email",user.getEmail());
                                             sp_editor.putString("pass",user.getPassword());
                                             sp_editor.commit();

                                             Intent lay_home = new Intent(getApplicationContext(), HomeActivity.class);
                                             startActivity(lay_home);
                                         }
                                     }

                                     if (cek == 0) {
                                         tv_respond3.setText("email dan pass salah");
//                                         Toast.makeText(MainActivity.this, "Email dan Pass sama", Toast.LENGTH_LONG).show();
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<Users> call, Throwable t) {
                                     tv_respond3.setText(String.valueOf(t));
                                 }
                             }
                );
                break;


//                break;

            case R.id.edt_reg:
                Intent lay_reg = new Intent(this, RegisterActivity.class);
                startActivity(lay_reg);
                break;
        }
    }
}
