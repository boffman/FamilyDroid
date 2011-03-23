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

public class PrognosView extends ListActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setTitle(this.getString(R.string.app_name) + " - " + Forman.prognos);

		// Hämta response objektet från app context
		MyApp app = ((MyApp)getApplicationContext());
	    FKWsResponse lefiResponse = app.getLefiResponse();

		NodeList prognosantagandeNodes;
		String prognosbelopp = null;
		String alder = null;
		String tillvaxt = null;
		List<String> values = new LinkedList<String>();
		try {
			prognosantagandeNodes = lefiResponse.getNodes("//ext:prognosantagande");
			for (int index = 0; index < prognosantagandeNodes.getLength(); ++index) {
				Node prognosantagandeNode = prognosantagandeNodes.item(index);
				prognosbelopp = lefiResponse.getString("ext:prognosbelopp", prognosantagandeNode);
				alder = lefiResponse.getString("ext:alder", prognosantagandeNode);
				tillvaxt = lefiResponse.getString("ext:tillvaxt", prognosantagandeNode);
				if (prognosbelopp.length() > 0 && alder.length() > 0 && tillvaxt.length() > 0) {
					values.add("Vid " + alder + " år, " + prognosbelopp + ":- med " + tillvaxt + "% tillväxt");
				}
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