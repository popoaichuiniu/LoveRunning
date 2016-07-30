package com.popoaichuiniu.jacy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.popoaichuiniu.jacy.myapplication.DistanceChooseModel;
import com.popoaichuiniu.jacy.R;
import com.popoaichuiniu.jacy.myapplication.TimeChooseModel;

/**
 * Created by jacy on 2016/1/19.
 */
public class MyRecyclerViewAdapter extends  RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private String[] mDataset;
    private Context context;




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView mTextView;
        public Context context;
        public ViewHolder(View v,Context context) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.my_recycler_view_textView);
            this.context=context;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {   if(getLayoutPosition()==0)
            {

            }
            if(getLayoutPosition()==1)
            {Intent intent=new Intent(context, TimeChooseModel.class);
                context.startActivity(intent);

            }
            if(getLayoutPosition()==2)
            {Intent intent=new Intent(context, DistanceChooseModel.class);
                context.startActivity(intent);

            }


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRecyclerViewAdapter(String[] myDataset,Context context) {
        mDataset = myDataset;
        this.context=context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.my_recycle_view_item, null);
        // set the view's size, margins, paddings and layout parameters
       // ...
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        (int)TypedValue.applyDimension
                                (TypedValue.COMPLEX_UNIT_DIP,48,context.getResources().getDisplayMetrics()));

        v.setLayoutParams(layoutParams);
        ViewHolder vh = new ViewHolder(v,context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
