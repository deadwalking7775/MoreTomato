package com.example.henry.moretomato.activities;

import android.app.Activity;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.henry.moretomato.adapters.TaskAdapter;
import com.example.henry.moretomato.R;
import com.example.henry.moretomato.data.TaskDB;
import com.example.henry.moretomato.data.TaskProvider;

public class ArrangementFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private TaskAdapter mTaskAdapter;
    private ListView mTodoList;
    private OnFragmentInteractionListener mListener;

    public ArrangementFragment() {
        super();
    }

    public static ArrangementFragment newInstance() {
        return new ArrangementFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskAdapter = new TaskAdapter(getActivity(), null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_arrangement, container, false);
        mTodoList = (ListView)v.findViewById(R.id.listView_todo_list);
        mTodoList.setAdapter(mTaskAdapter);
        mTodoList.setOnItemClickListener(mTaskAdapter);
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                TaskProvider.TASK_URI, null, null, null, TaskDB.UPDATEDTIME
                + " desc");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[] { "_id" });
        matrixCursor.addRow(new String[] { "0" });
        Cursor c = new MergeCursor(new Cursor[] { matrixCursor, cursor });
        mTaskAdapter.swapCursor(c);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        mTaskAdapter.swapCursor(null);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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



}
