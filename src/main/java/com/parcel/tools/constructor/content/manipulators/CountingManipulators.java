package com.parcel.tools.constructor.content.manipulators;

import com.parcel.tools.constructor.content.PageContent;

public class CountingManipulators extends PageContent {
	public CountingManipulators() {
		this.title = "Расчет манипуляторов";

		this.additionalCss.add("web/css/countingManipulators.css");
		this.additionalScripts.add("web/javascript/countingManipulators.js");
	}
}
