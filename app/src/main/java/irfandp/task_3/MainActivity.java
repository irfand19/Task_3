package irfandp.task_3;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login;
        TextView edt_reg;
        btn_login = (Button) findViewById(R.id.btn_login);
        edt_reg = (TextView) findViewById(R.id.edt_reg);

        btn_login.setOnClickListener(this);
        edt_reg.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent lay_home = new Intent(this, HomeActivity.class);
                startActivity(lay_home);
                break;
            case R.id.edt_reg:
                Intent lay_reg = new Intent(this, RegisterActivity.class);
                startActivity(lay_reg);
        }
    }
}
