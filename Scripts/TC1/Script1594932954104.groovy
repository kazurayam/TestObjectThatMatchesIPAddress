import java.nio.file.Path
import java.nio.file.Paths

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path fixtureDir = projectDir.resolve('Include/fixture')
Path html = fixtureDir.resolve('aut.html')
WebUI.comment("html=${html.toString()}")
URL aut = html.toFile().toURI().toURL()

List<TestObject> tObjects = [
	makeTestObject("case1", "/*[string-length(normalize-space(translate(., '.0123456789','')))=0]")
	]

WebUI.openBrowser(aut.toString())

TestObject tObj = tObjects[0]
List<WebElement> matchedElements = WebUI.findWebElements(tObj, 5)
if (matchedElements.size() > 0) {
	WebUI.comment("================================================================================")
	WebUI.comment("XPath expression \"${getLocatorOf(tObj)}\" matched :")
	for (e in matchedElements) {
		def text = WebUI.getText(tObj)
		WebUI.comment("    ${text}")
	}
} else {
	WebUI.comment("XPath expression \"${getLocatorOf(tObj)}\" had no match")
}

WebUI.closeBrowser()

/**
 * 
 * @param id
 * @param xpath
 * @return
 */
TestObject makeTestObject(String id, String xpath) {
	TestObject tObj = new TestObject(id)
	tObj.addProperty('xpath', ConditionType.EQUALS, xpath)
	return tObj
}

/**
 * 
 * @param testObject
 * @return
 */
String getLocatorOf(TestObject testObject) {
	SelectorMethod method = testObject.getSelectorMethod()
	switch (method) {
		case SelectorMethod.BASIC:
			return testObject.getSelectorCollection()[SelectorMethod.BASIC]
			break
		case SelectorMethod.CSS:
			return testObject.getSelectorCollection()[SelectorMethod.CSS]
			break
		case SelectorMethod.XPATH:
			return testObject.getSelectorCollection()[SelectorMethod.XPATH]
			break
	}
}