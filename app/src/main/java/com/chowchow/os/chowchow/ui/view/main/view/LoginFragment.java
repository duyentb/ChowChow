package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.User;
import com.chowchow.os.chowchow.realm.UserDAO;


public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText input_user_name, input_password;
    private Button btn_login;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //Get init Realm instance
        final UserDAO userDAO = new UserDAO();

        btn_login = (Button) view.findViewById(R.id.btn_login);
        input_password = (EditText) view.findViewById(R.id.input_password);
        input_user_name = (EditText) view.findViewById(R.id.input_user_name);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = input_user_name.getText().toString().trim();
                final String password = input_password.getText().toString().trim();

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!userDAO.checkExistUser(userName)) {
                    Toast.makeText(getContext(), "Tên đăng nhập không tồn tại !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = userDAO.getUser(userName, password);
                if (user == null) {
                    Toast.makeText(getContext(), "Mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
                } else {
                    userDAO.updateLogin(user.getUserName(), true);

                    Toast.makeText(getContext(), "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("USER_NAME", user.getUserName());
                    bundle.putString("NAME", user.getName());
                    bundle.putString("PASSWORD", user.getPassword());
                    bundle.putString("EMAIL", user.getEmail());
                    bundle.putString("PHONE", user.getPhone());
                    bundle.putString("AVATAR", user.getAvatar());

                    Fragment fragment = new ProfileFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                }
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

        TextView linkSignup = (TextView) view.findViewById(R.id.link_signup);
        linkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SignupFragment();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
