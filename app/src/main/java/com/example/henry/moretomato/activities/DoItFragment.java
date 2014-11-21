package com.example.henry.moretomato.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.henry.moretomato.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoItFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoItFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DoItFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView textTimeCounter;
    private Button buttonTomatoStart;
    private TomatoCountTimer countTimer;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static DoItFragment newInstance(String param1, String param2) {
        DoItFragment fragment = new DoItFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public DoItFragment() {
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
        View fragmentView = inflater.inflate(R.layout.fragment_doit, container, false);
        if (textTimeCounter == null){
            textTimeCounter = (TextView)fragmentView.findViewById(R.id.textView_tomatoCounter);
        }
        if (countTimer == null){
            countTimer = new TomatoCountTimer(10000, 1000);
        }
        if (buttonTomatoStart == null){
            buttonTomatoStart = (Button)fragmentView.findViewById(R.id.buttonTomatoStart);
            buttonTomatoStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    countTimer.start();
                }
            });
        }

        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        public void onFragmentInteraction(Uri uri);
    }


    class TomatoCountTimer extends CountDownTimer{
        public TomatoCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish(){
            textTimeCounter.setText("Done");
        }
        @Override
        public void onTick(long milliSec){
            textTimeCounter.setText(milliSec / 1000 + "秒");
        }
    }

}
