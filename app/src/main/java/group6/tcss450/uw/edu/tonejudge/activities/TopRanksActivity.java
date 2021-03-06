package group6.tcss450.uw.edu.tonejudge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import group6.tcss450.uw.edu.tonejudge.R;
import group6.tcss450.uw.edu.tonejudge.model.Tone;

/**
 * First activity when viewing top ranks. Displays a list containing all the different tones so that
 * the user can choose one.
 *
 * @author Hunter Bennett
 */
public class TopRanksActivity extends NavDrawerActivity implements ExpandableListView.OnGroupClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ranks);
        ExpandableListView mListView = (ExpandableListView) findViewById(R.id.top_ranks_expandable_list);
        Adapter mAdapter = new Adapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnGroupClickListener(this);
        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            mListView.expandGroup(i);
        }
        onCreateDrawer();
    }

    /**
     * Ignores clicks on groups.
     *
     * @return true.
     */
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return true;
    }

    /**
     * When the info icon is clicked. Displays the description of the tone in a dialog.
     */
    public void onClickInfo(View v) {
        Tone tone = (Tone) v.getTag();
        new AlertDialog.Builder(this)
                .setMessage(tone.getDescription(this))
                .setTitle(tone.getName())
                .show();
    }

    /**
     * When the element with the name of the tone is clicked. Changes to the TopRanksToneActivity.
     */
    public void onClickTone(View v) {
        Intent intent = new Intent(this, TopRanksToneActivity.class);
        intent.putExtra("tone", (Tone) v.getTag());
        startActivity(intent);
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
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.top_ranks_group_name, parent, false);
                tv.setText(groups[groupPosition].getName());
                return tv;
            }
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView != null) {
                return convertView;
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_ranks_element, parent, false);
                TextView toneView = (TextView) view.findViewById(R.id.top_ranks_element_tone);
                View infoView = view.findViewById(R.id.top_ranks_element_info);
                Tone tone = groups[groupPosition].getTones().get(childPosition);
                toneView.setText(tone.getName());
                toneView.setTag(tone);
                infoView.setTag(tone);
                toneView.setBackgroundColor(getResources().getColor(tone.getDarkColorId()));
                ViewCompat.setElevation(toneView, 8.0f);
                return view;
            }
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
