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

import javax.xml.xpath.XPathException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.fk.fkappening01.ws.FKWsResponse;

public class SummeraForman {
	static public String get(String forman, FKWsResponse lefiResponse) {
		try {
			if (Forman.pension.equals(forman)) {
				Node pensionNode = lefiResponse.getNode("//ext:formansinformation/ext:pension");
				if (pensionNode == null) {
					return "";
				}
				Double manadsBeloppSumma = 0.0;
				NodeList manadsbeloppNodes = lefiResponse.getNodes("//ext:manadsbelopp", pensionNode);
				for (int ix=0; ix<manadsbeloppNodes.getLength(); ++ix) {
					Node manadsBeloppVardeNode = manadsbeloppNodes.item(ix).getFirstChild();
					if (manadsBeloppVardeNode != null) {
						manadsBeloppSumma += Double.valueOf(manadsBeloppVardeNode.getNodeValue());
					}
				}
				return "Månadsbelopp: " + manadsBeloppSumma;
			}

			if (Forman.tillfalligForaldrapenning.equals(forman)) {
				Double antalDagarSumma = 0.0;
				NodeList antalDagarNodes = lefiResponse.getNodes("//ext:tfpArende/ext:period/ext:antalDagar");
				for (int ix=0; ix<antalDagarNodes.getLength(); ++ix) {
					Node antalDagarVardeNode = antalDagarNodes.item(ix).getFirstChild();
					if (antalDagarVardeNode != null) {
						antalDagarSumma += Double.valueOf(antalDagarVardeNode.getNodeValue());
					}
				}
				return "Totalt antal dagar: " + antalDagarSumma.intValue();
			}

			if (Forman.foraldrapenning.equals(forman)) {
				Double antalDagarSumma = 0.0;
				NodeList antalDagarNodes = lefiResponse.getNodes("//ext:fpArende/ext:period/ext:antalDagar");
				for (int ix=0; ix<antalDagarNodes.getLength(); ++ix) {
					Node antalDagarVardeNode = antalDagarNodes.item(ix).getFirstChild();
					if (antalDagarVardeNode != null) {
						antalDagarSumma += Double.valueOf(antalDagarVardeNode.getNodeValue());
					}
				}
				return "Totalt antal dagar: " + antalDagarSumma.intValue();
			}
		} catch (XPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}

		return "";
	}
}
