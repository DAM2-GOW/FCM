package gow.fcm.fragments;

import gow.fcm.footballcoachmanager.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FragmentFinishEvent extends Fragment {
	private OnClickFinishEvent mCallback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_action_finish_event,
				container, false);

		Button btn = (Button) view.findViewById(R.id.btn_show_blackboard);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCallback.onFinishEvent();
			}
		});

		return view;
	}

	public interface OnClickFinishEvent {
		public void onFinishEvent();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnClickFinishEvent) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " debe implementar la clase OnShowBlackBoardFragment");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallback = null;
	}
}
