package com.example.naren.munch.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naren.munch.R;
import com.example.naren.munch.model.Comment;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private static class CommentRowViewHolder {

        View comment_indicator_space;
        LinearLayout comment_indicator_color, comment_container;
        TextView comment_author, comment_score, comment_time, comment_body;

    }

    private final static int[] commentIndicatorColours = {R.color.blue_800,
            R.color.purple_800,
            R.color.green_800,
            R.color.orange_800,
            R.color.red_800,
            R.color.yellow_800
    };

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
        this.addAll(comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommentRowViewHolder holder;

        Comment comment = getItem(position);

        if (convertView == null) {

            convertView = View.inflate(getContext(), R.layout.comment_row, null);
            holder = new CommentRowViewHolder();

            holder.comment_indicator_space = convertView.findViewById(R.id.comment_indicator_space);
            holder.comment_indicator_color = (LinearLayout) convertView.findViewById(R.id.comment_indicator_color);
            holder.comment_author = (TextView) convertView.findViewById(R.id.comment_author);
            holder.comment_score = (TextView) convertView.findViewById(R.id.comment_score);
            holder.comment_body = (TextView) convertView.findViewById(R.id.comment_body);

            convertView.setTag(holder);
        } else {
            holder = (CommentRowViewHolder) convertView.getTag();
        }
        holder.comment_author.setText(comment.getComment_author());
        holder.comment_score.setText(parent.getContext().getString(R.string.comment_karma, comment.getComment_score()));
        holder.comment_body.setText(Html.fromHtml(comment.getComment_body()));
        holder.comment_body.setMovementMethod(LinkMovementMethod.getInstance());

        ViewGroup.LayoutParams layoutParams = holder.comment_indicator_space.getLayoutParams();
        layoutParams.width = comment.level * 8;
        holder.comment_indicator_color.setBackgroundColor(ContextCompat.getColor(getContext(), commentIndicatorColours[comment.level % commentIndicatorColours.length]));
        holder.comment_indicator_color.setVisibility(comment.level == 0 ? View.GONE : View.VISIBLE);
        holder.comment_indicator_space.setLayoutParams(layoutParams);

        return convertView;
    }

}

