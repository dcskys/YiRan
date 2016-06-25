package com.dc.androidtool.DouBanMovie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * FragmentStatePagerAdapter  管理fragment  一不存在就销毁
 */
public class ViewToolsAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;
    private List<String>   tableList;
   // private String[] TabTitles = {"RecyclerView", "ListView"};


    public ViewToolsAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> tableList) {
        super(fm);

        this.fragmentList = fragmentList;
        this.tableList = tableList;
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return tableList.get(position);
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    //多出2个方法，不保存fragment
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
