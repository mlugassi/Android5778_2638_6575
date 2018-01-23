package lugassi.wallach.android5778_2638_6575.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import lugassi.wallach.android5778_2638_6575.R;
import lugassi.wallach.android5778_2638_6575.model.backend.DBManagerFactory;
import lugassi.wallach.android5778_2638_6575.model.backend.DB_manager;
import lugassi.wallach.android5778_2638_6575.model.datasource.CarRentConst;

public class Login extends Activity implements View.OnClickListener {

    private DB_manager db_manager;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView linkSignupTextView;
    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // remove header title
        setContentView(R.layout.activity_login);
        try {
            db_manager = DBManagerFactory.getManager(); // singleton db to work with
            checkSharedPreferences(); // check if username & password already exist this will start ManageActivity accordingly and finish current activity
            findViews();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void checkSharedPreferences() {
        final String username = getDefaults(CarRentConst.UserConst.USER_NAME, this);
        // check if username not empty
        if (!username.equals("")) {
            // import password
            String password = getDefaults(CarRentConst.UserConst.PASSWORD, this);
            // connect server DB and check if username and password exit in DB
            new AsyncTask<String, Object, String>() {
                @Override
                protected void onPostExecute(String result) {
                    // if username and password are correct - get the string from db as declared there
                    if (result.contains("Success")) {
                        Intent intent = new Intent(Login.this, ManageActivity.class);
                        result = result.substring("Success Login:".length(), result.length() - 1);
                        // add id and username details to send ManageActivity
                        intent.putExtra(CarRentConst.CustomerConst.CUSTOMER_ID, Integer.parseInt(result));
                        intent.putExtra(CarRentConst.UserConst.USER_NAME, username);
                        // kill activity so it won't get into the stack
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                protected String doInBackground(String... params) {
                    String result = null;
                    try {
                        result = db_manager.checkAdmin(params[0], params[1]);
                    } catch (Exception e) {
                        return e.getMessage();
                    }
                    return result;
                }
            }.execute(username, password);
        }
    }

    private void findViews() {
        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        linkSignupTextView = (TextView) findViewById(R.id.linkSignupTextView);
        errorTextView = (TextView) findViewById(R.id.errorTextView);

        linkSignupTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    /**
     * method to check details inserted once login button is activated
     *
     * @return false if username empty or too long or if password empty
     */
    private boolean checkValues() {
        if (TextUtils.isEmpty(userNameEditText.getText().toString())) {
            userNameEditText.setError(getString(R.string.exceptionEmptyFileds));
            return false;
        }
        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            passwordEditText.setError(getString(R.string.exceptionEmptyFileds));
            return false;
        }
        if (userNameEditText.getText().toString().length() > 25) {
            userNameEditText.setError(getString(R.string.exceptionUserName));
            return false;
        }
        return true;
    }

    void login() {
        // check correctness of details
        if (!checkValues())
            return;
        // run a DB process using inserted details by the user
        new AsyncTask<String, Object, String>() {
            @Override
            protected void onPostExecute(String result) {
                if (result.contains("Success")) {
                    // if username and password are correct - set username and password as inserted by user
                    setDefaults(CarRentConst.UserConst.USER_NAME, userNameEditText.getText().toString(), Login.this);
                    setDefaults(CarRentConst.UserConst.PASSWORD, passwordEditText.getText().toString(), Login.this);
                    Intent intent = new Intent(Login.this, ManageActivity.class);
                    result = result.substring("Success Login:".length(), result.length() - 1);
                    // add id and username details to send ManageActivity
                    intent.putExtra(CarRentConst.CustomerConst.CUSTOMER_ID, Integer.parseInt(result));
                    intent.putExtra(CarRentConst.UserConst.USER_NAME, userNameEditText.getText().toString());
                    // kill activity so it won't get into the stack
                    finish();
                    Login.this.startActivity(intent);
                }
                // details inserted in Login screen failed - show error message
                else {
                    errorTextView.setText(result);
                    errorTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected String doInBackground(String... params) {
                String result = null;
                try {
                    result = db_manager.checkAdmin(params[0], params[1]);
                } catch (Exception e) {
                    result = e.getMessage();
                }
                return result;
            }
        }.execute(userNameEditText.getText().toString(), passwordEditText.getText().toString());

    }

    void signup() {
        Intent intent = new Intent(Login.this, AddCustomer.class);
        Login.this.startActivity(intent);
    }

    /**
     * overridden method implementing View.OnClickListener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == loginButton) {
            try {
                login();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else
            signup();

    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

}
