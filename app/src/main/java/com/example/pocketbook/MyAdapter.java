package com.example.pocketbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private Context context;
    private int images[];
    private String names[];
    private int color[];

    public MyAdapter(Context context, int[] images, String[] names, int[] color) {
        this.context = context;
        this.images = images;
        this.names = names;
        this.color = color;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View Layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homepage_grid,null);
        MyHolder myHolder = new MyHolder(Layout);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
        myHolder.img.setImageResource(images[i]);
        myHolder.txt.setText(names[i]);
        myHolder.txt.setBackgroundResource(color[i]);

        myHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;

                switch (i){

                    case 0:
                        intent = new Intent(context,CenterActivity.class);
                        intent.putExtra("position","bills");
                        break;
                    case 1:
                        intent = new Intent(context,CenterActivity.class);
                        intent.putExtra("position","travel");
                        break;
                    case 2:
                        intent = new Intent(context,CenterActivity.class);
                        intent.putExtra("position","shopping");
                        break;
                    case 3:
                        intent = new Intent(context,CenterActivity.class);
                        intent.putExtra("position","fuels");
                        break;
                    case 4:
                        intent = new Intent(context,CenterActivity.class);
                        intent.putExtra("position","health");
                        break;
                    case 5:
                        intent = new Intent(context,CenterActivity.class);
                        intent.putExtra("position","entertainment");
                        break;
                    case 6:
                        intent = new Intent(context,CenterActivity.class);
                        intent.putExtra("position","restaurant");
                        break;
                    case 7:
                        intent = new Intent(context,CenterActivity.class);
                        intent.putExtra("position","groceries");
                        break;
                    case 8:
                        intent = new Intent(context,CenterActivity.class);
                        intent.putExtra("position","EMI");
                        break;
                }
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView txt;
        public RelativeLayout relativeLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgview);
            txt = itemView.findViewById(R.id.txtview);
            relativeLayout = itemView.findViewById(R.id.list);
        }
    }

}
