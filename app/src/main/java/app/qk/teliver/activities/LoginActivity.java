package app.qk.teliver.activities;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Method;

import app.qk.teliver.R;
import app.qk.teliver.utils.BaseResponseHandler;
import app.qk.teliver.utils.Constants;
import app.qk.teliver.utils.JsonUtils;
import app.qk.teliver.utils.MPreference;
import app.qk.teliver.utils.Repository;
import app.qk.teliver.utils.Utils;

public class LoginActivity extends AppCompatActivity implements BaseResponseHandler{

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton ;

    private  Repository repository;
    private Method currentMethod;
    MPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         _emailText = (EditText) findViewById(R.id.input_email);
         _passwordText = (EditText) findViewById(R.id.input_password);
         _loginButton = (Button) findViewById(R.id.btn_login);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        repository = new Repository(this);
        preference  = new MPreference(this);

    }

    public void login() {

        if (!validate()) {
            onLoginFailed("Valores de campos incorrectos");
            return;
        }

        _loginButton.setEnabled(false);

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        try
        {
            currentMethod = LoginActivity.class.getDeclaredMethod("OnLoginSuccess", String.class);
            repository.Login(email,password);
        }
        catch (Exception ex) {
            OnFail(ex.getMessage());
        }

    }

    private void GetUserActor()    {
        try {
            currentMethod = LoginActivity.class.getDeclaredMethod("NavigateToUserScreen", String.class);
            repository.GetLoginActor();
        }
        catch (Exception e) {
            onLoginFailed(e.toString());
        }
    }

    public void NavigateToUserScreen(String response)    {

        String role = JsonUtils.getActor(response);

        if (role!= null)
        {
            if (role.equals("Chofer"))
            {
                startActivity(new Intent(this, ActivityDriver.class));
                return;
            }

            if (role.equals("Padre"))
            {
                startActivity(new Intent(this, ActivityCustomer.class));
                return;
            }


        }

    }

    public void OnLoginSuccess(String response) {

        OnEndConnection();

        String token = JsonUtils.getTokenFromString(response);

        if(token!= null && token.length() != 0 )
        {

            preference.storeBoolean(Constants.IS_LOGGED_IN, true);
            preference.storeString(Constants.LOGGE_IN_TOKEN,token);

            GetUserActor();

        }
        else {
          OnFail("El token recivido no es valido");
        }
    }

    public void onLoginFailed(String mensage) {
        Utils.showMensage(this,mensage);
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


        if (email.isEmpty()) {
            _passwordText.setError("No ha especificado nombre de usuario");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("No ha especificado contraseña");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public void OnBginConnection() {
        Utils.ShowProgessDialog(this, "Autenticando....");
    }

    @Override
    public void OnEndConnection() {
        _loginButton.setEnabled(true);
        Utils.HideProgessDialog();
    }

    @Override
    public void OnFail(String error) {
        OnEndConnection();
        onLoginFailed(error);
    }

    @Override
    public Method CurrentMethod() {
        return currentMethod;
    }

    @Override
    public Context Context() {
        return this;
    }
}
