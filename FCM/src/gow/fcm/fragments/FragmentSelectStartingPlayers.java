package gow.fcm.fragments;

import gow.fcm.footballcoachmanager.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentSelectStartingPlayers extends Fragment {
	private OnSelectStartingPlayers mCallback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_initial_chosen_players,
				container, false);

		LinearLayout ll = (LinearLayout) view.findViewById(R.id.LinearLayout_starting_players);
		ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCallback.onClickEvent();
			}
		});

		return view;
	}

	public interface OnSelectStartingPlayers {
		public void onClickEvent();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnSelectStartingPlayers) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " debe implementar la clase OnSelectStartingPlayers");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallback = null;
	}

}
