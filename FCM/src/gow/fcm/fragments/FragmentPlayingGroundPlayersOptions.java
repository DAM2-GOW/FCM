package gow.fcm.fragments;


import gow.fcm.footballcoachmanager.R;
import gow.fcm.sentencias.SentenciasSQLitePlayingGround;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentPlayingGroundPlayersOptions extends Fragment {
	private OnPlayerOptionClickEvent mCallback;
	private ViewGroup container;
	private LayoutInflater inflater;
	SentenciasSQLitePlayingGround sentences = new SentenciasSQLitePlayingGround();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.container=container;
		this.inflater=inflater;
		View view = SelectViewToShow(this.getArguments().getInt("lastDimension"),
				this.getArguments().getInt("comeFromRbtn"),this.getArguments().getInt("branchType"));
		
		return view;
	}

	/**
	 * Este metodo genera la interfaz en base a los parametros recibidos.
	 * Implementa los objetos de cada interfaz y añadidos.
	 */
	@SuppressLint("CutPasteId")
	private View SelectViewToShow(final int lastDimension, final int comeFromRbtn, final int branchType) {
		View view = null;
		final int Dimension = lastDimension+1;

		switch (Dimension) { // Selecciona la dimension.
		case 1: // Caso de dimension 1.
			Log.d("DEBUG", "ENTRA DIMENSION 1");
			int[] pid = new int[1];
			pid[0] = this.getArguments().getInt("playerID");
			sentences.ObtenerDatosDelimitadosJugadores(getActivity(), pid); // Mandamos la orden de obtener los datos de los jugadores.
			int[] res = new int[1];
			res = sentences.getTipo();
			final int branch_type = res[0];

			switch (branch_type) { // Dentro de la dimension 1, se selecciona una variable.
			case 0: // Determina rama y Layout para Ataque.
				view = inflater.inflate(R.layout.fragment_dim1_opt1234att,
						container, false);
				RadioGroup rgroup_att = (RadioGroup) view.findViewById(R.id.rGroup_att_1);
				rgroup_att.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId){
						// Obtiene el numero del RadioButton que se ha pulsado.
						int rbtnID = group.indexOfChild(container.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
						RadioButton rbtn = (RadioButton) container.findViewById(group.getCheckedRadioButtonId()); // Objeto rbtn.
						String RBTN_CONTENT = rbtn.getText().toString(); // Texto asociado al objeto rbtn.
						mCallback.onClickOptionEvent(rbtnID, Dimension, branch_type, getString(R.string.playingGround_fragment_final_words_1)+" "+RBTN_CONTENT);
					}
				});
				break;
			case 1: // Determina rama y Layout para Defensa.
				view = inflater.inflate(R.layout.fragment_dim1_opt123def,
						container, false);
				RadioGroup rgroup_def = (RadioGroup) view.findViewById(R.id.rGroup_def_1);
				rgroup_def.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId){
						// Obtiene el numero del RadioButton que se ha pulsado.
						int rbtnID = group.indexOfChild(container.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
						RadioButton rbtn = (RadioButton) container.findViewById(group.getCheckedRadioButtonId()); // Objeto rbtn.
						String RBTN_CONTENT = rbtn.getText().toString(); // Texto asociado al objeto rbtn.
						mCallback.onClickOptionEvent(rbtnID, Dimension, branch_type, getString(R.string.playingGround_fragment_final_words_1)+" "+RBTN_CONTENT);
					}
				});
				break;
			case 2: // Determina rama y Layout para Equipos Especiales.
				view = inflater.inflate(R.layout.fragment_dim1_opt12345ee,
						container, false);
				RadioGroup rgroup_ee = (RadioGroup) view.findViewById(R.id.rGroup_ee_1);
				rgroup_ee.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId){
						// Obtiene el numero del RadioButton que se ha pulsado.
						int rbtnID = group.indexOfChild(container.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
						RadioButton rbtn = (RadioButton) container.findViewById(group.getCheckedRadioButtonId()); // Objeto rbtn.
						String RBTN_CONTENT = rbtn.getText().toString(); // Texto asociado al objeto rbtn.
						mCallback.onClickOptionEvent(rbtnID, Dimension, branch_type, getString(R.string.playingGround_fragment_final_words_1)+" "+RBTN_CONTENT);
					}
				});
				break;
			default:
				break;
			}
			break;
		case 2: // Caso de dimension 2.
			Log.d("DEBUG", "ENTRA DIMENSION 2");
			switch(branchType){
			case 0: // Determina la rama de Layout para Ataque.
				switch(comeFromRbtn){ // Determina el Layout a utilizar segun rbtn seleccionado.
				case 0: // Sumar 6 puntos y seleccion de yardas.
					view = inflater.inflate(R.layout.fragment_dim2_opt12att_opt12def_opt1234ee,
							container, false);
					final NumberPicker np0 = (NumberPicker) view.findViewById(R.id.numberPicker1Dim2);
					np0.setMaxValue(100);
					np0.setMinValue(0);
					np0.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					np0.setOnValueChangedListener(new OnValueChangeListener() {
						
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
							// TODO Auto-generated method stub
							Log.d("MARCADOR", "+6 PUNTOS");
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_2)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick));
						}
					});
					break;
				case 1: // Seleccion yardas.
					view = inflater.inflate(R.layout.fragment_dim2_opt12att_opt12def_opt1234ee,
							container, false);
					final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1Dim2);
					np1.setMaxValue(100);
					np1.setMinValue(0);
					np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					np1.setOnValueChangedListener(new OnValueChangeListener() {
						
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
							// Envia los datos.
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_2)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick));
						}
					});
					break;
				case 2: // Seleccion yardas y seleccion jugador recibidor.
					view = inflater.inflate(R.layout.fragment_dim2_opt3att,
							container, false);
					
					sentences.ObtenerDatosDelimitadosJugadores(getActivity(), this.getArguments().getIntArray("playersOnTheField")); // Mandamos la orden de obtener los datos de los jugadores.
					String[] nombreApellidoJugadores = sentences.getNombreApellidos();
					final Spinner sp = (Spinner) view.findViewById(R.id.spinnerDim2);
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(),
							android.R.layout.simple_spinner_item, nombreApellidoJugadores);
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp.setAdapter(dataAdapter);
					sp.setEnabled(false);
					final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker2Dim2);
					np2.setMaxValue(100);
					np2.setMinValue(0);
					np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); // Se desactiva el teclado para el NumberPicker.
					np2.setOnValueChangedListener(new OnValueChangeListener() {
						
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, final int newVal) {
							// Recoge el numero y habilita spinner.
							sp.setEnabled(true);
							sp.setOnItemSelectedListener(new OnItemSelectedListener() {

								@SuppressLint("ResourceAsColor")
								@Override
								public void onItemSelected(
										AdapterView<?> parent, View view, int pos,
							            long id) {
									// Obtiene el texto del selector y envia datos.
									//((TextView) parent.getChildAt(0)).setTextColor(R.color.white);
									String SPtext = sp.getSelectedItem().toString();
									mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_2)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick)+" "+getString(R.string.playingGround_fragment_final_words_9)+" "+SPtext);
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {
									// Nada.
									
								}
								
							});
						}
					});
					break;
				case 3: // Condicional Si/No.
					view = inflater.inflate(R.layout.fragment_dim2_opt4att_opt3def_opt5ee_dim3_opt23att_opt1def,
							container, false);
					TextView tv0 = (TextView) view.findViewById(R.id.textViewDim3);
					tv0.setText(getString(R.string.playingGround_fragment_dim2_att4_def3_ee5_textView_conditional));
					RadioGroup rgroup = (RadioGroup) view.findViewById(R.id.radioGroupDim3);
					rgroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId){
							// Obtiene el numero del RadioButton que se ha pulsado y asigna un string.
							int rbtnID = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
							String content = null;
							if(rbtnID==0){
								content = getString(R.string.playingGround_fragment_final_words_3);
							}else{
								content = getString(R.string.playingGround_fragment_final_words_4);
							}
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, content);
						}
					});
					break;
				default:
					break;
				}
				break;
			case 1: // Determina la rama de Layout para Defensa.
				switch(comeFromRbtn){
				case 0: // Seleccion yardas.
					view = inflater.inflate(R.layout.fragment_dim2_opt12att_opt12def_opt1234ee,
							container, false);
					final NumberPicker np0 = (NumberPicker) view.findViewById(R.id.numberPicker1Dim2);
					np0.setMaxValue(100);
					np0.setMinValue(0);
					np0.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					np0.setOnValueChangedListener(new OnValueChangeListener() {
						
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
							// Envia los resultados.
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_2)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick));
						}
					});
					break;
				case 1: // Seleccion yardas.
					view = inflater.inflate(R.layout.fragment_dim2_opt12att_opt12def_opt1234ee,
							container, false);
					final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1Dim2);
					Log.d("MARCADOR", "DOWN A 1");
					np1.setMaxValue(100);
					np1.setMinValue(0);
					np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					np1.setOnValueChangedListener(new OnValueChangeListener() {
						
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
							// Envia los resultados.
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_2)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick));
						}
					});
					break;
				case 2: // Condicional Si/No.
					view = inflater.inflate(R.layout.fragment_dim2_opt4att_opt3def_opt5ee_dim3_opt23att_opt1def,
							container, false);
					TextView tv0 = (TextView) view.findViewById(R.id.textViewDim3);
					tv0.setText(getString(R.string.playingGround_fragment_dim2_att4_def3_ee5_textView_conditional));
					RadioGroup rgroup = (RadioGroup) view.findViewById(R.id.radioGroupDim3);
					rgroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId){
							// Obtiene el numero del RadioButton que se ha pulsado y asigna un string.
							int rbtnID = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
							String content = null;
							if(rbtnID==0){
								content = getString(R.string.playingGround_fragment_final_words_3);
							}else{
								content = getString(R.string.playingGround_fragment_final_words_4);
							}
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, content);
						}
					});
					break;
				default:
					break;
				}
				break;
			case 2: // Determina la rama de Layout para Equipos Especiales.
				switch(comeFromRbtn){
				case 0: // Seleccion yardas y suma 3 puntos.
					view = inflater.inflate(R.layout.fragment_dim2_opt12att_opt12def_opt1234ee,
							container, false);
					Log.d("MARCADOR", "+3 PUNTOS");
					final NumberPicker np0 = (NumberPicker) view.findViewById(R.id.numberPicker1Dim2);
					np0.setMaxValue(100);
					np0.setMinValue(0);
					np0.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					np0.setOnValueChangedListener(new OnValueChangeListener() {
						
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
							// Envia los resultados.
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_2)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick));
						}
					});
					break;
				case 1: // Seleccion yardas y suma 1 punto.
					view = inflater.inflate(R.layout.fragment_dim2_opt12att_opt12def_opt1234ee,
							container, false);
					Log.d("MARCADOR", "+1 PUNTOS");
					final NumberPicker np1 = (NumberPicker) view.findViewById(R.id.numberPicker1Dim2);
					np1.setMaxValue(100);
					np1.setMinValue(0);
					np1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					np1.setOnValueChangedListener(new OnValueChangeListener() {
						
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
							// Envia los resultados.
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_2)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick));
						}
					});
					break;
				case 2: // Seleccion yardas y suma 2 puntos.
					view = inflater.inflate(R.layout.fragment_dim2_opt12att_opt12def_opt1234ee,
							container, false);
					Log.d("MARCADOR", "+2 PUNTOS");
					final NumberPicker np2 = (NumberPicker) view.findViewById(R.id.numberPicker1Dim2);
					np2.setMaxValue(100);
					np2.setMinValue(0);
					np2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					np2.setOnValueChangedListener(new OnValueChangeListener() {
						
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
							// Envia los resultados.
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_2)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick));
						}
					});
					break;
				case 3: // Seleccion yardas.
					view = inflater.inflate(R.layout.fragment_dim2_opt12att_opt12def_opt1234ee,
							container, false);
					final NumberPicker np3 = (NumberPicker) view.findViewById(R.id.numberPicker1Dim2);
					np3.setMaxValue(100);
					np3.setMinValue(0);
					np3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					np3.setOnValueChangedListener(new OnValueChangeListener() {
						
						@Override
						public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
							// Envia los resultados.
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_2)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick));
						}
					});
					break;
				case 4: // Condicional Si/No.
					view = inflater.inflate(R.layout.fragment_dim2_opt4att_opt3def_opt5ee_dim3_opt23att_opt1def,
							container, false);
					TextView tv0 = (TextView) view.findViewById(R.id.textViewDim3);
					tv0.setText(getString(R.string.playingGround_fragment_dim2_att4_def3_ee5_textView_conditional));
					RadioGroup rgroup = (RadioGroup) view.findViewById(R.id.radioGroupDim3);
					rgroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId){
							// Obtiene el numero del RadioButton que se ha pulsado y asigna un string.
							int rbtnID = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
							String content = null;
							if(rbtnID==0){
								content = getString(R.string.playingGround_fragment_final_words_3);
							}else{
								content = getString(R.string.playingGround_fragment_final_words_4);
							}
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, content);
						}
					});
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			break;
		case 3: // Caso de dimension 3.
			Log.d("DEBUG", "ENTRA DIMENSION 3");
			switch(branchType){
			case 0: // Determina la rama de Layout para Ataque.
				switch(comeFromRbtn){ // Determina el Layout a utilizar segun rbtn seleccionado.
				case 0: //Mostrar resumen final y aceptar o cancelar el guardado de datos.
					view = inflater.inflate(R.layout.fragment_last_dimension_opt_all,
							container, false);
					String[] data = this.getArguments().getStringArray("dataToShow");
					TextView tvf0 = (TextView) view.findViewById(R.id.textViewFinal1);
					TextView tvf1 = (TextView) view.findViewById(R.id.textViewFinal2);
					TextView tvf2 = (TextView) view.findViewById(R.id.textViewFinal3);
					tvf0.setText(data[0]);
					tvf1.setText(data[1]+" "+data[2]);
					tvf2.setText("");
					break;
				case 1: // Condicional Si/No.
					view = inflater.inflate(R.layout.fragment_dim2_opt4att_opt3def_opt5ee_dim3_opt23att_opt1def,
							container, false);
					TextView tv0 = (TextView) view.findViewById(R.id.textViewDim3);
					tv0.setText(getString(R.string.playingGround_fragment_dim3_att23_textView_conditional));
					RadioGroup rgroup0 = (RadioGroup) view.findViewById(R.id.radioGroupDim3);
					rgroup0.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId){
							// Obtiene el numero del RadioButton que se ha pulsado y asigna un string.
							int rbtnID = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
							String content = null;
							if(rbtnID==0){
								content = getString(R.string.playingGround_fragment_final_words_5);
								Log.d("MARCADOR", "DOWN +1");
							}else{
								content = getString(R.string.playingGround_fragment_final_words_6);
								Log.d("MARCADOR", "DOWN A 1");
							}
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, content);
						}
					});
					break;
				case 2: // Condicional Si/No.
					view = inflater.inflate(R.layout.fragment_dim2_opt4att_opt3def_opt5ee_dim3_opt23att_opt1def,
							container, false);
					TextView tv1 = (TextView) view.findViewById(R.id.textViewDim3);
					tv1.setText(getString(R.string.playingGround_fragment_dim3_att23_textView_conditional));
					RadioGroup rgroup1 = (RadioGroup) view.findViewById(R.id.radioGroupDim3);
					rgroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId){
							// Obtiene el numero del RadioButton que se ha pulsado y asigna un string.
							int rbtnID = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
							String content = null;
							if(rbtnID==0){
								content = getString(R.string.playingGround_fragment_final_words_5);
								Log.d("MARCADOR", "DOWN +1");
							}else{
								content = getString(R.string.playingGround_fragment_final_words_6);
								Log.d("MARCADOR", "DOWN A 1");
							}
							mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, content);
						}
					});
					break;
				case 3: // Condicional Si/No, selecciona de yardas y jugador involucrado.
					view = inflater.inflate(R.layout.fragment_dim3_opt4att_opt3def_opt5ee,
							container, false);
					TextView tv2 = (TextView) view.findViewById(R.id.textView1Dim3);
					tv2.setText(getString(R.string.playingGround_fragment_dim3_att4_def3_ee5_textView_conditional));
					final NumberPicker np0 = (NumberPicker) view.findViewById(R.id.numberPickerDim3);
					np0.setMinValue(0);
					np0.setMaxValue(100);
					np0.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); // Se desactiva el teclado para el NumberPicker.
					np0.setEnabled(false);
					sentences.ObtenerDatosDelimitadosJugadores(getActivity(), this.getArguments().getIntArray("playersOnTheField")); // Mandamos la orden de obtener los datos de los jugadores.
					String[] nombreApellidoJugadores = sentences.getNombreApellidos();
					final Spinner sp = (Spinner) view.findViewById(R.id.spinnerDim3);
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(),
							android.R.layout.simple_spinner_item, nombreApellidoJugadores);
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp.setAdapter(dataAdapter);
					sp.setEnabled(false);
					RadioGroup rgroup2 = (RadioGroup) view.findViewById(R.id.radioGroup1Dim3);
					rgroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId){
							// Obtiene el numero del RadioButton que se ha pulsado y habilita el numberPicker.
							int rbtnID = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
							//Log.d("BOTON", ">> "+rbtnID);
							if(rbtnID==0){
								np0.setEnabled(true);
								np0.setOnValueChangedListener(new OnValueChangeListener() {
									@Override
									public void onValueChange(NumberPicker picker, int oldVal, final int newVal) {
										// Habilita el desplegable.
										sp.setEnabled(true);
										sp.setOnItemSelectedListener(new OnItemSelectedListener() {

											@Override
											public void onItemSelected(
													AdapterView<?> arg0,
													View arg1, int arg2,
													long arg3) {
												// Obtiene el texto del selector y envia datos.
												String SPtext = sp.getSelectedItem().toString();
												mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_10)+" "+getString(R.string.playingGround_fragment_final_words_12)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick)+" "+getString(R.string.playingGround_fragment_final_words_13)+" "+SPtext);
											}

											@Override
											public void onNothingSelected(
													AdapterView<?> arg0) {
												// Nada.
												
											}
											
										});
										//ASDADSADASADSADA
									}
								});
							}else{
								np0.setEnabled(false);
								sp.setEnabled(false);
								// Envia los datos del condicional.
								mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_11));
							}
						}
					});
					break;
				default:
					break;
				}
				break;
			case 1: // Determina la rama de Layout para Defensa.
				switch(comeFromRbtn){ // Determina el Layout a utilizar segun rbtn seleccionado.
				case 0: // Condicional Si/No.
					view = inflater.inflate(R.layout.fragment_dim2_opt4att_opt3def_opt5ee_dim3_opt23att_opt1def,
							container, false);
					TextView tv0 = (TextView) view.findViewById(R.id.textViewDim3);
					tv0.setText(getString(R.string.playingGround_fragment_dim3_def1_textView_conditional));
					RadioGroup rgroup0 = (RadioGroup) view.findViewById(R.id.radioGroupDim3);
					rgroup0.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId){
							// Obtiene el numero del RadioButton que se ha pulsado.
							int rbtnID = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
							if(rbtnID==0){
								Log.d("MARCADOR", "DOWN A 1");
								mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_7));
							}else{
								Log.d("MARCADOR", "DOWN A 1");
								mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_8));
							}
						}
					});
					break;
				case 1: //Mostrar resumen final y aceptar o cancelar el guardado de datos.
					view = inflater.inflate(R.layout.fragment_last_dimension_opt_all,
							container, false);
					String[] data = this.getArguments().getStringArray("dataToShow");
					TextView tvf0 = (TextView) view.findViewById(R.id.textViewFinal1);
					TextView tvf1 = (TextView) view.findViewById(R.id.textViewFinal2);
					TextView tvf2 = (TextView) view.findViewById(R.id.textViewFinal3);
					tvf0.setText(data[0]);
					tvf1.setText(data[1]+" "+data[2]);
					tvf2.setText("");
					break;
				case 2: // Condicional Si/No, selecciona de yardas y jugador involucrado.
					view = inflater.inflate(R.layout.fragment_dim3_opt4att_opt3def_opt5ee,
							container, false);
					TextView tv2 = (TextView) view.findViewById(R.id.textView1Dim3);
					tv2.setText(getString(R.string.playingGround_fragment_dim3_att4_def3_ee5_textView_conditional));
					final NumberPicker np0 = (NumberPicker) view.findViewById(R.id.numberPickerDim3);
					np0.setMinValue(0);
					np0.setMaxValue(100);
					np0.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); // Se desactiva el teclado para el NumberPicker.
					np0.setEnabled(false);
					RadioGroup rgroup2 = (RadioGroup) view.findViewById(R.id.radioGroup1Dim3);
					rgroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId){
							// Obtiene el numero del RadioButton que se ha pulsado.
							int rbtnID = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
							//Log.d("BOTON", ">> "+rbtnID);
							if(rbtnID==0){
								np0.setEnabled(true);
								np0.setOnValueChangedListener(new OnValueChangeListener() {
									@Override
									public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
										// Envia los datos del condicional y las yardas.
										mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_4)+" "+getString(R.string.playingGround_fragment_final_words_2)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick));
									}
								});
							}else{
								np0.setEnabled(false);
								// Envia los datos del condicional.
								mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_3));
							}
						}
					});
					break;
				default:
					break;
				}
				break;
			case 2: // Determina la rama de Layout para Equipos Especiales.
				switch(comeFromRbtn){ // Determina el Layout a utilizar segun rbtn seleccionado.
				case 0:
				case 1:
				case 2:
				case 3: //Mostrar resumen final y aceptar o cancelar el guardado de datos.
					view = inflater.inflate(R.layout.fragment_last_dimension_opt_all,
							container, false);
					String[] data = this.getArguments().getStringArray("dataToShow");
					TextView tvf0 = (TextView) view.findViewById(R.id.textViewFinal1);
					TextView tvf1 = (TextView) view.findViewById(R.id.textViewFinal2);
					TextView tvf2 = (TextView) view.findViewById(R.id.textViewFinal3);
					tvf0.setText(data[0]);
					tvf1.setText(data[1]+" "+data[2]);
					tvf2.setText("");
					break;
				case 4: // Condicional Si/No, selecciona de yardas y jugador involucrado.
					view = inflater.inflate(R.layout.fragment_dim3_opt4att_opt3def_opt5ee,
							container, false);
					TextView tv2 = (TextView) view.findViewById(R.id.textView1Dim3);
					tv2.setText(getString(R.string.playingGround_fragment_dim3_att4_def3_ee5_textView_conditional));
					final NumberPicker np0 = (NumberPicker) view.findViewById(R.id.numberPickerDim3);
					np0.setMinValue(0);
					np0.setMaxValue(100);
					np0.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); // Se desactiva el teclado para el NumberPicker.
					np0.setEnabled(false);
					RadioGroup rgroup2 = (RadioGroup) view.findViewById(R.id.radioGroup1Dim3);
					rgroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(RadioGroup group, int checkedId){
							// Obtiene el numero del RadioButton que se ha pulsado.
							int rbtnID = group.indexOfChild(group.findViewById(group.getCheckedRadioButtonId())); // Numero de rbtn.
							//Log.d("BOTON", ">> "+rbtnID);
							if(rbtnID==0){
								np0.setEnabled(true);
								np0.setOnValueChangedListener(new OnValueChangeListener() {
									@Override
									public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
										// Envia los datos del condicional y las yardas.
										mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_4)+" "+getString(R.string.playingGround_fragment_final_words_2)+" "+String.valueOf(newVal)+" "+getString(R.string.playingGround_fragment_textView_numPick));
									}
								});
							}else{
								np0.setEnabled(false);
								// Envia los datos del condicional.
								mCallback.onClickOptionEvent(comeFromRbtn, Dimension, branchType, getString(R.string.playingGround_fragment_final_words_3));
							}
						}
					});
					break;
				default:
					break;
				}
				break;
			}
			break;
		case 4: // Caso de dimension 4.
			switch(branchType){
			case 0: // Determina la rama de Layout para Ataque.
				switch(comeFromRbtn){ // Determina el Layout a utilizar segun rbtn seleccionado.
				// No se da el caso '0'.
				case 1:
				case 2:
				case 3: //Mostrar resumen final y aceptar o cancelar el guardado de datos.
					view = inflater.inflate(R.layout.fragment_last_dimension_opt_all,
							container, false);
					String[] data = this.getArguments().getStringArray("dataToShow");
					TextView tvf0 = (TextView) view.findViewById(R.id.textViewFinal1);
					TextView tvf1 = (TextView) view.findViewById(R.id.textViewFinal2);
					TextView tvf2 = (TextView) view.findViewById(R.id.textViewFinal3);
					tvf0.setText(data[0]);
					tvf1.setText(data[1]+" "+data[2]);
					tvf2.setText(data[3]);
					break;
				default:
					break;
				}
				break;
			case 1: // Determina la rama de Layout para Defensa.
				switch(comeFromRbtn){ // Determina el Layout a utilizar segun rbtn seleccionado.
				case 0:
				case 1:
				case 2: //Mostrar resumen final y aceptar o cancelar el guardado de datos.
					view = inflater.inflate(R.layout.fragment_last_dimension_opt_all,
							container, false);
					String[] data = this.getArguments().getStringArray("dataToShow");
					TextView tvf0 = (TextView) view.findViewById(R.id.textViewFinal1);
					TextView tvf1 = (TextView) view.findViewById(R.id.textViewFinal2);
					TextView tvf2 = (TextView) view.findViewById(R.id.textViewFinal3);
					tvf0.setText(data[0]);
					tvf1.setText(data[1]+" "+data[2]);
					tvf2.setText(data[3]);
					break;
				default:
					break;
				}
				break;
			case 2: // Determina la rama de Layout para Equipo Especial.
				switch(comeFromRbtn){ // Determina el Layout a utilizar segun rbtn seleccionado.
				// No se dan los casos '0', '1', '2', '3'.
				case 4: //Mostrar resumen final y aceptar o cancelar el guardado de datos.
					view = inflater.inflate(R.layout.fragment_last_dimension_opt_all,
							container, false);
					String[] data = this.getArguments().getStringArray("dataToShow");
					TextView tvf0 = (TextView) view.findViewById(R.id.textViewFinal1);
					TextView tvf1 = (TextView) view.findViewById(R.id.textViewFinal2);
					TextView tvf2 = (TextView) view.findViewById(R.id.textViewFinal3);
					tvf0.setText(data[0]);
					tvf1.setText(data[1]+" "+data[2]);
					tvf2.setText(data[3]);
					break;
				default:
					break;
				}
				break;
			}
			break;
		default:
			break;
		}
		return view;
	}

	// Interfaz de la clase.
	public interface OnPlayerOptionClickEvent {
		// "rbtnIDSelected" determina el RadioButton del que procede e indica por que fila continuar,(A PARTIR DE DIMENSION 2).
		// "lastDimension" determina la columna de donde procede el resultado, indica por que columna continuar(TODAS DIMENSIONES).
		// "branchType" determina el tipo de rama en la que se encuentra el jugador seleccionado(ataque/defensa/especial)(TODAS DIMENSIONES).
		// "dataSelected" determina el contenido del elemento seleccionado, da a conocer al usuario lo seleccionado en un resumen (TODAS DIMENSIONES).
		public void onClickOptionEvent(int rbtnIDSelected, int lastDimension, int branchType, String dataSelected);
		// En esta accion se aceptara lo mostrado en pantalla y se almacenara en la DB, todo desde la MAIN.
		public void onClickFinalOptionEvent();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OnPlayerOptionClickEvent) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " debe implementar la clase OnPlayerOptionClickEvent");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallback = null;
	}
}
