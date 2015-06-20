package com.pioneer.aaron.expandablelistview_anim;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import com.pioneer.aaron.expandablelistview_anim.Adapters.ExpandableListAdapter;
import com.pioneer.aaron.expandablelistview_anim.Constans.ExpandableListView_Helper.*;
import com.pioneer.aaron.expandablelistview_anim.Widgets.AnimatedExpandableListView;

/**
 * This is a demo for expandablelistview with animations
 * */
public class MainActivity extends ActionBarActivity {

    private AnimatedExpandableListView expandableListView;
    private ExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        List<GroupItem> items = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            GroupItem groupItem = new GroupItem();
            groupItem.title = "Group " + i;
            for (int j = 0; j < i; ++j) {
                ChildItem childItem = new ChildItem();
                childItem.title = "hello" + i;
                childItem.hint = "world" + j;

                groupItem.items.add(childItem);
            }

            items.add(groupItem);
        }

        adapter = new ExpandableListAdapter(this);
        adapter.setData(items);

        expandableListView = (AnimatedExpandableListView) findViewById(R.id.animated_listview);
        expandableListView.setAdapter(adapter);

        /*
        * click on group to expand/collapse
        * */
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (expandableListView.isGroupExpanded(groupPosition)) {
                    expandableListView.collapseGroupWithAnimation(groupPosition);
                } else {
                    expandableListView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
        });

        /*
        * click on child item to do things
        * */
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
