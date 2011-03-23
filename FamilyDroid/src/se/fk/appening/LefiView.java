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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.fk.appening.detail.AbbView;
import se.fk.appening.detail.BtpView;
import se.fk.appening.detail.FpView;
import se.fk.appening.detail.GpiView;
import se.fk.appening.detail.PensionView;
import se.fk.appening.detail.PrognosView;
import se.fk.appening.detail.SgiView;
import se.fk.appening.detail.SjpView;
import se.fk.appening.detail.TfpView;
import se.fk.appening.detail.UtbView;
import se.fk.fkappening01.ws.FKWsClient;
import se.fk.fkappening01.ws.FKWsClientImpl;
import se.fk.fkappening01.ws.FKWsException;
import se.fk.fkappening01.ws.FKWsResponse;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LefiView extends ListActivity {
	private static final String NAMESPACE = "http://schema.forsakringskassan.se/externa_intressenter";
	private static final String URL = "http://10.20.2.1:7001/LEFIAppeningWS/ws?WSDL";
	private static final String METHOD_NAME = "FK.Online.Fragakom";
	private static final String SOAP_ACTION = "http://10.20.2.1:7001/LEFIAppeningWS/ws";

	private FKWsResponse lefiResponse;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent myIntent = getIntent(); // this is just for example purpose
		String pnr = myIntent.getStringExtra("se.fk.appening.pnr");
		String name = myIntent.getStringExtra("se.fk.appening.name");
		
		this.setTitle(this.getString(R.string.app_name) + " - " + name + " - " + Tjanst.LEFI);
		
		FKWsClient client = new FKWsClientImpl(NAMESPACE, METHOD_NAME, URL,
				SOAP_ACTION);
		try {
			//lefiResponse = client.getFormansInformation("193802458988");
//			lefiResponse = client.getFormansInformation("197410356757");
//			lefiResponse = client.getFormansInformation("194201338763");
			lefiResponse = client.getFormansInformation(pnr);
		} catch (FKWsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    		
		// Spara undan response objektet i app context
		MyApp app = ((MyApp) getApplicationContext());
		app.setLefiResponse(lefiResponse);

		List<HashMap<String,String>> formanerInfo = new LinkedList<HashMap<String,String>>();

		NodeList formansInfoNodes = null;
		try {
			formansInfoNodes = lefiResponse
					.getNodes("//ext:formansforteckning/*");
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}

		if (formansInfoNodes.getLength() == 0) {
			HashMap<String, String> itemEntry = new HashMap<String,String>();
			itemEntry.put("title", "Inga förmåner hittades för " + pnr);
			itemEntry.put("descr", "");
			formanerInfo.add(itemEntry);			
		}
		else {
		
			for (int index = 0; index < formansInfoNodes.getLength(); ++index) {
				Node formanNode = formansInfoNodes.item(index);
				String nodeName = formanNode.getNodeName();
				Boolean nodeValue = Boolean.valueOf(formanNode.getTextContent());
				if (nodeValue == Boolean.TRUE) {
					HashMap<String, String> itemEntry = new HashMap<String,String>();
					String formanNamn = NameConverter.XmlToString(nodeName);
					itemEntry.put("title", formanNamn);
					itemEntry.put("descr", SummeraForman.get(formanNamn, lefiResponse));
					formanerInfo.add(itemEntry);
	//				formanerInfo.add(nodeName);
	//				formanerInfo.add(nodeName + "_beskrivning");
				}
			}
		}

		String[] from = {"title", "descr"};
		int[] to = {R.id.lefi_text1, R.id.lefi_text2};
        // Now create a new list adapter bound to the cursor.
        // SimpleListAdapter is designed for binding to a Cursor.
		//new SimpleAdapter(context, data, resource, from, to)
        ListAdapter adapter = new SimpleAdapter(
                this, // Context.
                formanerInfo,
                R.layout.lefi_list_item,  // Specify the row template to use (here, two columns bound to the two retrieved cursor rows).
                from,           // Array of cursor columns to bind to.
                to );  // Parallel array of which template objects to bind to those columns.

        setListAdapter(adapter);
		// setListAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, formaner));
//		setListAdapter(new ArrayAdapter<String>(this, R.layout.lefi_list_item,
//				formaner));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Context context = view.getContext();

				LinearLayout linearView = (LinearLayout)view;
				TextView titleView = (TextView)linearView.getChildAt(0);
				CharSequence value = titleView.getText();
				if (value.equals(Forman.pension)) {
					Intent myIntent = new Intent(context, PensionView.class);
					// myIntent.putExtra("Country", );

					startActivityForResult(myIntent, 0);
				} else if (value.equals(Forman.prognos)) {
					Intent myIntent = new Intent(context, PrognosView.class);
					startActivityForResult(myIntent, 0);
				} else if (value.equals(Forman.tillfalligForaldrapenning)) {
					Intent myIntent = new Intent(context, TfpView.class);
					startActivityForResult(myIntent, 0);
				} else if (value.equals(Forman.foraldrapenning)) {
					Intent myIntent = new Intent(context, FpView.class);
					startActivityForResult(myIntent, 0);
				} else if (value.equals(Forman.sjukpenninggrundandeInkomst)) {
					Intent myIntent = new Intent(context, SgiView.class);
					startActivityForResult(myIntent, 0);
				} else if (value.equals(Forman.sjukpenning)) {
					Intent myIntent = new Intent(context, SjpView.class);
					startActivityForResult(myIntent, 0);
				} else if (value.equals(Forman.utbetalning)) {
					Intent myIntent = new Intent(context, UtbView.class);
					startActivityForResult(myIntent, 0);
				} else if (value.equals(Forman.barnbidrag)) {
					Intent myIntent = new Intent(context, AbbView.class);
					startActivityForResult(myIntent, 0);
				} else if (value.equals(Forman.generellPersonInformation)) {
					Intent myIntent = new Intent(context, GpiView.class);
					startActivityForResult(myIntent, 0);
				} else if (value.equals(Forman.bostadstillagg)) {
					Intent myIntent = new Intent(context, BtpView.class);
					startActivityForResult(myIntent, 0);
				} else {
					// When clicked, show a toast with the TextView text
					Toast.makeText(getApplicationContext(),
							"Inga detaljer finns för " + value, Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}
}