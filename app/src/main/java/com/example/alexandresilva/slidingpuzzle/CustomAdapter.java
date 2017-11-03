package com.example.alexandresilva.slidingpuzzle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by alexandresilva on 2017-10-19.
 * Base on the source code from:
 * https://github.com/DaveNOTDavid/sample-puzzle/tree/master/app/src/main/java/com/davenotdavid/samplepuzzle
 */

public class CustomAdapter extends BaseAdapter {

    private ArrayList<ImageButton> myButtons;
    private int myColumnWidth;
    private int myColumnHeight;

    public CustomAdapter(ArrayList<ImageButton> buttons, int columnWigth, int columnHeight) {
        this.myButtons = buttons;
        this.myColumnWidth = columnWigth;
        this.myColumnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return myButtons.size();
    }

    @Override
    public Object getItem(int position) {
        return (Object) myButtons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        ImageButton button;

        if (contentView == null) {
            button =  myButtons.get(position);
        }
        else {
            button = (ImageButton) contentView;
        }

        android.widget.AbsListView.LayoutParams params =  new android.widget.AbsListView.LayoutParams(myColumnWidth, myColumnHeight);

        button.setLayoutParams(params);

        return button;
    }
}
