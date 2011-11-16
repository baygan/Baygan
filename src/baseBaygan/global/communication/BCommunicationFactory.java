package baseBaygan.global.communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import baseBaygan.BAbstractComponent;
import baseBaygan.BBaseStatics;

public abstract class BCommunicationFactory implements BBaseStatics {
	public HashMap<String, ArrayList<String>> componentInterfaces;

	public BCommunicationFactory() {
		componentInterfaces = new HashMap<String, ArrayList<String>>();
		try {
			parseConfig();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseConfig() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(CONFIG_COMMUNICATION))));
		String line;
		while ((line = br.readLine()) != null) {
			String[] split = line.split(" ");
			String type = split[0];
			String name = split[1];
			String value = split[2];
			if (type.equalsIgnoreCase("AddInterface")) {
				// Reading value from map
				ArrayList<String> list = componentInterfaces.get(name);
				if (list == null) {// not found
					// creating list
					list = new ArrayList<String>();
					// adding the new value
					list.add(value);
					// insert the value into map
					componentInterfaces.put(name, list);
				} else {// found
					// only add new value
					list.add(value);
				}
			}
		}
	}

	public abstract BCommunicationSystem createCommunicationSystem(
			BAbstractComponent component);

}
