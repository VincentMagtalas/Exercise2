package com.philsmile.exercise2.Adapters;

/**
 * Created by philsmile on 2/13/2018.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by philsmile on 2/8/2018.
 */
import com.philsmile.exercise2.MainActivity;
import com.philsmile.exercise2.R;
import com.philsmile.exercise2.Classes.DisplayPost;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {

    private ArrayList<DisplayPost> postList;
    private Context context;

    public PostRecyclerViewAdapter(ArrayList<DisplayPost> cLst, Context ctx) {
        postList = cLst;
        context = ctx;
    }

    @Override
    public PostRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

        PostRecyclerViewAdapter.ViewHolder viewHolder = new PostRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostRecyclerViewAdapter.ViewHolder holder, final int position) {
        final DisplayPost post = postList.get(position);


        String postid = post.getPostID().toString();
        String username = post.getUserName().toString();
        String body = post.getBody().toString();

        holder.tvName.setText(username);
        holder.tvBody.setText(body);

        holder.cardItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(context,"need to add to bookmark",Toast.LENGTH_LONG).show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public CardView cardItem;
        public TextView tvName;
        public TextView tvBody;

        public ViewHolder(View view) {
            super(view);
            cardItem = (CardView) view.findViewById(R.id.cardItem);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvBody = (TextView) view.findViewById(R.id.tvBody);

        }

    }
}
