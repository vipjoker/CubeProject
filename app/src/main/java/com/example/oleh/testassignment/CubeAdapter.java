package com.example.oleh.testassignment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oleh.testassignment.model.Cube;

import java.util.List;

public class CubeAdapter  extends RecyclerView.Adapter<CubeAdapter.CubeViewHodler>{

    private final List<Cube> itemList;

    public CubeAdapter(List<Cube> cubes){
        this.itemList = cubes;
    }

    @NonNull
    @Override
    public CubeViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cube, viewGroup, false);

        return new CubeViewHodler(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CubeViewHodler cubeViewHodler, int position) {




        Cube cube = itemList.get(position);
        cubeViewHodler.cubeView.setDimmensions(cube.getWidth(),cube.getLength(),cube.getHeigth());
        cubeViewHodler.cubeView.setColor(cube.getColor());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class CubeViewHodler extends RecyclerView.ViewHolder{

        CubeView cubeView;
        public CubeViewHodler(@NonNull View itemView) {
            super(itemView);
            cubeView = itemView.findViewById(R.id.cubeview);
        }
    }
}
