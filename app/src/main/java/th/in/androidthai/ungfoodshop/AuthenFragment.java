package th.in.androidthai.ungfoodshop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthenFragment extends Fragment {


    public AuthenFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        SignUp Controller
        signUpController();

//        SignIn Controller
        signInController();

    }   // Main Method

    private void signInController() {
        Button button = getView().findViewById(R.id.btnSignIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                String user = userEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                MyAlert myAlert = new MyAlert(getActivity());

                if (user.isEmpty() || password.isEmpty()) {
                    myAlert.normalDialog("Have Space", "Please Fill All EveryBlank");
                } else {
                    checkAuthen(user, password);
                }

            }
        });

    }

    private void checkAuthen(String user, String password) {

        MyConstant myConstant = new MyConstant();
        MyAlert myAlert = new MyAlert(getActivity());

        String name, truePassword;

        try {

            GetUserWhereUserThread getUserWhereUserThread = new GetUserWhereUserThread(getActivity());
            getUserWhereUserThread.execute(user, myConstant.getUrlGetUserWhereUser());

            String jsonString = getUserWhereUserThread.get();
            Log.d("8MayV1", "json ==> " + jsonString);

            if (jsonString.equals("null")) {
                myAlert.normalDialog("User False", "No " + user + " in my Database");
            } else {

                JSONArray jsonArray = new JSONArray(jsonString);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                name = jsonObject.getString("Name");
                truePassword = jsonObject.getString("Password");

                if (password.equals(truePassword)) {
//                    Password True
                    Toast.makeText(getActivity(), "Welcome " + name, Toast.LENGTH_SHORT).show();

                } else {
//                    Password False
                    myAlert.normalDialog("Password False", "Please Try Again Password False");
                }

            }   // if

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void signUpController() {
        Button button = getView().findViewById(R.id.btnSignUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Replace Fragment
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null).commit();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authen, container, false);
    }

}   // Main Class
