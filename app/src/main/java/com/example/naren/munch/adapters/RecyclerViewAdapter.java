package com.example.naren.munch.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.naren.munch.R;
import com.example.naren.munch.activities.DetailedPostActivity;
import com.example.naren.munch.activities.DetailedSelfPostActivity;
import com.example.naren.munch.activities.ExpandedImageView;
import com.example.naren.munch.activities.GifActivity;
import com.example.naren.munch.activities.WebActivity;
import com.example.naren.munch.activities.YoutubeActivity;
import com.example.naren.munch.model.RedditPost;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PostViewHolder> {

    //find each views to bind the data above
    public class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView mPostTitle, mPostScore, mComments, mSubreddit, mAuthor,
                mDomain, mLinkDomain;
        private ImageView mPostImage;
        private LinearLayout mPlaceholder, mMainContent;

        public PostViewHolder(final View itemView) {
            super(itemView);

            mPostTitle = (TextView) itemView.findViewById(R.id.post_title_textView);
            mPostImage = (ImageView) itemView.findViewById(R.id.post_image_imageView);
            mPlaceholder = (LinearLayout) itemView.findViewById(R.id.post_link_placeholder);
            mPostScore = (TextView) itemView.findViewById(R.id.post_score);
            mComments = (TextView) itemView.findViewById(R.id.post_comments);
            mSubreddit = (TextView) itemView.findViewById(R.id.post_subreddit);
            mDomain = (TextView) itemView.findViewById(R.id.post_domain);
            mAuthor = (TextView) itemView.findViewById(R.id.post_author);
            mLinkDomain = (TextView) itemView.findViewById(R.id.link_domain);
            mMainContent = (LinearLayout) itemView.findViewById(R.id.main_content_linear);

            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mCardView.setPreventCornerOverlap(false);

        }

    }

    private Context context;
    private ArrayList<RedditPost> posts;
    private View view;
    private CardView mCardView;

    public RecyclerViewAdapter(Context context, ArrayList<RedditPost> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflate the layout and pass to the viewholder to find each item
        view = LayoutInflater.from(context).inflate(R.layout.single_row, parent, false);

        PostViewHolder viewHolder = new PostViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {

        final RedditPost redditPost = posts.get(position);

        //get all the relevant post data and then bind it to the
        //appropriate views below
        final String url = redditPost.getUrl();
        final String jpegImageUrl = url + ".jpg";
        final String title = redditPost.getTitle();
        final String author = redditPost.getAuthor();
        final String subreddit = redditPost.getSubreddit();
        final String domain = redditPost.getDomain();
        final String selftext_html = redditPost.getSelftext_html();
        final String permalink = redditPost.getPermalink();
        final String thumbnail = redditPost.getThumbnail();
        final String youtube_thumbnail = redditPost.getYoutubeThumbnail();
        final int postScore = redditPost.getScore();
        final int comments = redditPost.getComments();

        //load url into the image view and handle the different media types below (videos, gifs, images...)
        Glide.with(context).load(url).into(holder.mPostImage);

        //handle images
        if (domain.equals("imgur.com") || domain.equals("i.imgur.com") || domain.equals("m.imgur.com")) {

            if (!url.equals(jpegImageUrl)) {
                Glide.with(context).load(jpegImageUrl).into(holder.mPostImage);
            } else {
                Glide.with(context).load(url).into(holder.mPostImage);
            }

            holder.mPlaceholder.setVisibility(View.GONE);
            holder.mPostImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExpandedImageView.startExpandedImageViewActivity(context, jpegImageUrl);
                }
            });
        }

        //handle gif content
        if (url.contains(".gif") || url.contains("gfy")) {

            Glide.with(context).load(thumbnail).into(holder.mPostImage);

            holder.mPlaceholder.setVisibility(View.VISIBLE);
            holder.mLinkDomain.setText("[Gif] " + domain);

            View.OnClickListener gifClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GifActivity.startGifActivity(context, url);
                }
            };

            holder.mPostImage.setOnClickListener(gifClickListener);
            holder.mPlaceholder.setOnClickListener(gifClickListener);


            //handle youtube content
        } else if (domain.equals("youtube.com") || domain.equals("youtu.be")
                || domain.equals("m.youtube.com")) {

            Glide.with(context).load(youtube_thumbnail).into(holder.mPostImage);

            holder.mPlaceholder.setVisibility(View.VISIBLE);
            holder.mLinkDomain.setText("[Video] " + domain);

            View.OnClickListener youtubeClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    YoutubeActivity.startYoutubeActivity(context, url);
                }
            };

            holder.mPostImage.setOnClickListener(youtubeClickListener);
            holder.mPlaceholder.setOnClickListener(youtubeClickListener);
        }

        //handle all other content
        View.OnClickListener webClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebActivity.startWebActivity(context, url);
            }
        };

        if (domain.contains("imgur") || domain.contains("youtube") ||
                domain.contains("youtu.be") || url.contains(".gif") || url.contains("gfy")
                || url.contains(".jpg") || url.equals(jpegImageUrl)) {

            if (url.contains("/gallery/") || url.contains("/a/")) {

                Glide.with(context).load(thumbnail).into(holder.mPostImage);

                holder.mPlaceholder.setVisibility(View.VISIBLE);
                holder.mLinkDomain.setText("[Album] " + domain);

                holder.mPostImage.setOnClickListener(webClickListener);
                holder.mPlaceholder.setOnClickListener(webClickListener);
            }

        } else {

            holder.mPlaceholder.setVisibility(View.VISIBLE);
            holder.mLinkDomain.setText("[Link] " + domain);

            holder.mPlaceholder.setOnClickListener(webClickListener);
        }

        //launch relevant detail post based on the domain and pass relevant data to each activity
        holder.mMainContent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //self post
                        if (domain.contains("self")) {
                            DetailedSelfPostActivity.startDetailedSelfPostActivity(context, selftext_html, title, author, subreddit, domain, postScore, comments, permalink);
                        } else {
                            DetailedPostActivity.startDetailedPostActivity(context, url, title, author, subreddit, domain, postScore, comments, permalink, thumbnail, youtube_thumbnail);
                        }
                    }
                }
        );

        holder.mPostTitle.setText(redditPost.getTitle());
        holder.mAuthor.setText(redditPost.getAuthor());
        holder.mSubreddit.setText(redditPost.getSubreddit());
        holder.mDomain.setText(redditPost.getDomain());
        holder.mPostScore.setText(redditPost.getScore() + " points");
        holder.mComments.setText(redditPost.getComments() + " comments");
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
