package com.example.naren.munch.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.naren.munch.R;
import com.example.naren.munch.fragments.RedditPostFragment;

public class SubredditViewPagerAdapter extends FragmentStatePagerAdapter {

    private final static int[] subredditIds = {
            R.id.item_frontpage, R.id.item_all, R.id.item_aww,
            R.id.item_art, R.id.item_askreddit, R.id.item_askscience, R.id.item_announcements,
            R.id.item_blog, R.id.item_books, R.id.item_creepy, R.id.item_dataisbeautiful,
            R.id.item_diy, R.id.item_documentaries, R.id.item_earthporn, R.id.item_eli5,
            R.id.item_fitness, R.id.item_food, R.id.item_funny, R.id.item_futurology,
            R.id.item_gadgets, R.id.item_gaming, R.id.item_getmotivated, R.id.item_gifs,
            R.id.item_history, R.id.item_iama, R.id.item_internetisbeautiful, R.id.item_jokes, R.id.item_lpt,
            R.id.item_listentothis, R.id.item_mildlyinteresting, R.id.item_movies, R.id.item_music,
            R.id.item_news, R.id.item_nosleep, R.id.item_nottheonion, R.id.item_oldschoolcool,
            R.id.item_personalfinance, R.id.item_philosophy, R.id.item_photoshopbattles, R.id.item_pics,
            R.id.item_science, R.id.item_showerthoughts, R.id.item_space, R.id.item_sports,
            R.id.item_television, R.id.item_tifu, R.id.item_til, R.id.item_twox,
            R.id.item_upnews, R.id.item_videos, R.id.item_worldnews, R.id.item_writingprompts
    };

    private final static String[] FEED_TITLES =

            {"frontpage", "all", "aww", "art", "askreddit", "askscience",
                    "announcements", "blogs", "books", "creepy", "dataisbeautiful", "DIY", "Documentaries",
                    "earthporn", "explainlimeimfive", "fitness", "food", "funny", "futurology", "gadgets", "gaming", "getmotivated",
                    "gifs", "history", "iama", "internetisbeautiful", "jokes", "lifeprotips",
                    "listentothis", "mildlyinteresting", "movies", "Music", "news", "nosleep",
                    "nottheonion", "oldschoolcool", "personalfinance", "philosophy",
                    "photoshopbattles", "pics", "science", "showerthoughts", "space", "sports",
                    "television", "tifu", "todayilearned", "twoxchromosomes", "upliftingnews", "videos", "worldnews", "writingprompts"
            };

    public SubredditViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return RedditPostFragment.newInstance(FEED_TITLES[position], "");
    }

    @Override
    public int getCount() {
        return FEED_TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FEED_TITLES[position];
    }

    private int lookupSubredditIdPosition(int itemId) {
        int position = 0;
        for (int i = 0; i < subredditIds.length; i++)
        {
            if (subredditIds[i] == itemId)
            {
                position = i;
                break;
            }
        }
        return position;
    }

    public int positionForSubreddit(int itemId) {
        return lookupSubredditIdPosition(itemId);
    }
}
