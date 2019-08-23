package com.vaidik.truesaviour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.stone.vega.library.VegaLayoutManager;

import java.util.Arrays;
import java.util.List;

import ru.github.igla.ferriswheel.FerrisWheelView;


public class Help extends Fragment {
    private static final String TAG = "DemoActivity";
    private SlidingUpPanelLayout mLayout;
    private FerrisWheelView ferrisWheelView;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ferrisWheelView = view.findViewById(R.id.ferrisWheelView);
        ferrisWheelView.startAnimation();

        recyclerView = view.findViewById(R.id.my_help_recycler_view);
        recyclerView.setLayoutManager(new VegaLayoutManager());

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.help_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.main_toolbar));

       /* ListView lv = view.findViewById(R.id.my_help_recycler_view);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "onItemClick", Toast.LENGTH_SHORT).show();
            }
        });*/

        List<String> your_array_list = Arrays.asList(
                "Shubham",
                "Chinmay",
                "Sikka",
                "Saniya"
        );

        RecyclerView.Adapter mAdapter = new Adapter(your_array_list);
        recyclerView.setAdapter(mAdapter);
        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
       /* ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                your_array_list);

        lv.setAdapter(arrayAdapter);*/

        mLayout = view.findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(PanelState.COLLAPSED);
            }
        });


        Button f = view.findViewById(R.id.follow);

        f.setMovementMethod(LinkMovementMethod.getInstance());
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.instagram.com/tutushubham"));
                startActivity(i);
            }
        });

        return view;
    }


}