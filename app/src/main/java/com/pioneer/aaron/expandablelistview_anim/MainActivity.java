package com.pioneer.aaron.expandablelistview_anim;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

    private static class GroupItem {
        String title;
        List<ChildItem> items = new ArrayList<ChildItem>();
    }

    private static class ChildItem {
        String title;
        String hint;
    }

    private static class ChildHolder {
        TextView title;
        TextView hint;
    }

    private static class GroupHolder {
        TextView title;
    }

    /**
     * adapter for customized expandablelistview(anim)
     * */
    private class ExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

        private LayoutInflater inflater;
        private List<GroupItem> items;

        public ExpandableListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<GroupItem> items) {
            this.items = items;
        }

        @Override
        public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder childHolder;
            ChildItem childItem = getChild(groupPosition, childPosition);
            if (convertView == null) {
                childHolder = new ChildHolder();
                convertView = inflater.inflate(R.layout.list_item, parent, false);
                childHolder.title = (TextView) convertView.findViewById(R.id.textTitle);
                childHolder.hint = (TextView) convertView.findViewById(R.id.textHint);
                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) convertView.getTag();
            }

            childHolder.title.setText(childItem.title);
            childHolder.hint.setText(childItem.hint);

            return convertView;
        }

        @Override
        public int getRealChildrenCount(int groupPosition) {
            return items.get(groupPosition).items.size();
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public GroupItem getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public ChildItem getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).items.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder groupHolder;
            GroupItem groupItem = getGroup(groupPosition);

            if (convertView == null) {
                groupHolder = new GroupHolder();
                convertView = inflater.inflate(R.layout.group_item, parent, false);

                groupHolder.title = (TextView) convertView.findViewById(R.id.textTitle);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }

            groupHolder.title.setText(groupItem.title);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}
