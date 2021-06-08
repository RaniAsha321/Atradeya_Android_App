package com.atradeya.asharani;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.Arrays;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Callback;

public class MultiSelectionSpinner extends androidx.appcompat.widget.AppCompatSpinner implements
        DialogInterface.OnMultiChoiceClickListener {


    public interface OnMultipleItemsSelectedListener{

        void selectedIndices(List<Integer> indices);
        void selectedStrings(List<String> strings);
    }

    private Callback<AssignEngineer> assignEngineerCallback;

    private OnMultipleItemsSelectedListener listener;

    String[] _items = null;
    boolean[] mSelection = null;
    boolean[] mSelectionAtStart = null;
    String _itemsAtStart = null;
    Context c;
    ArrayAdapter<String> simple_adapter;
    private boolean hasNone = false;

    public MultiSelectionSpinner(Context context) {
        super(context);
        c = context;
        simple_adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simple_adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);


    }

    public void setListener(OnMultipleItemsSelectedListener listener){
        this.listener = listener;
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelection != null && which < mSelection.length) {
            if(hasNone) {
                if (which == 0 && isChecked && mSelection.length > 1) {
                    for (int i = 1; i < mSelection.length; i++) {
                        mSelection[i] = false;
                        ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                    }
                } else if (which > 0 && mSelection[0] && isChecked) {
                    mSelection[0] = false;
                    ((AlertDialog) dialog).getListView().setItemChecked(0, false);
                }
            }


            mSelection[which] = isChecked;
            simple_adapter.clear();
            simple_adapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);

        builder.setTitle("Please select!");
        builder.setMultiChoiceItems(_items, mSelection, this);
        _itemsAtStart = getSelectedItemsAsString();

        Log.e("_itemsAtStart",_itemsAtStart+"");

        builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSelection(0);
           }
       });



        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.arraycopy(mSelection, 0, mSelectionAtStart, 0, mSelection.length);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                simple_adapter.clear();
                simple_adapter.add(_itemsAtStart);
                System.arraycopy(mSelectionAtStart, 0, mSelection, 0, mSelectionAtStart.length);
            }
        });

         builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(String[] items) {
        _items = items;
        mSelection = new boolean[_items.length];
        mSelectionAtStart = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[0]);
        Arrays.fill(mSelection, false);
        mSelection[0] = true;
        mSelectionAtStart[0] = true;
    }


    public void setItemsid(String[] items) {
        _items = items;
        mSelection = new boolean[_items.length];
        mSelectionAtStart = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[0]);
        Arrays.fill(mSelection, false);
        mSelection[0] = true;
        mSelectionAtStart[0] = true;

    }


    public void setSelection(int index) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
            mSelectionAtStart[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }

        Log.e("texta", buildSelectedItemString()+"");
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int[] selectedIndices) {

        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (int index : selectedIndices) {
            if (index >= 0 && index < mSelection.length) {
                mSelection[index] = true;
                mSelectionAtStart[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index
                        + " is out of bounds.");
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    private String buildSelectedItemString() {

        String[]projectlist2,temp;

        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        if((Paper.book().read("layout_project_edit","5").equals("1"))){

            String alloted=Paper.book().read("alloted_engineers");
            projectlist2 = alloted.split(",");

            String[] myarray = new String[projectlist2.length];

            for (int i=0; i<projectlist2.length;i++) {
                myarray[i]=projectlist2 [i];

                temp = myarray[i].split(" \\[");

            }

            sb.append(projectlist2);


            if((Paper.book().read("dataset","5").equals("1"))) {

                Log.e("readrty", "1");

                for (int i = 0; i < _items.length; ++i) {

                    if (mSelection[i]) {
                        if (foundOne) {
                            sb.append(", ");
                        }
                        foundOne = true;

                        sb.append(_items[i]);
                    }

                }
            }


        }

        else{

            for (int i = 0; i < _items.length; ++i) {

                if (mSelection[i]) {
                    if (foundOne) {
                        sb.append(", ");
                    }
                    foundOne = true;

                    sb.append(_items[i]);
                }
            }
        }

        Paper.book().write("engineer",sb.toString());

        return sb.toString();
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        if((Paper.book().read("dataset").equals("1"))){

            String alloted=Paper.book().read("alloted_engineers");
            sb.append(alloted);
        }

        else {

            for (int i = 0; i < _items.length; ++i) {
                if (mSelection[i]) {
                    if (foundOne) {
                        sb.append(", ");
                    }
                    foundOne = true;
                    sb.append(_items[i]);
                }
            }
        }

        return sb.toString();
    }

    public void hasNoneOption(boolean val){
        hasNone = val;
    }










/*

    String[] _items = null;
    boolean[] mSelection = null;

    ArrayAdapter<String> simple_adapter;

    public MultiSelectionSpinner(Context context) {
        super(context);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelection != null && which < mSelection.length) {
            mSelection[which] = isChecked;

            simple_adapter.clear();
            simple_adapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(_items, mSelection, this);
        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(String[] items) {
        _items = items;
        mSelection = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[0]);
        Arrays.fill(mSelection, false);
    }

    public void setItems(List<Oprativedatum> items) {
        _items = items.toArray(new String[items.size()]);
        mSelection = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[1]);
        Arrays.fill(mSelection, false);
    }

    public void setSelection(String[] selection) {
        for (String cell : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(cell)) {
                    mSelection[j] = true;
                }
            }
        }
    }

    public void setSelection(List<String> selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        for (String sel : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(sel)) {
                    mSelection[j] = true;
                }
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int index) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int[] selectedIndicies) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        for (int index : selectedIndicies) {
            if (index >= 0 && index < mSelection.length) {
                mSelection[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index
                        + " is out of bounds.");
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<String>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(_items[i]);
            }
        }
        return selection;
    }

    public List<Integer> getSelectedIndicies() {
        List<Integer> selection = new LinkedList<Integer>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;

                sb.append(_items[i]);
            }
        }
        return sb.toString();
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(_items[i]);
            }
        }
        return sb.toString();
    }

*/

}
