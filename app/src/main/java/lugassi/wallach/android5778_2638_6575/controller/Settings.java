package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import lugassi.wallach.android5778_2638_6575.R;

public class Settings extends Activity implements View.OnClickListener {

    private Button userAndPasswordButton;
    private Button personalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        findViews();
    }

    private void findViews() {
        userAndPasswordButton = (Button) findViewById(R.id.userAndPasswordButton);
        personalButton = (Button) findViewById(R.id.personalButton);

        userAndPasswordButton.setOnClickListener(this);
        personalButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == userAndPasswordButton) {
            startActivity(new Intent(Settings.this, AddUser.class));
        } else if (v == personalButton) {
            startActivity(new Intent(Settings.this, AddCustomer.class));
        }
    }

}



