package com.example.d_pop.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.d_pop.R;
import com.example.d_pop.adapter.ProjectBaseAdapter;
import com.example.d_pop.adapter.QueryBaseAdapter;
import com.example.d_pop.model.QueryModel;
import com.example.d_pop.network.GetAPIServices;
import com.example.d_pop.network.RetrofitAPICalls;
import com.example.d_pop.network.RetrofitAPIClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeTabFragOne extends Fragment {

    private RecyclerView mQueryRecyclerView;
    private ArrayList<QueryModel> mQueryModel;
    private QueryBaseAdapter mQueryBaseAdapter;
    private FloatingActionButton mAddQueryFloatingActionButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_tab_frag_one, container, false);

        mQueryRecyclerView = view.findViewById(R.id.query_recycler_view);
        mAddQueryFloatingActionButton = view.findViewById(R.id.add_query_floating_button);

        mAddQueryFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuery();
            }
        });

        getAllQueries();

        return view;
    }

    private void getAllQueries() {
            GetAPIServices services = RetrofitAPIClient.getRetrofitInstance().create(GetAPIServices.class);
            Call<ArrayList<QueryModel>> call = services.getAllQueries();
            call.enqueue(new Callback<ArrayList<QueryModel>>() {
                @Override
                public void onResponse(Call<ArrayList<QueryModel>> call, Response<ArrayList<QueryModel>> response) {

                    if(response.body().size() < 1){
                        Toast.makeText(getContext(), "No Project found!!", Toast.LENGTH_SHORT).show();
                    }else {
                        mQueryModel = response.body();
                        mQueryBaseAdapter = new QueryBaseAdapter(mQueryModel, getContext());
                        mQueryRecyclerView.setAdapter(mQueryBaseAdapter);
                        mQueryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }


                }

                @Override
                public void onFailure(Call<ArrayList<QueryModel>> call, Throwable t) {
                    Toast.makeText(getContext(), "Retrofit Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }


        private void addQuery() {
            final EditText queryEditText = new EditText(getContext());


            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("Add a new Question")
                    .setMessage("   ")
                    .setView(queryEditText)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String query = String.valueOf(queryEditText.getText());
                            if(query.length() > 0) {
                                addQueryToDB();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        }

        private void addQueryToDB() {
            QueryModel queryModel;
        }


}
