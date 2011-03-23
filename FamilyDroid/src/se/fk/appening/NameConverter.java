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
import java.util.Map;

import se.fk.fkappening01.ws.util.ErrorUtil;

public class NameConverter {
	static Map<String, String> xmlToString = new HashMap<String,String>();
	static {
//		xmlToString.put("ext:arbetsskadelivranta","Arbetsskadelivränta"); 
//		xmlToString.put("ext:sjukOchAktivitetsersattning","Sjuk- och aktivitetsersättning"); 
//		xmlToString.put("ext:pension","Pension"); 
//		xmlToString.put("ext:prognos","Prognos"); 
//		xmlToString.put("ext:tillfalligForaldrapenning","Tillfällig föräldrapenning"); 
//		xmlToString.put("ext:sjukpenninggrundandeInkomst","Sjukpenninggrundande inkomst"); 
//		xmlToString.put("ext:levnadsintyg","Levnadsintyg"); 
//		xmlToString.put("ext:sjukpenning","Sjukpenning"); 
//		xmlToString.put("ext:foraldrapenning","Föräldrapenning"); 
//		xmlToString.put("ext:rehabersattning","Rehabersättning"); 
//		xmlToString.put("ext:smittbararersattning","Smittbärarersättning"); 
//		xmlToString.put("ext:havandeskapspenning","Havandeskapspenning"); 
//		xmlToString.put("ext:narstaendepenning","Närståendepenning"); 
//		xmlToString.put("ext:dagpenningTillTotalforsvarspliktiga","Dagpenning till totalförsvarspliktiga"); 
//		xmlToString.put("ext:aktivitetsstod","Aktivitetsstod"); 
//		xmlToString.put("ext:barnbidrag","Barnbidrag"); 
//		xmlToString.put("ext:bostadstillagg","Bostadstillägg"); 
//		xmlToString.put("ext:efterlevandepension","Efterlevandepension"); 
//		xmlToString.put("ext:handikappersattning","Handikappersättning"); 
//		xmlToString.put("ext:generellPersonInformation","Generell personinformation"); 
//		xmlToString.put("ext:underhallsstod","Underhallsstöd"); 
//		xmlToString.put("ext:utbetalning","Utbetalning"); 
//		xmlToString.put("ext:vardbidrag","Vårdbidrag"); 
//		xmlToString.put("ext:yrkesskadelivranta","Yrkesskadelivränta"); 		
        xmlToString.put("ext:arbetsskadelivranta", Forman.arbetsskadelivranta);
        xmlToString.put("ext:sjukOchAktivitetsersattning", Forman.sjukOchAktivitetsersattning);
        xmlToString.put("ext:pension", Forman.pension);
        xmlToString.put("ext:prognos", Forman.prognos);
        xmlToString.put("ext:tillfalligForaldrapenning", Forman.tillfalligForaldrapenning);
        xmlToString.put("ext:sjukpenninggrundandeInkomst", Forman.sjukpenninggrundandeInkomst);
        xmlToString.put("ext:levnadsintyg", Forman.levnadsintyg);
        xmlToString.put("ext:sjukpenning", Forman.sjukpenning);
        xmlToString.put("ext:foraldrapenning", Forman.foraldrapenning);
        xmlToString.put("ext:rehabersattning", Forman.rehabersattning);
        xmlToString.put("ext:smittbararersattning", Forman.smittbararersattning);
        xmlToString.put("ext:havandeskapspenning", Forman.havandeskapspenning);
        xmlToString.put("ext:narstaendepenning", Forman.narstaendepenning);
        xmlToString.put("ext:dagpenningTillTotalforsvarspliktiga", Forman.dagpenningTillTotalforsvarspliktiga);
        xmlToString.put("ext:aktivitetsstod", Forman.aktivitetsstod);
        xmlToString.put("ext:barnbidrag", Forman.barnbidrag);
        xmlToString.put("ext:bostadstillagg", Forman.bostadstillagg);
        xmlToString.put("ext:efterlevandepension", Forman.efterlevandepension);
        xmlToString.put("ext:handikappersattning", Forman.handikappersattning);
        xmlToString.put("ext:generellPersonInformation", Forman.generellPersonInformation);
        xmlToString.put("ext:underhallsstod", Forman.underhallsstod);
        xmlToString.put("ext:utbetalning", Forman.utbetalning);
        xmlToString.put("ext:vardbidrag", Forman.vardbidrag);
        xmlToString.put("ext:yrkesskadelivranta", Forman.yrkesskadelivranta);

	}
	
	static public String XmlToString(String xmlName) {
		if (xmlToString.containsKey(xmlName)) {
			return xmlToString.get(xmlName);
		}
		else {
			ErrorUtil.log("XmlToString: xmlName " + xmlName + " does not have a mapping!");
			throw new RuntimeException("XmlToString: xmlName " + xmlName + " does not have a mapping!");
		}
	}
}
