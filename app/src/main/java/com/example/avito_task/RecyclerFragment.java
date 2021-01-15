package com.example.avito_task;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.loader.app.LoaderManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RecyclerFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    //список
    private RecyclerView mRecycler;

    //list of objects to display
    private List<Object> items = getSimpleArrayList();

    private final ComplexRecyclerViewAdapter mAdapter = new ComplexRecyclerViewAdapter(items);

    public static RecyclerFragment newInstance() {
        return new RecyclerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.fr_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecycler = view.findViewById(R.id.recycler);
    }

    @Override
    //когда уже есть доступ к контексту
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecycler.setAdapter(mAdapter);

        //initialize the loader with OPERATION_SEARCH_LOADER as the ID
        /* LOADER_IDENTIFICATOR is a constant we have defined to uniquely identify our loader
         *which will be used in case of orientation change or cancelling our Loader */
        if (LoaderManager.getInstance(this).hasRunningLoaders() == false)
            LoaderManager.getInstance(this).initLoader(LOADER_IDENTIFICATOR, null, this);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<Object> getSimpleArrayList() {
        ArrayList<Object> list =new ArrayList<>(); //new ArrayList<>(Collections.nCopies(15, 1));
        List<Integer> range = IntStream.rangeClosed(0, 14)
                .boxed().collect(Collectors.toList());
        for (int i = 0;i<range.size();i++){
            list.add(range.get(i));
        }

        return list;
    }

    //==============
    //Loader block
    //Define two constants for Loader
    // (1)Integer constant to uniquely identify our Loader
    // (2)String constant which will act as a key to pass data to   Loader

    public static final int LOADER_IDENTIFICATOR = 21;
    public static final String DATA_PASS_KEY = "my_key_1";
    //------------------------------------------------
    String positiveResult = "READY";
    String notDone = "NOT_DONE";

    //override loader methods
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(getActivity()) {
            String operationStringResult;

            @Nullable
            @Override
            public String loadInBackground() {

                operationStringResult = notDone;//This work as a cache variable

                //HERE WILL BE MY BACKGROUND WORK
                try {

                        TimeUnit.MILLISECONDS.sleep(5000);
                        operationStringResult = positiveResult;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return operationStringResult;
            }

            @Override
            public void onStartLoading() {
                //like onPreExecute

                if (operationStringResult != null) {
                    deliverResult(operationStringResult);
                } else {
                    forceLoad();
                }

            }

            @Override
            public void deliverResult(String data) {
                operationStringResult = data;
                super.deliverResult(data);
            }
        };
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        //From here we will update our UI
        processFinishedResult(data);
        LoaderManager.getInstance(this).restartLoader(LOADER_IDENTIFICATOR,null,this);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }


    private void processFinishedResult(String s) {
        if (s == notDone) {

        } else {
           mAdapter.AddCardToViewAdapter();
        }
    }
}
