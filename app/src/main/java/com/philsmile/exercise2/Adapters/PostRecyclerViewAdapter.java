package com.philsmile.exercise2.Adapters;

/**
 * Created by philsmile on 2/13/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by philsmile on 2/8/2018.
 */
import com.philsmile.exercise2.AppController;
import com.philsmile.exercise2.MainActivity;
import com.philsmile.exercise2.R;
import com.philsmile.exercise2.Classes.DisplayPost;
import com.philsmile.exercise2.db.Bookmark;
import com.philsmile.exercise2.db.BookmarkDao;
import com.philsmile.exercise2.db.DaoSession;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>{

    private ArrayList<DisplayPost> postList;
    private Context context;
    private AppCompatActivity cmain;
    Context contexts;

    DaoSession daoSession;

    public PostRecyclerViewAdapter(ArrayList<DisplayPost> cLst, Context ctx) {
        postList = cLst;
        context = ctx;
        daoSession = ((AppController) context).getDaoSession();
    }

    @Override
    public PostRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

        contexts = view.getContext();

        PostRecyclerViewAdapter.ViewHolder viewHolder = new PostRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PostRecyclerViewAdapter.ViewHolder holder, final int position) {
        final DisplayPost post = postList.get(position);


        String postid = post.getPostID().toString();
        String username = post.getUserName().toString();
        String body = post.getBody().toString();

        holder.tvName.setText(username);
        holder.tvBody.setText(body);

        holder.cardItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog(position,holder);
                return false;
            }
        });

    }

    private void showDialog(final int position, final ViewHolder holder) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(contexts);

        // set title
        alertDialogBuilder.setTitle("Bookmark");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to add this item to bookmark?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        insertItem(position);
                        holder.imgBookmarks.setVisibility(View.VISIBLE);

                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    private void insertItem(int position){
        DisplayPost dp = postList.get(position);

        BookmarkDao bookmarkDao = daoSession.getBookmarkDao();
        Bookmark bookmark = new Bookmark();

        bookmark.setPostid(dp.getPostID().toString());
        bookmark.setUserid(Integer.parseInt(dp.getUserID().toString()));
        bookmark.setUsername(dp.getUserName().toString());
        bookmark.setTitle(dp.getTitle().toString());
        bookmark.setBody(dp.getBody().toString());

        bookmarkDao.insert(bookmark);
        Toast.makeText(context, "Item Added to Bookmark", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public CardView cardItem;
        public TextView tvName;
        public TextView tvBody;
        public ImageView imgBookmarks;

        public ViewHolder(View view) {
            super(view);
            cardItem = (CardView) view.findViewById(R.id.cardItem);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvBody = (TextView) view.findViewById(R.id.tvBody);
            imgBookmarks = (ImageView) view.findViewById(R.id.imageView2);
        }

    }
}
