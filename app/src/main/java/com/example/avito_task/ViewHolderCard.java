package com.example.avito_task;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderCard extends RecyclerView.ViewHolder {

    private TextView mText;
    private Button mDeleteButton;

    public ViewHolderCard(@NonNull View v)
    {
        super(v);
        mText = v.findViewById(R.id.textnumber);
        mDeleteButton=v.findViewById(R.id.deleteButton);
    }

    public TextView getLabel(){return mText;}
    public Button getButton(){return mDeleteButton;}

    public void setLabel(TextView text){this.mText = text;}
    public void setButton(Button button){this.mDeleteButton = button;}
}
