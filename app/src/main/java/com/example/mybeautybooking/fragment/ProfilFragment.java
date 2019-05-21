package com.example.mybeautybooking.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybeautybooking.ClientProfilActivity;
import com.example.mybeautybooking.R;
import com.example.mybeautybooking.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1 ;
    private String mParam2;

    public ArrayList<String> arrayList = new ArrayList<>();
    ProgressBar progressBar;
    ProgressDialog progressDialog2;


    EditText email;
    EditText firstnameText_Client;
    EditText nameText_Client;
    EditText passText_Client;
    EditText phoneIntClient;
    EditText streetText_Client;
    EditText zipInt_Client;
    EditText cityText_Client;

    Button doneButton,deleteButton;

    //RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
    ClientProfilActivity clientProfilActivity;

    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.1.27/Test-Projet/delete_test.php";
    private StringRequest request;

    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        nameText_Client = (EditText) view.findViewById(R.id.input_name);
        nameText_Client.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserName());

        firstnameText_Client = (EditText) view.findViewById(R.id.input_first_name);
        firstnameText_Client.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserFirstname());

        phoneIntClient= (EditText) view.findViewById(R.id.input_phone);
        phoneIntClient.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserPhone());

        streetText_Client = (EditText) view.findViewById(R.id.input_street);
        streetText_Client.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserStreet());

        cityText_Client = (EditText) view.findViewById(R.id.input_city);
        cityText_Client.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserCity());


        zipInt_Client = (EditText) view.findViewById(R.id.input_zip);
        cityText_Client.setText(SharedPrefManager.getInstance(getActivity()).getKeyUserZip());

        email = (EditText) view.findViewById(R.id.input_email);
        email.setText(SharedPrefManager.getInstance(getActivity()).getUserEmail());

        doneButton = (Button)view.findViewById(R.id.btn_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getContext (), R.string.done_msg, Toast.LENGTH_LONG).show();
            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());


        clientProfilActivity = new ClientProfilActivity();
        deleteButton = (Button)view.findViewById(R.id.btn_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //confirmDelete();
                delete();
            }
        });

        return view;
    }

 /*   private void confirmDelete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder.setMessage("Êtes-vous sûr de vouloir supprimer votre compte?");
        alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //If the user confirms deletion, execute DeleteMovieAsyncTask
                        delete();

                    }
                });

        alertDialogBuilder.setNegativeButton("Annuler", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }*/

   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionHome(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            mListener.onFragmentInteractionHome(Uri.parse("doWhatYouWant"));
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        if ( progressDialog2!=null && progressDialog2.isShowing() ){
//            progressDialog2.cancel();
//        }
//    }*/

    public void delete () {

//        progressDialog2 = new ProgressDialog(getActivity());
//        progressDialog2.setMessage(" Suppression en cours. Veuillez patienter...");
//        progressDialog2.setIndeterminate(false);
//        progressDialog2.setCancelable(false);
//        progressDialog2.show();    f

        request = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // create json object with php reponse
                    JSONObject jsonObject = new JSONObject(response);

                    /*verification jsonphp reponse*/
                    if (jsonObject.names().get(0).equals("success")) {
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // onLoginSuccess

                                        progressBar.setVisibility(View.INVISIBLE);
                                        //progressDialog2.dismiss();
                                    }
                                }, 300);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError) {
                    Toast.makeText(getActivity(), "TimeoutError", Toast.LENGTH_LONG).show();
                }

                if (error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "NoConnectionError", Toast.LENGTH_LONG).show();
                }

                if (error instanceof AuthFailureError) {
                    Toast.makeText(getActivity(), "AuthFailureError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getActivity(), "ServerError", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getActivity(), "NetworkError", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getActivity(), "ParseError", Toast.LENGTH_LONG).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", SharedPrefManager.getInstance(getActivity()).getKeyUserID().toString());

                return hashMap;
            }
        };

        requestQueue.add(request);



    }

public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    public void onFragmentInteractionHome(Uri uri);

    }

}
