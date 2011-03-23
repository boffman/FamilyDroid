/*
Copyright (c) 2011 Team FK-Appening

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package se.fk.appening;

import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainView extends Activity implements OnClickListener
{
	private ImageView mParent1;
    private ImageView mParent2;
    private ImageView mChild1;
    private ImageView mChild2;
    private ImageView mAdd;
    private ImageView mBack;
    private int mPersonId = -1;
    private Dialog mAddDialog;
    private Dialog mServiceDialog;
    private EditText mAddPnr;
    private EditText mAddName;
    private TextView mTips;
    private String mPnr[];
    private String mName[];
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absolute);
        mParent1 = (ImageView) findViewById(R.id.parent1);
        mParent2 = (ImageView) findViewById(R.id.parent2);
        mChild1 = (ImageView) findViewById(R.id.child1);
        mChild2 = (ImageView) findViewById(R.id.child2);
        mAdd = (ImageView) findViewById(R.id.add);
        mBack = (ImageView) findViewById(R.id.back);
        mTips = (TextView) findViewById(R.id.tips);
        
        mParent1.setVisibility(View.INVISIBLE);
        mParent2.setVisibility(View.INVISIBLE);
        mChild1.setVisibility(View.INVISIBLE);
        mChild2.setVisibility(View.INVISIBLE);
        mBack.setVisibility(View.VISIBLE);
        mTips.setVisibility(View.VISIBLE);
        
        mParent1.setOnClickListener(this);
        mParent2.setOnClickListener(this);
        mChild1.setOnClickListener(this);
        mChild2.setOnClickListener(this);
        mAdd.setOnClickListener(this);
        
        mPnr = new String[4];
        mPnr[0] = new String();
        mPnr[1] = new String();
        mPnr[2] = new String();
        mPnr[3] = new String();
        
        mName = new String[4];
        mName[0] = new String();
        mName[1] = new String();
        mName[2] = new String();
        mName[3] = new String();
        
        mServiceDialog = new Dialog(this);
        mServiceDialog.setContentView(R.layout.service_dialog);
        mServiceDialog.setTitle("Select service");
        
        Gallery g = (Gallery) mServiceDialog.findViewById(R.id.gallery1);
        g.setAdapter(new ImageAdapter(this));

        g.setOnItemClickListener(new OnItemClickListener() 
        {
            public void onItemClick(AdapterView parent, View view, int position, long id)
            {
            	mParent1.setImageResource(R.drawable.parent);
        		mParent2.setImageResource(R.drawable.parent);
        		mChild1.setImageResource(R.drawable.child);
        		mChild2.setImageResource(R.drawable.child);
        		
            	mServiceDialog.cancel();
           
            	if(position == 3 && mPersonId != -1)
            	{
	            	Context context = view.getContext();
	        		Intent myIntent = new Intent(context, LefiView.class);
	        		myIntent.putExtra("se.fk.appening.pnr", mPnr[mPersonId]);
	        		myIntent.putExtra("se.fk.appening.name", mName[mPersonId]);
	        		startActivityForResult(myIntent, 0);
            	}
        		
                //Toast.makeText(MainView.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        mAddDialog = new Dialog(this);
        mAddDialog.setContentView(R.layout.add_dialog);
        mAddDialog.setTitle("Add family member");
        
        mAddPnr = (EditText) mAddDialog.findViewById(R.id.pnr);
        mAddName = (EditText) mAddDialog.findViewById(R.id.name);
        
        Button done = (Button) mAddDialog.findViewById(R.id.done);
        done.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View view)
            {
            	char[] pnrArr = mAddPnr.getText().toString().toCharArray();
            	
            	Date today = new Date();
            	int age = (1900 + today.getYear()) - 
            			  ((pnrArr[0]-'0')*1000 +
            			  (pnrArr[1]-'0')*100 +
            			  (pnrArr[2]-'0')*10 +
            			  (pnrArr[3]-'0'));
            	
            	if(age >= 40 && mParent1.getVisibility() == View.INVISIBLE)
            	{
            		mParent1.setVisibility(View.VISIBLE);
            		mPnr[0] = mAddPnr.getText().toString();
            		mName[0] = mAddName.getText().toString();
            	}
            	else if(age >= 40 && mParent2.getVisibility() == View.INVISIBLE)
            	{
            		mParent2.setVisibility(View.VISIBLE);
            		mPnr[1] = mAddPnr.getText().toString();
            		mName[1] = mAddName.getText().toString();
            	}
            	
            	if(age < 40 && mChild1.getVisibility() == View.INVISIBLE)
            	{
            		mChild1.setVisibility(View.VISIBLE);
            		mPnr[2] = mAddPnr.getText().toString();
            		mName[2] = mAddName.getText().toString();
            	}
            	else if(age < 40 && mChild2.getVisibility() == View.INVISIBLE)
            	{
            		mChild2.setVisibility(View.VISIBLE);
            		mPnr[3] = mAddPnr.getText().toString();
            		mName[3] = mAddName.getText().toString();
            	}
            	//Toast.makeText(getApplicationContext(), Integer.valueOf(age).toString(), Toast.LENGTH_SHORT).show();
            	mBack.setVisibility(View.INVISIBLE);
            	
            	mAddDialog.dismiss();
            }
        });
    }
    
	public void onClick(View view)
	{
		mPersonId = -1;
		
		mParent1.setImageResource(R.drawable.parent);
		mParent2.setImageResource(R.drawable.parent);
		mChild1.setImageResource(R.drawable.child);
		mChild2.setImageResource(R.drawable.child);
		
		if(view instanceof ImageView)
		{
			ImageView imageView = (ImageView) view;
	   
			switch(imageView.getId()) 
			{
		   		case R.id.parent1:
		   			//Toast.makeText(getApplicationContext(),"Parent 1 Clicked!!!", Toast.LENGTH_SHORT).show();
		   			mParent1.setImageResource(R.drawable.parent_focus);
		   			mPersonId = 0;
		    	break;
		   		case R.id.parent2:
		   			//Toast.makeText(getApplicationContext(),"Parent 2 Clicked!!!", Toast.LENGTH_SHORT).show();
		   			mParent2.setImageResource(R.drawable.parent_focus);
		   			mPersonId = 1;
		   		break;
		   		case R.id.child1:
		   			//Toast.makeText(getApplicationContext(),"Child 1 Clicked!!!", Toast.LENGTH_SHORT).show();
		   			mChild1.setImageResource(R.drawable.child_focus);
		   			mPersonId = 2;
		    	break;
		   		case R.id.child2:
		   			//Toast.makeText(getApplicationContext(),"Child 2 Clicked!!!", Toast.LENGTH_SHORT).show();
		   			mChild2.setImageResource(R.drawable.child_focus);
		   			mPersonId = 3;
		   		break;
		   		case R.id.add:
		   			mAddPnr.setText("");
		   			mAddName.setText("");
		   			mTips.setVisibility(View.INVISIBLE);
		   			mAddDialog.show();
		   		break;
			}
			
			if(mPersonId != -1)
			{
				setTitle(this.getString(R.string.app_name) + " - " + mName[mPersonId]);
				mServiceDialog.show();
			}
		}
	}
	
	public class ImageAdapter extends BaseAdapter
	{
	    private Context mContext;

	    private Integer[] mImageIds = {
	            R.drawable.pic1,
	            R.drawable.pic2,
	            R.drawable.pic3,
	            R.drawable.pic4,
	            R.drawable.pic5
	    };

	    public ImageAdapter(Context c)
	    {
	        mContext = c;
	    }

	    public int getCount() {
	        return mImageIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView i = new ImageView(mContext);

	        i.setImageResource(mImageIds[position]);
	        i.setLayoutParams(new Gallery.LayoutParams(88, 64));
	        i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

	        return i;
	    }
	}
}
