package irfandp.task_3;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class RegisterActivity extends AppCompatActivity {

    EditText et_name, et_pass2, et_email2;
    Button btn_send;
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    TextView tv_respond, tv_respond2,tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_name = (EditText) findViewById(R.id.et_name);
        et_pass2 = (EditText) findViewById(R.id.et_pass2);
        et_email2 = (EditText) findViewById(R.id.et_email2);
        btn_send = (Button) findViewById(R.id.btn_send);
        tv_respond = (TextView) findViewById(R.id.tv_respond);
        tv_respond2 = (TextView) findViewById(R.id.tv_respond2);
        tv_result = (TextView) findViewById(R.id.tv_result);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:3000")
                        .baseUrl("https://private-9989a0-task33.apiary-mock.com/users/") //ipan
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                final UserApi user_api = retrofit.create(UserApi.class);

                // // implement interface for get all user
                Call<Users> call = user_api.getUsers();
                call.enqueue(new Callback<Users>() {

                                 @Override
                                 public void onResponse(Call<Users> call, Response<Users> response) {
                                     int status = response.code();
                                     if (status == 200) {
                                         tv_respond.setText("");
                                     }

                                     //this extract data from retrofit with for() loop
                                     for (Users.UserItem user : response.body().getUsers()) {
                                         if (et_email2.getText().toString().equals(user.getEmail())) {
//                                             Toast.makeText(RegisterActivity.this,"",Toast.LENGTH_LONG).show();
                                             tv_respond.setText("email anda sudah terdaftar");
                                         } else {
                                             tv_respond.setText("email belum terdaftar");

                                             User user_save = new User(et_name.getText().toString(), et_email2.getText().toString(), et_pass2.getText().toString());
                                             Call<User> call2 = user_api.saveUser(user_save);
                                             call2.enqueue(new Callback<User>() {
                                                 @Override
                                                 public void onResponse(Call<User> call, Response<User> response) {
                                                     int status = response.code();
                                                     tv_respond2.setText(String.valueOf(status));
                                                     tv_result.setText(String.valueOf(response.body()));
                                                 }

                                                 @Override
                                                 public void onFailure(Call<User> call, Throwable t) {
                                                     tv_respond2.setText(String.valueOf(t));
                                                 }
                                             });
//

                                         }
                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<Users> call, Throwable t) {
                                     tv_respond.setText(String.valueOf(t));
                                 }


                             }
                );
            }
        });
    }
}
