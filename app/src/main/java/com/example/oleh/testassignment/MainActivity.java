  package com.example.oleh.testassignment;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.example.oleh.testassignment.model.Cube;

import java.util.ArrayList;
import java.util.List;

  public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        setupRecyclerView();
    }

      private void setupRecyclerView() {
          List<Cube> cubeList = new ArrayList<>();
          cubeList.add(new Cube(Color.RED,15,15,15));
          cubeList.add(new Cube(Color.GREEN,100,25,25));
          cubeList.add(new Cube(Color.BLUE,75,75,75));
          CubeAdapter adapter = new CubeAdapter(cubeList);
          LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
          recyclerView.setLayoutManager(manager);
          PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
          pagerSnapHelper.attachToRecyclerView(recyclerView);

          recyclerView.setAdapter(adapter);

      }
  }
