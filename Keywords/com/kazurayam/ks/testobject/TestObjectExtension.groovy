package com.kazurayam.ks.testobject

import org.openqa.selenium.By

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject

import groovy.json.JsonOutput

/**
 * TestObjectExtention.apply() extends Katalon's TestObject.
 * TestObject will be added with a few methods which
 * - coverts it to a JSON String
 * - convers it to a By object of WebDriver
 *
 * @author kazurayam
 */
class TestObjectExtension {

	@Keyword
	static void apply() {
		TestObject.metaClass.invokeMethod = { String name, args ->
			switch (name) {
				case "toJson" :
					return toJson(delegate)
					break
				case "prettyPrint" :
					return prettyPrint(delegate)
					break
				case "toBy" :
					return toBy(delegate)
					break
				default :
				// just do what TestObject is designed to do
					def result
					try {
						result = delegate.metaClass.getMetaMethod(name, args).invoke(delegate, args)
					} catch (Exception e) {
						System.err.println("call to method $name raised an Exception")
						e.printStackTrace()
					}
					return result
			}
		}
	}

	/**
	 * make a String representation of this TestObject instance
	 * in JSON format. All properties are rendered.
	 *
	 * @param testObject
	 * @return JSON string. e.g.
	 * <pre>
	 * {"objectId": "Page_CURA Healthcare Service/a_Make Appointment","selectorMethod": "BASIC","selectorCollection": {"BASIC": "//a[@id='btn']"}}
	 * </pre>
	 */
	@Keyword
	static String toJson(TestObject testObject) {
		Objects.requireNonNull(testObject, "testObject must not be null")
		String json = JsonOutput.toJson(testObject)
		return json
	}

	/**
	 * make a String representation in pretty-printed JSON format.
	 *
	 * @param testObject
	 * @return JSON string. e.g.
	 * <pre>
	 * {
	 *     "objectId": "Page_CURA Healthcare Service/a_Make Appointment",
	 *     "selectorMethod": "BASIC",
	 *     "selectorCollection": {
	 *         "BASIC": "//a[@id='btn']"
	 *     }
	 * }
	 * </pre>
	 */
	static String prettyPrint(TestObject testObject) {
		Objects.requireNonNull(testObject, "testObject must not be null")
		String json = JsonOutput.toJson(testObject)
		String pp = JsonOutput.prettyPrint(json)
		return pp
	}

	/**
	 * create a new instance of Selenium By class out of Katalon TestObjec.
	 *
	 * @param testObject
	 * @return
	 */
	@Keyword
	static By toBy(TestObject testObject) {
		Objects.requireNonNull(testObject, "testObject must not be null")
		switch (testObject.selectorMethod) {
			case 'BASIC' :
				return By.xpath(testObject.getSelectorCollection()[SelectorMethod.BASIC])
				break
			case 'CSS' :
				return By.cssSelector(testObject.getSelectorCollection()[SelectorMethod.CSS])
				break
			case 'XPATH' :
				return By.xpath(testObject.getSelectorCollection()[SelectorMethod.XPATH])
				break
			default :
				throw new IllegalArgumentException("unable to convert to By: " + prettyPrint(testObject))
		}
	}
	
	@Keyword
	static List<By> toBy(List<TestObject> testObjectList) {
		List<By> list = new ArrayList<By>()
		for (TestObject to : testObjectList) {
			list.add(TestObjectExtension.toBy(to))
		}
		return list
	}

}