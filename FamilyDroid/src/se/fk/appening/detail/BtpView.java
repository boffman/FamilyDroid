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
package se.fk.appening.detail;

import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.fk.appening.Forman;
import se.fk.appening.MyApp;
import se.fk.appening.R;
import se.fk.fkappening01.ws.FKWsResponse;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BtpView extends ListActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setTitle(this.getString(R.string.app_name) + " - " + Forman.bostadstillagg);

		// Hämta response objektet från app context
		MyApp app = ((MyApp)getApplicationContext());
	    FKWsResponse lefiResponse = app.getLefiResponse();

		NodeList btpArendeNodes;
		List<String> values = new LinkedList<String>();
		try {
			btpArendeNodes = lefiResponse.getNodes("//ext:btpArende");
			for (int index = 0; index < btpArendeNodes.getLength(); ++index) {
				Node btpArendeNode = btpArendeNodes.item(index);
				
				String from = lefiResponse.getString("ext:from", btpArendeNode);
				String tom = lefiResponse.getString("ext:tom", btpArendeNode);
				String bokostnadBrutto = lefiResponse.getString("ext:bokostnad/ext:brutto", btpArendeNode);
				String bokostnadNettoBTP = lefiResponse.getString("ext:bokostnad/ext:nettoBTP", btpArendeNode);
				String bokostnadNettoAFS = lefiResponse.getString("ext:bokostnad/ext:nettoAFS", btpArendeNode);

				String beviljatbostadstillaggBTP = lefiResponse.getString("ext:beviljatbostadstillagg/ext:BTP", btpArendeNode);
				String beviljatbostadstillaggSBTP = lefiResponse.getString("ext:beviljatbostadstillagg/ext:SBTP", btpArendeNode);
				String beviljatbostadstillaggAFS = lefiResponse.getString("ext:beviljatbostadstillagg/ext:AFS", btpArendeNode);

				String medsokandePnr = lefiResponse.getString("ext:medsokande/ext:medsokandepersonnummer", btpArendeNode);

				if (bokostnadBrutto.length()==0) bokostnadBrutto="0";
				if (bokostnadNettoBTP.length()==0) bokostnadNettoBTP="0";
				if (bokostnadNettoAFS.length()==0) bokostnadNettoAFS="0";
				if (beviljatbostadstillaggBTP.length()==0) beviljatbostadstillaggBTP="0";
				if (beviljatbostadstillaggSBTP.length()==0) beviljatbostadstillaggSBTP="0";
				if (beviljatbostadstillaggAFS.length()==0) beviljatbostadstillaggAFS="0";
				
				String value = "From: " + from + ", tom: " + tom + 
					"\nBokostnad brutto: " + bokostnadBrutto + 
					"\nBokostnad netto BTP: " + bokostnadNettoBTP + 
					"\nBokostnad netto AFS: " + bokostnadNettoAFS + 
					"\nBeviljat bostadstillägg BTP: " + beviljatbostadstillaggBTP + 
					"\nBeviljat bostadstillägg SBTP: " + beviljatbostadstillaggSBTP + 
					"\nBeviljat bostadstillägg AFS: " + beviljatbostadstillaggAFS;
				if (medsokandePnr.length() > 0) {
					value += "\nMedsökande: " + medsokandePnr;
				}
				values.add(value);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    		
		setListAdapter(new ArrayAdapter<String>(this,
				R.layout.tfp_list_item, values));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});

	}

}