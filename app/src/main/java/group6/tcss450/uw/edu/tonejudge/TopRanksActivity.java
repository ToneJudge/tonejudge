package group6.tcss450.uw.edu.tonejudge;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class TopRanksActivity extends NavDrawerActivity {

    private ExpandableListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ranks);
        mListView = (ExpandableListView) findViewById(R.id.top_ranks_expandable_list);
        Adapter adapter = new Adapter();
        mListView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            mListView.expandGroup(i);
        }
        onCreateDrawer();
    }

    private class Adapter extends BaseExpandableListAdapter {

        private Tone.Category[] groups = Tone.Category.values();

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return groups[groupPosition].getTones().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return groups[groupPosition].getTones().get(childPosition);
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
            if (convertView != null) {
                return convertView;
            } else {
                TextView tv = new TextView(TopRanksActivity.this);
                tv.setText(groups[groupPosition].getName());
                return tv;
            }
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView != null) {
                return convertView;
            } else {
                TextView tv = new TextView(TopRanksActivity.this);
                tv.setText(groups[groupPosition].getTones().get(childPosition).getName());
                return tv;
            }
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
