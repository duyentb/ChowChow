package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chowchow.os.chowchow.model.User;
import com.chowchow.os.chowchow.realm.UserDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.chowchow.os.chowchow.R;

public class SignupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText input_name, input_user_name, input_email, input_phone, input_password;
    private Button btnSignIn, btn_signup, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private OnFragmentInteractionListener mListener;

    public SignupFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        //Get init Realm instance
        final UserDAO userDAO = new UserDAO();

        btn_signup = (Button) view.findViewById(R.id.btn_signup);
        input_name = (EditText) view.findViewById(R.id.input_name);
        input_email = (EditText) view.findViewById(R.id.input_email);
        input_password = (EditText) view.findViewById(R.id.input_password);
        input_user_name = (EditText) view.findViewById(R.id.input_user_name);
        input_phone = (EditText) view.findViewById(R.id.input_phone);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = input_email.getText().toString().trim();
                final String name = input_name.getText().toString().trim();
                final String userName = input_user_name.getText().toString().trim();
                final String phone = input_phone.getText().toString().trim();
                final String password = input_password.getText().toString().trim();

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userDAO.checkExistUser(userName)) {
                    Toast.makeText(getContext(), "Tên đăng nhập đã tồn tại !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getContext(), "Mật khẩu phải lớn hơn 6 ký tự !", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setUserName(userName);
                user.setPassword(password);
                user.setName(name);
                user.setEmail(email);
                user.setPhone(phone);
                user.setLogin(false);

                userDAO.insertUser(user);

                Toast.makeText(getContext(), "Đăng ký thành công !", Toast.LENGTH_SHORT).show();

                Fragment fragment = new LoginFragment();
                loadFragment(fragment);

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView ivLogo = (ImageView) view.findViewById(R.id.imgLogo);
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        TextView linkLogin = (TextView) view.findViewById(R.id.link_login);
        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                loadFragment(fragment);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
        mListener = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
