package lugassi.wallach.android5778_2638_6575.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 10/12/2017.
 */

public class MyListAdapter <T> extends BaseAdapter {

    private final Context mContext;
    private List<T> mData;

    public MyListAdapter(final Context context, final ArrayList<T> mData) {
        this.mData = mData;
        this.mContext = context;
    }

    public void setData(ArrayList<T> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
     //   this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

// implement all abstract methods here
}
