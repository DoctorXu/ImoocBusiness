package com.doctor.imoocvoice.view.home.adapter;

import com.doctor.imoocvoice.view.discovery.DiscoveryFragment;
import com.doctor.imoocvoice.view.friend.FriendFragment;
import com.doctor.imoocvoice.view.home.model.CHANNEL;
import com.doctor.imoocvoice.view.mine.MineFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Doctor on 2021/1/11.
 * 首页ViewPagerAdapter
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private CHANNEL[] mList;

    public HomePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public HomePagerAdapter(@NonNull FragmentManager fm, int behavior, CHANNEL[] datas) {
        super(fm, behavior);
        mList = datas;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        int value = mList[position].getValue();
        switch (value) {
            case CHANNEL.MINE_ID:
                return MineFragment.newInstance();

            case CHANNEL.DISCOVERY_ID:
                return DiscoveryFragment.newInstance();

            case CHANNEL.FRIEND_ID:
                return FriendFragment.newInstance();

            //case CHANNEL.VIDEO_ID:
            //return VideoFragment.newInstance;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.length;
        }
    }
}
